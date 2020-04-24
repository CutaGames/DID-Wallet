package com.tancheng.carbonchain.activities.storage.mine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.storage.phonebackup.ActivityPhoneBackup;

/**
 * 个人信息
 */
public class ActivityUserInfo extends AppCompatActivity {
    private LinearLayout llUnLockWay,llEmail,llAccountPsd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        setOnClickLister();
    }

    private void setOnClickLister() {
        llUnLockWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), UnLockWayActivity.class);
                startActivity(intent);
            }
        });

        llEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), ActivityBindEmail.class);
                startActivity(intent);
            }
        });

        llAccountPsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), ActivityModifyPassword.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        llUnLockWay = findViewById(R.id.llUnLockWay);
        llEmail = findViewById(R.id.llEmail);
        llAccountPsd = findViewById(R.id.llAccountPsd);
    }
}
