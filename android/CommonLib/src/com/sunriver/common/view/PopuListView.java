package com.sunriver.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class PopuListView extends LinearLayout implements OnClickListener {
	private PopupWindow mPopup;
	private int mPopuWidth;
	private int mPopuHeight;
	private int mPopuMaxHeight;
	private Drawable mPopuDrawable;
	private ListView mListView;
	private TextView mTitleTv;
	private OnItemClickListener mOnItemClickListener;

	public PopuListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PopuListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray a = context.obtainStyledAttributes(attrs,
				com.sunriver.R.styleable.com_sunriver_common_view_PopuListView,
				defStyle, 0);
		mPopuWidth = a
				.getLayoutDimension(
						com.sunriver.R.styleable.com_sunriver_common_view_PopuListView_popupWidth,
						ViewGroup.LayoutParams.WRAP_CONTENT);
		mPopuHeight = a
				.getLayoutDimension(
						com.sunriver.R.styleable.com_sunriver_common_view_PopuListView_popupHeight,
						ViewGroup.LayoutParams.WRAP_CONTENT);
		mPopuMaxHeight = a
				.getLayoutDimension(
						com.sunriver.R.styleable.com_sunriver_common_view_PopuListView_popupMaxHeight,
						ViewGroup.LayoutParams.WRAP_CONTENT);
		mPopuDrawable = a
				.getDrawable(com.sunriver.R.styleable.com_sunriver_common_view_PopuListView_popupBackground);
		a.recycle();

		init(context);
	}

	private void init(Context context) {
		mListView = new ListView(context);
		LayoutParams lp = new LinearLayout.LayoutParams(mPopuWidth, mPopuHeight);
		mListView.setLayoutParams(lp);
		for (int i = 0, len = this.getChildCount(); i < len; i++) {
			View child = this.getChildAt(i);
			child.setOnClickListener(this);
		}
		this.setOnClickListener(this);
	}

	public void setAdapter(ListAdapter adapter) {
		mListView.setAdapter(adapter);
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.mOnItemClickListener = listener;
		if (listener != null) {
			mListView.setOnItemClickListener(new WrapOnItemClickListener());
		} else {
			mListView.setOnItemClickListener(null);
		}
	}

	private void show() {
		mPopup = new PopupWindow(mListView, mPopuWidth, mPopuHeight);
		mPopup.setFocusable(true);
		mPopup.setBackgroundDrawable(mPopuDrawable);
		mPopup.showAsDropDown(this);
	}

	private void dismiss() {
		if (mPopup != null && mPopup.isShowing()) {
			mPopup.dismiss();
		}
	}

	private boolean isShowing() {
		if (mPopup != null && mPopup.isShowing()) {
			return true;
		}
		return false;
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		mTitleTv = new TextView(getContext());
		mTitleTv.setGravity(Gravity.CENTER);
		LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.CENTER;
		addView(mTitleTv, lp);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		dismiss();
	}

	@Override
	public void onClick(View v) {
		if (isShowing()) {
			mPopup.dismiss();
			mPopup = null;
		} else {
			show();
		}
	}

	private class WrapOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (mOnItemClickListener != null) {
				String selectItem = (String) mListView
						.getItemAtPosition(position);
				mTitleTv.setText(selectItem);
				mOnItemClickListener.onItemClick(parent, view, position, id);
				dismiss();

			}
		}

	}

}
