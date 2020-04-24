package com.tancheng.carbonchain.activities.storage.phonebackup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.utils.PhoneBackupConfirmUtil;

public class ActivityPhoneBackupDetail extends AppCompatActivity {
    private static final String TAG = "ActivityPhoneBackupDetail";
    private final Boolean DEBUG = true;
    private ViewGroup mViewGroup;
    private LinearLayout mHistoryContainer;
    private PhoneBackupConfirmUtil mPhoneBackupConfirmUtil;
    private Button mRecoveryBtn;
    private Button mDeleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_backup_detail);
        findViews();
        initViews();
        setOnClickListeners();
    }

    private void findViews(){
        mViewGroup = (ViewGroup)findViewById(R.id.root_layout);
        mHistoryContainer = (LinearLayout)findViewById(R.id.history_container);
        mRecoveryBtn = findViewById(R.id.phone_backup_detail_history_item_recovery);
        mDeleteBtn = findViewById(R.id.phone_backup_detail_history_item_delete);
    }

    private void initViews(){
        mPhoneBackupConfirmUtil = new PhoneBackupConfirmUtil(ActivityPhoneBackupDetail.this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPhoneBackupConfirmUtil.setLayoutParams(layoutParams);
    }

    private void setOnClickListeners(){
        mRecoveryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPhoneBackupConfirmUtil.setConfirmTitleText(getString(R.string.phone_backup_confirm_recovery_text_default_1));
                mPhoneBackupConfirmUtil.setConfirmDeleteText(getString(R.string.phone_recovery_text));
                mPhoneBackupConfirmUtil.isAddToParentView = true;
                mViewGroup.addView(mPhoneBackupConfirmUtil);
            }
        });

        mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPhoneBackupConfirmUtil.setConfirmTitleText(getString(R.string.phone_backup_confirm_delete_text_default_1));
                mPhoneBackupConfirmUtil.setConfirmDeleteText(getString(R.string.public_delete_text));
                mPhoneBackupConfirmUtil.isAddToParentView = true;
                mViewGroup.addView(mPhoneBackupConfirmUtil);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(mPhoneBackupConfirmUtil.doBackPress()){

        }else {
            super.onBackPressed();
        }
    }
}
