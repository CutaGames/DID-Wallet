package com.tancheng.carbonchain.activities.asset.wallet.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.TextWidthColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;
import com.tancheng.carbonchain.activities.asset.wallet.ui.adapter.LoadWalletPageFragmentAdapter;
import com.tancheng.carbonchain.activities.asset.wallet.ui.fragment.ImportKeystoreFragment;
import com.tancheng.carbonchain.activities.asset.wallet.ui.fragment.ImportMnemonicFragment;
import com.tancheng.carbonchain.activities.asset.wallet.ui.fragment.ImportPrivateKeyFragment;
import com.tancheng.carbonchain.activities.base.BaseActivity;
import com.tancheng.carbonchain.activities.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by collin on 2020/4/3 10:16
 * Description: 导入钱包
 * version: 1.0
 */
public class WalletImportActivity extends BaseActivity {
    private static final String WALLET_TYPE = "walletType";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.indicator_view)
    ScrollIndicatorView indicatorView;
    @BindView(R.id.vp_load_wallet)
    ViewPager vpLoadWallet;

    boolean firstAccount;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private LoadWalletPageFragmentAdapter loadWalletPageFragmentAdapter;
    private IndicatorViewPager indicatorViewPager;
    private WalletType walletType;

    /**
     * 打开界面
     *
     * @param walletTypeId
     */
    public static void startThisAct(Context context, int walletTypeId) {
        Intent intent = new Intent(context, WalletImportActivity.class);
        intent.putExtra(WALLET_TYPE, walletTypeId);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_import;
    }

    @Override
    public void initDatas() {
        Intent intent = mContext.getIntent();
        int typeId = intent.getIntExtra(WALLET_TYPE, -1);
        walletType = WalletType.of(typeId);
        tvTitle.setText("导入" + walletType.getName() + "钱包");
        firstAccount = getIntent().getBooleanExtra("first_account", false);
    }

    @Override
    public void configViews() {
        String[] tabNams = {"助记词", "keytroe", "私钥"};
        switch (walletType) {
            case ETH:
                fragmentList.add(ImportMnemonicFragment.getInstance(walletType.getWalletType()));
                fragmentList.add(ImportKeystoreFragment.getInstance(walletType.getWalletType()));
                fragmentList.add(ImportPrivateKeyFragment.getInstance(walletType.getWalletType()));
                break;
            case BTC:
                fragmentList.add(ImportMnemonicFragment.getInstance(walletType.getWalletType()));
                fragmentList.add(ImportPrivateKeyFragment.getInstance(walletType.getWalletType()));
                tabNams = new String[]{"助记词", "私钥"};
                break;
        }

        indicatorView.setSplitAuto(true);
        indicatorView.setOnTransitionListener(new OnTransitionTextListener()
                .setColor(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.black))
                .setSize(14, 14));
        indicatorView.setScrollBar(new TextWidthColorBar(this, indicatorView, getResources().getColor(R.color.colorPrimary), ConvertUtils.dp2px(2)));
        indicatorView.setScrollBarSize(50);
        indicatorViewPager = new IndicatorViewPager(indicatorView, vpLoadWallet);
        loadWalletPageFragmentAdapter = new LoadWalletPageFragmentAdapter(this, getSupportFragmentManager(), fragmentList, tabNams);
        indicatorViewPager.setAdapter(loadWalletPageFragmentAdapter);
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
