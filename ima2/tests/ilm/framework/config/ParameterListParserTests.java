package ilm.framework.config;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class ParameterListParserTests {

	@Test
	public void DesktopParameterListParserTest() {
		String[] args = new String[4];
		
		args[0] = "-key1";
		args[1] = "value1";
		args[2] = "-key2";
		args[3] = "value2";
		
		IParameterListParser parser = new DesktopParameterListParser();
		Map<String, String> actual = parser.Parse(args);
		
		assertEquals("value1", actual.get("key1"));
		assertEquals("value2", actual.get("key2"));
	}
	
	@Test
	public void AppletParameterListParserTest() {
		String[] args = new String[2];
		
		args[0] = "key1=value1";
		args[1] = "key2=value2";
		
		IParameterListParser parser = new AppletParameterListParser();
		Map<String, String> actual = parser.Parse(args);
		
		assertEquals("value1", actual.get("key1"));
		assertEquals("value2", actual.get("key2"));
	}

}
