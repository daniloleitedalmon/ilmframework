package ilm.framework.comm;

import static org.junit.Assert.*;

import ilm.framework.IlmProtocol;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class DesktopFileRWTests {

	private IlmDesktopFileRW objUnderTest;
	private String testMetadataString;
	private String testAssignmentString;
	
	@Before
	public void setUp() throws Exception {
		objUnderTest = new IlmDesktopFileRW();
		
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
				  	"<" + IlmProtocol.OBJECT_LIST_MODULE_NAME + ">" +
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
					"</" + IlmProtocol.OBJECT_LIST_MODULE_NAME + ">" +
					"<" + IlmProtocol.HISTORY_MODULE_NAME + "/>" +
					"<" + IlmProtocol.UNDO_REDO_MODULE_NAME + "/>" +
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
	public void testConvertInputStreamToString() {
		String text = "ssahdfjhdsgflsakdhfa";
		try {
			InputStream in = new ByteArrayInputStream(text.getBytes("UTF-8"));
			String s = objUnderTest.convertInputStreamToString(in);
			assertEquals(text, s);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testReadMetadataFile() {
		String metadata = "<metadata><title>Titulo</title></metadata>";
		String packageFile = "tests/ilm/framework/comm/test_metadata.zip";
		try {
			String result = objUnderTest.readMetadataFile(packageFile);
			assertEquals(result, metadata);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testReadAssignmentFiles() {
		ArrayList<String> assignmentListTest = new ArrayList<String>();
		assignmentListTest.add("<header>\r\n<title>Titulo<title>\r\n</header>\r\n<initial>\r\n<object>Point<object>\r\n</initial>\r\n<expected>\r\n<object>Point<object>\r\n</expected>\r\n<modules>\r\n</modules>\r\n<config>\r\n</config>\r\n<metadata>\r\n</metadata>");
		String packageFile = "tests/ilm/framework/comm/test_metadata.zip";
		ArrayList<String> assignmentFileList = new ArrayList<String>();
		assignmentFileList.add("exercicio_teste.geo");
		try {
			ArrayList<String> result = objUnderTest.readAssignmentFiles(packageFile, assignmentFileList);
			assertEquals(assignmentListTest.get(0), result.get(0));
		}
		catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testWriteAssignmentPackage() {
		ArrayList<String> list = new ArrayList<String>();
		list.add(testAssignmentString);
		list.add(testAssignmentString);
		ArrayList<String> nameList = new ArrayList<String>();
		nameList.add("exercicio_teste.geo");
		nameList.add("exercicio_teste2.geo");
		objUnderTest.writeAssignmentPackage("test.zip", testMetadataString, null, null, nameList, list);
	}
	
}
