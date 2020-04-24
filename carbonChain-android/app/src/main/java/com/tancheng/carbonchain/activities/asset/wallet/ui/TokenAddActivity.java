package com.tancheng.carbonchain.activities.asset.wallet.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.domain.EventBusMsgType;
import com.tancheng.carbonchain.activities.asset.wallet.domain.MessageWrap;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.ETHWallet;
import com.tancheng.carbonchain.activities.asset.wallet.domain.TokenType;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletConstants;
import com.tancheng.carbonchain.activities.asset.wallet.ui.adapter.TokenManageAdapter;
import com.tancheng.carbonchain.activities.base.BaseActivity;
import com.tancheng.carbonchain.db.gen.WalletDaoUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by tc_collin on 2020/3/30 10:19
 * Description: 添加代币列表
 * version: 1.0
 */
public class TokenAddActivity extends BaseActivity {
    @BindView(R.id.iv_title_left_back)
    ImageView ivTitleLeftBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_coins)
    ListView lvCoins;

    private static final String INTENT_WALLET_TYPE = "walletType";
    private static final String INTENT_WALLET_TOKENS = "walletTokens";
    private static final String INTENT_WALLET_ID = "walletId";

    public static void startAct(Activity mActivity, int walletType, String walletTokens, long walletId) {
        Intent intent = new Intent(mActivity, TokenAddActivity.class);
        intent.putExtra(INTENT_WALLET_TYPE, walletType);
        intent.putExtra(INTENT_WALLET_TOKENS, walletTokens);
        intent.putExtra(INTENT_WALLET_ID, walletId);
        mActivity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_coin;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("管理币种");
    }

    @Override
    public void configViews() {
        long walletId = getIntent().getLongExtra(INTENT_WALLET_ID, -1);
        WalletDaoUtils walletDaoUtils = WalletDaoUtils.getIntence();
        ETHWallet walletInfo = walletDaoUtils.getWalletInfo(walletId);
        if (walletInfo != null) {
            int walletType = walletInfo.getWalletType();
            String tokenIds = walletInfo.getCoinIds();
            List<TokenType> walletCoins = TokenType.getTokenListByWalletType(walletType);
            TokenManageAdapter adapter = new TokenManageAdapter(mContext, walletCoins, R.layout.list_item_add_token, tokenIds);
            lvCoins.setAdapter(adapter);
            adapter.setSwitchListener(new TokenManageAdapter.SwitchChangeListener() {
                @Override
                public void OnSwitch(TokenType tokenType, boolean checked) {
                    LogUtils.e("SwitchChangeListener: " + checked);
                    String curTokens = walletInfo.getCoinIds();
                    String coinIds = walletInfo.getCoinIds();
                    LogUtils.i("coinIds:" + coinIds);
                    String[] idArray = coinIds.split(WalletConstants.COIIN_SPLIT_CHAR);
                    if (checked) {//添加
                        curTokens = coinIds + WalletConstants.COIIN_SPLIT_CHAR + tokenType.getId();
                    } else {//删除
                        StringBuffer buffer = new StringBuffer();
                        for (String id : idArray) {
                            String selectedId = tokenType.getId() + "";
                            if (!id.equals(selectedId)) {
                                buffer.append(id).append(WalletConstants.COIIN_SPLIT_CHAR);
                            }
                        }
                        if (buffer.toString().endsWith(WalletConstants.COIIN_SPLIT_CHAR)) {
                            curTokens = buffer.substring(0, buffer.length() - 1);
                        }
                    }
                    LogUtils.i("curTokens: " + curTokens);
                    walletInfo.setCoinIds(curTokens);
                    walletDaoUtils.updateWallet(walletInfo);
                }
            });
        }
    }

    @OnClick(R.id.iv_title_left_back)
    public void myClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left_back:
                toBack();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        toBack();
    }

    private void toBack() {
        MessageWrap<String> messageWrap = new MessageWrap<String>(EventBusMsgType.UPDATE_WALLET,"更新UI");
        EventBus.getDefault().post(messageWrap);
        finish();
    }


}
