package com.oobe.fragment;

import com.oobe.R;
import com.oobe.R.layout;
import com.oobe.fragment.LeftListFragment.OnArticleSelectedListener;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class FragmentActivity extends Activity implements OnArticleSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_layout);
		Log.v("Fragment", "FragmentActivity.onCreate()+");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.v("Fragment", "FragmentActivity.onStart()+");
	}


	@Override
	public void onArticleSelected(Uri articleUri) {
		Toast.makeText(this, "Article selected!", 1000);
	}
	
}
