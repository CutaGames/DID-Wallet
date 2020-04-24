package com.tancheng.carbonchain.activities.asset.wallet.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.domain.EventBusMsgType;
import com.tancheng.carbonchain.activities.asset.wallet.domain.MessageWrap;
import com.tancheng.carbonchain.activities.asset.wallet.domain.TokenType;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletBannerModel;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;
import com.tancheng.carbonchain.activities.asset.wallet.ui.TokenAddActivity;
import com.tancheng.carbonchain.activities.asset.wallet.ui.GatheringQRActivity;
import com.tancheng.carbonchain.activities.asset.wallet.ui.TokenDetailActivity;
import com.tancheng.carbonchain.activities.asset.wallet.ui.WalletCreateActivity;
import com.tancheng.carbonchain.activities.asset.wallet.ui.WalletDetailActivity;
import com.tancheng.carbonchain.activities.asset.wallet.ui.adapter.BannerViewHolder;
import com.tancheng.carbonchain.activities.asset.wallet.ui.adapter.WalletTokenListAdapter;
import com.tancheng.carbonchain.activities.asset.wallet.ui.view.ListViewForScrollView;
import com.tancheng.carbonchain.activities.base.BaseFragment;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.ETHWallet;
import com.tancheng.carbonchain.db.gen.WalletBalnceDaoUtils;
import com.tancheng.carbonchain.db.gen.WalletDaoUtils;
import com.tancheng.carbonchain.utils.Constants;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 热钱包页面
 * Created by gll on 2020/2/8.
 */

public class AssetHotWalletFragment extends BaseFragment {

