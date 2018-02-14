package com.raah.webcrawler.main;

import java.util.List;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.raah.objects.PageCrawler;
import com.raah.objects.WebPage;

public class WebCrawler {	
	public static XMLCreator xmlDocCreator;
	public static PageCrawler crawler;
	public static String url = "http://wiprodigital.com";	
	public static DocumentBuilder xmlDocumentBuilder;
	public static Document doc;
	
	public static void main(String[] args) throws MalformedURLException {
		url = userInput();
		crawler = new PageCrawler();
		xmlDocCreator = new XMLCreator();
		//Try creating the document
		try {
			xmlDocumentBuilder = xmlDocCreator.createMainXMLDocument();
		} catch (ParserConfigurationException e1) {		
			System.out.println("Failed to create the XML Document");
			e1.printStackTrace();		
		}		
		
		try {
			WebPage firstPage = crawler.loadPageDocumentFromURL(url);
			if(firstPage != null) {
				List<String> urlListToVisit = getValidInternalLinks(firstPage.get_internalLinks());											
				doc = xmlDocumentBuilder.newDocument();
				Element rootElement = doc.createElement("DocumentRootElement");
				doc.appendChild(rootElement);
			
				/*Create a new child node for the first page*/
				Element firstPageElement = xmlDocCreator.addXMLElements(doc, url,
																		firstPage.get_internalLinks(),
																		firstPage.get_externalLinks(),
																		firstPage.get_imageLinks());
				rootElement.appendChild(firstPageElement);
			
				/*Go through all the internal links, fetch all URLS and static content and create XML element and then
			 	append the elements to the root element of the document*/
				if(urlListToVisit.size() > 0) {
					for(String alink : urlListToVisit) {
						WebPage page = crawler.loadPageDocumentFromURL(alink);
						Element childElement = xmlDocCreator.addXMLElements(doc, alink,
																			page.get_internalLinks(),
																			page.get_externalLinks(),
																			page.get_imageLinks());
						rootElement.appendChild(childElement);					
					}				
				}
			}
		} catch (IllegalArgumentException i) {
			System.out.println("The URL format is not correct");
			i.getMessage();
		} catch (Exception e) {
			e.printStackTrace();			
		}
		
		//Try writing the document
		try {
			xmlDocCreator.writeDocument(doc);			
		} catch (Exception e) {
			System.out.println("Failed to write the document");
			e.printStackTrace();
		}								
	} //end of method main
	
	/**
	 * Method to get Input from user
	 * @return returns a Web site URL as a string
	 */
	public static String userInput() {
		System.out.println("Enter a web site address (for example: http://wiprodigital.com");
		Scanner sc = new 
				Scanner(System.in);
		String inputString = sc.nextLine();
		sc.close();		
		if(inputString.length() > 0) {	
			try {
				URL url = new URL(inputString);
				return url.toString(); 
			} catch (MalformedURLException e) {
				System.out.println("Not a valild URL");		
			} 
		}										
		return null;
	} //end of method userInput
	
	/**
	 * Method to create list of URLs that the crawler will crawl through
	 * @param internalLinkMap - requires a the URL hashMap 
	 * @return returns an ArrayList<String>() that contains a list of URLs
	 */
	public static List<String> getValidInternalLinks(Map<String, String> internalLinkMap){
		List<String> urlListToVisit = new ArrayList<String>();			
		internalLinkMap.forEach((k, v)-> {
			try {
				int urlStatus = crawler.validateURL(v);
				if(urlStatus == 200) {
					urlListToVisit.add(v);
				}
			} catch (Exception e) {					
				
			}
		});
		return urlListToVisit;		
	} //end of method getValidInternalLinks
		
} //end of class
