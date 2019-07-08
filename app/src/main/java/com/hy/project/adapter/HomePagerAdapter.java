package com.hy.project.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hy.project.fragment.HomeFragment;
import com.hy.project.fragment.NotificationFragment;
import com.hy.project.fragment.SettingsFragment;

import java.util.ArrayList;

public class HomePagerAdapter extends FragmentPagerAdapter {


    private static final int NUM_PAGES = 3;
    public static final int MESSAGE_PAGE_TITLE = 0;
    public static final int SETTINGS_PAGE_TITLE = 1;
    public static final int NOTIFICATION_PAGE_TITLE = 2;

    private ArrayList<Fragment> listFragment = new ArrayList<>();

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
        listFragment.add(HomeFragment.getInstance(null));
        listFragment.add(SettingsFragment.getInstance(null));
        listFragment.add(NotificationFragment.getInstance(null));
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
