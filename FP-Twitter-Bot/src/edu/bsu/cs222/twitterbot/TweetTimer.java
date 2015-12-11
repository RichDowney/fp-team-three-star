package edu.bsu.cs222.twitterbot;

import java.io.IOException;
import java.net.URLConnection;
import java.util.TimerTask;

import org.json.simple.parser.ParseException;

public class TweetTimer extends TimerTask {
	
	private OAuth oAuth;
	private String giphySearchTerm;
	
	public TweetTimer(OAuth oAuth, String giphySearchTerm) {
		this.oAuth = oAuth;
		this.giphySearchTerm = giphySearchTerm;
	}

	@Override
	public void run() {
		try {
			String gifURL = generateGif();
			TweetPoster tweetPoster = new TweetPoster(oAuth, gifURL);
			tweetPoster.tryToPostTweet();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private String generateGif() throws ParseException, IOException {
		GiphyConnection giphyConnection = new  GiphyConnection(giphySearchTerm);
		URLConnection  connection = giphyConnection.connectToGiphy();
		GiphyJSONParser giphyParser = new GiphyJSONParser(connection);
		String gifURL = giphyParser.parseOutURL();
		return gifURL;
	}

}
