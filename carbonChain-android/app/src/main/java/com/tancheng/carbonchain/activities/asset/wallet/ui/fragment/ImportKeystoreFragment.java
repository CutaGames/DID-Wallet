package com.tancheng.carbonchain.activities.asset.wallet.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
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
 * created by tc_collin on 2020/4/3
 * Description: 导入钱包 - keystrore
 * version: 1.0
 */
public class ImportKeystoreFragment extends BaseFragment {
    private static  final String WALLET_TYPE_ID = "walletTypeId";
    @BindView(R.id.et_keystore)
    EditText etKeystore;
    @BindView(R.id.et_wallet_pwd)
    EditText etWalletPwd;
    @BindView(R.id.et_pwd_tips)
    EditText etPwdTips;
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

    public static ImportKeystoreFragment getInstance(int args){
        ImportKeystoreFragment fragment = new ImportKeystoreFragment();
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
        return R.layout.fragment_load_wallet_by_official_wallet;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {
        int walletTypeId = getArguments().getInt(WALLET_TYPE_ID);
        walletType = WalletType.of(walletTypeId);
    }

    @Override
    public void configViews() {
        cbAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                btnLoadWallet.setEnabled(b);
            }
        });
    }

    @OnClick({R.id.btn_load_wallet, R.id.btn_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_load_wallet:
                String walletPwd = etWalletPwd.getText().toString().trim();
                String keystore = etKeystore.getText().toString().trim();
                String pwdTips = etPwdTips.getText().toString().trim();
                if (checkInfo(keystore, walletPwd)) {
                    showDialog("钱包导入中");
                    IwalletService walletService = WalletServiceeFactory.getWalletCreator(walletType);
                    ETHWallet wallet = walletService.loadWalletByKeystore(keystore, walletPwd);
                    if (wallet != null) {
                        wallet.setPwdTips(pwdTips);
                        wallet.setWalletType(walletType.getWalletType());
                        wallet.setCoinIds(TokenType.getDefaultTokenByWalletType(walletType.getWalletType()).getId() + "");
                        boolean insert = WalletDaoUtils.getIntence().insert(wallet);
                        if (insert) {
                            WalletDaoUtils.getIntence().setCurrentWallet(wallet.getId());
                            //更新余额
                            String address = wallet.getAddress();
                            new Thread(new LoadBalanceRunable(address,walletType)).start();
                            ToastUtils.showShort("导入成功！");
                            activity.finish();
                        } else {
                            ToastUtils.showShort("导入失败,请检查keystore格式是否正确！");
                        }
                    } else {
                        ToastUtils.showShort("导入失败，请检查keystore格式是否正确！");
                    }
                    hideDialog();
                }
                break;
            case R.id.btn_help:

                break;
        }
    }

    private boolean checkInfo(String keystore, String walletPwd) {
        if (StringUtils.isEmpty(keystore)) {
            ToastUtils.showShort("keystore不合法！");
            return false;
        }
        if (StringUtils.isEmpty(walletPwd)) {
            ToastUtils.showShort("密码不能为空!");
            return false;
        }
        return true;
    }

}
