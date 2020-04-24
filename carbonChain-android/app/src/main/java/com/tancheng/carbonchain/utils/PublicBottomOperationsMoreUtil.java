package com.tancheng.carbonchain.utils;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.move.ActivityMoveTargetFolderMain;

/**
 * Created by gll on 2020/2/15.
 */

public class PublicBottomOperationsMoreUtil extends RelativeLayout {

    private static final String TAG = "BottomOperationsMoreUti";

    public Boolean isAddToParentView = false;

    private TextView moreTitle;
    private LinearLayout moreImageLayout;
    private RelativeLayout moreRenameLayout;
    private ViewGroup mViewGroup;

    private PublicRenameUtil mPublicRenameUtil;

    private TextView mFileNameTextView;

    public PublicBottomOperationsMoreUtil(Context context, ViewGroup viewGroup) {
        super(context);
        this.mViewGroup = viewGroup;
        initViews();
    }

    public PublicBottomOperationsMoreUtil(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PublicBottomOperationsMoreUtil(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initViews(){
        View view = inflate(getContext(), R.layout.public_bottom_operations_more_layout, this);
        view.setOnClickListener(null);
        ImageView collect = view.findViewById(R.id.operation_more_collect);
        collect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                removeFromParent();
            }
        });

        ImageView copy = view.findViewById(R.id.operation_more_copy);
        copy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getContext(), ActivityMoveTargetFolderMain.class);
                getContext().startActivity(intent);
            }
        });

        mPublicRenameUtil = new PublicRenameUtil(getContext());
        RelativeLayout.LayoutParams renameLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPublicRenameUtil.setLayoutParams(renameLayoutParams);

        ImageView rename = view.findViewById(R.id.operation_more_rename);
        rename.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mPublicRenameUtil.isAddToParentView = true;
                mViewGroup.addView(mPublicRenameUtil);
            }
        });

        moreTitle = view.findViewById(R.id.more_title);
        moreImageLayout = view.findViewById(R.id.more_image_layout);
        moreRenameLayout = view.findViewById(R.id.bottom_operation_icon_rename_layout);
        mFileNameTextView = view.findViewById(R.id.more_image_file_name);
    }

    public Boolean doBackPress(){
        if(mPublicRenameUtil.doBackPress()){
            return true;
        }else if(isAddToParentView){
            removeFromParent();
            return true;
        }else{
            return false;
        }
    }

    private void removeFromParent(){
        ViewGroup parent = (ViewGroup) getParent();
        if(parent != null && parent instanceof ViewGroup){
            parent.removeView(PublicBottomOperationsMoreUtil.this);
        }
        isAddToParentView = false;
    }

    public void setTypeImage(){
        moreTitle.setVisibility(View.GONE);
        moreImageLayout.setVisibility(View.VISIBLE);
        moreRenameLayout.setVisibility(View.VISIBLE);
    }

    public void setTypeMp3(){
        moreTitle.setVisibility(View.GONE);
        moreImageLayout.setVisibility(View.VISIBLE);
        moreRenameLayout.setVisibility(View.VISIBLE);
    }

    public void setMoreFileName(String str){
        mFileNameTextView.setText(str);
    }
}
