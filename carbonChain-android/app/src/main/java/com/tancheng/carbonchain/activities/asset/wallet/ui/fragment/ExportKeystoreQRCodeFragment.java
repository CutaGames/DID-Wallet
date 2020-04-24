package com.tancheng.carbonchain.activities.asset.wallet.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.king.zxing.util.CodeUtils;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.base.BaseFragment;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * keystore 二维码导出
 */
public class ExportKeystoreQRCodeFragment extends BaseFragment {
    String walletKeystore;
    @BindView(R.id.iv_keystore)
    ImageView ivKeystore;
    @BindView(R.id.btn_show)
    TextView btnShow;
    boolean isShow = false;
    Bitmap qrCode = null;

    @Override
    public void lazyLoad() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_derive_keystore_qrcode;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {
        Bundle arguments = getArguments();
        walletKeystore = arguments.getString("keystore");
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.btn_show})
    public void myClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show:
                showQR();
                break;
        }
    }

    private void showQR() {
        if (isShow) {
            ivKeystore.setImageResource(R.mipmap.ic_keystore_qr_hidden);
            btnShow.setText("显示二维码");
        } else {
            if (qrCode == null) {
                qrCode = CodeUtils.createQRCode(walletKeystore, 400);
            }
            ivKeystore.setImageBitmap(qrCode);
            btnShow.setText("隐藏二维码");
        }
        isShow = !isShow;
    }
}
