package com.tancheng.carbonchain.activities.asset.digit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.utils.PublicRenameUtil;

public class ActivitySoftWalletDetail extends AppCompatActivity {

    private static final String TAG = "ActivitySoftWalletDetai";
    private ViewGroup mViewGroup;
    private RelativeLayout mWalletNameLayout;
    private PublicRenameUtil mPublicRenameUtil;
    private Button mBtnRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_wallet_detail);
        findViews();
        initViews();
        setOnClickListeners();
    }

    private void findViews(){
        mViewGroup = findViewById(R.id.root_layout);
        mWalletNameLayout = findViewById(R.id.wallet_name_layout);
        mBtnRemove = findViewById(R.id.btn_remove);
    }

    private void initViews(){
        mPublicRenameUtil = new PublicRenameUtil(this);
        RelativeLayout.LayoutParams renameLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPublicRenameUtil.setLayoutParams(renameLayoutParams);
        mPublicRenameUtil.setRenameTitle(getResources().getString(R.string.asset_soft_wallet_change_name_text));
    }

    private void setOnClickListeners(){
        mWalletNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPublicRenameUtil.isAddToParentView = true;
                mViewGroup.addView(mPublicRenameUtil);
            }
        });


        mBtnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mPublicRenameUtil.isAddToParentView) {
                    Log.d(TAG, "click remove");
                    Intent intent = new Intent();
                    intent.setClass(ActivitySoftWalletDetail.this, ActivitySoftWalletRemove.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(mPublicRenameUtil.doBackPress()){

        }else {
            super.onBackPressed();
        }
    }
}
