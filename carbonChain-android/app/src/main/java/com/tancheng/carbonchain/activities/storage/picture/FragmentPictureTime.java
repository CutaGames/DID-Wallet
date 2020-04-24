package com.tancheng.carbonchain.activities.storage.picture;

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
import com.tancheng.carbonchain.utils.SelectableImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gll on 2020/2/13.
 */

public class FragmentPictureTime extends Fragment {

    private LinearLayout mYearLayout;
    private LinearLayout mMonthLayout;
    private LinearLayout mDayLayout;

    private RelativeLayout mYearItem1;
    private RelativeLayout mYearItem2;
    private RelativeLayout mYearItem3;
    private int mWindowWidth;
    private int mWindowHeight;
    private List defaultPictureResources;
    private List mMonthSelectableImages;
    private List mDaySelectableImages;
    private PublicBottomOperationsUtil mPublicBottomOperationsUtil;
    private ViewGroup mViewGroup;

    private Boolean mOnLongTouchMode = false;

    public FragmentPictureTime() {
    }


    @SuppressLint("ValidFragment")
    public FragmentPictureTime(ViewGroup viewGroup) {
        this.mViewGroup = viewGroup;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture_time, container,false);

        defaultPictureResources = new ArrayList<>();
        defaultPictureResources.add(R.mipmap.picture_default_1);
        defaultPictureResources.add(R.mipmap.picture_default_2);
        defaultPictureResources.add(R.mipmap.picture_default_3);
        defaultPictureResources.add(R.mipmap.picture_default_4);
        defaultPictureResources.add(R.mipmap.picture_default_5);
        defaultPictureResources.add(R.mipmap.picture_default_6);
        defaultPictureResources.add(R.mipmap.picture_default_7);
        defaultPictureResources.add(R.mipmap.picture_default_8);

        mWindowWidth = this.getResources().getDisplayMetrics().widthPixels;
        mWindowHeight = this.getResources().getDisplayMetrics().heightPixels;

        findViews(view);

        initMonthViews();
        initDayViews();
        initBottomOperationUtil();

