package edu.bsu.cs222.twitterbot;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class TweetPoster {
	private OAuth oAuth;
	private String tweetText;
	
	public TweetPoster(OAuth oAuth, String tweetText){
		this.oAuth = oAuth;
		this.tweetText = tweetText;
	}
	
	public void tryToPostTweet() {
		try {
			postTweet();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void postTweet() throws UnsupportedEncodingException{
		String urlTweet = setUpTwitterRequestURL();
		OAuthRequest tweetRequest = setUpTweetRequest(urlTweet);
		tweetRequest.send();
	}
	
	private String setUpTwitterRequestURL() throws UnsupportedEncodingException  {
		String encodedTweet = URLEncoder.encode(tweetText,"UTF-8");
		String urlTweet="https://api.twitter.com/1.1/statuses/update.json?status="+encodedTweet;
		return urlTweet;
	}
	
	private OAuthRequest setUpTweetRequest(String urlTweet){
		OAuthRequest tweetRequest = new OAuthRequest(Verb.POST, urlTweet);
		OAuthService service = oAuth.getOAuthService();
		Token accessToken = oAuth.getAccessToken();
		service.signRequest(accessToken, tweetRequest);
		return tweetRequest;
	}

}