    //标志位，标志已经初始化完成
    private boolean isPrepared;
    //是否已被加载过一次，第二次就不再去请求数据了
    private boolean mHasLoadedOnce;
    private static final String ARG_NAME = "walletTypeId";

    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.tv_coin_name)
    TextView tvCoinName;
    @BindView(R.id.tv_wallet_address)
    TextView tvWalletAddress;
    @BindView(R.id.tv_wallet_balance)
    TextView tvWalletBalance;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.lv_coins)
    ListViewForScrollView listViewCoins;
    @BindView(R.id.iv_add_wallet_icon)
    ImageView mAddWalletImageView;
    @BindView(R.id.iv_eye)
    ImageView ivEye;
    @BindView(R.id.banner)
    MZBannerView mMZBanner;

    private ETHWallet curWallet;
    private List<TokenType> walletCoins;
    private WalletTokenListAdapter adapter;
    private List<WalletBannerModel> bannerList;
    private int walletTypeId;
    private long currentWalletId = -1;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(MessageWrap message) {
        if (message != null) {
            String eventType = message.eventType;
            if (EventBusMsgType.UPDATE_WALLET.equals(eventType)) {
                //刷新钱包
                initOrrefreshData();
            } else if (EventBusMsgType.HIDE_BALANCE.equals(eventType)) {
                //显示隐藏资产
                if (bannerList != null) {
                    mMZBanner.setPages(bannerList, new MZHolderCreator<BannerViewHolder>() {
                        @Override
                        public BannerViewHolder createViewHolder() {
                            return new BannerViewHolder();
                        }
                    });
                }
            }
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_asset_hot_wallet;
    }

    @Override
    public void attachView() {
        isPrepared = true;
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
        Bundle bundle = getArguments();
        walletTypeId = bundle.getInt(ARG_NAME);
        initOrrefreshData();
    }

    @Override
    public void configViews() {
        swipeRefreshLayout.setColorSchemeColors(activity.getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新逻辑
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        listViewCoins.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TokenDetailActivity.startAct(activity, curWallet.getId(), walletCoins.get(i));
            }
        });

        mMZBanner.addPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                WalletBannerModel selectWallet = bannerList.get(i);
                currentWalletId = selectWallet.getWalletId();
                //该钱包已添加的代币
                WalletDaoUtils walletDaoUtils = WalletDaoUtils.getIntence();
                curWallet = walletDaoUtils.getWalletInfo(currentWalletId);
                List<TokenType> datas = walletDaoUtils.getWalletCoins(selectWallet.getWalletId());
                walletCoins.clear();
                walletCoins.addAll(datas);
                adapter.setWalletAddr(curWallet.getAddress());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    /**
     * 重新渲染界面
     */
    public void initOrrefreshData() {
        //钱包banner
        if (bannerList == null) {
            bannerList = new ArrayList<>();
        } else {
            bannerList.clear();
        }
        List<ETHWallet> walletList = WalletDaoUtils.getIntence().getWalletListByType(walletTypeId);
        if (walletList == null || walletList.size() == 0) {
            WalletBannerModel model = new WalletBannerModel();
            model.setWalletTypeId(walletTypeId);
            model.setWalletName(WalletType.of(walletTypeId).getName());
            model.setWalletAddr("");
            model.setBgResource(WalletType.of(walletTypeId).getBannerBg());
            model.setBalance("0.00");
            bannerList.add(model);
        } else {
            for (ETHWallet wallet : walletList) {
                WalletBannerModel model = new WalletBannerModel();
                model.setWalletTypeId(walletTypeId);
                model.setWalletId(wallet.getId());
                model.setWalletName(wallet.getName());
                model.setWalletAddr(wallet.getAddress());
                model.setBgResource(WalletType.of(walletTypeId).getBannerBg());
                //余额
                BigDecimal allTokenBalance = WalletBalnceDaoUtils.getIntence().getWalletTotalBalance(wallet.getAddress());
                String balance = "0.00";
                if (allTokenBalance != null) {
                    balance = allTokenBalance.setScale(2, RoundingMode.HALF_UP).toPlainString();
                }
                model.setBalance(balance);
                bannerList.add(model);
            }
            currentWalletId = walletList.get(0).getId();
            curWallet = walletList.get(0);
        }
        //显示指示器
        mMZBanner.setIndicatorVisible(bannerList.size() <= 1 ? false : true);
        // 设置数据
        mMZBanner.setPages(bannerList, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });

        //已添加的代币列表
        if (currentWalletId != -1) {
            WalletDaoUtils walletDaoUtils = WalletDaoUtils.getIntence();
            if (adapter == null) {
                walletCoins = walletDaoUtils.getWalletCoins(currentWalletId);
                adapter = new WalletTokenListAdapter(mContext, curWallet.getAddress(),
                        walletCoins, R.layout.list_item_coin);
                listViewCoins.setAdapter(adapter);
            } else {
                List<TokenType> datas = walletDaoUtils.getWalletCoins(currentWalletId);
                walletCoins.clear();
                walletCoins.addAll(datas);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @OnClick({R.id.rl_top, R.id.iv_add_wallet_icon, R.id.tv_wallet_address, R.id.iv_eye, R.id.ll_add_wallet})
    public void setOnClickListeners(View view) {
        switch (view.getId()) {
            case R.id.ll_add_wallet://创建钱包
                WalletCreateActivity.startThisAct(mContext, walletTypeId);
                break;
            case R.id.iv_add_wallet_icon://添加代币
                if (curWallet == null) {
                    ToastUtils.showLong("没有可用钱包，请先添加钱包！");
                    return;
                }
                TokenAddActivity.startAct(activity, curWallet.getWalletType(), curWallet.getCoinIds(), curWallet.getId());
                break;
            case R.id.iv_eye://显示隐藏资产
                boolean aBoolean = SPUtils.getInstance().getBoolean(Constants.ASSET_EYE_STATUS, false);
                SPUtils.getInstance().put(Constants.ASSET_EYE_STATUS, !aBoolean);
                setOpenOrClose(100.66);
                break;
            case R.id.tv_wallet_address://收款码
                if (curWallet == null) {
                    ToastUtils.showShort("没有可用钱包，请先添加钱包！");
                    return;
                }
                GatheringQRActivity.startAct(activity, curWallet.getAddress(), WalletType.of(curWallet.getWalletType()).getLogo());
                break;
            case R.id.rl_top://钱包详情
                if (curWallet == null) {
                    ToastUtils.showShort("没有可用钱包，请先添加钱包！");
                    return;
                }
                WalletDetailActivity.startAct(activity, curWallet.getId(), 1000.00);
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
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    public static AssetHotWalletFragment newInstance(String param1) {
        AssetHotWalletFragment fragment = new AssetHotWalletFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    private void setOpenOrClose(double balance) {
        boolean isClose = SPUtils.getInstance().getBoolean(Constants.ASSET_EYE_STATUS, false);
        if (isClose) {
            tvWalletBalance.setText("***.**");
            ivEye.setImageDrawable(activity.getResources().getDrawable(R.mipmap.ic_eye_close));
        } else {
            tvWalletBalance.setText(balance + "");
            ivEye.setImageDrawable(activity.getResources().getDrawable(R.mipmap.ic_eye_open));
        }
    }
}
