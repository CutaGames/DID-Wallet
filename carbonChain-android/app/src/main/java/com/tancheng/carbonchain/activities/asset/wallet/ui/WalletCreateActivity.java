package com.tancheng.carbonchain.activities.asset.wallet.ui;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tancheng.carbonchain.MyApplication;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.ETHWallet;
import com.tancheng.carbonchain.activities.asset.wallet.domain.EventBusMsgType;
import com.tancheng.carbonchain.activities.asset.wallet.domain.MessageWrap;
import com.tancheng.carbonchain.activities.asset.wallet.domain.TokenType;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;
import com.tancheng.carbonchain.activities.asset.wallet.service.WalletServiceeFactory;
import com.tancheng.carbonchain.activities.asset.wallet.utils.AESCrypt;
import com.tancheng.carbonchain.activities.base.BaseActivity;
import com.tancheng.carbonchain.db.gen.ETHWalletDao;
import com.tancheng.carbonchain.db.gen.WalletDaoUtils;

import org.greenrobot.eventbus.EventBus;

import java.security.GeneralSecurityException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by collin on 2020/3/26 15:10
 * Description: 创建钱包
 * version: 1.0
 */
public class WalletCreateActivity extends BaseActivity {

    private static final int CREATE_WALLET_RESULT = 2202;
    private static final int LOAD_WALLET_REQUEST = 1101;
    private static final String WALLET_TYPE = "walletType";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_wallet_name)
    EditText etWalletName;
    @BindView(R.id.et_wallet_pwd)
    EditText etWalletPwd;
    @BindView(R.id.et_wallet_pwd_again)
    EditText etWalletPwdAgain;
    @BindView(R.id.et_wallet_pwd_reminder_info)
    EditText etWalletPwdReminderInfo;
    @BindView(R.id.cb_agreement)
    CheckBox cbAgreement;
    @BindView(R.id.btn_create_wallet)
    TextView btnCreateWallet;
    private WalletType walletType;

    /**
     * 打开界面
     *
     * @param walletTypeId
     */
    public static void startThisAct(Context context, int walletTypeId) {
        Intent intent = new Intent(context, WalletCreateActivity.class);
        intent.putExtra(WALLET_TYPE, walletTypeId);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_wallet;
    }

    @Override
    public void initDatas() {
        Intent intent = mContext.getIntent();
        int typeId = intent.getIntExtra(WALLET_TYPE, -1);
        walletType = WalletType.of(typeId);
        tvTitle.setText("创建" + walletType.getName() + "钱包");
    }

    @Override
    public void configViews() {
        cbAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btnCreateWallet.setEnabled(isChecked);
            }
        });
    }

    @OnClick({R.id.iv_title_left_back, R.id.tv_agreement, R.id.btn_create_wallet, R.id.lly_wallet_agreement, R.id.btn_input_wallet})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left_back://返回
                finish();
                break;
            case R.id.tv_agreement://服务条款
                break;
            case R.id.btn_create_wallet:
                showDialog(mContext.getResources().getString(R.string.creating_wallet_tip));
                String walletName = etWalletName.getText().toString().trim();
                String walletPwd = etWalletPwd.getText().toString().trim();
                String confirmPwd = etWalletPwdAgain.getText().toString().trim();
                String pwdReminder = etWalletPwdReminderInfo.getText().toString().trim();
                boolean verifyWalletInfo = verifyInfo(walletName, walletPwd, confirmPwd, pwdReminder);
                WalletDaoUtils walletDaoUtils;
                if (verifyWalletInfo) {
                    ETHWallet wallet = WalletServiceeFactory.getWalletCreator(walletType).createNewWallet(walletName, walletPwd);
                    wallet.setBackup(false);
                    wallet.setCurrent(true);
                    wallet.setName(walletName);
                    wallet.setPwdTips(pwdReminder);
                    wallet.setPassword(EncryptUtils.encryptMD5ToString(walletPwd));
                    wallet.setWalletType(walletType.getWalletType());
                    wallet.setCoinIds(TokenType.getDefaultTokenByWalletType(walletType.getWalletType()).getId() + "");
                    walletDaoUtils = WalletDaoUtils.getIntence();
                    boolean insert = walletDaoUtils.insert(wallet);
                    if (insert) {
                        walletDaoUtils.setCurrentWallet(wallet.getId());
                        //发布更新界面事件
                        MessageWrap<String> messageWrap = new MessageWrap<String>(EventBusMsgType.UPDATE_WALLET, "更新UI");
                        EventBus.getDefault().post(messageWrap);
                        jumpToWalletBackUp(wallet);
                    } else {
                        ToastUtils.showShort("钱包创建失败!");
                    }
                }
                dismissDialog();
                break;
            case R.id.lly_wallet_agreement:
                if (cbAgreement.isChecked()) {
                    cbAgreement.setChecked(false);
                } else {
                    cbAgreement.setChecked(true);
                }
                break;
            case R.id.btn_input_wallet://导入钱包
                WalletImportActivity.startThisAct(mContext, walletType.getWalletType());
                finish();
                break;
        }
    }

    private boolean verifyInfo(String walletName, String walletPwd, String confirmPwd, String pwdReminder) {
        ETHWalletDao ethWalletDao = MyApplication.getmDaoSession().getETHWalletDao();
        long count = ethWalletDao.queryBuilder().where(ETHWalletDao.Properties.Name.eq(walletName)).count();
        if (count > 0) {
            ToastUtils.showShort(R.string.create_wallet_name_repeat_tips);
            // 同时不可重复
            return false;
        } else if (TextUtils.isEmpty(walletName)) {
            ToastUtils.showShort(R.string.create_wallet_name_input_tips);
            // 同时不可重复
            return false;
        } else if (TextUtils.isEmpty(walletPwd)) {
            ToastUtils.showShort(R.string.create_wallet_pwd_input_tips);
            return false;
        } else if (walletPwd.length() < 6) {
            ToastUtils.showShort("密码长度需在6-16位之间");
            return false;
        } else if (TextUtils.isEmpty(confirmPwd) || !TextUtils.equals(confirmPwd, walletPwd)) {
            ToastUtils.showShort(R.string.create_wallet_pwd_confirm_input_tips);
            return false;
        }
        return true;
    }

    public void jumpToWalletBackUp(ETHWallet wallet) {
        ToastUtils.showShort("创建钱包成功");
        String walletPwd = etWalletPwd.getText().toString().trim();
        setResult(CREATE_WALLET_RESULT, new Intent());
        Intent intent = new Intent(this, WalletBackupActivity.class);
        intent.putExtra("walletId", wallet.getId());
        intent.putExtra("walletPwd", walletPwd);
        intent.putExtra("walletAddress", wallet.getAddress());
        intent.putExtra("walletName", wallet.getName());
        intent.putExtra("walletMnemonic", AESCrypt.decrypt(wallet.getMnemonic(),walletPwd));
        intent.putExtra("first_account", true);
        startActivity(intent);
        finish();
    }

}
