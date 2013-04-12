/**
 * 
 */
package com.oobe.ui;


import com.oobe.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

/**
 * @author leon
 *
 */
public class CustomAlertDialog extends AlertDialog implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener, DialogInterface.OnCancelListener{

	private Context mContext;
	/**
	 * @param context
	 */
	public CustomAlertDialog(Context context) {
		super(context);
		mContext=context;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param theme
	 */
	public CustomAlertDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param cancelable
	 * @param cancelListener
	 */
	public CustomAlertDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Context context=getContext();
		Resources rs=context.getResources();
		Resources mrs=mContext.getResources();
		setTitle(R.string.unlicensed_dialog_title);
//		String title=rs.getString(R.string.unlicensed_dialog_title);
		setMessage(getContext().getString(R.string.unlicensed_dialog_title));
		setCancelable(false);
		String text=getContext().getString(R.string.buy_button);
		setButton(DialogInterface.BUTTON_POSITIVE, getContext().getString(R.string.buy_button,"buy"), this);
		setButton(DialogInterface.BUTTON_NEUTRAL, getContext().getString(R.string.retry_button,"retry"), this);
		setButton(DialogInterface.BUTTON_NEGATIVE, getContext().getString(R.string.quit_button,"quit"), this);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		Log.v("Click", "");
		
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		// TODO Auto-generated method stub
		
	}
}
