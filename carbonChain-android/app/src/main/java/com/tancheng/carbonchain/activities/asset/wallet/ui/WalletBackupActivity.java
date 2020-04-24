package com.tancheng.carbonchain.activities.asset.wallet.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.base.BaseActivity;
import com.tancheng.carbonchain.activities.main.ActivityMain;
import com.tancheng.carbonchain.activities.asset.wallet.ui.view.InputPwdDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by collin on 2020/3/26 16:06
 * Description: 钱包备份
 * version: 1.0
 */
public class WalletBackupActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_backup)
    TextView btnBackup;
    @BindView(R.id.btn_backup_help)
    TextView btnBackupHelp;
    @BindView(R.id.tv_skip)
    TextView btnSkip;

    private InputPwdDialog inputPwdDialog;
    private String walletPwd;
    private String walletAddress;
    private String walletName;
    private String walletMnemonic;
    private long walletId;
    private boolean firstAccount;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_backup;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("备份钱包");
        Intent intent = getIntent();
        walletId = intent.getLongExtra("walletId", -1);
        walletPwd = intent.getStringExtra("walletPwd");
        walletAddress = intent.getStringExtra("walletAddress");
        walletName = intent.getStringExtra("walletName");
        walletMnemonic = intent.getStringExtra("walletMnemonic");
        firstAccount = getIntent().getBooleanExtra("first_account", false);
        LogUtils.d("WalletBackupActivity", "walletMnemonic:" + walletMnemonic);
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.iv_title_left_back,R.id.btn_backup, R.id.btn_backup_help, R.id.tv_skip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left_back:
                 finish();
                break;
            case R.id.btn_backup:
                inputPwdDialog = new InputPwdDialog(this);
                inputPwdDialog.setOnInputDialogButtonClickListener(new InputPwdDialog.OnInputDialogButtonClickListener() {
                    @Override
                    public void onCancel() {
                        inputPwdDialog.dismiss();
                    }

                    @Override
                    public void onConfirm(String pwd) {
                        if (TextUtils.isEmpty(pwd)) {
                            ToastUtils.showShort("请输入密码");
                            return;
                        }

                        if (TextUtils.equals(EncryptUtils.encryptMD5ToString(pwd), walletPwd)) {
                            if (firstAccount) {
                                Intent intent = new Intent(WalletBackupActivity.this, ActivityMain.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }

                            Intent intent = new Intent(WalletBackupActivity.this, MnemonicBackupActivity.class);
                            intent.putExtra("walletId", walletId);
                            intent.putExtra("walletMnemonic", walletMnemonic);
                            startActivity(intent);
                            finish();
                        }else{
                            ToastUtils.showShort("密码输入不正确！");
                        }
                    }
                });
                inputPwdDialog.show();
                break;
            case R.id.tv_skip://跳过
                if (firstAccount) {
                    Intent intent = new Intent(WalletBackupActivity.this, ActivityMain.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

                finish();
                break;
            case R.id.btn_backup_help://指导
                break;
        }
    }


}
