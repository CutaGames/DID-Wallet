package com.tancheng.carbonchain.activities.smarthome.camera.history;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.utils.SelectableCameraHistoryMinute;

import java.util.ArrayList;
import java.util.List;

public class ActivityCameraHistoryHour extends AppCompatActivity {

    private ViewGroup mViewGroup;
    private LinearLayout mContainerLayout;
    private int mWindowWidth;
    private List mSelectableMinutes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_history_hour);
        mWindowWidth = this.getResources().getDisplayMetrics().widthPixels;

        findViews();
        initViews();
        setOnClickListeners();
    }

    private void findViews(){
        mViewGroup = findViewById(R.id.root_layout);
        mContainerLayout = findViewById(R.id.container);
    }

    private void initViews(){
        mSelectableMinutes = new ArrayList<SelectableCameraHistoryMinute>();
        // insert some views
        TextView hourTextView = new TextView(this);
        LinearLayout.LayoutParams time1TextViewLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        time1TextViewLayoutParams.setMargins(
                (int)getResources().getDimension(R.dimen.camera_history_time_margin_left),
                (int)getResources().getDimension(R.dimen.camera_history_time_margin_top),
                (int)getResources().getDimension(R.dimen.camera_history_time_margin_right),
                (int)getResources().getDimension(R.dimen.camera_history_time_margin_bottom)
        );
        hourTextView.setLayoutParams(time1TextViewLayoutParams);
        hourTextView.setText(getText(R.string.camera_history_time_hour_default_text_1));
        hourTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.public_text_size_12_sp));
        hourTextView.setTextColor(getResources().getColor(R.color.camera_history_time_title_text_color));
        mContainerLayout.addView(hourTextView);

        String hourBegin = "05";
        String hourEnd = "06";
        for(int i = 0; i < 4; i++){
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = getResources().getDimensionPixelOffset(R.dimen.camera_history_time_line_margin_left);
            layoutParams.rightMargin = getResources().getDimensionPixelOffset(R.dimen.camera_history_time_line_margin_right);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for(int j = 0; j < 3; j++) {
                SelectableCameraHistoryMinute selectableMinute = new SelectableCameraHistoryMinute(this);
                LinearLayout.LayoutParams minuteLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                minuteLayoutParams.width = (mWindowWidth - layoutParams.leftMargin - layoutParams.rightMargin) / 3;
                minuteLayoutParams.height = minuteLayoutParams.width / 2;
                selectableMinute.setLayoutParams(minuteLayoutParams);
                int minuteBegin = i * 3 * 5 + j * 5;
                int minuteEnd = minuteBegin + 5;
                String timeStr = "";
                if(minuteEnd == 60){
                    timeStr = hourBegin + ":" + minuteBegin + "-" + hourEnd + ":" + "00";
                }else{
                    timeStr = hourBegin + ":" + minuteBegin + "-" + hourBegin + ":" + minuteEnd;
                }
                selectableMinute.setShowTimeText(timeStr);
                linearLayout.addView(selectableMinute);
                mSelectableMinutes.add(selectableMinute);
            }
            mContainerLayout.addView(linearLayout);
        }
    }

    private void setOnClickListeners(){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ActivityCameraHistoryHour.this,ActivityCameraHistoryPlay.class);
                startActivity(intent);
            }
        };

        for(int i = 0; i < mSelectableMinutes.size(); i++){
            SelectableCameraHistoryMinute minute = (SelectableCameraHistoryMinute)mSelectableMinutes.get(i);
            minute.setOnClickListener(onClickListener);
        }
    }
}
