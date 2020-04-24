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

/**
 * 确认Pin码页面
 */
public class ConfirmPinCodeActivity extends BaseActivity {
    private TextView tvSetPinCode,tvTip,tvComplete;
    private CodeEditText etAgainAccount;
    private String pinCode,pinCodeTwo;
    private UserPreference preference;

    @Override
    public int getLayoutId() {
        return R.layout.activity_confirm_pin_code;
    }

    @Override
    public void initDatas() {
        preference = UserPreference.getUserPreference(this);
        pinCode = getIntent().getStringExtra("pinCode");
    }

    @Override
    public void configViews() {
        tvSetPinCode = findViewById(R.id.tvSetPinCode);
        tvTip = findViewById(R.id.tvTip);
        tvComplete = findViewById(R.id.tvComplete);
        etAgainAccount = findViewById(R.id.etAgainAccount);
        etAgainAccount.setOnTextFinishListener(new CodeEditText.OnTextFinishListener() {
            @Override
            public void onTextFinish(CharSequence text, int length) {
                pinCodeTwo = text.toString();
                inputCode(pinCodeTwo);
            }
        });
        tvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(pinCode) && TextUtils.isEmpty(pinCodeTwo)){
                    showToast("请再次输入PIN码!");
                    return;
                }
                if (pinCode.equals(pinCodeTwo)){
                    preference.setPincode(pinCodeTwo);
                    preference.save();
                    startActivity(new Intent(ConfirmPinCodeActivity.this, ActivityMain.class));

                }else {
                    showToast("两次输入不一致，请再次输入!");
                    etAgainAccount.setText("");
                }
            }
        });
    }


    private void inputCode(String pinCodeTwo){
        if (TextUtils.isEmpty(pinCode) && TextUtils.isEmpty(pinCodeTwo)){
            showToast("请再次输入PIN码!");
            return;
        }

        if (!pinCode.equals(pinCodeTwo)){
            tvTip.setText(getResources().getString(R.string.errro_tip));
            tvTip.setTextColor(getResources().getColor(R.color.color_FF4A51));
            etAgainAccount.setText("");
        }else {
            tvTip.setText(getResources().getString(R.string.confirm_pin_code_again_tip));
            tvTip.setTextColor(getResources().getColor(R.color.color_BBBBBB));
        }

    }


}
