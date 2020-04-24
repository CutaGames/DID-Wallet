package com.tancheng.carbonchain.activities.storage.picture;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.NoScrollViewPager;
import com.tancheng.carbonchain.utils.dropdowntablayout.MyViewPagerAdapter;
import com.tancheng.carbonchain.utils.dropdowntablayout.picture.PictureTabLayout;

import java.util.ArrayList;
import java.util.List;

public class ActivityPictureOverView extends AppCompatActivity implements PictureTabLayout.OnTabSelectedListener {

    private PictureTabLayout mTabLayout;
    private List<List<String>> mTitleList;
    private NoScrollViewPager mViewPager;
    private ViewGroup mViewGroup;

    private FragmentPictureTime mFragmentPictureTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_over_view);

        mViewGroup = (FrameLayout) getWindow().getDecorView().findViewById(R.id.root_layout);

        mTitleList = getTitleList();
        mTabLayout = (PictureTabLayout) findViewById(R.id.tab_layout);
        mViewPager = (NoScrollViewPager) findViewById(R.id.view_pager);
        mViewPager.setCanScroll(false);

        mTabLayout.setOnTabSelectedListener(this);

        List<Fragment> testFragmentList = new ArrayList<>();
        mFragmentPictureTime = new FragmentPictureTime(mViewGroup);
        testFragmentList.add(mFragmentPictureTime);
        testFragmentList.add(new FragmentPictureAlbum());

        MyViewPagerAdapter testAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),testFragmentList);
        mViewPager.setAdapter(testAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.setUpTitle(mTitleList);

        mTabLayout.addOnTabSelectedListener(mOnTabSelectedListener);
        mTabLayout.changeTabColor(0);
    }

    private TabLayout.OnTabSelectedListener mOnTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mTabLayout.changeTabColor(tab.getPosition());
            mFragmentPictureTime.removeBottomOperationsIfExits();
            mFragmentPictureTime.cancelLongClickMode();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {}

        @Override
        public void onTabReselected(TabLayout.Tab tab) {}
    };

    @Override
    public void selected(int tabPosition, int listPosition) {
        //在这里可以执行显示数据的修改操作  tabPosition:第几个tab  listPosition:下拉列表中第几个选项
        //Toast.makeText(this, "点击了TAB"+ tabPosition + "，Position"+ listPosition, Toast.LENGTH_SHORT).show();
        if(tabPosition == 0){
            mFragmentPictureTime.selectTime(listPosition);
        }
    }

    private List<List<String>> getTitleList(){
        //左侧 筛选
        List<List<String>> tabList = new ArrayList<>();
        List<String> subList = new ArrayList<>();
        subList.add(getString(R.string.drop_down_tab_layout_item_text_year));
        subList.add(getString(R.string.drop_down_tab_layout_item_text_month));
        subList.add(getString(R.string.drop_down_tab_layout_item_text_day));
        tabList.add(subList);

        //右侧 专辑
        List<String> tab1List = new ArrayList<>();
        tab1List.add(getString(R.string.drop_down_tab_layout_item_text_album));
        tabList.add(tab1List);

        return tabList;
    }

    @Override
    public void onBackPressed() {
        if(mFragmentPictureTime.doBackPressed()){

        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        mTabLayout.dismissIfNeed();
        super.onDestroy();
    }
}
