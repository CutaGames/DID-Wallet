package com.tancheng.carbonchain.activities.asset.wallet.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.TextWidthColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.ETHWallet;
import com.tancheng.carbonchain.activities.asset.wallet.domain.TokenType;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;
import com.tancheng.carbonchain.activities.asset.wallet.service.IchainService;
import com.tancheng.carbonchain.activities.asset.wallet.service.WalletServiceeFactory;
import com.tancheng.carbonchain.activities.asset.wallet.ui.adapter.DeriveKeystorePageFragmentAdapter;
import com.tancheng.carbonchain.activities.asset.wallet.ui.fragment.TransactionRexordFragment;
import com.tancheng.carbonchain.activities.base.BaseActivity;
import com.tancheng.carbonchain.activities.base.BaseFragment;
import com.tancheng.carbonchain.db.gen.WalletDaoUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.tancheng.carbonchain.db.gen.WalletDaoUtils.getIntence;

/**
 * created by tc_collin on 2020/4/1 9:47
 * Description: token详情
 * version: 1.0
 */
public class TokenDetailActivity extends BaseActivity {
    private static final String INTENT_WALLET_ID = "walletId";
    private static final String INTENT_TOKEN = "token";

    @BindView(R.id.iv_title_left_back)
    ImageView ivTitleLeftBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.tv_amount2)
    TextView tvAmount2;
    @BindView(R.id.lly_transfer)
    LinearLayout llyTransfer;
    @BindView(R.id.lly_gathering)
    LinearLayout llyGathering;
    @BindView(R.id.indicator_view)
    ScrollIndicatorView indicatorView;
    @BindView(R.id.vp_wallet_record)
    ViewPager vpWalletRecord;

    private WalletDaoUtils walletDaoUtils;
    private ETHWallet walletInfo;
    private TokenType tokenType;
    private String balance;

    private List<BaseFragment> fragmentList = new ArrayList<>();
    private IndicatorViewPager indicatorViewPager;
    public static boolean refresh = false;

    private DeriveKeystorePageFragmentAdapter deriveKeystorePageFragmentAdapter;
    private TransactionRexordFragment transactionRexordFragmentAll, transactionRexordFragmentOut, transactionRexordFragmentIn;

    public static void startAct(Activity mActivitty, long walletId, TokenType tokenType) {
        Intent intent = new Intent(mActivitty, TokenDetailActivity.class);
        intent.putExtra(INTENT_WALLET_ID, walletId);
        intent.putExtra(INTENT_TOKEN, tokenType);
        mActivitty.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (refresh) {
            getBalance(walletInfo.getAddress(), tokenType.getAddress());
            refresh = false;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_token_detail;
    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        long walletId = intent.getLongExtra(INTENT_WALLET_ID, -1);
        tokenType = (TokenType) intent.getSerializableExtra(INTENT_TOKEN);
        walletDaoUtils = getIntence();
        walletInfo = walletDaoUtils.getWalletInfo(walletId);
        if (walletInfo == null) {
            ToastUtils.showShort("未查到钱包信息");
        }

        transactionRexordFragmentAll = new TransactionRexordFragment();
        transactionRexordFragmentOut = new TransactionRexordFragment();
        transactionRexordFragmentIn = new TransactionRexordFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putLong("tokenId", tokenType.getId());
        bundle1.putInt("direct", -1);
        bundle1.putString("address", walletInfo.getAddress());
        Bundle bundle2 = new Bundle();
        bundle2.putLong("tokenId", tokenType.getId());
        bundle2.putInt("direct", 0);
        bundle2.putString("address", walletInfo.getAddress());
        Bundle bundle3 = new Bundle();
        bundle3.putLong("tokenId", tokenType.getId());
        bundle3.putInt("direct", 1);
        bundle3.putString("address", walletInfo.getAddress());

        transactionRexordFragmentAll.setArguments(bundle1);
        transactionRexordFragmentOut.setArguments(bundle2);
        transactionRexordFragmentIn.setArguments(bundle3);
        fragmentList.add(transactionRexordFragmentAll);
        fragmentList.add(transactionRexordFragmentOut);
        fragmentList.add(transactionRexordFragmentIn);
    }

    @Override
    public void configViews() {
        tvTitle.setText(tokenType.getSymbol());
        int[] titles = {R.string.tx_record_all, R.string.tx_record_out, R.string.tx_record_in};
        indicatorView.setSplitAuto(true);
        indicatorView.setOnTransitionListener(new OnTransitionTextListener()
                .setColor(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.black))
                .setSize(14, 14));
        indicatorView.setScrollBar(new TextWidthColorBar(this, indicatorView, getResources().getColor(R.color.colorPrimary), ConvertUtils.dp2px(2)));
        indicatorView.setScrollBarSize(50);
        indicatorViewPager = new IndicatorViewPager(indicatorView, vpWalletRecord);
        deriveKeystorePageFragmentAdapter = new DeriveKeystorePageFragmentAdapter(this, getSupportFragmentManager(), fragmentList, titles);
        indicatorViewPager.setAdapter(deriveKeystorePageFragmentAdapter);
        indicatorViewPager.setCurrentItem(0, false);
        vpWalletRecord.setOffscreenPageLimit(4);
        getBalance(walletInfo.getAddress(), tokenType.getAddress());
    }

    @OnClick({R.id.iv_title_left_back, R.id.lly_transfer, R.id.lly_gathering})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left_back:
                finish();
                break;
            case R.id.lly_transfer:
                TokenTransferActivity.startAct(mContext, walletInfo.getId(), tokenType, balance);
                break;
            case R.id.lly_gathering:
                GatheringQRActivity.startAct(mContext, walletInfo.getAddress(), tokenType.getLogoUrl());
                break;
        }
    }

    private void getBalance(String address, String contract) {
        showDialog("数据加载中");
        new Thread(new Runnable() {
            @Override
            public void run() {
                WalletType walletType = WalletType.of(tokenType.getWalletType());
                IchainService chainService = WalletServiceeFactory.getChainService(walletType);
                BigDecimal balanceBig = chainService.getBalance(address, contract);
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        balance = balanceBig.toString();
                        if (tvAmount != null && tokenType != null && !StringUtils.isEmpty(balance)) {
                            SPUtils.getInstance().put(walletInfo.getAddress()+tokenType.getSymbol() + "_balance", balance);
                            tvAmount.setText(balanceBig.setScale(4, RoundingMode.UP) + tokenType.getSymbol());
                            String priceStr = SPUtils.getInstance().getString(tokenType.getSymbol() + "_price");
                            BigDecimal convertBalance = balanceBig.multiply(new BigDecimal(priceStr)).setScale(2,RoundingMode.UP);
                            tvAmount2.setText(convertBalance.toPlainString() + "(￥)");
                        }
                        dismissDialog();
                    }
                });
            }
        }).start();
    }
}
