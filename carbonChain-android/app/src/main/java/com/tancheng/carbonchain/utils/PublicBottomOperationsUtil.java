package com.tancheng.carbonchain.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.move.ActivityMoveTargetFolderMain;

/**
 * Created by gll on 2020/2/15.
 */

public class PublicBottomOperationsUtil extends RelativeLayout {
    private static final Boolean DEBUG = true;
    private static final String TAG = "PublicBottomOperations";

    public Boolean isAddToParentView = false;
    private RelativeLayout operationDeleteView;
    private RelativeLayout operationShareToView;
    private RelativeLayout operationMoveView;
    private RelativeLayout operationMoreView;
    private PublicShareToUtil mPublicShareToUtil;
    private Context mContext;
    private ViewGroup mViewGroup;
    private PublicConfirmDeleteUtil mPublicConfirmDeleteUtil;
    private PublicBottomOperationsMoreUtil mPublicBottomOperationsMoreUtil;



    public PublicBottomOperationsUtil(Context context, ViewGroup viewGroup) {
        super(context);
        this.mContext = context;
        this.mViewGroup = viewGroup;
        initViews();
        setOnClickListeners();
    }

    public PublicBottomOperationsUtil(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PublicBottomOperationsUtil(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initViews() {
        View view = inflate(getContext(), R.layout.public_bottom_operations_layout, this);
        operationDeleteView = view.findViewById(R.id.operation_delete);

        mPublicConfirmDeleteUtil = new PublicConfirmDeleteUtil(mContext);
        RelativeLayout.LayoutParams confirmDeleteLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPublicConfirmDeleteUtil.setLayoutParams(confirmDeleteLayoutParams);

        operationShareToView = view.findViewById(R.id.operation_share);
        operationMoveView = view.findViewById(R.id.operation_move);

        mPublicShareToUtil = new PublicShareToUtil(mContext, mViewGroup);
        RelativeLayout.LayoutParams shareToLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPublicShareToUtil.setLayoutParams(shareToLayoutParams);

        operationMoreView = view.findViewById(R.id.operation_more);
        mPublicBottomOperationsMoreUtil = new PublicBottomOperationsMoreUtil(mContext, mViewGroup);
        RelativeLayout.LayoutParams moreLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPublicBottomOperationsMoreUtil.setLayoutParams(moreLayoutParams);

    }

    private void setOnClickListeners(){
        operationDeleteView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mPublicConfirmDeleteUtil.isAddToParentView = true;
                mViewGroup.addView(mPublicConfirmDeleteUtil);
            }
        });

        operationShareToView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mPublicShareToUtil.isAddToParentView = true;
                mViewGroup.addView(mPublicShareToUtil);
            }
        });

        operationMoveView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click upload target folder");
                Intent intent = new Intent();
                intent.setClass(mContext, ActivityMoveTargetFolderMain.class);
                mContext.startActivity(intent);
            }
        });

        operationMoreView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mPublicBottomOperationsMoreUtil.isAddToParentView = true;
                mViewGroup.addView(mPublicBottomOperationsMoreUtil);
            }
        });
    }

    public Boolean doBackPressed(){
        if(mPublicShareToUtil.doBackPress()){
            return true;
        }else if(mPublicBottomOperationsMoreUtil.doBackPress()){
            return true;
        }else if(mPublicConfirmDeleteUtil.doBackPress()){
            return true;
        }else if(isAddToParentView){
            isAddToParentView = false;
            mViewGroup.removeView(PublicBottomOperationsUtil.this);
            return true;
        }else{
            return false;
        }
    }

    public Boolean doBackPressedNotRemoveFromCurrentParent(){
        if(mPublicShareToUtil.doBackPress()){
            return true;
        }else if(mPublicBottomOperationsMoreUtil.doBackPress()){
            return true;
        }else if(mPublicConfirmDeleteUtil.doBackPress()){
            return true;
        }else if(isAddToParentView){
            //isAddToParentView = false;
            //mViewGroup.removeView(PublicBottomOperationsUtil.this);
            return false;
        }else{
            return false;
        }
    }

    public void setTypeImage(){
        mPublicBottomOperationsMoreUtil.setTypeImage();
    }

    public void setTypeMp3(){
        mPublicBottomOperationsMoreUtil.setTypeMp3();
    }

    public void setMoreFileName(String str){
        mPublicBottomOperationsMoreUtil.setMoreFileName(str);
    }

}
