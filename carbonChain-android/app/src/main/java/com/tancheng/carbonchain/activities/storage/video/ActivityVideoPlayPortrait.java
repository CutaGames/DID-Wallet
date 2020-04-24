package com.tancheng.carbonchain.activities.storage.video;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.utils.PublicBottomOperationsUtil;

public class ActivityVideoPlayPortrait extends AppCompatActivity {

    private ViewGroup mViewGroup;
    private PublicBottomOperationsUtil mPublicBottomOperationsUtil;
    private ImageView mSwitchScreenIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play_portrait);
        findViews();
        setOnClickListeners();
        initBottomOperationUtil();
    }

    private void findViews(){
        mViewGroup = findViewById(R.id.root_layout);
        mSwitchScreenIcon = (ImageView)findViewById(R.id.switch_screen);
    }

    private void setOnClickListeners(){
        mSwitchScreenIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ActivityVideoPlayPortrait.this, ActivityVideoPlayLandscape.class);
                startActivity(intent);
                //finish();
            }
        });
    }

    private void initBottomOperationUtil(){
        mPublicBottomOperationsUtil = new PublicBottomOperationsUtil(ActivityVideoPlayPortrait.this, mViewGroup);
        RelativeLayout.LayoutParams bottomOperationsLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPublicBottomOperationsUtil.setLayoutParams(bottomOperationsLayoutParams);
        mPublicBottomOperationsUtil.isAddToParentView = true;
        mViewGroup.addView(mPublicBottomOperationsUtil);
    }

    @Override
    public void onBackPressed() {
        if(mPublicBottomOperationsUtil.doBackPressedNotRemoveFromCurrentParent()){

        }else {
            super.onBackPressed();
        }
    }
}
