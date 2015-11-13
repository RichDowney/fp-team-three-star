package edu.bsu.cs222.twitterbot;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class GiphyConnection {

	public static void main(String[] args) {

	}

	private String giphySearchParam;
	private URLConnection giphyConnection;

	public GiphyConnection(String giphySearchParam) {
		this.giphySearchParam = giphySearchParam;
	}

	public URLConnection connectToGiphy() throws IOException {
		String encodedTitleParam = URLEncoder.encode(this.giphySearchParam, "UTF-8");
		URL url = new URL("http://api.giphy.com/v1/gifs/random?tag=" + encodedTitleParam + "&api_key=dc6zaTOxFJmzC&limit=1");
		giphyConnection = url.openConnection();
		giphyConnection.setRequestProperty("User-Agent",
				"CS222FinalProject/0.2 "
						+ "(http://github.com/bsu-cs222-fall2015-dll/fp-team-three-star giphy-api-logic; "
						+ "eclarocco@gmail.com)");
		giphyConnection.connect();
		return giphyConnection;
	}
}