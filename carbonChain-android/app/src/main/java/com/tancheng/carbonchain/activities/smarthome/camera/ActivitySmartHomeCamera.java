package com.tancheng.carbonchain.activities.smarthome.camera;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.smarthome.camera.history.ActivityCameraHistory;
import com.tancheng.carbonchain.activities.smarthome.camera.photos.ActivityCameraPhotos;
import com.tancheng.carbonchain.activities.smarthome.camera.settings.ActivityCameraSettings;

import java.util.ArrayList;
import java.util.List;

public class ActivitySmartHomeCamera extends AppCompatActivity implements SmartHomeSleepConfirmUtil.OnConfirmSelectedListener {
    private static final String TAG = "ActivitySmartHomeCamera";
    private Boolean isCameraSleeping = false;
    private Boolean isRecording = false;
    private Boolean isMuting = false;

    private ViewGroup mViewGroup;
    private ImageView mSleepImageView;
    private ImageView mRecordImageView;
    private SmartHomeSleepConfirmUtil mSmartHomeSleepConfirmUtil;
    private LinearLayout mOperationLayout1;
    private LinearLayout mOperationLayout2;

    private ImageView mSleepingImageView;
    private TextView mSleepingTextView;
    private TextView mSpeedTextView;
    private TextView mTimeTextView;
    private TextView mRecordingTextView;
    private ImageView mMuteImageView;
    private ImageView mSwitchScreenImageView;
    private ImageView mHistoryImageView;
    private ImageView mCallImageView;
    private ImageView mPhotosImageView;
    private ImageView mSettingsImageView;

