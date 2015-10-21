package edu.bsu.cs222.twitterbot;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadAPIValuesFromFile {
	private JSONObject apiValueJSONObject;
	private JSONParser parser = new JSONParser();
	
	public void tryTtoReadFromFile() {
		try {
			readFromFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readFromFile() throws FileNotFoundException, IOException, ParseException {
		Object fileObject = parser.parse(new FileReader("twitter-api-values/api-values.txt"));
		this.apiValueJSONObject = (JSONObject) fileObject;
	}
	
	public String getAPIKey(){
		String apiKey = (String) this.apiValueJSONObject.get("apiKey");
		return apiKey;
	}
	
	public String getAPISecret(){
		String apiSecret = (String) this.apiValueJSONObject.get("apiSecret");
		return apiSecret;
	}

}
