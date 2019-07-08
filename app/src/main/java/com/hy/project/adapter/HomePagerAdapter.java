package com.hy.project.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class HomePagerAdapter extends FragmentPagerAdapter {


    private static final int NUM_PAGES = 2;
    public static final int MESSAGE_PAGE_TITLE = 0;
    public static final int SETTINGS_PAGE_TITLE = 1;

    private ArrayList<Fragment> listFragment;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
