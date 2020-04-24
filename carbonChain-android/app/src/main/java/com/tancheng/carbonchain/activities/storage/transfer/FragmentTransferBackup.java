package com.tancheng.carbonchain.activities.storage.transfer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tancheng.carbonchain.R;

/**
 * Created by gll on 2020/2/13.
 */

public class FragmentTransferBackup extends Fragment {
    private static final Boolean DEBUG = true;
    private static final String TAG = "FragmentTransferBackup";



    public FragmentTransferBackup() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transfer_backup, container,false);
        findViews(view);
        setOnClickListeners();
        return view;
    }

    private void findViews(View view){
    }

    private void setOnClickListeners(){
    }

}
