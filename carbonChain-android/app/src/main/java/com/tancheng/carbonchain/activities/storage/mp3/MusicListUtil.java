package com.tancheng.carbonchain.activities.storage.mp3;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;

/**
 * Created by gll on 2020/2/18.
 */

public class MusicListUtil extends RelativeLayout {
    private static final String TAG = "MusicListUtil";
    public Boolean isAddToParentView = false;
    private ViewGroup mViewGroup;

    public MusicListUtil(Context context, ViewGroup viewGroup) {
        super(context);
        this.mViewGroup = viewGroup;
        initViews();
    }

    public MusicListUtil(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MusicListUtil(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initViews() {
        View view = inflate(getContext(), R.layout.music_list_layout, this);
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

    private void removeFromParent(){
        ViewGroup parent = (ViewGroup) getParent();
        if(parent != null && parent instanceof ViewGroup){
            parent.removeView(MusicListUtil.this);
        }
        isAddToParentView = false;
    }
}
