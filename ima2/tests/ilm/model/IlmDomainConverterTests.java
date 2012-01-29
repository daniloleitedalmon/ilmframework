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
		ArrayList<DomainObject> resultList = objUnderTest.convertStringToObject(objListString);
		// TODO implement methods to compare domain objects
		// otherwise it will be impossible to use "assertEquals"
		printObjList(objList, "expected");
		printObjList(resultList, "result");
		assertEquals(objList, resultList);
	}
	
	private void printObjList(ArrayList<DomainObject> list, String listName) {
		System.out.println(listName + ": ");
		for(DomainObject o : list) {
			System.out.println(o.getName() + ": " + o.getDescription());
		}
	}
	
	@Test
	public void testConvertObjectToString() {
		String result = objUnderTest.convertObjectToString(objList);
		assertEquals(objListString, result);
	}
	
	@Test
	public void testConvertStringToAction() {
		ArrayList<DomainAction> resultList = objUnderTest.convertStringToAction(actionListString);
		// TODO implement methods to compare domain objects
		// otherwise it will be impossible to use "assertEquals"
		printActionList(actionList, "expected");
		printActionList(resultList, "result");
		assertEquals(actionList, resultList);
	}
	
	private void printActionList(ArrayList<DomainAction> list, String listName) {
		System.out.println(listName + ": ");
		for(DomainAction a : list) {
			System.out.println(a.getName() + ": " + a.getDescription());
		}
	}

	@Test
	public void testConvertActionToString() {
		String result = objUnderTest.convertActionToString(actionList);
		assertEquals(actionListString, result);
	}
	
}
