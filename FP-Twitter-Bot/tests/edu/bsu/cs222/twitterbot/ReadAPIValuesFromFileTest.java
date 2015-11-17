package edu.bsu.cs222.twitterbot;

import static org.junit.Assert.assertTrue;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class ReadAPIValuesFromFileTest {
	
	private ParseFromJSONFile apiValueFileReader;
	private JSONObject apiFileObject;
	
	@Before
	public void testSetUp() {
		apiValueFileReader = new ParseFromJSONFile("test-assets/test-api-values.txt");
		this.apiFileObject = this.apiValueFileReader.tryTtoReadFromFile();
	}
	
	@Test
	public void getAPIKeyTest() {
		String apiKey = this.apiValueFileReader.parseOutObjectValue("apiKey", this.apiFileObject);
		assertTrue(apiKey.equals("GdiOaLYzitCeCw0yrcqC1YXfP"));
	}
	
	@Test
	public void getAPISecretTest() {
		String apiSecret = this.apiValueFileReader.parseOutObjectValue("apiSecret", this.apiFileObject);
		assertTrue(apiSecret.equals("bwtcAcA4kGSkIphO1eqkwANLzahgZ2Z1INmKnfMc8kfgrCmhc6"));
	}

}
