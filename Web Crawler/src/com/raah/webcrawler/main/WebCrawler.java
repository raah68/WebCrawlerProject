package com.raah.webcrawler.main;

import java.util.Map;
import java.util.Map.Entry;

import com.raah.objects.PageCrawler;
import com.raah.objects.WebPage;

public class WebCrawler {

	public static void main(String[] args) {
		PageCrawler crawler = new PageCrawler();
		String url = "http://wiprodigital.com/";
		WebPage firstPage = crawler.loadPageDocumentFromURL(url);
		
		System.out.println(firstPage.get_title());
		System.out.println(firstPage.get_url());
		System.out.println(firstPage.isVisitStatus());
		
		Map<String, String> pageLinks = firstPage.get_pageLinks();
		for(Entry<String, String> entry : pageLinks.entrySet()) {
			System.out.println(entry);		
		}
		
		Map<String, String> imageList = firstPage.get_imageLinks();
		System.out.println(imageList.entrySet().size());
		for(Entry<String, String> entry: imageList.entrySet()) {
			System.out.println(entry);
		}
		
	}

}
