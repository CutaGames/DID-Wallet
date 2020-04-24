package com.tancheng.carbonchain.activities.storage.secure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tancheng.carbonchain.R;

public class ActivitySecureUnlock extends AppCompatActivity {

    private GraphicLockView mGraphicLockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secure_unlock);
        findViews();
        setListeners();
    }

    private void findViews(){
        mGraphicLockView = findViewById(R.id.graphic_lock_view);
    }

    private void setListeners(){
        mGraphicLockView.setOnGraphicLockListener(new GraphicLockView.OnGraphicLockListener() {

            @Override
            public void LockSuccess(int what, String password) {
                Intent intent = new Intent();
                intent.setClass(ActivitySecureUnlock.this, ActivitySecure.class);
                startActivity(intent);
                finish();

//                if (what == 0) {
//                    GraphicLockView.mPassword = password;
//                    //text.setText("再次绘制解锁图案");
//                } else {
//                    //SharePreferenceUtil.put(SettingActivity.this, "PASSWORD", password);
//                    ActivitySecureUnlock.this.finish();
//                }
            }

            @Override
            public void LockFailure() {
                //text.setText("与上次绘制的不一致");
            }

            @Override
            public void LockShort() {
                //text.setText("最少连接4个点");
            }
        });
    }
}
