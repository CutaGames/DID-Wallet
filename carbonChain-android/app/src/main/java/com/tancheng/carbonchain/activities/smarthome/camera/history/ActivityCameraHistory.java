package com.tancheng.carbonchain.activities.smarthome.camera.history;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.utils.SelectableCameraHistoryHour;

import java.util.ArrayList;
import java.util.List;

public class ActivityCameraHistory extends AppCompatActivity {

    private ViewGroup mViewGroup;
    private LinearLayout mContainerLayout;
    private int mWindowWidth;
    private List mSelectableTimes;

    private Boolean mOnLongTouchMode = false;
    private BottomOperationSaveOrDeleteUtil mBottomOperationSaveOrDeleteUtil;
    private LinearLayout mBottomSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_history);
        mWindowWidth = this.getResources().getDisplayMetrics().widthPixels;
        findViews();
        initViews();
        setOnLongClickListeners();
        setOnClickListeners();
    }

    private void findViews(){
        mViewGroup = (ViewGroup)findViewById(R.id.root_layout);
        mContainerLayout = (LinearLayout)findViewById(R.id.container);
        mBottomSettings = (LinearLayout)findViewById(R.id.bottom_settings);
    }

    private void initViews(){
        mSelectableTimes = new ArrayList<SelectableCameraHistoryHour>();
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
        time1TextView.setText(getText(R.string.camera_history_time_default_text_1));
        time1TextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.public_text_size_12_sp));
        time1TextView.setTextColor(getResources().getColor(R.color.camera_history_time_title_text_color));
        mContainerLayout.addView(time1TextView);

        int timeHeight = getResources().getDimensionPixelOffset(R.dimen.camera_history_time_line_item_height);
        for(int i = 0; i < 4; i++){
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = getResources().getDimensionPixelOffset(R.dimen.camera_history_time_line_margin_left);
            layoutParams.rightMargin = getResources().getDimensionPixelOffset(R.dimen.camera_history_time_line_margin_right);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for(int j = 0; j < 6; j++) {
                SelectableCameraHistoryHour selectableTime = new SelectableCameraHistoryHour(ActivityCameraHistory.this);
                LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                imageLayoutParams.width = (mWindowWidth - layoutParams.leftMargin - layoutParams.rightMargin) / 6;
                imageLayoutParams.height = timeHeight;
                selectableTime.setLayoutParams(imageLayoutParams);
                int time = i * 6 + j;
                String timeStr = "";
                if(time < 10){
                    timeStr = "0" + time + ":00";
                }else{
                    timeStr = time + ":00";
                }
                selectableTime.setShowTimeText(timeStr);
                selectableTime.setHasContent(i % 2 == 0 ? true : false);
                linearLayout.addView(selectableTime);
                mSelectableTimes.add(selectableTime);
            }
            mContainerLayout.addView(linearLayout);
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
        time2TextView.setText(getText(R.string.camera_history_time_default_text_2));
        time2TextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.public_text_size_12_sp));
        time2TextView.setTextColor(getResources().getColor(R.color.camera_history_time_title_text_color));
        mContainerLayout.addView(time2TextView);
        for(int i = 0; i < 4; i++){
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = getResources().getDimensionPixelOffset(R.dimen.camera_history_time_line_margin_left);
            layoutParams.rightMargin = getResources().getDimensionPixelOffset(R.dimen.camera_history_time_line_margin_right);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for(int j = 0; j < 6; j++) {
                SelectableCameraHistoryHour selectableTime = new SelectableCameraHistoryHour(ActivityCameraHistory.this);
                LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                imageLayoutParams.width = (mWindowWidth - layoutParams.leftMargin - layoutParams.rightMargin) / 6;
                imageLayoutParams.height = timeHeight;
                selectableTime.setLayoutParams(imageLayoutParams);
                int time = i * 6 + j;
                String timeStr = "";
                if(time < 10){
                    timeStr = "0" + time + ":00";
                }else{
                    timeStr = time + ":00";
                }
                selectableTime.setShowTimeText(timeStr);
                selectableTime.setHasContent(i % 2 == 0 ? true : false);
                linearLayout.addView(selectableTime);
                mSelectableTimes.add(selectableTime);
            }
            mContainerLayout.addView(linearLayout);
        }


        mBottomOperationSaveOrDeleteUtil = new BottomOperationSaveOrDeleteUtil(ActivityCameraHistory.this, mViewGroup);
        RelativeLayout.LayoutParams bottomOperationsLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBottomOperationSaveOrDeleteUtil.setLayoutParams(bottomOperationsLayoutParams);
    }

    private void setOnLongClickListeners(){
        View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mOnLongTouchMode = true;
                for(int i = 0; i < mSelectableTimes.size(); i++){
                    SelectableCameraHistoryHour selectableCameraHistoryHour = (SelectableCameraHistoryHour) mSelectableTimes.get(i);
                    if(selectableCameraHistoryHour.getHasContent()) {
                        selectableCameraHistoryHour.getSelectIcon().setVisibility(View.VISIBLE);
                    }
                }
                SelectableCameraHistoryHour selectableCameraHistoryHour = (SelectableCameraHistoryHour)view;
                if(selectableCameraHistoryHour.getHasContent()) {
                    if (selectableCameraHistoryHour.getIsSelected()) {
                        selectableCameraHistoryHour.setIsSelected(false);
                    } else {
                        selectableCameraHistoryHour.setIsSelected(true);
                    }
                }

                if(mBottomOperationSaveOrDeleteUtil.isAddToParentView == false){
                    mBottomOperationSaveOrDeleteUtil.isAddToParentView = true;
                    mViewGroup.addView(mBottomOperationSaveOrDeleteUtil);
                    mBottomSettings.setVisibility(View.INVISIBLE);
                }

                return true;
            }
        };

        for(int i = 0; i < mSelectableTimes.size(); i++){
            SelectableCameraHistoryHour selectableImage = (SelectableCameraHistoryHour) mSelectableTimes.get(i);
            selectableImage.setOnLongClickListener(onLongClickListener);
        }
    }



    private void setOnClickListeners(){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectableCameraHistoryHour selectableCameraHistoryHour = (SelectableCameraHistoryHour) view;
                if(mOnLongTouchMode){
                    if(selectableCameraHistoryHour.getHasContent()) {
                        if (selectableCameraHistoryHour.getIsSelected()) {
                            selectableCameraHistoryHour.setIsSelected(false);
                        } else {
                            selectableCameraHistoryHour.setIsSelected(true);
                        }
                    }
                }else{
                    if(selectableCameraHistoryHour.getHasContent()){
                        // 跳转到单独图片界面
                        jumpToCameraHistoryHour();
                    }else{
                        // no video, do nothing
                    }
                }
            }
        };

        for(int i = 0; i < mSelectableTimes.size(); i++){
            SelectableCameraHistoryHour selectableImage = (SelectableCameraHistoryHour) mSelectableTimes.get(i);
            selectableImage.setOnClickListener(onClickListener);
        }
    }

    @Override
    public void onBackPressed() {
        if(mBottomOperationSaveOrDeleteUtil.doBackPress() == false){
            super.onBackPressed();
        }
        if(mBottomOperationSaveOrDeleteUtil.isAddToParentView){

        }else {
            mOnLongTouchMode = false;
            cancelLongClickMode();
            mBottomSettings.setVisibility(View.VISIBLE);
        }
    }

    private void cancelLongClickMode(){
        for(int i = 0; i < mSelectableTimes.size(); i++){
            SelectableCameraHistoryHour selectableCameraHistoryHour = (SelectableCameraHistoryHour) mSelectableTimes.get(i);
            selectableCameraHistoryHour.cancelLongClickMode();
        }
    }

    private void jumpToCameraHistoryHour(){
        Intent intent = new Intent();
        intent.setClass(this,ActivityCameraHistoryHour.class);
        startActivity(intent);
    }
}
