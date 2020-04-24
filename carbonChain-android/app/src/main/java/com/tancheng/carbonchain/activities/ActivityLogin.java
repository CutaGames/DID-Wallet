package com.tancheng.carbonchain.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.main.ActivityMain;
import com.tancheng.carbonchain.activities.register.ActivityRegisterBind1;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityLogin extends AppCompatActivity {

    private static final Boolean DEBUG = true;
    private static final String TAG = "ActivityLogin";

    @BindView(R.id.login_textview_forget_password)
    TextView mBtnForgetPassword;

    @BindView(R.id.login_textview_register_now)
    TextView mBtnRegisterNow;

    @BindView(R.id.login_btn_login)
    Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mBtnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DEBUG) Log.d(TAG, "click forget password");
                Intent intent = new Intent();
                intent.setClass(com.tancheng.carbonchain.activities.ActivityLogin.this, ActivityForgetPassword.class);
                startActivity(intent);
            }
        });

        mBtnRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DEBUG) Log.d(TAG, "click register now");
                Intent intent = new Intent();
                intent.setClass(com.tancheng.carbonchain.activities.ActivityLogin.this, ActivityRegisterBind1.class);
                startActivity(intent);
            }
        });

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DEBUG) Log.d(TAG, "click register now");
                Intent intent = new Intent();
                intent.setClass(com.tancheng.carbonchain.activities.ActivityLogin.this, ActivityMain.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
