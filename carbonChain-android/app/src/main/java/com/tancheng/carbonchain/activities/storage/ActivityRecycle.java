package com.tancheng.carbonchain.activities.storage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.storage.picture.ActivityPictureDetail;

public class ActivityRecycle extends AppCompatActivity {

    private static final Boolean DEBUG = true;
    private static final String TAG = "ActivityRecycle";

    private RelativeLayout mItem1;
    private RelativeLayout mItem2;
    private RelativeLayout mItem3;

    private ImageView mItemSelectImageView1;
    private ImageView mItemSelectImageView2;
    private ImageView mItemSelectImageView3;

    private Boolean mItem1SelectState = false;
    private Boolean mItem2SelectState = false;
    private Boolean mItem3SelectState = false;

    private Boolean mItemLongSelectState = false;

    private LinearLayout mRecycleOprationsLayout;
    private RelativeLayout mRecycleOprationsRecovery;
    private RelativeLayout mRecycleOprationsDelete;
    private RelativeLayout mConfirmDeleteLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        findViews();
        setOnClickListeners();
    }

    private void findViews(){
        mItem1 = findViewById(R.id.recycle_item_layout_1);
        mItem2 = findViewById(R.id.recycle_item_layout_2);
        mItem3 = findViewById(R.id.recycle_item_layout_3);
        mItemSelectImageView1 = findViewById(R.id.recycle_item_icon_select_1);
        mItemSelectImageView2 = findViewById(R.id.recycle_item_icon_select_2);
        mItemSelectImageView3 = findViewById(R.id.recycle_item_icon_select_3);
        mRecycleOprationsLayout = findViewById(R.id.recycle_oprations_layout);
        mRecycleOprationsRecovery = findViewById(R.id.recycle_oprations_recovery);
        mRecycleOprationsDelete = findViewById(R.id.recycle_oprations_delete);
        mConfirmDeleteLayout = findViewById(R.id.confirm_delete_layout);
    }

    private void setOnClickListeners(){

        mRecycleOprationsRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click recovery");
                mRecycleOprationsLayout.setVisibility(View.GONE);
                mItemSelectImageView1.setVisibility(View.GONE);
                mItemSelectImageView2.setVisibility(View.GONE);
                mItemSelectImageView3.setVisibility(View.GONE);
                mItemSelectImageView1.setImageResource(R.mipmap.recycle_item_icon_select_false);
                mItemSelectImageView2.setImageResource(R.mipmap.recycle_item_icon_select_false);
                mItemSelectImageView3.setImageResource(R.mipmap.recycle_item_icon_select_false);
                mItem1SelectState = false;
                mItem2SelectState = false;
                mItem3SelectState = false;
                mItemLongSelectState = false;
            }
        });

        mRecycleOprationsDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click delete");
                mConfirmDeleteLayout.setVisibility(View.VISIBLE);
            }
        });

        mConfirmDeleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click confirm");
                mConfirmDeleteLayout.setVisibility(View.GONE);
            }
        });


        mItem1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (DEBUG) Log.d(TAG, "long click layout 1");
                mItemLongSelectState = true;
                if (mItem1SelectState || mItem2SelectState || mItem3SelectState || mItemLongSelectState){
                    if (mItem1SelectState){
                        mItem1SelectState = false;
                        mItemSelectImageView1.setImageResource(R.mipmap.recycle_item_icon_select_false);
                    }else{
                        mItem1SelectState = true;
                        mItemSelectImageView1.setImageResource(R.mipmap.recycle_item_icon_select_true);
                        mItemSelectImageView1.setVisibility(View.VISIBLE);
                        mItemSelectImageView2.setVisibility(View.VISIBLE);
                        mItemSelectImageView3.setVisibility(View.VISIBLE);
                        mRecycleOprationsLayout.setVisibility(View.VISIBLE);
                    }
                }else{
                    mItemSelectImageView1.setImageResource(R.mipmap.recycle_item_icon_select_false);
                    mItemSelectImageView2.setImageResource(R.mipmap.recycle_item_icon_select_false);
                    mItemSelectImageView3.setImageResource(R.mipmap.recycle_item_icon_select_false);
                    mItemSelectImageView1.setVisibility(View.VISIBLE);
                    mItemSelectImageView2.setVisibility(View.VISIBLE);
                    mItemSelectImageView3.setVisibility(View.VISIBLE);
                    mRecycleOprationsLayout.setVisibility(View.VISIBLE);
                    mItem1SelectState = true;
                    mItemSelectImageView1.setImageResource(R.mipmap.recycle_item_icon_select_true);
                }
                return true;
            }
        });
        mItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click layout 1");
                if (mItem1SelectState || mItem2SelectState || mItem3SelectState || mItemLongSelectState){
                    if (mItem1SelectState){
                        mItem1SelectState = false;
                        mItemSelectImageView1.setImageResource(R.mipmap.recycle_item_icon_select_false);
                    }else{
                        mItem1SelectState = true;
                        mItemSelectImageView1.setImageResource(R.mipmap.recycle_item_icon_select_true);
                    }
                }else{

                }
            }
        });

        mItem2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (DEBUG) Log.d(TAG, "long click layout 2");
                mItemLongSelectState = true;
                if (mItem1SelectState || mItem2SelectState || mItem3SelectState || mItemLongSelectState){
                    if (mItem2SelectState){
                        mItem2SelectState = false;
                        mItemSelectImageView2.setImageResource(R.mipmap.recycle_item_icon_select_false);
                    }else{
                        mItem2SelectState = true;
                        mItemSelectImageView2.setImageResource(R.mipmap.recycle_item_icon_select_true);
                        mItemSelectImageView1.setVisibility(View.VISIBLE);
                        mItemSelectImageView2.setVisibility(View.VISIBLE);
                        mItemSelectImageView3.setVisibility(View.VISIBLE);
                        mRecycleOprationsLayout.setVisibility(View.VISIBLE);
                    }
                }else{
                    mItemSelectImageView1.setImageResource(R.mipmap.recycle_item_icon_select_false);
                    mItemSelectImageView2.setImageResource(R.mipmap.recycle_item_icon_select_false);
                    mItemSelectImageView3.setImageResource(R.mipmap.recycle_item_icon_select_false);
                    mItemSelectImageView1.setVisibility(View.VISIBLE);
                    mItemSelectImageView2.setVisibility(View.VISIBLE);
                    mItemSelectImageView3.setVisibility(View.VISIBLE);
                    mRecycleOprationsLayout.setVisibility(View.VISIBLE);
                    mItem2SelectState = true;
                    mItemSelectImageView2.setImageResource(R.mipmap.recycle_item_icon_select_true);
                }
                return true;
            }
        });
        mItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click layout 2");
                if (mItem1SelectState || mItem2SelectState || mItem3SelectState || mItemLongSelectState){
                    if (mItem2SelectState){
                        mItem2SelectState = false;
                        mItemSelectImageView2.setImageResource(R.mipmap.recycle_item_icon_select_false);
                    }else{
                        mItem2SelectState = true;
                        mItemSelectImageView2.setImageResource(R.mipmap.recycle_item_icon_select_true);
                    }
                }else{
                    if (DEBUG) Log.d(TAG, "click picture");
                    Intent intent = new Intent();
                    intent.setClass(ActivityRecycle.this, ActivityPictureDetail.class);
                    startActivity(intent);
                }
            }
        });

        mItem3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (DEBUG) Log.d(TAG, "long click layout 3");
                mItemLongSelectState = true;
                if (mItem1SelectState || mItem2SelectState || mItem3SelectState || mItemLongSelectState){
                    if (mItem3SelectState){
                        mItem3SelectState = false;
                        mItemSelectImageView3.setImageResource(R.mipmap.recycle_item_icon_select_false);
                    }else{
                        mItem3SelectState = true;
                        mItemSelectImageView3.setImageResource(R.mipmap.recycle_item_icon_select_true);
                        mItemSelectImageView1.setVisibility(View.VISIBLE);
                        mItemSelectImageView2.setVisibility(View.VISIBLE);
                        mItemSelectImageView3.setVisibility(View.VISIBLE);
                        mRecycleOprationsLayout.setVisibility(View.VISIBLE);
                    }
                }else{
                    mItemSelectImageView1.setImageResource(R.mipmap.recycle_item_icon_select_false);
                    mItemSelectImageView2.setImageResource(R.mipmap.recycle_item_icon_select_false);
                    mItemSelectImageView3.setImageResource(R.mipmap.recycle_item_icon_select_false);
                    mItemSelectImageView1.setVisibility(View.VISIBLE);
                    mItemSelectImageView2.setVisibility(View.VISIBLE);
                    mItemSelectImageView3.setVisibility(View.VISIBLE);
                    mRecycleOprationsLayout.setVisibility(View.VISIBLE);
                    mItem3SelectState = true;
                    mItemSelectImageView3.setImageResource(R.mipmap.recycle_item_icon_select_true);
                }
                return true;
            }
        });
        mItem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEBUG) Log.d(TAG, "click layout 3");
                if (mItem1SelectState || mItem2SelectState || mItem3SelectState || mItemLongSelectState){
                    if (mItem3SelectState){
                        mItem3SelectState = false;
                        mItemSelectImageView3.setImageResource(R.mipmap.recycle_item_icon_select_false);
                    }else{
                        mItem3SelectState = true;
                        mItemSelectImageView3.setImageResource(R.mipmap.recycle_item_icon_select_true);
                    }
                }else{

                }
            }
        });
    }
}
