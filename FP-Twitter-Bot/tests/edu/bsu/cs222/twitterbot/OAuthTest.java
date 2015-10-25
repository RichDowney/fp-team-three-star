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
		OAuthService service = oAuth.createOAuthService();
		String serviceString = service.toString();
		System.out.println(serviceString);
		assertTrue(serviceString.equals("org.scribe.oauth.OAuth10aServiceImpl@15327b79"));
	}
}
