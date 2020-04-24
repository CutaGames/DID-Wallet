package com.tancheng.carbonchain.activities.bindsevice;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.base.BaseActivity;
import com.tancheng.carbonchain.customview.CodeEditText;

import butterknife.OnClick;

/**
 * 设置PIN码
 */
public class SetPINCodeActivity extends BaseActivity {
    private TextView tvSetPinCode, tvTip, tvComplete;
    private CodeEditText etAccount, etAgainAccount;
    private String pinCode;
    private static final String TAG = "ScreenSaverActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_screen_saver;
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        tvSetPinCode = findViewById(R.id.tvSetPinCode);
        tvTip = findViewById(R.id.tvTip);
        tvComplete = findViewById(R.id.tvComplete);
        etAccount = findViewById(R.id.etAccount);
        etAgainAccount = findViewById(R.id.etAgainAccount);

        etAccount.setOnTextFinishListener(new CodeEditText.OnTextFinishListener() {
            @Override
            public void onTextFinish(CharSequence text, int length) {
                pinCode = text.toString();
                tvComplete.setBackground(getResources().getDrawable(R.drawable.bottom_bg_btn_linear_blue));
            }
        });

    }

    @OnClick({R.id.tvComplete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvComplete:
                if (TextUtils.isEmpty(pinCode)) {
                    showToast("请输入PIN码!");
                    return;
                }
                Intent intent = new Intent(SetPINCodeActivity.this, ConfirmPinCodeActivity.class);
                intent.putExtra("pinCode", pinCode);
                startActivity(intent);
                break;
        }

    }

}