        setOnLongClickListeners();
        setOnClickListeners();
        return view;
    }

    private void findViews(View view){
        mYearLayout = view.findViewById(R.id.picture_time_year_layout);    // 0
        mMonthLayout = view.findViewById(R.id.picture_time_month_layout);  // 1
        mDayLayout = view.findViewById(R.id.picture_time_day_layout);      // 2

        mYearItem1 = view.findViewById(R.id.upload_picture_select_folder_body_item_layout_1);
        mYearItem2 = view.findViewById(R.id.upload_picture_select_folder_body_item_layout_2);
        mYearItem3 = view.findViewById(R.id.upload_picture_select_folder_body_item_layout_3);
    }

    private void initBottomOperationUtil(){
        mPublicBottomOperationsUtil = new PublicBottomOperationsUtil(getContext(), mViewGroup);
        RelativeLayout.LayoutParams bottomOperationsLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPublicBottomOperationsUtil.setLayoutParams(bottomOperationsLayoutParams);
    }

    private void initMonthViews(){
        mMonthSelectableImages = new ArrayList<SelectableImage>();
        TextView month1TextView = new TextView(getContext());
        LinearLayout.LayoutParams month1TextViewLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        month1TextViewLayoutParams.setMargins(
                (int)getResources().getDimension(R.dimen.picture_time_title_margin_left),
                (int)getResources().getDimension(R.dimen.picture_time_title_margin_top),
                (int)getResources().getDimension(R.dimen.picture_time_title_margin_right),
                (int)getResources().getDimension(R.dimen.picture_time_title_margin_bottom)
        );
        month1TextView.setLayoutParams(month1TextViewLayoutParams);
        month1TextView.setText(getText(R.string.picture_time_month_default_text_1));
        month1TextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.picture_time_title_text_size));
        month1TextView.setTextColor(getResources().getColor(R.color.picture_time_title_text_color));
        mMonthLayout.addView(month1TextView);

        for(int i = 0; i < 2; i++){
            LinearLayout linearLayout = new LinearLayout(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for(int j = 0; j < 8; j++) {
                SelectableImage selectableImage = new SelectableImage(getContext());
                LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(mWindowWidth / 8, mWindowWidth / 8);
                selectableImage.setLayoutParams(imageLayoutParams);
                selectableImage.getShowImage().setImageResource((int) defaultPictureResources.get(j));
                linearLayout.addView(selectableImage);
                mMonthSelectableImages.add(selectableImage);
            }
            mMonthLayout.addView(linearLayout);
        }

        TextView month2TextView = new TextView(getContext());
        LinearLayout.LayoutParams month2TextViewLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        month2TextViewLayoutParams.setMargins(
                (int)getResources().getDimension(R.dimen.picture_time_title_margin_left),
                (int)getResources().getDimension(R.dimen.picture_time_title_margin_top),
                (int)getResources().getDimension(R.dimen.picture_time_title_margin_right),
                (int)getResources().getDimension(R.dimen.picture_time_title_margin_bottom)
        );
        month2TextView.setLayoutParams(month2TextViewLayoutParams);
        month2TextView.setText(getText(R.string.picture_time_month_default_text_2));
        month2TextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.picture_time_title_text_size));
        month2TextView.setTextColor(getResources().getColor(R.color.picture_time_title_text_color));
        mMonthLayout.addView(month2TextView);

        for(int i = 0; i < 2; i++){
            LinearLayout linearLayout = new LinearLayout(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for(int j = 0; j < 8; j++) {
                SelectableImage selectableImage = new SelectableImage(getContext());
                LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(mWindowWidth / 8, mWindowWidth / 8);
                selectableImage.setLayoutParams(imageLayoutParams);
                selectableImage.getShowImage().setImageResource((int) defaultPictureResources.get(j));
                linearLayout.addView(selectableImage);
                mMonthSelectableImages.add(selectableImage);
            }
            mMonthLayout.addView(linearLayout);
        }
    }

    private void initDayViews(){
        mDaySelectableImages = new ArrayList<SelectableImage>();
        TextView day1TextView = new TextView(getContext());
        LinearLayout.LayoutParams day1TextViewLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        day1TextViewLayoutParams.setMargins(
                (int)getResources().getDimension(R.dimen.picture_time_title_margin_left),
                (int)getResources().getDimension(R.dimen.picture_time_title_margin_top),
                (int)getResources().getDimension(R.dimen.picture_time_title_margin_right),
                (int)getResources().getDimension(R.dimen.picture_time_title_margin_bottom)
        );
        day1TextView.setLayoutParams(day1TextViewLayoutParams);
        day1TextView.setText(getText(R.string.picture_time_day_default_text_1));
        day1TextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.picture_time_title_text_size));
        day1TextView.setTextColor(getResources().getColor(R.color.picture_time_title_text_color));
        mDayLayout.addView(day1TextView);

        for(int i = 0; i < 2; i++){
            LinearLayout linearLayout = new LinearLayout(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for(int j = 0; j < 4; j++) {
                SelectableImage selectableImage = new SelectableImage(getContext());
                LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(mWindowWidth / 4, mWindowWidth / 4);
                selectableImage.setLayoutParams(imageLayoutParams);
                selectableImage.getShowImage().setImageResource((int) defaultPictureResources.get(j));
                linearLayout.addView(selectableImage);
                mDaySelectableImages.add(selectableImage);
            }
            mDayLayout.addView(linearLayout);
        }

        TextView day2TextView = new TextView(getContext());
        LinearLayout.LayoutParams day2TextViewLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        day2TextViewLayoutParams.setMargins(
                (int)getResources().getDimension(R.dimen.picture_time_title_margin_left),
                (int)getResources().getDimension(R.dimen.picture_time_title_margin_top),
                (int)getResources().getDimension(R.dimen.picture_time_title_margin_right),
                (int)getResources().getDimension(R.dimen.picture_time_title_margin_bottom)
        );
        day2TextView.setLayoutParams(day2TextViewLayoutParams);
        day2TextView.setText(getText(R.string.picture_time_day_default_text_2));
        day2TextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.picture_time_title_text_size));
        day2TextView.setTextColor(getResources().getColor(R.color.picture_time_title_text_color));
        mDayLayout.addView(day2TextView);

        for(int i = 0; i < 2; i++){
            LinearLayout linearLayout = new LinearLayout(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for(int j = 0; j < 4; j++) {
                SelectableImage selectableImage = new SelectableImage(getContext());
                LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(mWindowWidth / 4, mWindowWidth / 4);
                selectableImage.setLayoutParams(imageLayoutParams);
                selectableImage.getShowImage().setImageResource((int) defaultPictureResources.get(j));
                linearLayout.addView(selectableImage);
                mDaySelectableImages.add(selectableImage);
            }
            mDayLayout.addView(linearLayout);
        }
    }

    public void selectTime(int position){
        if(position == 0){
            mMonthLayout.setVisibility(View.GONE);
            mDayLayout.setVisibility(View.GONE);
            mYearLayout.setVisibility(View.VISIBLE);
        }else if(position == 1){
            mYearLayout.setVisibility(View.GONE);
            mDayLayout.setVisibility(View.GONE);
            mMonthLayout.setVisibility(View.VISIBLE);
        }else if(position == 2){
            mYearLayout.setVisibility(View.GONE);
            mMonthLayout.setVisibility(View.GONE);
            mDayLayout.setVisibility(View.VISIBLE);
        }

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
        for(int i = 0; i < mMonthSelectableImages.size(); i++){
            SelectableImage selectableImage = (SelectableImage) mMonthSelectableImages.get(i);
            selectableImage.cancelLongClickMode();
        }
        for(int i = 0; i < mDaySelectableImages.size(); i++){
            SelectableImage selectableImage = (SelectableImage) mDaySelectableImages.get(i);
            selectableImage.cancelLongClickMode();
        }
    }

    private void setOnClickListeners(){
        View.OnClickListener jumpToActivityPictureYear = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ActivityPictureTimeYear.class);
                startActivity(intent);
            }
        };

        mYearItem1.setOnClickListener(jumpToActivityPictureYear);
        mYearItem2.setOnClickListener(jumpToActivityPictureYear);
        mYearItem3.setOnClickListener(jumpToActivityPictureYear);



        // month
        View.OnClickListener monthSelectableImageOnClickListener = new View.OnClickListener() {
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
        for(int i = 0; i < mMonthSelectableImages.size(); i++){
            SelectableImage selectableImage = (SelectableImage) mMonthSelectableImages.get(i);
            selectableImage.setOnClickListener(monthSelectableImageOnClickListener);
        }


        // day
        View.OnClickListener daySelectableImageOnClickListener = new View.OnClickListener() {
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
        for(int i = 0; i < mDaySelectableImages.size(); i++){
            SelectableImage selectableImage = (SelectableImage) mDaySelectableImages.get(i);
            selectableImage.setOnClickListener(daySelectableImageOnClickListener);
        }
    }

    private void setOnLongClickListeners(){
        // month
        View.OnLongClickListener monthSelectableImageOnLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                for(int i = 0; i < mMonthSelectableImages.size(); i++){
                    SelectableImage selectableImage = (SelectableImage) mMonthSelectableImages.get(i);
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
        for(int i = 0; i < mMonthSelectableImages.size(); i++){
            SelectableImage selectableImage = (SelectableImage) mMonthSelectableImages.get(i);
            selectableImage.setOnLongClickListener(monthSelectableImageOnLongClickListener);
        }


        // day
        View.OnLongClickListener daySelectableImageOnLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                for(int i = 0; i < mDaySelectableImages.size(); i++){
                    SelectableImage selectableImage = (SelectableImage) mDaySelectableImages.get(i);
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
        for(int i = 0; i < mDaySelectableImages.size(); i++){
            SelectableImage selectableImage = (SelectableImage) mDaySelectableImages.get(i);
            selectableImage.setOnLongClickListener(daySelectableImageOnLongClickListener);
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

    private void jumpToPictureDetail(){
        Intent intent = new Intent();
        intent.setClass(getActivity(), ActivityPictureDetail.class);
        startActivity(intent);
    }
}
