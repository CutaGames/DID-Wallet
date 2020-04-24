package com.tancheng.carbonchain.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tancheng.carbonchain.R;

/**
 * Created by gll on 2020/2/15.
 */

public class PublicRenameUtil extends LinearLayout {
    private static final String TAG = "PublicRenameUtil";
    public Boolean isAddToParentView = false;
    private TextView mTvRenameTitle;

    public PublicRenameUtil(Context context) {
        super(context);
        initViews();
    }

    public PublicRenameUtil(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PublicRenameUtil(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initViews() {
        View view = inflate(getContext(), R.layout.public_rename_layout, this);
        view.setOnClickListener(null);
        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        mTvRenameTitle = findViewById(R.id.tv_rename_title);

        TextView confirm = view.findViewById(R.id.confirm_text);
        confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "rename confirm");
                // TODO
                ViewGroup parent = (ViewGroup) getParent();
                if(parent != null && parent instanceof ViewGroup){
                    isAddToParentView = false;
                    parent.removeView(PublicRenameUtil.this);
                }
            }
        });

        TextView confirmCancel = view.findViewById(R.id.confirm_cancel);
        confirmCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "rename cancel");
                ViewGroup parent = (ViewGroup) getParent();
                if(parent != null && parent instanceof ViewGroup){
                    isAddToParentView = false;
                    parent.removeView(PublicRenameUtil.this);
                }
            }
        });
    }

    public void setRenameTitle(String title){
        mTvRenameTitle.setText(title);
    }

    public Boolean doBackPress(){
        if(isAddToParentView){
            ViewGroup parent = (ViewGroup) getParent();
            if(parent != null && parent instanceof ViewGroup){
                parent.removeView(PublicRenameUtil.this);
            }
            isAddToParentView = false;
            return true;
        }else{
            return false;
        }
    }
}
