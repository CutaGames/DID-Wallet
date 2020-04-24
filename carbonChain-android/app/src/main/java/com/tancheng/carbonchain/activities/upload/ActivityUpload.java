package com.tancheng.carbonchain.activities.upload;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.tancheng.carbonchain.R;


import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityUpload extends AppCompatActivity {

    @BindView(R.id.upload_icons_item_picture)
    ImageView mUploadIconPicture;
    @BindView(R.id.upload_icons_item_video)
    ImageView mUploadIconVideo;

    private static final int RC_CHOOSE_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        ButterKnife.bind(this);

        setOnClickListeners();
    }

    private void setOnClickListeners(){
        mUploadIconPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToPictureFolder = new Intent(ActivityUpload.this, ActivityUploadSelectPictureFolder.class);
                startActivity(intentToPictureFolder);
//                if (ContextCompat.checkSelfPermission(ActivityUpload.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    //未授权，申请授权(从相册选择图片需要读取存储卡的权限)
//                    ActivityCompat.requestPermissions(ActivityUpload.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RC_CHOOSE_PHOTO);
//                } else {
//                    //已授权，获取照片
//                    choosePhoto();
//                }
            }
        });

        mUploadIconVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToPictureFolder = new Intent(ActivityUpload.this, ActivityUploadVideos.class);
                startActivity(intentToPictureFolder);
            }
        });
    }

    /**
     权限申请结果回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RC_CHOOSE_PHOTO:   //相册选择照片权限申请返回
                choosePhoto();
                break;
        }
    }

    private void choosePhoto() {
//        ImagePicker.getInstance()
//                .setTitle("选择图片")//设置标题
//                .showCamera(false)//设置是否显示拍照按钮
//                .showImage(true)//设置是否展示图片
//                .showVideo(true)//设置是否展示视频
//                .setMaxCount(9)//设置最大选择图片数目(默认为1，单选)
//                .start(ActivityUpload.this, RC_CHOOSE_PHOTO);//REQEST_SELECT_IMAGES_CODE为Intent调用的requestCode

//        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
//        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//        startActivityForResult(intentToPickPic, RC_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_CHOOSE_PHOTO && resultCode == RESULT_OK) {
            //List<String> imagePaths = data.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES);
        }
    }
}
