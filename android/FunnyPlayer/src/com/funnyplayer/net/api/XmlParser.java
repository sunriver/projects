package com.funnyplayer.net.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.funnyplayer.net.api.Result.Status;

public class XmlParser {
	
	public static Result createResultFromInputStream(InputStream inputStream) throws SAXException, IOException {
		Document document = newDocumentBuilder().parse(new InputSource(new InputStreamReader(inputStream, "UTF-8")));
		Element root = document.getDocumentElement(); 
		String statusString = root.getAttribute("status");
		Status status = "ok".equals(statusString) ? Status.OK : Status.FAILED;
		if (status == Status.FAILED) {
			Element errorElement = (Element) root.getElementsByTagName("error").item(0);
			int errorCode = Integer.parseInt(errorElement.getAttribute("code"));
			String message = errorElement.getTextContent();
			return Result.createRestErrorResult(errorCode, message);
		} else {
			return Result.createOkResult(document);
		}
	}

	private static DocumentBuilder newDocumentBuilder() {
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			return builderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
}
