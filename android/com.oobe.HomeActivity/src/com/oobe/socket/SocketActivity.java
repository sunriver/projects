package com.oobe.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SocketActivity extends Activity {
	private String mIp = "localhost";
	private int mPort = 12345;

	class Server extends Thread {
		private ServerSocket mServerSocket;
		public Server() {
			try {
				mServerSocket = new ServerSocket(mPort);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			try {
				Socket socket = mServerSocket.accept();
				BufferedReader bufReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
				String msg = null;
				do {
					msg = bufReader.readLine();
					writer.println("Ack:" + msg);
				} while (msg.equals("end"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	class Client extends Thread {

		@Override
		public void run() {
			try {
				Socket socket = new Socket(mIp, mPort);
				PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String msg = null;
				do {
					msg = reader.readLine();
				} while (msg.equals("Bye"));
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private EditText mServerText;
	private EditText mClientText;
	
	private Server mServer;
	private Client mClient;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_RIGHT_ICON);
		setTitle("Talk");
		setTheme(android.R.style.Theme_Light);

		LinearLayout mainLayout = new LinearLayout(this, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(this, null);
		lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
		lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
		setContentView(mainLayout);
		
		mServerText= new EditText(this);
		lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
		lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
		lp.weight = 1;
		mainLayout.addView(mServerText, lp);

		TextView divider = new TextView(this);
		divider.setText("---------------------------");
		mainLayout.addView(divider, lp);
		
		mClientText = new EditText(this);
		mainLayout.addView(mClientText, lp);
	}
	
	@Override
	protected void onStart() {
		beginToTalk();
	}


	private void init() {
		mServerText.setOnKeyListener(new OnKeyListener(){

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					
				}
				return false;
			}});
	}

	private void beginToTalk() {
		mServer = new Server();
		mClient = new Client();
		mServer.start();
		mClient.start();
	}
	
}
