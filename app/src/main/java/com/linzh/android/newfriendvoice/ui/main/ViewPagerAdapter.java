package com.linzh.android.newfriendvoice.ui.main;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by linzh on 2018/3/21.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String[] tabTitles = {"手语语音模式", "文本语音模式"};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {

        }
        return null;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    // ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
