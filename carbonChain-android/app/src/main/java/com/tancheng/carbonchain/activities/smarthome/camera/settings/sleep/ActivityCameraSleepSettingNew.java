package com.tancheng.carbonchain.activities.smarthome.camera.settings.sleep;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.smarthome.camera.settings.sleep.datepicker.CustomDatePicker;
import com.tancheng.carbonchain.activities.smarthome.camera.settings.sleep.datepicker.DateFormatUtils;

public class ActivityCameraSleepSettingNew extends AppCompatActivity {

    private ViewGroup mViewGroup;
    private RelativeLayout mRepeatLayout;
    private BottomRepeatDayUtil mBottomRepeatDayUtil;
    private RelativeLayout mBeginTimeLayout;
    private RelativeLayout mEndTimeLayout;
    private CustomDatePicker mBeginTimerPicker;
    private CustomDatePicker mEndTimerPicker;
    private TextView mBeginTimeTv;
    private TextView mEndTimeTv;
    private ImageView mBeginTimeArrow;
    private ImageView mEndTimeArrow;
    private TextView mBeginTimeDelete;
    private TextView mEndTimeDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_sleep_setting_new);
        findViews();
        initViews();
        setOnClickListeners();
    }

    private void findViews(){
        mViewGroup = findViewById(R.id.root_layout);
        mRepeatLayout = findViewById(R.id.repeat_day_layout);
        mBeginTimeLayout = findViewById(R.id.begin_time_layout);
        mEndTimeLayout = findViewById(R.id.end_time_layout);
        mBeginTimeTv = findViewById(R.id.tv_begin_time);
        mEndTimeTv = findViewById(R.id.tv_end_time);
        mBeginTimeArrow = findViewById(R.id.iv_begin_time_arrow);
        mEndTimeArrow = findViewById(R.id.iv_end_time_arrow);
        mBeginTimeDelete = findViewById(R.id.iv_begin_time_delete);
        mEndTimeDelete = findViewById(R.id.iv_end_time_delete);
    }

    private void initViews(){
        mBottomRepeatDayUtil = new BottomRepeatDayUtil(this, mViewGroup);
        RelativeLayout.LayoutParams bottomOperationsLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBottomRepeatDayUtil.setLayoutParams(bottomOperationsLayoutParams);

        initBeginTimerPicker();
        initEndTimerPicker();
    }

    private void initBeginTimerPicker() {
        String beginTime = "2020-02-24 10:00";
        String endTime = DateFormatUtils.long2Str(System.currentTimeMillis(), true);

        //mTvSelectedTime.setText(endTime);

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mBeginTimerPicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mBeginTimeTv.setText(DateFormatUtils.long2Str(timestamp, true).substring(11));
                mBeginTimeArrow.setVisibility(View.GONE);
                mBeginTimeDelete.setVisibility(View.VISIBLE);
            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        mBeginTimerPicker.setCancelable(true);

        // 隐藏年月日
        mBeginTimerPicker.setCanShowYear(false);
        mBeginTimerPicker.setCanShowMonth(false);
        mBeginTimerPicker.setCanShowDay(false);

        // 显示时和分
        mBeginTimerPicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        mBeginTimerPicker.setScrollLoop(true);
        // 允许滚动动画
        mBeginTimerPicker.setCanShowAnim(true);

        mBeginTimerPicker.setTitleText(getResources().getString(R.string.smart_home_camera_sleep_settings_new_begin_time_title));
    }

    private void initEndTimerPicker() {
        String beginTime = "2020-02-24 10:00";
        String endTime = DateFormatUtils.long2Str(System.currentTimeMillis(), true);

        //mTvSelectedTime.setText(endTime);

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mEndTimerPicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                mEndTimeTv.setText(DateFormatUtils.long2Str(timestamp, true).substring(11));
                mEndTimeArrow.setVisibility(View.GONE);
                mEndTimeDelete.setVisibility(View.VISIBLE);
            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        mEndTimerPicker.setCancelable(true);

        // 隐藏年月日
        mEndTimerPicker.setCanShowYear(false);
        mEndTimerPicker.setCanShowMonth(false);
        mEndTimerPicker.setCanShowDay(false);

        // 显示时和分
        mEndTimerPicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        mEndTimerPicker.setScrollLoop(true);
        // 允许滚动动画
        mEndTimerPicker.setCanShowAnim(true);

        mEndTimerPicker.setTitleText(getResources().getString(R.string.smart_home_camera_sleep_settings_new_end_time_title));
    }

    private void setOnClickListeners(){
        mRepeatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomRepeatDayUtil.isAddToParentView = true;
                mViewGroup.addView(mBottomRepeatDayUtil);
            }
        });

        mBeginTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBeginTimerPicker.show(System.currentTimeMillis());
            }
        });

        mEndTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEndTimerPicker.show(System.currentTimeMillis());
            }
        });

        mBeginTimeDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBeginTimeTv.setText(R.string.smart_home_camera_sleep_setting_new_time_none_text);
                mBeginTimeArrow.setVisibility(View.VISIBLE);
                mBeginTimeDelete.setVisibility(View.GONE);
            }
        });

        mEndTimeDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEndTimeTv.setText(R.string.smart_home_camera_sleep_setting_new_time_none_text);
                mEndTimeArrow.setVisibility(View.VISIBLE);
                mEndTimeDelete.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(mBottomRepeatDayUtil.doBackPress()){

        }else {
            super.onBackPressed();
        }
    }
}
