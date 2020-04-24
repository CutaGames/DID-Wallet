package com.tancheng.carbonchain.activities.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.tancheng.carbonchain.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityRegisterBind2 extends AppCompatActivity {

    private static final Boolean DEBUG = true;
    private static final String TAG = "ActivityRegisterBind2";

    @BindView(R.id.register_bind_phone_next_layout_before)
    LinearLayout mRegisterBindPhoneNextLayoutBefore;

    @BindView(R.id.register_bind_phone_next)
    Button mRegisterBindPhoneNext;

    @BindView(R.id.register_bind_phone_next_layout_after)
    LinearLayout mRegisterBindPhoneNextLayoutAfter;

    @BindView(R.id.register_bind_phone_next_set_password)
    Button mRegisterBindPhoneNextSetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bind_2);
        ButterKnife.bind(this);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
            actionBar.setBackgroundDrawable(null);
            actionBar.setHomeAsUpIndicator(R.mipmap.action_bar_icon_back);
        }

        mRegisterBindPhoneNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DEBUG) Log.d(TAG, "click register phone next");
                mRegisterBindPhoneNextLayoutBefore.setVisibility(View.GONE);
                mRegisterBindPhoneNextLayoutAfter.setVisibility(View.VISIBLE);
            }
        });

        mRegisterBindPhoneNextSetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DEBUG) Log.d(TAG, "bind phone, click correct code");
                Intent intent = new Intent();
                intent.setClass(ActivityRegisterBind2.this, ActivityRegisterBind3.class);
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
