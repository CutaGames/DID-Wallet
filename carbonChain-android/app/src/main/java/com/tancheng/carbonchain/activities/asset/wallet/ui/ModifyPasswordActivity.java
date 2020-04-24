package com.tancheng.carbonchain.activities.asset.wallet.ui;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.ETHWallet;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;
import com.tancheng.carbonchain.activities.asset.wallet.service.IwalletService;
import com.tancheng.carbonchain.activities.asset.wallet.service.WalletServiceeFactory;
import com.tancheng.carbonchain.activities.asset.wallet.service.eth.ETHWalletUtil;
import com.tancheng.carbonchain.activities.base.BaseActivity;
import com.tancheng.carbonchain.activities.main.FragmentMainAsset;
import com.tancheng.carbonchain.db.gen.WalletDaoUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by tc_collin on 2020/3/31 16:51
 * Description: 修改钱包密码
 * version: 1.0
 */
public class ModifyPasswordActivity extends BaseActivity {
    private static final String INTENT_WALLET_ID = "walletId";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_old_pwd)
    EditText etOldPwd;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.et_new_pwd_again)
    EditText etNewPwdAgain;
    @BindView(R.id.et_new_pwd_tips)
    EditText etNewPwdTips;

    ETHWallet walletInfo;
    WalletDaoUtils walletDaoUtils;

    public static void startAct(Activity mActivity, long walletID) {
        Intent intent = new Intent(mActivity, ModifyPasswordActivity.class);
        intent.putExtra(INTENT_WALLET_ID, walletID);
        mActivity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_wallet_password;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("修改密码");
        Intent intent = getIntent();
        long walletID = intent.getLongExtra(INTENT_WALLET_ID, -1);

        walletDaoUtils = WalletDaoUtils.getIntence();
        walletInfo = walletDaoUtils.getWalletInfo(walletID);
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.iv_title_left_back, R.id.btn_modify, R.id.tv_import_wallet})
    public void myClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left_back:
                finish();
                break;
            case R.id.btn_modify://修改
                String oldPwd = etOldPwd.getText().toString().trim();
                String newPwd = etNewPwd.getText().toString().trim();
                String newPwdAgain = etNewPwdAgain.getText().toString().trim();
                String pwdTips = etNewPwdTips.getText().toString().trim();

                if (verifyPassword(oldPwd, newPwd, newPwdAgain)) {
                    showDialog("");
                    IwalletService walletService = WalletServiceeFactory.getWalletCreator(WalletType.of(walletInfo.getWalletType()));
                    walletService.modifyPassword(walletInfo.getId(), oldPwd, newPwd);
                    walletInfo.setPassword(EncryptUtils.encryptMD5ToString(newPwd));
                    walletInfo.setPwdTips(StringUtils.isEmpty(pwdTips) == true ? "" : pwdTips);
                    walletDaoUtils.updateWallet(walletInfo);
                    WalletDetailActivity.refreshData = true;
                    ToastUtils.showShort("密码更新成功!");
                    dismissDialog();
                    finish();
                }
                break;
            case R.id.tv_import_wallet://导入钱包
                WalletImportActivity.startThisAct(mContext,walletInfo.getWalletType());
                finish();
                break;
        }
    }

    /**
     * 验证密码
     *
     * @param oldPwd
     * @param newPwd
     * @param newPwdAgain
     * @return
     */
    private boolean verifyPassword(String oldPwd, String newPwd, String newPwdAgain) {
        if (!TextUtils.equals(EncryptUtils.encryptMD5ToString(oldPwd), walletInfo.getPassword())) {
            ToastUtils.showShort("旧密码输入不正确!");
            return false;
        } else if (!TextUtils.equals(newPwd, newPwdAgain)) {
            ToastUtils.showShort("两次输入的新密码不一致!");
            return false;
        }
        return true;
    }

}
