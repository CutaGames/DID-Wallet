package com.tancheng.carbonchain.utils.dropdowntablayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by jiaojing on 2017/12/12.
 */

public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> testFragmentList;
    public MyViewPagerAdapter(FragmentManager fm, List<Fragment> testFragmentList) {
        super(fm);
        this.testFragmentList = testFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return testFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return testFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
