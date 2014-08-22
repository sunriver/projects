package fi.harism.curl.doc;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.TextView;

public class ReadView extends TextView {

	public ReadView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ReadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ReadView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

//	@Override
//	protected void onLayout(boolean changed, int left, int top, int right,
//			int bottom) {
//		super.onLayout(changed, left, top, right, bottom);
//		resize();
//	}


	private int resize() {
		CharSequence oldContent = getText();
		CharSequence newContent = oldContent.subSequence(0, getCharNum());
		setText(newContent);
		return oldContent.length() - newContent.length();
	}

	public int getCharNum() {
		return getLayout().getLineEnd(getLineNum());
	}

	private int getLineNum() {
		Layout layout = getLayout();
		int topOfLastLine = getHeight() - getPaddingTop() - getPaddingBottom()
				- getLineHeight();
		return layout.getLineForVertical(topOfLastLine);
	}
}