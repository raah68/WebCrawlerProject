package com.raah.webcrawler.main;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.raah.objects.PageCrawler;
import com.raah.objects.WebPage;

public class WebCrawler {

	public static void main(String[] args) {
		PageCrawler crawler = new PageCrawler();
		String url = "http://wiprodigital.com/";
		WebPage firstPage = crawler.loadPageDocumentFromURL(url);
		
		//System.out.println(firstPage.get_title());
		//System.out.println(firstPage.get_url());
		//System.out.println(firstPage.isVisitStatus());
		
//		Map<String, String> pageLinks = firstPage.get_pageLinks();
//		for(Entry<String, String> entry : pageLinks.entrySet()) {
//			System.out.println(entry);		
//		}
		
		/*Map<String, String> imageList = firstPage.get_imageLinks();
		System.out.println(imageList.entrySet().size());
		for(Entry<String, String> entry: imageList.entrySet()) {
			System.out.println(entry);
		}*/
		
/*		Map<String, String> internalLinks = firstPage.get_internalLinks();
		System.out.println("List of internal links");
		for(Entry<String, String> entry : internalLinks.entrySet()) {
			System.out.println(entry);
		}*/
		
/*		Map<String, String> externalLinks = firstPage.get_externalLinks();
		System.out.println("List of internal links");
		for(Entry<String, String> entry : externalLinks.entrySet()) {
			System.out.println(entry);
		}*/
		
		/*int beginIndex = url.indexOf("://")+3;
		int endIndex = url.indexOf(".com");
		System.out.println(url.substring(beginIndex, endIndex));*/
		//getDomainNameFromURL();
		System.out.println(getDomainName(url));
		
	}
	
	public static void getDomainNameFromURL() {
		String url = "http://google.co.uk/";
		try {
			URL netUrl = new URL(url);
			System.out.println(netUrl.getHost());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 

	public static String getDomainName(String url){
		String hostExtractorRegexString = "(?:https?://)?(?:www\\.)?(.+\\.)(com|au\\.uk|co\\.in|be|in|uk|org\\.in|org|net|edu|gov|mil)";
		Pattern hostExtractorRegexPattern = Pattern.compile(hostExtractorRegexString);
		if (url == null) return null;
	    url = url.trim();
	    Matcher m = hostExtractorRegexPattern.matcher(url);
	    if(m.find() && m.groupCount() == 2) {
	        return m.group(1) + m.group(2);
	    }
	    else {
	        return null;
	    }
	}

}
