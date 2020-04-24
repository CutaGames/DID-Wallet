package com.tancheng.carbonchain.activities.asset.record;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.NoScrollViewPager;
import com.tancheng.carbonchain.utils.dropdowntablayout.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ActivityBusinessRecord extends AppCompatActivity {


    private TabLayout mTableLayout;
    private NoScrollViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        findViews();
        inits();
        setOnClickListers();
    }

    private void findViews(){
        mTableLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setCanScroll(false);
    }

    private void inits(){
        List<Fragment> fragments = new ArrayList<>();
        FragmentBusinessRecordAll fragmentBusinessRecordAll = new FragmentBusinessRecordAll();
        FragmentBusinessRecordReceive fragmentBusinessRecordReceive = new FragmentBusinessRecordReceive();
        FragmentBusinessRecordTrade fragmentBusinessRecordTrade = new FragmentBusinessRecordTrade();
        fragments.add(fragmentBusinessRecordAll);
        fragments.add(fragmentBusinessRecordReceive);
        fragments.add(fragmentBusinessRecordTrade);
        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),fragments));
        mTableLayout.setupWithViewPager(mViewPager);
        mTableLayout.getTabAt(0).setText(R.string.asset_recode_all);
        mTableLayout.getTabAt(1).setText(R.string.asset_recode_receive);
        mTableLayout.getTabAt(2).setText(R.string.asset_recode_trade);
    }

    private void setOnClickListers(){

    }
}
