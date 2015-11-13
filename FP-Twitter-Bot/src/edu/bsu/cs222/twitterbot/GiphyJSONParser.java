package edu.bsu.cs222.twitterbot;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GiphyJSONParser {
	
	private JSONParser jsonParser = new JSONParser();
	private URLConnection giphyConnection;
	
	public GiphyJSONParser(URLConnection giphyConnection) {
		this.giphyConnection = giphyConnection;
	}
	
	public String parseOutURL() throws ParseException, IOException{
		JSONObject connectionObject = parseConnectionJSON();
		JSONObject dataObject = (JSONObject) connectionObject.get("data");
		String url =  (String) dataObject.get("url");
		System.out.println(url);
		return url;
	}
	
	public JSONObject parseConnectionJSON() throws ParseException, IOException {
		InputStreamReader inputReader = new InputStreamReader(giphyConnection.getInputStream());
		JSONObject jsonObject = (JSONObject) jsonParser.parse(inputReader);
		return jsonObject;
	}
	
}
