package com.tancheng.carbonchain.activities.smarthome.camera.settings.sleep;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.smarthome.camera.settings.sleep.alarmhelper.CameraSleepAlarmAdapter;
import com.tancheng.carbonchain.activities.smarthome.camera.settings.sleep.alarmhelper.WeSwipe;

import java.util.ArrayList;
import java.util.List;

public class ActivityCameraSleepSetting extends AppCompatActivity implements CameraSleepAlarmAdapter.DeletedItemListener{

    private static final int MSG_CALL_SUCCESS = 1;
    private ImageView mAddImage;
    private RelativeLayout mNoneLayout;
    private RecyclerView mRecyclerView;
    private CameraSleepAlarmAdapter mAdapter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (MSG_CALL_SUCCESS == msg.what) {
                mNoneLayout.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_sleep_setting);
        findViews();
        initDatas();
        setOnClickListeners();
        startCallSuccessThread();
    }

    private void findViews(){
        mAddImage = findViewById(R.id.iv_add);
        mNoneLayout = findViewById(R.id.none_layout);
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    private void initDatas(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CameraSleepAlarmAdapter(this);
        mAdapter.setDeletedItemListener(this);
        mRecyclerView.setAdapter(mAdapter);
        //设置WeSwipe
        WeSwipe.attach(mRecyclerView);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list.add("Item  " +i);
        }
        mAdapter.setList(list,true);
    }

    private void setOnClickListeners(){
        mAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ActivityCameraSleepSetting.this, ActivityCameraSleepSettingNew.class);
                startActivity(intent);
            }
        });
    }

    private void startCallSuccessThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(MSG_CALL_SUCCESS);
            }
        }).start();
    }

    @Override
    public void deleted(int position) {
        mAdapter.removeDataByPosition(position);
    }
}
