package com.tancheng.carbonchain.activities.bindsevice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.base.BaseActivity;
import com.tancheng.carbonchain.utils.UserPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 扫描成功
 */
public class ScanSuccessActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivSuccess)
    ImageView ivSuccess;
    @BindView(R.id.tvTipOne)
    TextView tvTipOne;
    @BindView(R.id.tvTipTwo)
    TextView tvTipTwo;
    @BindView(R.id.tvCreateDiD)
    TextView tvCreateDiD;

    private boolean isSuccess = false;
    private UserPreference preference;

    @Override
    public int getLayoutId() {
        return R.layout.activity_scal_success;
    }

    @Override
    public void initDatas() {
        preference = UserPreference.getUserPreference(this);
        if (!isSuccess) {
            tvTipTwo.setVisibility(View.VISIBLE);
            ivSuccess.setBackground(getResources().getDrawable(R.mipmap.succeed_account));
        } else {
            tvTipTwo.setVisibility(View.GONE);
            tvTipOne.setText(getResources().getString(R.string.create_fail));
            tvTipOne.setTextColor(getResources().getColor(R.color.color_FF4A51));
            ivSuccess.setBackground(getResources().getDrawable(R.mipmap.fail_account));
        }
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.ivBack, R.id.tvCreateDiD})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvCreateDiD:
                if (!isSuccess) {
                    tvCreateDiD.setText(getResources().getString(R.string.create_did));
                    showToast("成功了");
                    startActivity(new Intent(ScanSuccessActivity.this, DownloadIDCardActivity.class));
                } else {
                    tvCreateDiD.setText(getResources().getString(R.string.rebind));
                    showToast("失败了");
                }
                break;
        }

    }


}
