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
	public DocumentBuilder createMainXMLDocument() throws ParserConfigurationException {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
			//Document doc = docBuilder.newDocument();
			return docBuilder;			
	} //end of method createMainXMLDocument
	
	/**
	 * Method to add links and image element to the document
	 * @param doc - requires a new document to append child to
	 * @param pageURL - requires the page URL to create a root element 
	 * @param internalLinks - requires a list of links to add to the XML document (internal links)
	 * @param externalLink - requires a list of links to add to the XML document (external links)
	 * @param images - requires a list of images to add to the XML document 
	 * @return - returns the XML documents with the added child elements
	 */
	public Element addXMLElements(Document doc,
						  String pageURL, 
						  Map<String, String> internalLinks, 
						  Map<String, String> externalLink, 
						  List<String> images) 
	{
		Element subChildElement = null;
		try {						
			//Create root element
			String elementName = getNameFromURL(pageURL);					
			subChildElement = doc.createElement(removeIllegalCharacters(elementName));			
			//doc.appendChild(rootElement);
			
			//Create link elements
			if(internalLinks.size() > 0) {				
				createLinkElements(_internalLinkElement, internalLinks, subChildElement, doc);				
			} 
			
			//Create external link elements
			if(externalLink.size() >0 ) {
				createLinkElements(_externalLinkElement, externalLink, subChildElement, doc);
			}
							
			//Create image elements 
			if(images.size() > 0) {
				createImageElements(_imageElement, images, subChildElement, doc);
			}
			
			return subChildElement;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return null;
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
		//return targetString.replaceAll("[^A-Za-z0-9()\\[\\]]", "");
		return targetString.replaceAll("\\W+","");
	} //end of method removeIllegalCharacters
		
	/**
	 * Method to obtain the last segment of a URL
	 * @param url requires a URL to split in to segments
	 * @return returns the last segment of a split URL
	 */
	public String getNameFromURL(String url) {
		String segement[] = url.split("/");
		return segement[segement.length-1];
	} //end of method getNameFromURL
	
	/**
	 * Method to write the content in an XML file
	 * @param doc - requires the document to write
	 * @throws TransformerException
	 */
	public void writeDocument(Document doc) throws TransformerException {		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);	
		//File fileSource = new File("C:\\Windows\\Temp\\output.xml");
		//fileSource.setWritable(true);
		//StreamResult result = new StreamResult(fileSource);
		//transformer.transform(source, result);
		//System.out.println("File writting has been completed, please check location: "+fileSource.getAbsolutePath()+" for results");
		StreamResult consoleResult = new StreamResult(System.out);
        transformer.transform(source, consoleResult);        
	} //end of method writeDocument	

} //end of class
