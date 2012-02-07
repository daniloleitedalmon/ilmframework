package ilm.framework.assignment;

import static org.junit.Assert.*;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.domain.DomainConverter;
import ilm.framework.modules.AssignmentModule;
import ilm.framework.modules.IlmModule;
import ilm.framework.modules.assignment.HistoryModule;
import ilm.framework.modules.assignment.ObjectListModule;
import ilm.framework.modules.assignment.UndoRedoModule;
import ilm.model.IlmDomainConverter;
import ilm.model.ObjectSubString;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class AssignmentParserTests {

	private AssignmentParser objUnderTest;
	private String testMetadataString;
	private String assignStr;
	
	@Before
	public void setUp() {
		objUnderTest = new AssignmentParser();
		testMetadataString = 
							  "<package>" + 
							    "<files>" +
							      "<assignment>exercicio_teste.geo</assignment>" +
							      "<assignment>exercicio_teste2.geo</assignment>" +
							    "</files>" +
							    "<config>" +
							      "<color>Blue</color>" +
							      "<speed>Fast</speed>" +
							    "</config>" +
							    "<metadata>" +
							      "<title>Titulo</title>" +
							      "<country>Brasil</country>" +
							      "<date>25/01/2012</date>" +
							    "</metadata>" +
							  "</package>";
		assignStr =
							"<assignment>" +
								"<proposition>Bla bla bla</proposition>" +
							  "<initial>" +
									"<objects>" +
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
									"</objects>" +					
							  "</initial>" +
							  "<current/>" +
							  "<expected/>" +
							  "<config>" +
						  		"<disablebutton2>Button2</disablebutton2>" +
						  		"<disablebutton1>Button1</disablebutton1>" +
						  	  "</config>" +
							  "<metadata>" +
							  	"<author>Danilo</author>" +
							  "</metadata>" +
							  "<modules>" +
								"<history/>" +
								"<undo_redo/>" +
							  	"<object_list>" +
								  	"<objects>" +
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
									"</objects>" +
								"</object_list>" +
							  "</modules>" +
							"</assignment>";
	}
	
	// from string to assignment
	@Test
	public void testConvertStringToAssignment() {
		assertTrue(true);
	}
	
	@Test
	public void testGetProposition() {
		String expected = "Bla bla bla";
		String result = objUnderTest.getProposition(assignStr);
		assertEquals(expected, result);
	}
	
	@Test
	public void testGetState() {
		AssignmentState expected = new AssignmentState();
		ArrayList<DomainObject> objList = new ArrayList<DomainObject>();
		objList.add(new ObjectSubString("a", "a", "a"));
		objList.add(new ObjectSubString("s", "s", "s"));
		objList.add(new ObjectSubString("q", "q", "q"));
		objList.add(new ObjectSubString("t", "t", "t"));
		expected.setList(objList);
		
		DomainConverter converter = new IlmDomainConverter();
		AssignmentState result = objUnderTest.getState(converter, assignStr, IlmProtocol.ASSIGNMENT_INITIAL_NODE);
		assertTrue(compareDomainObjectList(expected.getList(), result.getList()));
	}
	
	@Test
	public void testConvertStringToMap() {
		HashMap<String, String> expectedMap = new HashMap<String, String>();
		expectedMap.put("color", "Blue");
		expectedMap.put("speed", "Fast");
	    HashMap<String, String> resultMap = objUnderTest.convertStringToMap(testMetadataString, IlmProtocol.CONFIG_LIST_NODE);
	    assertEquals(expectedMap, resultMap);
	
	    expectedMap = new HashMap<String, String>();
	    expectedMap.put("title", "Titulo");
	    expectedMap.put("country", "Brasil");
	    expectedMap.put("date", "25/01/2012");
	    resultMap = objUnderTest.convertStringToMap(testMetadataString, IlmProtocol.METADATA_LIST_NODE);
	    assertEquals(expectedMap, resultMap);
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
	
	//from assignment to string
	@Test
	public void testConvertAssignmentToString() {
		IlmDomainConverter converter = new IlmDomainConverter();
		Assignment assignment = objUnderTest.convertStringToAssignment(converter, assignStr);
		String result = objUnderTest.convertAssignmentToString(converter, assignment);
		//it must remove the string data referring to the modules
		assertEquals(assignStr.substring(0, assignStr.lastIndexOf("<modules>")), 
				     result.substring(0, result.lastIndexOf("</assignment>")));
	}
		
	@Test
	public void testConvertMapToString() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("disablebutton2", "Button2");
		map.put("disablebutton1", "Button1");
  		String expected = "<disablebutton2>Button2</disablebutton2><disablebutton1>Button1</disablebutton1>";
  		String result = objUnderTest.convertMapToString(map);
  		assertEquals(expected, result);
  		
  		map = new HashMap<String, String>();
  		map.put("title", "Titulo");
	    map.put("country", "Brasil");
	    map.put("date", "25/01/2012");
	    expected = "<title>Titulo</title><date>25/01/2012</date><country>Brasil</country>";
	    result = objUnderTest.convertMapToString(map);
	    assertEquals(expected, result);
	}
	
	//from metadata file to assignment (vice-versa)
	@Test
	public void testGetAssignmentFileList() {
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("exercicio_teste.geo");
		expected.add("exercicio_teste2.geo");
	    ArrayList<String> result = objUnderTest.getAssignmentFileList(testMetadataString);
	    assertEquals(expected, result);
	}
	
	@Test
	public void testMergeMetadata() {
		HashMap<String, String> metadataMap = new HashMap<String, String>();
		metadataMap.put("color", "Red");
		metadataMap.put("size", "Big");
		String expected = assignStr.substring(0, assignStr.lastIndexOf("</metadata>")) + 
											  "<color>Red</color>" +
											  "<size>Big</size>" +
											assignStr.substring(assignStr.lastIndexOf("</metadata>"));
		ArrayList<String> assignmentList = new ArrayList<String>();
		assignmentList.add(assignStr);
		ArrayList<String> result = objUnderTest.mergeMetadata(assignmentList, metadataMap);
		assertEquals(expected, result.get(0));
	}
	
	@Test
	public void testGetMetadataFileContent() {
		// TODO method yet to be written
	}
	
	//from modules to string (vice-versa)
	@Test
	public void testSetAssignmentModulesData() {
		DomainConverter converter = new IlmDomainConverter();
		HashMap<String, IlmModule> ilmModuleList = new HashMap<String, IlmModule>();
		IlmModule module = new UndoRedoModule();
		ilmModuleList.put(module.getName(), module);
		module = new HistoryModule();
		ilmModuleList.put(module.getName(), module);
		module = new ObjectListModule();
		ilmModuleList.put(module.getName(), module);
		objUnderTest.setAssignmentModulesData(converter, assignStr, ilmModuleList, 0);
		
		ArrayList<DomainObject> expected = new ArrayList<DomainObject>();
		expected.add(new ObjectSubString("a", "a", "a"));
		expected.add(new ObjectSubString("s", "s", "s"));
		expected.add(new ObjectSubString("q", "q", "q"));
		expected.add(new ObjectSubString("t", "t", "t"));
		
		ArrayList<DomainObject> result = ((ObjectListModule)ilmModuleList.get(IlmProtocol.OBJECT_LIST_MODULE_NAME)).getObjectList();
		assertTrue(compareDomainObjectList(expected, result));
	}
	
	@Test
	public void testGetAssignmentModuleData() {
		DomainConverter converter = new IlmDomainConverter();
		HashMap<String, IlmModule> ilmModuleList = new HashMap<String, IlmModule>();
		AssignmentModule module = new UndoRedoModule();
		module.addAssignment();
		module.setAssignmentIndex(0);
		ilmModuleList.put(module.getName(), module);
		module = new HistoryModule();
		module.addAssignment();
		module.setAssignmentIndex(0);
		ilmModuleList.put(module.getName(), module);
		module = new ObjectListModule();
		module.setContentFromString(converter, 0, assignStr.substring(assignStr.lastIndexOf("<object_list>") 
																	+ "<object_list>".length(), 
																   assignStr.lastIndexOf("</object_list>")));
		module.setAssignmentIndex(0);
		module.print();
		ilmModuleList.put(module.getName(), module);
		/*
		String result = objUnderTest.getAssignmentModulesData(converter, ilmModuleList);
		assertEquals(assignStr.substring(assignStr.lastIndexOf("<modules>"), 
					 assignStr.lastIndexOf("</modules>") + "</modules>".length()),
					 result);*/
	}
	
}
