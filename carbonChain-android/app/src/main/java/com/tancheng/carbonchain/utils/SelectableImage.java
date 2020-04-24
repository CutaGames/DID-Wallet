package com.tancheng.carbonchain.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.tancheng.carbonchain.R;

/**
 * Created by gll on 2020/2/14.
 */

public class SelectableImage extends FrameLayout {

    private final Boolean DEBUG = true;
    private final String TAG = getClass().getSimpleName();
    private ImageView mSelectIcon;
    private ImageView mShowImage;
    private Boolean isSelected = false;

    public SelectableImage(@NonNull Context context) {
        super(context);
        initViews();
    }

    public SelectableImage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectableImage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initViews(){
        View view = inflate(getContext(), R.layout.public_selectable_image_layout, this);
        mSelectIcon = view.findViewById(R.id.select_icon);
        mShowImage = view.findViewById(R.id.show_image);
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

    public void cancelLongClickMode(){
        isSelected = false;
        mSelectIcon.setImageResource(R.mipmap.image_icon_select_false);
        mSelectIcon.setVisibility(View.GONE);
    }
}
