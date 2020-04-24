package com.tancheng.carbonchain.activities.move;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityMoveTargetFolderMain extends AppCompatActivity {

    @BindView(R.id.upload_target_folder_main_my_space)
    RelativeLayout mMainMySpaceLayout;

    @BindView(R.id.upload_target_folder_main_public_space)
    RelativeLayout mMainPublicSpaceLayout;

    @BindView(R.id.upload_target_folder_main_my_collect)
    RelativeLayout mMainMyCollectLayout;

    @BindView(R.id.upload_target_folder_main_safe_box)
    RelativeLayout mMainSafeBoxLayout;

    private static final Boolean DEBUG = true;
    private static final String TAG = "ActiUplTargetFolderMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_target_folder_main);
        ButterKnife.bind(this);

        setOnClickListeners();
    }

    private void setOnClickListeners(){
        mMainMySpaceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click upload target folder my space");
                Intent intent = new Intent();
                intent.setClass(ActivityMoveTargetFolderMain.this, ActivityMoveTargetFolderSub.class);
                startActivity(intent);
            }
        });

        mMainPublicSpaceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click upload target folder my space");
                Intent intent = new Intent();
                intent.setClass(ActivityMoveTargetFolderMain.this, ActivityMoveTargetFolderSub.class);
                startActivity(intent);
            }
        });

        mMainMyCollectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click upload target folder my space");
                Intent intent = new Intent();
                intent.setClass(ActivityMoveTargetFolderMain.this, ActivityMoveTargetFolderSub.class);
                startActivity(intent);
            }
        });

        mMainSafeBoxLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click upload target folder my space");
                Intent intent = new Intent();
                intent.setClass(ActivityMoveTargetFolderMain.this, ActivityMoveTargetFolderSub.class);
                startActivity(intent);
            }
        });
    }
}
