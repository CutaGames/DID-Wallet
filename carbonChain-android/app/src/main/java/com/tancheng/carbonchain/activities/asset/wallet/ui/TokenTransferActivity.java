package com.tancheng.carbonchain.activities.asset.wallet.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.ETHWallet;
import com.tancheng.carbonchain.activities.asset.wallet.domain.TokenType;
import com.tancheng.carbonchain.activities.asset.wallet.domain.TransferData;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;
import com.tancheng.carbonchain.activities.asset.wallet.service.IchainService;
import com.tancheng.carbonchain.activities.asset.wallet.service.IwalletService;
import com.tancheng.carbonchain.activities.asset.wallet.service.WalletServiceeFactory;
import com.tancheng.carbonchain.activities.asset.wallet.service.btc.BTCClintUtil;
import com.tancheng.carbonchain.activities.asset.wallet.service.eth.ETHChainServiceImpl;
import com.tancheng.carbonchain.activities.asset.wallet.service.eth.Web3jUtil;
import com.tancheng.carbonchain.activities.asset.wallet.ui.fragment.TransactionRexordFragment;
import com.tancheng.carbonchain.activities.asset.wallet.ui.view.ConfirmTransactionView;
import com.tancheng.carbonchain.activities.asset.wallet.ui.view.InputPwdView;
import com.tancheng.carbonchain.activities.base.BaseActivity;
import com.tancheng.carbonchain.activities.bindsevice.CustomCaptureActivity;
import com.tancheng.carbonchain.db.gen.WalletDaoUtils;

import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Context;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.utils.BtcFormat;
import org.web3j.abi.datatypes.Address;
import org.web3j.protocol.Web3j;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;

import static com.tancheng.carbonchain.db.gen.WalletDaoUtils.getIntence;

/**
 * created by collin on 2020/4/1 11:48
 * Description: 转账页面
 * version: 1.0
 */
public class TokenTransferActivity extends BaseActivity {

    private final int REQUEST_CODE_SCANNER = 201;//扫描二维码
    private final int REQUEST_CODE_ADDRESS_BOOK = 202;//选择联系人
    private static final String INTENT_WALLET_ID = "walletId";
    private static final String INTENT_BALANCE = "balance";
    private static final String INTENT_TOKEN = "token";

