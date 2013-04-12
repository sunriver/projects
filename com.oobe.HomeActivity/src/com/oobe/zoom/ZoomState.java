package com.oobe.zoom;

import java.util.Observable;

import android.graphics.PointF;

public class ZoomState extends Observable {
	private float mOffsetLeft = 0;
	private float mOffsetTop = 0;
	
	private PointF mCenterPoint = new PointF();
	
	private float mZoom = 1f;
	
	public  void setOffset(float offsetLeft, float offsetTop) {
		mOffsetLeft = offsetLeft;
		mOffsetTop = offsetTop;
		setChanged();
		notifyObservers();
	}
	
	public void setZoom(float zoom) {
		mZoom = zoom;
	}
	
	public float getZoom() {
		return mZoom;
	}
	
	public float getOffsetLeft() {
		return mOffsetLeft;
	}
	
	public float getOffsetTop() {
		return mOffsetTop;
	}
	
	public void setCenterPoint(float x, float y) {
		mCenterPoint.set(x, y);
	}
	
	public PointF getCenterPoint() {
		return mCenterPoint;
	}
	
}
