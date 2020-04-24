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

public class SelectableItem extends FrameLayout {

    private final Boolean DEBUG = true;
    private final String TAG = getClass().getSimpleName();
    private ImageView mSelectIcon;
    private ImageView mShowImage;
    private TextView mTitleTextView;
    private TextView mTimeTextView;
    private TextView mSizeTextView;
    private Boolean isSelected = false;

    public SelectableItem(@NonNull Context context) {
        super(context);
        initViews();
    }

    public SelectableItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectableItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initViews(){
        View view = inflate(getContext(), R.layout.public_selectable_item_layout, this);
        mSelectIcon = view.findViewById(R.id.select_icon);
        mShowImage = view.findViewById(R.id.item_pre_image);
        mTitleTextView = view.findViewById(R.id.item_title);
        mTimeTextView = view.findViewById(R.id.item_time);
        mSizeTextView = view.findViewById(R.id.item_size);
    }

    public ImageView getSelectIcon() {
        return mSelectIcon;
    }

    public ImageView getShowImage() {
        return mShowImage;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean selected) {
        isSelected = selected;
    }

    public void setItemTitleText(String text){
        mTitleTextView.setText(text);
    }

    public void setItemTimeText(String text){
        mTimeTextView.setText(text);
    }

    public void setItemSizeText(String text){
        mSizeTextView.setText(text);
    }

    public void cancelLongClickMode(){
        isSelected = false;
        mSelectIcon.setImageResource(R.mipmap.image_icon_select_false);
        mSelectIcon.setVisibility(View.INVISIBLE);
    }
}
