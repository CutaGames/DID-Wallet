package com.tancheng.carbonchain.activities.smarthome;

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

public class SmartHomeItem extends FrameLayout {

    private final Boolean DEBUG = true;
    private final String TAG = getClass().getSimpleName();
    private ImageView mShowImage;
    private TextView mTitleTextView;
    private TextView mStatusTextView;
    private ImageView mStatusImageView;

    public SmartHomeItem(@NonNull Context context) {
        super(context);
        initViews();
    }

    public SmartHomeItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartHomeItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initViews(){
        View view = inflate(getContext(), R.layout.smart_home_item_layout, this);
        mShowImage = view.findViewById(R.id.item_pre_image);
        mTitleTextView = view.findViewById(R.id.item_title);
        mStatusTextView = view.findViewById(R.id.item_status_text);
        mStatusImageView = view.findViewById(R.id.item_status_icon);
    }

    public void setShowImage(int resId) {
        mShowImage.setImageResource(resId);
    }

    public void setTitleText(String str){
        mTitleTextView.setText(str);
    }

    public void setStatusText(String str){
        mStatusTextView.setText(str);
    }

    public void setStatusTextColor(int resId){
        mStatusTextView.setTextColor(getResources().getColor(resId));
    }

    public void setStatusImageView(int resId){
        mStatusImageView.setImageResource(resId);
    }
}
