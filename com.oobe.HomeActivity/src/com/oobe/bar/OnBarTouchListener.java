package com.oobe.bar;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnBarTouchListener implements OnTouchListener {
	
	public abstract class ComboKeyListener {
		public abstract void onClick(View v);
	}
	
	class WinComoboKeyListner extends ComboKeyListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	}

	class MacComoboKeyListner extends ComboKeyListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
