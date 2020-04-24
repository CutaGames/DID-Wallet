package com.tancheng.carbonchain.activities.storage.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.utils.PublicBottomOperationsUtil;
import com.tancheng.carbonchain.utils.SelectableVideo;

import java.util.ArrayList;
import java.util.List;

public class ActivityVideoAlbumDetail extends AppCompatActivity {

    private static final Boolean DEBUG = true;
    private static final String TAG = "ActivityVideoAlbumDetail";
    private FrameLayout mViewGroup;
    private LinearLayout mContainer;
    private List mSelectableVideos;
    private Boolean mOnLongTouchMode = false;
    private PublicBottomOperationsUtil mPublicBottomOperationsUtil;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_album_detail);
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
        mSelectableVideos = new ArrayList<SelectableVideo>();
        // insert some videos
        for(int j = 0; j < 4; j++) {
            SelectableVideo selectableVideo = new SelectableVideo(ActivityVideoAlbumDetail.this);
            LinearLayout.LayoutParams videoLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            selectableVideo.setLayoutParams(videoLayoutParams);
            selectableVideo.getShowImage().setImageResource(R.mipmap.video_pre_image_default);
            mSelectableVideos.add(selectableVideo);
            mContainer.addView(selectableVideo);
        }
    }

    private void initBottomOperationUtil(){
        mPublicBottomOperationsUtil = new PublicBottomOperationsUtil(ActivityVideoAlbumDetail.this, mViewGroup);
        RelativeLayout.LayoutParams bottomOperationsLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPublicBottomOperationsUtil.setLayoutParams(bottomOperationsLayoutParams);
    }

    private void setOnLongClickListeners(){
        View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                for(int i = 0; i < mSelectableVideos.size(); i++){
                    SelectableVideo selectableVideo = (SelectableVideo) mSelectableVideos.get(i);
                    selectableVideo.getSelectIcon().setVisibility(View.VISIBLE);
                }
                SelectableVideo selectableVideo = (SelectableVideo)view;
                if(selectableVideo.getIsSelected()){
                    selectableVideo.getSelectIcon().setImageResource(R.mipmap.image_icon_select_false);
                    selectableVideo.setIsSelected(false);
                }else {
                    selectableVideo.getSelectIcon().setImageResource(R.mipmap.image_icon_select_true);
                    selectableVideo.setIsSelected(true);
                }
                mOnLongTouchMode = true;

                if(mPublicBottomOperationsUtil.isAddToParentView == false){
                    mPublicBottomOperationsUtil.isAddToParentView = true;
                    mViewGroup.addView(mPublicBottomOperationsUtil);
                }

                return true;
            }
        };

        for(int i = 0; i < mSelectableVideos.size(); i++){
            SelectableVideo selectableVideo = (SelectableVideo) mSelectableVideos.get(i);
            selectableVideo.setOnLongClickListener(onLongClickListener);
        }
    }

    private void setOnClickListeners(){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnLongTouchMode){
                    SelectableVideo selectableVideo = (SelectableVideo) view;
                    if(selectableVideo.getIsSelected()){
                        selectableVideo.setIsSelected(false);
                        selectableVideo.getSelectIcon().setImageResource(R.mipmap.image_icon_select_false);
                    }else{
                        selectableVideo.setIsSelected(true);
                        selectableVideo.getSelectIcon().setImageResource(R.mipmap.image_icon_select_true);
                    }
                }else{
                    // 跳转到视频播放界面
                    jumpToPlayVideo();
                }
            }
        };

        for(int i = 0; i < mSelectableVideos.size(); i++){
            SelectableVideo selectableVideo = (SelectableVideo) mSelectableVideos.get(i);
            selectableVideo.setOnClickListener(onClickListener);
        }
    }

    private void jumpToPlayVideo(){
        Intent intent = new Intent();
        intent.setClass(ActivityVideoAlbumDetail.this, ActivityVideoPlayPortrait.class);
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
        for(int i = 0; i < mSelectableVideos.size(); i++){
            SelectableVideo selectableVideo = (SelectableVideo) mSelectableVideos.get(i);
            selectableVideo.cancelLongClickMode();
        }
    }
}
