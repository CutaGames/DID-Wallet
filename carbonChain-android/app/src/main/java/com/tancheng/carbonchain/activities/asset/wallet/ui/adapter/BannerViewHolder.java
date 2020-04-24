package com.tancheng.carbonchain.activities.asset.wallet.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.domain.TokenType;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletBannerModel;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletConstants;
import com.tancheng.carbonchain.activities.asset.wallet.ui.GatheringQRActivity;
import com.tancheng.carbonchain.activities.asset.wallet.ui.WalletDetailActivity;
import com.zhouwei.mzbanner.holder.MZViewHolder;

/**
 * created by tc_collin on 2020/4/14 17:49
 * Description:
 * version: 1.0
 */
public class BannerViewHolder implements MZViewHolder<WalletBannerModel> {

    TextView tvCoinName;
    TextView tvWalletAddress;
    TextView tvWalletBalance;
    ImageView ivBanner;
    Activity mActivity;

    @Override
    public View createView(Context context) {
        // 返回页面布局
        mActivity = (Activity) context;
        View view = LayoutInflater.from(context).inflate(R.layout.wallet_banner, null);
        ivBanner = view.findViewById(R.id.iv_banner);
        tvCoinName = (TextView) view.findViewById(R.id.tv_coin_name);
        tvWalletAddress = (TextView) view.findViewById(R.id.tv_wallet_address);
        tvWalletBalance = (TextView) view.findViewById(R.id.tv_wallet_balance);
        return view;
    }

    @Override
    public void onBind(Context context, int position, WalletBannerModel data) {
        // 数据绑定
        boolean hideBalance = SPUtils.getInstance().getBoolean(WalletConstants.HIDE_BALANCE, false);
        ivBanner.setImageResource(data.getBgResource());
        tvCoinName.setText(data.getWalletName());
        String walletAddr = data.getWalletAddr();
        if (StringUtils.isEmpty(walletAddr)){
            tvWalletAddress.setVisibility(View.INVISIBLE);
        }else{
            tvWalletAddress.setVisibility(View.VISIBLE);
        }
        tvWalletAddress.setText(data.getWalletAddr());
        tvWalletBalance.setText(hideBalance?"***.**":data.getBalance());
        ivBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.getWalletId() >= 0) {
                    WalletDetailActivity.startAct(mActivity, data.getWalletId(), Double.parseDouble(data.getBalance()));
                }
            }
        });

        tvWalletAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.getWalletId() >= 0) {
                    TokenType tokenType = TokenType.getDefaultTokenByWalletType(data.getWalletTypeId());
                    GatheringQRActivity.startAct(mActivity, data.getWalletAddr(), tokenType.getLogoUrl());
                }
            }
        });

    }
}
