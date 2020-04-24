package com.tancheng.carbonchain.activities.smarthome.camera;

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

public class SmartHomeSleepConfirmUtil extends LinearLayout {
    private static final String TAG = "SmartHomeSleepConfirmUt";
    public Boolean isAddToParentView = false;
    public SmartHomeSleepConfirmUtil(Context context) {
        super(context);
        initViews();
    }

    public SmartHomeSleepConfirmUtil(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartHomeSleepConfirmUtil(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initViews() {
        View view = inflate(getContext(), R.layout.smart_home_camera_sleep_confirm, this);
        view.setOnClickListener(null);
        TextView confirmDelete = (TextView) view.findViewById(R.id.confirm_delete);
        confirmDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "confirm sleep");
                // TODO
                onConfirmSelectedListener.sleepCamera();
                removeFromParent();
            }
        });

        TextView confirmCancel = (TextView) view.findViewById(R.id.confirm_cancel);
        confirmCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "confirm cancel");
                removeFromParent();
            }
        });
    }

    public interface OnConfirmSelectedListener{
        void sleepCamera();
    }

    private OnConfirmSelectedListener onConfirmSelectedListener;

    public void setOnConfirmSelectedListener(OnConfirmSelectedListener onConfirmSelectedListener){
        this.onConfirmSelectedListener = onConfirmSelectedListener;
    }

    public Boolean doBackPress(){
        if(isAddToParentView){
            removeFromParent();
            return true;
        }else{
            return false;
        }
    }

    private void removeFromParent(){
        ViewGroup parent = (ViewGroup) getParent();
        if(parent != null && parent instanceof ViewGroup){
            parent.removeView(SmartHomeSleepConfirmUtil.this);
        }
        isAddToParentView = false;
    }
}
