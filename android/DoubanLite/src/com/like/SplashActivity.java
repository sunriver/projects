package com.like;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class SplashActivity extends ActionBarActivity implements AnimationListener {
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context appCtx = this.getApplicationContext();
        ViewGroup splashView = (ViewGroup) View.inflate(appCtx, R.layout.splash, null);
        setContentView(splashView);
        Animation animation = AnimationUtils.loadAnimation(appCtx, R.anim.slide_from_bottom);
        animation.setAnimationListener(this);
        
        ImageView splashIv = (ImageView) splashView.findViewById(R.id.iv_splash);
        splashIv.startAnimation(animation);
    }


	@Override
	public void onAnimationStart(Animation animation) {

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		Intent intent = new Intent(this, MainActivity.class);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ignore) {
		}
		startActivity(intent);
		finish();
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
	}


}
