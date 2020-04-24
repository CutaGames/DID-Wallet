package com.tancheng.carbonchain.activities.bindsevice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.king.zxing.CaptureActivity;
import com.king.zxing.camera.FrontLightMode;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.ui.AddressBookAddActivity;
import com.tancheng.carbonchain.activities.asset.wallet.ui.TokenTransferActivity;

/**
 * 自定义扫一扫界面
 */
public class CustomCaptureActivity extends CaptureActivity {

    public static final String INTENT_SCANNER_TYPE = "scannerType";
    public static final String WALLET_SCANNER = "walletScanner";
    public static final String ADDRESS_SCANNER = "addressScanner";
    public static final String RESULT_DATA = "resultData";
    private String intentType;

    @Override
    public int getLayoutId() {
        return R.layout.custom_capture_activity;
    }

    @Override
    public void onCreate(Bundle icicle) {

        super.onCreate(icicle);
        Toolbar toolbar = findViewById(R.id.toolbar);
//        StatusBarUtils.immersiveStatusBar(this,toolbar,0.2f);

        Intent intent = getIntent();
        intentType = intent.getStringExtra(INTENT_SCANNER_TYPE);

        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("扫一扫");

        //获取CaptureHelper，里面有扫码相关的配置设置
        getCaptureHelper().playBeep(false)//播放音效
                .vibrate(true)//震动
                .supportVerticalCode(true)//支持扫垂直条码，建议有此需求时才使用。
//                .decodeFormats(DecodeFormatManager.QR_CODE_FORMATS)//设置只识别二维码会提升速度
//                .framingRectRatio(0.9f)//设置识别区域比例，范围建议在0.625 ~ 1.0之间。非全屏识别时才有效
//                .framingRectVerticalOffset(0)//设置识别区域垂直方向偏移量，非全屏识别时才有效
//                .framingRectHorizontalOffset(0)//设置识别区域水平方向偏移量，非全屏识别时才有效
                .frontLightMode(FrontLightMode.AUTO)//设置闪光灯模式
                .tooDarkLux(45f)//设置光线太暗时，自动触发开启闪光灯的照度值
                .brightEnoughLux(100f)//设置光线足够明亮时，自动触发关闭闪光灯的照度值
                .continuousScan(false)//是否连扫
                .supportLuminanceInvert(true);//是否支持识别反色码（黑白反色的码），增加识别率
    }


    /**
     * 扫码结果回调
     *
     * @param result 扫码结果
     * @return
     */
    @Override
    public boolean onResultCallback(String result) {
        if (WALLET_SCANNER.equals(intentType)) {
            Intent intent = new Intent();
            intent.putExtra(RESULT_DATA, result);
            setResult(100, intent);
            finish();
        } else if (ADDRESS_SCANNER.equals(intentType)) {
            Intent intent = new Intent(this, AddressBookAddActivity.class);
            intent.putExtra(RESULT_DATA, result);
            setResult(100, intent);
            finish();
        } else {
            startActivity(new Intent(this, ScanSuccessActivity.class));
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        }
        return super.onResultCallback(result);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivLeft:
                onBackPressed();
                break;
        }
    }
}
