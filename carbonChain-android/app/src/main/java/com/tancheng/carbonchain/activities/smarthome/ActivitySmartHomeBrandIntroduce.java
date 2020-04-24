package com.tancheng.carbonchain.activities.smarthome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tancheng.carbonchain.R;

public class ActivitySmartHomeBrandIntroduce extends AppCompatActivity {

    private Button mBtnBindAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_home_brand_introduce);
        findViews();
        setOnClickListeners();
    }

    private void findViews(){
        mBtnBindAccount = findViewById(R.id.bind_account);
    }

    private void setOnClickListeners(){
        mBtnBindAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ActivitySmartHomeBrandIntroduce.this, ActivitySmartHomeBindAccount.class);
                startActivity(intent);
            }
        });
    }
}
