package com.tancheng.carbonchain.activities.asset.data;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import com.tancheng.carbonchain.R;

public class ActivityAssetDataAdd extends AppCompatActivity {

    private ScrollView mStep1Layout;
    private Button mStep1BtnNext;
    private ScrollView mStep2Layout;
    private Button mStep2BtnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_data_add);
        findViews();
        setOnClickListeners();
    }

    private void findViews(){
        mStep1Layout = findViewById(R.id.step_1_layout);
        mStep1BtnNext = findViewById(R.id.btn_step_1_next);
        mStep2Layout = findViewById(R.id.step_2_layout);
        mStep2BtnNext = findViewById(R.id.btn_step_2_next);
    }

    private void setOnClickListeners(){
        // step 1 next btn
        mStep1BtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStep1Layout.setVisibility(View.GONE);
                mStep2Layout.setVisibility(View.VISIBLE);
            }
        });


    }
}
