package com.raah.webcrawler.main;

import java.util.List;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.raah.objects.PageCrawler;
import com.raah.objects.WebPage;

public class WebCrawler {	
	public static XMLCreator xmlDocCreator;
	public static PageCrawler crawler;
	public static Document xmlDocument;
	
	public static void main(String[] args) throws MalformedURLException {
		crawler = new PageCrawler();
		xmlDocCreator = new XMLCreator();
		//Try creating the document
		try {
			xmlDocument = xmlDocCreator.createMainXMLDocument();
		} catch (ParserConfigurationException e1) {		
			System.out.println("Failed to create the XML Document");
			e1.printStackTrace();		
		}		
		
		try {
			Element rootElement = xmlDocument.createElement("WiproDigitalWebSite");
			String url = "http://wiprodigital.com/";
			WebPage firstPage = crawler.loadPageDocumentFromURL(url);
			addPageElements(firstPage);
			
			firstPage.get_externalLinks().forEach( (k,v) -> {
				WebPage aPage = crawler.loadPageDocumentFromURL(v);
				rootElement.appendChild(addPageElements(aPage));
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Try writing the document
		try {
			xmlDocCreator.writeDocument(xmlDocument);
		} catch (Exception e) {
			System.out.println("Failed to write the document");
			e.printStackTrace();
		}
		
								
	} //end of method main
	
	public static String userInput() {
		System.out.println("Enter a web site address (for example: http://wiprodigital.com/");
		Scanner sc = new 
				Scanner(System.in);
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
	
	public static Element addPageElements(WebPage page) {
		Element childELement = null;
		try {
			//Element rootElement = xmlDocument.createElement(xmlDocCreator.removeIllegalCharacters(page.get_title()));
			childELement = xmlDocument.createElement(xmlDocCreator.removeIllegalCharacters(page.get_title()));
			childELement.appendChild(xmlDocCreator.addXMLElements(xmlDocument, page.get_title(),
										page.get_internalLinks(),
										page.get_externalLinks(),
										page.get_imageLinks()));											
			//xmlDocument.appendChild(rootElement);	
			return childELement;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return childELement;
	}
		
} //end of class
