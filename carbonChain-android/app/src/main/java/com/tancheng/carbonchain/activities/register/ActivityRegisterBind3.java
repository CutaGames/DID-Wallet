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
import com.tancheng.carbonchain.activities.main.ActivityMain;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityRegisterBind3 extends AppCompatActivity {

    private static final Boolean DEBUG = true;
    private static final String TAG = "ActivityRegisterBind2";

    @BindView(R.id.register_bind_password_set_layout)
    LinearLayout mRegisterBindPasswordSetLayout;

    @BindView(R.id.register_bind_password_next)
    Button mRegisterBindPasswordNext;

    @BindView(R.id.register_bind_password_confirm_layout)
    LinearLayout mRegisterBindPasswordConfirmLayout;

    @BindView(R.id.register_bind_password_confirm_save_btn)
    Button mRegisterBindPasswordConfirmSaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bind_3);
        ButterKnife.bind(this);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
            actionBar.setBackgroundDrawable(null);
            actionBar.setHomeAsUpIndicator(R.mipmap.action_bar_icon_back);
        }

        mRegisterBindPasswordNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegisterBindPasswordSetLayout.setVisibility(View.GONE);
                mRegisterBindPasswordConfirmLayout.setVisibility(View.VISIBLE);
            }
        });

        mRegisterBindPasswordConfirmSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DEBUG) Log.d(TAG, "bind phone, click correct code");
                Intent intent = new Intent();
                intent.setClass(ActivityRegisterBind3.this, ActivityMain.class);
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
