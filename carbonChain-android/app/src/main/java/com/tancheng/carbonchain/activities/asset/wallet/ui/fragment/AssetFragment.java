package com.tancheng.carbonchain.activities.asset.wallet.ui.fragment;

import android.support.v4.view.ViewPager;

import com.blankj.utilcode.util.ConvertUtils;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.TextWidthColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.data.FragmentMainAssetData;
import com.tancheng.carbonchain.activities.asset.digit.FragmentMainAssetDigitHard;
import com.tancheng.carbonchain.activities.asset.wallet.domain.EventBusMsgType;
import com.tancheng.carbonchain.activities.asset.wallet.domain.MessageWrap;
import com.tancheng.carbonchain.activities.asset.wallet.ui.adapter.BannerViewHolder;
import com.tancheng.carbonchain.activities.asset.wallet.ui.adapter.LoadWalletPageFragmentAdapter;
import com.tancheng.carbonchain.activities.base.BaseFragment;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * created by tc_collin on 2020/4/13 18:11
 * Description:资产主界面
 * version: 1.0
 */
public class AssetFragment extends BaseFragment {

    @BindView(R.id.indicator_view)
    ScrollIndicatorView indicatorView;
    @BindView(R.id.vp_wallet)
    ViewPager vpWallet;

    private List<BaseFragment> fragmentList = new ArrayList<>();
    private LoadWalletPageFragmentAdapter pageAdapter;
    private IndicatorViewPager indicatorViewPager;


    @Override
    public void lazyLoad() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_asset;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {

        String[] tabNams = {"热钱包", "硬件钱包", "理财"};
        fragmentList.add(new AssetHotWaletMainFragment());
        fragmentList.add(new FragmentMainAssetDigitHard());
        fragmentList.add(new FragmentMainAssetData());

        indicatorView.setSplitAuto(true);
        indicatorView.setOnTransitionListener(new OnTransitionTextListener()
                .setColor(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.black))
                .setSize(15, 14));
//        indicatorView.setScrollBar(new TextWidthColorBar(mContext, indicatorView, getResources().getColor(R.color.colorPrimary), ConvertUtils.dp2px(0)));
//        indicatorView.setScrollBarSize(50);
        indicatorViewPager = new IndicatorViewPager(indicatorView, vpWallet);
        pageAdapter = new LoadWalletPageFragmentAdapter(mContext, getChildFragmentManager(), fragmentList, tabNams);
        indicatorViewPager.setAdapter(pageAdapter);
        indicatorViewPager.setCurrentItem(0, false);
        vpWallet.setOffscreenPageLimit(3);
    }

    @Override
    public void configViews() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
