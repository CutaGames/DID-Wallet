package com.tancheng.carbonchain.activities.asset.digit;

import android.os.Bundle;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.base.BaseFragment;

/**
 * Created by gll on 2020/2/8.
 */

public class FragmentMainAssetDigitHard extends BaseFragment {

    /**
     * 标志位，标志已经初始化完成
     */
    private boolean isPrepared;
    /**
     * 是否已被加载过一次，第二次就不再去请求数据了
     */
    private boolean mHasLoadedOnce;

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        if (mView == null) {
//            // 需要inflate一个布局文件 填充Fragment
//            mView = inflater.inflate(R.layout.fragment_main_asset_digit_hard, container, false);
//            isPrepared = true;
////        实现懒加载
//            lazyLoad();
//        }
//        //缓存的mView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个mView已经有parent的错误。
//        ViewGroup parent = (ViewGroup) mView.getParent();
//        if (parent != null) {
//            parent.removeView(mView);
//        }
//
//        return mView;
//    }

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
        return R.layout.fragment_main_asset_digit_hard;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {
//        Bundle bundle = getArguments();
//        String args = bundle.getString("agrs1");
    }

    @Override
    public void configViews() {

    }

    public static FragmentMainAssetDigitHard newInstance(String param1) {
        FragmentMainAssetDigitHard fragment = new FragmentMainAssetDigitHard();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }
}
