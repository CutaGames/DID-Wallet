package com.tancheng.carbonchain.activities.asset.wallet.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tancheng.carbonchain.R;

import java.util.List;

public class MnemonicSelectedWordsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public MnemonicSelectedWordsAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String string) {
        helper.setText(R.id.tv_mnemonic_selected_word, string);
    }

}
