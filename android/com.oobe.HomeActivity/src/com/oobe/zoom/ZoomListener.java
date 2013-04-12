package com.oobe.zoom;

import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class ZoomListener implements OnTouchListener {
	
	private ZoomState mState;
	
	private float mDistance = 10;
	private float mLastX0;
	private float mLastY0;
	private float mLastX1;
	private float mLastY1;
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getPointerCount() != 2) {
			return true;
		}
		int action = event.getAction();
		switch (action & MotionEvent.ACTION_MASK) {
			case  MotionEvent.ACTION_POINTER_DOWN:
				mLastX0 = event.getX(0);
				mLastY0 = event.getY(0);
				mLastX1 = event.getX(1);
				mLastY1 = event.getY(1);
				mDistance = (float) Math.sqrt((mLastX1 - mLastX0) * (mLastX1 - mLastX0) + (mLastY1 - mLastY0) * (mLastY1 - mLastY0));
				mState.setCenterPoint((mLastX0 + mLastX1) / 2, (mLastY0 + mLastY1) / 2);
				break;
			case MotionEvent.ACTION_MOVE:
				float x0 = event.getX(0);
				float y0 = event.getY(0);
				float x1 = event.getX(1);
				float y1 = event.getY(1);
				
				float distance0 = (float) Math.sqrt((x0 - mLastX0) * (x0 - mLastX0) + (y0 - mLastY0) * (y0 - mLastY0));
				float distance1 = (float) Math.sqrt((x1 - mLastX1) * (x1 - mLastX1) + (y1 - mLastY1) * (y1 - mLastY1));
				if ((int) (distance0 + distance1) == 0) {
					break;
				}
				float centerX  = x0 * (distance0 / (distance0 + distance1)) + x1 * (distance1 / (distance0 + distance1));
				float centerY  = y0 * (distance0 / (distance0 + distance1)) + y1 * (distance1 / (distance0 + distance1));
				
				float distance = (float) Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0));
				float zoom = mState.getZoom() * distance / mDistance;
				float offsetLeft = mState.getOffsetLeft() + zoom * (centerX - mState.getCenterPoint().x);
				float offsetTop = mState.getOffsetTop() + zoom * (centerY -  mState.getCenterPoint().y);
				
				mDistance = distance ;
				mState.setCenterPoint(centerX, centerY);
				mState.setZoom(zoom);
				mState.setOffset(offsetLeft, offsetTop);
				break;
			case MotionEvent.ACTION_POINTER_UP:
				break;
		}
		
		return true;
	}
	
	public void setZoomState(ZoomState state) {
		mState = state;
	}

}
