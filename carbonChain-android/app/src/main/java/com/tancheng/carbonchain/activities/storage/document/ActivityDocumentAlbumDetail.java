package com.tancheng.carbonchain.activities.storage.document;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.utils.PublicBottomOperationsUtil;
import com.tancheng.carbonchain.utils.SelectableItem;

import java.util.ArrayList;
import java.util.List;

public class ActivityDocumentAlbumDetail extends AppCompatActivity {

    private static final Boolean DEBUG = true;
    private static final String TAG = "ActivityDocumentAlbumDetail";
    private FrameLayout mViewGroup;
    private LinearLayout mContainer;
    private List mSelectableDocuments;
    private Boolean mOnLongTouchMode = false;
    private PublicBottomOperationsUtil mPublicBottomOperationsUtil;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_album_detail);
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
        mSelectableDocuments = new ArrayList<SelectableItem>();
        // insert some documents

        SelectableItem selectableItemPpt = new SelectableItem(ActivityDocumentAlbumDetail.this);
        LinearLayout.LayoutParams documentPptLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        selectableItemPpt.setLayoutParams(documentPptLayoutParams);
        selectableItemPpt.getShowImage().setImageResource(R.mipmap.item_pre_icon_default_ppt);
        mSelectableDocuments.add(selectableItemPpt);
        mContainer.addView(selectableItemPpt);

        SelectableItem selectableItemDoc = new SelectableItem(ActivityDocumentAlbumDetail.this);
        LinearLayout.LayoutParams documentDocLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        selectableItemDoc.setLayoutParams(documentDocLayoutParams);
        selectableItemDoc.getShowImage().setImageResource(R.mipmap.item_pre_icon_default_doc);
        mSelectableDocuments.add(selectableItemDoc);
        mContainer.addView(selectableItemDoc);

        SelectableItem selectableItemXlsx = new SelectableItem(ActivityDocumentAlbumDetail.this);
        LinearLayout.LayoutParams documentXlsxLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        selectableItemXlsx.setLayoutParams(documentXlsxLayoutParams);
        selectableItemXlsx.getShowImage().setImageResource(R.mipmap.item_pre_icon_default_xlsx);
        mSelectableDocuments.add(selectableItemXlsx);
        mContainer.addView(selectableItemXlsx);

        SelectableItem selectableItemPdf = new SelectableItem(ActivityDocumentAlbumDetail.this);
        LinearLayout.LayoutParams documentPdfLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        selectableItemPdf.setLayoutParams(documentPdfLayoutParams);
        selectableItemPdf.getShowImage().setImageResource(R.mipmap.item_pre_icon_default_pdf);
        mSelectableDocuments.add(selectableItemPdf);
        mContainer.addView(selectableItemPdf);

    }

    private void initBottomOperationUtil(){
        mPublicBottomOperationsUtil = new PublicBottomOperationsUtil(ActivityDocumentAlbumDetail.this, mViewGroup);
        RelativeLayout.LayoutParams bottomOperationsLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPublicBottomOperationsUtil.setLayoutParams(bottomOperationsLayoutParams);
    }

    private void setOnLongClickListeners(){
        View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                for(int i = 0; i < mSelectableDocuments.size(); i++){
                    SelectableItem selectableItem = (SelectableItem) mSelectableDocuments.get(i);
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

        for(int i = 0; i < mSelectableDocuments.size(); i++){
            SelectableItem selectableItem = (SelectableItem) mSelectableDocuments.get(i);
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

        for(int i = 0; i < mSelectableDocuments.size(); i++){
            SelectableItem selectableItem = (SelectableItem) mSelectableDocuments.get(i);
            selectableItem.setOnClickListener(onClickListener);
        }
    }

    private void jumpToShowDocument(){
        Intent intent = new Intent();
        intent.setClass(ActivityDocumentAlbumDetail.this, ActivityShowDocument.class);
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
        for(int i = 0; i < mSelectableDocuments.size(); i++){
            SelectableItem selectableItem = (SelectableItem) mSelectableDocuments.get(i);
            selectableItem.cancelLongClickMode();
        }
    }
}
