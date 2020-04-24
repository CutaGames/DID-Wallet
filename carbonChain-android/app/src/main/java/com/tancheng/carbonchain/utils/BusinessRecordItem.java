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

public class BusinessRecordItem extends FrameLayout {

    private final Boolean DEBUG = true;
    private final String TAG = getClass().getSimpleName();
    public static final int BUSINESS_STATUS_COMPLETE = 0;
    public static final int BUSINESS_STATUS_DOING = 1;
    private ImageView mShowImage;
    private TextView mCoinName;
    private TextView mTvBusinessNumber;
    private TextView mTvBusinessStatus;
    private Boolean mIsProfit = true;
    private int profitColorTrue;
    private int profitColorFalse;

    public BusinessRecordItem(@NonNull Context context) {
        super(context);
        initViews();
    }

    public BusinessRecordItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BusinessRecordItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initViews(){
        View view = inflate(getContext(), R.layout.business_record_item_layout, this);
        mShowImage = view.findViewById(R.id.show_image);
        mCoinName = view.findViewById(R.id.tv_coin_name);
        mTvBusinessStatus = findViewById(R.id.tv_business_status);
        mTvBusinessNumber = findViewById(R.id.tv_business_number);
        profitColorTrue = getResources().getColor(R.color.asset_business_record_profit_true);
        profitColorFalse = getResources().getColor(R.color.asset_business_record_profit_false);
    }



    public void setCoinNameText(String text){
        mCoinName.setText(text);
    }

    public void setShowImage(int resId){
        mShowImage.setImageResource(resId);
    }

    public void setBusinessStatus(int status){
        if(BUSINESS_STATUS_COMPLETE == status){
            mTvBusinessStatus.setText(getResources().getString(R.string.asset_business_record_status_complete));
        }else if(BUSINESS_STATUS_DOING == status){
            mTvBusinessStatus.setText(getResources().getString(R.string.asset_business_record_status_doing));
        }else{
            mTvBusinessStatus.setText("");
        }
    }

    public void setIsProfit(Boolean isProfit){
        this.mIsProfit = isProfit;
    }

    public void setBusinessNumber(String number){
        if(mIsProfit){
            mTvBusinessNumber.setText("+" + number);
            mTvBusinessNumber.setTextColor(profitColorTrue);
        }else {
            mTvBusinessNumber.setText("-" + number);
            mTvBusinessNumber.setTextColor(profitColorFalse);
        }
    }

}
