package com.tancheng.carbonchain.activities.main;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tancheng.carbonchain.R;

/**
 * Created by gll on 2020/2/8.
 */

public class FragmentMainFind extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_find, null);
        return view;
    }
}
