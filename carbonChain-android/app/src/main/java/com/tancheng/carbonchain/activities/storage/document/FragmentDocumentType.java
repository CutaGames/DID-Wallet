package com.tancheng.carbonchain.activities.storage.document;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.utils.PublicBottomOperationsUtil;
import com.tancheng.carbonchain.utils.SelectableItem;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gll on 2020/2/13.
 */

public class FragmentDocumentType extends Fragment {


    private int mWindowWidth;
    private int mWindowHeight;
    private PublicBottomOperationsUtil mPublicBottomOperationsUtil;
    private ViewGroup mViewGroup;
    private LinearLayout mDocumentBodyLayout;

    private Boolean mOnLongTouchMode = false;
    private List mSelectableDocuments;

    public FragmentDocumentType() {
    }


    @SuppressLint("ValidFragment")
    public FragmentDocumentType(ViewGroup viewGroup) {
        this.mViewGroup = viewGroup;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_document_type, container,false);

        mWindowWidth = this.getResources().getDisplayMetrics().widthPixels;
        mWindowHeight = this.getResources().getDisplayMetrics().heightPixels;

        findViews(view);

        initDocumentViews();
        initBottomOperationUtil();

        setOnLongClickListeners();
        setOnClickListeners();
        return view;
    }

    private void findViews(View view){
        mDocumentBodyLayout = view.findViewById(R.id.document_body_layout);

    }

    private void initBottomOperationUtil(){
        mPublicBottomOperationsUtil = new PublicBottomOperationsUtil(getContext(), mViewGroup);
        RelativeLayout.LayoutParams bottomOperationsLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPublicBottomOperationsUtil.setLayoutParams(bottomOperationsLayoutParams);
    }

    private void initDocumentViews(){
        mSelectableDocuments = new ArrayList<SelectableItem>();
        //创建临时数据
        TextView textView1 = new TextView(getContext());
        LinearLayout.LayoutParams textViewLayoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textViewLayoutParams1.setMargins(
                (int)getResources().getDimension(R.dimen.video_time_default_text_margin_left),
                (int)getResources().getDimension(R.dimen.video_time_default_text_margin_top),
                0,
                (int)getResources().getDimension(R.dimen.video_time_default_text_margin_bottom)
        );
        textView1.setLayoutParams(textViewLayoutParams1);
        textView1.setText(getText(R.string.video_time_default_text_1));
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.video_time_default_text_size));
        textView1.setTextColor(getResources().getColor(R.color.item_title_time_default_text_color));
        mDocumentBodyLayout.addView(textView1);

        for(int j = 0; j < 3; j++) {
            SelectableItem selectableItem = new SelectableItem(getContext());
            LinearLayout.LayoutParams documentLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            selectableItem.setLayoutParams(documentLayoutParams);
            selectableItem.getShowImage().setImageResource(R.mipmap.item_pre_icon_default_doc);
            mSelectableDocuments.add(selectableItem);
            mDocumentBodyLayout.addView(selectableItem);
        }

        TextView textView2 = new TextView(getContext());
        LinearLayout.LayoutParams textViewLayoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textViewLayoutParams2.setMargins(
                (int)getResources().getDimension(R.dimen.video_time_default_text_margin_left),
                (int)getResources().getDimension(R.dimen.video_time_default_text_margin_top),
                0,
                (int)getResources().getDimension(R.dimen.video_time_default_text_margin_bottom)
        );
        textView2.setLayoutParams(textViewLayoutParams2);
        textView2.setText(getText(R.string.video_time_default_text_2));
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.video_time_default_text_size));
        textView2.setTextColor(getResources().getColor(R.color.item_title_time_default_text_color));
        mDocumentBodyLayout.addView(textView2);
        for(int j = 0; j < 2; j++) {
            SelectableItem selectableItem = new SelectableItem(getContext());
            LinearLayout.LayoutParams documentLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            selectableItem.setLayoutParams(documentLayoutParams);
            selectableItem.getShowImage().setImageResource(R.mipmap.item_pre_icon_default_doc);
            mSelectableDocuments.add(selectableItem);
            mDocumentBodyLayout.addView(selectableItem);
        }
    }



    public void selectTime(int position){
        //筛选器 选择不同时间


        //重置时间后，退出长按模式
        removeBottomOperationsIfExits();
    }

    public void removeBottomOperationsIfExits(){
        if(mPublicBottomOperationsUtil.isAddToParentView){
            mOnLongTouchMode = false;
            mPublicBottomOperationsUtil.isAddToParentView = false;
            mViewGroup.removeView(mPublicBottomOperationsUtil);
            cancelLongClickMode();
        }
    }

    public void cancelLongClickMode(){
        mOnLongTouchMode = false;
        for(int i = 0; i < mSelectableDocuments.size(); i++){
            SelectableItem selectableItem = (SelectableItem) mSelectableDocuments.get(i);
            selectableItem.cancelLongClickMode();
        }
    }

    private void setOnClickListeners(){
        View.OnClickListener selectableDocumentOnClickListener = new View.OnClickListener() {
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
                    // 跳转到单独视频界面
                    jumpToDocument();
                }
            }
        };
        for(int i = 0; i < mSelectableDocuments.size(); i++){
            SelectableItem selectableItem = (SelectableItem) mSelectableDocuments.get(i);
            selectableItem.setOnClickListener(selectableDocumentOnClickListener);
        }
    }

    private void setOnLongClickListeners(){
        View.OnLongClickListener selectableDocumentOnLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                for(int i = 0; i < mSelectableDocuments.size(); i++){
                    SelectableItem selectableItem = (SelectableItem) mSelectableDocuments.get(i);
                    selectableItem.getSelectIcon().setVisibility(View.VISIBLE);
                }
                SelectableItem selectableItem = (SelectableItem) view;
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
            selectableItem.setOnLongClickListener(selectableDocumentOnLongClickListener);
        }
    }

    public Boolean doBackPressed() {
        if(mPublicBottomOperationsUtil.doBackPressed() == false){
            return false;
        }
        if(mPublicBottomOperationsUtil.isAddToParentView == false){
            cancelLongClickMode();
            return true;
        }else{
            return true;
        }
    }

    private void jumpToDocument(){
        Intent intent = new Intent();
        intent.setClass(getActivity(), ActivityShowDocument.class);
        startActivity(intent);
    }
}
