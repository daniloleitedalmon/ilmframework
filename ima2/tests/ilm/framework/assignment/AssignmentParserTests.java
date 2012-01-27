package ilm.framework.assignment;

import static org.junit.Assert.*;

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
								"<object>Point1</object>" +
								"<object>Point2</object>" +
								"<object>Point3</object>" +
								"<object>Point4</object>" +
							  "</initial>" +
							  "<expected>" +
								"<object>Point5</object>" +
								"<object>Point6</object>" +
							  "</expected>" +
							  "<modules>" +
							  	"<objectlist>" +
								  "<object>Point1</object>" +
								  "<object>Point2</object>" +
								  "<object>Point3</object>" +
								  "<object>Point4</object>" +
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
		//no
	}
	
	@Test
	public void testSetAssignmentModulesData() {
		//no
	}
	
	@Test
	public void testGetCurrentState() {
		//no
	}
	
	@Test
	public void testGetExpectedAnswer() {
		//no
	}
	
	@Test
	public void testGetInitialState() {
		//no
	}
	
	@Test
	public void testGetProposition() {
		String expectedProposition = "Bla bla bla";
		String resultProposition = objUnderTest.getProposition(testAssignmentString);
		assertEquals(expectedProposition, resultProposition);
	}
	
	@Test
	public void testGetAssignmentFileList() {
		ArrayList<String> expectedList = new ArrayList<String>();
		expectedList.add("exercicio_teste.geo");
		expectedList.add("exercicio_teste2.geo");
	    ArrayList<String> resultList = objUnderTest.getAssignmentFileList(testMetadataString);
	    assertEquals(expectedList, resultList);
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
		String expectedString = testAssignmentString.substring(0, 554) + 
											  "<color>Red</color>" +
											  "<size>Big</size>" +
											"</metadata>" +
										  "</assignment>";
		ArrayList<String> assignmentList = new ArrayList<String>();
		assignmentList.add(testAssignmentString);
		ArrayList<String> resultString = objUnderTest.mergeMetadata(assignmentList, metadataMap);
		assertEquals(expectedString, resultString.get(0).substring(AUTO_GEN_HEADER_SIZE));
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
