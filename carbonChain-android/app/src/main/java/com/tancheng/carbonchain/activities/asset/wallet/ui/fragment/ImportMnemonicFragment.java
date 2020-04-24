package com.tancheng.carbonchain.activities.asset.wallet.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.ETHWallet;
import com.tancheng.carbonchain.activities.asset.wallet.domain.TokenType;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;
import com.tancheng.carbonchain.activities.asset.wallet.service.IwalletService;
import com.tancheng.carbonchain.activities.asset.wallet.service.LoadBalanceRunable;
import com.tancheng.carbonchain.activities.asset.wallet.service.WalletServiceeFactory;
import com.tancheng.carbonchain.activities.asset.wallet.ui.view.LoadWalletSelectStandardPopupWindow;
import com.tancheng.carbonchain.activities.asset.wallet.service.eth.ETHWalletUtil;
import com.tancheng.carbonchain.activities.base.BaseFragment;
import com.tancheng.carbonchain.db.gen.WalletDaoUtils;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by collin on 2020/4/3
 * Description: 导入钱包 - 助记词
 * version: 1.0
 */
public class ImportMnemonicFragment extends BaseFragment {

    private static  final String WALLET_TYPE_ID = "walletTypeId";
    @BindView(R.id.et_mnemonic)
    EditText etMnemonic;
    @BindView(R.id.et_standard)
    EditText etStandard;
    @BindView(R.id.lly_standard_menu)
    LinearLayout llyStandardMenu;
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

    private LoadWalletSelectStandardPopupWindow popupWindow;
    private String walletPath = WalletType.ETH.getPath();
    private WalletType walletType;

    public static ImportMnemonicFragment getInstance(int args){
        ImportMnemonicFragment fragment = new ImportMnemonicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(WALLET_TYPE_ID,args);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_load_wallet_by_mnemonic;
    }

    @Override
    public void attachView() {
        int walletTypeId = getArguments().getInt(WALLET_TYPE_ID);
        walletType = WalletType.of(walletTypeId);
        walletPath =  walletType.getPath();
    }

    @Override
    public void initDatas() {
        cbAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    String mnemonic = etMnemonic.getText().toString().trim();
                    String walletPwd = etWalletPwd.getText().toString().trim();
                    String confirmPwd = etWalletPwdAgain.getText().toString().trim();
                    String pwdReminder = etWalletPwdReminderInfo.getText().toString().trim();
                    boolean verifyWalletInfo = checkInfo(mnemonic, walletPwd, confirmPwd, pwdReminder);
                    if (verifyWalletInfo) {
                        cbAgreement.setEnabled(true);
                    }
                } else {
                    cbAgreement.setEnabled(false);
                }
            }
        });

        popupWindow = new LoadWalletSelectStandardPopupWindow(getContext());
        popupWindow.setOnPopupItemSelectedListener(new LoadWalletSelectStandardPopupWindow.OnPopupItemSelectedListener() {
            @Override
            public void onSelected(int selection) {
                switch (selection) {
                    case 0:
                        etStandard.setText(R.string.load_wallet_by_mnemonic_standard);
                        walletPath = ETHWalletUtil.ETH_JAXX_TYPE;
                        etStandard.setEnabled(false);
                        break;
                    case 1:
                        etStandard.setText(R.string.load_wallet_by_mnemonic_standard_ledger);
                        walletPath = ETHWalletUtil.ETH_LEDGER_TYPE;
                        etStandard.setEnabled(false);
                        break;
                    case 2:
                        etStandard.setText(R.string.load_wallet_by_mnemonic_standard_custom);
                        walletPath = ETHWalletUtil.ETH_CUSTOM_TYPE;
                        etStandard.setEnabled(true);
                        etStandard.setFocusable(true);
                        etStandard.setFocusableInTouchMode(true);
                        etStandard.requestFocus();
                        break;
                }
            }
        });
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.btn_load_wallet, R.id.btn_help, R.id.lly_standard_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_load_wallet:
                String mnemonic = etMnemonic.getText().toString().trim();
                String walletPwd = etWalletPwd.getText().toString().trim();
                String confirmPwd = etWalletPwdAgain.getText().toString().trim();
                String pwdReminder = etWalletPwdReminderInfo.getText().toString().trim();
                boolean verifyWalletInfo = checkInfo(mnemonic, walletPwd, confirmPwd, pwdReminder);
                if (verifyWalletInfo) {
                    IwalletService walletService = WalletServiceeFactory.getWalletCreator(walletType);

                    ETHWallet wallet = walletService.loadWalletByMnemonic(walletPath
                            , Arrays.asList(mnemonic.split(" ")), walletPwd);
                    if (wallet != null) {
                        wallet.setPwdTips(pwdReminder);
                        wallet.setWalletType(walletType.getWalletType());
                        wallet.setCoinIds(TokenType.getDefaultTokenByWalletType(walletType.getWalletType()).getId() + "");
                        boolean insert = WalletDaoUtils.getIntence().insert(wallet);
                        if (insert) {
                            WalletDaoUtils.getIntence().setCurrentWallet(wallet.getId());
                            //更新余额
                            String address = wallet.getAddress();
                            new Thread(new LoadBalanceRunable(address,walletType)).start();
                            ToastUtils.showShort("钱包导入成功！");
                            activity.finish();
                        } else {
                            ToastUtils.showShort("导入失败,请检查助记词填写格式是否正确!");
                        }
                    } else {
                        ToastUtils.showShort("导入失败,请检查助记词填写格式是否正确!");
                    }
                }
                break;
            case R.id.btn_help:
                break;
            case R.id.lly_standard_menu:
                popupWindow.showAsDropDown(etStandard, 0, ConvertUtils.dp2px(10));
                break;
        }
    }

    private boolean checkInfo(String mnemonic, String walletPwd, String confirmPwd, String pwdReminder) {
        if (TextUtils.isEmpty(mnemonic)) {
            ToastUtils.showShort(R.string.load_wallet_by_mnemonic_input_tip);
            return false;
        }
//        else if (!WalletDaoUtils.isValid(mnemonic)) {
//            ToastUtils.showShort(R.string.load_wallet_by_mnemonic_input_tip);
//            return false;
//        } else if (WalletDaoUtils.checkRepeatByMenmonic(mnemonic)) {
//            ToastUtils.showShort(R.string.load_wallet_already_exist);
//            return false;
//        }
        else if (TextUtils.isEmpty(confirmPwd) || !TextUtils.equals(confirmPwd, walletPwd)) {
            ToastUtils.showShort(R.string.create_wallet_pwd_confirm_input_tips);
            return false;
        }
        return true;
    }
}
