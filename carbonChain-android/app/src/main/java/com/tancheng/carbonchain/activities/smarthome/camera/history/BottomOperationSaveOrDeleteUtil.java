package com.tancheng.carbonchain.activities.smarthome.camera.history;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.utils.PublicConfirmDeleteUtil;

/**
 * Created by gll on 2020/2/22.
 */

public class BottomOperationSaveOrDeleteUtil extends RelativeLayout {
    private static final String TAG = "BottomOperationsMoreUti";

    public Boolean isAddToParentView = false;
    private ViewGroup mViewGroup;
    private PublicConfirmDeleteUtil mPublicConfirmDeleteUtil;

    public BottomOperationSaveOrDeleteUtil(Context context, ViewGroup viewGroup) {
        super(context);
        this.mViewGroup = viewGroup;
        initViews();
    }

    public BottomOperationSaveOrDeleteUtil(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomOperationSaveOrDeleteUtil(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initViews() {
        View view = inflate(getContext(), R.layout.camera_history_bottom_operations_save_or_delete_layout, this);
        //view.setOnClickListener(null);

        mPublicConfirmDeleteUtil = new PublicConfirmDeleteUtil(getContext());
        RelativeLayout.LayoutParams confirmDeleteLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPublicConfirmDeleteUtil.setLayoutParams(confirmDeleteLayoutParams);

        LinearLayout deleteLayout = view.findViewById(R.id.delete_layout);
        deleteLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mPublicConfirmDeleteUtil.isAddToParentView = true;
                mViewGroup.addView(mPublicConfirmDeleteUtil);
            }
        });
    }

    public Boolean doBackPress(){
        if(mPublicConfirmDeleteUtil.doBackPress()){
            return true;
        }else if(isAddToParentView){
            removeFromParent();
            return true;
        }else{
            return false;
        }
    }

    public Boolean doBackPressedNotRemoveFromCurrentParent(){
        if(mPublicConfirmDeleteUtil.doBackPress()){
            return true;
        }else if(isAddToParentView){
            //removeFromParent();
            return false;
        }else{
            return false;
        }
    }

    private void removeFromParent(){
        ViewGroup parent = (ViewGroup) getParent();
        if(parent != null && parent instanceof ViewGroup){
            parent.removeView(BottomOperationSaveOrDeleteUtil.this);
        }
        isAddToParentView = false;
    }
}
