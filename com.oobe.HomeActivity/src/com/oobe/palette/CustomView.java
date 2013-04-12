package com.oobe.palette;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.Scroller;
import android.widget.Toast;

public class CustomView extends View {
	
	private GradientDrawable mShadow;
	
	private Scroller mScroller;
	
	private Context mContext;
	
	private GestureDetector mGestureDetector;
	
	private Paint mPaint;
	
	private State mode = State.POINT;
	
	private enum State {
		LINE, POINT, UNKNOW
	}
	
	private Bitmap mCacheBitmap;
	
	private Canvas mCacheCanvas;
	
	private PointF mLastPoint = new PointF(-1f,-1f);
	
	private SimpleOnGestureListener mGestureListener = new SimpleOnGestureListener() {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			mode = State.LINE;
			if (mLastPoint.equals(-1f, -1f)) {
				mLastPoint.x = e1.getX();
				mLastPoint.y = e1.getY();
			}
			mCacheCanvas.drawLine(mLastPoint.x, mLastPoint.y, e2.getX(), e2.getY(), mPaint);
			mLastPoint.x = e2.getX();
			mLastPoint.y = e2.getY();
			invalidate();
			return true;
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			mode = State.POINT;
			mCacheCanvas.drawPoint(e.getX(), e.getY(), mPaint);
			invalidate();
			return true;
		}
		
		
	};
	
	public CustomView(Context context) {
		super(context);
		init(context);
	}
	
	public CustomView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	@Override
    protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		setBackgroundResource(com.oobe.R.drawable.shadow);
		canvas.drawBitmap(mCacheBitmap, 0, 0, mPaint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getPointerCount() > 1) {
			return true;
		}
		mGestureDetector.onTouchEvent(event);
		switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				if (State.LINE == mode) {
					mLastPoint.x = -1f;
					mLastPoint.y = -1f;
				}
				break;
		}
		return true;
	}

	private void drawReg(Canvas canvas) {
		mShadow.setBounds(20, 250, 100, 350);
		mShadow.setColor(Color.BLUE);
		mShadow.setCornerRadius(15);
		mShadow.setAlpha(100);
		mShadow.draw(canvas);
	}
	

	private void init(Context context) {
		mContext = context;
		mShadow = new GradientDrawable();
		mScroller = new Scroller(context);
		
		mCacheBitmap = createBitmap();
		mCacheCanvas = new Canvas(mCacheBitmap);
		
		mGestureDetector = new GestureDetector(context, mGestureListener);
		
		mPaint = new Paint();
		mPaint.setColor(Color.BLUE);
		mPaint.setStrokeWidth(5.0f);
		
	}
	
	public void clear() {
		mCacheBitmap.recycle();
		mCacheBitmap = createBitmap();
		mCacheCanvas.setBitmap(mCacheBitmap);
		invalidate(); 
	}
	
	private Bitmap createBitmap() {
		int width = mContext.getResources().getDisplayMetrics().widthPixels;
		int height = mContext.getResources().getDisplayMetrics().heightPixels;
		return Bitmap.createBitmap(width, height, Config.ARGB_8888);
	}
	
	public void save() {
		mCacheCanvas.save(Canvas.ALL_SAVE_FLAG);
	    File f = new File("/sdcard/0.png");  
        FileOutputStream fos = null;  
        try {  
            fos = new FileOutputStream(f);  
            mCacheBitmap.compress(Bitmap.CompressFormat.PNG, 50, fos); 
            Toast.makeText(mContext, "Save succeed!", 2000).show();
            
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        }  
	}
}
