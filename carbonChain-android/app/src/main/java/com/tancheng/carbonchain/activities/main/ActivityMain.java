package com.tancheng.carbonchain.activities.main;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.tancheng.carbonchain.Helper.BottomNavigationViewHelper;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.NoScrollViewPager;
import com.tancheng.carbonchain.activities.asset.wallet.service.SyncService;
import com.tancheng.carbonchain.activities.asset.wallet.ui.fragment.AssetFragment;
import com.tancheng.carbonchain.activities.base.BaseActivity;
import com.tancheng.carbonchain.utils.PermissionsConstans;

import butterknife.BindView;

public class ActivityMain extends BaseActivity {

    private static final String TAG = "ActivityMain";

    @BindView(R.id.main_viewpager)
    NoScrollViewPager mMainViewPager;
    @BindView(R.id.main_bottom_navigation)
    BottomNavigationView mMainBottomNavigation;

    private MenuItem menuItem;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            resetToDefaultIcon();//重置到默认不选中图片
            switch (item.getItemId()) {
                case R.id.main_navigation_smart_home:
                    mMainViewPager.setCurrentItem(0, false);
                    item.setIcon(R.mipmap.main_navigation_smart_home_true);
                    break;
                case R.id.main_navigation_storage:
                    mMainViewPager.setCurrentItem(1, false);
                    item.setIcon(R.mipmap.main_navigation_storage_true);
                    break;
                case R.id.main_navigation_find:
                    mMainViewPager.setCurrentItem(2, false);
                    item.setIcon(R.mipmap.main_navigation_find_true);
                    break;
                case R.id.main_navigation_asset:
                    mMainViewPager.setCurrentItem(3, false);
                    item.setIcon(R.mipmap.main_navigation_asset_true);
                    break;
                case R.id.main_navigation_mine:
                    mMainViewPager.setCurrentItem(4, false);
                    item.setIcon(R.mipmap.main_navigation_mine_true);
                    break;
            }
            return false;
        }
    };

    private void resetToDefaultIcon() {
        mMainBottomNavigation.getMenu().findItem(R.id.main_navigation_smart_home).setIcon(R.mipmap.main_navigation_smart_home_false);
        mMainBottomNavigation.getMenu().findItem(R.id.main_navigation_storage).setIcon(R.mipmap.main_navigation_storage_false);
        mMainBottomNavigation.getMenu().findItem(R.id.main_navigation_find).setIcon(R.mipmap.main_navigation_find_false);
        mMainBottomNavigation.getMenu().findItem(R.id.main_navigation_asset).setIcon(R.mipmap.main_navigation_asset_false);
        mMainBottomNavigation.getMenu().findItem(R.id.main_navigation_mine).setIcon(R.mipmap.main_navigation_mine_false);
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            Log.d(TAG, "position=" + position);
            if (menuItem != null) {
                menuItem.setChecked(false);
            } else {
                mMainBottomNavigation.getMenu().getItem(0).setChecked(false);
            }
            menuItem = mMainBottomNavigation.getMenu().getItem(position);
            menuItem.setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initDatas() {

        //
        Thread thread = new Thread(new SyncService());
        thread.start();

        mMainViewPager.setCanScroll(false);
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        BottomNavigationViewHelper.disableShiftMode(mMainBottomNavigation);
        BottomNavigationViewHelper.disableItemScale(mMainBottomNavigation);
        mMainBottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mMainBottomNavigation.setItemIconTintList(null);
        mMainViewPager.addOnPageChangeListener(mOnPageChangeListener);
        mMainViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        mMainViewPager.setOffscreenPageLimit(5);
        setupViewPager();
        checkPermission();
    }

    @Override
    public void configViews() {
        //mMainBottomNavigation.setSelectedItemId(R.id.main_navigation_smart_home);
        //mMainBottomNavigation.setSelectedItemId(R.id.main_navigation_storage);
        //mMainBottomNavigation.setSelectedItemId(R.id.main_navigation_find);
        mMainBottomNavigation.setSelectedItemId(R.id.main_navigation_asset);
        //mMainBottomNavigation.setSelectedItemId(R.id.main_navigation_mine);
    }

    /**
     * 检查权限
     */
    private void checkPermission() {
        if (!hasPermission(Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)) {

            requestPermission(PermissionsConstans.INSEPCT_CAMERA,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS);
            LogUtils.e("无权限.....");
        } else {
            doCameraPermission();
        }
    }


    @Override
    protected void doCameraPermission() {
        super.doCameraPermission();

    }

    private void setupViewPager() {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());

        FragmentMainSmartHome fragmentMainSmartHome = new FragmentMainSmartHome();
        FragmentMainStorage fragmentMainStorage = new FragmentMainStorage();
        FragmentMainFind fragmentMainFind = new FragmentMainFind();
        AssetFragment fragmentMainAsset = new AssetFragment();
        FragmentMainMine fragmentMainMine = new FragmentMainMine();

        adapter.addFragment(fragmentMainSmartHome);
        adapter.addFragment(fragmentMainStorage);
        adapter.addFragment(fragmentMainFind);
        adapter.addFragment(fragmentMainAsset);
        adapter.addFragment(fragmentMainMine);

        mMainViewPager.setAdapter(adapter);
    }

}
