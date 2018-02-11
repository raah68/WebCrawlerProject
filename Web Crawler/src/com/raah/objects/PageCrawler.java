package com.raah.objects;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.raah.webcrawler.main.WebCrawler;

public class PageCrawler {
	private String _url;
	private final String _domainName;
	private boolean _vistiStatus;
	private Document _document;
	private Map<String, String> _linkList = new HashMap<String, String>();
	private Map<String, String> _imageList  = new HashMap<String, String>();
	Map<String, String> _externalLinks = new HashMap<String, String>();
	Map<String, String> _internalLinks = new HashMap<String, String>();
	
	public PageCrawler() {
		this._vistiStatus = true;
		this._domainName = getDomainNameFromURL(this._url);
	} //end of constructor
	
	
	/**
	 * Public method to load a web page using URL 
	 * @param url requires a web site URL to
	 * @return WebPage returns a parsed web page
	 */
	public WebPage loadPageDocumentFromURL(String url) {
		this._url = url;
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
								_externalLinks, 
								_internalLinks);
		}
		return aPage;
	} //end of method loadPageDocumentFromURL
	
	
	/**
	 * Method to connect to an URL and return the parsed HTML as document
	 * @param url  requires an URL to connect
	 * @return returns a parsed HTML web page
	 */
	private boolean connectToURL() {	
		try {
			this._document = Jsoup.connect(_url).get();			
		} catch (IOException ex) {
			Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		if(this._document != null) {
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
		if(this._document != null) {
			Elements links = _document.select("a[href]");
			//Enhanced loop as we are only reading the data
			for(Element link : links) {
				this._linkList.put(link.text(), link.attr("href"));
			}
		}
	} //end of method collectAllLinksFromPage

	
	/**
	 * Method to collect all images on the page
	 * @return return a map of images on the page
	 */
	private void collectAllImages(){
		if(this._document != null) {
			Elements images = _document.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
			for(Element img : images) {
				_imageList.put(img.attr("alt"), img.attr("src"));
			}
		}
	} //end of method collectAllImagesFromPage
	
	
	/**
	 * Method to separate internal links from external links. 
	 */
	private void separeteInternaLinksFromExternalLinks() {
		for(Entry<String, String> entry : this._linkList.entrySet()) {
			String entryDomainName = getDomainNameFromURL(entry.getValue());
			if(entryDomainName.equalsIgnoreCase(this._domainName)) {
				this._internalLinks.put(entry.getKey(), entry.getValue());
			} else {
				this._externalLinks.put(entry.getKey(), entry.getValue());
			}				
		}		
	} //end of method separeteInternaLinksFromExternalLinks

	/**
	 * Method to get domain name from a given string
	 * @param url requires the URL as String
	 * @return returns the domain name from the URL
	 */
	private static String getDomainNameFromURL(String url) {				
		try {
			URL netUrl = new URL(url);
			return netUrl.getHost();
		} catch (MalformedURLException e) {			
			e.printStackTrace();
		}
		return null;
	} //end of method getDomainNameFromURL

} //end of class
