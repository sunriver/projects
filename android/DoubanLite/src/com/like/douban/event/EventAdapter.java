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
	private Event[] mEvents = new Event[0];
	private LayoutInflater mInflater;
	private ImageLoader mImageLoader;
	
	public EventAdapter(Context ctx, ImageLoader loader) {
		mInflater = LayoutInflater.from(ctx);
		this.mImageLoader = loader;
	}

	@Override
	public int getCount() {
		return mEvents.length;
	}

	@Override
	public Object getItem(int position) {
		return mEvents[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void updateEventList(EventList eventList) {
		this.mEvents = eventList.events;
		this.notifyDataSetChanged();
	}
	
	public void updateEventList(Event[] eventList) {
		this.mEvents = eventList;
		this.notifyDataSetChanged();
	}
	

	private static class ViewHolder {
		TextView eventNameTv;
		TextView eventCategoryNameTv;
		TextView eventAddressTv;
		TextView eventTimeTv;
		TextView eventWisherCountTv;
		TextView eventParticipantCountTv;
		NetworkImageView  eventThumbIv;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			ViewGroup vg = (ViewGroup) mInflater.inflate(R.layout.listview_item_event, null, false);
			holder.eventNameTv = (TextView) vg.findViewById(R.id.tv_event_name);
			holder.eventCategoryNameTv = (TextView) vg.findViewById(R.id.tv_event_category_name);
			holder.eventAddressTv = (TextView) vg.findViewById(R.id.tv_event_address);
			holder.eventTimeTv = (TextView) vg.findViewById(R.id.tv_event_time);
			holder.eventWisherCountTv = (TextView) vg.findViewById(R.id.tv_event_wisher_count);
			holder.eventParticipantCountTv = (TextView) vg.findViewById(R.id.tv_event_participant_count);
			holder.eventThumbIv = (NetworkImageView) vg.findViewById(R.id.niv_event_thumb);
			vg.setTag(holder);
			convertView = vg;
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Event evt = mEvents[position];
		holder.eventNameTv.setText(evt.title);
		holder.eventCategoryNameTv.setText(" < " + evt.category_name + " > ");
		holder.eventAddressTv.setText(evt.address);
		holder.eventParticipantCountTv.setText(String.valueOf(evt.participant_count));
		holder.eventWisherCountTv.setText(String.valueOf(evt.wisher_count));
		
		holder.eventTimeTv.setText(evt.getEventTime());
		holder.eventThumbIv.setImageUrl(evt.image, mImageLoader);
		return convertView;
	}

}
