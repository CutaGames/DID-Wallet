package com.tancheng.carbonchain.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tancheng.carbonchain.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityWelcome extends AppCompatActivity {

    private static final Boolean DEBUG = true;
    private static final String TAG = "ActivityWelcome";

    @BindView(R.id.btnStart) Button mBtnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DEBUG) Log.d(TAG, "click start");
                Intent intent = new Intent();
                intent.setClass(com.tancheng.carbonchain.activities.ActivityWelcome.this, ActivityLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
