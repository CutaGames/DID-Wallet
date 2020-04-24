package com.tancheng.carbonchain.activities.smarthome.camera.settings.sleep;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.storage.ActivityShareToFriends;
import com.tancheng.carbonchain.utils.PublicConfirmDeleteUtil;

/**
 * Created by gll on 2020/2/22.
 */

public class BottomRepeatDayUtil extends RelativeLayout {
    private static final String TAG = "BottomOperationsMoreUti";

    public Boolean isAddToParentView = false;
    private ViewGroup mViewGroup;

    public BottomRepeatDayUtil(Context context, ViewGroup viewGroup) {
        super(context);
        this.mViewGroup = viewGroup;
        initViews();
    }

    public BottomRepeatDayUtil(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomRepeatDayUtil(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initViews() {
        View view = inflate(getContext(), R.layout.camera_seting_sleep_repeat_day_layout, this);
        view.setOnClickListener(null);

    }

    public Boolean doBackPress(){
        if(isAddToParentView){
            removeFromParent();
            return true;
        }else{
            return false;
        }
    }

    public Boolean doBackPressedNotRemoveFromCurrentParent(){
        if(isAddToParentView){
            //removeFromParent();
            return false;
        }else{
            return false;
        }
    }

    private void removeFromParent(){
        ViewGroup parent = (ViewGroup) getParent();
        if(parent != null && parent instanceof ViewGroup){
            parent.removeView(BottomRepeatDayUtil.this);
        }
        isAddToParentView = false;
    }
}
