package com.raah.webcrawler.main;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLCreator {
	private String _internalLinkElement = "InternalLinks";
	private String _externalLinkElement = "ExternalLinks";
	private String _imageElement = "Images";
	
	/**
	 * Method to form a new XML document
	 * @return - returns a newly created XML document
	 * @throws ParserConfigurationException
	 */
	public Document createMainXMLDocument() throws ParserConfigurationException {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			return doc;			
	} //end of method createMainXMLDocument

	/**
	 * Method to add links and image element to the document
	 * @param doc - requires a new document to append child to
	 * @param pageName - requires the page name to create a root element 
	 * @param internalLinks - requires a list of links to add to the XML document (internal links)
	 * @param externalLink - requires a list of links to add to the XML document (external links)
	 * @param images - requires a list of images to add to the XML document 
	 * @return - returns the XML documents with the added child elements
	 */
	public Element addXMLElements(Document doc,
						  String pageName, 
						  Map<String, String> internalLinks, 
						  Map<String, String> externalLink, 
						  List<String> images) 
	{
		Element rootElement = null;
		try {						
			//Create root element
			rootElement = doc.createElement(removeIllegalCharacters(pageName));
			//doc.appendChild(rootElement);
			
			//Create link elements
			if(internalLinks.size() > 0) {				
				createLinkElements(_internalLinkElement, internalLinks, rootElement, doc);				
			} 
			
			//Create external link elements
			if(externalLink.size() >0 ) {
				createLinkElements(_externalLinkElement, externalLink, rootElement, doc);
			}
							
			//Create image elements 
			if(images.size() > 0) {
				createImageElements(_imageElement, images, rootElement, doc);
			}					
			return rootElement;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return rootElement;
	} //end of method createXML
	
	
	/**
	 * Method to add the link URL to the XML document 
	 * @param elementName - Name should appear at the root level
	 * @param links - List of links to include in the document
	 * @param rootElement - Name of the parent element
	 * @param doc - returns the XML documents with the added child elements
	 */
	private void createLinkElements(String elementName, Map<String, String> links, Element rootElement, Document doc) {
		Element linkElement = doc.createElement(elementName);
		for(Entry<String, String> entry : links.entrySet()) {
			Element urlName = doc.createElement("URLName");
			if(entry.getKey().isEmpty()) {
				urlName.appendChild(doc.createTextNode("NameNotFound"));
			} else {
				urlName.appendChild(doc.createTextNode(entry.getKey()));
			}			
			linkElement.appendChild(urlName);			
			Element urlLink = doc.createElement("URLLink");
			urlLink.appendChild(doc.createTextNode(entry.getValue()));
			linkElement.appendChild(urlLink);					
		}
		rootElement.appendChild(linkElement);
	} //end of method createLinkElements
	
	
	/**
	 * Method to add image elements to a given XML document
	 * @param imageElementName - requires a name to formulate the element name
	 * @param imageList - requires a list of images to add as elements
	 * @param rootElement - requires the root element where to add a newly created image element
	 * @param doc - returns the XML documents with the added child elements
	 */
	private void createImageElements(String imageElementName, List<String> imageList, Element rootElement, Document doc) {
		Element imageElement = doc.createElement(imageElementName);					
		for(String listItem : imageList) {
			Element imageSrcElement = doc.createElement("SRC");
			imageSrcElement.appendChild(doc.createTextNode(listItem));
			imageElement.appendChild(imageSrcElement);			
		}
		rootElement.appendChild(imageElement);		
	} //end of method createImageElements 
	
	/**
	 * Method to remove any illegal characters such as empty spaces to 
	 * @param targetString - requires a string to strip out the illegal characters 
	 * @return - returns a string after stripping out the illegal character
	 */
	public String removeIllegalCharacters(String targetString) {
		return targetString.replaceAll("[^A-Za-z0-9()\\[\\]]", "");
	} //end of method removeIllegalCharacters
	
	/**
	 * Method to write a XML document
	 * @param doc - requires the document to write
	 * @throws TransformerException
	 */
	public void writeDocument(Document doc) throws TransformerException {
		//Write the content into XML file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		//StreamResult result = new StreamResult(new File("C:\\sitemap.xml"));
		//transformer.transform(source, result);		
		StreamResult consoleResult = new StreamResult(System.out);
        transformer.transform(source, consoleResult);        
	} //end of method writeDocument	

} //end of class
