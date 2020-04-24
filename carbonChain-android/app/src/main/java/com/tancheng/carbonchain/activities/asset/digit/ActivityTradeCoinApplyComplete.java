package com.tancheng.carbonchain.activities.asset.digit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tancheng.carbonchain.R;

public class ActivityTradeCoinApplyComplete extends AppCompatActivity {

    private TextView mTvComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_coin_apply_complete);
        findViews();
        setOnClickListeners();
    }

    private void findViews(){
        mTvComplete = findViewById(R.id.tv_complete);
    }

    private void setOnClickListeners(){
        mTvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
