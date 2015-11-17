package edu.bsu.cs222.twitterbot;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

public class OAuth {
	
	private String apiKey;
	private String apiSecret;
	private OAuthService service;
	private Token requestToken;
	private Token accessToken;
	private Verifier verifier;
	private String authorizationUrl;
	
	public OAuth(String apiKey, String apiSecret){
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
	}
	
	public void createOAuthService(){
		this.service = new ServiceBuilder()
				.provider(TwitterApi.class)
				.apiKey(this.apiKey)
				.apiSecret(this.apiSecret)
				.build();
	}
	
	
	public void createRequestToken(){
		this.requestToken = service.getRequestToken();
	}
	
	public void createAuthorizationUrl() {
		this.authorizationUrl = service.getAuthorizationUrl(requestToken);
	}
	
	public void createVerifier(String verifierCode){
		this.verifier = new Verifier(verifierCode);
	}
	
	public void createAccessToken(){
		this.accessToken = service.getAccessToken(requestToken, verifier);
	}
	
	public void createAccessTokenFromValues(String tokenString, String tokenSecret){
		this.accessToken = new Token(tokenString, tokenSecret);
	}
	
	public OAuthService getOAuthService(){
		return this.service;
	}
	
	public String getAuthorizationUrl(){
		return this.authorizationUrl;
	}
	
	public Token getAccessToken(){
		return this.accessToken;
	}
}
