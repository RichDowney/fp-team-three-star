package edu.bsu.cs222.twitterbot;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class ReadAPIValuesFromFileTest {
	
	private ReadAPIValuesFromFile apiValueReader = new ReadAPIValuesFromFile("test-assets/test-api-values.txt");
	
	@Before
	public void testSetUp() {
		this.apiValueReader.tryTtoReadFromFile();
	}
	
	@Test
	public void getAPIKeyTest() {
		String apiKey = this.apiValueReader.getAPIKey();
		assertTrue(apiKey.equals("GdiOaLYzitCeCw0yrcqC1YXfP"));
	}
	
	@Test
	public void getAPISecretTest() {
		String apiSecret = this.apiValueReader.getAPISecret();
		assertTrue(apiSecret.equals("bwtcAcA4kGSkIphO1eqkwANLzahgZ2Z1INmKnfMc8kfgrCmhc6"));
	}

}
