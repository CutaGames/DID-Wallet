package com.tancheng.carbonchain.activities.asset.digit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tancheng.carbonchain.R;

public class ActivityTradeCoinConfirmDevice extends AppCompatActivity {

    private ImageView mConfirmImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_coin_confirm_device);
        findViews();
        setOnClickListener();
    }

    private void findViews(){
        mConfirmImage = findViewById(R.id.asset_transfer_coin_confirm_device_image);
    }

    private void setOnClickListener(){
        mConfirmImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ActivityTradeCoinConfirmDevice.this, ActivityTradeCoinApplyComplete.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
