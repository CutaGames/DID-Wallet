package com.tancheng.carbonchain.activities.bindsevice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 绑定设备
 */
public class BindStartActivity extends BaseActivity {
    @BindView(R.id.tvScan)
    TextView tvScan;
    @BindView(R.id.ivBack)
    ImageView ivBack;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_start;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.tvScan, R.id.ivBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvScan:
                startActivity(new Intent(BindStartActivity.this, ScanSuccessActivity.class));
                break;
        }
    }
}
