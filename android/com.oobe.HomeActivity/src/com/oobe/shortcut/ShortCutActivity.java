package com.oobe.shortcut;

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class ShortCutActivity extends Activity {
	
	ImageButton mCreateShortCutBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}
	
	private void init() {
		mCreateShortCutBtn = new ImageButton(this);
		
		this.setContentView(mCreateShortCutBtn);
		mCreateShortCutBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				createShortCut();
			}});
	}
	
	private void createShortCut(){
		Intent intent = new Intent();
		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "myshort");
		Parcelable icon = ShortcutIconResource.fromContext(this, R.drawable.btn_default);
		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, icon);
		//快捷方式激活的activity，需要执行的intent，自己定义
		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent());
		//OK，生成
		setResult(RESULT_OK, intent);
	}

}
