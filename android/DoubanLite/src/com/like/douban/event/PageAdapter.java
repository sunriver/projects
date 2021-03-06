/**
 * 
 */

package com.like.douban.event;

import java.util.ArrayList;

import com.like.R;
import com.viewpagerindicator.IconPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter implements IconPagerAdapter  {
	
    protected static final int[] ICONS = new int[] {
        R.drawable.ic_event_private,
        R.drawable.ic_event_private
    };

    private final ArrayList<Fragment> mFragments = new ArrayList<Fragment>();

    public PageAdapter(FragmentManager manager) {
        super(manager);
    }

    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }


	@Override
	public int getIconResId(int index) {
	     return ICONS[index % ICONS.length];
	}

}
