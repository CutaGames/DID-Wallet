package com.tancheng.carbonchain.activities.storage.secure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.storage.document.ActivityShowDocument;
import com.tancheng.carbonchain.utils.PublicBottomOperationsUtil;
import com.tancheng.carbonchain.utils.SelectableItem;

import java.util.ArrayList;
import java.util.List;

public class ActivitySecure extends AppCompatActivity {

    private static final Boolean DEBUG = true;
    private static final String TAG = "ActivitySecure";
    private FrameLayout mViewGroup;
    private LinearLayout mContainer;
    private List mSelectableItems;
    private Boolean mOnLongTouchMode = false;
    private PublicBottomOperationsUtil mPublicBottomOperationsUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secure);
        findViews();
        initViews();
        initBottomOperationUtil();
        setOnLongClickListeners();
        setOnClickListeners();
    }

    private void findViews(){
        mViewGroup = findViewById(R.id.root_layout);
        mContainer = (LinearLayout) findViewById(R.id.container);
    }

    private void initViews(){
        mSelectableItems = new ArrayList<SelectableItem>();
        // insert some items

        SelectableItem selectableItemFolder = new SelectableItem(ActivitySecure.this);
        LinearLayout.LayoutParams folderLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        selectableItemFolder.setLayoutParams(folderLayoutParams);
        selectableItemFolder.getShowImage().setImageResource(R.mipmap.item_pre_icon_default_folder);
        selectableItemFolder.setItemTitleText(getString(R.string.public_item_title_default_text_folder));
        mSelectableItems.add(selectableItemFolder);
        mContainer.addView(selectableItemFolder);

        SelectableItem selectableItemPicture = new SelectableItem(ActivitySecure.this);
        LinearLayout.LayoutParams pictureLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        selectableItemPicture.setLayoutParams(pictureLayoutParams);
        selectableItemPicture.getShowImage().setImageResource(R.mipmap.search_result_default_car);
        selectableItemPicture.setItemTitleText(getString(R.string.public_item_picture_text));
        mSelectableItems.add(selectableItemPicture);
        mContainer.addView(selectableItemPicture);

        SelectableItem selectableItemPpt = new SelectableItem(ActivitySecure.this);
        LinearLayout.LayoutParams pptLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        selectableItemPpt.setLayoutParams(pptLayoutParams);
        selectableItemPpt.getShowImage().setImageResource(R.mipmap.item_pre_icon_default_ppt);
        selectableItemPpt.setItemTitleText(getString(R.string.public_item_document_text));
        mSelectableItems.add(selectableItemPpt);
        mContainer.addView(selectableItemPpt);

    }

    private void initBottomOperationUtil(){
        mPublicBottomOperationsUtil = new PublicBottomOperationsUtil(ActivitySecure.this, mViewGroup);
        RelativeLayout.LayoutParams bottomOperationsLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPublicBottomOperationsUtil.setLayoutParams(bottomOperationsLayoutParams);
    }

    private void setOnLongClickListeners(){
        View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                for(int i = 0; i < mSelectableItems.size(); i++){
                    SelectableItem selectableItem = (SelectableItem) mSelectableItems.get(i);
                    selectableItem.getSelectIcon().setVisibility(View.VISIBLE);
                }
                SelectableItem selectableItem = (SelectableItem)view;
                if(selectableItem.getIsSelected()){
                    selectableItem.getSelectIcon().setImageResource(R.mipmap.image_icon_select_false);
                    selectableItem.setIsSelected(false);
                }else {
                    selectableItem.getSelectIcon().setImageResource(R.mipmap.image_icon_select_true);
                    selectableItem.setIsSelected(true);
                }
                mOnLongTouchMode = true;

                if(mPublicBottomOperationsUtil.isAddToParentView == false){
                    mPublicBottomOperationsUtil.isAddToParentView = true;
                    mViewGroup.addView(mPublicBottomOperationsUtil);
                }

                return true;
            }
        };

        for(int i = 0; i < mSelectableItems.size(); i++){
            SelectableItem selectableItem = (SelectableItem) mSelectableItems.get(i);
            selectableItem.setOnLongClickListener(onLongClickListener);
        }
    }

    private void setOnClickListeners(){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnLongTouchMode){
                    SelectableItem selectableItem = (SelectableItem) view;
                    if(selectableItem.getIsSelected()){
                        selectableItem.setIsSelected(false);
                        selectableItem.getSelectIcon().setImageResource(R.mipmap.image_icon_select_false);
                    }else{
                        selectableItem.setIsSelected(true);
                        selectableItem.getSelectIcon().setImageResource(R.mipmap.image_icon_select_true);
                    }
                }else{
                    // 跳转到打开文档界面
                    jumpToShowDocument();
                }
            }
        };

        for(int i = 0; i < mSelectableItems.size(); i++){
            SelectableItem selectableItem = (SelectableItem) mSelectableItems.get(i);
            selectableItem.setOnClickListener(onClickListener);
        }
    }

    private void jumpToShowDocument(){
        Intent intent = new Intent();
        intent.setClass(ActivitySecure.this, ActivityShowDocument.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if(mPublicBottomOperationsUtil.doBackPressed()){
            if(mPublicBottomOperationsUtil.isAddToParentView == false){
                cancelLongClickMode();
                mOnLongTouchMode = false;
            }
        }else {
            super.onBackPressed();
        }
    }

    private void cancelLongClickMode(){
        for(int i = 0; i < mSelectableItems.size(); i++){
            SelectableItem selectableItem = (SelectableItem) mSelectableItems.get(i);
            selectableItem.cancelLongClickMode();
        }
    }
}
