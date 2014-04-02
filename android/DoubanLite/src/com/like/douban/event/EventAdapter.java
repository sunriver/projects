package com.like.douban.event;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.like.R;
import com.like.douban.event.bean.Event;
import com.like.douban.event.bean.EventList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EventAdapter extends BaseAdapter {
	
	private EventList mEvents = new EventList();
	private LayoutInflater mInflater;
	private ImageLoader mImageLoader;
	
	public EventAdapter(Context ctx, ImageLoader loader) {
		mInflater = LayoutInflater.from(ctx);
		this.mImageLoader = loader;
	}

	@Override
	public int getCount() {
		return mEvents.events.length;
	}

	@Override
	public Object getItem(int position) {
		return mEvents.events[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void updateEventList(EventList eventList) {
		this.mEvents = eventList;
		this.notifyDataSetChanged();
	}
	

	private static class ViewHolder {
		TextView eventNameTv;
		TextView eventAddressTv;
		TextView eventTimeTv;
		NetworkImageView  eventThumbIv;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			ViewGroup vg = (ViewGroup) mInflater.inflate(R.layout.listview_item_event, null, false);
			holder.eventNameTv = (TextView) vg.findViewById(R.id.tv_event_name);
			holder.eventAddressTv = (TextView) vg.findViewById(R.id.tv_event_address);
			holder.eventTimeTv = (TextView) vg.findViewById(R.id.tv_event_time);
			holder.eventThumbIv = (NetworkImageView) vg.findViewById(R.id.niv_event_thumb);
			vg.setTag(holder);
			convertView = vg;
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Event evt = mEvents.events[position];
		holder.eventNameTv.setText(evt.title);
		holder.eventAddressTv.setText(evt.address);
		
		holder.eventTimeTv.setText(evt.getEventTime());
		holder.eventThumbIv.setImageUrl(evt.image, mImageLoader);
		return convertView;
	}

}