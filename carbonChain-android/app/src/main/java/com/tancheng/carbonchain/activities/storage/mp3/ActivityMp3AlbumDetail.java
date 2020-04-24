package com.tancheng.carbonchain.activities.storage.mp3;

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
import com.tancheng.carbonchain.utils.SelectableMp3;

import java.util.ArrayList;
import java.util.List;

public class ActivityMp3AlbumDetail extends AppCompatActivity {

    private static final Boolean DEBUG = true;
    private static final String TAG = "ActivityMp3AlbumDetail";
    private FrameLayout mViewGroup;
    private LinearLayout mContainer;
    private List mSelectableMp3s;
    private Boolean mOnLongTouchMode = false;
    private PublicBottomOperationsUtil mPublicBottomOperationsUtil;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3_album_detail);
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
        mSelectableMp3s = new ArrayList<SelectableMp3>();
        // insert some videos
        for(int j = 0; j < 4; j++) {
            SelectableMp3 selectableMp3 = new SelectableMp3(ActivityMp3AlbumDetail.this);
            LinearLayout.LayoutParams videoLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            selectableMp3.setLayoutParams(videoLayoutParams);
            selectableMp3.getShowImage().setImageResource(R.mipmap.mp3_pre_image_default);
            mSelectableMp3s.add(selectableMp3);
            mContainer.addView(selectableMp3);
        }
    }

    private void initBottomOperationUtil(){
        mPublicBottomOperationsUtil = new PublicBottomOperationsUtil(ActivityMp3AlbumDetail.this, mViewGroup);
        RelativeLayout.LayoutParams bottomOperationsLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPublicBottomOperationsUtil.setLayoutParams(bottomOperationsLayoutParams);
    }

    private void setOnLongClickListeners(){
        View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                for(int i = 0; i < mSelectableMp3s.size(); i++){
                    SelectableMp3 selectableMp3 = (SelectableMp3) mSelectableMp3s.get(i);
                    selectableMp3.getSelectIcon().setVisibility(View.VISIBLE);
                }
                SelectableMp3 selectableMp3 = (SelectableMp3)view;
                if(selectableMp3.getIsSelected()){
                    selectableMp3.getSelectIcon().setImageResource(R.mipmap.image_icon_select_false);
                    selectableMp3.setIsSelected(false);
                }else {
                    selectableMp3.getSelectIcon().setImageResource(R.mipmap.image_icon_select_true);
                    selectableMp3.setIsSelected(true);
                }
                mOnLongTouchMode = true;

                if(mPublicBottomOperationsUtil.isAddToParentView == false){
                    mPublicBottomOperationsUtil.isAddToParentView = true;
                    mViewGroup.addView(mPublicBottomOperationsUtil);
                }

                return true;
            }
        };

        for(int i = 0; i < mSelectableMp3s.size(); i++){
            SelectableMp3 selectableMp3 = (SelectableMp3) mSelectableMp3s.get(i);
            selectableMp3.setOnLongClickListener(onLongClickListener);
        }
    }

    private void setOnClickListeners(){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnLongTouchMode){
                    SelectableMp3 selectableMp3 = (SelectableMp3) view;
                    if(selectableMp3.getIsSelected()){
                        selectableMp3.setIsSelected(false);
                        selectableMp3.getSelectIcon().setImageResource(R.mipmap.image_icon_select_false);
                    }else{
                        selectableMp3.setIsSelected(true);
                        selectableMp3.getSelectIcon().setImageResource(R.mipmap.image_icon_select_true);
                    }
                }else{
                    // 跳转到视频播放界面
                    jumpToPlayMp3();
                }
            }
        };

        for(int i = 0; i < mSelectableMp3s.size(); i++){
            SelectableMp3 selectableMp3 = (SelectableMp3) mSelectableMp3s.get(i);
            selectableMp3.setOnClickListener(onClickListener);
        }
    }

    private void jumpToPlayMp3(){
        Intent intent = new Intent();
        intent.setClass(ActivityMp3AlbumDetail.this, ActivityMp3Play.class);
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
        for(int i = 0; i < mSelectableMp3s.size(); i++){
            SelectableMp3 selectableMp3 = (SelectableMp3) mSelectableMp3s.get(i);
            selectableMp3.cancelLongClickMode();
        }
    }
}
