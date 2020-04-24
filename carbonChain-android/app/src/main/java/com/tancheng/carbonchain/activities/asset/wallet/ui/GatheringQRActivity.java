package com.tancheng.carbonchain.activities.asset.wallet.ui;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.zxing.qrcode.encoder.QRCode;
import com.king.zxing.util.CodeUtils;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.domain.TokenType;
import com.tancheng.carbonchain.activities.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by tc_collin on 2020/3/30 15:33
 * Description: 收款码
 * version: 1.0
 */
public class GatheringQRActivity extends BaseActivity {

    public static final String INTENT_WALLET_ADDRESS = "walletAddress";
    public static final String INTENT_TOKEN_TYPE = "tokenType";
    @BindView(R.id.cv_cardview)
    CardView cv_cardview;
    @BindView(R.id.iv_title_left_back)
    ImageView ivTitleLeftBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.iv_wallet_icon)
    ImageView ivWalletIcon;
    @BindView(R.id.iv_QRcode)
    ImageView ivQRcode;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.iv_copy)
    ImageView ivCopy;

    String walletAddrress;
    int tokenLogo;

    public static void startAct(Activity mActivitty, String walletAddr, int logo) {
        Intent intent = new Intent(mActivitty, GatheringQRActivity.class);
        intent.putExtra(INTENT_WALLET_ADDRESS, walletAddr);
        intent.putExtra(INTENT_TOKEN_TYPE, logo);
        mActivitty.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_gathering_qr;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("收款码");
        Intent intent = getIntent();
        walletAddrress = intent.getStringExtra(INTENT_WALLET_ADDRESS);
        tokenLogo = intent.getIntExtra(INTENT_TOKEN_TYPE,R.mipmap.coin_icon_btc);
    }

    @Override
    public void configViews() {
        tvAddress.setText(walletAddrress);
        ivWalletIcon.setImageResource(tokenLogo);
        Bitmap qrCode = CodeUtils.createQRCode(walletAddrress, 400);
        ivQRcode.setImageBitmap(qrCode);
    }

    @OnClick({R.id.iv_title_left_back, R.id.iv_share, R.id.iv_copy})
    public void myClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left_back:
                finish();
                break;

            case R.id.iv_share:
                Bitmap bitmap = ImageUtils.view2Bitmap(cv_cardview);
                ImageUtils.save(bitmap, SDCardUtils.getSDCardPathByEnvironment(), Bitmap.CompressFormat.PNG);
                ToastUtils.showShort("收款码已保存" + SDCardUtils.getSDCardPathByEnvironment());
                break;

            case R.id.iv_copy:
                copyWalletAddress();
                break;
        }
    }

    private void copyWalletAddress() {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        if (cm != null) {
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", walletAddrress);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
        }
        ToastUtils.showShort("   钱包地址已复制   ");
    }

}
