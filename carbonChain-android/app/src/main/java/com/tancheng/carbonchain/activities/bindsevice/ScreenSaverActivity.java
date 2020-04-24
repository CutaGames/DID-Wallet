package com.tancheng.carbonchain.activities.bindsevice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.base.BaseActivity;
import com.tancheng.carbonchain.activities.main.ActivityMain;
import com.tancheng.carbonchain.customview.CodeEditText;
import com.tancheng.carbonchain.utils.UserPreference;

import butterknife.OnClick;

/**
 * 解锁屏保页面
 */
public class ScreenSaverActivity extends BaseActivity {
    private TextView tvTip;
    private CodeEditText etAccount;
    private UserPreference preference;

    @Override
    public int getLayoutId() {
        return R.layout.activity_screen_saver2;
    }

    @Override
    public void initDatas() {
        preference = UserPreference.getUserPreference(this);
    }

    @Override
    public void configViews() {
        tvTip = findViewById(R.id.tvTip);
        etAccount = findViewById(R.id.etAccount);
        etAccount.setOnTextFinishListener(new CodeEditText.OnTextFinishListener() {
            @Override
            public void onTextFinish(CharSequence text, int length) {
                if (!text.toString().equals(preference.getPincode())) {
                    //密码输入错误
                    tvTip.setText(getResources().getString(R.string.errro_tip));
                    tvTip.setTextColor(getResources().getColor(R.color.color_FF4A51));
                    etAccount.setText("");
                } else {
                    //解锁屏保
                    finish();
                }
            }
        });
    }

    @OnClick({R.id.tvForGetPINCode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvForGetPINCode:
                startActivity(new Intent(this, ForgetPINCodeActivity.class));
                break;
        }

    }

}
