package edu.bsu.cs222.twitterbot;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.oauth.OAuthService;

public class OAuth {
	
	private String apiKey;
	private String apiSecret;
	
	public OAuth(String apiKey, String apiSecret){
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
	}
	
	public OAuthService createOAuthService(){
		OAuthService service = new ServiceBuilder()
				.provider(TwitterApi.class)
				.apiKey(this.apiKey)
				.apiSecret(this.apiSecret)
				.build();
		return service;
	}
}
