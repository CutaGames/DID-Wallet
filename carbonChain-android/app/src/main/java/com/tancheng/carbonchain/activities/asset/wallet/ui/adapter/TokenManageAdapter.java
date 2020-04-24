package com.tancheng.carbonchain.activities.asset.wallet.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.blankj.utilcode.util.LogUtils;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.domain.TokenType;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletConstants;

import java.util.List;

/**
 * created by collin on 2020/3/30 10:35
 * Description: token列表adapter
 * version: 1.0
 */
public class TokenManageAdapter extends BaseAdapter {

    private Activity mActivity;
    private int layoutId;
    private List<TokenType> items;
    private String tokenIds;//已添加的token
    private SwitchChangeListener switchListener;

    public TokenManageAdapter(Activity context, List<TokenType> tokenItems, int layoutId, String tokenIds) {
        this.mActivity = context;
        this.layoutId = layoutId;
        this.items = tokenItems;
        this.tokenIds = tokenIds;
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

        if (haveAdded(items.get(position))) {
            holder.setChecked(R.id.add_switch, true);
        } else {
            holder.setChecked(R.id.add_switch, false);
        }

        if (position == 0) {
            holder.getView(R.id.lly_item).setBackgroundColor(mActivity.getResources().getColor(R.color.white));
            holder.setVisible(R.id.add_switch, false);
        } else {
//            holder.getView(R.id.lly_item).setBackgroundColor(mActivity.getResources().getColor(R.color.add_property_gray_bg_color));
            holder.setVisible(R.id.add_switch, true);
            holder.setTag(R.id.add_switch, items.get(position));

            ((CheckBox) holder.getView(R.id.add_switch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    switchListener.OnSwitch(items.get(position), b);
                }
            });
        }

        holder.setText(R.id.tv_ico_name, items.get(position).getSymbol());
        holder.setImageResource(R.id.civ_ico_logo, items.get(position).getLogoUrl());

        return holder.getConvertView();
    }

    private boolean haveAdded(TokenType tokenType) {
        String[] tokenAdds = tokenIds.split(WalletConstants.COIIN_SPLIT_CHAR);
        for (String id : tokenAdds) {
            if (id.equals(tokenType.getId() + "")) {
                return true;
            }
        }
        return false;
    }

    public void setSwitchListener(SwitchChangeListener switchListener) {
        this.switchListener = switchListener;
    }

    public interface SwitchChangeListener {
        void OnSwitch(TokenType tokenType, boolean checked);
    }

}




