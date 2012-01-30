package ilm.model;

import static org.junit.Assert.*;
import ilm.framework.assignment.model.DomainAction;
import ilm.framework.assignment.model.DomainObject;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class IlmDomainConverterTests {

	private IlmDomainConverter objUnderTest;
	private String objListString;
	private String actionListString;
	private ArrayList<DomainObject> objList;
	private ArrayList<DomainAction> actionList;
	
	@Before
	public void setUp() {
		objUnderTest = new IlmDomainConverter();
		
		objListString = "<objects>" +
						"<objectsubstring>" +
						"<name>a</name>" +
						"<description>a</description>" +
						"<substring>a</substring>" +
						"</objectsubstring>" +
						"<objectsubstring>" +
						"<name>s</name>" +
						"<description>s</description>" +
						"<substring>s</substring>" +
						"</objectsubstring>" +
						"<objectsubstring>" +
						"<name>q</name>" +
						"<description>q</description>" +
						"<substring>q</substring>" +
						"</objectsubstring>" +
						"<objectsubstring>" +
						"<name>t</name>" +
						"<description>t</description>" +
						"<substring>t</substring>" +
						"</objectsubstring>" +
						"</objects>";
		
		actionListString = "<actions>" +
						"<addaction>" +
						"<name>add</name>" +
						"<description>add: b</description>" +
						"<substring>b</substring>" +
						"</addaction>" +
						"<addaction>" +
						"<name>add</name>" +
						"<description>add: a</description>" +
						"<substring>a</substring>" +
						"</addaction>" +
						"<removeaction>" +
						"<name>del</name>" +
						"<description>del: a</description>" +
						"<substring>a</substring>" +
						"</removeaction>" +
						"</actions>";
		
		objList = new ArrayList<DomainObject>();
		objList.add(new ObjectSubString("a", "a", "a"));
		objList.add(new ObjectSubString("s", "s", "s"));
		objList.add(new ObjectSubString("q", "q", "q"));
		objList.add(new ObjectSubString("t", "t", "t"));
		
		actionList = new ArrayList<DomainAction>();
		ActionAddSubString addAction = new ActionAddSubString("add", "add");
		addAction.setSubString("b");
		actionList.add(addAction);
		addAction = new ActionAddSubString("add", "add");
		addAction.setSubString("a");
		actionList.add(addAction);
		ActionRemoveSubString delAction = new ActionRemoveSubString("del", "del");
		delAction.setSubString("a");
		actionList.add(delAction);
	}
	
	@Test
	public void testConvertStringToObject() {
		ArrayList<DomainObject> result = objUnderTest.convertStringToObject(objListString);
		assertTrue(compareDomainObjectList(objList, result));
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
	public void testConvertObjectToString() {
		String result = objUnderTest.convertObjectToString(objList);
		assertEquals(objListString, result);
	}
	
	@Test
	public void testConvertStringToAction() {
		ArrayList<DomainAction> result = objUnderTest.convertStringToAction(actionListString);
		assertTrue(compareDomainActionList(actionList, result));
	}
	
	private boolean compareDomainActionList(ArrayList<DomainAction> listA, ArrayList<DomainAction> listB) {
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
	public void testConvertActionToString() {
		String result = objUnderTest.convertActionToString(actionList);
		assertEquals(actionListString, result);
	}
	
}
