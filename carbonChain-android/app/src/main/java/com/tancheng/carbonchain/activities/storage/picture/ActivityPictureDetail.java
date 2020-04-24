package com.tancheng.carbonchain.activities.storage.picture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.utils.PublicBottomOperationsUtil;

public class ActivityPictureDetail extends AppCompatActivity {

    private static final Boolean DEBUG = true;
    private static final String TAG = "ActivityPictureDetail";
    private ViewGroup mViewGroup;
    private PublicBottomOperationsUtil mPublicBottomOperationsUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_detail);

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
        mPublicBottomOperationsUtil = new PublicBottomOperationsUtil(ActivityPictureDetail.this, mViewGroup);
        RelativeLayout.LayoutParams bottomOperationsLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPublicBottomOperationsUtil.setLayoutParams(bottomOperationsLayoutParams);
        mPublicBottomOperationsUtil.setTypeImage();

        mPublicBottomOperationsUtil.isAddToParentView = true;
        mViewGroup.addView(mPublicBottomOperationsUtil);
    }

    private void setOnClickListeners(){

    }

    @Override
    public void onBackPressed() {
        if(mPublicBottomOperationsUtil.doBackPressedNotRemoveFromCurrentParent()){

        }else {
            super.onBackPressed();
        }
    }
}
