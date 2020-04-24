package com.tancheng.carbonchain.activities.main;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.blankj.utilcode.util.ToastUtils;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.ui.WalletChooseActivity;
import com.tancheng.carbonchain.activities.asset.wallet.ui.WalletManageActivity;
import com.tancheng.carbonchain.activities.asset.wallet.ui.fragment.AssetHotWalletFragment;
import com.tancheng.carbonchain.activities.base.BaseFragment;
import com.tancheng.carbonchain.activities.asset.MyPagerAdapter;
import com.tancheng.carbonchain.activities.asset.NoScrollViewPager;
import com.tancheng.carbonchain.activities.asset.data.FragmentMainAssetData;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.ETHWallet;
import com.tancheng.carbonchain.activities.asset.wallet.ui.adapter.DrawerWalletAdapter;
import com.tancheng.carbonchain.db.gen.WalletDaoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gll on 2020/2/8.
 * 钱包主界面
 */
public class FragmentMainAsset extends BaseFragment {

    @BindView(R.id.asset_tab_layout_digit_data)
    TabLayout assetTabLayoutDigitData;
    @BindView(R.id.iv_my_wallet)
    ImageView ivMyWallet;
    @BindView(R.id.asset_tab_top)
    LinearLayout assetTabTop;
    @BindView(R.id.asset_digit_data_viewpager)
    NoScrollViewPager assetDigitDataViewpager;
    @BindView(R.id.lly_create_wallet)
    LinearLayout llyCreateWallet;
    @BindView(R.id.lly_hard_wallet)
    LinearLayout llyHardWallet;

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private MyPagerAdapter myPagerAdapter;
    private TabLayout mTableLayout;
    private NoScrollViewPager mViewPager;
    private ImageView ivMywallet;

    private DrawerLayout drawer;
    private ListView lvWallet;
    private List<ETHWallet> ethWallets;
    private boolean isInitWallet = false;//钱包是否已初始化

    AssetHotWalletFragment assetHotWalletFragment;
    FragmentMainAssetData fragmentMainAssetData;
    DrawerWalletAdapter drawerWalletAdapter;

    private static final int QRCODE_SCANNER_REQUEST = 1100;
    private static final int CREATE_WALLET_REQUEST = 1101;
    public static boolean refresh = true;

    @Override
    public void lazyLoad() {
        //初始化当前钱包
//        ETHWallet curWallet = null;//当前钱包
//        for (ETHWallet wallet : ethWallets) {
//            if (wallet.isCurrent()) {
//                curWallet = wallet;
//                break;
//            }
//        }
//        assetHotWalletFragment.notifyDataChange(curWallet);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main_asset;
    }

    @Override
    public void attachView() {
        mTableLayout = parentView.findViewById(R.id.asset_tab_layout_digit_data);
        mViewPager = parentView.findViewById(R.id.asset_digit_data_viewpager);
        ivMywallet = parentView.findViewById(R.id.iv_my_wallet);
        drawer = parentView.findViewById(R.id.drawer);
        lvWallet = parentView.findViewById(R.id.lv_wallet);
        mViewPager.setCanScroll(false);
    }

    @Override
    public void initDatas() {
        String tabDigit = getString(R.string.asset_tab_layout_digit_text);
        String tabData = getString(R.string.asset_tab_layout_data_text);
        titles.clear();
        assetHotWalletFragment = AssetHotWalletFragment.newInstance(tabDigit);
        fragmentMainAssetData = FragmentMainAssetData.newInstance(tabData);

        fragments.add(assetHotWalletFragment);
        fragments.add(fragmentMainAssetData);
        titles.add(tabDigit);
        titles.add(tabData);

        if (myPagerAdapter == null) {
            myPagerAdapter = new MyPagerAdapter(getChildFragmentManager(), getActivity(), fragments, titles);
        }
        mViewPager.setAdapter(myPagerAdapter);
        mTableLayout.setupWithViewPager(mViewPager);//此方法就是让tablayout和ViewPager联动
        initWallets();
    }

    @Override
    public void configViews() {

        mTableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 1) {
                    ivMywallet.setVisibility(View.INVISIBLE);
                } else {
                    ivMywallet.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @OnClick({R.id.iv_my_wallet, R.id.lly_create_wallet, R.id.lly_hard_wallet, R.id.lly_manage_wallet})
    public void myClick(View view) {
        switch (view.getId()) {
            case R.id.iv_my_wallet://打开|关闭菜单
                openOrCloseDrawerLayout();
                break;
            case R.id.lly_create_wallet://创建钱包
                Intent intent = new Intent(mContext, WalletChooseActivity.class);
                startActivityForResult(intent, CREATE_WALLET_REQUEST);
                openOrCloseDrawerLayout();
                break;
            case R.id.lly_hard_wallet://硬件钱包
                ToastUtils.showShort("硬件钱包");
                openOrCloseDrawerLayout();
                break;
            case R.id.lly_manage_wallet://钱包管理
                startActivity(new Intent(mContext, WalletManageActivity.class));
                openOrCloseDrawerLayout();
                break;
        }
    }

    // 打开关闭DrawerLayout
    private void openOrCloseDrawerLayout() {
        boolean drawerOpen = drawer.isDrawerOpen(Gravity.END);
        if (drawerOpen) {
            drawer.closeDrawer(Gravity.END);
        } else {
            drawer.openDrawer(Gravity.END);
        }
    }

    public void initWallets() {
//        //设置钱包菜单
//        WalletDaoUtils walletDaoUtils = WalletDaoUtils.getIntence();
//        ethWallets = walletDaoUtils.getWallets();
//        drawerWalletAdapter = new DrawerWalletAdapter(getContext(), ethWallets, R.layout.list_item_drawer_property_wallet);
//        lvWallet.setAdapter(drawerWalletAdapter);
//        lvWallet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                drawerWalletAdapter.setCurrentWalletPosition(position);
//                ETHWallet wallet = drawerWalletAdapter.getDatas().get(position);
//                assetHotWalletFragment.notifyDataChange(wallet);
//
//                WalletDaoUtils walletDaoUtils = WalletDaoUtils.getIntence();
//                walletDaoUtils.setCurrentWallet(wallet.getId());
//                wallet.setCurrent(true);
//                drawerWalletAdapter.notifyDataSetChanged();
//                openOrCloseDrawerLayout();
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (refresh) {
//            //设置钱包
//            WalletDaoUtils walletDaoUtils = WalletDaoUtils.getIntence();
//            List<ETHWallet> datas = walletDaoUtils.getWallets();
//            ethWallets.clear();
//            ethWallets.addAll(datas);
//            drawerWalletAdapter.notifyDataSetChanged();
//
//            ETHWallet currentWallet = walletDaoUtils.getCurrentWallet();
//            assetHotWalletFragment.notifyDataChange(currentWallet);
//            refresh = false;
//        }
    }
}
