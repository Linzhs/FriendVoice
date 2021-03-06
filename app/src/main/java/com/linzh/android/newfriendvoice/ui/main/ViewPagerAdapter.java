package com.linzh.android.newfriendvoice.ui.main;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.linzh.android.newfriendvoice.ui.main.fragment.GestureTabFragment;
import com.linzh.android.newfriendvoice.ui.main.fragment.TextTabFragment;
import com.linzh.android.newfriendvoice.ui.main.fragment.VoiceToTextFragment;

/**
 * Created by linzh on 2018/3/21.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String[] tabTitles = {"手语语音模式", "文本语音模式", "语音文本模式"};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new TextTabFragment();
        } else if (position == 2) {
            return new VoiceToTextFragment();
        }
        return new GestureTabFragment();
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
