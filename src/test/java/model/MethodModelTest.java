package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import utility.IFilter;
import utility.MethodType;
import utility.Modifier;

public class MethodModelTest {

	@Test
	public void testGetDependentMethods() {
		ClassModel dummy = ASMParser.getClassByName("dummy.Dummy");

		assertEquals("dummy.Dummy", dummy.getName());

		MethodModel methodModel = dummy.getMethodBySignature(Signature.parse("publicMethod", "()LString"));

		assertTrue(methodModel != null);
		
		Set<String> expected = new HashSet<>(Arrays.asList("append", "toString", "java.lang.StringBuilder"));
		Collection<String> actual = new ArrayList<>();

		Collection<MethodModel> methods = methodModel.getDependentMethods();
		methods.forEach((m) -> actual.add(m.getName()));

		assertEquals(expected.size(), actual.size());
		assertEquals(expected, new HashSet<>(actual));
	}

	@Test
	public void testGetDependentFields() {
		ClassModel dummy = ASMParser.getClassByName("dummy.Dummy");

		IFilter<MethodModel> filter = (d) -> d.getModifier() == Modifier.PRIVATE
				&& d.getMethodType() == MethodType.METHOD;

		assertEquals("dummy.Dummy", dummy.getName());
		Iterator<? extends MethodModel> itr = filter.filter(dummy.getMethods()).iterator();

		MethodModel methodModel = itr.next();
		assertEquals("privateMethod", methodModel.getName());
		assertFalse(itr.hasNext());

		Set<String> expected = new HashSet<>(Arrays.asList("proctedField", "defaultField", "publicField"));
		Collection<String> actual = new ArrayList<>();

		Collection<FieldModel> method = methodModel.getDependentFields();
		method.forEach((m) -> actual.add(m.getName()));

		assertEquals(expected.size(), actual.size());
		assertEquals(expected, new HashSet<>(actual));
	}
}