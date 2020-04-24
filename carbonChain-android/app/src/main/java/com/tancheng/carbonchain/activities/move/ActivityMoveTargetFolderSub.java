package com.tancheng.carbonchain.activities.move;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.utils.PublicNewFolderUtil;

public class ActivityMoveTargetFolderSub extends AppCompatActivity {

    private Button mBtnNewFolder;
    private Button mBtnConfirm;
    private ViewGroup mViewGroup;
    private PublicNewFolderUtil mPublicNewFolderUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_target_folder_sub);

        findViews();
        initViews();
        setOnClickListeners();
    }

    private void findViews(){
        mBtnNewFolder = findViewById(R.id.new_folder);
        mViewGroup = findViewById(R.id.root_layout);
        mBtnConfirm = findViewById(R.id.confirm_btn);
    }

    private void initViews(){
        mPublicNewFolderUtil = new PublicNewFolderUtil(ActivityMoveTargetFolderSub.this, mViewGroup);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPublicNewFolderUtil.setLayoutParams(layoutParams);
    }

    private void setOnClickListeners(){
        mBtnNewFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPublicNewFolderUtil.isAddToParentView = true;
                mViewGroup.addView(mPublicNewFolderUtil);
            }
        });

        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(mPublicNewFolderUtil.doBackPress()){

        }else {
            super.onBackPressed();
        }
    }
}
