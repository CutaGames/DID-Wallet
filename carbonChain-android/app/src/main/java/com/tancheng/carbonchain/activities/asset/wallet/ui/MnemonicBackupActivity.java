package com.tancheng.carbonchain.activities.asset.wallet.ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.ui.view.MnemonicBackupAlertDialog;
import com.tancheng.carbonchain.activities.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by tc_collin on 2020/3/26 20:39
 * Description: 备份助记词
 * version: 1.0
 */
public class MnemonicBackupActivity extends BaseActivity {

    private static final int VERIFY_MNEMONIC_BACKUP_REQUEST = 1101;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_mnemonic)
    TextView tvMnemonic;
    private String walletMnemonic;

    private long walletId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mnemonic_backup;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("备份助记词");
        MnemonicBackupAlertDialog mnemonicBackupAlertDialog = new MnemonicBackupAlertDialog(this);
        mnemonicBackupAlertDialog.show();
        Intent intent = getIntent();
        walletId = intent.getLongExtra("walletId", -1);
        walletMnemonic = intent.getStringExtra("walletMnemonic");
        tvMnemonic.setText(walletMnemonic);
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.btn_next,R.id.tv_title})
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.tv_title:
                finish();
                break;

            case R.id.btn_next:
        Intent intent = new Intent(this, MnemonicBackupVerifyActivity.class);
        intent.putExtra("walletId", walletId);
        intent.putExtra("walletMnemonic", walletMnemonic);
        startActivityForResult(intent, VERIFY_MNEMONIC_BACKUP_REQUEST);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VERIFY_MNEMONIC_BACKUP_REQUEST) {
            if (data != null) {
                finish();
            }
        }
    }

}
