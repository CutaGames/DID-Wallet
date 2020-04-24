package com.tancheng.carbonchain.activities.smarthome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tancheng.carbonchain.R;

/**
 * Created by gll on 2020/2/13.
 */

public class FragmentSmartHomeNoEncrypt extends Fragment {
    private static final Boolean DEBUG = true;
    private static final String TAG = "FragmentSmartHomeNoEncrypt";

    private LinearLayout mContainer;

    public FragmentSmartHomeNoEncrypt() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smart_home_no_encrypt, container,false);
        findViews(view);
        initViews();
        return view;
    }

    private void findViews(View view){
        mContainer = (LinearLayout)view.findViewById(R.id.container);
    }

    private void initViews(){
        SmartHomeItem smartHomeItem2 = new SmartHomeItem(getContext());
        smartHomeItem2.setShowImage(R.mipmap.smart_home_item_no_encrypt_device_default_2);
        smartHomeItem2.setTitleText(getString(R.string.smart_home_item_encrypt_tancheng_default_2));
        smartHomeItem2.setStatusText(getString(R.string.smart_home_item_status_off_line));
        smartHomeItem2.setStatusTextColor(R.color.smart_home_device_offline_color);
        smartHomeItem2.setStatusImageView(R.mipmap.smart_home_item_status_off_line);
        LinearLayout.LayoutParams layout2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        smartHomeItem2.setLayoutParams(layout2);
        mContainer.addView(smartHomeItem2);
    }

}
