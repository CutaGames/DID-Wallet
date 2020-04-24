package com.tancheng.carbonchain.activities.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 弃用
 */
public class ActivityRegisterBind1 extends AppCompatActivity {

    private static final Boolean DEBUG = true;
    private static final String TAG = "ActivityRegisterBind1";

    @BindView(R.id.register_bind_device_scan_layout)
    RelativeLayout mRegisterBindDeviceScanLayout;

    @BindView(R.id.register_bind_device_scan_layout_button)
    Button mRegisterBindDeviceScanLayoutBtn;

    @BindView(R.id.register_bind_device_scan_continue_layout)
    RelativeLayout mRegisterBindDeviceScanContinueLayout;

    @BindView(R.id.register_bind_device_scan_continue)
    Button mRegisterBindDeviceScanContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bind_1);
        ButterKnife.bind(this);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
            actionBar.setBackgroundDrawable(null);
            actionBar.setHomeAsUpIndicator(R.mipmap.action_bar_icon_back);
        }

        mRegisterBindDeviceScanLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegisterBindDeviceScanLayout.setVisibility(View.GONE);
                mRegisterBindDeviceScanContinueLayout.setVisibility(View.VISIBLE);
            }
        });

        mRegisterBindDeviceScanContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DEBUG) Log.d(TAG, "click register now");
                Intent intent = new Intent();
                intent.setClass(ActivityRegisterBind1.this, ActivityRegisterBind2.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
