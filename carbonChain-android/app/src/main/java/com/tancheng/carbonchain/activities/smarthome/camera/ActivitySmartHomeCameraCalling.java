package com.tancheng.carbonchain.activities.smarthome.camera;

import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tancheng.carbonchain.R;

public class ActivitySmartHomeCameraCalling extends AppCompatActivity {

    private static final String TAG = "SmartHomeCameraCalling";

    private ImageView mCallingImage;
    private TextView mCallingText;
    private ImageView mNoVideoImage;
    private ImageView mNoVoiceImage;
    private ImageView mSwitchScreenImage;
    private ImageView mMineImage;
    private TextView mRecordingTextView;
    private TextView mSpeedTextView;

    private static final int MSG_CALL_SUCCESS = 1;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(MSG_CALL_SUCCESS == msg.what){
                Log.d(TAG, "call success");
                if(null != mCallingImage) {
                    mCallingImage.setVisibility(View.GONE);
                }
                if(null != mCallingText) {
                    mCallingText.setVisibility(View.GONE);
                }
                if(null != mNoVideoImage){
                    mNoVideoImage.setVisibility(View.VISIBLE);
                }
                if(null != mNoVoiceImage){
                    mNoVoiceImage.setVisibility(View.VISIBLE);
                }
                if(null != mSwitchScreenImage){
                    mSwitchScreenImage.setVisibility(View.VISIBLE);
                }
                if(null != mMineImage){
                    mMineImage.setVisibility(View.VISIBLE);
                }
                if(null != mRecordingTextView){
                    mRecordingTextView.setVisibility(View.VISIBLE);
                }
                if(null != mSpeedTextView){
                    mSpeedTextView.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_home_camera_calling);
        findViews();
        setOnClickListeners();
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            startCallSuccessThread();//模拟呼叫成功,1秒后显示视频界面
        }
    }

    private void findViews(){
        mCallingImage = findViewById(R.id.smart_home_camera_calling_image);
        mCallingText = findViewById(R.id.smart_home_camera_calling_text);
        mNoVideoImage = findViewById(R.id.iv_no_video);
        mNoVoiceImage = findViewById(R.id.iv_no_voice);
        mSwitchScreenImage = findViewById(R.id.switch_screen);
        mMineImage = findViewById(R.id.iv_mine);
        mRecordingTextView = findViewById(R.id.smart_home_camera_recording_text);
        mSpeedTextView = findViewById(R.id.smart_home_camera_speed);
    }

    private void setOnClickListeners(){
        if(null != mSwitchScreenImage){
            mSwitchScreenImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
                        Log.d(TAG, "click switch screen, switch to landscape");
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    }else if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
                        Log.d(TAG, "click switch screen, switch to portrait");
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }
                }
            });
        }
    }

    private void startCallSuccessThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(MSG_CALL_SUCCESS);
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            Log.d(TAG, "on back pressed, switch to portrait");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else {
            super.onBackPressed();
        }
    }
}
