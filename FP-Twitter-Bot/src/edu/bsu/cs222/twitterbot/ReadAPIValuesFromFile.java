package edu.bsu.cs222.twitterbot;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadAPIValuesFromFile {
	private JSONObject fileJSONObject;
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
		this.fileJSONObject = (JSONObject) fileObject;
	}
	
	private String parseOutObjectValue(String key) {
		String parsedValue = (String) this.fileJSONObject.get(key);
		return parsedValue;
	}
	
	private JSONObject parseOutObject(String key) {
		JSONObject parsedObject = (JSONObject) this.fileJSONObject.get(key);
		return parsedObject;
	}
	
}