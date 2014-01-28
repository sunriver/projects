package com.coco.reader.view;


import com.aphidmobile.flip.FlipViewController;
import com.codo.reader.data.Document;
import com.sunriver.common.utils.ApiUtil;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.widget.EditText;

public class ReadView extends EditText {
	private final static String TAG = ReadView.class.getSimpleName();
	
	public ReadView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}


	public ReadView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	private void init(final Context context) {
//		this.setMovementMethod(ScrollingMovementMethod.getInstance());
//		this.setSelection(getText().length(), getText().length());
//		this.addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before,
//					int count) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void afterTextChanged(Editable s) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});
		
		Handler handler = new Handler();
		handler.post(new Runnable() {
			@Override
			public void run() {
				GetPageTask task = new GetPageTask();
				if (ApiUtil.hasHoneycomb()) {
					task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				} else {
					task.execute();
				}
			}
		});
	}
	
	private class GetPageTask extends AsyncTask<Void, Integer, String> {

		@Override
		protected String doInBackground(Void... params) {
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			ReadView.this.setText(result);
		}
		
	}

}
