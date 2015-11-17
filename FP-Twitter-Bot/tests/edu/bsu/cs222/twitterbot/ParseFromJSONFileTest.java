package edu.bsu.cs222.twitterbot;

import static org.junit.Assert.assertTrue;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class ParseFromJSONFileTest {
	
	private ParseFromJSONFile apiValueReader;
	private JSONObject apiFileObject;
	private ParseFromJSONFile tokenValueReader;
	private JSONObject tokenFileObject;
	
	@Before
	public void testSetUp() {
		apiValueReader = new ParseFromJSONFile("test-assets/test-api-values.txt");
		this.apiFileObject = this.apiValueReader.tryTtoReadFromFile();
		tokenValueReader = new ParseFromJSONFile("test-assets/token-values.txt");
		this.tokenFileObject = this.tokenValueReader.tryTtoReadFromFile();
	}
	
	@Test
	public void getAPIKeyTest() {
		String apiKey = this.apiValueReader.parseOutObjectValue("apiKey", this.apiFileObject);
		assertTrue(apiKey.equals("GdiOaLYzitCeCw0yrcqC1YXfP"));
	}
	
	@Test
	public void getAPISecretTest() {
		String apiSecret = this.apiValueReader.parseOutObjectValue("apiSecret", this.apiFileObject);
		assertTrue(apiSecret.equals("bwtcAcA4kGSkIphO1eqkwANLzahgZ2Z1INmKnfMc8kfgrCmhc6"));
	}
	
	@Test
	public void getTokenStringTest() {
		String tokenString = this.tokenValueReader.parseOutObjectValue("tokenString", this.tokenFileObject);
		assertTrue(tokenString.equals("4000272419-Two6y2CzdG0jpFMeKLVY9MAg4iNVc46SHA1dJBE"));
	}
	
	@Test
	public void getTokenSecretTest() {
		String tokenSecret = this.tokenValueReader.parseOutObjectValue("tokenSecret", this.tokenFileObject);
		assertTrue(tokenSecret.equals("l4TSgcMON0OX5AdUagKxLh81hIAJxgt2fgAgtcTVZwarV"));
	}

}
