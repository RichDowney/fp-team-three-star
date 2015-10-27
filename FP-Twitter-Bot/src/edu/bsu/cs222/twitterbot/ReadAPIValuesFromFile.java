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
	private String filePath;
	
	public ReadAPIValuesFromFile(String filePath) {
		this.filePath = filePath;
	}
	
	public void tryTtoReadFromFile() {
		try {
			readFromFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readFromFile() throws FileNotFoundException, IOException, ParseException {
		Object fileObject = parser.parse(new FileReader(this.filePath));
		this.apiValueJSONObject = (JSONObject) fileObject;
	}
	
	public String getAPIKey(){
		String apiKey = parseOutAPIKey();
		return apiKey;
	}
	
	private String parseOutAPIKey() {
		String apiKey = (String) this.apiValueJSONObject.get("apiKey");
		if (apiKey.equalsIgnoreCase("")) {
			apiKey = "No Saved API Key";
		}
		return apiKey;
	}
	
	public String getAPISecret(){
		String apiSecret = parseOutAPISecret();
		return apiSecret;
	}
	
	private String parseOutAPISecret() {
		String apiSecret = (String) this.apiValueJSONObject.get("apiSecret");
		if (apiSecret.equalsIgnoreCase("")) {
			apiSecret = "No Saved API Secret";
		}
		return apiSecret;
	}

}
