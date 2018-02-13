package com.raah.objects;

import java.util.List;
import java.util.Map;

public class WebPage {
	private String _url;
	private String _title;
	private boolean visitStatus;
	private Map<String, String> _pageLinks;
	private List<String> _imageLinks;
	private Map<String, String> _internalLinks;
	private Map<String, String> _externalLinks;
	
	public WebPage(String _url, 
				   boolean visitStatus, 
				   String _title, 
				   Map<String, String> pageLinks, 
				   List<String> imageLinks, 
				   Map<String, String> internalLinks, 
				   Map<String, String> externalLinks) {
		this._url = _url;
		this._title = _title;
		this.visitStatus = visitStatus;
		this._pageLinks = pageLinks;
		this._imageLinks = imageLinks;
		this._internalLinks = internalLinks;
		this._externalLinks = externalLinks;
	} //end of constructor

	public String get_url() {
		return _url;
	}

	public String get_title() {
		return _title;
	}

	public boolean isVisitStatus() {
		return visitStatus;
	}

	public Map<String, String> get_pageLinks() {
		return _pageLinks;
	}
	
	public List<String> get_imageLinks() {
		return _imageLinks;
	}

	public Map<String, String> get_internalLinks() {
		return _internalLinks;
	}

	public Map<String, String> get_externalLinks() {
		return _externalLinks;
	}
	
	
	
	
	
	
	
	
	
	
	


	
	
	
	

}
