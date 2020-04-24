package com.tancheng.carbonchain.activities.asset.digit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.utils.SelectableCoinItem;

import java.util.ArrayList;
import java.util.List;

public class ActivityTradeCoin extends AppCompatActivity {

    private LinearLayout mAddrLayout;
    private View mAddrBottomView;
    private TextView mTvTransferConfirm;
    private LinearLayout mContainerScroll;
    private View mCoinTopView;
    private ImageView mIvTouchUpArrow;
    private LinearLayout mContainer;
    private List mSelectableCoins;
    private ImageView mIvAddressBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_coin);
        findViews();
        initViews();
        setOnClickListeners();
    }

    private void findViews(){
        mAddrLayout = findViewById(R.id.addr_layout);
        mAddrBottomView = findViewById(R.id.addr_bottom_view);

        mCoinTopView = findViewById(R.id.coin_top_view);
        mContainerScroll = (LinearLayout)findViewById(R.id.container_scroll);
        mTvTransferConfirm = findViewById(R.id.tv_transfer_confirm);
        mIvTouchUpArrow = findViewById(R.id.iv_coin_touch_arrow_up);
        mContainer = findViewById(R.id.container);
        mIvAddressBook = findViewById(R.id.addr_book);
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


    }

    private void setOnClickListeners(){
        // coin onclick
        View.OnClickListener coinOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddrLayout.setVisibility(View.VISIBLE);

                LinearLayout.LayoutParams containerScrollLayoutParams = (LinearLayout.LayoutParams) mContainerScroll.getLayoutParams();
                containerScrollLayoutParams.weight = 145;
                LinearLayout.LayoutParams coinTopViewLayoutParams = (LinearLayout.LayoutParams) mCoinTopView.getLayoutParams();
                coinTopViewLayoutParams.weight = 434;
                mTvTransferConfirm.setVisibility(View.VISIBLE);
                mIvTouchUpArrow.setVisibility(View.VISIBLE);
            }
        };
        for(int i = 0; i < mSelectableCoins.size(); i++){
            SelectableCoinItem selectableCoinItem = (SelectableCoinItem) mSelectableCoins.get(i);
            selectableCoinItem.setOnClickListener(coinOnClickListener);
        }

        // touch up arrow
        mIvTouchUpArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddrLayout.setVisibility(View.GONE);

                LinearLayout.LayoutParams containerScrollLayoutParams = (LinearLayout.LayoutParams) mContainerScroll.getLayoutParams();
                containerScrollLayoutParams.weight = 1;
                LinearLayout.LayoutParams coinTopViewLayoutParams = (LinearLayout.LayoutParams) mCoinTopView.getLayoutParams();
                coinTopViewLayoutParams.weight = 0;
                mTvTransferConfirm.setVisibility(View.GONE);
                mIvTouchUpArrow.setVisibility(View.GONE);
            }
        });

        // confirm
        mTvTransferConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ActivityTradeCoin.this, ActivityTradeCoinConfirm.class);
                startActivity(intent);
                finish();
            }
        });

        // address book
        mIvAddressBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ActivityTradeCoin.this, ActivityTradeCoinAddressBook.class);
                startActivity(intent);
            }
        });
    }
}
