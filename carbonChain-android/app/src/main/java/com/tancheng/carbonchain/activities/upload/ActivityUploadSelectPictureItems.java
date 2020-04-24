package com.tancheng.carbonchain.activities.upload;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.move.ActivityMoveTargetFolderMain;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityUploadSelectPictureItems extends AppCompatActivity {

    private static final Boolean DEBUG = true;
    private static final String TAG = "ActiUplSelePicItems";

    @BindView(R.id.upload_select_target_folder_layout)
    RelativeLayout mUploadTargetFolderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_select_picture_items);

        ButterKnife.bind(this);
        setOnClickListeners();
    }

    private void setOnClickListeners(){
        mUploadTargetFolderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click upload target folder");
                Intent intent = new Intent();
                intent.setClass(ActivityUploadSelectPictureItems.this, ActivityMoveTargetFolderMain.class);
                startActivity(intent);
            }
        });
    }
}
