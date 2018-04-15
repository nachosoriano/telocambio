/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfc.soriano.ws.test.util;

import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author NACHO
 */
public class ModelResponse {

	private int		code;
	private String	result;

	public ModelResponse() {

	}

	public ModelResponse(int c, String r) {
		this.code = c;
		this.result = r;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * Convert the result String (body response) to Document
	 *
	 * @return Document
	 * @throws javax.xml.parsers.ParserConfigurationException
	 * @throws org.xml.sax.SAXException
	 * @throws java.io.IOException
	 */
	public Document getResultXML() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		builder = factory.newDocumentBuilder();
		return builder.parse(new InputSource(new StringReader(this.getResult())));
	}

	public boolean isValidResponseCode() {
		return getCode() == HttpURLConnection.HTTP_OK || getCode() == HttpURLConnection.HTTP_NO_CONTENT;
	}
}
