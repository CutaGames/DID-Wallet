package com.tancheng.carbonchain.activities.storage.picture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.utils.PublicBottomOperationsUtil;
import com.tancheng.carbonchain.utils.SelectableImage;

import java.util.ArrayList;
import java.util.List;

public class ActivityPictureAlbumDetail extends AppCompatActivity {

    private static final Boolean DEBUG = true;
    private static final String TAG = "ActivityPictureAlbumDet";
    private FrameLayout mViewGroup;
    private LinearLayout mContainer;
    private List defaultPictureResources;
    private int mWindowWidth;
    private List mSelectableImages;
    private Boolean mOnLongTouchMode = false;
    private PublicBottomOperationsUtil mPublicBottomOperationsUtil;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_album_detail);
        mWindowWidth = this.getResources().getDisplayMetrics().widthPixels;
        findViews();
        initViews();
        initBottomOperationUtil();
        setOnLongClickListeners();
        setOnClickListeners();
    }

    private void findViews(){
        mViewGroup = findViewById(R.id.root_layout);
        mContainer = (LinearLayout) findViewById(R.id.container);
    }

    private void initViews(){
        defaultPictureResources = new ArrayList<>();
        defaultPictureResources.add(R.mipmap.picture_default_1);
        defaultPictureResources.add(R.mipmap.picture_default_2);
        defaultPictureResources.add(R.mipmap.picture_default_3);
        defaultPictureResources.add(R.mipmap.picture_default_4);
        defaultPictureResources.add(R.mipmap.picture_default_5);
        defaultPictureResources.add(R.mipmap.picture_default_6);
        defaultPictureResources.add(R.mipmap.picture_default_7);
        defaultPictureResources.add(R.mipmap.picture_default_8);
        defaultPictureResources.add(R.mipmap.picture_default_9);
        defaultPictureResources.add(R.mipmap.picture_default_10);
        defaultPictureResources.add(R.mipmap.picture_default_11);
        defaultPictureResources.add(R.mipmap.picture_default_12);
        mSelectableImages = new ArrayList<SelectableImage>();


        // insert some pictures
        for(int i = 0; i < 3; i++){
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for(int j = 0; j < 4; j++) {
                SelectableImage selectableImage = new SelectableImage(ActivityPictureAlbumDetail.this);
                LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                imageLayoutParams.width = mWindowWidth / 4;
                imageLayoutParams.height = mWindowWidth / 4;
                selectableImage.setLayoutParams(imageLayoutParams);
                selectableImage.getShowImage().setImageResource((int) defaultPictureResources.get(i*3 + j));
                linearLayout.addView(selectableImage);
                mSelectableImages.add(selectableImage);
            }
            mContainer.addView(linearLayout);
        }
    }

    private void initBottomOperationUtil(){
        mPublicBottomOperationsUtil = new PublicBottomOperationsUtil(ActivityPictureAlbumDetail.this, mViewGroup);
        RelativeLayout.LayoutParams bottomOperationsLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPublicBottomOperationsUtil.setLayoutParams(bottomOperationsLayoutParams);
    }

    private void setOnLongClickListeners(){
        View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                for(int i = 0; i < mSelectableImages.size(); i++){
                    SelectableImage selectableImage = (SelectableImage) mSelectableImages.get(i);
                    selectableImage.getSelectIcon().setVisibility(View.VISIBLE);
                }
                SelectableImage selectableImage = (SelectableImage)view;
                if(selectableImage.getIsSelected()){
                    selectableImage.getSelectIcon().setImageResource(R.mipmap.image_icon_select_false);
                    selectableImage.setIsSelected(false);
                }else {
                    selectableImage.getSelectIcon().setImageResource(R.mipmap.image_icon_select_true);
                    selectableImage.setIsSelected(true);
                }
                mOnLongTouchMode = true;

                if(mPublicBottomOperationsUtil.isAddToParentView == false){
                    mPublicBottomOperationsUtil.isAddToParentView = true;
                    mViewGroup.addView(mPublicBottomOperationsUtil);
                }

                return true;
            }
        };

        for(int i = 0; i < mSelectableImages.size(); i++){
            SelectableImage selectableImage = (SelectableImage) mSelectableImages.get(i);
            selectableImage.setOnLongClickListener(onLongClickListener);
        }
    }

    private void setOnClickListeners(){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnLongTouchMode){
                    SelectableImage selectableImage = (SelectableImage) view;
                    if(selectableImage.getIsSelected()){
                        selectableImage.setIsSelected(false);
                        selectableImage.getSelectIcon().setImageResource(R.mipmap.image_icon_select_false);
                    }else{
                        selectableImage.setIsSelected(true);
                        selectableImage.getSelectIcon().setImageResource(R.mipmap.image_icon_select_true);
                    }
                }else{
                    // 跳转到单独图片界面
                    jumpToPictureDetail();
                }
            }
        };

        for(int i = 0; i < mSelectableImages.size(); i++){
            SelectableImage selectableImage = (SelectableImage) mSelectableImages.get(i);
            selectableImage.setOnClickListener(onClickListener);
        }
    }

    private void jumpToPictureDetail(){
        Intent intent = new Intent();
        intent.setClass(ActivityPictureAlbumDetail.this, ActivityPictureDetail.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if(mPublicBottomOperationsUtil.doBackPressed()){
            if(mPublicBottomOperationsUtil.isAddToParentView == false){
                cancelLongClickMode();
                mOnLongTouchMode = false;
            }
        }else {
            super.onBackPressed();
        }
    }

    private void cancelLongClickMode(){
        for(int i = 0; i < mSelectableImages.size(); i++){
            SelectableImage selectableImage = (SelectableImage) mSelectableImages.get(i);
            selectableImage.cancelLongClickMode();
        }
    }
}
