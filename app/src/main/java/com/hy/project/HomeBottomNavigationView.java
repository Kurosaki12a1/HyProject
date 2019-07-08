package com.hy.project;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hy.project.adapter.HomePagerAdapter;

public class HomeBottomNavigationView extends BottomNavigationView implements BottomNavigationView.OnNavigationItemSelectedListener {
    public ViewPager viewPager;

    public HomeBottomNavigationView(Context context) {
        super(context);
        init();
    }

    public HomeBottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HomeBottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        inflateMenu(R.menu.bottom_navigation_items);
        setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (viewPager == null) {
            return false;
        }
        switch (menuItem.getItemId()) {
            case R.id.menu_chat:
                viewPager.setCurrentItem(HomePagerAdapter.MESSAGE_PAGE_TITLE);
                break;
            case R.id.menu_option:
                viewPager.setCurrentItem(HomePagerAdapter.SETTINGS_PAGE_TITLE);
                break;
            case R.id.menu_notification:
                viewPager.setCurrentItem(HomePagerAdapter.NOTIFICATION_PAGE_TITLE);
                break;
        }
        return true;
    }
}
