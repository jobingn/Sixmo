package com.sixmogroup.app.sixmo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sixmogroup.app.sixmo.fragments.admin.ActiveEventsFragment;
import com.sixmogroup.app.sixmo.fragments.admin.AllRequestsFragment;

/**
 * Created by Jobin on Nov 06.
 */
public class EventPagerAdapter extends FragmentStatePagerAdapter {
    public EventPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0) {
            return new ActiveEventsFragment();
        }else {
            return new AllRequestsFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
