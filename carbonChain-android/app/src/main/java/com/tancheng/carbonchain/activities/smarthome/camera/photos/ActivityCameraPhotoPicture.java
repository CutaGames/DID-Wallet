package com.tancheng.carbonchain.activities.smarthome.camera.photos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;

public class ActivityCameraPhotoPicture extends AppCompatActivity {

    private static final Boolean DEBUG = true;
    private static final String TAG = "ActivityCameraPhotoPicture";
    private ViewGroup mViewGroup;
    private BottomOperationShareOrDeleteUtil mBottomOperationShareOrDeleteUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_photo_picture);

        findViews();
        initViews();
        setOnClickListeners();
    }

    private void findViews(){
        mViewGroup = findViewById(R.id.root_layout);
    }

    private void initViews(){
        initBottomOperationUtil();
    }

    private void initBottomOperationUtil(){
        mBottomOperationShareOrDeleteUtil = new BottomOperationShareOrDeleteUtil(this, mViewGroup);
        RelativeLayout.LayoutParams bottomOperationsLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBottomOperationShareOrDeleteUtil.setLayoutParams(bottomOperationsLayoutParams);

        mBottomOperationShareOrDeleteUtil.isAddToParentView = true;
        mViewGroup.addView(mBottomOperationShareOrDeleteUtil);
    }

    private void setOnClickListeners(){

    }

    @Override
    public void onBackPressed() {
        if(mBottomOperationShareOrDeleteUtil.doBackPressedNotRemoveFromCurrentParent()){

        }else {
            super.onBackPressed();
        }
    }
}
