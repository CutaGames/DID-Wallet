package com.tancheng.carbonchain.activities.asset.record;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.utils.BusinessRecordItem;

/**
 * Created by gll on 2020/2/13.
 */

public class FragmentBusinessRecordTrade extends Fragment {
    private static final Boolean DEBUG = true;
    private static final String TAG = "FragmentBusinessRecordTrade";


    private ViewGroup mContainer;

    public FragmentBusinessRecordTrade() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_trade, container,false);
        findViews(view);
        initViews();
        setOnClickListeners();
        return view;
    }

    private void findViews(View view){
        mContainer = view.findViewById(R.id.container);
    }

    private void initViews(){
        // insert some views
        BusinessRecordItem businessRecordItem3 = new BusinessRecordItem(getContext());
        businessRecordItem3.setBusinessStatus(BusinessRecordItem.BUSINESS_STATUS_COMPLETE);
        businessRecordItem3.setIsProfit(false);
        businessRecordItem3.setBusinessNumber("0.8");
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        businessRecordItem3.setLayoutParams(layoutParams3);
        mContainer.addView(businessRecordItem3);
    }

    private void setOnClickListeners(){
    }

}
