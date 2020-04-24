package com.tancheng.carbonchain.activities.upload;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityUploadSelectPictureFolder extends AppCompatActivity {

    private static final Boolean DEBUG = true;
    private static final String TAG = "ActiUplSelePicFolder";

    @BindView(R.id.upload_picture_select_folder_body_item_layout_1)
    RelativeLayout mItem1;
    @BindView(R.id.upload_picture_select_folder_body_item_layout_2)
    RelativeLayout mItem2;
    @BindView(R.id.upload_picture_select_folder_body_item_layout_3)
    RelativeLayout mItem3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_select_picture_folder);
        ButterKnife.bind(this);

        setOnClickListeners();
    }

    private void setOnClickListeners(){
        mItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click choose folder");
                Intent intent = new Intent();
                intent.setClass(ActivityUploadSelectPictureFolder.this, ActivityUploadSelectPictureItems.class);
                startActivity(intent);
            }
        });
        mItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click choose folder");
                Intent intent = new Intent();
                intent.setClass(ActivityUploadSelectPictureFolder.this, ActivityUploadSelectPictureItems.class);
                startActivity(intent);
            }
        });
        mItem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click choose folder");
                Intent intent = new Intent();
                intent.setClass(ActivityUploadSelectPictureFolder.this, ActivityUploadSelectPictureItems.class);
                startActivity(intent);
            }
        });
    }

}
