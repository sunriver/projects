package com.funnyplayer.ui.widgets;

import java.util.ArrayList;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class ScrollTabView extends HorizontalScrollView implements
		ViewPager.OnPageChangeListener {

	private ViewPager mPager = null;

	private TabAdapter mAdapter = null;

	private final LinearLayout mContainer;

	private final ArrayList<View> mTabs = new ArrayList<View>();

	public ScrollTabView(Context context) {
		this(context, null);
	}

	public ScrollTabView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ScrollTabView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);

		this.setHorizontalScrollBarEnabled(false);
		this.setHorizontalFadingEdgeEnabled(false);

		mContainer = new LinearLayout(context);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		mContainer.setLayoutParams(params);
		mContainer.setOrientation(LinearLayout.HORIZONTAL);

		this.addView(mContainer);

	}

	public void setAdapter(TabAdapter adapter) {
		this.mAdapter = adapter;

		if (mPager != null && mAdapter != null) {
			initTabs();
		}
	}

	public void setViewPager(ViewPager pager) {
		this.mPager = pager;

		if (mPager != null && mAdapter != null) {
			initTabs();
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mContainer.measure(widthMeasureSpec, heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		for (int count = mContainer.getChildCount(), i = 0, childWidthSize = width / count; i < count; i++) {
			 View child = mContainer.getChildAt(i);
			int childWidthMeasureSpec = child.getMeasuredWidth();
			int childHidthMeasureSpec = child.getMeasuredHeight();
	        childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
			child.measure(childWidthMeasureSpec, childHidthMeasureSpec);
		}
	}

	private void initTabs() {

		mContainer.removeAllViews();
		mTabs.clear();

		if (mAdapter == null) {
			return;
		}
		
		int len = mPager.getAdapter().getCount();

		for (int i = 0 ; i < len ; i++) {

			final int index = i;

			View tab = mAdapter.getView(i);
			mContainer.addView(tab);

			tab.setFocusable(true);

			mTabs.add(tab);

			tab.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mPager.getCurrentItem() == index) {
						selectTab(index);
					} else {
						mPager.setCurrentItem(index, true);
					}
				}
			});

		}

		selectTab(mPager.getCurrentItem());
	}

	@Override
	public void onPageScrollStateChanged(int state) {
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
	}

	@Override
	public void onPageSelected(int position) {
		selectTab(position);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);

		if (changed && mPager != null)
			selectTab(mPager.getCurrentItem());
	}

	public void selectTab(int position) {

		for (int i = 0, pos = 0; i < mContainer.getChildCount(); i++, pos++) {
			View tab = mContainer.getChildAt(i);
			tab.setSelected(pos == position);
		}

		Button selectedTab = (Button) mContainer.getChildAt(position);
		if (null == selectedTab) {
			return;
		}
//		float oldSize = selectedTab.getTextSize();
//		selectedTab.setTextSize(oldSize + 1.0f);
		
		final int w = selectedTab.getMeasuredWidth();
		final int l = selectedTab.getLeft();

		final int x = l - this.getWidth() / 2 + w / 2;

		smoothScrollTo(x, this.getScrollY());
	}
	

	public interface TabAdapter {
		   public View getView(int position);
	}
}
