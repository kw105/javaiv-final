/**
 * Demonstrate Java XML skills learned through the UCSD Java IV course
 * A Java console application that reads in the attached file JobResults_UCSDExt.xml
 * and uses XML parsing to generate the required output.
 * @author Kevin
 */

package com.adv.java.xml;

import java.io.File;
import java.io.IOException;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;


import javax.xml.parsers.*;
/*
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
 */

/*
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathEvaluationResult;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathNodes;
 */

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class Lesson4XML {

	/**
	 * Parse the XML data to display the desired output using SAX Parser
	 * @param filename Name of the file containing the XML data
	 */
	public static void displayResultsBySAXParser(String filename) {	

		var handler = new DefaultHandler() {
			boolean isSerial = false;
			String serialResult = "";

			boolean isVisibleString = false;
			String visibleStringResult = "";

			boolean isUnsigned = false;
			String unsignedResult = "";

			public void startElement(String namespaceURI, String lname, String qname, Attributes attrs) {
				if (lname.equals("serial")) {
					isSerial = true;
				}
				if (lname.equals("visible-string")) {
					isVisibleString = true;
				}
				if (lname.equals("unsigned")) {
					isUnsigned = true;
				}
			}

			public void endElement(String namespaceURI, String lname, String qname) {
				if (lname.equals("serial")) {
					isSerial = false;
				}
				if (lname.equals("visible-string")) {
					isVisibleString = false;
				}
				if (lname.equals("unsigned")) {
					isUnsigned = false;
				}
			}

			public void characters(char[] chars, int start, int len) throws SAXException {
				if (isSerial) {
					serialResult = new String(chars, start, len);
				}
				if (isVisibleString) {
					visibleStringResult = new String(chars, start, len);
				}
				if (isUnsigned) {
					unsignedResult = new String(chars, start, len);
				}
			}

			public void endDocument() throws SAXException {
				System.out.println("serial: " + serialResult);
				System.out.println("visible-string: " + visibleStringResult);
				System.out.println("unsigned: " + unsignedResult);
			}

		};

		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(true);
			factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			SAXParser saxParser = factory.newSAXParser();
			File f = new File(filename);
			saxParser.parse(f, handler);
		}
		catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		}
		catch (SAXException e) {
			System.out.println(e.getMessage());
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}


}
