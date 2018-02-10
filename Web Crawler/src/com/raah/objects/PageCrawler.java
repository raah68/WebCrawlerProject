package com.raah.objects;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.raah.webcrawler.main.WebCrawler;

public class PageCrawler {
	private String _url;
	private String _title;
	private Document _document;
	private Map<String, String> _linkList = new HashMap<String, String>();
	private Map<String, String> _imageList  = new HashMap<String, String>();
	
	public WebPage loadPageDocumentFromURL(String url) {
		this._url = url;
		WebPage aPage = null;
		if(connectToURL()){
			_title = _document.title();
			collectAllLinksFromPage();
			collectAllImagesFromPage();
			aPage = new WebPage(_url, true,_title, _linkList, _imageList);
		}
		return aPage;
	}
	
	
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
	private void collectAllLinksFromPage(){
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
	private void collectAllImagesFromPage(){
		if(this._document != null) {
			Elements images = _document.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
			for(Element img : images) {
				_imageList.put(img.attr("alt"), img.attr("src"));
			}
		}
	} //end of method collectAllImagesFromPage
}
