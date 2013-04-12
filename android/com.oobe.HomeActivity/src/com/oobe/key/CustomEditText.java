package com.oobe.key;

import com.oobe.R;

import android.app.Activity;
import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.TextView;

public class CustomEditText extends EditText {

	public CustomEditText(Context context) {
		super(context);
	}
	
	public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	private String getEventInfo(KeyEvent event) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" keyCode=" + event.getKeyCode());
		buffer.append(" action=" + event.getAction());
		buffer.append(" source=" + event.getSource());
		buffer.append(" characters=" + event.getCharacters());
		buffer.append(" deviceId=" + event.getDeviceId());
		buffer.append(" displayLable=" + event.getDisplayLabel());
		buffer.append(" unicodeChar=" + event.getUnicodeChar());
		buffer.append(" metaState=" + Integer.toBinaryString(event.getMetaState()));
    	return buffer.toString();
	}

	private void printLog(CharSequence msg) {
		TextView onKeyPreLogText = (TextView)((Activity) getContext()).findViewById(R.id.OnKeyPre_Log);
		CharSequence lastText = onKeyPreLogText.getText();
		onKeyPreLogText.setText(msg);
		onKeyPreLogText.append(lastText);
	}

	public boolean onKeyPreIme(int keyCode, KeyEvent event) {
		printLog("EditText.onKeyPreIme " + getEventInfo(event) + "\n");
		return false;
	}
	
	@Override
	public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
		
		outAttrs.inputType = InputType.TYPE_CLASS_TEXT;
		outAttrs.imeOptions = EditorInfo.IME_FLAG_NO_FULLSCREEN;
		
		return new BaseInputConnection(this, false) {
			@Override
			public boolean commitText(CharSequence text, int newCursorPosition) {
				printLog("InputConnection.commitText " + " text='" + text + "' newCursorPosition:" + newCursorPosition + "\n");
				return super.commitText(text, newCursorPosition);
			}

			@Override
			public boolean sendKeyEvent(KeyEvent event) {
				printLog("InpucConnection.sendKeyEvent " + getEventInfo(event) + "\n");
				return super.sendKeyEvent(event);
			}
			
			@Override
			public boolean deleteSurroundingText(int leftLength, int rightLength) {
				printLog("InputConnection.deleteSurroundingText left:" + leftLength + " right:" + rightLength + "\n");
				return super.deleteSurroundingText(leftLength, rightLength);
			}

			@Override
			public boolean beginBatchEdit() {
				printLog("InputConnection.beginBatchEdit\n");
				return super.beginBatchEdit();
			}

			@Override
			public boolean endBatchEdit() {
				printLog("InputConnection.endBatchEdit\n");
				return super.endBatchEdit();
			}
			
			@Override
			public boolean setComposingText(CharSequence text, int newCursorPosition) {
				printLog("InputConnection.setComposingText text:'" + text + "' newCursorPosition:" + newCursorPosition + "\n");
				return super.setComposingText(text, newCursorPosition);
			}
			
			@Override
			public boolean finishComposingText() {
				printLog("InputConnection.finishComposingText\n");
				return super.finishComposingText();
			}
		};

	}
}
