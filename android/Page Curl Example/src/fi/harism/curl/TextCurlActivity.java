/*
   Copyright 2012 Harri Smatt

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package fi.harism.curl;

import java.io.IOException;

import fi.harism.curl.doc.Document;
import fi.harism.curl.doc.ReadView;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.widget.TextView;

/**
 * Simple Activity for curl testing.
 * 
 * @author harism
 */
public class TextCurlActivity extends Activity {
	private final static String TAG = TextCurlActivity.class.getSimpleName();
	private CurlView mCurlView;
	private Document mDoc;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		int index = 0;
		if (getLastNonConfigurationInstance() != null) {
			index = (Integer) getLastNonConfigurationInstance();
		}
		mCurlView = (CurlView) findViewById(R.id.curl);
		mCurlView.setPageProvider(new PageProvider());
		mCurlView.setSizeChangedObserver(new SizeChangedObserver());
		mCurlView.setCurrentIndex(index);
		mCurlView.setBackgroundColor(0xFF202830);
		mCurlView.setRenderLeftPage(false);
		
		initData();
		// This is something somewhat experimental. Before uncommenting next
		// line, please see method comments in CurlView.
		// mCurlView.setEnableTouchPressure(true);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mDoc != null) {
			mDoc.close();
		}
	}

	private void initData() {
		
		try {
			Document doc = new Document(getApplicationContext(), "doc/text.txt");
			mDoc = doc;
		} catch (Throwable e) {
			Log.w(TAG, e);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		mCurlView.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		mCurlView.onResume();
	}

//	@Override
//	public Object onRetainNonConfigurationInstance() {
//		return mCurlView.getCurrentIndex();
//	}

	/**
	 * Bitmap provider.
	 */
	private class PageProvider implements CurlView.PageProvider {

		// Bitmap resources.
		private int[] mBitmapIds = { R.drawable.obama, R.drawable.road_rage,
				R.drawable.taipei_101, R.drawable.world };
		
		private String[] mTextArray = {
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaakdajfkdasjfkadsj;fkjdsa;flkajsdf;ljadsf;lkjadsfkjkjdshfjdsakfhaskdfjhsajdkfhadsfkjhalkjdfshasdlkjfhadsflkjhdslkjfhdslkfhfjsdhfl",
				"bbbbbbbbbbbbbbbbbbbbb99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",
				"ccccccccccccccccccccc",
				"ddddddddddddddddddddd"
				
		};
		
		
		

		@Override
		public int getPageCount() {
			return Integer.MAX_VALUE;
		}

		private Bitmap loadBitmap(int width, int height, int index) {
			Bitmap b = Bitmap.createBitmap(width, height,
					Bitmap.Config.ARGB_8888);
			b.eraseColor(0xFFFFFFFF);
			Canvas c = new Canvas(b);
			Drawable d = getResources().getDrawable(mBitmapIds[index]);

			int margin = 7;
			int border = 3;
			Rect r = new Rect(margin, margin, width - margin, height - margin);

			int imageWidth = r.width() - (border * 2);
			int imageHeight = imageWidth * d.getIntrinsicHeight()
					/ d.getIntrinsicWidth();
			if (imageHeight > r.height() - (border * 2)) {
				imageHeight = r.height() - (border * 2);
				imageWidth = imageHeight * d.getIntrinsicWidth()
						/ d.getIntrinsicHeight();
			}

			r.left += ((r.width() - imageWidth) / 2) - border;
			r.right = r.left + imageWidth + border + border;
			r.top += ((r.height() - imageHeight) / 2) - border;
			r.bottom = r.top + imageHeight + border + border;

			Paint p = new Paint();
			p.setColor(0xFFC0C0C0);
			c.drawRect(r, p);
			r.left += border;
			r.right -= border;
			r.top += border;
			r.bottom -= border;

			d.setBounds(r);
			d.draw(c);
			c.drawText("qqqqqqqqqqqqqqqqqqqqqqqqq", 50, 0, p);

			return b;
		}
		
		private Bitmap drawText(int width, int height, boolean isNextPage) {
			Bitmap b = Bitmap.createBitmap(width, height,
					Bitmap.Config.ARGB_8888);
			b.eraseColor(0xFFFFFFFF);
			Canvas c = new Canvas(b);
			
			int margin = 7;
			int border = 3;
			Rect r = new Rect(margin, margin, width - margin, height - margin);
			Paint p = new Paint();
			p.setColor(0xFFC0C0C0);
			c.drawRect(r, p);
			
            ReadView tv = new ReadView(getApplicationContext());
            tv.setSingleLine(false);
            tv.setTextColor( 0xa00050ff );
            tv.setTextSize(24);
//            int lineHeight = tv.getLineHeight();
//            tv.setLineSpacing(10, 2);
//            lineHeight = tv.getLineHeight();
            tv.layout( 0, 0, width, height);
            Layout layout = tv.getLayout();
//            int charNum = tv.getCharNum();
//            lineHeight = tv.getLineHeight();
            int lineNum = height / tv.getLineHeight();
            float textSize = tv.getTextSize();
//            tv.setTextScaleX(2);
            textSize = tv.getTextSize();
            int charNumOfLine = (int) (width / (tv.getTextScaleX() * textSize));
            int charNum = lineNum * charNumOfLine;
            
//            tv.setTypeface( typeface );
			String text = null;
			try {
				if (isNextPage) {
					text = mDoc.nextPage(charNum);
				} else {
					text = mDoc.prevPage(charNum);
				}
			} catch (Exception e) {
				Log.e(TAG, "", e);
			}
			float realTextWidth = tv.getPaint().measureText(text);
			tv.setTextScaleX(width * lineNum * tv.getTextScaleX() / realTextWidth);
            tv.setText(text);
			tv.draw(c);
//			p.setColor(Color.BLACK);
//			p.setTextSize(20f);
//			p.setTextScaleX(2.0f);
//			c.drawText(text, 0, 50, p);
			return b;
		}
		
		
//		@Override
//		public void updatePage(CurlPage page, int width, int height, int index) {
//			Log.d(TAG, "updatePage() mLastIndex=" + mLastIndex + "	index="
//					+ index);
//			if (null == mDoc || mLastIndex == index) {
//				return;
//			}
//			Bitmap front = drawText(width, height, index);
//			// Bitmap front = loadBitmap(width, height, index);
//			page.setTexture(front, CurlPage.SIDE_BOTH);
//			page.setColor(Color.argb(127, 255, 255, 255), CurlPage.SIDE_BACK);
//
//		}

		@Override
		public boolean hasNextPage() {
			return mDoc.hasNextPage();
		}

		@Override
		public boolean hasPrevPage() {
			// TODO Auto-generated method stub
			return mDoc.hasPrevPage();
		}

		@Override
		public void nextPage(CurlPage page, int width, int height) {
			if (null == mDoc) {
				return;
			}
			Bitmap front = drawText(width, height, true);
			// Bitmap front = loadBitmap(width, height, index);
			page.setTexture(front, CurlPage.SIDE_BOTH);
			page.setColor(Color.argb(127, 255, 255, 255), CurlPage.SIDE_BACK);

			
		}

		@Override
		public void prevPage(CurlPage page, int width, int height) {
			if (null == mDoc) {
				return;
			}
			Bitmap front = drawText(width, height, false);
			// Bitmap front = loadBitmap(width, height, index);
			page.setTexture(front, CurlPage.SIDE_BOTH);
			page.setColor(Color.argb(127, 255, 255, 255), CurlPage.SIDE_BACK);	// TODO Auto-generated method stub
			
		}


//		@Override
//		public void updatePage(CurlPage page, int width, int height, int index) {
//			Log.d("", "");
//			switch (index) {
//			// First case is image on front side, solid colored back.
//			case 0: {
//				Bitmap front = loadBitmap(width, height, 0);
//				page.setTexture(front, CurlPage.SIDE_FRONT);
//				page.setColor(Color.rgb(180, 180, 180), CurlPage.SIDE_BACK);
//				break;
//			}
//			// Second case is image on back side, solid colored front.
//			case 1: {
//				Bitmap back = loadBitmap(width, height, 2);
//				page.setTexture(back, CurlPage.SIDE_BACK);
//				page.setColor(Color.rgb(127, 140, 180), CurlPage.SIDE_FRONT);
//				break;
//			}
//			// Third case is images on both sides.
//			case 2: {
//				Bitmap front = loadBitmap(width, height, 1);
//				Bitmap back = loadBitmap(width, height, 3);
//				page.setTexture(front, CurlPage.SIDE_FRONT);
//				page.setTexture(back, CurlPage.SIDE_BACK);
//				break;
//			}
//			// Fourth case is images on both sides - plus they are blend against
//			// separate colors.
//			case 3: {
//				Bitmap front = loadBitmap(width, height, 2);
//				Bitmap back = loadBitmap(width, height, 1);
//				page.setTexture(front, CurlPage.SIDE_FRONT);
//				page.setTexture(back, CurlPage.SIDE_BACK);
//				page.setColor(Color.argb(127, 170, 130, 255),
//						CurlPage.SIDE_FRONT);
//				page.setColor(Color.rgb(255, 190, 150), CurlPage.SIDE_BACK);
//				break;
//			}
//			// Fifth case is same image is assigned to front and back. In this
//			// scenario only one texture is used and shared for both sides.
//			case 4:
//				Bitmap front = loadBitmap(width, height, 0);
//				page.setTexture(front, CurlPage.SIDE_BOTH);
//				page.setColor(Color.argb(127, 255, 255, 255),
//						CurlPage.SIDE_BACK);
//				break;
//			}
//		}

	}

	/**
	 * CurlView size changed observer.
	 */
	private class SizeChangedObserver implements CurlView.SizeChangedObserver {
		@Override
		public void onSizeChanged(int w, int h) {
//			if (w > h) {
//				mCurlView.setViewMode(CurlView.SHOW_TWO_PAGES);
//				mCurlView.setMargins(.1f, .05f, .1f, .05f);
//			} else {
//				mCurlView.setViewMode(CurlView.SHOW_ONE_PAGE);
//				mCurlView.setMargins(.1f, .1f, .1f, .1f);
//			}
		}
	}

}