package ilm.framework.assignment;

import static org.junit.Assert.*;

import ilm.framework.IlmProtocol;
import ilm.framework.assignment.model.AssignmentState;
import ilm.framework.assignment.model.DomainObject;
import ilm.framework.domain.DomainConverter;
import ilm.model.IlmDomainConverter;
import ilm.model.ObjectSubString;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AssignmentParserTests {

	private AssignmentParser objUnderTest;
	private String testMetadataString;
	private String testAssignmentString;
	private static final int AUTO_GEN_HEADER_SIZE = 54;
	
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
		testAssignmentString =
							"<assignment>" +
							  "<header>" +
								"<title>Titulo</title>" +
								"<proposition>Bla bla bla</proposition>" +
							  "</header>" +
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
							  "<modules>" +
							  	"<objectlist>" +
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
								"</objectlist>" +
								"<history/>" +
								"<undoredo/>" +
							  "</modules>" +
							  "<config>" +
							  	"<disablebuttons>" +
							  		"<button>Button1</button>" +
							  		"<button>Button2</button>" +
							  	"</disablebuttons>" +
							  "</config>" +
							  "<metadata>" +
							  	"<author>Danilo</author>" +
							  "</metadata>" +
							"</assignment>";
	}
	
	@Test
	public void testConvertStringToAssignment() {
		//ok
	}
	
	@Test
	public void testSetAssignmentModulesData() {
		//ok
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
		AssignmentState result = objUnderTest.getState(converter, testAssignmentString, IlmProtocol.ASSIGNMENT_INITIAL_NODE);
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
	public void testGetProposition() {
		String expected = "Bla bla bla";
		String result = objUnderTest.getProposition(testAssignmentString);
		assertEquals(expected, result);
	}
	
	@Test
	public void testGetAssignmentFileList() {
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("exercicio_teste.geo");
		expected.add("exercicio_teste2.geo");
	    ArrayList<String> result = objUnderTest.getAssignmentFileList(testMetadataString);
	    assertEquals(expected, result);
	}
	
	@Test
	public void testGetConfig() {
		HashMap<String, String> expectedMap = new HashMap<String, String>();
		expectedMap.put("color", "Blue");
		expectedMap.put("speed", "Fast");
	    HashMap<String, String> resultMap = objUnderTest.getConfig(testMetadataString);
	    assertEquals(expectedMap, resultMap);
	}
	
	@Test
	public void testGetMetadata() {
	    HashMap<String, String> expectedMap = new HashMap<String, String>();
	    expectedMap.put("title", "Titulo");
	    expectedMap.put("country", "Brasil");
	    expectedMap.put("date", "25/01/2012");
	    HashMap<String, String> resultMap = objUnderTest.getMetadata(testMetadataString);
	    assertEquals(expectedMap, resultMap);
	}
	
	@Test
	public void testMergeMetadata() {
		HashMap<String, String> metadataMap = new HashMap<String, String>();
		metadataMap.put("color", "Red");
		metadataMap.put("size", "Big");
		String expected = testAssignmentString.substring(0, testAssignmentString.length() - 6 - 
															IlmProtocol.METADATA_LIST_NODE.length() -
															IlmProtocol.ASSIGNMENT_FILE_NODE.length()) + 
											  "<color>Red</color>" +
											  "<size>Big</size>" +
											"</metadata>" +
										  "</assignment>";
		ArrayList<String> assignmentList = new ArrayList<String>();
		assignmentList.add(testAssignmentString);
		ArrayList<String> result = objUnderTest.mergeMetadata(assignmentList, metadataMap);
		assertEquals(expected, result.get(0));
	}
	
	@Test
	public void testConvertDocToXMLString() {
		Document doc = AssignmentParser.convertXMLStringToDoc(testMetadataString);
		String resultString = AssignmentParser.convertDocToXMLString(doc);
		assertEquals(resultString.substring(AUTO_GEN_HEADER_SIZE), testMetadataString);
	}
	
	@Test
	public void testConvertXMLStringToDoc() {
		Document doc = AssignmentParser.convertXMLStringToDoc(testMetadataString);
		Node packageNode = doc.getChildNodes().item(0);
		NodeList list = packageNode.getChildNodes();
		String result = "<" + packageNode.getNodeName() + ">";
		for(int i = 0; i < list.getLength(); i++) {
			NodeList subList = list.item(i).getChildNodes();
			if(subList.getLength() > 1) {
				result += "<" + list.item(i).getNodeName() + ">";
				for(int j = 0; j < subList.getLength(); j++) {
					Node node = subList.item(j);
					result += "<" + node.getNodeName() + ">" + node.getTextContent() + "</" + node.getNodeName() + ">";
				}
				result += "</" + list.item(i).getNodeName() + ">";
			}
			else {
				result += "<" + list.item(i).getNodeName() + ">" + list.item(i).getTextContent() + "</" + list.item(i).getNodeName() + ">";
			}
		}
		result += "</" + packageNode.getNodeName() + ">";
		assertEquals(result, testMetadataString);
	}
	
}
