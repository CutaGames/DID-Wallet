package com.tancheng.carbonchain.activities.storage.video;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.NoScrollViewPager;
import com.tancheng.carbonchain.utils.dropdowntablayout.MyViewPagerAdapter;
import com.tancheng.carbonchain.utils.dropdowntablayout.video.VideoTabLayout;

import java.util.ArrayList;
import java.util.List;

public class ActivityVideoOverView extends AppCompatActivity implements VideoTabLayout.OnTabSelectedListener {

    private VideoTabLayout mTabLayout;
    private List<List<String>> mTitleList;
    private NoScrollViewPager mViewPager;
    private ViewGroup mViewGroup;

    private FragmentVideoTime mFragmentVideoTime;
    private ImageView mSortView;
    private PopupWindow mSortPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_over_view);

        findViews();
        inits();
        setOnClickListeners();
    }

    private void findViews(){
        mViewGroup = (FrameLayout) getWindow().getDecorView().findViewById(R.id.root_layout);
        mTabLayout = (VideoTabLayout) findViewById(R.id.tab_layout);
        mViewPager = (NoScrollViewPager) findViewById(R.id.view_pager);
        mViewPager.setCanScroll(false);
        mSortView= (ImageView)findViewById(R.id.custom_action_bar_sort);
    }

    private void inits(){
        mTabLayout.setOnTabSelectedListener(this);
        List<Fragment> testFragmentList = new ArrayList<>();
        mFragmentVideoTime = new FragmentVideoTime(mViewGroup);
        testFragmentList.add(mFragmentVideoTime);
        testFragmentList.add(new FragmentVideoAlbum());

        MyViewPagerAdapter videoFragmentAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),testFragmentList);
        mViewPager.setAdapter(videoFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mTitleList = getTitleList();
        mTabLayout.setUpTitle(mTitleList);

        mTabLayout.addOnTabSelectedListener(mOnTabSelectedListener);
        mTabLayout.changeTabColor(0);
    }

    private void setOnClickListeners(){
        mSortView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSortPopWindow(view);
            }
        });
    }

    private void showSortPopWindow(View v) {
        final List<String> itemList = new ArrayList<>();
        itemList.add(getString(R.string.video_sort_text_name));
        itemList.add(getString(R.string.video_sort_text_time));
        itemList.add(getString(R.string.video_sort_text_size));
        if(mSortPopupWindow != null && mSortPopupWindow.isShowing()) {
            mSortPopupWindow.dismiss();
            mSortPopupWindow = null;
        }else {
            WindowManager windowManager  = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            View inflate = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_tab_layout_popup_window, null);
            ListView listview = (ListView) inflate.findViewById(R.id.listview);

            listview.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return itemList.size();
                }

                @Override
                public Object getItem(int position) {
                    return null;
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {
                    View view;
                    if(convertView == null) {
                        view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_tab_layout_item_dance_category,null);
                    }else {
                        view = convertView;
                    }
                    final TextView name= (TextView) view.findViewById(R.id.dance_name);

                    name.setText(itemList.get(position));

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //排序事件

                            //关闭窗口
                            mSortPopupWindow.dismiss();

                        }
                    });

                    return view;
                }
            });

            mSortPopupWindow = new PopupWindow(inflate, windowManager.getDefaultDisplay().getWidth()/2, ViewPager.LayoutParams.WRAP_CONTENT);
            mSortPopupWindow.setOutsideTouchable(true);

            int[] location = new int[2];
            v.getLocationOnScreen(location);
            int x = location[0] - mSortPopupWindow.getWidth() / 2;
            int y = location[1] + v.getHeight();
            mSortPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, x, y);
        }
    }

    private TabLayout.OnTabSelectedListener mOnTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mTabLayout.changeTabColor(tab.getPosition());
            mFragmentVideoTime.removeBottomOperationsIfExits();
            mFragmentVideoTime.cancelLongClickMode();
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
            mFragmentVideoTime.selectTime(listPosition);
        }
    }

    private List<List<String>> getTitleList(){
        //左侧 筛选
        List<List<String>> tabList = new ArrayList<>();
        List<String> subList = new ArrayList<>();
        subList.add(getString(R.string.drop_down_tab_layout_item_text_less_1));
        subList.add(getString(R.string.drop_down_tab_layout_item_text_less_10));
        subList.add(getString(R.string.drop_down_tab_layout_item_text_less_30));
        subList.add(getString(R.string.drop_down_tab_layout_item_text_less_60));
        subList.add(getString(R.string.drop_down_tab_layout_item_text_big_60));
        tabList.add(subList);

        //右侧 专辑
        List<String> tab1List = new ArrayList<>();
        tab1List.add(getString(R.string.drop_down_tab_layout_item_text_album));
        tabList.add(tab1List);

        return tabList;
    }

    @Override
    public void onBackPressed() {
        if(mFragmentVideoTime.doBackPressed()){

        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if(mSortPopupWindow != null){
            mSortPopupWindow.dismiss();
        }
        mTabLayout.dismissIfNeed();
        super.onDestroy();
    }
}
