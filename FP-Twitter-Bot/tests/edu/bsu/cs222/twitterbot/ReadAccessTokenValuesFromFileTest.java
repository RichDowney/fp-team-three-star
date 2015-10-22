package edu.bsu.cs222.twitterbot;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ReadAccessTokenValuesFromFileTest {
	
	private ReadAccessTokenValuesFromFile tokenValueReader = new ReadAccessTokenValuesFromFile("test-assets/token-values.txt");
	
	@Before
	public void testSetUp() {
		this.tokenValueReader.tryTtoReadFromFile();
	}
	
	@Test
	public void getTokenStringTest() {
		String tokenString = this.tokenValueReader.getTokenString();
		assertTrue(tokenString.equals("4000272419-Two6y2CzdG0jpFMeKLVY9MAg4iNVc46SHA1dJBE"));
	}
	
	@Test
	public void getTokenSecretTest() {
		String tokenSecret = this.tokenValueReader.getTokenSecret();
		assertTrue(tokenSecret.equals("l4TSgcMON0OX5AdUagKxLh81hIAJxgt2fgAgtcTVZwarV"));
	}

}
