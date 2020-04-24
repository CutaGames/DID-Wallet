package com.tancheng.carbonchain.activities.asset.digit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tancheng.carbonchain.R;

public class ActivityTradeCoinAddressBook extends Activity {

    private static final int MSG_LOAD_SUCCESS = 1;
    private LinearLayout mNoneLayout;
    private LinearLayout mContainerLayout;
    private ImageView mIvAdd;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (MSG_LOAD_SUCCESS == msg.what) {
                mNoneLayout.setVisibility(View.GONE);
                mContainerLayout.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_coin_address_book);
        findViews();
        setOnClickListeners();
        startCallSuccessThread();
    }

    private void findViews(){
        mNoneLayout = (LinearLayout)findViewById(R.id.none_layout);
        mContainerLayout = (LinearLayout)findViewById(R.id.container);
        mIvAdd = findViewById(R.id.iv_add);
    }

    private void setOnClickListeners(){
        mIvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ActivityTradeCoinAddressBook.this, ActivityTradeCoinAddressAdd.class);
                startActivity(intent);
            }
        });
    }

    private void startCallSuccessThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
            }
        }).start();
    }
}
