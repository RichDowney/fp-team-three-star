package edu.bsu.cs222.twitterbot;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ParseFromJSONFile {
	private JSONObject fileJSONObject;
	private JSONParser parser = new JSONParser();
	private String filePath;
	
	public ParseFromJSONFile(String filePath) {
		this.filePath = filePath;
	}
	
	public JSONObject tryTtoReadFromFile() {
		try {
			readFromFile();
		} catch (Exception e) {
			this.fileJSONObject = null;
		}
		return this.fileJSONObject;
	}

	public void readFromFile() throws FileNotFoundException, IOException, ParseException {
		Object fileObject = parser.parse(new FileReader(this.filePath));
		this.fileJSONObject = (JSONObject) fileObject;
	}
	
	public String parseOutObjectValue(String key, JSONObject jsonObject) {
		String parsedValue = (String) jsonObject.get(key);
		return parsedValue;
	}
	
	public JSONObject parseOutObject(String key, JSONObject jsonObject) {
		JSONObject parsedObject = (JSONObject) jsonObject.get(key);
		return parsedObject;
	}
	
}