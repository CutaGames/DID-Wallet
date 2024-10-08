package com.tancheng.carbonchain.activities.asset.wallet.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.tancheng.carbonchain.R;

/**
 * 助记词截图警告框
 */
public class MnemonicBackupAlertDialog extends Dialog implements View.OnClickListener {


    public MnemonicBackupAlertDialog(@NonNull Context context) {
        super(context);
    }

    public MnemonicBackupAlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_mnemonic_backup_alert);
        setCanceledOnTouchOutside(false);
        //初始化界面控件的事件
        initEvent();
    }

    private void initEvent() {
        findViewById(R.id.btn_confirm).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:// 确定
                dismiss();
                break;
        }
    }

}
