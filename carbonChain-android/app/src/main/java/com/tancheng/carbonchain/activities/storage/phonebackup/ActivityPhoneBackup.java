package com.tancheng.carbonchain.activities.storage.phonebackup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;

public class ActivityPhoneBackup extends AppCompatActivity {

    private RelativeLayout mItemFriendBackupLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_backup);
        findViews();
        setOnClickListeners();
    }

    private void findViews(){
        mItemFriendBackupLayout = findViewById(R.id.phone_backup_friend_layout);
    }

    private void setOnClickListeners(){
        mItemFriendBackupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ActivityPhoneBackup.this, ActivityPhoneBackupDetail.class);
                startActivity(intent);
            }
        });
    }
}
