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

public class SelectableCameraHistoryHour extends FrameLayout {

    private final Boolean DEBUG = true;
    private final String TAG = getClass().getSimpleName();
    private ImageView mSelectIcon;
    private TextView mShowTime;
    private Boolean isSelected = false;
    private Boolean hasContent = false;

    public SelectableCameraHistoryHour(@NonNull Context context) {
        super(context);
        initViews();
    }

    public SelectableCameraHistoryHour(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectableCameraHistoryHour(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initViews(){
        View view = inflate(getContext(), R.layout.public_selectable_camera_history_hour_layout, this);
        mSelectIcon = view.findViewById(R.id.select_icon);
        mShowTime = view.findViewById(R.id.show_time);
    }

    public void setHasContent(Boolean hasContent){
        this.hasContent = hasContent;
        if(hasContent){
            mShowTime.setBackgroundColor(getResources().getColor(R.color.camera_history_selectable_time_bg_true_color));
            mShowTime.setTextColor(getResources().getColor(R.color.camera_history_selectable_time_text_true_color));
        }else{
            mShowTime.setBackgroundColor(getResources().getColor(R.color.camera_history_selectable_time_bg_false_color));
            mShowTime.setTextColor(getResources().getColor(R.color.camera_history_selectable_time_text_false_color));
        }
    }

    public void setIsSelected(Boolean isSelected){
        if(this.hasContent) {
            this.isSelected = isSelected;
            if(isSelected){
                mSelectIcon.setImageResource(R.mipmap.camera_history_time_icon_select_true);
            }else {
                mSelectIcon.setImageResource(R.mipmap.camera_history_time_icon_select_false);
            }
        }
    }

    public ImageView getSelectIcon(){
        return mSelectIcon;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public Boolean getHasContent(){
        return hasContent;
    }

    public void setShowTimeText(String text){
        mShowTime.setText(text);
    }

    public void cancelLongClickMode(){
        isSelected = false;
        mSelectIcon.setImageResource(R.mipmap.camera_history_time_icon_select_false);
        mSelectIcon.setVisibility(View.GONE);
    }
}
