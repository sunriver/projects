package com.oobe.fragment;

import com.oobe.R;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class LeftListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<String> {
	
	private OnArticleSelectedListener mListener;
	
//	private SimpleCursorAdapter mSimpleCursorAdapter;
	
	private ArrayAdapter mArrayAdapter;
	
	@Override
	public void onAttach(Activity activity) {
		Log.v("Fragment", "LeftListFragment.onAttach()+");
		mListener = (OnArticleSelectedListener) activity;
		super.onAttach(activity);
	}
	
	


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.v("Fragment", "LeftListFragment.onActivityCreated()+");
		super.onActivityCreated(savedInstanceState); 
//        mSimpleCursorAdapter = new SimpleCursorAdapter  
//        (  
//                getActivity(),  
//                android.R.layout.simple_list_item_1,   
//                null,  
//                new String[] {},  
//                new int[] { android.R.id.text1 },  
//                0  
//        ); 
		mArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        setListAdapter(mArrayAdapter);
        getLoaderManager().initLoader(0, null, this);
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v("Fragment", "LeftListFragment.onCreateView()+");
		return  inflater.inflate(android.R.layout.list_content, container);
	}



	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		mListener.onArticleSelected(null);
	}



	/**
	 *
	 */
	public interface OnArticleSelectedListener {
	    public void onArticleSelected(Uri articleUri);
	}




	@Override
	public Loader<String> onCreateLoader(int id, Bundle bundle) {
		Log.v("Fragment", "LeftListFragment.onCreateLoader()+");
		mArrayAdapter.add("Monday");
		mArrayAdapter.add("Tuesday");
		mArrayAdapter.add("Wensday");
		mArrayAdapter.add("Thursday");
		mArrayAdapter.add("Friday");
		return new Loader<String>(getActivity());
	}




	@Override
	public void onLoadFinished(Loader<String> loader, String str) {
		Log.v("Fragment", "LeftListFragment.onLoadFinished()+");
	}




	@Override
	public void onLoaderReset(Loader<String> loader) {
		Log.v("Fragment", "LeftListFragment.onLoaderReset)+");
	}
}
