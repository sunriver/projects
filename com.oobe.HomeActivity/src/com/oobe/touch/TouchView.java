package com.oobe.touch;

import com.oobe.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class TouchView extends SurfaceView implements SurfaceHolder.Callback, OnTouchListener{
	public interface MultiTouchListener {
		public void onZoom(float zoom);
		public boolean onTouchEvent(MotionEvent event);
	}
	
	class SurfaceThread extends Thread {
		private SurfaceHolder mHolder;
		
		public SurfaceThread(SurfaceHolder holder) {
			this.mHolder = holder;
		}
		
		@Override
		public void run() {
			int count = 0;
			while (true) {
				Canvas c = null;
				try {
					synchronized(mHolder) {
						c = mHolder.lockCanvas();
						c.drawColor(Color.BLACK);
						Paint p = new Paint();
						p.setColor(Color.GREEN);
						Rect r = new Rect(100, 50, 300, 250);
						c.drawRect(r, p);
						c.drawText("这是第"+(count++)+"秒", 100, 310, p);
						Thread.sleep(1000);//睡眠时间为1秒
					}
				} catch(Exception ex) {
					ex.printStackTrace();
				} finally {
					if (c!=null) {
						mHolder.unlockCanvasAndPost(c);
					}
				}
			}
		}
		
	}
	
	private int mSurfaceWidth;
	private int mSurfaceHeight;
	private SurfaceHolder mHolder;
	private Thread mThread;
	private Bitmap mBitmap;
	private Handler mHandler ;
	private Rect mRectSrc;
	private Rect mRectDst;
	
	//zoom state
	private float mOffsetLeft = 0;
	private float mOffsetTop = 0;
	private float mZoom = 1;
	
	private MultiTouchListener mMultiTouchListener = new MultiTouchListener() {
		float mDistance = 10;
		float mLastX0;
		float mLastY0;
		float mLastX1;
		float mLastY1;
		PointF mCenterPoint = new PointF();
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			if (event.getPointerCount() != 2) {
				return false;
			}
			int action = event.getAction();
			switch (action & MotionEvent.ACTION_MASK) {
				case  MotionEvent.ACTION_POINTER_DOWN:
					mLastX0 = event.getX(0);
					mLastY0 = event.getY(0);
					mLastX1 = event.getX(1);
					mLastY1 = event.getY(1);
					mDistance = (float) Math.sqrt((mLastX1 - mLastX0) * (mLastX1 - mLastX0) + (mLastY1 - mLastY0) * (mLastY1 - mLastY0));
					mCenterPoint.x = (mLastX0 + mLastX1) / 2;
					mCenterPoint.y = (mLastY0 + mLastY1) / 2;
					break;
				case MotionEvent.ACTION_MOVE:
					float x0 = event.getX(0);
					float y0 = event.getY(0);
					float x1 = event.getX(1);
					float y1 = event.getY(1);
					
					float distance0 = (float) Math.sqrt((x0 - mLastX0) * (x0 - mLastX0) + (y0 - mLastY0) * (y0 - mLastY1));
					float distance1 = (float) Math.sqrt((x1 - mLastX1) * (x1 - mLastX1) + (y1 - mLastY1) * (y1 - mLastY1));
					float centerX  = x0 * (distance0 / (distance0 + distance1)) + x1 * (distance1 / (distance0 + distance1));
					float centerY  = y0 * (distance0 / (distance0 + distance1)) + y1 * (distance1 / (distance0 + distance1));
					float distance = (float) Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0));
					
					mZoom = distance / mDistance;
					mOffsetLeft = mOffsetLeft + mZoom * (centerX - mCenterPoint.x);
					mOffsetTop = mOffsetTop + mZoom * (centerY - mCenterPoint.y);
					
//					mDistance = distance;
					mCenterPoint.x = centerX;
					mCenterPoint.y = centerY;
					
					this.onZoom(mZoom);
					break;
				case MotionEvent.ACTION_POINTER_UP:
					break;
			}
			
			return false;
		}
		@Override
		public void onZoom(final float zoom) {
			// TODO Auto-generated method stub
			mHandler.post(new Runnable(){
				@Override
				public void run() {
//					drawBitmap(zoom);
					drawBitmap(1,1);
				}});
			
		}
		
	};
	
	public TouchView(Context context) {
		super(context);
		TypedArray attrs = context.obtainStyledAttributes(R.styleable.myView);
		float textSize = attrs.getDimension(R.styleable.myView_textColor, 30);
		attrs.recycle();
		// TODO Auto-generated constructor stub
		this.mHolder = this.getHolder();
		mHandler = new Handler();
		mHolder.addCallback(this);
		mSurfaceWidth = context.getResources().getDisplayMetrics().widthPixels;
		mSurfaceHeight = context.getResources().getDisplayMetrics().heightPixels;
		this.mThread = new SurfaceThread(mHolder);
		mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.screen);
		mRectSrc = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
		mRectDst = new Rect(0, 0, mSurfaceWidth, mSurfaceHeight);
		this.setOnTouchListener(this);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
//		Toast.makeText(null, "surfaceCreated", 1000);
//		mThread.start();
		drawBitmap(1, 1);
		
	}
	


	private void drawBitmap(float zoomX, float zoomY) {
		synchronized(mHolder) {
			Canvas canvas = mHolder.lockCanvas();
//            Matrix matrix=new Matrix();
//            matrix.postScale(zoomX, zoomY);
//            Bitmap dstbmp=Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
//            canvas.drawColor(Color.BLACK);  
//            canvas.drawBitmap(dstbmp, 10, 10, null);  
			int left = (int) (-mOffsetLeft / mZoom) ;
			int top = (int) (-mOffsetTop / mZoom);
			int right = (int) (left + mSurfaceWidth / mZoom );
			int bottom = (int) (top + mSurfaceHeight / mZoom );
			left = left < 0 ? 0 : left;
			top = top < 0 ? 0 : top;
			right = right > mSurfaceWidth ? mSurfaceWidth : right;
			bottom = bottom > mSurfaceHeight ? mSurfaceHeight : bottom;
			mRectSrc.set(left, top, right, bottom);
			canvas.drawBitmap(mBitmap, mRectSrc, mRectDst, null);
            mHolder.unlockCanvasAndPost(canvas);
		}
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
//		Toast.makeText(null, "surfaceDestroyed", 1000);
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getPointerCount() > 1) {
			return mMultiTouchListener.onTouchEvent(event);
		}
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

}
