package com.tancheng.carbonchain.activities.asset.wallet.ui.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SPUtils;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.TextWidthColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.domain.EventBusMsgType;
import com.tancheng.carbonchain.activities.asset.wallet.domain.MessageWrap;
import com.tancheng.carbonchain.activities.asset.wallet.domain.TokenType;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletConstants;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;
import com.tancheng.carbonchain.activities.asset.wallet.ui.adapter.LoadWalletPageFragmentAdapter;
import com.tancheng.carbonchain.activities.base.BaseFragment;
import com.tancheng.carbonchain.db.gen.WalletBalnceDaoUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * created by tc_collin on 2020/4/13 18:19
 * Description: 热钱包主页面
 * version: 1.0
 */
public class AssetHotWaletMainFragment extends BaseFragment {

    @BindView(R.id.indicator_view)
    ScrollIndicatorView indicatorView;
    @BindView(R.id.vp_wallet)
    ViewPager vpWallet;
    @BindView(R.id.mPieChart)
    PieChart mPieChart;
    @BindView(R.id.iv_eye)
    ImageView ivEye;

    String balanceCny = "0.00";
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private LoadWalletPageFragmentAdapter pageAdapter;
    private IndicatorViewPager indicatorViewPager;
    private ArrayList<PieEntry> entries;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(MessageWrap message) {
        if (message != null) {
            String eventType = message.eventType;
            if (EventBusMsgType.UPDATE_WALLET.equals(eventType)) {
                //刷新钱包
                setData();
            }
        }
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_asset_hot_wallet_main;
    }

    @Override
    public void attachView() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void initDatas() {
        //钱包类型viewPager
        List<String> titles = new ArrayList<>();
        List<WalletType> walletTypes = WalletType.walletTypeList();
        for (WalletType type : walletTypes) {
            titles.add(type.getName());
            AssetHotWalletFragment fragment = new AssetHotWalletFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("walletTypeId", type.getWalletType());
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }
        String[] tabNams = new String[titles.size()];
        for (int i = 0; i < titles.size(); i++) {
            String name = titles.get(i);
            tabNams[i] = name;
        }
        indicatorView.setSplitAuto(true);
        indicatorView.setOnTransitionListener(new OnTransitionTextListener()
                .setColor(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.black))
                .setSize(14, 14));
        indicatorView.setScrollBar(new TextWidthColorBar(mContext, indicatorView, getResources().getColor(R.color.colorPrimary), ConvertUtils.dp2px(2)));
        indicatorView.setScrollBarSize(50);
        indicatorViewPager = new IndicatorViewPager(indicatorView, vpWallet);
        pageAdapter = new LoadWalletPageFragmentAdapter(mContext, getChildFragmentManager(), fragmentList, tabNams);
        indicatorViewPager.setAdapter(pageAdapter);
        indicatorViewPager.setCurrentItem(0, false);
        vpWallet.setOffscreenPageLimit(4);

        boolean isClose = SPUtils.getInstance().getBoolean(WalletConstants.HIDE_BALANCE, false);
        Drawable eyeResource = isClose ? activity.getResources().getDrawable(R.mipmap.ic_eye_open_gray) : activity.getResources().getDrawable(R.mipmap.ic_eye_close_gray);
        ivEye.setImageDrawable(eyeResource);
        BigDecimal walletAllTokenBalance = WalletBalnceDaoUtils.getIntence().getAllWalletTotalBalance();
        if (walletAllTokenBalance != null) {
            balanceCny = walletAllTokenBalance.setScale(2, RoundingMode.HALF_UP).toPlainString();
        }

