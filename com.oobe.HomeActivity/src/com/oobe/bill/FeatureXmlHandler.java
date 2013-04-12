package com.oobe.bill;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class FeatureXmlHandler extends DefaultHandler{
    //Feature attribute 
    public static final String BILLING_FEATIRE_CODE = "code";
    public static final String BILLING_FEATIRE_KIND = "kind";
    public static final String BILLING_FEATIRE_TIME_LEFT = "time_left";
    
	
	private List<FeatureBean> mFeatures;
	private FeatureBean feature;
	
	
	public List<FeatureBean> getFeatures() {
		return mFeatures;
	}
	
	
	@Override
	public void startDocument() throws SAXException {
		mFeatures = new ArrayList<FeatureBean>();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, 
			Attributes attributes) throws SAXException {
		if ("feature".equals(localName)) {
			feature = new FeatureBean();
			feature.setCode(attributes.getValue(BILLING_FEATIRE_CODE));
			feature.setKind(attributes.getValue(BILLING_FEATIRE_KIND));
			feature.setTimeLeft(attributes.getValue(BILLING_FEATIRE_TIME_LEFT));
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if("feature".equals(localName) && feature!=null){
			mFeatures.add(feature);
			feature = null;
		} 
	}
	
}
