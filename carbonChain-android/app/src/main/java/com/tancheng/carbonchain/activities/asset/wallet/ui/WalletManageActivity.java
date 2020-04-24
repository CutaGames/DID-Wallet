package com.tancheng.carbonchain.activities.asset.wallet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.ETHWallet;
import com.tancheng.carbonchain.activities.asset.wallet.ui.adapter.WalletManageAdapter;
import com.tancheng.carbonchain.activities.base.BaseActivity;
import com.tancheng.carbonchain.db.gen.WalletDaoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import name.quanke.app.libs.emptylayout.EmptyLayout;

/**
 * created by tc_collin on 2020/4/2 16:47
 * Description: 钱包管理
 * version: 1.0
 */
public class WalletManageActivity extends BaseActivity {
    @BindView(R.id.lv_data)
    ListView lvData;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.emptyLayout)
    EmptyLayout emptyLayout;

    WalletDaoUtils walletDaoUtils;
    List<ETHWallet> wallets;
    WalletManageAdapter adapter;
    public static boolean refreshData = false;

    @Override
    protected void onResume() {
        super.onResume();
        if (refreshData) {
            refresh();
            refreshData = true;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_manage;
    }

    @Override
    public void initDatas() {
        tvTitle.setText("钱包管理");
    }

    @Override
    public void configViews() {
        walletDaoUtils = WalletDaoUtils.getIntence();
        wallets = walletDaoUtils.getWallets();
        adapter = new WalletManageAdapter(mContext, wallets, R.layout.list_item_wallet_manage);
        lvData.setAdapter(adapter);

        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                WalletDetailActivity.startAct(mContext, wallets.get(position).getId(), 600.00);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        emptyLayout.setShowEmptyButton(false);
    }

    @OnClick({R.id.iv_title_left_back, R.id.lly_create_wallet, R.id.lly_import_wallet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left_back:
                finish();
                break;
            case R.id.lly_create_wallet:
                Intent intent = new Intent(mContext, WalletChooseActivity.class);
                startActivity(intent);
                break;
            case R.id.lly_import_wallet:
                Intent importWalletIntnet = new Intent(this, WalletImportActivity.class);
                startActivity(importWalletIntnet);
                finish();
                break;
        }
    }

    private void refresh() {
        if (walletDaoUtils == null) {
            walletDaoUtils = WalletDaoUtils.getIntence();
        }
        List<ETHWallet> datas = walletDaoUtils.getWallets();
        wallets.clear();
        wallets.addAll(datas);
        adapter.notifyDataSetChanged();
        checkEmpty();
    }

    private void checkEmpty() {
        if (wallets == null || wallets.size() == 0) {
            emptyLayout.showEmpty();
            emptyLayout.setEmptyMessage("您还未创建钱包");
            emptyLayout.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.GONE);
        } else {
            emptyLayout.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
        }
    }
}
