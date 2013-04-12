package com.oobe.bill;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import com.oobe.R;
import com.oobe.bill.Consts.ResponseCode;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class BillActivity extends Activity implements OnItemSelectedListener,
		OnClickListener {
	
    private static final String TAG = "Dungeons";

    /**
     * Used for storing the log text.
     */
    private static final String LOG_TEXT_KEY = "DUNGEONS_LOG_TEXT";
    
	private ListView mOwnedItemsTable;
	private ArrayList<String> list;
	private String mItemName;
	private String mSku;

	private Button mBuyButton1;
	private Button mBuyButton2;
	
//    private DungeonsPurchaseObserver mDungeonsPurchaseObserver;
    private Handler mHandler;

    private BillingService mBillingService;
    
    private static List<FeatureBean> mFeatureCache = new ArrayList<FeatureBean>();
    
    
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feature_list);
		init();
	}

	private void init() {
		mBuyButton1 = (Button) findViewById(R.id.buy_button1);
		mBuyButton1.setEnabled(false);
		mBuyButton1.setOnClickListener(this);

		mBuyButton2 = (Button) findViewById(R.id.buy_button1);
		mBuyButton2.setEnabled(false);
		mBuyButton2.setOnClickListener(this);

		mOwnedItemsTable = (ListView) findViewById(R.id.owned_items);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, getData());
		mOwnedItemsTable.setAdapter(adapter);
	}

	private ArrayList<String> getData()  {
		if (null == list) {
			list = new ArrayList<String>();
		}
		FeatureXMLParser parser = new FeatureXMLParser();
		try {
			InputStream in = getAssets().open("features.xml");
			List<FeatureBean> features = parser.parseData(in);
			for(FeatureBean feature : features) {
				list.add(feature.getCode());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void onClick(View v) {
		if (v == mBuyButton1) {
//			if (!mBillingService.requestPurchase(mSku, mPayloadContents)) {
//				showDialog(DIALOG_BILLING_NOT_SUPPORTED_ID);
//			}
		} else if (v == mBuyButton2) {
//			showPayloadEditDialog();
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		mItemName = getString(CATALOG[position].nameId);
		mSku = CATALOG[position].sku;
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

	private enum Managed {
		MANAGED, UNMANAGED
	}

	private static class CatalogEntry {
		public String sku;
		public int nameId;
		public Managed managed;

		public CatalogEntry(String sku, int nameId, Managed managed) {
			this.sku = sku;
			this.nameId = nameId;
			this.managed = managed;
		}
	}

	/** An array of product list entries for the products that can be purchased. */
	private static final CatalogEntry[] CATALOG = new CatalogEntry[] {
			new CatalogEntry("sword_001", R.string.two_handed_sword,
					Managed.MANAGED),
			new CatalogEntry("potion_001", R.string.potions, Managed.UNMANAGED),
			new CatalogEntry("android.test.purchased",
					R.string.android_test_purchased, Managed.UNMANAGED),
			new CatalogEntry("android.test.canceled",
					R.string.android_test_canceled, Managed.UNMANAGED),
			new CatalogEntry("android.test.refunded",
					R.string.android_test_refunded, Managed.UNMANAGED),
			new CatalogEntry("android.test.item_unavailable",
					R.string.android_test_item_unavailable, Managed.UNMANAGED), };
}
