package com.tancheng.carbonchain.activities.asset.wallet.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.AddressBook;
import com.tancheng.carbonchain.activities.asset.wallet.domain.TokenType;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletConstants;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;
import com.tancheng.carbonchain.activities.asset.wallet.ui.AddressBookAddActivity;

import java.util.List;

/**
 * created by collin on 2020/3/30 10:35
 * Description: 地址簿列表adapter
 * version: 1.0
 */
public class AddressBookAdapter extends BaseAdapter {
    private Activity mActivity;
    private int layoutId;
    private List<AddressBook> items;

    public AddressBookAdapter(Activity context, List<AddressBook> tokenItems, int layoutId) {
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
        holder.setImageResource(R.id.iv_logo, walletType.getLogo());
        holder.setText(R.id.tv_name, items.get(position).getName());
        holder.setText(R.id.tv_address, items.get(position).getAddress());
        holder.setOnClickListener(R.id.tv_edit, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddressBook addressBook = items.get(position);
                AddressBookAddActivity.startAct(mActivity, addressBook.getId());
            }
        });
        return holder.getConvertView();
    }

}




