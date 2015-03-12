package objectexplorer;

import java.util.HashSet;

import org.junit.Test;

public class ObjectSerializerTest {

	@Test
	public void simpleTest() {
		Object rootObject = new HashSet();
		System.out.println(ObjectGraphMeasurer.measure(rootObject));
		System.out.println(ObjectSerializer.measure(rootObject));
	}

}
