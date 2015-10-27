package edu.bsu.cs222.twitterbot;

import org.junit.Test;
import org.scribe.oauth.OAuthService;
import static org.junit.Assert.*;

public class OAuthTest {
	
	private String apiKey = "GdiOaLYzitCeCw0yrcqC1YXfP";
	private String apiSecret = "bwtcAcA4kGSkIphO1eqkwANLzahgZ2Z1INmKnfMc8kfgrCmhc6";
	
	@Test
	public void createOAuthServiceTest(){
		OAuth oAuth = new OAuth(this.apiKey, this.apiSecret);
		oAuth.createOAuthService();
		OAuthService service = oAuth.getOAuthService();
		assertTrue(service != null);
	}
}
