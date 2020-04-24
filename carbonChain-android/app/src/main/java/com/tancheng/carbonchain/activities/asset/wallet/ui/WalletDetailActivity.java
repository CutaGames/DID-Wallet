package com.tancheng.carbonchain.activities.asset.wallet.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.ETHWallet;
import com.tancheng.carbonchain.activities.asset.wallet.domain.EventBusMsgType;
import com.tancheng.carbonchain.activities.asset.wallet.domain.MessageWrap;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletConstants;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;
import com.tancheng.carbonchain.activities.asset.wallet.service.IwalletService;
import com.tancheng.carbonchain.activities.asset.wallet.service.WalletServiceeFactory;
import com.tancheng.carbonchain.activities.asset.wallet.ui.view.InputPwdDialog;
import com.tancheng.carbonchain.activities.asset.wallet.service.eth.ETHWalletUtil;
import com.tancheng.carbonchain.activities.base.BaseActivity;
import com.tancheng.carbonchain.db.gen.WalletBalnceDaoUtils;
import com.tancheng.carbonchain.db.gen.WalletDaoUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by tc_collin on 2020/3/31 9:58
 * Description: 钱包详情
 * version: 1.0
 */
public class WalletDetailActivity extends BaseActivity {

    private static final String INTENT_WALLET_ID = "walletId";
    private static final String INTENT_WALLETT_BALANCE = "walletBalance";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lly_back)
    LinearLayout llyBack;
    @BindView(R.id.iv_btn)
    TextView ivBtn;
    @BindView(R.id.rl_btn)
    LinearLayout rlBtn;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.civ_wallet)
    ImageView civWallet;
    @BindView(R.id.tv_eth_balance)
    TextView tvEthBalance;
    @BindView(R.id.lly_wallet_property)
    LinearLayout llyWalletProperty;
    @BindView(R.id.tv_wallet_address)
    TextView tvWalletAddress;
    @BindView(R.id.et_wallet_name)
    EditText etWalletName;
    @BindView(R.id.rl_modify_pwd)
    RelativeLayout rlModifyPwd;
    @BindView(R.id.rl_tips_pwd)
    RelativeLayout rltipspwd;
    @BindView(R.id.tv_password_tips)
    TextView tvPasswordTips;
    @BindView(R.id.iv_password)
    ImageView ivPassword;
    @BindView(R.id.rl_derive_private_key)
    RelativeLayout rlDerivePrivateKey;
    @BindView(R.id.rl_derive_keystore)
    RelativeLayout rlDeriveKeystore;
    @BindView(R.id.btn_delete_wallet)
    TextView btnDeleteWallet;
    @BindView(R.id.btn_mnemonic_backup)
    TextView btnMnemonicBackup;

    public static boolean refreshData = false;
    private long walletID;
    private double balance;
    private WalletDaoUtils walletDaoUtils;
    private ETHWallet walletInfo;
    private String pwdTips;
    private PrivateKeyDerivetDialog privateKeyDerivetDialog;
    private InputPwdDialog inputPwdDialog;
    private IwalletService walletService;

    public static void startAct(Activity mActivity, long walletID, double balance) {
        Intent intent = new Intent(mActivity, WalletDetailActivity.class);
        intent.putExtra(INTENT_WALLET_ID, walletID);
        intent.putExtra(INTENT_WALLETT_BALANCE, balance);
        mActivity.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (refreshData) {
            walletInfo = walletDaoUtils.getWalletInfo(walletID);
            refreshData = false;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_detail;
    }

    @Override
    public void initDatas() {
        Intent intent = mContext.getIntent();
        walletID = intent.getLongExtra(INTENT_WALLET_ID, -1);
        balance = intent.getDoubleExtra(INTENT_WALLETT_BALANCE, -1);

        walletDaoUtils = WalletDaoUtils.getIntence();
        walletInfo = walletDaoUtils.getWalletInfo(walletID);
        walletService = WalletServiceeFactory.getWalletCreator(WalletType.of(walletInfo.getWalletType()));
        if (walletInfo != null) {
            tvWalletAddress.setText(walletInfo.getAddress());
            etWalletName.setText(walletInfo.getName());
            WalletType walletTypeEnum = WalletType.of(walletInfo.getWalletType());
            civWallet.setImageResource(walletTypeEnum.getLogo());
            tvEthBalance.setText("≈"+balance );
            pwdTips = StringUtils.isEmpty(walletInfo.getPwdTips()) ? "" : walletInfo.getPwdTips();
            boolean passwordShow = SPUtils.getInstance().getBoolean(WalletConstants.HIDE_PASSWORD_TIPS, false);
            tvPasswordTips.setText(passwordShow ? pwdTips : "***");
            ivPassword.setImageResource(passwordShow ? R.mipmap.ic_eye_close_gray : R.mipmap.ic_eye_open_gray);
            String keystorePath = walletInfo.getKeystorePath();
            if (StringUtils.isEmpty(keystorePath)){
                rlDeriveKeystore.setVisibility(View.GONE);
            }
        }
        inputPwdDialog = new InputPwdDialog(this);
    }

    @Override
    public void configViews() {

    }

    @OnClick({R.id.lly_back, R.id.iv_btn, R.id.rl_modify_pwd, R.id.rl_derive_private_key,
            R.id.rl_derive_keystore, R.id.btn_mnemonic_backup, R.id.btn_delete_wallet,
            R.id.rl_tips_pwd, R.id.iv_password})
    public void myClick(View view) {
        switch (view.getId()) {
            case R.id.lly_back://返回
                finish();
                break;
            case R.id.iv_password://显示隐藏密码提示
                boolean passwordShow = SPUtils.getInstance().getBoolean(WalletConstants.HIDE_PASSWORD_TIPS);
                tvPasswordTips.setText(passwordShow ? "***" : pwdTips);
                ivPassword.setImageResource(passwordShow ? R.mipmap.ic_eye_open_gray : R.mipmap.ic_eye_close_gray);
                SPUtils.getInstance().put(WalletConstants.HIDE_PASSWORD_TIPS, !passwordShow);
                break;
            case R.id.iv_btn://保存
                String walletName = etWalletName.getText().toString();
                if (StringUtils.isEmpty(walletName)) {
                    ToastUtils.showShort("钱包名称不能为空！");
                    return;
                }

                boolean isExist = walletDaoUtils.walletNameIsExist(walletName);
                if (isExist) {
                    ToastUtils.showShort("钱包名称已存在！");
                    return;
                }
                walletInfo.setName(walletName);
                walletDaoUtils.updateWallet(walletInfo);
                ToastUtils.showShort("保存成功！");
                break;
            case R.id.rl_modify_pwd://修改密码
                ModifyPasswordActivity.startAct(mContext, walletID);
                break;
            case R.id.rl_derive_private_key://导出私钥
                inputPwdDialog.show();
                inputPwdDialog.setDeleteAlertVisibility(false);
                inputPwdDialog.setOnInputDialogButtonClickListener(new InputPwdDialog.OnInputDialogButtonClickListener() {
                    @Override
                    public void onCancel() {
                        inputPwdDialog.dismiss();
                    }

                    @Override
                    public void onConfirm(String pwd) {
                        inputPwdDialog.dismiss();
                        if (TextUtils.equals(walletInfo.getPassword(), EncryptUtils.encryptMD5ToString(pwd))) {
                            privateKeyDerivetDialog = new PrivateKeyDerivetDialog(WalletDetailActivity.this);
                            privateKeyDerivetDialog.show();
                            String privateKey = walletService.derivePrivateKey(walletID, pwd);
                            privateKeyDerivetDialog.setPrivateKey(privateKey);
                        } else {
                            ToastUtils.showShort("密码输入错误!");
                        }
                    }
                });
                break;
            case R.id.rl_derive_keystore://导出keystore
                inputPwdDialog.show();
                inputPwdDialog.setDeleteAlertVisibility(false);
                inputPwdDialog.setOnInputDialogButtonClickListener(new InputPwdDialog.OnInputDialogButtonClickListener() {
                    @Override
                    public void onCancel() {
                        inputPwdDialog.dismiss();
                    }

                    @Override
                    public void onConfirm(String pwd) {
                        if (TextUtils.equals(walletInfo.getPassword(), EncryptUtils.encryptMD5ToString(pwd))) {
                            String keystore = walletService.deriveKeystore(walletID, pwd);
                            Intent intent = new Intent(mContext, WalletExportKeystoreActivityStep1.class);
                            intent.putExtra("keystore", keystore);
                            startActivity(intent);
                        } else {
                            ToastUtils.showShort("密码输入错误");
                        }
                        inputPwdDialog.dismiss();
                    }
                });
                break;
            case R.id.btn_mnemonic_backup://备份助记词
                inputPwdDialog.show();
                inputPwdDialog.setDeleteAlertVisibility(false);
                inputPwdDialog.setOnInputDialogButtonClickListener(new InputPwdDialog.OnInputDialogButtonClickListener() {
                    @Override
                    public void onCancel() {
                        inputPwdDialog.dismiss();
                    }

                    @Override
                    public void onConfirm(String pwd) {
                        if (TextUtils.equals(walletInfo.getPassword(), EncryptUtils.encryptMD5ToString(pwd))) {
                            String mnemonic = walletService.deriveMnemonic(walletID, pwd);
                            if (StringUtils.isEmpty(mnemonic)){
                                privateKeyDerivetDialog = new PrivateKeyDerivetDialog(WalletDetailActivity.this);
                                privateKeyDerivetDialog.show();
                                String privateKey = walletService.derivePrivateKey(walletID, pwd);
                                privateKeyDerivetDialog.setPrivateKey(privateKey);
                            }else{
                                Intent intent = new Intent(WalletDetailActivity.this, MnemonicBackupActivity.class);
                                intent.putExtra("walletId", walletID);
                                intent.putExtra("walletMnemonic", mnemonic);
                                startActivity(intent);
                            }
                        } else {
                            ToastUtils.showShort("密码错误");
                        }
                        inputPwdDialog.dismiss();
                    }
                });
                break;
            case R.id.btn_delete_wallet://删除钱包
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("删除提示");
                dialog.setMessage("确定要删除钱包吗，删除前请确认该钱包私钥或助记词已备份方便恢复钱包！");
                dialog.setCancelable(true);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        inputPwdDialog.show();
                        inputPwdDialog.setDeleteAlertVisibility(true);
                        inputPwdDialog.setOnInputDialogButtonClickListener(new InputPwdDialog.OnInputDialogButtonClickListener() {
                            @Override
                            public void onCancel() {
                                inputPwdDialog.dismiss();
                            }

                            @Override
                            public void onConfirm(String pwd) {
                                if (TextUtils.equals(walletInfo.getPassword(), EncryptUtils.encryptMD5ToString(pwd))) {
                                    WalletDaoUtils.getIntence().delByPrimkey(walletID);
                                    //删除钱包余额记录
                                    WalletBalnceDaoUtils.getIntence().deleteByWalletAddr(walletInfo.getAddress());
                                    MessageWrap<String> message = new MessageWrap<>(EventBusMsgType.UPDATE_WALLET,null);
                                    EventBus.getDefault().post(message);
                                    ToastUtils.showShort("钱包删除成功！");
                                    finish();
                                } else {
                                    ToastUtils.showShort("密码错误");
                                }
                                inputPwdDialog.dismiss();
                            }
                        });
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
        }
    }

}
