package com.tancheng.carbonchain.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tancheng.carbonchain.R;

/**
 * Created by gll on 2020/2/14.
 */

public class SelectableCameraHistoryMinute extends FrameLayout {

    private final Boolean DEBUG = true;
    private final String TAG = getClass().getSimpleName();
    private TextView mShowTime;
    private ImageView mShowImage;

    public SelectableCameraHistoryMinute(@NonNull Context context) {
        super(context);
        initViews();
    }

    public SelectableCameraHistoryMinute(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectableCameraHistoryMinute(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initViews(){
        View view = inflate(getContext(), R.layout.public_selectable_camera_history_minute_layout, this);
        mShowTime = view.findViewById(R.id.show_time);
        mShowImage = view.findViewById(R.id.show_image);
    }



    public void setShowTimeText(String text){
        mShowTime.setText(text);
    }

    public void setShowImage(int resId){
        mShowImage.setImageResource(resId);
    }


}
