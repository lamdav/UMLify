package model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import generator.IClassModel;
import utility.IFilter;

public class ClassModelTest {

	@Test
	public void testGetField() {
		ASMServiceProvider parser = new ASMParser();
		ClassModel model = parser.getClassByName("java.lang.String");
		assertEquals("java.lang.String", model.getName());

		Set<String> fields = new HashSet<>();
		Set<String> actfields = new HashSet<>(
				Arrays.asList("value", "hash", "serialVersionUID", "serialPersistentFields", "CASE_INSENSITIVE_ORDER"));

		for (FieldModel field : model.getFields())
			fields.add(field.getName());

		assertEquals(actfields, fields);
	}

	@Test
	public void testGetMethods() {
		ASMServiceProvider parser = new ASMParser();
		ClassModel model = parser.getClassByName("model.Dummy");

		Set<String> actual = new HashSet<>();
		Set<String> expected = new HashSet<>(Arrays.asList("publicMethod", "publicMethod2"));

		IFilter<MethodModel> getInstanceMethod = new IFilter<MethodModel>() {
			@Override
			public boolean filter(MethodModel data) {
				switch (data.getMethodType()) {
				case METHOD:
					return true;
				default:
					return false;
				}
			}
		};

		for (MethodModel x : getInstanceMethod.filter(model.getMethods()))
			actual.add(x.getName());

		assertEquals(expected, actual);
	}

	@Test
	public void testGetInterface() {
		ASMServiceProvider parser = new ASMParser();
		ClassModel model = parser.getClassByName("java.lang.String");
		assertEquals("java.lang.String", model.getName());

		Set<String> acutalInterfaces = new HashSet<>();
		Set<String> expectInterfaces = new HashSet<>();

		expectInterfaces.add("java.io.Serializable");
		expectInterfaces.add("java.lang.Comparable");
		expectInterfaces.add("java.lang.CharSequence");

		for (IClassModel interf : model.getInterfaces())
			acutalInterfaces.add(interf.getName());

		assertEquals(expectInterfaces, acutalInterfaces);
	}

	@Test
	public void testGetStringInterfaceNonRecursive() {
		ASMClassTracker parser = ASMParser.getInstance(new IModelConfiguration() {
			@Override
			public boolean isRecursive() {
				return false;
			}

			@Override
			public Iterable<String> getClasses() {
				return Arrays.asList("java.lang.String", "java/io/Serializable", "java/lang/Comparable");
			}
		});
		ClassModel model = parser.getClassByName("java/lang/String");
		assertEquals("java.lang.String", model.getName());

		Set<String> acutalInterfaces = new HashSet<>();
		Set<String> expectInterfaces = new HashSet<>();

		expectInterfaces.add("java.io.Serializable");
		expectInterfaces.add("java.lang.Comparable");

		for (IClassModel interf : model.getInterfaces())
			acutalInterfaces.add(interf.getName());

		assertEquals(expectInterfaces, acutalInterfaces);
	}

	@Test
	public void testGetInterfaceLab_1_AmazonParser() {
		ASMServiceProvider parser = new ASMParser();
		ClassModel model = parser.getClassByName("problem.AmazonLineParser");
		assertEquals("problem.AmazonLineParser", model.getName());

		Set<String> acutalInterfaces = new HashSet<>();
		Set<String> expectInterfaces = new HashSet<>();

		expectInterfaces.add("problem.ILineParser");

		for (IClassModel interf : model.getInterfaces())
			acutalInterfaces.add(interf.getName());

		assertEquals(expectInterfaces, acutalInterfaces);
	}

}