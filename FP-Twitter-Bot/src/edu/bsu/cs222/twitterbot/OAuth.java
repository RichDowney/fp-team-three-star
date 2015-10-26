package edu.bsu.cs222.twitterbot;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

public class OAuth {
	
	private String apiKey;
	private String apiSecret;
	private Token requestToken;
	private Token accessToken;
	private Verifier verifier;
	private String authorizationUrl;
	
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
