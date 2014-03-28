package com.douban.lite.fragment;

import com.android.volley.RequestQueue;
import com.douban.lite.R;
import com.douban.lite.api.event.GetEvents;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class EventFragment extends Fragment {
	private ListView mEventListView;
	private GetEvents mGetEvents;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.fragment_event, null, false);
		mEventListView = (ListView) contentView.findViewById(R.id.lv_event);
		return contentView;
	}
	
	public void bindRequetQueue(RequestQueue queue) {
		mGetEvents = new GetEvents(getActivity().getApplicationContext(), queue, this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mGetEvents.query("hangzhou");
	}

	
}
