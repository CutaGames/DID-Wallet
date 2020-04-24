package com.tancheng.carbonchain.activities.storage.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tancheng.carbonchain.R;

/**
 * 修改密码
 */
public class ActivityModifyPassword extends AppCompatActivity {
    private LinearLayout llInputOldPsd,llSetPsd,llSave;
    private TextView tvNext,tvNextTwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        initview();
        setOnClickListener();
    }

    private void initview() {
        llInputOldPsd = findViewById(R.id.llInputOldPsd);
        llSetPsd = findViewById(R.id.llSetPsd);
        llSave = findViewById(R.id.llSave);
        tvNext = findViewById(R.id.tvNext);
        tvNextTwo = findViewById(R.id.tvNextTwo);
    }

    private void setOnClickListener() {
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llInputOldPsd.setVisibility(View.GONE);
                llSetPsd.setVisibility(View.VISIBLE);
            }
        });

        tvNextTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSave.setVisibility(View.VISIBLE);
                llSetPsd.setVisibility(View.GONE);
            }
        });



    }

}