    private ImageView mSwitchHDImageView;
    private PopupWindow mSwitchHdPopupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_home_camera);
        findViews();
        initSleepConfirmDialog();
        setOnClickListeners();
    }

    private void findViews(){
        mViewGroup = (ViewGroup)findViewById(R.id.root_layout);
        mSleepImageView = findViewById(R.id.iv_sleep);
        mOperationLayout1 = findViewById(R.id.operation_layout_1);
        mOperationLayout2 = findViewById(R.id.operation_layout_2);
        mSleepingImageView = findViewById(R.id.smart_home_camera_sleeping_image);
        mSleepingTextView = findViewById(R.id.smart_home_camera_sleeping_text);
        mSpeedTextView = findViewById(R.id.smart_home_camera_speed);
        mTimeTextView = findViewById(R.id.smart_home_camera_time);
        mSwitchHDImageView = findViewById(R.id.camera_switch_hd);
        mRecordImageView = findViewById(R.id.camera_record);
        mRecordingTextView = findViewById(R.id.smart_home_camera_recording_text);
        mMuteImageView = findViewById(R.id.camera_mute);
        mSwitchScreenImageView = findViewById(R.id.switch_screen);
        mHistoryImageView = findViewById(R.id.iv_history);
        mCallImageView = findViewById(R.id.iv_call);
        mPhotosImageView = findViewById(R.id.iv_photos);
        mSettingsImageView = findViewById(R.id.iv_settings);
    }

    private void initSleepConfirmDialog(){
        mSmartHomeSleepConfirmUtil = new SmartHomeSleepConfirmUtil(ActivitySmartHomeCamera.this);
        RelativeLayout.LayoutParams confirmLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mSmartHomeSleepConfirmUtil.setLayoutParams(confirmLayoutParams);

        mSmartHomeSleepConfirmUtil.setOnConfirmSelectedListener(this);

    }

    private void setOnClickListeners(){
        // sleep
        if(null != mSleepImageView) {
            mSleepImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isCameraSleeping) {
                        //TODO wake camera
                        isCameraSleeping = false;
                        mOperationLayout1.setVisibility(View.VISIBLE);
                        mSleepImageView.setImageResource(R.mipmap.smart_home_camera_operation_2_item_sleep_false);
                        for (int i = 0; i < mOperationLayout2.getChildCount(); i++) {
                            View v = mOperationLayout2.getChildAt(i);
                            v.setVisibility(View.VISIBLE);
                        }
                        mOperationLayout2.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                        mSleepingImageView.setVisibility(View.GONE);
                        mSleepingTextView.setVisibility(View.GONE);
                        mSpeedTextView.setVisibility(View.VISIBLE);
                        mTimeTextView.setVisibility(View.VISIBLE);
                    } else {
                        mSmartHomeSleepConfirmUtil.isAddToParentView = true;
                        mViewGroup.addView(mSmartHomeSleepConfirmUtil);
                    }
                }
            });
        }

        // switch hd
        if(null != mSwitchHDImageView) {
            mSwitchHDImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showSwitchHdPopWindow(view);
                }
            });
        }

        // record
        if(null != mRecordImageView){
            mRecordImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isRecording){
                        isRecording = false;
                        mRecordImageView.setImageResource(R.mipmap.smart_home_camera_operation_1_item_record_port_false);
                        mRecordingTextView.setVisibility(View.GONE);
                    }else{
                        isRecording = true;
                        mRecordImageView.setImageResource(R.mipmap.smart_home_camera_operation_1_item_record_port_true);
                        mRecordingTextView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        // mute
        if(null != mMuteImageView){
            mMuteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isMuting){
                        isMuting = false;
                        mMuteImageView.setImageResource(R.mipmap.smart_home_camera_operation_1_item_mute_port_false);
                    }else {
                        isMuting = true;
                        mMuteImageView.setImageResource(R.mipmap.smart_home_camera_operation_1_item_mute_port_true);
                    }
                }
            });
        }

        // switch screen
        mSwitchScreenImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
                    Log.d(TAG, "click switch screen, switch to landscape");
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }else if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
                    Log.d(TAG, "click switch screen, switch to portrait");
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
        });

        // camera history
        if(null != mHistoryImageView) {
            mHistoryImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(ActivitySmartHomeCamera.this, ActivityCameraHistory.class);
                    startActivity(intent);
                }
            });
        }

        // call
        mCallImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ActivitySmartHomeCamera.this, ActivitySmartHomeCameraCalling.class);
                startActivity(intent);
            }
        });

        // photos
        if(null != mPhotosImageView){
            mPhotosImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(ActivitySmartHomeCamera.this, ActivityCameraPhotos.class);
                    startActivity(intent);
                }
            });
        }

        // settings
        if(null != mSettingsImageView){
            mSettingsImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(ActivitySmartHomeCamera.this, ActivityCameraSettings.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            Log.d(TAG, "on back pressed, switch to portrait");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else {
            if (mSmartHomeSleepConfirmUtil.doBackPress()) {

            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void sleepCamera() {
        //执行摄像头睡眠后的操作
        //TODO sleep camera
        Log.d(TAG, "sleep camera");
        isCameraSleeping = true;
        mOperationLayout1.setVisibility(View.INVISIBLE);
        mSleepImageView.setImageResource(R.mipmap.smart_home_camera_operation_2_item_sleep_true);
        for(int i = 0; i < mOperationLayout2.getChildCount(); i++){
            View v = mOperationLayout2.getChildAt(i);
            if(v.getId() != mSleepImageView.getId()){
                v.setVisibility(View.GONE);
            }
        }
        mOperationLayout2.getLayoutParams().width = mOperationLayout2.getLayoutParams().height;
        mSleepingImageView.setVisibility(View.VISIBLE);
        mSleepingTextView.setVisibility(View.VISIBLE);
        mSpeedTextView.setVisibility(View.GONE);
        mTimeTextView.setVisibility(View.GONE);
    }

    private void showSwitchHdPopWindow(View view){
        final List<String> itemList = new ArrayList<>();
        itemList.add(getString(R.string.smart_home_camera_switch_hd_high));
        itemList.add(getString(R.string.smart_home_camera_switch_hd_fluent));
        itemList.add(getString(R.string.smart_home_camera_switch_hd_auto));
        if(mSwitchHdPopupWindow != null && mSwitchHdPopupWindow.isShowing()) {
            mSwitchHdPopupWindow.dismiss();
            mSwitchHdPopupWindow = null;
        }else {
            WindowManager windowManager  = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            View inflate = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_tab_layout_popup_window, null);
            ListView listview = (ListView) inflate.findViewById(R.id.listview);

            listview.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return itemList.size();
                }

                @Override
                public Object getItem(int position) {
                    return null;
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {
                    View view;
                    if(convertView == null) {
                        view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_tab_layout_item_dance_category,null);
                    }else {
                        view = convertView;
                    }
                    final TextView name= (TextView) view.findViewById(R.id.dance_name);

                    name.setText(itemList.get(position));

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //点击事件

                            //关闭窗口
                            mSwitchHdPopupWindow.dismiss();

                        }
                    });

                    return view;
                }
            });

            mSwitchHdPopupWindow = new PopupWindow(inflate, getResources().getDimensionPixelSize(R.dimen.smart_home_camera_switch_hd_window_width), ViewPager.LayoutParams.WRAP_CONTENT);
            mSwitchHdPopupWindow.setOutsideTouchable(true);

            int[] location = new int[2];
            view.getLocationOnScreen(location);
            int x = location[0] + view.getWidth() / 2 - mSwitchHdPopupWindow.getWidth() / 2;
            int y = location[1] - getResources().getDimensionPixelSize(R.dimen.public_popup_window_line_height) * itemList.size();
            mSwitchHdPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, x, y);
        }
    }

    @Override
    protected void onDestroy() {
        if(mSwitchHdPopupWindow != null){
            mSwitchHdPopupWindow.dismiss();
            mSwitchHdPopupWindow = null;
        }
        super.onDestroy();
    }

}
