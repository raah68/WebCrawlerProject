package com.raah.webcrawler.main;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.raah.objects.PageCrawler;
import com.raah.objects.WebPage;

public class WebCrawler {

	public static void main(String[] args) throws MalformedURLException {
		PageCrawler crawler = new PageCrawler();
		String url = "http://wiprodigital.com/";
		WebPage firstPage = crawler.loadPageDocumentFromURL(url);
		Map<String, String> internalLinks = firstPage.get_internalLinks();
		Map<String, String> externalLinks = firstPage.get_externalLinks();
		Map<String, String> allLinks = firstPage.get_pageLinks();
		
		Map<String, String> images = firstPage.get_imageLinks();
		String title = firstPage.get_title();
		
		for(Entry<String, String> entry : internalLinks.entrySet()) {
			System.out.println(entry.getValue());
		}
		
		//XMLCreator siteMap = new XMLCreator();
		//siteMap.createXML(title, internalLinks,externalLinks, images);
		
		//System.out.println(userInput());
		//System.out.println(isURL("www.google.com"));
		
		
		//System.out.println(firstPage.get_title());
		//System.out.println(firstPage.get_url());
		//System.out.println(firstPage.isVisitStatus());
		
//		Map<String, String> pageLinks = firstPage.get_pageLinks();
//		for(Entry<String, String> entry : pageLinks.entrySet()) {
//			System.out.println(entry.getValue());		
//		}
		

		
		
		/*Map<String, Strings> imageList = firstPage.get_imageLinks();
		 * 
		System.out.println(imageList.entrySet().size());
		for(Entry<String, String> entry: imageList.entrySet()) {
			System.out.println(entry);
		}*/
		
//		Map<String, String> internalLinks = firstPage.get_internalLinks();
//		System.out.println("List of internal links");
//		for(Entry<String, String> entry : internalLinks.entrySet()) {
//			System.out.println(entry);
//		}
		
//		Map<String, String> externalLinks = firstPage.get_externalLinks();
//		System.out.println("List of internal links");
//		System.out.println(externalLinks.size());
//		for(Entry<String, String> entry : externalLinks.entrySet()) {
//			System.out.println(entry.getValue());
//		}
		
				
	} //end of method main
	
	public static String userInput() {
		System.out.println("Enter a web site address (for example: http://wiprodigital.com/");
		Scanner sc = new Scanner(System.in);
		String inputString = sc.nextLine();
		sc.close();
		URL url = null;
		if(inputString.length() > 0) {	
			try {
				url = new URL(inputString);
			} catch (MalformedURLException e) {
				System.out.println("Not a valild URL");		
			} 
		}										
		return url.toString();
	} //end of method userInput

} //end of class
