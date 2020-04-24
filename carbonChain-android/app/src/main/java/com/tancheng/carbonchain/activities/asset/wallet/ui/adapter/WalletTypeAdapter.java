package com.tancheng.carbonchain.activities.asset.wallet.ui.adapter;

import android.content.Context;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;
import java.util.List;

/**
 * created by tc_collin on 2020/3/27 18:45
 * Description: 钱包类型 adapter
 * version: 1.0
 */
public class WalletTypeAdapter extends CommonAdapter<WalletType> {

    public void WalletTypeAdapter(){

    }

    public WalletTypeAdapter(Context context, List<WalletType> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, WalletType walletType) {
        holder.setImageResource(R.id.show_image, walletType.getLogo());
        holder.setText(R.id.tv_wallet_name, walletType.name());
    }
}
