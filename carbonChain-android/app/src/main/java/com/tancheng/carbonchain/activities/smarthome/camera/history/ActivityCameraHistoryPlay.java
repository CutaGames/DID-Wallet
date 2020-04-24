package com.tancheng.carbonchain.activities.smarthome.camera.history;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;

public class ActivityCameraHistoryPlay extends AppCompatActivity {
    private static final String TAG = "ActivityCameraHistoryPl";

    private ViewGroup mViewGroup;
    private BottomOperationSaveOrDeleteUtil mBottomOperationSaveOrDeleteUtil;
    private ImageView mSwitchScreenImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_history_play);
        findViews();
        initViews();
        setOnClickListeners();
    }

    private void findViews(){
        mViewGroup = findViewById(R.id.root_layout);
        mSwitchScreenImageView = findViewById(R.id.switch_screen);
    }

    private void initViews(){
        mBottomOperationSaveOrDeleteUtil = new BottomOperationSaveOrDeleteUtil(this, mViewGroup);
        RelativeLayout.LayoutParams bottomOperationsLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBottomOperationSaveOrDeleteUtil.setLayoutParams(bottomOperationsLayoutParams);
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            mBottomOperationSaveOrDeleteUtil.isAddToParentView = true;
            mViewGroup.addView(mBottomOperationSaveOrDeleteUtil);
        }
    }

    private void setOnClickListeners(){
        // switch screen
        mSwitchScreenImageView.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onBackPressed() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            Log.d(TAG, "on back pressed, switch to portrait");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else {
            if (mBottomOperationSaveOrDeleteUtil.doBackPressedNotRemoveFromCurrentParent()) {

            } else {
                super.onBackPressed();
            }
        }
    }
}
