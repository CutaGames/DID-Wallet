package com.tancheng.carbonchain.activities.asset.wallet.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.ETHWallet;
import com.tancheng.carbonchain.activities.asset.wallet.domain.TokenType;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;
import com.tancheng.carbonchain.activities.asset.wallet.service.IwalletService;
import com.tancheng.carbonchain.activities.asset.wallet.service.LoadBalanceRunable;
import com.tancheng.carbonchain.activities.asset.wallet.service.WalletServiceeFactory;
import com.tancheng.carbonchain.activities.base.BaseFragment;
import com.tancheng.carbonchain.db.gen.WalletDaoUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by collin on 2020/4/3
 * Description: 导入钱包-私钥
 * version: 1.0
 */
public class ImportPrivateKeyFragment extends BaseFragment {
    private static final String WALLET_TYPE_ID = "walletTypeId";
    @BindView(R.id.et_private_key)
    EditText etPrivateKey;
    @BindView(R.id.et_wallet_pwd)
    EditText etWalletPwd;
    @BindView(R.id.et_wallet_pwd_again)
    EditText etWalletPwdAgain;
    @BindView(R.id.et_wallet_pwd_reminder_info)
    EditText etWalletPwdReminderInfo;
    @BindView(R.id.cb_agreement)
    CheckBox cbAgreement;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.lly_wallet_agreement)
    LinearLayout llyWalletAgreement;
    @BindView(R.id.btn_load_wallet)
    TextView btnLoadWallet;
    @BindView(R.id.btn_help)
    TextView btnHelp;
    private WalletType walletType;

    public static ImportPrivateKeyFragment getInstance(int args) {
        ImportPrivateKeyFragment fragment = new ImportPrivateKeyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(WALLET_TYPE_ID, args);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_load_wallet_by_private_key;
    }

    @Override
    public void attachView() {
        int walletTypeId = getArguments().getInt(WALLET_TYPE_ID);
        walletType = WalletType.of(walletTypeId);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        cbAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    String privateKey = etPrivateKey.getText().toString().trim();
                    String walletPwd = etWalletPwd.getText().toString().trim();
                    String confirmPwd = etWalletPwdAgain.getText().toString().trim();
                    String pwdReminder = etWalletPwdReminderInfo.getText().toString().trim();
                    boolean verifyWalletInfo = checkInfo(privateKey, walletPwd, confirmPwd, pwdReminder);
                    if (verifyWalletInfo) {
                        btnLoadWallet.setEnabled(true);
                    } else {
                        cbAgreement.setChecked(false);
                    }
                } else {
                    btnLoadWallet.setEnabled(false);
                }
            }
        });
    }

    @OnClick({R.id.btn_load_wallet, R.id.btn_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_load_wallet:
                String privateKey = etPrivateKey.getText().toString().trim();
                String walletPwd = etWalletPwd.getText().toString().trim();
                String confirmPwd = etWalletPwdAgain.getText().toString().trim();
                String pwdReminder = etWalletPwdReminderInfo.getText().toString().trim();
                boolean verifyWalletInfo = checkInfo(privateKey, walletPwd, confirmPwd, pwdReminder);
                if (verifyWalletInfo) {
                    IwalletService walletService = WalletServiceeFactory.getWalletCreator(walletType);
                    ETHWallet wallet = walletService.loadWalletByPrivatekey(privateKey, walletPwd);
                    if (wallet != null) {
                        wallet.setPwdTips(pwdReminder);
                        wallet.setWalletType(walletType.getWalletType());
                        wallet.setCoinIds(TokenType.getDefaultTokenByWalletType(walletType.getWalletType()).getId() + "");
                        boolean insert = WalletDaoUtils.getIntence().insert(wallet);
                        if (insert) {
                            WalletDaoUtils.getIntence().setCurrentWallet(wallet.getId());
                            //更新余额
                            String address = wallet.getAddress();
                            new Thread(new LoadBalanceRunable(address, walletType)).start();
                            ToastUtils.showShort("钱包导入成功!");
                            activity.finish();
                        } else {
                            ToastUtils.showShort("导入失败，请检查私钥填写是否正确!");
                        }
                    } else {
                        ToastUtils.showShort("导入失败，请检查私钥填写是否正确!");
                    }
                }
                break;
            case R.id.btn_help:
                break;
        }
    }

    /**
     * 验证信息
     *
     * @param privateKey
     * @param walletPwd
     * @param confirmPwd
     * @param pwdReminder
     * @return
     */
    private boolean checkInfo(String privateKey, String walletPwd, String confirmPwd, String pwdReminder) {
        if (TextUtils.isEmpty(privateKey)) {
//            || ETHWalletUtils.isTooSimplePrivateKey(walletPwd)
            ToastUtils.showShort(R.string.load_wallet_by_private_key_input_tip);
            return false;
        } else if (TextUtils.isEmpty(walletPwd)) {
            ToastUtils.showShort(R.string.create_wallet_pwd_input_tips);
            return false;
        } else if (TextUtils.isEmpty(confirmPwd) || !TextUtils.equals(confirmPwd, walletPwd)) {
            ToastUtils.showShort(R.string.create_wallet_pwd_confirm_input_tips);
            return false;
        }
        return true;
    }
}
