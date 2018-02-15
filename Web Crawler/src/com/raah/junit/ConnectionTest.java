package com.raah.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import com.raah.objects.PageCrawler;

public class ConnectionTest {
	private PageCrawler crawler = new PageCrawler();
	private String url = "http://wiprodigital.com";
	
	@Test
	public void test() {
		testConnection();
		verifyURLTest();
	}
	
	public void testConnection() {
		boolean result = crawler.connectToURL(url);
		assertEquals("Connection was successful", true, result);
	} //end of method testConnecton
	
	public void verifyURLTest() {
		int response = crawler.validateURL(url);
		assertEquals("This URL is valid", 200, response);
	}
}
