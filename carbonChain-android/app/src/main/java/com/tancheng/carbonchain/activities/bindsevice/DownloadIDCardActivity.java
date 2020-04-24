package com.tancheng.carbonchain.activities.bindsevice;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.zxing.util.CodeUtils;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.base.BaseActivity;
import com.tancheng.carbonchain.bean.UserInfoBean;
import com.tancheng.carbonchain.db.gen.DaoUtils;
import com.tancheng.carbonchain.utils.CommonUtil;

import butterknife.OnClick;

/**
 * 保存身份id
 */
public class DownloadIDCardActivity extends BaseActivity {
    private TextView tvDID, tvGoOn;
    private ImageView  ivCode;
    private RelativeLayout rlIDCardImage, rlActivity;
    private boolean isSave = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_download_idcard;
    }

    @Override
    public void initDatas() {
        DaoUtils daoUtils = new DaoUtils(this);
        daoUtils.insertUserInfo(new UserInfoBean("123456", "123456"));
        createQRCode("shshhshshshhs");
    }

    @Override
    public void configViews() {
        tvDID = findViewById(R.id.tvDID);
        tvGoOn = findViewById(R.id.tvGoOn);
        ivCode = findViewById(R.id.ivCode);
        rlActivity = findViewById(R.id.rlActivity);
        rlIDCardImage = findViewById(R.id.rlIDCardImage);
        rlIDCardImage.setGravity(Gravity.CENTER);
    }

    @OnClick({R.id.ivBack, R.id.tvGoOn, R.id.ivDown})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack://返回
                finish();
                break;

            case R.id.tvGoOn://继续
                if (isSave) {
                    showToast("请先保存炭承身份ID!");
                } else {
                    //go on
                    startActivity(new Intent(DownloadIDCardActivity.this, SetPINCodeActivity.class));
                }
                break;
            case R.id.ivDown://下载碳承id
                isSave = true;
                Bitmap conversionBitmap = viewConversionBitmap(rlActivity);
                CommonUtil.saveBitmap2file(conversionBitmap, DownloadIDCardActivity.this);
                break;

        }
    }


    /**
     * 生成二维码
     *
     * @param content
     */
    private void createQRCode(String content) {
        new Thread(() -> {
            //生成二维码相关放在子线程里面
//            Bitmap logo = BitmapFactory.decodeResource(getResources(),R.drawable.logo);
            Bitmap bitmap = CodeUtils.createQRCode(content, 600, null);
            runOnUiThread(() -> {
                //显示二维码
                ivCode.setImageBitmap(bitmap);
            });
        }).start();

    }

    /**
     * view转bitmap
     */
    public Bitmap viewConversionBitmap(View v) {
        int w = v.getWidth();
        int h = v.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }


}
