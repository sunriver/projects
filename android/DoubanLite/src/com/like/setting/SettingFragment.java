package com.like.setting;

import com.like.R;
import com.like.douban.login.LoginActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class SettingFragment extends Fragment implements OnClickListener {
	private ImageView mLoginIv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.fragment_setting, null, false);
		mLoginIv = (ImageView) contentView.findViewById(R.id.iv_login);
		mLoginIv.setOnClickListener(this);
		return contentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	private void doLogin() {
		Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_login:
			doLogin();
			break;
		default:
			break;
		}
	}

}
