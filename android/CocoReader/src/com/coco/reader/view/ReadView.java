package com.coco.reader.view;


import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.EditText;

public class ReadView extends EditText {

	private String mReadPath;
	public ReadView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		postContent(context);
	}


	public ReadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		postContent(context);
	}
	
	private void postContent(Context context) {
		Handler handler = new Handler();
		handler.post(new Runnable() {

			@Override
			public void run() {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < 1000; i++) {
					sb.append("content" +i + "\n");
				}
				ReadView.this.setText(sb.toString());
			}
			
		});
	}
	


}
