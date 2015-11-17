package edu.bsu.cs222.twitterbot;

import static org.junit.Assert.assertTrue;

import org.json.simple.JSONObject;
import org.junit.Test;

public class WriteAccessTokenValuesToFileTest extends ParseFromJSONFileTest {
	
	@Test
	public void createJSONObjectTest() {
		String userName = "Example";
		String tokenString = "4000272419-Two6y2CzdG0jpFMeKLVY9MAg4iNVc46SHA1dJBE";
		String tokenSecret = "l4TSgcMON0OX5AdUagKxLh81hIAJxgt2fgAgtcTVZwarV";
		UserValueFileWriter userWriter = new UserValueFileWriter(userName, tokenString, tokenSecret);
		JSONObject tokenJsonObject = userWriter.createJSONObject(userFileObject);
		assertTrue(tokenJsonObject.containsKey(userName));
	}
}
