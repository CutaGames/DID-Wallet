package com.tancheng.carbonchain.activities.storage.mp3;

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
import com.tancheng.carbonchain.utils.SelectableMp3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gll on 2020/2/13.
 */

public class FragmentMp3All extends Fragment {
    private static final Boolean DEBUG = true;
    private static final String TAG = "FragmentMp3All";

    private ViewGroup mViewGroup;
    private LinearLayout mMp3BodyLayout;
    private PublicBottomOperationsUtil mPublicBottomOperationsUtil;
    private Boolean mOnLongTouchMode = false;
    private List mSelectableMp3s;


    public FragmentMp3All() {
    }

    @SuppressLint("ValidFragment")
    public FragmentMp3All(ViewGroup viewGroup) {
        this.mViewGroup = viewGroup;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mp3_all, container,false);
        findViews(view);

        initMp3Views();

        initBottomOperationUtil();

        setOnLongClickListeners();
        setOnClickListeners();

        return view;
    }

    private void findViews(View view){
        mMp3BodyLayout = view.findViewById(R.id.mp3_body_layout);
    }

    private void initMp3Views() {
        mSelectableMp3s = new ArrayList<SelectableMp3>();
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
        mMp3BodyLayout.addView(textView1);

        for(int j = 0; j < 3; j++) {
            SelectableMp3 selectableMp3 = new SelectableMp3(getContext());
            LinearLayout.LayoutParams videoLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            selectableMp3.setLayoutParams(videoLayoutParams);
            selectableMp3.getShowImage().setImageResource(R.mipmap.mp3_pre_image_default);
            mSelectableMp3s.add(selectableMp3);
            mMp3BodyLayout.addView(selectableMp3);
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
        mMp3BodyLayout.addView(textView2);
        for(int j = 0; j < 2; j++) {
            SelectableMp3 selectableMp3 = new SelectableMp3(getContext());
            LinearLayout.LayoutParams videoLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            selectableMp3.setLayoutParams(videoLayoutParams);
            selectableMp3.getShowImage().setImageResource(R.mipmap.mp3_pre_image_default);
            mSelectableMp3s.add(selectableMp3);
            mMp3BodyLayout.addView(selectableMp3);
        }
    }

    private void initBottomOperationUtil(){
        mPublicBottomOperationsUtil = new PublicBottomOperationsUtil(getContext(), mViewGroup);
        RelativeLayout.LayoutParams bottomOperationsLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPublicBottomOperationsUtil.setLayoutParams(bottomOperationsLayoutParams);
    }

    private void setOnLongClickListeners(){
        View.OnLongClickListener selectableMp3OnLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                for(int i = 0; i < mSelectableMp3s.size(); i++){
                    SelectableMp3 selectableMp3 = (SelectableMp3) mSelectableMp3s.get(i);
                    selectableMp3.getSelectIcon().setVisibility(View.VISIBLE);
                }
                SelectableMp3 selectableMp3 = (SelectableMp3) view;
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
            selectableMp3.setOnLongClickListener(selectableMp3OnLongClickListener);
        }
    }

    private void setOnClickListeners(){
        View.OnClickListener selectableMp3OnClickListener = new View.OnClickListener() {
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
                    // 跳转到单独音乐界面
                    jumpToPlayMp3();
                }
            }
        };
        for(int i = 0; i < mSelectableMp3s.size(); i++){
            SelectableMp3 selectableMp3 = (SelectableMp3) mSelectableMp3s.get(i);
            selectableMp3.setOnClickListener(selectableMp3OnClickListener);
        }
    }

    private void jumpToPlayMp3(){
        Intent intent = new Intent();
        intent.setClass(getActivity(), ActivityMp3Play.class);
        startActivity(intent);
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

    public void cancelLongClickMode(){
        mOnLongTouchMode = false;
        for(int i = 0; i < mSelectableMp3s.size(); i++){
            SelectableMp3 selectableMp3 = (SelectableMp3) mSelectableMp3s.get(i);
            selectableMp3.cancelLongClickMode();
        }
    }

    public void removeBottomOperationsIfExits(){
        if(mPublicBottomOperationsUtil.isAddToParentView){
            mOnLongTouchMode = false;
            mPublicBottomOperationsUtil.isAddToParentView = false;
            mViewGroup.removeView(mPublicBottomOperationsUtil);
            cancelLongClickMode();
        }
    }

}
