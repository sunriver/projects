package com.oobe.bar;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.oobe.R;




public class HotkeyModel {
	public enum CommHotkey		{SHIFT, CTRL, OPT, CMD, ALT, ESC, TAB, DEL, MORE, KEYBOARD};
	
	private Map<Integer, Integer> mHotkeyCodeMap;
	private Map<Integer, Boolean> mHotkeyStatus;
	
	public HotkeyModel(int serverType) {
		init();
	}
	
	private void init() {
		mHotkeyCodeMap = new HashMap<Integer, Integer>();
		mHotkeyStatus = new HashMap<Integer, Boolean>();
		
		mHotkeyCodeMap.put(R.id.key_shift, 59);
		mHotkeyStatus.put(R.id.key_shift, false);


		mHotkeyCodeMap.put(R.id.key_ctrl, 113);
		mHotkeyStatus.put(R.id.key_ctrl, false);


		mHotkeyCodeMap.put(R.id.key_cmd, 171);
		mHotkeyStatus.put(R.id.key_cmd, false);

		mHotkeyCodeMap.put(R.id.key_alt, 57);
		mHotkeyStatus.put(R.id.key_alt, false);

		mHotkeyCodeMap.put(R.id.key_esc, 111);
		mHotkeyStatus.put(R.id.key_esc, false);
		
		mHotkeyCodeMap.put(R.id.key_tab, 61);
		mHotkeyStatus.put(R.id.key_tab, false);
				
		mHotkeyCodeMap.put(R.id.key_del, 111);
		mHotkeyStatus.put(R.id.key_del, false);

		mHotkeyCodeMap.put(R.id.key_common_more, 112);
		mHotkeyStatus.put(R.id.key_common_more, false);

		mHotkeyStatus.put(R.id.hotkeybar_common, true);
		mHotkeyStatus.put(R.id.hotkeybar_combo, false);
	}
	

	public boolean getKeyStatus(int keyId) {
		return mHotkeyStatus.get(keyId);
	}
	
	public void setKeyStatus(int keyId , boolean status) {
		mHotkeyStatus.put(keyId, status);
	}
	
	public Iterator<Entry<Integer, Boolean>> getStatusIterator() {
		return mHotkeyStatus.entrySet().iterator();
	}
	
	
	
}
