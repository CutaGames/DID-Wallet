package com.tancheng.carbonchain.activities.storage.transfer;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.NoScrollViewPager;
import com.tancheng.carbonchain.utils.dropdowntablayout.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ActivityTransfer extends AppCompatActivity {

    private static final String TAG = "ActivityTransfer";
    private static Boolean DEBUG = true;

    private TabLayout mTableLayout;
    private NoScrollViewPager mViewPager;
    private ImageView mMoreIcon;

    private PopupWindow mMorePopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        findViews();
        inits();
        setOnClickListers();
    }

    private void findViews(){
        mTableLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setCanScroll(false);
        mMoreIcon = findViewById(R.id.custom_action_bar_more_icon);
    }

    private void inits(){
        mViewPager.setCanScroll(false);
        List<Fragment> fragments = new ArrayList<>();
        FragmentTransferDown fragmentTransferDown = new FragmentTransferDown();
        FragmentTransferUp fragmentTransferUp = new FragmentTransferUp();
        FragmentTransferBackup fragmentTransferBackup = new FragmentTransferBackup();
        fragments.add(fragmentTransferDown);
        fragments.add(fragmentTransferUp);
        fragments.add(fragmentTransferBackup);
        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),fragments));
        mTableLayout.setupWithViewPager(mViewPager);
        mTableLayout.getTabAt(0).setText(R.string.transfer_title_down_text);
        mTableLayout.getTabAt(1).setText(R.string.transfer_title_up_text);
        mTableLayout.getTabAt(2).setText(R.string.transfer_title_backup_text);
    }

    private void setOnClickListers(){
        mMoreIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMorePopWindow(view);
            }
        });
    }

    private void showMorePopWindow(View v) {
        final List<String> itemList = new ArrayList<>();
        itemList.add(getString(R.string.clear_missions_current_doing));
        itemList.add(getString(R.string.retry_missions_failure));
        itemList.add(getString(R.string.clear_missions_failure));
        if(mMorePopupWindow != null && mMorePopupWindow.isShowing()) {
            mMorePopupWindow.dismiss();
            mMorePopupWindow = null;
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
                            //事件

                            //关闭窗口
                            mMorePopupWindow.dismiss();

                        }
                    });

                    return view;
                }
            });

            mMorePopupWindow = new PopupWindow(inflate, windowManager.getDefaultDisplay().getWidth()/2, ViewPager.LayoutParams.WRAP_CONTENT);
            mMorePopupWindow.setOutsideTouchable(true);

            int[] location = new int[2];
            v.getLocationOnScreen(location);
            int x = location[0] - mMorePopupWindow.getWidth() / 2;
            int y = location[1] + v.getHeight();
            mMorePopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, x, y);
        }
    }

    @Override
    protected void onDestroy() {
        if(mMorePopupWindow != null){
            mMorePopupWindow.dismiss();
        }
        super.onDestroy();
    }
}
