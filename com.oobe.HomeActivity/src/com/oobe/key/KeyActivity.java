/**
 * 
 */
package com.oobe.key;

import com.oobe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author leon
 *
 */
public class KeyActivity extends Activity {
	
	private final static String EMAIL_RECEIVER = "support-android@splashtop.com";
	private final static String EMAIL_SUBJECT = "User key event log information";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Trace key input event.");
		setContentView(R.layout.key_main);
		
		EditText edit = ((EditText) findViewById(R.id.content_text));
		edit.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
			
		});
	}
	
	private void sendEmail() {
		Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("plain/text");
		String[] strEmailReciver = new String[] { EMAIL_RECEIVER };
        String strEmailBody = "Model:  "+ Build.MODEL + "\n";
        strEmailBody += "Manufacturer:  " + Build.MANUFACTURER + "\n" ;
        strEmailBody += "-----------------------------------------------------------\n";
        strEmailBody += ((EditText) findViewById(R.id.content_text)).getText();
        strEmailBody += "\n-----------------------------------------------------------\n";
        strEmailBody += ((TextView) findViewById(R.id.OnKeyPre_Log)).getText();

        intent.putExtra(Intent.EXTRA_EMAIL, strEmailReciver);
        intent.putExtra(Intent.EXTRA_TEXT, strEmailBody);
        intent.putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT);
        startActivity(Intent.createChooser(intent,getResources().getString(R.string.email_msg)));
	}
	
	private void clearText() {
		((EditText) findViewById(R.id.content_text)).setText("");
		((TextView) findViewById(R.id.OnKeyPre_Log)).setText("");
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.send_email:
			sendEmail();
			break;
		case R.id.clear:
			clearText();
			break;
		}
	    return super.onOptionsItemSelected(item);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.action_option, menu);
		return super.onCreateOptionsMenu(menu);
	}

}
