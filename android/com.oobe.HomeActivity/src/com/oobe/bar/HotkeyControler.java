package com.oobe.bar;


import java.util.Observer;

import com.oobe.R;

import android.database.Observable;
import android.view.MotionEvent;


public class HotkeyControler extends Observable {
	
	
	public void onKeyPress(HotkeyModel model, MotionEvent event, int keyId) {

		updateKeyStatus(model, event, keyId);
		
		//Invalidate hot key View
		for (Object object : mObservers) {
			Observer observer = (Observer) object;
			observer.update(null, null);
		}
		
		/**
		 * TODO: back end task
		 */
	}
	
	private void updateKeyStatus(HotkeyModel model, MotionEvent event, int keyId) {
		switch (keyId) {
		case R.id.key_shift:
		case R.id.key_ctrl:
		case R.id.key_cmd:
		case R.id.key_alt:
			if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
				boolean status = model.getKeyStatus(keyId) ;
				model.setKeyStatus(keyId, !status);
			}
			break;
		case R.id.key_common_more:
			model.setKeyStatus(R.id.hotkeybar_combo, true);
			model.setKeyStatus(R.id.hotkeybar_common, false);
			break;
		case R.id.key_revert:
			model.setKeyStatus(R.id.hotkeybar_combo, false);
			model.setKeyStatus(R.id.hotkeybar_common, true);
			break;
		default:
			boolean status = model.getKeyStatus(keyId) ;
			model.setKeyStatus(keyId, !status);
			break;
		}
	}
	
}
