package com.tancheng.carbonchain.activities.smarthome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tancheng.carbonchain.R;

public class ActivitySmartHomeBindAccount extends AppCompatActivity {

    private Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_home_bind_account);
        findViews();
        setOnClickListeners();
    }

    private void findViews(){
        mBtnLogin = findViewById(R.id.smart_home_login_btn);
    }

    private void setOnClickListeners(){
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                // TODO target activity
                //intent.setClass(ActivitySmartHomeBindAccount.this, ActivitySmartHome.class);
                //startActivity(intent);
            }
        });
    }
}
