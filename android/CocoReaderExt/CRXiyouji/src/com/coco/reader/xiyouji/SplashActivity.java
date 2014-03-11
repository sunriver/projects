package com.coco.reader.xiyouji;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import com.coco.reader.xiyouji.R;

public class SplashActivity extends ActionBarActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        View view = View.inflate(this, R.layout.splash, null);
        setContentView(view);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        view.startAnimation(animation);
        animation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        goHome();
                    }
                }, 500);
            }
        });
    }

    private void goHome() {
		Intent intent = new Intent(this, YoumiHomeActivity.class);
		startActivity(intent);
		finish();
    }

}
