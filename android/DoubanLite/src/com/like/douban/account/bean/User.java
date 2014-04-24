package com.like.douban.account.bean;

import java.io.Serializable;
import org.json.JSONObject;
import android.util.Log;

public class User implements Serializable {
	private static final String TAG = User.class.getSimpleName();
	private static final long serialVersionUID = 1L;

	private interface Property {
		public static final String NAME = "name";
		public static final String ID = "id";
		public static final String AVATAR = "avatar";
		public static final String UID = "uid";

	}

	public String id;
	public String uid;
	public String name;
	public String avatar;

	public static User fromJSONObject(JSONObject obj) {
		User user = new User();
		try {
			if (obj.has(Property.NAME)) {
				user.name = obj.getString(Property.NAME);
			}
			if (obj.has(Property.ID)) {
				user.id = obj.getString(Property.ID);
			}
			if (obj.has(Property.UID)) {
				user.uid = obj.getString(Property.UID);
			}
			if (obj.has(Property.AVATAR)) {
				user.avatar = obj.getString(Property.AVATAR);
			}

		} catch (Exception any) {
			Log.w(TAG, "Fail to parse json object", any);
		}
		return user;
	}
}
