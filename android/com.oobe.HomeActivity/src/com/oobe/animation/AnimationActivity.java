package com.oobe.animation;


import com.oobe.R;
import com.oobe.R.id;
import com.oobe.R.layout;
import com.oobe.palette.CustomView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class AnimationActivity extends Activity {

	private ImageButton mImgBtn;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anim_layout);
		mImgBtn = (ImageButton) findViewById(R.id.animBtn);
	}


	private final int TRANSLATE = 0 ;
	private final int ALPHA = 1 ;
	private final int ROTATE = 2 ;
	private final int SCALE = 3 ;
	private final int ANIMATION_SET = 4 ;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuItem item = menu.add(0, TRANSLATE, Menu.NONE, "translate");
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		
		item = menu.add(0, ALPHA, Menu.NONE, "alpha");
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		
		item = menu.add(0, ROTATE, Menu.NONE, "rotate");
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		item = menu.add(0, SCALE, Menu.NONE, "scale");
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		
		item = menu.add(0, ANIMATION_SET, Menu.NONE, "set");
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case TRANSLATE:
				mImgBtn.startAnimation(getAnimation(TRANSLATE));
				break;
			case ALPHA:
				mImgBtn.startAnimation(getAnimation(ALPHA));
				break;
			case ROTATE:
				mImgBtn.startAnimation(getAnimation(ROTATE));
				break;
			case ANIMATION_SET:
				startAnimationSet();
				break;
			case SCALE:
				mImgBtn.startAnimation(getAnimation(SCALE));
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private Animation getAnimation(int type) {
		Animation a = null;
		switch (type) {
			case ALPHA:
				a = new AlphaAnimation(1.0f, 0.2f);
				a.setDuration(5000);
				a.setRepeatCount(Animation.INFINITE);
				a.setInterpolator(AnimationActivity.this, android.R.anim.accelerate_interpolator);
				break;
			case ROTATE:
				a = new RotateAnimation(3.14f / 2, 3.14f * 2);
				a.setDuration(5000);
				a.setRepeatCount(Animation.INFINITE);
				a.setInterpolator(AnimationActivity.this, android.R.anim.accelerate_interpolator);
				break;
			case TRANSLATE:	
				a = new TranslateAnimation(0.0f, 600.0f, 0.0f, 300.0f);
				a.setDuration(5000);
				a.setRepeatCount(Animation.INFINITE);
				a.setInterpolator(AnimationActivity.this, android.R.anim.accelerate_interpolator);
				break;
			case SCALE:
				/**
				 * fromX, toX, fromY, toY, pivotX, pivotY; 注意pivotX, pivotY是相对于物体的坐标
				 */
				a = new ScaleAnimation(0.0f, 1.4f, 0.0f, 2.0f, 50, 0);
				/**
				 *动画播放结束后保持大小不变
				 */
				a.setFillAfter(true);
				a.setDuration(5000);
				a.setInterpolator(AnimationActivity.this, android.R.anim.accelerate_interpolator);
				break;
			default:
				break;
		}
		return a;
	}
	
	private void startAnimationSet() {
		AnimationSet as = new AnimationSet(this, null);
		as.addAnimation(getAnimation(TRANSLATE));
		as.addAnimation(getAnimation(ALPHA));
		as.setDuration(5000);
		mImgBtn.startAnimation(as);
	}
	
}
