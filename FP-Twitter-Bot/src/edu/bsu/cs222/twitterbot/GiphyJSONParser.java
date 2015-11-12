package edu.bsu.cs222.twitterbot;

import org.json.simple.JSONObject;

public class GiphyJSONParser {
	
	private JSONObject jsonConnection;
	
	public GiphyJSONParser(JSONObject jsonConnection) {
		this.jsonConnection = jsonConnection;
	}
	
	public void giphyParser(){
		JSONObject connectionObject = (JSONObject) jsonConnection.get("data");
		String urlString =  (String) connectionObject.get("url");
		System.out.println(urlString);
	}
	
}
