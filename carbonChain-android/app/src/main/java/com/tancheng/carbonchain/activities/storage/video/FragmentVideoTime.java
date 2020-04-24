package com.tancheng.carbonchain.activities.storage.video;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.utils.PublicBottomOperationsUtil;
import com.tancheng.carbonchain.utils.SelectableVideo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gll on 2020/2/13.
 */

public class FragmentVideoTime extends Fragment {


    private int mWindowWidth;
    private int mWindowHeight;
    private PublicBottomOperationsUtil mPublicBottomOperationsUtil;
    private ViewGroup mViewGroup;
    private LinearLayout mVideoBodyLayout;

    private Boolean mOnLongTouchMode = false;
    private List mSelectableVideos;

    public FragmentVideoTime() {
    }


    @SuppressLint("ValidFragment")
    public FragmentVideoTime(ViewGroup viewGroup) {
        this.mViewGroup = viewGroup;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_time, container,false);

        mWindowWidth = this.getResources().getDisplayMetrics().widthPixels;
        mWindowHeight = this.getResources().getDisplayMetrics().heightPixels;

        findViews(view);

        initVideoViews();
        initBottomOperationUtil();

        setOnLongClickListeners();
        setOnClickListeners();
        return view;
    }

    private void findViews(View view){
        mVideoBodyLayout = view.findViewById(R.id.video_body_layout);

    }

    private void initBottomOperationUtil(){
        mPublicBottomOperationsUtil = new PublicBottomOperationsUtil(getContext(), mViewGroup);
        RelativeLayout.LayoutParams bottomOperationsLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPublicBottomOperationsUtil.setLayoutParams(bottomOperationsLayoutParams);
    }

    private void initVideoViews(){
        mSelectableVideos = new ArrayList<SelectableVideo>();
        //创建临时数据
        TextView textView1 = new TextView(getContext());
        LinearLayout.LayoutParams textViewLayoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textViewLayoutParams1.setMargins(
                (int)getResources().getDimension(R.dimen.video_time_default_text_margin_left),
                (int)getResources().getDimension(R.dimen.video_time_default_text_margin_top),
                0,
                (int)getResources().getDimension(R.dimen.video_time_default_text_margin_bottom)
        );
        textView1.setLayoutParams(textViewLayoutParams1);
        textView1.setText(getText(R.string.video_time_default_text_1));
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.video_time_default_text_size));
        textView1.setTextColor(getResources().getColor(R.color.item_title_time_default_text_color));
        mVideoBodyLayout.addView(textView1);

        for(int j = 0; j < 3; j++) {
            SelectableVideo selectableVideo = new SelectableVideo(getContext());
            LinearLayout.LayoutParams videoLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            selectableVideo.setLayoutParams(videoLayoutParams);
            selectableVideo.getShowImage().setImageResource(R.mipmap.video_pre_image_default);
            mSelectableVideos.add(selectableVideo);
            mVideoBodyLayout.addView(selectableVideo);
        }

        TextView textView2 = new TextView(getContext());
        LinearLayout.LayoutParams textViewLayoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textViewLayoutParams2.setMargins(
                (int)getResources().getDimension(R.dimen.video_time_default_text_margin_left),
                (int)getResources().getDimension(R.dimen.video_time_default_text_margin_top),
                0,
                (int)getResources().getDimension(R.dimen.video_time_default_text_margin_bottom)
        );
        textView2.setLayoutParams(textViewLayoutParams2);
        textView2.setText(getText(R.string.video_time_default_text_2));
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.video_time_default_text_size));
        textView2.setTextColor(getResources().getColor(R.color.item_title_time_default_text_color));
        mVideoBodyLayout.addView(textView2);
        for(int j = 0; j < 2; j++) {
            SelectableVideo selectableVideo = new SelectableVideo(getContext());
            LinearLayout.LayoutParams videoLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            selectableVideo.setLayoutParams(videoLayoutParams);
            selectableVideo.getShowImage().setImageResource(R.mipmap.video_pre_image_default);
            mSelectableVideos.add(selectableVideo);
            mVideoBodyLayout.addView(selectableVideo);
        }
    }



    public void selectTime(int position){
        //筛选器 选择不同时间


        //重置时间后，退出长按模式
        removeBottomOperationsIfExits();
    }

    public void removeBottomOperationsIfExits(){
        if(mPublicBottomOperationsUtil.isAddToParentView){
            mOnLongTouchMode = false;
            mPublicBottomOperationsUtil.isAddToParentView = false;
            mViewGroup.removeView(mPublicBottomOperationsUtil);
            cancelLongClickMode();
        }
    }

    public void cancelLongClickMode(){
        mOnLongTouchMode = false;
        for(int i = 0; i < mSelectableVideos.size(); i++){
            SelectableVideo selectableVideo = (SelectableVideo) mSelectableVideos.get(i);
            selectableVideo.cancelLongClickMode();
        }
    }

    private void setOnClickListeners(){
        View.OnClickListener selectableVideoOnClickListener = new View.OnClickListener() {
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
                    // 跳转到单独视频界面
                    jumpToPlayVideo();
                }
            }
        };
        for(int i = 0; i < mSelectableVideos.size(); i++){
            SelectableVideo selectableVideo = (SelectableVideo) mSelectableVideos.get(i);
            selectableVideo.setOnClickListener(selectableVideoOnClickListener);
        }
    }

    private void setOnLongClickListeners(){
        View.OnLongClickListener selectableVideoOnLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                for(int i = 0; i < mSelectableVideos.size(); i++){
                    SelectableVideo selectableVideo = (SelectableVideo) mSelectableVideos.get(i);
                    selectableVideo.getSelectIcon().setVisibility(View.VISIBLE);
                }
                SelectableVideo selectableVideo = (SelectableVideo) view;
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
            selectableVideo.setOnLongClickListener(selectableVideoOnLongClickListener);
        }
    }

    public Boolean doBackPressed() {
        if(mPublicBottomOperationsUtil.doBackPressed() == false){
            return false;
        }
        if(mPublicBottomOperationsUtil.isAddToParentView == false){
            cancelLongClickMode();
            return true;
        }else{
            return true;
        }
    }

    private void jumpToPlayVideo(){
        Intent intent = new Intent();
        intent.setClass(getActivity(), ActivityVideoPlayPortrait.class);
        startActivity(intent);
    }
}