    @BindView(R.id.iv_title_left_back)
    ImageView ivTitleLeftBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_title_right)
    ImageView ivTitleRight;
    @BindView(R.id.iv_token_logo)
    ImageView ivTokenLogo;
    @BindView(R.id.tv_token_value)
    TextView tvTokenValue;
    @BindView(R.id.et_transfer_address)
    EditText etTransferAddress;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.lly_contacts)
    LinearLayout llyContacts;
    @BindView(R.id.send_amount)
    EditText sendAmount;
    @BindView(R.id.tv_gas_cost)
    TextView tvGasCost;
    @BindView(R.id.seekbar)
    SeekBar seekbar;
    @BindView(R.id.gas_price)
    TextView tvGasPrice;
    @BindView(R.id.lly_gas)
    LinearLayout llyGas;
    @BindView(R.id.custom_gas_price)
    EditText customGasPrice;
    @BindView(R.id.custom_gas_limit)
    EditText customGasLimit;
    @BindView(R.id.et_hex_data)
    EditText etHexData;
    @BindView(R.id.lly_advance_param)
    LinearLayout llyEthAdvanceParam;
    @BindView(R.id.custom_btc_gas_price)
    EditText customBtcGasPrice;
    @BindView(R.id.lly_btc_advance_param)
    LinearLayout llyBtcAdvanceParam;

    @BindView(R.id.advanced_switch)
    Switch advancedSwitch;
    @BindView(R.id.btn_next)
    TextView btnNext;

    private String netCost;//网络手续费
    private BigInteger gasPrice = new BigInteger("10");
    private BigInteger gasLimit = new BigInteger("144000");
    private static double miner_min = 5;
    private static double miner_max = 55;

    private WalletDaoUtils walletDaoUtils;
    private ETHWallet walletInfo;
    private TokenType tokenType;
    private WalletType walletType;
    private String balance;
    private Dialog dialog;

    public static void startAct(Activity mActivitty, long walletId, TokenType tokenType, String balance) {
        Intent intent = new Intent(mActivitty, TokenTransferActivity.class);
        intent.putExtra(INTENT_WALLET_ID, walletId);
        intent.putExtra(INTENT_TOKEN, tokenType);
        intent.putExtra(INTENT_BALANCE, balance);
        mActivitty.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_transfer_token;
    }

    @Override
    public void initDatas() {
        ivTitleRight.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        long walletId = intent.getLongExtra(INTENT_WALLET_ID, -1);
        tokenType = (TokenType) intent.getSerializableExtra(INTENT_TOKEN);
        walletType = WalletType.of(tokenType.getWalletType());
        balance = intent.getStringExtra(INTENT_BALANCE);
        walletDaoUtils = getIntence();
        walletInfo = walletDaoUtils.getWalletInfo(walletId);

        switch (walletType) {
            case ETH:

                break;
            case BTC:
                miner_min = 5;
                miner_max = 25;
                gasLimit = new BigInteger("78");//预估长度
                String btcValue = BTCClintUtil.sat2btc((gasPrice.multiply(gasLimit).longValue()));
                tvGasCost.setText(btcValue + " btc");
                break;
        }

        if (walletInfo == null) {
            ToastUtils.showShort("未查到钱包信息");
            return;
        }
    }

    @Override
    public void configViews() {
        tvTitle.setText(tokenType.getSymbol() + "转账");
        ivTokenLogo.setImageResource(tokenType.getLogoUrl());
        if (!StringUtils.isEmpty(balance)){
            tvTokenValue.setText(new BigDecimal(balance).setScale(6,RoundingMode.HALF_UP).toPlainString());
        }

        advancedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switch (walletType) {
                        case ETH:
                            llyEthAdvanceParam.setVisibility(View.VISIBLE);
                            llyGas.setVisibility(View.GONE);
                            customGasPrice.setText(Convert.fromWei(new BigDecimal(gasPrice), Convert.Unit.GWEI).toString());
                            customGasLimit.setText(gasLimit.toString());
                            break;
                        case BTC:
                            llyBtcAdvanceParam.setVisibility(View.VISIBLE);
                            llyGas.setVisibility(View.GONE);
                            customBtcGasPrice.setText(gasPrice.toString());
                            break;
                    }
                } else {
                    switch (walletType) {
                        case ETH:
                            llyEthAdvanceParam.setVisibility(View.GONE);
                            llyGas.setVisibility(View.VISIBLE);
                            break;
                        case BTC:
                            llyBtcAdvanceParam.setVisibility(View.GONE);
                            llyGas.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            }
        });

        final DecimalFormat gasformater = new DecimalFormat();
        //保留几位小数
        gasformater.setMaximumFractionDigits(2);
        //模式  四舍五入
        gasformater.setRoundingMode(RoundingMode.CEILING);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double p = progress / 100f;
                double d = (miner_max - miner_min) * p + miner_min;
                switch (walletType) {
                    case ETH:
                        gasPrice = Convert.toWei(BigDecimal.valueOf(d), Convert.Unit.GWEI).toBigInteger();
                        tvGasPrice.setText(gasformater.format(d) + " " + Convert.Unit.GWEI);
                        updateNetworkFee();
                        break;
                    case BTC:
                        gasPrice = BigDecimal.valueOf(d).toBigInteger();
                        tvGasPrice.setText(gasPrice + " sat");
                        String btcValue = BTCClintUtil.sat2btc((gasPrice.multiply(gasLimit).longValue()));
                        tvGasCost.setText(btcValue + " btc");
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbar.setProgress(10);

        customGasPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().isEmpty()) {
                    return;
                }
                gasPrice = Convert.toWei(new BigDecimal(s.toString()), Convert.Unit.GWEI).toBigInteger();
                try {
                    BigInteger multiply = gasPrice.multiply(gasLimit);
                    netCost = Convert.fromWei(multiply + "", Convert.Unit.ETHER) + "";
                    tvGasCost.setText(String.valueOf(netCost));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        customGasLimit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                gasLimit = new BigInteger(s.toString());
                updateNetworkFee();
            }
        });

        customBtcGasPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().isEmpty()) {
                    return;
                }
                gasPrice = new BigDecimal(s.toString()).toBigInteger();
                try {
                    String btcValue = BTCClintUtil.sat2btc(gasPrice.multiply(gasLimit).longValue());
                    tvGasCost.setText(btcValue + "btc");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick({R.id.iv_title_left_back, R.id.lly_contacts, R.id.seekbar, R.id.advanced_switch, R.id.btn_next, R.id.iv_title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left_back:
                finish();
                break;
            case R.id.iv_title_right:
                Intent intent = new Intent(mContext, CustomCaptureActivity.class);
                intent.putExtra(CustomCaptureActivity.INTENT_SCANNER_TYPE, CustomCaptureActivity.WALLET_SCANNER);
                startActivityForResult(intent, REQUEST_CODE_SCANNER);
                break;
            case R.id.lly_contacts:
                Intent bookIntent = new Intent(mContext, AddressBookActivity.class);
                startActivityForResult(bookIntent, REQUEST_CODE_ADDRESS_BOOK);
                break;
            case R.id.btn_next://下一步
                String toAddr = etTransferAddress.getText().toString().trim();
                String amount = sendAmount.getText().toString().trim();

                if (verifyInfo(toAddr, amount)) {
                    ConfirmTransactionView confirmView = new ConfirmTransactionView(this, new ConfirmClicker());
                    confirmView.fillInfo(walletInfo.getAddress(), toAddr, " - " + amount + " " + tokenType.getSymbol(), netCost, gasPrice, gasLimit);

                    dialog = new BottomSheetDialog(this, R.style.BottomSheetDialog);
                    dialog.setContentView(confirmView);
                    dialog.setCancelable(true);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.show();
                }
                break;
        }
    }

    private void updateNetworkFee() {
        try {
            BigInteger multiply = gasPrice.multiply(gasLimit);
            BigDecimal bigDecimal = Convert.fromWei(multiply.toString(), Convert.Unit.ETHER);
            netCost = bigDecimal.setScale(6,RoundingMode.HALF_UP).toPlainString() + " Ether";
            tvGasCost.setText(netCost);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 转账信息验证
     *
     * @param address
     * @param amount
     * @return
     */
    private boolean verifyInfo(String address, String amount) {
        IwalletService walletCreator = WalletServiceeFactory.getWalletCreator(walletType);
        boolean formatRight = walletCreator.verifyAddress(address);
        if (!formatRight) {
            ToastUtils.showShort("地址格式不正确!");
            return false;
        }

        try {
            BigDecimal bigDecimal = new BigDecimal(amount);
            return bigDecimal != null;
        } catch (Exception e) {
            ToastUtils.showShort("转账金额填写不正确!");
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_SCANNER) {
            if (data != null) {
                String stringExtra = data.getStringExtra(CustomCaptureActivity.RESULT_DATA);
                ToastUtils.showShort("扫描完成【" + stringExtra + "】");
            }
        } else if (requestCode == REQUEST_CODE_ADDRESS_BOOK) {
            if (data != null) {
                String address = data.getStringExtra("address");
                if (!StringUtils.isEmpty(address)) {
                    etTransferAddress.setText(address);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    class ConfirmClicker implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            dialog.hide();
            String fromAddress = walletInfo.getAddress();
            String toAddress = etTransferAddress.getText().toString().trim();
            String remark = etRemark.getText().toString().trim();
            InputPwdView pwdView = new InputPwdView(mContext, pwd -> {
                boolean isRight = EncryptUtils.encryptMD5ToString(pwd).equals(walletInfo.getPassword());
                if (isRight) {
                    ETHChainServiceImpl ethChainService = new ETHChainServiceImpl();
                    TransferData transferData = new TransferData();
                    transferData.setCoinType(WalletType.ETH.getWalletType() + "");
                    transferData.setFrom(fromAddress);
                    transferData.setTo(toAddress);
                    transferData.setGas(gasLimit + "");
                    transferData.setGasPrice(gasPrice + "");
                    transferData.setRemark(remark);
                    String amount = sendAmount.getText().toString().trim();
                    transferData.setValue(new BigDecimal(amount));
                    transferData.setTokenInfo(tokenType);
                    transferData.setPassword(pwd);
                    showDialog("交易发送中");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int walletTypeId = walletInfo.getWalletType();
                            WalletType walletType = WalletType.of(walletTypeId);
                            IchainService chainService = WalletServiceeFactory.getChainService(walletType);
                            boolean b = chainService.transferToken(transferData);
                            ((Activity) mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (b) {
                                        ToastUtils.showShort("发送成功，等待打包！");
                                        TransactionRexordFragment.refresh = true;
                                        TokenDetailActivity.refresh = true;
                                        finish();
                                    } else {
                                        ToastUtils.showShort("转账失败，余额不足！");
                                    }
                                    dismissDialog();
                                }
                            });
                        }
                    }).start();
                    dialog.dismiss();
                } else {
                    ToastUtils.showShort("密码不正确！");
                }
            });
            dialog = new BottomSheetDialog(mContext);
            dialog.setContentView(pwdView);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
        }
    }
}
