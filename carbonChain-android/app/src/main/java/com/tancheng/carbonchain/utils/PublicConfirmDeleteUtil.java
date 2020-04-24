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

public class PublicConfirmDeleteUtil extends LinearLayout {
    private static final String TAG = "PublicConfirmDeleteUtil";
    public Boolean isAddToParentView = false;
    public PublicConfirmDeleteUtil(Context context) {
        super(context);
        initViews();
    }

    public PublicConfirmDeleteUtil(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PublicConfirmDeleteUtil(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initViews() {
        View view = inflate(getContext(), R.layout.public_confirm_delete_layout, this);
        view.setOnClickListener(null);
        TextView confirmDelete = (TextView) view.findViewById(R.id.confirm_delete);
        confirmDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "confirm delete");
                // TODO
                ViewGroup parent = (ViewGroup) getParent();
                if(parent != null && parent instanceof ViewGroup){
                    isAddToParentView = false;
                    parent.removeView(PublicConfirmDeleteUtil.this);
                }
            }
        });

        TextView confirmCancel = (TextView) view.findViewById(R.id.confirm_cancel);
        confirmCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "confirm cancel");
                ViewGroup parent = (ViewGroup) getParent();
                if(parent != null && parent instanceof ViewGroup){
                    isAddToParentView = false;
                    parent.removeView(PublicConfirmDeleteUtil.this);
                }
            }
        });
    }

    public Boolean doBackPress(){
        if(isAddToParentView){
            ViewGroup parent = (ViewGroup) getParent();
            if(parent != null && parent instanceof ViewGroup){
                parent.removeView(PublicConfirmDeleteUtil.this);
            }
            isAddToParentView = false;
            return true;
        }else{
            return false;
        }
    }
}
