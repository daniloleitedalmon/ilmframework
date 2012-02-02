package ilm.framework.gui;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class IlmFormTests {

	private IlmForm objUnderTest;
	private HashMap<String, String> map;
	private String title;
	
	@Before
	public void setUp() {
		title = "test";
		map = new HashMap<String, String>();
		map.put("color", "blue");
		map.put("size", "big");
		map.put("country", "Brasil");
	}
	
	@Test
	public void testConstructor() {
		objUnderTest = new IlmForm(map, title);
		objUnderTest.setVisible(true);
	}

	@Test
	public void testGetUpdatedMap() {
		objUnderTest = new IlmForm(map, title);
		map = new HashMap<String, String>();
		map.put("color", "red");
		map.put("size", "small");
		map.put("country", "Brasil");
		objUnderTest.setVisible(true);
		objUnderTest.changeFieldValues(map);
		assertEquals(map, objUnderTest.getUpdatedMap());
	}
	
}
