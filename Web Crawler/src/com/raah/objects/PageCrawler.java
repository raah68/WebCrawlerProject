package com.raah.objects;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PageCrawler {
	private String _url;
	private String _domainName;
	private boolean _vistiStatus;
	private Document _document;
	private Map<String, String> _linkList = new HashMap<String, String>();
	private List<String> _imageList  = new ArrayList<String>();
	private Map<String, String> _externalLinks = new HashMap<String, String>();
	private Map<String, String> _internalLinks = new HashMap<String, String>();
		
	/**
	 * Public method to load a web page using URL 
	 * @param url requires a web site URL to
	 * @return WebPage returns a parsed web page
	 */
	public WebPage loadPageDocumentFromURL(String url) {
		this._url = url;
		this._domainName = getDomainNameFromURL(this._url);
		this._vistiStatus = true;				
		WebPage aPage = null;		
		if(connectToURL()){
			collectAllLinks();
			separeteInternaLinksFromExternalLinks();			
			collectAllImages();			
			aPage = new WebPage(_url, 
								_vistiStatus,
								_document.title(), 
								_linkList, 
								_imageList,
								_internalLinks, 
								_externalLinks);
		}
		return aPage;
	} //end of method loadPageDocumentFromURL
		
	/**
	 * Method to connect to an URL and return the parsed HTML as document
	 * @param url  requires an URL to connect
	 * @return returns a parsed HTML web page
	 */
	public boolean connectToURL() {	
		try {
			_document = Jsoup.connect(_url).get();			
		} catch (Exception e) {
			System.out.println("The URL is not Valid");;			
		}
		
		if(_document != null) {
			return true;
		} else {
			return false;
		}
	} //end of method loadDocumentFromWeb
		
	/**
	 * Method to collect all URLs on the page
	 * @return returns a map of URLs contained on the page
	 */
	public void collectAllLinks(){
		if(_document != null) {
			Elements links = _document.select("a[href]");
			Elements importedLinks = _document.select("link[href]");			
			//Enhanced loop as we are only reading the data			
			for(Element link : links) {
				if(link.text().length() > 0 ) {
					_linkList.put(link.text(), link.absUrl("href"));
				} else {
					_linkList.put(link.attr("title").trim(), link.absUrl("href"));
				}								
			}
			for(Element importedLink : importedLinks) {
				_linkList.put(importedLink.attr("rel"), importedLink.absUrl("href"));				
			}
		}
	} //end of method collectAllLinksFromPage
	
	/**
	 * Method to collect all images on the page
	 * @return return a map of images on the page
	 */
	public void collectAllImages(){
		if(_document != null) {
			Elements images = _document.getElementsByTag("img");
			for(Element img : images) {
				_imageList.add(img.attr("abs:src"));				
			}
		}
	} //end of method collectAllImagesFromPage
		
	/**
	 * Method to separate internal links from external links. 
	 */
	public void separeteInternaLinksFromExternalLinks() {
		for(Entry<String, String> entry : _linkList.entrySet()) {			
			if(entry.getValue().startsWith("http") && !entry.getValue().equalsIgnoreCase(_url)) {
				String entryDomainName = getDomainNameFromURL(entry.getValue());				
				if(entryDomainName.equalsIgnoreCase(_domainName)) {
					_internalLinks.put(entry.getKey(), entry.getValue());
				} else {
					_externalLinks.put(entry.getKey(), entry.getValue());
				}				
			}
		}		
	} //end of method separeteInternaLinksFromExternalLinks

	/**
	 * Method to get domain name from a given string
	 * @param url requires the URL as String
	 * @return returns the domain name from the URL
	 */
	public String getDomainNameFromURL(String url) {				
		try {
			URL netUrl = new URL(url);
			return netUrl.getHost();
		} catch (MalformedURLException e) {			
			return null;
		}
	} //end of method getDomainNameFromURL
	
	/**
	 * Method to validate URL by checking its response code
	 * @param url requires a URL as a string
	 * @return returns a the response code received for the URL
	 */
	public int validateURL(String url) {
		//Document webPageDocument;
		int status;
		Connection.Response resp;
		try {
			resp = Jsoup.connect(url).execute();
			status = resp.statusCode();
		} catch (Exception e) {
			status = 0;						
		}		
		return status;		
	} //end of method validateURL

} //end of class
