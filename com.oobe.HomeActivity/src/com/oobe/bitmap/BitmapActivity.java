package com.oobe.bitmap;

import com.oobe.R;
import com.oobe.R.drawable;

import android.app.Activity;   
import android.graphics.Bitmap;   
import android.graphics.BitmapFactory;   
import android.graphics.Matrix;   
import android.graphics.drawable.BitmapDrawable;   
import android.os.Bundle;   
import android.util.FloatMath;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;   
import android.widget.ImageView;   
import android.widget.LinearLayout;   
import android.widget.ImageView.ScaleType;   
  
public class BitmapActivity extends Activity implements OnTouchListener{  
	
	public interface MultiGestureListener  {
		public void onZoom();
		public boolean onTouchEvent(MotionEvent event);
	}
	
	private Bitmap mBitmap;
	private ImageView mImageView;
	private GestureDetector mGestureDetector;
	private SimpleOnGestureListener mGestureListener = new SimpleOnGestureListener() {
		
	};
	
	private MultiGestureListener mMultiGestureListener = new MultiGestureListener() {
		private float mLastX0;
		private float mLastY0;
		private float mLastX1;
		private float mLastY1;
		
		@Override
		public void onZoom() {
			Bitmap bm = Bitmap.createBitmap(mBitmap, 20, 20, 100, 100);
	        BitmapDrawable bmd = new BitmapDrawable(bm);   
			mImageView.setImageDrawable(bmd);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			if (event.getPointerCount() != 2) {
				return false;
			}
			float x0 = event.getX(0);
			float x1 = event.getX(1);
			float y0 = event.getY(0);
			float y1 = event.getY(1);
			int action = event.getAction();
			switch (action & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_POINTER_DOWN:
					mLastX0 = x0;
					mLastY0 = y0;
					mLastX1 = x1;
					mLastY1 = y1;
					break;
				case MotionEvent.ACTION_MOVE:
					float offsetX0 = Math.abs(mLastX0 - x0);
					float offsetX1 = Math.abs(mLastX1 - x1);
					float offsetY0 = mLastY0 - y0;
					float offsetY1 = mLastY1 - y1;
					float distance = FloatMath.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0));
					if (distance > 0 ) {
						this.onZoom();
					}
					
					break;
				case MotionEvent.ACTION_POINTER_UP:
					break;
			}
		
			return false;
		}
		
	};
	
	public void onCreate(Bundle icicle) {   
	        super.onCreate(icicle);   
	        LinearLayout linLayout = new LinearLayout(this);  
	        
	        mGestureDetector = new GestureDetector(this, mGestureListener);
	  
	        // 加载需要操作的图片，这里是eoeAndroid的logo图片   
	        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mode);
	  
	        //获取这个图片的宽和高   
	        int width = mBitmap.getWidth();   
	        int height = mBitmap.getHeight();   
	  
	        //定义预转换成的图片的宽度和高度   
	        int newWidth = 200;   
	        int newHeight = 200;   
	  
	        //计算缩放率，新尺寸除原始尺寸   
	        float scaleWidth = ((float) newWidth) / width;   
	        float scaleHeight = ((float) newHeight) / height;   
	  
	        // 创建操作图片用的matrix对象   
	        Matrix matrix = new Matrix();   
	  
	        // 缩放图片动作   
	        matrix.postScale(scaleWidth, scaleHeight);   
	  
	        //旋转图片 动作   
	        matrix.postRotate(45);   
	  
	        // 创建新的图片   
	        Bitmap resizedBitmap = Bitmap.createBitmap(mBitmap, 0, 0,  400, 400, matrix, true);   
	  
	        //将上面创建的Bitmap转换成Drawable对象，使得其可以使用在ImageView, ImageButton中   
	        BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);   
	  
	        //创建一个ImageView   
	        ImageView mImageView = new ImageView(this);  
	        
	        mImageView.setOnTouchListener(this);
	  
	        // 设置ImageView的图片为上面转换的图片   
	        mImageView.setImageDrawable(bmd);   
	  
	        //将图片居中显示   
	        mImageView.setScaleType(ScaleType.CENTER);   
	  
	        //将ImageView添加到布局模板中   
	        linLayout.addView(mImageView,   
	          new LinearLayout.LayoutParams(   
	                      LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT   
	                )   
	        );   
	  
	        // 设置为本activity的模板   
	        setContentView(linLayout);   
	    }
	

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getPointerCount() == 2) {
			return mMultiGestureListener.onTouchEvent(event);
		}
		return false;
	} 
	
}   