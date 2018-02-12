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

	public static void main(String[] args) {
		PageCrawler crawler = new PageCrawler();
		//String url = "http://wiprodigital.com/";
		//WebPage firstPage = crawler.loadPageDocumentFromURL(url);
		//System.out.println(userInput());
		//System.out.println(isURL("www.google.com"));
		
		String a = "go.com/";
		String http = "http://";
		if(!a.contains("://")) {
			http = http.concat(a);
			try {
				URL url = new URL(http);
				url.toURI();
				System.out.println(http);
			} catch (MalformedURLException e) {
				System.out.println("not a valid URL");				
			}
		}
		
		
		
		
		
		//System.out.println(firstPage.get_title());
		//System.out.println(firstPage.get_url());
		//System.out.println(firstPage.isVisitStatus());
		
/*		Map<String, String> pageLinks = firstPage.get_pageLinks();
		for(Entry<String, String> entry : pageLinks.entrySet()) {
			System.out.println(entry.getValue());		
		}*/
		
		/*Map<String, String> imageList = firstPage.get_imageLinks();
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
	
	public static URL userInput() {
		System.out.println("Enter a web site address in the format of http://www. !");
		Scanner sc = new Scanner(System.in);
		String inputString = sc.nextLine();
		URL url = null;
		if(inputString.length() > 0) {
				
			
			if(isURL(inputString)) {
				try {
					url = new URL(inputString);
				} catch (MalformedURLException e) {
					System.out.println("Not a valild URL");
					
				}
			} 
		}										
		return url;
	} //end of method userInput
	
	public static boolean isURL(String url) {
		if (url == null) {
	      return false;
	    }
	    String urlPattern = "^http(s{0,1})://[a-zA-Z0-9_/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9_/\\&\\?\\=\\-\\.\\~\\%]*";
	    return url.matches(urlPattern);
	}

} //end of class
