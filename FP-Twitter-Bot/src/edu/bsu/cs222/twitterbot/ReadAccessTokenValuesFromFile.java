package edu.bsu.cs222.twitterbot;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadAccessTokenValuesFromFile {
	
	private JSONObject tokenValueJSONObject;
	private JSONParser parser = new JSONParser();
	private String filePath;
	
	public ReadAccessTokenValuesFromFile(String filePath) {
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
		this.tokenValueJSONObject = (JSONObject) fileObject;
	}
	
	public String getTokenString(){
		String tokenString = (String) this.tokenValueJSONObject.get("tokenString");
		return tokenString;
	}
	
	public String getTokenSecret(){
		String tokenSecret = (String) this.tokenValueJSONObject.get("tokenSecret");
		return tokenSecret;
	}
}
