package com.tancheng.carbonchain.activities.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.smarthome.camera.ActivitySmartHomeCamera;

/**
 * Created by gll on 2020/2/13.
 */

public class FragmentSmartHomeEncrypt extends Fragment {
    private static final Boolean DEBUG = true;
    private static final String TAG = "FragmentSmartHomeEncrypt";

    private LinearLayout mContainer;

    public FragmentSmartHomeEncrypt() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smart_home_encrypt, container,false);
        findViews(view);
        initViews();
        return view;
    }

    private void findViews(View view){
        mContainer = (LinearLayout)view.findViewById(R.id.container);
    }

    private void initViews(){
        SmartHomeItem smartHomeItem1 = new SmartHomeItem(getContext());
        smartHomeItem1.setShowImage(R.mipmap.smart_home_item_encrypt_device_default_1);
        smartHomeItem1.setTitleText(getString(R.string.smart_home_item_encrypt_tancheng_default_1));
        smartHomeItem1.setStatusText(getString(R.string.smart_home_item_status_on_line));
        smartHomeItem1.setStatusTextColor(R.color.smart_home_device_online_color);
        smartHomeItem1.setStatusImageView(R.mipmap.smart_home_item_status_on_line);
        LinearLayout.LayoutParams layout1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        smartHomeItem1.setLayoutParams(layout1);
        mContainer.addView(smartHomeItem1);
        smartHomeItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ActivitySmartHomeCamera.class);
                getContext().startActivity(intent);
            }
        });
    }

}
