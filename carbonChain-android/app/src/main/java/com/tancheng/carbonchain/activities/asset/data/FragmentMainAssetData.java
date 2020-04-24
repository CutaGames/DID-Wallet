package com.tancheng.carbonchain.activities.asset.data;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.base.BaseFragment;
import com.tancheng.carbonchain.activities.asset.NoScrollViewPager;
import com.tancheng.carbonchain.utils.dropdowntablayout.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * Created by gll on 2020/2/8.
 */

public class FragmentMainAssetData extends BaseFragment {

    private static final String TAG = "FragmentMainAssetData";
    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoadedOnce;

    private TabLayout mTableLayout;

    NoScrollViewPager mViewPager;
    private ImageView mAddDataIcon;

    private void inits(){
        List<Fragment> fragments = new ArrayList<>();
        FragmentAssetDataHealth fragmentAssetDataHealth = new FragmentAssetDataHealth();
        FragmentAssetDataOriginal fragmentAssetDataOriginal = new FragmentAssetDataOriginal();
        fragments.add(fragmentAssetDataHealth);
        fragments.add(fragmentAssetDataOriginal);
        mViewPager.setAdapter(new MyViewPagerAdapter(getChildFragmentManager(),fragments));
        mTableLayout.setupWithViewPager(mViewPager);
        mTableLayout.getTabAt(0).setText(R.string.asset_data_health_text);
        mTableLayout.getTabAt(1).setText(R.string.asset_data_original_text);
    }

    @Override
    public void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        //填充各控件的数据
        mHasLoadedOnce = true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main_asset_data;
    }

    @Override
    public void attachView() {
        mTableLayout = parentView.findViewById(R.id.tab_layout);
        mViewPager = parentView.findViewById(R.id.view_pager);
        mViewPager.setCanScroll(false);
        mAddDataIcon = parentView.findViewById(R.id.iv_add_data_icon);
    }

    @Override
    public void initDatas() {
        Bundle bundle = getArguments();
//        String args = bundle.getString("agrs1");

        List<Fragment> fragments = new ArrayList<>();
        FragmentAssetDataHealth fragmentAssetDataHealth = new FragmentAssetDataHealth();
        FragmentAssetDataOriginal fragmentAssetDataOriginal = new FragmentAssetDataOriginal();
        fragments.add(fragmentAssetDataHealth);
        fragments.add(fragmentAssetDataOriginal);
        mViewPager.setAdapter(new MyViewPagerAdapter(getChildFragmentManager(),fragments));
        mTableLayout.setupWithViewPager(mViewPager);
        mTableLayout.getTabAt(0).setText(R.string.asset_data_health_text);
        mTableLayout.getTabAt(1).setText(R.string.asset_data_original_text);
    }

    @Override
    public void configViews() {

    }

    @OnClick(R.id.iv_add_data_icon)
    public void myClickListers(View view){
        switch (view.getId()){
            case R.id.iv_add_data_icon:
                Intent intent = new Intent();
                intent.setClass(getContext(), ActivityAssetDataAdd.class);
                startActivity(intent);
                break;
        }
    }

    public static FragmentMainAssetData newInstance(String param1) {
        FragmentMainAssetData fragment = new FragmentMainAssetData();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }
}
