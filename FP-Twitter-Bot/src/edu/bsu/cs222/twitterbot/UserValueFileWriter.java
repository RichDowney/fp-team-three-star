package edu.bsu.cs222.twitterbot;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.json.simple.JSONObject;

public class UserValueFileWriter {
	
	private String userName;
	private String tokenString;
	private String tokenSecret;

	public UserValueFileWriter(String userName, String tokenString, String tokenSecret) {
		this.userName = userName;
		this.tokenString = tokenString;
		this.tokenSecret = tokenSecret;
	}

	public void tryToWriteToJsonFile(JSONObject fileObject) throws IOException {
		try {
			writeToJsonFile(fileObject);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeToJsonFile(JSONObject fileJsonObject) throws IOException {
		JSONObject newFileJsonObject = createJSONObject(fileJsonObject);
		FileWriter file = new FileWriter("twitter-values/users.json");
		file.write(newFileJsonObject.toJSONString());
		file.flush();
		file.close();
	}

	public JSONObject createJSONObject(JSONObject fileJsonObject) {
		JSONObject tokenJsonObject = new JSONObject();
		asMap(tokenJsonObject).put("tokenString", this.tokenString);
		asMap(tokenJsonObject).put("tokenSecret", this.tokenSecret);
		asMap(fileJsonObject).put(this.userName, tokenJsonObject);
		return fileJsonObject;
	}

	@SuppressWarnings("unchecked")
	private final static Map<Object, Object> asMap(JSONObject jsonObject) {
		return jsonObject;
	}
	
}
