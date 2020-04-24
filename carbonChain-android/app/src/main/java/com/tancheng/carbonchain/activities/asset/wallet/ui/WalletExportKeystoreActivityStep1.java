package com.tancheng.carbonchain.activities.asset.wallet.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by tc_collin on 2020/4/7 14:52
 * Description: 导出keyStore step1
 * version: 1.0
 */
public class WalletExportKeystoreActivityStep1 extends BaseActivity {
    @BindView(R.id.iv_title_left_back)
    ImageView ivTitleLeftBack;
    @BindView(R.id.btn_next_step)
    TextView btnNextStep;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    String keystore;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_export_keystore_step1;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("备份提示");
        Intent intent = getIntent();
        keystore = intent.getStringExtra("keystore");
    }

    @Override
    public void configViews() {

    }


    @OnClick({R.id.iv_title_left_back, R.id.btn_next_step})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left_back:
                finish();
                break;
            case R.id.btn_next_step:
                Intent intent = new Intent(mContext, WalletExportKeystoreActivity.class);
                intent.putExtra("keystore", keystore);
                startActivity(intent);
                break;
        }
    }
}
