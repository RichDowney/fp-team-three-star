package edu.bsu.cs222.twitterbot;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GiphyConnection {

	public static void main(String[] args) {

	}

	private String titleParam;
	private URLConnection connection;
	private JSONParser jsonParser = new JSONParser();

	public GiphyConnection(String titleParam) {
		this.titleParam = titleParam;
	}

	public URLConnection connectToWikipedia() throws IOException {
		String encodedTitleParam = URLEncoder.encode(this.titleParam, "UTF-8");
		URL url = new URL("http://api.giphy.com/v1/gifs/random?tag=" + encodedTitleParam + "&api_key=dc6zaTOxFJmzC&limit=1");
		connection = url.openConnection();
		connection.setRequestProperty("User-Agent",
				"CS222FinalProject/0.2 "
						+ "(http://github.com/bsu-cs222-fall2015-dll/fp-team-three-star giphy-api-logic; "
						+ "eclarocco@gmail.com)");
		connection.connect();
		return connection;
	}

	public JSONObject parseConnectionJSON() throws ParseException, IOException {
		InputStreamReader inputReader = new InputStreamReader(connection.getInputStream());
		JSONObject jsonObject = (JSONObject) jsonParser.parse(inputReader);
		System.out.print(jsonObject);
		return jsonObject;
	}
}