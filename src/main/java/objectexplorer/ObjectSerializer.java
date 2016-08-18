package objectexplorer;

import java.lang.reflect.Array;
import java.util.EnumSet;

import objectexplorer.ObjectExplorer.Feature;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;

/**
 * A tool that can qualitatively measure the footprint ({@literal e.g.}, number
 * of objects, references, primitives) of a graph structure.
 */
public class ObjectSerializer {

	/**
	 * Measures the footprint of the specified object graph. The object graph is
	 * defined by a root object and whatever object can be reached through that,
	 * excluding static fields, {@code Class} objects, and fields defined in
	 * {@code enum}s (all these are considered shared values, which should not
	 * contribute to the cost of any single object graph).
	 * <p>
	 * Equivalent to {@code measure(rootObject, Predicates.alwaysTrue())}.
	 *
	 * @param rootObject
	 *            the root object of the object graph
	 * @return the footprint of the object graph
	 */
	public static String measure(Object rootObject) {
		return measure(rootObject, Predicates.alwaysTrue());
	}

	/**
	 * Measures the footprint of the specified object graph. The object graph is
	 * defined by a root object and whatever object can be reached through that,
	 * excluding static fields, {@code Class} objects, and fields defined in
	 * {@code enum}s (all these are considered shared values, which should not
	 * contribute to the cost of any single object graph), and any object for
	 * which the user-provided predicate returns {@code false}.
	 *
	 * @param rootObject
	 *            the root object of the object graph
	 * @param objectAcceptor
	 *            a predicate that returns {@code true} for objects to be
	 *            explored (and treated as part of the footprint), or
	 *            {@code false} to forbid the traversal to traverse the given
	 *            object
	 * @return the footprint of the object graph
	 */
	public static String measure(Object rootObject, Predicate<Object> objectAcceptor) {
		Preconditions.checkNotNull(objectAcceptor, "predicate");

		Predicate<Chain> completePredicate = Predicates.and(ImmutableList.of(
						ObjectExplorer.notEnumFieldsOrClasses,
						Predicates.compose(objectAcceptor, ObjectExplorer.chainToObject),
						// ObjectExplorer.skipTransientFields,
						new ObjectExplorer.AtMostOncePredicate()));

		return ObjectExplorer.exploreObject(rootObject, new ObjectGraphVisitor(completePredicate),
						EnumSet.of(Feature.VISIT_PRIMITIVES, Feature.VISIT_NULL));
	}

	private static class ObjectGraphVisitor implements ObjectVisitor<String> {
		private final Predicate<Chain> predicate;
		private final StringBuffer sb = new StringBuffer(32);

		ObjectGraphVisitor(Predicate<Chain> predicate) {
			this.predicate = predicate;
		}

		public Traversal visit(Chain chain) {
			if (chain.isPrimitive()) {
				chain.toString(sb);
				sb.append(": ");
				sb.append(chain.getValue().toString());
				sb.append("\n");
				return Traversal.SKIP;
			} else if (predicate.apply(chain)) {
				Object value = chain.getValue();

				if (value != null) {
					chain.toString(sb);
					sb.append(": ");
					sb.append(value.getClass().toString());

					if (value.getClass().isArray()) {
						int arrayLength = Array.getLength(value);
						sb.append(", length = ");
						sb.append(arrayLength);
					}

					sb.append("\n");

					return Traversal.EXPLORE;
				} else {
					chain.toString(sb);
					sb.append(": ");
					sb.append("null");
					sb.append("\n");

					return Traversal.SKIP;
				}
			} else {
				return Traversal.SKIP;
			}
		}

		public String result() {
			return sb.toString();
		}
	}
}
