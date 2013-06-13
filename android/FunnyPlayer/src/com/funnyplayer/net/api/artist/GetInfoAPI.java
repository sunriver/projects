package com.funnyplayer.net.api.artist;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;



import android.util.Log;

import com.funnyplayer.cache.ImageUtils.ImageSize;
import com.funnyplayer.net.api.LastFmAPI;
import com.funnyplayer.net.api.Result;

public class GetInfoAPI extends LastFmAPI <String> {

	public GetInfoAPI() {
		super("artist.getInfo", HttpMethod.POST);
	}

	
	protected void onHandleResponse(Result result) {
		Element doc = result.getResultDocument().getDocumentElement();
		NodeList nodeList = doc.getElementsByTagName("image");
		for (int i = 0, len = nodeList.getLength(); i < len; i++) {
			Element e = (Element) nodeList.item(i);
			if (e.hasAttribute("size")) {
				ImageSize size = ImageSize.valueOf(e.getAttribute("size").toUpperCase());
				if (size == ImageSize.LARGE) {
					mResult = e.getTextContent();
					break;
				}
			}
		}
	}

	@Override
	protected HttpRequestBase onCreateHttpRequestInited() {
		HttpPost request = new HttpPost(toURL());
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (Entry<String, String> entry : mParamMap.entrySet()) {
			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		try {
			request.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, e.getMessage());
		}
		return request;
	}

}
