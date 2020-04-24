package com.tancheng.carbonchain.activities.asset.wallet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.TextWidthColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.ui.adapter.DeriveKeystorePageFragmentAdapter;
import com.tancheng.carbonchain.activities.asset.wallet.ui.fragment.ExportKeystoreQRCodeFragment;
import com.tancheng.carbonchain.activities.asset.wallet.ui.fragment.ExportKeystoreStringFragment;
import com.tancheng.carbonchain.activities.base.BaseActivity;
import com.tancheng.carbonchain.activities.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by tc_collin on 2020/4/7 15:22
 * Description: 导出keystore
 * version: 1.0
 */
public class WalletExportKeystoreActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.indicator_view)
    ScrollIndicatorView indicatorView;
    @BindView(R.id.vp_load_wallet)
    ViewPager vpLoadWallet;

    private List<BaseFragment> fragmentList = new ArrayList<>();
    private IndicatorViewPager indicatorViewPager;

    private DeriveKeystorePageFragmentAdapter deriveKeystorePageFragmentAdapter;
    private ExportKeystoreStringFragment deriveKeystoreStringFragment;
    private ExportKeystoreQRCodeFragment deriveKeystoreQRCodeFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_import;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("导出Keystore");
        Intent intent = getIntent();
        String walletKeystore = intent.getStringExtra("keystore");
        deriveKeystoreStringFragment = new ExportKeystoreStringFragment();
        deriveKeystoreQRCodeFragment = new ExportKeystoreQRCodeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keystore", walletKeystore);//这里的values就是我们要传的值
        deriveKeystoreStringFragment.setArguments(bundle);
        deriveKeystoreQRCodeFragment.setArguments(bundle);
        fragmentList.add(deriveKeystoreStringFragment);
        fragmentList.add(deriveKeystoreQRCodeFragment);
    }

    @Override
    public void configViews() {
        int[] titles = {R.string.derive_with_keystore, R.string.derive_with_qrcode, R.string.derive_with_qrcode};
        indicatorView.setSplitAuto(true);
        indicatorView.setOnTransitionListener(new OnTransitionTextListener()
                .setColor(getResources().getColor(R.color.transfer_advanced_setting_help_text_color), getResources().getColor(R.color.colorPrimary))
                .setSize(14, 14));
        indicatorView.setScrollBar(new TextWidthColorBar(this, indicatorView, getResources().getColor(R.color.transfer_advanced_setting_help_text_color), ConvertUtils.dp2px(2)));
        indicatorView.setScrollBarSize(50);
        indicatorViewPager = new IndicatorViewPager(indicatorView, vpLoadWallet);
        deriveKeystorePageFragmentAdapter = new DeriveKeystorePageFragmentAdapter(this, getSupportFragmentManager(), fragmentList,titles);
        indicatorViewPager.setAdapter(deriveKeystorePageFragmentAdapter);
        indicatorViewPager.setCurrentItem(0, false);
        vpLoadWallet.setOffscreenPageLimit(4);
    }

    @OnClick(R.id.iv_title_left_back)
    public void myClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left_back:
                finish();
                break;
        }
    }
}
