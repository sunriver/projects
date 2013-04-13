package com.oobe.tcp;

import java.io.IOException;

import com.oobe.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class TcpActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		try {
			 Runtime.getRuntime().exec("su");
			 Runtime.getRuntime().exec("dd if=/mnt/sdcard/tcpdump of=/data/local/tcpdump");
			 Runtime.getRuntime().exec("chmod 6755 /data/local/tcpdump");
			Runtime.getRuntime().exec("/data/local/tcpdump -p -vv -s 0 -w /sdcard/capture.pcap");
			Log.i("run", "success!!!!!!!!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("run", e.toString());
		}
	}
}
