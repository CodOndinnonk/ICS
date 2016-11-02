package com.example.vakery.ics;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {


    public PagerAdapter(FragmentManager mgr) {
        super(mgr);
    }


    @Override
    public int getCount() {
        return(7);
    }


    @Override
    public Fragment getItem(int position) {
        return(ScheduleListFragment.newInstance(position));
    }



}