package com.tancheng.carbonchain.activities.asset.wallet.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.ETHWallet;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;

import java.util.List;

/**
 * created by tc_collin on 2020/4/2 16:54
 * Description: 钱包管理adapter
 * version: 1.0
 */
public class WalletManageAdapter extends BaseAdapter {
    private Activity mActivity;
    private int layoutId;
    private List<ETHWallet> items;

    public WalletManageAdapter(Activity context, List<ETHWallet> tokenItems, int layoutId) {
        this.mActivity = context;
        this.layoutId = layoutId;
        this.items = tokenItems;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = ViewHolder.get(mActivity, view, viewGroup,
                layoutId, position);
        WalletType walletType = WalletType.of(items.get(position).getWalletType());
        holder.setImageResource(R.id.civ_wallet_logo, walletType.getLogo());
        holder.setText(R.id.tv_wallet_name, items.get(position).getName());
        holder.setText(R.id.tv_wallet_address, items.get(position).getAddress());

        return holder.getConvertView();
    }
}
