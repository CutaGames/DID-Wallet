package com.tancheng.carbonchain.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;

/**
 * Created by gll on 2020/2/15.
 */

public class PublicShareToPrivateLinkUtil extends RelativeLayout {

    private static final String TAG = "PublicShareToPrivateLin";

    public Boolean isAddToParentView = false;

    public PublicShareToPrivateLinkUtil(Context context) {
        super(context);
        initViews();
    }

    public PublicShareToPrivateLinkUtil(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PublicShareToPrivateLinkUtil(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initViews(){
        View view = inflate(getContext(), R.layout.public_share_to_private_link_layout, this);
        view.setOnClickListener(null);
        Button know = (Button)view.findViewById(R.id.share_to_private_link_know_btn);
        know.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "know");
                ViewGroup parent = (ViewGroup) getParent();
                if(parent != null && parent instanceof ViewGroup){
                    isAddToParentView = false;
                    parent.removeView(PublicShareToPrivateLinkUtil.this);
                }
            }
        });
    }

    public Boolean doBackPress(){
        if(isAddToParentView){
            ViewGroup parent = (ViewGroup) getParent();
            if(parent != null && parent instanceof ViewGroup){
                parent.removeView(PublicShareToPrivateLinkUtil.this);
            }
            isAddToParentView = false;
            return true;
        }else{
            return false;
        }
    }
}
