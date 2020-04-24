package com.tancheng.carbonchain.activities.storage.picture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.utils.PublicBottomOperationsUtil;
import com.tancheng.carbonchain.utils.SelectableImage;

import java.util.ArrayList;
import java.util.List;

public class ActivityPictureTimeYear extends AppCompatActivity {

    private FrameLayout mRootViewGroup;
    private LinearLayout mContainerLayout;
    private int mWindowWidth;
    private int mWindowHeight;
    private List defaultPictureResources;
    private List mSelectableImages;
    private Boolean mOnLongTouchMode = false;
    private PublicBottomOperationsUtil mPublicBottomOperationsUtil;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_time_year);
        mRootViewGroup = (FrameLayout) getWindow().getDecorView().findViewById(R.id.root_layout);
        mContainerLayout = (LinearLayout) getWindow().getDecorView().findViewById(R.id.content);
        mWindowWidth = this.getResources().getDisplayMetrics().widthPixels;
        mWindowHeight = this.getResources().getDisplayMetrics().heightPixels;
        initViews();
        setOnLongClickListeners();
        setOnClickListeners();
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

        mSelectableImages = new ArrayList<SelectableImage>();

        TextView year1TextView = new TextView(this);
        LinearLayout.LayoutParams year1TextViewLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        year1TextViewLayoutParams.setMargins(
                (int)getResources().getDimension(R.dimen.picture_time_title_margin_left),
                (int)getResources().getDimension(R.dimen.picture_time_title_margin_top),
                (int)getResources().getDimension(R.dimen.picture_time_title_margin_right),
                (int)getResources().getDimension(R.dimen.picture_time_title_margin_bottom)
        );
        year1TextView.setLayoutParams(year1TextViewLayoutParams);
        year1TextView.setText(getText(R.string.picture_time_month_default_text_1));
        year1TextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.picture_time_title_text_size));
        year1TextView.setTextColor(getResources().getColor(R.color.picture_time_title_text_color));
        mContainerLayout.addView(year1TextView);

        for(int i = 0; i < 2; i++){
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for(int j = 0; j < 8; j++) {
                SelectableImage selectableImage = new SelectableImage(ActivityPictureTimeYear.this);
                LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                imageLayoutParams.width = mWindowWidth / 8;
                imageLayoutParams.height = mWindowWidth / 8;
                selectableImage.setLayoutParams(imageLayoutParams);
                selectableImage.getShowImage().setImageResource((int) defaultPictureResources.get(j));
                linearLayout.addView(selectableImage);
                mSelectableImages.add(selectableImage);
            }
            mContainerLayout.addView(linearLayout);
        }

        TextView year2TextView = new TextView(this);
        LinearLayout.LayoutParams year2TextViewLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        year2TextViewLayoutParams.setMargins(
                (int)getResources().getDimension(R.dimen.picture_time_title_margin_left),
                (int)getResources().getDimension(R.dimen.picture_time_title_margin_top),
                (int)getResources().getDimension(R.dimen.picture_time_title_margin_right),
                (int)getResources().getDimension(R.dimen.picture_time_title_margin_bottom)
        );
        year2TextView.setLayoutParams(year2TextViewLayoutParams);
        year2TextView.setText(getText(R.string.picture_time_month_default_text_2));
        year2TextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.picture_time_title_text_size));
        year2TextView.setTextColor(getResources().getColor(R.color.picture_time_title_text_color));
        mContainerLayout.addView(year2TextView);

        for(int i = 0; i < 2; i++){
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for(int j = 0; j < 8; j++) {
                SelectableImage selectableImage = new SelectableImage(ActivityPictureTimeYear.this);
                LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                imageLayoutParams.width = mWindowWidth / 8;
                imageLayoutParams.height = mWindowWidth / 8;
                selectableImage.setLayoutParams(imageLayoutParams);
                selectableImage.getShowImage().setImageResource((int) defaultPictureResources.get(j));
                linearLayout.addView(selectableImage);
                mSelectableImages.add(selectableImage);
            }
            mContainerLayout.addView(linearLayout);
        }

        mPublicBottomOperationsUtil = new PublicBottomOperationsUtil(ActivityPictureTimeYear.this, mRootViewGroup);
        RelativeLayout.LayoutParams bottomOperationsLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPublicBottomOperationsUtil.setLayoutParams(bottomOperationsLayoutParams);


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
                    mRootViewGroup.addView(mPublicBottomOperationsUtil);
                }

                return true;
            }
        };

        for(int i = 0; i < mSelectableImages.size(); i++){
            SelectableImage selectableImage = (SelectableImage) mSelectableImages.get(i);
            selectableImage.setOnLongClickListener(onLongClickListener);
        }
    }

    @Override
    public void onBackPressed() {
        if(mPublicBottomOperationsUtil.doBackPressed() == false){
            super.onBackPressed();
        }
        if(mPublicBottomOperationsUtil.isAddToParentView == false){
            mOnLongTouchMode = false;
            cancelLongClickMode();
        }
    }

    private void cancelLongClickMode(){
        for(int i = 0; i < mSelectableImages.size(); i++){
            SelectableImage selectableImage = (SelectableImage) mSelectableImages.get(i);
            selectableImage.cancelLongClickMode();
        }
    }

    private void jumpToPictureDetail(){
        Intent intent = new Intent();
        intent.setClass(ActivityPictureTimeYear.this, ActivityPictureDetail.class);
        startActivity(intent);
    }
}
