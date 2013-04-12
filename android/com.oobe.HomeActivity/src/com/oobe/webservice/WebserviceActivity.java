package com.oobe.webservice;

import java.io.UnsupportedEncodingException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;

import com.oobe.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class WebserviceActivity extends Activity {
	private Button okButton;

	final static String TAG = "OOBE";
	private Handler mHandler;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
  
		setContentView(R.layout.webservice);
		
		okButton = (Button) this.findViewById(R.id.searchBtn);
		okButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				  new Thread() {
					public void run() {
						String city = "北京";
						getWeather(city);  
					}
				  }.start();
			}
		});
		mHandler = new Handler();

	}

	private static final String NAMESPACE = "http://WebXml.com.cn/";

	// WebService地址
	private static String URL = "http://www.webxml.com.cn/webservices/weatherwebservice.asmx";

	private static final String METHOD_NAME = "getWeatherbyCityName";

	private static String SOAP_ACTION = "http://WebXml.com.cn/getWeatherbyCityName";

	private String weatherToday;

	private SoapObject detail;

	public void getWeather(String cityName) {
		try {
			SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);
			Log.d(TAG, "WebserviceActivity::getWeather() cityName:" + cityName + " rpc:" + rpc);
			rpc.addProperty("theCityName", cityName);

			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.bodyOut = rpc;
			envelope.dotNet = true;
			envelope.setOutputSoapObject(rpc);
			
			HttpTransportSE ht = new HttpTransportSE(URL);

//			AndroidHttpTransport ht = new AndroidHttpTransport(URL);
			ht.debug = true;

			ht.call(SOAP_ACTION, envelope);
			//ht.call(null, envelope);

			//SoapObject result = (SoapObject)envelope.bodyIn;
			//detail = (SoapObject) result.getProperty("getWeatherbyCityNameResult");

			detail =(SoapObject) envelope.getResponse();
			
			//System.out.println("result" + result);

//			Toast.makeText(this, detail.toString(), 10000).show();
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					String text;
					try {
						text = parseWeather(detail);
						AlertDialog.Builder builder = new AlertDialog.Builder(WebserviceActivity.this);
						builder.setMessage(text);
						builder.setPositiveButton("OK", null);
						builder.show();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			});
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String parseWeather(SoapObject detail)
			throws UnsupportedEncodingException {
		String date = detail.getProperty(6).toString();
		weatherToday = "今天：" + date.split(" ")[0];
		weatherToday = weatherToday + "\n天气：" + date.split(" ")[1];
		weatherToday = weatherToday + "\n气温："
				+ detail.getProperty(5).toString();
		weatherToday = weatherToday + "\n风力："
				+ detail.getProperty(7).toString() + "\n";
		Log.d(TAG, "WebserviceActivity::parseWeather() weatherToday:" + weatherToday);
		return weatherToday;
	}
}
