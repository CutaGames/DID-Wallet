package com.tancheng.carbonchain.activities.asset.wallet.ui.adapter;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.WalletBalance;
import com.tancheng.carbonchain.activities.asset.wallet.domain.TokenType;
import com.tancheng.carbonchain.db.gen.WalletBalnceDaoUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * created by tc_collin on 2020/3/28 13:46
 * Description: 代币列表adapter
 * version: 1.0
 */
public class WalletTokenListAdapter extends CommonAdapter<TokenType> {

    private String walletAddr;

    public WalletTokenListAdapter(Context context, String walletAddress, List<TokenType> datas, int layoutId) {
        super(context, datas, layoutId);
        walletAddr = walletAddress;
    }

    /**
     * 设置当前钱包地址
     *
     * @param walletAddr
     */
    public void setWalletAddr(String walletAddr) {
        this.walletAddr = walletAddr;
    }

    @Override
    public void convert(ViewHolder holder, TokenType tokenType) {
        String symbol = tokenType.getSymbol();
        WalletBalance walletBalance = WalletBalnceDaoUtils.getIntence().getWalletBalance(walletAddr, symbol);
        holder.setText(R.id.symbol, tokenType.getSymbol());

        String CoinBalance = walletBalance == null ? "0.00" : new BigDecimal(walletBalance.getBalance()).setScale(4, RoundingMode.HALF_UP).toPlainString();
        holder.setText(R.id.balance, CoinBalance);
        holder.setImageResource(R.id.logo, tokenType.getLogoUrl());
        String priceStr = SPUtils.getInstance().getString(symbol + "_price");
        BigDecimal price = BigDecimal.ZERO;
        if (!StringUtils.isEmpty(priceStr)) {
            price = new BigDecimal(priceStr);
        }
        BigDecimal balance = BigDecimal.ZERO;
        if (walletBalance != null) {
            balance = BigDecimal.valueOf(walletBalance.getBalance());
        }
        BigDecimal total = balance.multiply(price);
        holder.setText(R.id.tv_property_cny, total.setScale(2, RoundingMode.HALF_UP).toPlainString() + "(￥)");
    }

}
