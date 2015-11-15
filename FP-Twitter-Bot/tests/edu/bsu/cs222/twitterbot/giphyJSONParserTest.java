package edu.bsu.cs222.twitterbot;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

public class giphyJSONParserTest {

	
private JSONObject giphyJSONObject;
private JSONParser jsonParser = new JSONParser();
	
	@Before
	public void readFileAsJSONObject() throws IOException, ParseException {
		InputStream giphyFileInputStream = Thread.currentThread()
				.getContextClassLoader().getResourceAsStream("giphy.json");
		InputStreamReader inputReader = new InputStreamReader(giphyFileInputStream);
		JSONObject jsonObject = (JSONObject) jsonParser.parse(inputReader);
		this.giphyJSONObject = jsonObject;
	}
	
	@Test
	public void parseOutURL() {
		JSONObject dataObject = (JSONObject) giphyJSONObject.get("data");
		String url =  (String) dataObject.get("url");
		assertTrue(url.equals("http://giphy.com/gifs/cat-gif-silence-of-the-lambs-jean-havoc-RJh4i2uSQdHYQ"));
	}
	
}
