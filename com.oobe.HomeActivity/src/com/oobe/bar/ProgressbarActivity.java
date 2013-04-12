package com.oobe.bar;

import com.oobe.HomeActivity;
import com.oobe.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;

public class ProgressbarActivity extends Activity {

	private ProgressBar mProgressBar;
	private ProgressBar mProgressBar1;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.progressbar);
		mProgressBar = (ProgressBar) this.findViewById(R.id.progressBar);
		mProgressBar1 = (ProgressBar) this.findViewById(R.id.progressBar1);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item != null && item.getItemId() == android.R.id.home) {
			Intent intent = new Intent(this, HomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
		return super.onContextItemSelected(item);
	}

	private void init() {
		new Thread(new ProgressRunnable()).start();
	}
	
	class ProgressRunnable implements Runnable {
		@Override
		public void run() {
			int position = 0;
			int max = mProgressBar.getMax();
			while (true) {
				position = (position + 1) % max;
				mProgressBar.setProgress(position);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
