package ilm.framework.comm;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

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
		try {
			String result = objUnderTest.readMetadataFile("tests/ilm/framework/comm/test_metadata.zip");
			assertEquals(result, metadata);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
