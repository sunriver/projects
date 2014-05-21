package com.funnyplayer.net.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {
	public static JSONObject parse(InputStream in) throws IOException, JSONException {
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		for (String s = reader.readLine(); s != null; s = reader.readLine()) {
			builder.append(s);
		}
		
		return new JSONObject(builder.toString());
	}

}
