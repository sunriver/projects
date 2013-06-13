package com.funnyplayer.net.api.album;


import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;



import com.funnyplayer.cache.ImageUtils.ImageSize;
import com.funnyplayer.net.api.LastFmAPI;
import com.funnyplayer.net.api.Result;

public class GetInfoAPI extends LastFmAPI <String> {

	public GetInfoAPI() {
		super("album.getInfo", HttpMethod.POST);
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
		return new HttpPost(toURL());
	}

}
