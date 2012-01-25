package ilm.framework.comm;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class DesktopFileRWTests {

	private IlmDesktopFileRW objUnderTest;
	
	@Before
	public void setUp() throws Exception {
		objUnderTest = new IlmDesktopFileRW();
	}
	
	@Test
	public void testConstructor() {
		
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
	
}
