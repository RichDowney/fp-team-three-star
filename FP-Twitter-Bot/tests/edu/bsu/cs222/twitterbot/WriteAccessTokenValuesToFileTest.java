package edu.bsu.cs222.twitterbot;

import static org.junit.Assert.assertTrue;

import org.json.simple.JSONObject;
import org.junit.Test;

public class WriteAccessTokenValuesToFileTest {
	
	@Test
	public void createJSONObjectTest() {
		String tokenString = "4000272419-Two6y2CzdG0jpFMeKLVY9MAg4iNVc46SHA1dJBE";
		String tokenSecret = "l4TSgcMON0OX5AdUagKxLh81hIAJxgt2fgAgtcTVZwarV";
		WriteAccessTokenValuesToFile tokenWriter = new WriteAccessTokenValuesToFile(tokenString, tokenSecret);
		JSONObject tokenJsonObject = tokenWriter.createJSONObject();
		assertTrue(tokenJsonObject.containsValue(tokenSecret));
	}
}
