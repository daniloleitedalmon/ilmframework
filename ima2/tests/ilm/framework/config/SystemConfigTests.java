package ilm.framework.config;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;
import org.junit.Before;
import org.junit.Test;

public class SystemConfigTests {

	private SystemConfig objUnderTest;
	
	@Before
	public void setUp() throws Exception {
		Map<String, String> parameterList = new HashMap<String, String>();
		Properties customProperties = new Properties();
		customProperties.loadFromXML(new FileInputStream("tests/ilm/framework/config/test.properties"));
		
		objUnderTest = new SystemConfig(false, parameterList, customProperties);
	}


	@Test
	public void testDefaultConstructor() {
		try {
			Map<String, String> parameterList = new HashMap<String, String>();
			parameterList.put("test", "success");
			objUnderTest = new SystemConfig(true, parameterList);
			
			assertEquals("pt-br", objUnderTest.getLanguage().toString());
			assertEquals("0", objUnderTest.getValue("numberOfAssignments"));
			assertEquals("success", objUnderTest.getValue("test"));
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConstructorWithParameterProperties() {
		try {
			Map<String, String> parameterList = new HashMap<String, String>();
			parameterList.put("config", "tests/ilm/framework/config/test2.properties");
			objUnderTest = new SystemConfig(true, parameterList);
			
			assertEquals("fr-fr", objUnderTest.getLanguage().toString());
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSetParameter() {
		assertEquals(null, objUnderTest.getValue("test"));
		objUnderTest.setParameter("test", "success");
		assertEquals("success", objUnderTest.getValue("test"));
	}

	@Test
	public void testSetLanguage() {
		assertEquals("en-us", objUnderTest.getLanguage().toString());
		objUnderTest.setLanguage("pt-BR");
		assertEquals("pt-br", objUnderTest.getLanguage().toString());
	}

	@Test
	public void testGetValue() {
		assertEquals("10", objUnderTest.getValue("numberOfAssignments"));
	}

	@Test
	public void testGetLanguage() {
		assertEquals("en-us", objUnderTest.getLanguage().toString());
	}

	@Test
	public void testIsApplet() {
		assertEquals(false, objUnderTest.isApplet());
	}

}
