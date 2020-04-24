package com.tancheng.carbonchain.activities.asset.digit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tancheng.carbonchain.R;

public class ActivityTradeCoinConfirm extends AppCompatActivity {

    private Button mConfirmTransferBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_coin_confirm);
        findViews();
        setOnClickListener();
    }

    private void findViews(){
        mConfirmTransferBtn = findViewById(R.id.asset_transfer_coin_confirm_btn);
    }

    private void setOnClickListener(){
        mConfirmTransferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ActivityTradeCoinConfirm.this, ActivityTradeCoinConfirmDevice.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
