package ilm.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainObject;

import org.junit.Before;
import org.junit.Test;

import example.ilm.model.IlmDomainModel;
import example.ilm.model.ObjectSubString;

public class IlmDomainModelTests {

	private IlmDomainModel objUnderTest;
	
	@Before
	public void setUp() {
		objUnderTest = new IlmDomainModel();
	}
	
	@Test
	public void testAddSubstring() {
		AssignmentState expected = new AssignmentState();
		String s = "a";
		expected.add(new ObjectSubString(s + 1, s, s));
		AssignmentState result = new AssignmentState();
		objUnderTest.AddSubString(result, s);
		assertTrue(compareDomainObjectList(expected.getList(), result.getList()));
	}

	@Test
	public void testRemoveSubstring() {
		AssignmentState expected = new AssignmentState();
		expected.add(new ObjectSubString("a" + 1, "a", "a"));
		expected.add(new ObjectSubString("b" + 2, "b", "b"));
		AssignmentState result = new AssignmentState();
		result.setList(expected.getList());
		result.add(new ObjectSubString("c" + 3, "c", "c"));
		objUnderTest.RemoveSubString(result);
		assertTrue(compareDomainObjectList(expected.getList(), result.getList()));
	}
	
	private boolean compareDomainObjectList(ArrayList<DomainObject> listA, ArrayList<DomainObject> listB) {
		if(listA.size() != listB.size()) {
			return false;
		}
		for(int i = 0; i < listA.size(); i++) {
			if(!listA.get(i).equals(listB.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	@Test
	public void testAutomaticChecking() {
		AssignmentState expected = new AssignmentState();
		expected.add(new ObjectSubString("a" + 1, "a", "a"));
		expected.add(new ObjectSubString("b" + 2, "b", "b"));
		expected.add(new ObjectSubString("c" + 3, "c", "c"));
		AssignmentState result = new AssignmentState();
		result.add(new ObjectSubString("a" + 1, "a", "a"));
		result.add(new ObjectSubString("c" + 2, "c", "c"));
		result.add(new ObjectSubString("c" + 3, "c", "c"));
		assertEquals(objUnderTest.AutomaticChecking(result, expected), 2/3, 0.001);
	}
	
}
