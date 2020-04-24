package com.tancheng.carbonchain.activities.asset.digit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.utils.SelectableCoinItem;

import java.util.ArrayList;
import java.util.List;

public class ActivitySoftWalletImportSelectCoin extends AppCompatActivity {


    private LinearLayout mContainer;
    private List mSelectableCoins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_wallet_import_select_coin);
        findViews();
        initViews();
        setOnClickListeners();
    }

    private void findViews(){
        mContainer = findViewById(R.id.container);
    }

    private void initViews(){

        mSelectableCoins = new ArrayList<SelectableCoinItem>();

        // insert some coins
        SelectableCoinItem selectableCoinBtc = new SelectableCoinItem(this);
        selectableCoinBtc.setShowImage(R.mipmap.coin_icon_btc);
        selectableCoinBtc.setCoinNameText(getString(R.string.coin_name_btc));
        selectableCoinBtc.setCoinNameFullText(getString(R.string.coin_name_full_btc));
        LinearLayout.LayoutParams btcLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        selectableCoinBtc.setLayoutParams(btcLayoutParams);
        mContainer.addView(selectableCoinBtc);
        mSelectableCoins.add(selectableCoinBtc);

        SelectableCoinItem selectableCoinUsdt = new SelectableCoinItem(this);
        selectableCoinUsdt.setShowImage(R.mipmap.coin_icon_usdt);
        selectableCoinUsdt.setCoinNameText(getString(R.string.coin_name_usdt));
        selectableCoinUsdt.setCoinNameFullText(getString(R.string.coin_name_full_usdt));
        LinearLayout.LayoutParams usdtLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        selectableCoinUsdt.setLayoutParams(usdtLayoutParams);
        mContainer.addView(selectableCoinUsdt);
        mSelectableCoins.add(selectableCoinUsdt);

        SelectableCoinItem selectableCoinXrp = new SelectableCoinItem(this);
        selectableCoinXrp.setShowImage(R.mipmap.coin_icon_xrp);
        selectableCoinXrp.setCoinNameText(getString(R.string.coin_name_xrp));
        selectableCoinXrp.setCoinNameFullText(getString(R.string.coin_name_full_xrp));
        LinearLayout.LayoutParams xrpLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        selectableCoinXrp.setLayoutParams(xrpLayoutParams);
        mContainer.addView(selectableCoinXrp);
        mSelectableCoins.add(selectableCoinXrp);

        SelectableCoinItem selectableCoinEth = new SelectableCoinItem(this);
        selectableCoinEth.setShowImage(R.mipmap.coin_icon_eth);
        selectableCoinEth.setCoinNameText(getString(R.string.coin_name_eth));
        selectableCoinEth.setCoinNameFullText(getString(R.string.coin_name_full_eth));
        LinearLayout.LayoutParams ethLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        selectableCoinEth.setLayoutParams(ethLayoutParams);
        mContainer.addView(selectableCoinEth);
        mSelectableCoins.add(selectableCoinEth);

        SelectableCoinItem selectableCoinEos = new SelectableCoinItem(this);
        selectableCoinEos.setShowImage(R.mipmap.coin_icon_eos);
        selectableCoinEos.setCoinNameText(getString(R.string.coin_name_eos));
        selectableCoinEos.setCoinNameFullText(getString(R.string.coin_name_full_eos));
        LinearLayout.LayoutParams eosLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        selectableCoinEos.setLayoutParams(eosLayoutParams);
        mContainer.addView(selectableCoinEos);
        mSelectableCoins.add(selectableCoinEos);

        SelectableCoinItem selectableCoinLtc = new SelectableCoinItem(this);
        selectableCoinLtc.setShowImage(R.mipmap.coin_icon_ltc);
        selectableCoinLtc.setCoinNameText(getString(R.string.coin_name_ltc));
        selectableCoinLtc.setCoinNameFullText(getString(R.string.coin_name_full_ltc));
        LinearLayout.LayoutParams ltcLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        selectableCoinLtc.setLayoutParams(ltcLayoutParams);
        mContainer.addView(selectableCoinLtc);
        mSelectableCoins.add(selectableCoinLtc);
    }

    private void setOnClickListeners(){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivitySoftWalletImportSelectCoin.this, ActivitySoftWalletImportCoinDetail.class);
                startActivity(intent);
                finish();
            }
        };
        for(int i = 0; i < mSelectableCoins.size(); i++){
            SelectableCoinItem selectableCoin = (SelectableCoinItem) mSelectableCoins.get(i);
            selectableCoin.setOnClickListener(onClickListener);
        }
    }
}
