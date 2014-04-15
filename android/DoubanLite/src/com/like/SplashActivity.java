package com.like;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.like.common.BitmapCache;
import com.like.douban.event.EventFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;

public class SplashActivity extends ActionBarActivity implements AnimationListener {
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context appCtx = this.getApplicationContext();
        View view = View.inflate(appCtx, R.layout.splash, null);
        setContentView(view);
        Animation animation = AnimationUtils.loadAnimation(appCtx, R.anim.alpha);
        animation.setAnimationListener(this);
        view.startAnimation(animation);
    }


	@Override
	public void onAnimationStart(Animation animation) {

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
	}


}
