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

public class SelectableCoinItem extends FrameLayout {

    private final Boolean DEBUG = true;
    private final String TAG = getClass().getSimpleName();
    private ImageView mShowImage;
    private TextView mCoinName;
    private TextView mCoinNameFull;
    private ImageView mSelectIcon;
    private Boolean isSelected = false;

    public SelectableCoinItem(@NonNull Context context) {
        super(context);
        initViews();
    }

    public SelectableCoinItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectableCoinItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initViews(){
        View view = inflate(getContext(), R.layout.public_selectable_coin_item_layout, this);
        mShowImage = view.findViewById(R.id.show_image);
        mCoinName = view.findViewById(R.id.tv_coin_name);
        mCoinNameFull = view.findViewById(R.id.tv_coin_name_full);
        mSelectIcon = findViewById(R.id.select_icon);
    }

    public void setIsSelected(Boolean isSelected){
        this.isSelected = isSelected;
        if(isSelected){
            mSelectIcon.setImageResource(R.mipmap.image_icon_select_true);
        }else {
            mSelectIcon.setImageResource(R.mipmap.image_icon_select_false);
        }
    }

    public ImageView getSelectIcon(){
        return mSelectIcon;
    }

    public Boolean getIsSelected(){
        return this.isSelected;
    }

    public void setCoinNameText(String text){
        mCoinName.setText(text);
    }

    public void setCoinNameFullText(String text){
        mCoinNameFull.setText(text);
    }

    public void setShowImage(int resId){
        mShowImage.setImageResource(resId);
    }

    public void cancelLongClickMode(){
        isSelected = false;
        mSelectIcon.setImageResource(R.mipmap.image_icon_select_false);
        mSelectIcon.setVisibility(View.GONE);
    }


}
