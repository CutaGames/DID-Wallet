package com.tancheng.carbonchain.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tancheng.carbonchain.R;

/**
 * Created by gll on 2020/2/14.
 */

public class SelectableCameraPhoto extends FrameLayout {

    public static final int PHOTO_TYPE_PICTURE = 1;
    public static final int PHOTO_TYPE_VIDEO = 2;
    private int mPhotoType;
    private final Boolean DEBUG = true;
    private final String TAG = getClass().getSimpleName();
    private TextView mShowTime;
    private ImageView mShowImage;
    private ImageView mPlayIconImage;
    private ImageView mSelectIcon;
    private Boolean isSelected = false;

    public SelectableCameraPhoto(@NonNull Context context) {
        super(context);
        initViews();
    }

    public SelectableCameraPhoto(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectableCameraPhoto(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initViews(){
        View view = inflate(getContext(), R.layout.public_selectable_camera_photo_layout, this);
        mShowTime = view.findViewById(R.id.show_time);
        mShowImage = view.findViewById(R.id.show_image);
        mPlayIconImage = findViewById(R.id.play_icon);
        mSelectIcon = findViewById(R.id.select_icon);
    }

    public void setPhotoType(int type){
        this.mPhotoType = type;
        if(PHOTO_TYPE_PICTURE == mPhotoType){
            mShowTime.setVisibility(View.GONE);
            mPlayIconImage.setVisibility(View.GONE);
        }else{
            mShowTime.setVisibility(View.VISIBLE);
            mPlayIconImage.setVisibility(View.VISIBLE);
        }
    }

    public Boolean isPhotoTypePicture(){
        return PHOTO_TYPE_PICTURE == this.mPhotoType;
    }

    public void setIsSelected(Boolean isSelected){
        this.isSelected = isSelected;
        if(isSelected){
            mSelectIcon.setImageResource(R.mipmap.image_icon_select_true);
        }else {
            mSelectIcon.setImageResource(R.mipmap.image_icon_select_false);
        }
    }

    public ImageView getSelectIcon(){
        return mSelectIcon;
    }

    public Boolean getIsSelected(){
        return this.isSelected;
    }

    public void setShowTimeText(String text){
        mShowTime.setText(text);
    }

    public void setShowImage(int resId){
        mShowImage.setImageResource(resId);
    }

    public void cancelLongClickMode(){
        isSelected = false;
        mSelectIcon.setImageResource(R.mipmap.image_icon_select_false);
        mSelectIcon.setVisibility(View.GONE);
    }


}
