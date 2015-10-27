package edu.bsu.cs222.twitterbot;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.json.simple.JSONObject;

public class APIValueFileWriter {

	private String apiKey;
	private String apiSecret;

	public APIValueFileWriter(String apiKey, String apiSecret) {
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
	}

	public void tryToWriteToJsonFile() {
		try {
			writeToJsonFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeToJsonFile() throws IOException {
		JSONObject apiJsonObject = createJSONObject();
		FileWriter file = new FileWriter("twitter-api-values/api-values.txt");
		file.write(apiJsonObject.toJSONString());
		file.flush();
		file.close();
	}

	public JSONObject createJSONObject() {
		JSONObject apiJsonObject = new JSONObject();
		asMap(apiJsonObject).put("apiKey", this.apiKey);
		asMap(apiJsonObject).put("apiSecret", this.apiSecret);
		return apiJsonObject;
	}

	@SuppressWarnings("unchecked")
	private final static Map<Object, Object> asMap(JSONObject jsonObject) {
		return jsonObject;
	}
	
}