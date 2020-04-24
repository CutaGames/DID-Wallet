package com.tancheng.carbonchain.activities.main;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.smarthome.ActivitySmartHomeAddDevice;
import com.tancheng.carbonchain.activities.smarthome.ActivitySmartHomeSamba;
import com.tancheng.carbonchain.activities.smarthome.FragmentSmartHomeAll;
import com.tancheng.carbonchain.activities.smarthome.FragmentSmartHomeEncrypt;
import com.tancheng.carbonchain.activities.smarthome.FragmentSmartHomeNoEncrypt;
import com.tancheng.carbonchain.activities.asset.NoScrollViewPager;
import com.tancheng.carbonchain.utils.dropdowntablayout.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gll on 2020/2/8.
 */

public class FragmentMainSmartHome extends Fragment {

    private static final String TAG = "FragmentMainSmartHome";
    private TabLayout mTableLayout;
    private NoScrollViewPager mViewPager;
    private ImageView mAddImageView;
    private TextView mTextViewSamba;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_smart_home, null);
        findViews(view);
        inits();
        setOnClickListeners();
        return view;
    }

    private void findViews(View view){
        mTableLayout = view.findViewById(R.id.tab_layout);
        mViewPager = view.findViewById(R.id.view_pager);
        mViewPager.setCanScroll(false);
        mAddImageView = view.findViewById(R.id.smart_home_add_device_icon);
        mTextViewSamba = view.findViewById(R.id.custom_action_bar_samba);
    }

    private void inits() {
        mViewPager.setCanScroll(false);
        List<Fragment> fragments = new ArrayList<>();
        FragmentSmartHomeAll fragmentSmartHomeAll = new FragmentSmartHomeAll();
        FragmentSmartHomeEncrypt fragmentSmartHomeEncrypt = new FragmentSmartHomeEncrypt();
        FragmentSmartHomeNoEncrypt fragmentSmartHomeNoEncrypt = new FragmentSmartHomeNoEncrypt();
        fragments.add(fragmentSmartHomeAll);
        fragments.add(fragmentSmartHomeEncrypt);
        fragments.add(fragmentSmartHomeNoEncrypt);
        //mViewPager.setAdapter(new MyViewPagerAdapter(getActivity().getSupportFragmentManager(), fragments));
        mViewPager.setAdapter(new MyViewPagerAdapter(getChildFragmentManager(), fragments));
        mTableLayout.setupWithViewPager(mViewPager);
        mTableLayout.getTabAt(0).setText(R.string.smart_home_all_text);
        mTableLayout.getTabAt(1).setText(R.string.smart_home_encrypt_text);
        mTableLayout.getTabAt(2).setText(R.string.smart_home_no_encrypt_text);
    }

    private void setOnClickListeners(){
        mAddImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getContext(), ActivitySmartHomeAddDevice.class);
                getContext().startActivity(intent);
            }
        });

        mTextViewSamba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getContext(), ActivitySmartHomeSamba.class);
                getContext().startActivity(intent);
            }
        });
    }
}
