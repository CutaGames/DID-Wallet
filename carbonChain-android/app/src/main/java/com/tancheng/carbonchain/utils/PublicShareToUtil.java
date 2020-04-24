package com.tancheng.carbonchain.utils;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.storage.ActivityShareToFriends;

/**
 * Created by gll on 2020/2/15.
 */

public class PublicShareToUtil extends RelativeLayout {
    private static final String TAG = "PublicShareToUtil";

    public Boolean isAddToParentView = false;
    private RelativeLayout mShareToRrivateLink;
    private Context mContext;
    private ViewGroup mViewGroup;
    private PublicShareToPrivateLinkUtil mPublicPrivateLinkUtil;

    public PublicShareToUtil(Context context, ViewGroup viewGroup) {
        super(context);
        mContext = context;
        mViewGroup = viewGroup;
        initViews();
    }

    public PublicShareToUtil(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PublicShareToUtil(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initViews() {
        View view = inflate(getContext(), R.layout.public_share_to_layout, this);
        view.setOnClickListener(null);

        mPublicPrivateLinkUtil = new PublicShareToPrivateLinkUtil(mContext);
        RelativeLayout.LayoutParams shareToPrivateLinkLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPublicPrivateLinkUtil.setLayoutParams(shareToPrivateLinkLayoutParams);

        Button cancelBtn = view.findViewById(R.id.share_to_cancel);
        cancelBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "share to cancel");
                ViewGroup parent = (ViewGroup) getParent();
                if(parent != null && parent instanceof ViewGroup){
                    isAddToParentView = false;
                    parent.removeView(PublicShareToUtil.this);
                }
            }
        });

        RelativeLayout shareToFriend = view.findViewById(R.id.share_to_friend);
        shareToFriend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "share to friend");
                Intent intent = new Intent();
                intent.setClass(getContext(), ActivityShareToFriends.class);
                getContext().startActivity(intent);
            }
        });

        mShareToRrivateLink = view.findViewById(R.id.share_to_private_link);
        mShareToRrivateLink.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "share to private link");
                mPublicPrivateLinkUtil.isAddToParentView = true;
                mViewGroup.addView(mPublicPrivateLinkUtil);
            }
        });
    }

    public Boolean doBackPress(){
        if(mPublicPrivateLinkUtil.doBackPress()){
            return true;
        }else {
            if (isAddToParentView) {
                ViewGroup parent = (ViewGroup) getParent();
                if (parent != null && parent instanceof ViewGroup) {
                    parent.removeView(PublicShareToUtil.this);
                }
                isAddToParentView = false;
                return true;
            } else {
                return false;
            }
        }
    }

}
