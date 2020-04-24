package com.tancheng.carbonchain.activities.storage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tancheng.carbonchain.R;

public class ActivityShareToFriends extends AppCompatActivity {

    private TextView mCancel;
    private TextView mShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_to_friends);

        findViews();
        setOnClickListeners();
    }

    private void findViews(){
        mCancel = findViewById(R.id.custom_action_bar_cancel);
        mShare = findViewById(R.id.custom_action_bar_share);
    }

    private void setOnClickListeners(){
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
