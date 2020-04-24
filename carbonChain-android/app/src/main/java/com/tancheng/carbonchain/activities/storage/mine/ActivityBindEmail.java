package com.tancheng.carbonchain.activities.storage.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.utils.CustomEditText;

/**
 * 绑定邮箱
 */
public class ActivityBindEmail extends AppCompatActivity {
    private LinearLayout llInPutVerifyCode,llBindEmail;
    private CustomEditText etCustomEditText;
    private TextView tvNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_email);
        initView();
        setOnClickListener();
    }

    private void setOnClickListener() {
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llBindEmail.setVisibility(View.GONE);
                llInPutVerifyCode.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initView() {
        llInPutVerifyCode = findViewById(R.id.llInPutVerifyCode);
        llBindEmail = findViewById(R.id.llBindEmail);
        llBindEmail.setVisibility(View.VISIBLE);
        etCustomEditText = findViewById(R.id.etCustomEditText);
        tvNext = findViewById(R.id.tvNext);
    }



}
