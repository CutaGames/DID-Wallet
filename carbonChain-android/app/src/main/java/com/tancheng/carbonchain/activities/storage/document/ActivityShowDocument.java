package com.tancheng.carbonchain.activities.storage.document;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.utils.PublicBottomOperationsUtil;

public class ActivityShowDocument extends AppCompatActivity {

    private ViewGroup mViewGroup;
    private PublicBottomOperationsUtil mPublicBottomOperationsUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_document);

        findViews();
        inits();
        initBottomOperationUtil();
    }

    private void findViews() {
        mViewGroup = findViewById(R.id.root_layout);
    }

    private void inits(){

    }

    private void setOnClickListeners(){}

    private void initBottomOperationUtil(){
        mPublicBottomOperationsUtil = new PublicBottomOperationsUtil(ActivityShowDocument.this, mViewGroup);
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
