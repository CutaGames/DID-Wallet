package com.tancheng.carbonchain.activities.smarthome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tancheng.carbonchain.R;

public class ActivitySmartHomeAddDevice extends AppCompatActivity {

    private ImageView brand1;
    private ImageView brand2;
    private ImageView brand3;
    private ImageView brand4;
    private ImageView brand5;
    private ImageView brand6;
    private ImageView brand7;
    private ImageView brand8;
    private ImageView brand9;
    private ImageView brand10;
    private ImageView brand11;
    private ImageView brand12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_home_add_device);
        findViews();
        setOnClickListeners();
    }

    private void findViews(){
        brand1 = findViewById(R.id.brand1);
        brand2 = findViewById(R.id.brand2);
        brand3 = findViewById(R.id.brand3);
        brand4 = findViewById(R.id.brand4);
        brand5 = findViewById(R.id.brand5);
        brand6 = findViewById(R.id.brand6);
        brand7 = findViewById(R.id.brand7);
        brand8 = findViewById(R.id.brand8);
        brand9 = findViewById(R.id.brand9);
        brand10 = findViewById(R.id.brand10);
        brand11 = findViewById(R.id.brand11);
        brand12 = findViewById(R.id.brand12);
    }

    private void setOnClickListeners(){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ActivitySmartHomeAddDevice.this, ActivitySmartHomeBrandIntroduce.class);
                startActivity(intent);
            }
        };
        brand1.setOnClickListener(listener);
        brand2.setOnClickListener(listener);
        brand3.setOnClickListener(listener);
        brand4.setOnClickListener(listener);
        brand5.setOnClickListener(listener);
        brand6.setOnClickListener(listener);
        brand7.setOnClickListener(listener);
        brand8.setOnClickListener(listener);
        brand9.setOnClickListener(listener);
        brand10.setOnClickListener(listener);
        brand11.setOnClickListener(listener);
        brand12.setOnClickListener(listener);
    }
}
