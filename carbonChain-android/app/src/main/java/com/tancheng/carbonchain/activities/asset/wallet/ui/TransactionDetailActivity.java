package com.tancheng.carbonchain.activities.asset.wallet.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;
import com.tancheng.carbonchain.activities.asset.wallet.service.eth.Web3jUtil;
import com.tancheng.carbonchain.activities.base.BaseActivity;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by tc_collin on 2020/4/9 17:01
 * Description: 交易详情
 * version: 1.0
 */
public class TransactionDetailActivity extends BaseActivity {

    @BindView(R.id.btn_more_detail)
    TextView btnMoreDetail;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.iv_status)
    ImageView ivStatus;
    @BindView(R.id.tv_status_name)
    TextView tvStatusName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.tv_from)
    TextView tvFrom;
    @BindView(R.id.tv_to)
    TextView tvTo;
    @BindView(R.id.tv_gas_fee)
    TextView tvGasFee;
    @BindView(R.id.tv_txhash)
    TextView tvTxhash;
    @BindView(R.id.tv_block_number)
    TextView tvBlockNumber;
    @BindView(R.id.cv_cardview)
    CardView cvCardview;

    private String from;
    private String to;
    private String txHash;
    private int walletType;

    @Override
    public int getLayoutId() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true, 1f)
                .init();

        return R.layout.activity_transaction_detail;
    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        walletType = intent.getIntExtra("walletType", 0);
        int status = intent.getIntExtra("status", 0);
        double amount = intent.getDoubleExtra("amount", 0);
        String time = intent.getStringExtra("time");
        from = intent.getStringExtra("from");
        to = intent.getStringExtra("to");
        txHash = intent.getStringExtra("txHash");
        String blockNumber = intent.getStringExtra("blockNumber");
        long gasFee = intent.getLongExtra("gasFee", -1);
        int direct = intent.getIntExtra("direct", -1);

        if (status == 0) {//padding
            tvStatusName.setText("打包中");
            ivStatus.setImageResource(R.mipmap.ic_tx_waiting);
        } else if (status == 1) {//成功
            if (direct == 0) {//转出
                tvStatusName.setText("转出成功");
            } else {//转入
                tvStatusName.setText("转入成功");
            }
            ivStatus.setImageResource(R.mipmap.ic_tx_success);
        } else {//失败
            if (direct == 0) {//转出
                tvStatusName.setText("转出失败");
            } else {//转入
                tvStatusName.setText("转入失败");
            }
            ivStatus.setImageResource(R.mipmap.ic_tx_fail);
        }

        tvAmount.setText(BigDecimal.valueOf(amount).toPlainString());
        tvFrom.setText(from);
        tvTo.setText(to);
        tvTime.setText(StringUtils.isEmpty(time) ? "" : time);
        tvBlockNumber.setText(StringUtils.isEmpty(blockNumber) ? "" : blockNumber);
        tvTxhash.setText(txHash);
        tvGasFee.setText(gasFee > 0 ? gasFee + "" : "");
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.tv_from, R.id.tv_to, R.id.tv_txhash, R.id.btn_more_detail, R.id.iv_back, R.id.iv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_from:
                copyCotent("   付款地址已复制   ", from);
                break;
            case R.id.tv_to:
                copyCotent("   收款地址已复制   ", to);
                break;
            case R.id.tv_txhash:
                copyCotent("   交易号已复制   ", txHash);
                break;
            case R.id.btn_more_detail:
                jumpToWebView();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                ToastUtils.showShort("分享...");
                break;
        }
    }

    private void copyCotent(String tips, String content) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        if (cm != null) {
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", content);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
        }
        ToastUtils.showShort(tips);
    }

    /**
     * 跳转到webView
     */
    private void jumpToWebView() {
        String webUrl = "";
        WalletType type = WalletType.of(walletType);
        switch (type) {
            case BTC:
                break;
            case ETH:
                webUrl = Web3jUtil.getTxURL() + txHash;
                break;
        }
        WebViewActivity.startAct(mContext, webUrl, "交易详情");
    }
}
