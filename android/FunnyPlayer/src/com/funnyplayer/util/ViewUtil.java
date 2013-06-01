package com.funnyplayer.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.funnyplayer.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class ViewUtil {
	
	
	public static int getPixByDP(Context ctx, int dp) { 
		float density = ctx.getResources().getDisplayMetrics().density;
		return (int) (dp * density + 0.5f);
	}
	
	public static int getDPByPix(Context ctx, int pixel) {
		return getDPByPix(ctx.getResources().getDisplayMetrics(), pixel);
	}
	
	public static int getDPByPix(DisplayMetrics metrics, int pixel) {
		return Math.round(pixel / metrics.density);
	}
    
	
	public static boolean deviceIsPad(Context context) {
		boolean isPad = (getSmallestScreenWidthDp(context) >= SCREEN_TYPE_7INCH);
		if (Build.MANUFACTURER.equals("Amazon")) {
			if (Build.MODEL.equals("Kindle Fire")) { // KindleFire don't support three finger events
				isPad = false;
			}
			if (Build.MODEL.equals("KFTT")) { // KindleFireHD 7" only sw533dp
				isPad = true;
			}
		}
		return isPad;
	}
	
	// FIXME: Should remove this
	public static boolean deviceIsPad() {
		boolean isPad = true;
		if (Build.MANUFACTURER.equals("Amazon")) {
			if (Build.MODEL.equals("Kindle Fire")) { // KindleFire don't support three finger events
				isPad = false;
			}
			if (Build.MODEL.equals("KFTT")) { // KindleFireHD 7" only sw533dp
				isPad = true;
			}
		}
		return isPad;
	}
	
	public static int getSmallestScreenWidthDp(Context context) {
		int value = 0;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			value = context.getResources().getConfiguration().smallestScreenWidthDp;
		} else {
			//Display disp = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			DisplayMetrics metrics = context.getResources().getDisplayMetrics();
			value = getDPByPix(metrics, Math.min(metrics.heightPixels, metrics.widthPixels));
		}
		return value;
	}
	
	public static final int SCREEN_TYPE_UNKNOWN	= 0;
	public static final int SCREEN_TYPE_3INCH	= 320;
	public static final int SCREEN_TYPE_5INCH	= 480;
	public static final int SCREEN_TYPE_7INCH	= 600;
	public static final int SCREEN_TYPE_10INCH	= 720;
	
	public static int getScreenSizeInDP(Context context) {
		int type = SCREEN_TYPE_UNKNOWN;
		int minWidthInDP = getSmallestScreenWidthDp(context);
		if (minWidthInDP >= 320) type = SCREEN_TYPE_3INCH;
		if (minWidthInDP >= 480) type = SCREEN_TYPE_5INCH;
		if (minWidthInDP >= 600) type = SCREEN_TYPE_7INCH;
		if (minWidthInDP >= 720) type = SCREEN_TYPE_10INCH;
		return type;
	}
	
	public static void setActionBarBackgroundRepeat(Context context, ActionBar actionBar) {
		// Set background for ActionBar workaround, define in XML files not
		// works under ICS
		
		//if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) return;
		if (actionBar == null) return;

		Resources resource = context.getResources();
		
		// FIXME: Because SDK < 4.1 doesn't support drawable-sw600dp-hdpi/mdpi, we had to add below quirk fix
//		int rid = R.drawable.actionbar_bg;
//		if (resource.getDimension(R.dimen.action_bar_size_handset) == resource.getDimension(R.dimen.action_bar_size)) {
//			rid = R.drawable.custom_titlebar_bg;
//		}
		
		BitmapDrawable bg = new BitmapDrawable(BitmapFactory.decodeResource(resource, R.drawable.action_bar_bg));
		bg.setTileModeX(TileMode.REPEAT);
		actionBar.setBackgroundDrawable(bg);
	}

	private static boolean mIsMultiPane = true;
	public static void setFragmentIsMultiPane(boolean isMutliPane) {
		mIsMultiPane = (isMutliPane && mIsMultiPane);
	}
	
	public static boolean getFragmentIsMultiPanel() {
		return mIsMultiPane;
	}
	
	// Hacking: Accessing Android Resources By Name at Runtime
    public static int getAndroidDimen(String nativeName){
        int resourceId = Resources.getSystem().getIdentifier(nativeName, "dimen", "android");
        if(resourceId == 0){
            return 0;
        } else {
            return (int) Resources.getSystem().getDimension(resourceId);
        }
    }
    
	// Hacking: Accessing Android Resources By Name at Runtime
    public static int getAndroidLayout(String nativeName){
	    int resourceId = Resources.getSystem().getIdentifier(nativeName, "layout", "android");
	    return resourceId;
	}

    public static int getAndroidId(String nativeName){
	    int resourceId = Resources.getSystem().getIdentifier(nativeName, "id", "android");
	    return resourceId;
	}
    
    public static int getAndroidDrawable(String nativeName){
	    int resourceId = Resources.getSystem().getIdentifier(nativeName, "drawable", "android");
	    return resourceId;
	}
    

    
    private static void resetFragmentPadding(View v) {
		// Padding alignment hacking: Please refer to "/res/layout/preference_list_content.xml" or
		// /res/layout-xlarge/breadcrumbs_in_fragment.xml.
		// SDK 4.0~4.1(14_16) Left/Right padding = @dimen/preference_fragment_padding_side
		// SDK 3.2 Left/Right padding = @dimen/preference_breadcrumb_paddingLeft/Right
		// SDK 3.1 Left/Right padding = 32dip
		// SDK 3.0 Left/Right padding = 32dip
		int padding = 0;
		switch (Build.VERSION.SDK_INT) {
		case Build.VERSION_CODES.HONEYCOMB:
		case Build.VERSION_CODES.HONEYCOMB_MR1:
			//32 dip to pixel
			padding = getPixByDP(v.getContext(), 32);
			break;
		case Build.VERSION_CODES.HONEYCOMB_MR2:
			padding = getAndroidDimen("preference_breadcrumb_paddingLeft");
			break;
		case Build.VERSION_CODES.ICE_CREAM_SANDWICH:
//		case Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1:
		// 4.1.1 and 4.1.2 have different fragment padding, 4.1.1 =  preference_fragment_padding_side, 4.1.2 = 0dp
		//case Build.VERSION_CODES.JELLY_BEAN: 
			padding = getAndroidDimen("preference_fragment_padding_side");
			break;
		default:
			break;
		}

    }
	

	
	public static void setTabWidgetFont(TabHost	mTabHost, float fontSize) {
		TabWidget tabWidget = mTabHost.getTabWidget(); 
		for (int i = 0; i < tabWidget.getChildCount(); i++) { 
			TextView tv=(TextView)tabWidget.getChildAt(i).findViewById(android.R.id.title); 
			tv.setTextSize(fontSize);
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tv.getLayoutParams();  
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0); 
			params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE); 

		} 
	}
	
	public static Point getBitmapSize(int id, Context context) {
		int x;
		int y;
		BitmapFactory.Options opts = new BitmapFactory.Options();
	    opts.inJustDecodeBounds = true;
	    BitmapFactory.decodeResource(context.getResources(), id, opts);
	    if (0 == opts.inDensity) {
	    	x = opts.outWidth;
	    	y = opts.outHeight;
	    } else {
	    	x = opts.outWidth * opts.inTargetDensity / opts.inDensity;
	    	y = opts.outHeight * opts.inTargetDensity / opts.inDensity;
	    }
	    if (x <= 0 || y <=0 ) return null;
	    return new Point(x, y);
	}
	
	public static void setColorSpanText(TextView view, String fulltext, String subtext, int color) {
		view.setText(fulltext, TextView.BufferType.SPANNABLE);
		Spannable str = (Spannable) view.getText();
		int index = fulltext.indexOf(subtext);
		str.setSpan(new ForegroundColorSpan(color), index, index + subtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	}
	
	public static void setUnderLineSpanText(TextView view, String fulltext, String subtext) {
		view.setText(fulltext, TextView.BufferType.SPANNABLE);
		Spannable str = (Spannable) view.getText();
		int index = fulltext.indexOf(subtext);
		str.setSpan(new UnderlineSpan(), index, index + subtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	}
	
	public static void setColorUnderLineSpanText(TextView view, String fulltext, String subtext, int color) {
		view.setText(fulltext, TextView.BufferType.SPANNABLE);
		Spannable str = (Spannable) view.getText();
		int index = fulltext.indexOf(subtext);
		str.setSpan(new ForegroundColorSpan(color), index, index + subtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		str.setSpan(new UnderlineSpan(), index, index + subtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	}
}
