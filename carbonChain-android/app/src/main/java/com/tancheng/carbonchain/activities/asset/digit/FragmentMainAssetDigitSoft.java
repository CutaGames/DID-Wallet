package com.tancheng.carbonchain.activities.asset.digit;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * Created by gll on 2020/2/8.
 */

public class FragmentMainAssetDigitSoft extends BaseFragment {
     //标志位，标志已经初始化完成
    private boolean isPrepared;
   //是否已被加载过一次，第二次就不再去请求数据了
    private boolean mHasLoadedOnce;
    private RelativeLayout mAddWallet;
    private List mWalletList;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        if (mView == null) {
//            // 需要inflate一个布局文件 填充Fragment
//            mView = inflater.inflate(R.layout.fragment_main_asset_digit_soft, container, false);
//            initView();
//            isPrepared = true;
//            //实现懒加载
//            lazyLoad();
//            findViews(mView);
//            setOnClickListeners();
//        }
//        //缓存的mView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个mView已经有parent的错误。
//        ViewGroup parent = (ViewGroup) mView.getParent();
//        if (parent != null) {
//            parent.removeView(mView);
//        }
//
//        return mView;
//    }

    @OnClick({R.id.add_wallet})
    public void myClickListeners(View view){

        switch (view.getId()){
            case R.id.add_wallet:
                final Dialog dialog = new Dialog(getContext(), R.style.full_screen_dialog);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.asset_add_soft_wallet_dialog_layout);

                Window window = dialog.getWindow();
                if (window != null) {
                    WindowManager.LayoutParams lp = window.getAttributes();
                    lp.gravity = Gravity.BOTTOM;
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(lp);
                }

                // 第2套方案 控制弹出框位置
//                LayoutInflater inflater = LayoutInflater.from(getContext());
//                View viewDialog = inflater.inflate(R.layout.asset_add_soft_wallet_dialog_layout, null);
//                int width = getResources().getDisplayMetrics().widthPixels;
//                int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
//                int statusBarHeight = 0;
//                if(resourceId > 0){
//                    statusBarHeight = getResources().getDimensionPixelSize(resourceId);
//                }
//                int height = getResources().getDisplayMetrics().heightPixels - statusBarHeight;
//                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
//                dialog.setContentView(viewDialog, layoutParams);

                dialog.show();

                // on click
                TextView cancel = dialog.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                TextView createWallet = dialog.findViewById(R.id.create_wallet);
                createWallet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), ActivitySoftWalletCreateSetPassword.class);
                        dialog.dismiss();
                        startActivity(intent);
                    }
                });

                TextView importWallet = dialog.findViewById(R.id.import_wallet);
                importWallet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), ActivitySoftWalletImportSelectCoin.class);
                        dialog.dismiss();
                        startActivity(intent);
                    }
                });
                break;
        }
    }

    @Override
    public void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        //填充各控件的数据
        mHasLoadedOnce = true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main_asset_digit_soft;
    }

    @Override
    public void attachView() {
        mAddWallet = parentView.findViewById(R.id.add_wallet);
        View lsWallet1 = parentView.findViewById(R.id.ls_wallet_1);
        View lsWallet2 = parentView.findViewById(R.id.ls_wallet_2);
        mWalletList.add(lsWallet1);
        mWalletList.add(lsWallet2);
    }

    @Override
    public void initDatas() {
        Bundle bundle = getArguments();
        String args = bundle.getString("agrs1");
        mWalletList = new ArrayList<>();
    }

    @Override
    public void configViews() {

    }

    public static FragmentMainAssetDigitSoft newInstance(String param1) {
        FragmentMainAssetDigitSoft fragment = new FragmentMainAssetDigitSoft();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }
}
