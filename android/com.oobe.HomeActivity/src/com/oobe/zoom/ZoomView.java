package com.oobe.zoom;

import java.util.Observable;
import java.util.Observer;

import com.oobe.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ZoomView extends SurfaceView implements SurfaceHolder.Callback , Observer{
	
	class SurfaceThread extends Thread {
		@Override
		public void run() {
			while (true) {
				drawBitmap();
			}
		}
	}
	
	private ZoomState mZoomState;
	
	private Bitmap mBitmap;
	
	private SurfaceHolder mHolder;
	
	private Handler mHandler;
	
	private Rect mRectSrc;
	private Rect mRectDst;
	private int mSurfaceWidth;
	private int mSurfaceHeight;
	private int mBitmapWidth;
	private int mBitmapHeight;
	
	
	public ZoomView(Context context) {
		super(context);
		mZoomState = new ZoomState();
		mHolder = getHolder();
		mHandler = new Handler();
		mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.screen);
		mBitmapWidth = mBitmap.getWidth();
		mBitmapHeight = mBitmap.getHeight();
		mSurfaceWidth = context.getResources().getDisplayMetrics().widthPixels;
		mSurfaceHeight = context.getResources().getDisplayMetrics().heightPixels;
		mRectSrc = new Rect(0, 0, mBitmapWidth, mBitmapHeight);
		mRectDst = new Rect(0, 0, mSurfaceWidth, mSurfaceHeight);
		init();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		new SurfaceThread().start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(Observable observable, Object data) {
		//Set Size
		resize();
	}
	
	private void drawBitmap() {
		Canvas cs = null;
		try {
			synchronized(mHolder){
				cs = mHolder.lockCanvas();
				cs.drawColor(Color.BLACK);
				cs.drawBitmap(mBitmap, new Rect(mRectSrc), new Rect(mRectDst), null);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			mHolder.unlockCanvasAndPost(cs);
		}
	}
	
	private void resize() {
		float zoom = mZoomState.getZoom();
		float offsetleft = mZoomState.getOffsetLeft();
		float offsetTop = mZoomState.getOffsetTop();
		PointF centerPoint = mZoomState.getCenterPoint();
		
		
		
		// Init video rect, default draw the whole bitmap
		mRectSrc.left = 0;
		mRectSrc.top = 0;
		mRectSrc.right = mBitmapWidth;
		mRectSrc.bottom = mBitmapHeight;
		
		// Init surface rect, default fill the whole window
		mRectDst.left = 0;
		mRectDst.top = 0;
		mRectDst.right = mSurfaceWidth;
		mRectDst.bottom = mSurfaceHeight;
		
//		int left = (int) (-offsetleft / zoom) ;
//		int top = (int) (-offsetTop / zoom);
//		int right = (int) (left + mSurfaceWidth / zoom );
//		int bottom = (int) (top + mSurfaceHeight / zoom );
//		left = left < 0 ? 0 : left;
//		top = top < 0 ? 0 : top;
//		right = right > mSurfaceWidth ? mSurfaceWidth : right;
//		bottom = bottom > mSurfaceHeight ? mSurfaceHeight : bottom;
		int left = (int) (centerPoint.x * (zoom -1) / zoom);
		int top = (int) (centerPoint.y * (zoom -1) / zoom);
		int right = (int) (left + mSurfaceWidth / zoom );
		int bottom = (int) (top + mSurfaceHeight / zoom );
		mRectSrc.set(left, top, right, bottom);
	}
	
	private void init() {
		mZoomState.addObserver(this);
		mHolder.addCallback(this);
		//Set Touch listener
		ZoomListener zoomListener = new ZoomListener();
		zoomListener.setZoomState(mZoomState);
		setOnTouchListener(zoomListener);
	}

}
