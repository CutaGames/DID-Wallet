package com.tancheng.carbonchain.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.tancheng.carbonchain.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityForgetPassword extends AppCompatActivity {

    private static final Boolean DEBUG = true;
    private static final String TAG = "ActivityForgetPassword";


    @BindView(R.id.forget_step1_layout)
    LinearLayout mForgetStep1Layout;

    @BindView(R.id.forget_step1_next_btn)
    Button mForgetStep1Next;

    @BindView(R.id.forget_step2_layout)
    LinearLayout mForgetStep2Layout;

    @BindView(R.id.forget_step2_next_btn)
    Button mForgetStep2Next;

    @BindView(R.id.forget_step3_layout)
    LinearLayout mForgetStep3Layout;

    @BindView(R.id.forget_step3_next_btn)
    Button mForgetStep3Next;

    @BindView(R.id.forget_step4_layout)
    LinearLayout mForgetStep4Layout;

    @BindView(R.id.forget_step4_save_btn)
    Button mForgetStep4Save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
            actionBar.setBackgroundDrawable(null);
            actionBar.setHomeAsUpIndicator(R.mipmap.action_bar_icon_back);
        }

        setOnClickListeners();
    }

    private void setOnClickListeners() {
        mForgetStep1Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mForgetStep1Layout.setVisibility(View.GONE);
                mForgetStep2Layout.setVisibility(View.VISIBLE);
            }
        });

        mForgetStep2Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mForgetStep2Layout.setVisibility(View.GONE);
                mForgetStep3Layout.setVisibility(View.VISIBLE);
            }
        });

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
                intent.setClass(ActivityForgetPassword.this, ActivityLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
