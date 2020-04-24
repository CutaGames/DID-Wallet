package com.tancheng.carbonchain.activities.smarthome.camera.photos;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.utils.SelectableCameraPhoto;

import java.util.ArrayList;
import java.util.List;

public class ActivityCameraPhotos extends AppCompatActivity {

    private ViewGroup mViewGroup;
    private LinearLayout mNoneLayout;
    private LinearLayout mContainer;
    private static final int MSG_LOAD_SUCCESS = 1;
    private int mWindowWidth;
    private List mSelectablePhotos;
    private Boolean mOnLongTouchMode = false;
    private BottomOperationShareOrDeleteUtil mBottomOperationShareOrDeleteUtil;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (MSG_LOAD_SUCCESS == msg.what) {
                mNoneLayout.setVisibility(View.GONE);
                mContainer.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_photos);
        mWindowWidth = this.getResources().getDisplayMetrics().widthPixels;
        findViews();
        initViews();
        setOnLongClickListeners();
        setOnClickListeners();
        startCallSuccessThread();
    }

    private void findViews(){
        mViewGroup = (ViewGroup)findViewById(R.id.root_layout);
        mNoneLayout = (LinearLayout)findViewById(R.id.none_layout);
        mContainer = (LinearLayout)findViewById(R.id.container);
    }

    private void startCallSuccessThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
            }
        }).start();
    }

    private void initViews(){
        mSelectablePhotos = new ArrayList<SelectableCameraPhoto>();

        mBottomOperationShareOrDeleteUtil = new BottomOperationShareOrDeleteUtil(this, mViewGroup);
        RelativeLayout.LayoutParams bottomOperationsLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBottomOperationShareOrDeleteUtil.setLayoutParams(bottomOperationsLayoutParams);

        // insert some views
        TextView time1TextView = new TextView(this);
        LinearLayout.LayoutParams time1TextViewLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        time1TextViewLayoutParams.setMargins(
                (int)getResources().getDimension(R.dimen.camera_history_time_margin_left),
                (int)getResources().getDimension(R.dimen.camera_history_time_margin_top),
                (int)getResources().getDimension(R.dimen.camera_history_time_margin_right),
                (int)getResources().getDimension(R.dimen.camera_history_time_margin_bottom)
        );
        time1TextView.setLayoutParams(time1TextViewLayoutParams);
        time1TextView.setText(getText(R.string.video_time_default_text_1));
        time1TextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.public_text_size_15_sp));
        time1TextView.setTextColor(getResources().getColor(R.color.camera_history_time_title_text_color));
        mContainer.addView(time1TextView);

        for(int i = 0; i < 2; i++){
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = getResources().getDimensionPixelOffset(R.dimen.camera_history_time_line_margin_left);
            layoutParams.rightMargin = getResources().getDimensionPixelOffset(R.dimen.camera_history_time_line_margin_right);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for(int j = 0; j < 4; j++) {
                SelectableCameraPhoto selectablePhoto = new SelectableCameraPhoto(this);
                LinearLayout.LayoutParams photoLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                photoLayoutParams.width = (mWindowWidth - layoutParams.leftMargin - layoutParams.rightMargin) / 4;
                photoLayoutParams.height = photoLayoutParams.width;
                selectablePhoto.setLayoutParams(photoLayoutParams);

                if(i % 2 == 0) {
                    selectablePhoto.setPhotoType(SelectableCameraPhoto.PHOTO_TYPE_PICTURE);
                }else{
                    selectablePhoto.setPhotoType(SelectableCameraPhoto.PHOTO_TYPE_VIDEO);
                }
                selectablePhoto.setShowImage(R.mipmap.picture_default_11);
                selectablePhoto.setShowTimeText("00:00:23");
                linearLayout.addView(selectablePhoto);
                mSelectablePhotos.add(selectablePhoto);
            }
            mContainer.addView(linearLayout);
        }

        TextView time2TextView = new TextView(this);
        LinearLayout.LayoutParams time2TextViewLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        time2TextViewLayoutParams.setMargins(
                (int)getResources().getDimension(R.dimen.camera_history_time_margin_left),
                (int)getResources().getDimension(R.dimen.camera_history_time_margin_top),
                (int)getResources().getDimension(R.dimen.camera_history_time_margin_right),
                (int)getResources().getDimension(R.dimen.camera_history_time_margin_bottom)
        );
        time2TextView.setLayoutParams(time2TextViewLayoutParams);
        time2TextView.setText(getText(R.string.video_time_default_text_2));
        time2TextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.public_text_size_15_sp));
        time2TextView.setTextColor(getResources().getColor(R.color.camera_history_time_title_text_color));
        mContainer.addView(time2TextView);

        for(int i = 0; i < 2; i++){
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = getResources().getDimensionPixelOffset(R.dimen.camera_history_time_line_margin_left);
            layoutParams.rightMargin = getResources().getDimensionPixelOffset(R.dimen.camera_history_time_line_margin_right);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for(int j = 0; j < 4; j++) {
                SelectableCameraPhoto selectablePhoto = new SelectableCameraPhoto(this);
                LinearLayout.LayoutParams photoLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                photoLayoutParams.width = (mWindowWidth - layoutParams.leftMargin - layoutParams.rightMargin) / 4;
                photoLayoutParams.height = photoLayoutParams.width;
                selectablePhoto.setLayoutParams(photoLayoutParams);

                if(i % 2 == 0) {
                    selectablePhoto.setPhotoType(SelectableCameraPhoto.PHOTO_TYPE_PICTURE);
                }else{
                    selectablePhoto.setPhotoType(SelectableCameraPhoto.PHOTO_TYPE_VIDEO);
                }
                selectablePhoto.setShowImage(R.mipmap.picture_default_1);
                selectablePhoto.setShowTimeText("00:00:23");
                linearLayout.addView(selectablePhoto);
                mSelectablePhotos.add(selectablePhoto);
            }
            mContainer.addView(linearLayout);
        }
    }

    private void setOnLongClickListeners(){
        View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mOnLongTouchMode = true;
                for(int i = 0; i < mSelectablePhotos.size(); i++){
                    SelectableCameraPhoto selectableCameraPhoto = (SelectableCameraPhoto) mSelectablePhotos.get(i);
                    selectableCameraPhoto.getSelectIcon().setVisibility(View.VISIBLE);
                }
                SelectableCameraPhoto selectableCameraHistoryHour = (SelectableCameraPhoto)view;

                if (selectableCameraHistoryHour.getIsSelected()) {
                    selectableCameraHistoryHour.setIsSelected(false);
                } else {
                    selectableCameraHistoryHour.setIsSelected(true);
                }


                if(mBottomOperationShareOrDeleteUtil.isAddToParentView == false){
                    mBottomOperationShareOrDeleteUtil.isAddToParentView = true;
                    mViewGroup.addView(mBottomOperationShareOrDeleteUtil);
                }

                return true;
            }
        };

        for(int i = 0; i < mSelectablePhotos.size(); i++){
            SelectableCameraPhoto selectableCameraPhoto = (SelectableCameraPhoto) mSelectablePhotos.get(i);
            selectableCameraPhoto.setOnLongClickListener(onLongClickListener);
        }

    }


    private void setOnClickListeners(){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectableCameraPhoto selectableCameraPhoto = (SelectableCameraPhoto) view;
                if(mOnLongTouchMode){
                    if (selectableCameraPhoto.getIsSelected()) {
                        selectableCameraPhoto.setIsSelected(false);
                    } else {
                        selectableCameraPhoto.setIsSelected(true);
                    }
                }else{
                    if(selectableCameraPhoto.isPhotoTypePicture()){
                        // 跳转到图片界面
                        jumpToCameraPhotoPicture();
                    }else{
                        // 跳转到视频界面
                        jumpToCameraPhotoVideo();
                    }
                }
            }
        };

        for(int i = 0; i < mSelectablePhotos.size(); i++){
            SelectableCameraPhoto selectablePhoto = (SelectableCameraPhoto) mSelectablePhotos.get(i);
            selectablePhoto.setOnClickListener(onClickListener);
        }
    }

    @Override
    public void onBackPressed() {
        if(mBottomOperationShareOrDeleteUtil.doBackPress() == false){
            super.onBackPressed();
        }
        if(mBottomOperationShareOrDeleteUtil.isAddToParentView){

        }else {
            mOnLongTouchMode = false;
            cancelLongClickMode();
        }
    }

    private void cancelLongClickMode(){
        for(int i = 0; i < mSelectablePhotos.size(); i++){
            SelectableCameraPhoto selectableCameraPhoto = (SelectableCameraPhoto) mSelectablePhotos.get(i);
            selectableCameraPhoto.cancelLongClickMode();
        }
    }

    private void jumpToCameraPhotoPicture(){
        Intent intent = new Intent();
        intent.setClass(ActivityCameraPhotos.this, ActivityCameraPhotoPicture.class);
        startActivity(intent);
    }

    private void jumpToCameraPhotoVideo(){
        Intent intent = new Intent();
        intent.setClass(ActivityCameraPhotos.this, ActivityCameraPhotoVideo.class);
        startActivity(intent);
    }

}
