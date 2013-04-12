package com.oobe.bill;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;


import android.util.Log;

public class FeatureXMLParser {

	private static final boolean DEBUG = true;
	private static final String TAG		= "Bill";

	private static SAXParserFactory	m_sax_factory	= null;
	private static SAXParser		m_sax_parser	= null;
	private static XMLReader		m_xml_reader	= null;
	private static FeatureXmlHandler m_xml_handler	= null;

	public FeatureXMLParser() {
		try {
			m_sax_factory	= SAXParserFactory.newInstance();
			m_sax_parser	= m_sax_factory.newSAXParser();
			m_xml_reader	= m_sax_parser.getXMLReader();
			
			// Create handler to handle XML Tags (extends DefaultHandler)
			m_xml_handler	= new FeatureXmlHandler();
//			m_xml_handler.init();
			m_xml_reader.setContentHandler(m_xml_handler);
		} catch (Exception e) {
			Log.e(TAG, "BackendXMLParser construct Excpetion=" + e.toString());
		}
	
	} /* end FeatureXMLParser	*/
	
	public List<FeatureBean> parseData(InputStream in) {
		try {
			InputSource input = new InputSource(in);
			m_xml_reader.parse(input);
			return m_xml_handler.getFeatures();
		} catch (Exception e) {
			Log.e(TAG, "XML Pasing Excpetion=" + e.toString());
		}
		return null;
	}
}
