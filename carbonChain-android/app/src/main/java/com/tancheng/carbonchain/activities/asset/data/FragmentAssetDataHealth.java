package com.tancheng.carbonchain.activities.asset.data;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.utils.BusinessRecordItem;

/**
 * Created by gll on 2020/2/13.
 */

public class FragmentAssetDataHealth extends Fragment {
    private static final Boolean DEBUG = true;
    private static final String TAG = "FragmentAssetDataHealth";
    private static final int MSG_LOAD_SUCCESS = 1;

    private RelativeLayout mNoneLayout;
    private LinearLayout mContainer;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (MSG_LOAD_SUCCESS == msg.what) {
                mNoneLayout.setVisibility(View.GONE);
                mContainer.setVisibility(View.VISIBLE);
            }
        }
    };

    public FragmentAssetDataHealth() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asset_data_health, container,false);
        findViews(view);
        initViews();
        setOnClickListeners();
        //startCallSuccessThread();
        return view;
    }

    private void findViews(View view){
        mContainer = view.findViewById(R.id.container);
        mNoneLayout = view.findViewById(R.id.none_layout);
    }

    private void initViews(){

    }

    private void setOnClickListeners(){
    }

    private void startCallSuccessThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
            }
        }).start();
    }

}
