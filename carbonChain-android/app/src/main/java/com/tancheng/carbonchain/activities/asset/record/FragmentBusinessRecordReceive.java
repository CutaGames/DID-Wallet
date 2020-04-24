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

public class FragmentBusinessRecordReceive extends Fragment {
    private static final Boolean DEBUG = true;
    private static final String TAG = "FragmentBusinessRecordReceive";


    private ViewGroup mContainer;

    public FragmentBusinessRecordReceive() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_receive, container,false);
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
        BusinessRecordItem businessRecordItem1 = new BusinessRecordItem(getContext());
        businessRecordItem1.setBusinessStatus(BusinessRecordItem.BUSINESS_STATUS_COMPLETE);
        businessRecordItem1.setIsProfit(true);
        businessRecordItem1.setBusinessNumber("10.2");
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        businessRecordItem1.setLayoutParams(layoutParams1);
        mContainer.addView(businessRecordItem1);

        BusinessRecordItem businessRecordItem2 = new BusinessRecordItem(getContext());
        businessRecordItem2.setBusinessStatus(BusinessRecordItem.BUSINESS_STATUS_DOING);
        businessRecordItem2.setIsProfit(true);
        businessRecordItem2.setBusinessNumber("3.4");
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        businessRecordItem2.setLayoutParams(layoutParams2);
        mContainer.addView(businessRecordItem2);
    }

    private void setOnClickListeners(){
    }

}
