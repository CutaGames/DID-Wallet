package com.tancheng.carbonchain.activities.storage.mp3;

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

import java.util.ArrayList;
import java.util.List;

public class ActivityMp3OverView extends AppCompatActivity {

    private static final String TAG = "ActivityMp3OverView";
    private static Boolean DEBUG = true;

    private TabLayout mTabLayout;
    private NoScrollViewPager mViewPager;
    private ImageView mSortIcon;
    private ViewGroup mViewGroup;

    private PopupWindow mSortPopupWindow;
    private FragmentMp3All mFragmentMp3All;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3_over_view);
        findViews();
        inits();
        setOnClickListers();
    }

    private void findViews(){
        mViewGroup = (FrameLayout) getWindow().getDecorView().findViewById(R.id.root_layout);
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setCanScroll(false);
        mSortIcon = findViewById(R.id.custom_action_bar_sort);
    }

    private void inits(){
        mViewPager.setCanScroll(false);
        List<Fragment> fragments = new ArrayList<>();
        mFragmentMp3All = new FragmentMp3All(mViewGroup);
        FragmentMp3Album fragmentMp3Album = new FragmentMp3Album();
        fragments.add(mFragmentMp3All);
        fragments.add(fragmentMp3Album);
        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),fragments));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText(R.string.mp3_tab_all_text);
        mTabLayout.getTabAt(1).setText(R.string.mp3_tab_album_text);

        mTabLayout.addOnTabSelectedListener(mOnTabSelectedListener);
    }

    private void setOnClickListers(){
        mSortIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMorePopWindow(view);
            }
        });
    }

    private void showMorePopWindow(View v) {
        final List<String> itemList = new ArrayList<>();
        itemList.add(getString(R.string.mp3_sort_by_name));
        itemList.add(getString(R.string.mp3_sort_by_time));
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
                            //点击事件

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

    @Override
    protected void onDestroy() {
        if(mSortPopupWindow != null){
            mSortPopupWindow.dismiss();
            mSortPopupWindow = null;
        }
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        if(mFragmentMp3All.doBackPressed()){

        }else {
            super.onBackPressed();
        }
    }

    private TabLayout.OnTabSelectedListener mOnTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mFragmentMp3All.removeBottomOperationsIfExits();
            mFragmentMp3All.cancelLongClickMode();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {}

        @Override
        public void onTabReselected(TabLayout.Tab tab) {}
    };
}
