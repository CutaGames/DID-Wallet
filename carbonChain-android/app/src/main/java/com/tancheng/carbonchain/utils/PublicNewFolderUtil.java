package com.tancheng.carbonchain.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tancheng.carbonchain.R;

/**
 * Created by gll on 2020/2/15.
 */

public class PublicNewFolderUtil extends LinearLayout {
    private static final String TAG = "PublicNewFolderUtil";
    public Boolean isAddToParentView = false;

    private ViewGroup mViewGroup;

    public PublicNewFolderUtil(Context context, ViewGroup viewGroup) {
        super(context);
        this.mViewGroup = viewGroup;
        initViews();
    }

    public PublicNewFolderUtil(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PublicNewFolderUtil(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initViews() {
        View view = inflate(getContext(), R.layout.public_new_folder_layout, this);
        view.setOnClickListener(null);
        TextView confirm = view.findViewById(R.id.confirm_text);
        confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "new folder confirm");
                // TODO
                ViewGroup parent = (ViewGroup) getParent();
                if(parent != null && parent instanceof ViewGroup){
                    isAddToParentView = false;
                    parent.removeView(PublicNewFolderUtil.this);
                }
            }
        });

        TextView confirmCancel = view.findViewById(R.id.confirm_cancel);
        confirmCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "new folder cancel");
                ViewGroup parent = (ViewGroup) getParent();
                if(parent != null && parent instanceof ViewGroup){
                    isAddToParentView = false;
                    parent.removeView(PublicNewFolderUtil.this);
                }
            }
        });
    }

    public Boolean doBackPress(){
        if(isAddToParentView){
            ViewGroup parent = (ViewGroup) getParent();
            if(parent != null && parent instanceof ViewGroup){
                parent.removeView(PublicNewFolderUtil.this);
            }
            isAddToParentView = false;
            return true;
        }else{
            return false;
        }
    }
}