        //饼状图数据
        initPipeChart();
    }

    @Override
    public void configViews() {
        ivEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOpenOrClose();
            }
        });
    }

    private void setOpenOrClose() {
        boolean isClose = SPUtils.getInstance().getBoolean(WalletConstants.HIDE_BALANCE, false);
        if (isClose) {
            ivEye.setImageDrawable(activity.getResources().getDrawable(R.mipmap.ic_eye_open_gray));
            mPieChart.setCenterText(getSpannableText(balanceCny));
            mPieChart.invalidate();
        } else {
            ivEye.setImageDrawable(activity.getResources().getDrawable(R.mipmap.ic_eye_close_gray));
            mPieChart.setCenterText(getSpannableText("***.**"));
            mPieChart.invalidate();
        }
        SPUtils.getInstance().put(WalletConstants.HIDE_BALANCE, !isClose);
        MessageWrap<String> message = new MessageWrap<>(EventBusMsgType.HIDE_BALANCE, null);
        EventBus.getDefault().post(message);
    }

    /**
     * https://yq.aliyun.com/articles/454682?spm=a2c4e.11155472.0.0.32a4fdb0oo2pKV
     */
    private void initPipeChart() {
        // 显示百分比
        mPieChart.setUsePercentValues(true);
        mPieChart.setExtraOffsets(1, 1, 1, 1);

        mPieChart.setDrawSliceText(false);
        mPieChart.setDrawEntryLabels(false);
        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        //设置中间文件
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setDrawCenterText(true);
        mPieChart.setCenterText(getSpannableText(balanceCny));
        mPieChart.setCenterTextColor(Color.BLACK);
        mPieChart.setNoDataText("暂无数据");
        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);

        mPieChart.setTransparentCircleColor(Color.BLACK);
        mPieChart.setTransparentCircleAlpha(110);
        // 设置圆孔半径
        mPieChart.setHoleRadius(80f);
        mPieChart.setTransparentCircleRadius(61f);

        mPieChart.setRotationAngle(0);
        // 触摸旋转
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);

        //变化监听
//        mPieChart.setOnChartValueSelectedListener(this);


        // 设置动画
        mPieChart.animateY(800, Easing.EasingOption.EaseInOutQuad);
        // 设置显示的比例
        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(1f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setWordWrapEnabled(true);//比例块换行
        l.setTextSize(12);
        l.setTextColor(getResources().getColor(R.color.black));
        l.setForm(Legend.LegendForm.CIRCLE);

        // 输入标签样式
        mPieChart.setEntryLabelColor(Color.BLACK);
        mPieChart.setEntryLabelTextSize(12f);

        //设置数据
        setData();

        //刷新
        mPieChart.highlightValues(null);
        mPieChart.invalidate();
    }

    private void setData() {
        if (entries == null) {
            entries = new ArrayList<>();
        } else {
            if (entries.size() > 0)
                entries.clear();
        }

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        List<TokenType> tokenList = TokenType.getTokenList();
        BigDecimal totalBalance = WalletBalnceDaoUtils.getIntence().getAllWalletTotalBalance();
        balanceCny =  totalBalance.setScale(2,RoundingMode.HALF_UP).toPlainString();
        mPieChart.setCenterText(getSpannableText(balanceCny));
        for (TokenType tokenType : tokenList) {
            BigDecimal balance = WalletBalnceDaoUtils.getIntence().getBalanceBySymbol(tokenType.getSymbol());
            String percent = "0";
            if (totalBalance != null && totalBalance.compareTo(BigDecimal.ZERO) > 0) {
                if (balance!=null){
                    percent = balance.divide(totalBalance,5,RoundingMode.HALF_UP).multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP).toPlainString();
                }
            }
            String label = tokenType.getSymbol() + "   " + percent + "%";
            PieEntry pieEntry = new PieEntry(balance.floatValue(), label);
            entries.add(pieEntry);
            colors.add(getResources().getColor(tokenType.getThemeColor()));
        }
        //资产为0时默认显示
        if (totalBalance == null || totalBalance.compareTo(BigDecimal.ZERO) == 0) {
//             entries.get(0).setData(0.8f);
            PieEntry pieEntry = new PieEntry(10, "");
            entries.add(pieEntry);
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        // 设置饼图区块之间的距离
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setDrawValues(false);//不显示饼状图种百分比数字
        mPieChart.setData(data);

        mPieChart.highlightValues(null);
        //刷新
        mPieChart.invalidate();
    }

    //绘制中心文字
    private SpannableString getSpannableText(String amount) {
        SpannableString s = new SpannableString("总资产\n¥" + amount);
//        s.setSpan(new RelativeSizeSpan(1.5f), 0, 3, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 3, 0);
//        s.setSpan(new RelativeSizeSpan(.65f), 14, s.length() - 15, 0);
        if (amount.length() > 0) {
            s.setSpan(new StyleSpan(Typeface.BOLD), 5, s.length(), 0);
        }
//        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 17, s.length(), 0);
        return s;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
