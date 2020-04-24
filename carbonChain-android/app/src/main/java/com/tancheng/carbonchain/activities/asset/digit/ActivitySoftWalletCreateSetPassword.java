package com.tancheng.carbonchain.activities.asset.digit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.tancheng.carbonchain.R;

public class ActivitySoftWalletCreateSetPassword extends AppCompatActivity {

    private static final Boolean DEBUG = true;
    private static final String TAG = "WalletCreateSetPassword";

    private LinearLayout mForgetStep3Layout;
    private Button mForgetStep3Next;
    private LinearLayout mForgetStep4Layout;
    private Button mForgetStep4Save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_wallet_create_set_password);
        findViews();
        setOnClickListeners();
    }

    private void findViews(){
        mForgetStep3Layout = findViewById(R.id.forget_step3_layout);
        mForgetStep3Next = findViewById(R.id.forget_step3_next_btn);
        mForgetStep4Layout = findViewById(R.id.forget_step4_layout);
        mForgetStep4Save = findViewById(R.id.forget_step4_save_btn);
    }

    private void setOnClickListeners(){
        mForgetStep3Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mForgetStep3Layout.setVisibility(View.GONE);
                mForgetStep4Layout.setVisibility(View.VISIBLE);
            }
        });

        mForgetStep4Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DEBUG) Log.d(TAG, "click register now");
                Intent intent = new Intent();
                intent.setClass(ActivitySoftWalletCreateSetPassword.this, ActivitySoftWalletCreateSelectCoin.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
