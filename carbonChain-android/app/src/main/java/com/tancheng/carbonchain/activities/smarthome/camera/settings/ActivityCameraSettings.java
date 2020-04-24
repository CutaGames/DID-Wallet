package com.tancheng.carbonchain.activities.smarthome.camera.settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.smarthome.camera.settings.sleep.ActivityCameraSleepSetting;

public class ActivityCameraSettings extends AppCompatActivity {

    private RelativeLayout mSleepSettingLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_settings);
        findViews();
        setOnClickListeners();
    }

    private void findViews(){
        mSleepSettingLayout = findViewById(R.id.camera_sleep_setting);
    }

    private void setOnClickListeners(){
        mSleepSettingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ActivityCameraSettings.this, ActivityCameraSleepSetting.class);
                startActivity(intent);
            }
        });
    }
}
