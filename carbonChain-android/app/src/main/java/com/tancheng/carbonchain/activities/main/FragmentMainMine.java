package com.tancheng.carbonchain.activities.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.ui.WalletManageActivity;
import com.tancheng.carbonchain.activities.bindsevice.CustomCaptureActivity;
import com.tancheng.carbonchain.activities.storage.mine.ManageUsersActivity;

/**
 * Created by gll on 2020/2/8.
 */

public class FragmentMainMine extends Fragment {
    private RelativeLayout rlUserInfo, rlWalletManage, rlUserManage, rlDeviceManage, rlSoftwareManage, rlHelp, rlMyId, rlAbout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_mine, null);
        initView(view);
        setOnClickListeners();
        return view;
    }

    private void initView(View view) {
        rlUserInfo = view.findViewById(R.id.rlUserInfo);
        rlUserManage = view.findViewById(R.id.rlUserManage);
        rlWalletManage = view.findViewById(R.id.rlWalletManage);
        rlDeviceManage = view.findViewById(R.id.rlDeviceManage);
        rlSoftwareManage = view.findViewById(R.id.rlSoftwareManage);
        rlHelp = view.findViewById(R.id.rlHelp);
        rlMyId = view.findViewById(R.id.rlMyId);
        rlAbout = view.findViewById(R.id.rlAbout);
    }


    private void setOnClickListeners() {
        rlUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), CustomCaptureActivity.class);
                getContext().startActivity(intent);
            }
        });
        rlUserManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), ManageUsersActivity.class);
                getContext().startActivity(intent);
            }
        });
        rlWalletManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), WalletManageActivity.class);
                getContext().startActivity(intent);
            }
        });

    }


}
