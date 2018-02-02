package com.hulian.huliantecdemo.refresh;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by wiky_zhang on 2017/9/14.
 */

public class TabAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> tabTitle;

    public TabAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }
    public void setTabTitle(List<String> tabTitle){
        this.tabTitle=tabTitle;

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle.get(position);
    }
}
