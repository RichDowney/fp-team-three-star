package edu.bsu.cs222.twitterbot;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Test;

public class APIValueFileWriterTest {
	
	@Test
	public void createJSONObjectTest() {
		String apiKey = "GdiOaLYzitCeCw0yrcqC1YXfP";
		String apiSecret = "bwtcAcA4kGSkIphO1eqkwANLzahgZ2Z1INmKnfMc8kfgrCmhc6";
		APIValueFileWriter apiWriter = new APIValueFileWriter(apiKey, apiSecret);
		JSONObject apiJsonObject = apiWriter.createJSONObject();
		assertTrue(apiJsonObject.containsValue(apiSecret));
	}

}
