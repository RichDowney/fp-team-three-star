package edu.bsu.cs222.twitterbot;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.json.simple.JSONObject;

public class WriteAccessTokenValuesToFile {
	
	private String tokenString;
	private String tokenSecret;

	public WriteAccessTokenValuesToFile(String tokenString, String tokenSecret) {
		this.tokenString = tokenString;
		this.tokenSecret = tokenSecret;
	}

	public void tryToWriteToJsonFile() throws IOException {
		try {
			writeToJsonFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeToJsonFile() throws IOException {
		JSONObject tokenJsonObject = createJSONObject();
		FileWriter file = new FileWriter("twitter-token-values/token-values.txt");
		file.write(tokenJsonObject.toJSONString());
		file.flush();
		file.close();
	}

	public JSONObject createJSONObject() {
		JSONObject tokenJsonObject = new JSONObject();
		asMap(tokenJsonObject).put("tokenString", this.tokenString);
		asMap(tokenJsonObject).put("tokenSecret", this.tokenSecret);
		return tokenJsonObject;
	}

	@SuppressWarnings("unchecked")
	private final static Map<Object, Object> asMap(JSONObject jsonObject) {
		return jsonObject;
	}
	
}
