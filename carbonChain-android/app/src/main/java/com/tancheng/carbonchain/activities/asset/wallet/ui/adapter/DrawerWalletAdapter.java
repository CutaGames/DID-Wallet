package com.tancheng.carbonchain.activities.asset.wallet.ui.adapter;

import android.content.Context;
import android.graphics.Color;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.ETHWallet;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;

import java.util.List;

public class DrawerWalletAdapter extends CommonAdapter<ETHWallet> {
    private int currentWalletPosition = 0;

    public DrawerWalletAdapter(Context context, List<ETHWallet> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public void setCurrentWalletPosition(int currentWalletPosition) {
        this.currentWalletPosition = currentWalletPosition;
        notifyDataSetChanged();
    }

    @Override
    public void convert(ViewHolder holder, ETHWallet wallet) {
        boolean isCurrent = wallet.getIsCurrent();
        int position = holder.getPosition();
        if (isCurrent) {
            currentWalletPosition = position;
            holder.getView(R.id.lly_wallet).setBackgroundColor(mContext.getResources().getColor(R.color.asset_data_add_item_edit_text_color));
        } else {
            holder.getView(R.id.lly_wallet).setBackgroundColor(Color.WHITE);
        }
        holder.setText(R.id.tv_wallet_name, wallet.getName());
        int walletTypeId = wallet.getWalletType();
        WalletType walletType = WalletType.of(walletTypeId);
        holder.setImageResource(R.id.civ_ico_logo,walletType.getLogo());
    }

}
