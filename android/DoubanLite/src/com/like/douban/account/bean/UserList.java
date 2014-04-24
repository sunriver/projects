package com.like.douban.account.bean;

import java.io.Serializable;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;


public class UserList implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String TAG = UserList.class.getSimpleName();

	public interface Property {
		public static final String COUNT = "count";
		public static final String START = "start";
		public static final String TOTAL = "total";
		public static final String USERS = "users";
	}

	public int count;
	public int start;
	public int total;
	public User[] users;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public User[] getUsers() {
		return users;
	}
	
	public void setUsers(User[] users) {
		this.users = users;
	}

	
	public UserList() {
		this.count = 0;
		this.start = 0;
		this.total = 0;
		this.users = new User[0];
	}

	public static UserList fromJSONObject(JSONObject obj) {
		UserList userList = new UserList();
		try {
			if (obj.has(Property.COUNT)) {
				userList.count = obj.getInt(Property.COUNT);
			}
			if (obj.has(Property.START)) {
				userList.start = obj.getInt(Property.START);
			}
			if (obj.has(Property.TOTAL)) {
				userList.total = obj.getInt(Property.TOTAL);
			}
			if (obj.has(Property.USERS)) {
				JSONArray jsonArray = obj.getJSONArray(Property.USERS);
				int len = jsonArray.length(); 
				userList.users = new User[len];
				for (int i = 0; i < len; i++) {
					JSONObject userJsObj = jsonArray.getJSONObject(i);
					userList.users[i] = User.fromJSONObject(userJsObj);
				}
			}
		} catch (Exception any) {
			Log.w(TAG, "Fail to parse json object", any);
		}
		return userList;
	}

}
