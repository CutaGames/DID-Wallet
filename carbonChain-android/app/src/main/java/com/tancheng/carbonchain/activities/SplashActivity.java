package com.tancheng.carbonchain.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.base.BaseActivity;
import com.tancheng.carbonchain.activities.bindsevice.BindStartActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 欢迎页
 */
public class SplashActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.btnStart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                startActivity(new Intent(SplashActivity.this, BindStartActivity.class));
                break;

        }
    }

}
