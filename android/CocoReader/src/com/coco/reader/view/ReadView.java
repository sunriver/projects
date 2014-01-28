package com.coco.reader.view;


import com.codo.reader.data.Document;
import com.sunriver.common.utils.ApiUtil;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.EditText;

public class ReadView extends EditText {
	private final static String TAG = ReadView.class.getSimpleName();
	private Document mDocument;

	
	public ReadView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mDocument = new Document(context);
		init(context);
	}


	public ReadView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	private void init(final Context context) {
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
			if (mDocument != null) {
				return mDocument.nextPage();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			ReadView.this.setText(result);
		}
		
	}

}
