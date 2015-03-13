package objectexplorer;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;

import org.junit.Test;

public class ObjectSerializerTest {

	@Test
	public void simpleTest() {
//		HashMap rootObject1 = new HashMap();
//		rootObject1.put(2, 2);
//		rootObject1.put(3, 3);
//		rootObject1.put(4, 4);
//		rootObject1.put(1, 1);
//		rootObject1.put("new dog", "new dog");
//
//		HashMap rootObject2 = new HashMap();
//		rootObject2.put(1, 1);
//		rootObject2.put(4, 4);
//		rootObject2.put("new dog", "new dog");
//		rootObject2.put(2, 2);
//		rootObject2.put(3, 3);

//		Object rootObject1 = new LinkedHashSet(Arrays.asList(2, 3, 4, 1, "new dog"));
//		Object rootObject2 = new LinkedHashSet(Arrays.asList(1, 4, "new dog", 2, 3));		
		
//		Object rootObject1 = new HashSet(Arrays.asList(2, 3, 4, 1, "new dog"));
//		Object rootObject2 = new HashSet(Arrays.asList(1, 4, "new dog", 2, 3));
		
//		Object rootObject1 = new HashSet(Arrays.asList(2, 3, 4, 1, "new dog", 17));
//		Object rootObject2 = new HashSet(Arrays.asList(17, 1, 4, "new dog", 2, 3));
		
		HashSet rootObject1 = new HashSet(Arrays.asList(2, 3, 4, 1, "new dog", 17));
		HashSet rootObject2 = new HashSet(Arrays.asList(2, 3, 4, 1, "new dog"));
		rootObject2.add(17);
		rootObject2.add(15);
		rootObject2.remove(15);
		
		assertEquals(rootObject2, rootObject2);
		
		System.out.println(ObjectGraphMeasurer.measure(rootObject1));
		System.out.println(ObjectGraphMeasurer.measure(rootObject2));
		
		System.out.print("\n\n\n");
		
		System.out.println(ObjectSerializer.measure(rootObject1));
		System.out.print("\n\n\n");
		System.out.println(ObjectSerializer.measure(rootObject2));
	}

}
