package com.tancheng.carbonchain.activities.bindsevice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.base.BaseActivity;

/**
 * 忘记PIN码
 */
public class ForgetPINCodeActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvScan;
    private ImageView ivBack;

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_pincode;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        tvScan = findViewById(R.id.tvScan);
        tvScan.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvScan:
                startActivity(new Intent(this, CustomCaptureActivity.class));
                break;
        }
    }
}
