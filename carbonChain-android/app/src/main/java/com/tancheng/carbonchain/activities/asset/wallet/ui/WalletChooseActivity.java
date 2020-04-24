package com.tancheng.carbonchain.activities.asset.wallet.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;
import com.tancheng.carbonchain.activities.asset.wallet.ui.adapter.WalletTypeAdapter;
import com.tancheng.carbonchain.activities.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by tc_collin on 2020/3/27 17:54
 * Description: 选择钱包类型
 * version: 1.0
 */
public class WalletChooseActivity extends BaseActivity {

    @BindView(R.id.lv_wallet_type)
    ListView lvWalletType;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private List<WalletType> walletTypes;

    @Override
    public int getLayoutId() {
        return R.layout.activity_choose_wallet_type;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("添加钱包");
        walletTypes = WalletType.walletTypeList();
        WalletTypeAdapter adapter = new WalletTypeAdapter(mContext, walletTypes, R.layout.list_item_wallet_type);
        lvWalletType.setAdapter(adapter);
    }

    @Override
    public void configViews() {
        lvWalletType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WalletType walletType = walletTypes.get(i);
                WalletCreateActivity.startThisAct(mContext, walletType.getWalletType());
                finish();
            }
        });
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
