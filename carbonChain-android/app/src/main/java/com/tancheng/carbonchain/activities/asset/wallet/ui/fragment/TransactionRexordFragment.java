package com.tancheng.carbonchain.activities.asset.wallet.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.TransactionRecord;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;
import com.tancheng.carbonchain.activities.asset.wallet.ui.TransactionDetailActivity;
import com.tancheng.carbonchain.activities.asset.wallet.ui.adapter.TxRecordAdapter;
import com.tancheng.carbonchain.activities.base.BaseFragment;
import com.tancheng.carbonchain.db.gen.TransationRecordDaoUtils;

import java.util.List;

import butterknife.BindView;
import name.quanke.app.libs.emptylayout.EmptyLayout;

/**
 * created by tc_collin on 2020/4/8 15:41
 * Description: 交易记录列表
 * version: 1.0
 */
public class TransactionRexordFragment extends BaseFragment {
    @BindView(R.id.lv_trading_record)
    ListView lv_listview;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.emptyLayout)
    EmptyLayout emptyLayout;

    private long tokenId;
    private int direct;
    private String address;
    private TransationRecordDaoUtils txRecordDaoUtils;
    private List<TransactionRecord> transactions;
    private TxRecordAdapter adapter;
    public static boolean refresh = false;//是否刷新数据

    @Override
    public void onResume() {
        super.onResume();
        if (refresh) {
            refreshData();
            refresh = false;
        }
    }

    @Override
    public void lazyLoad() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_tx_record;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {
        Bundle arguments = getArguments();
        tokenId = arguments.getLong("tokenId");
        direct = arguments.getInt("direct");
        address = arguments.getString("address");
        txRecordDaoUtils = TransationRecordDaoUtils.getIntence();
        transactions = txRecordDaoUtils.getTransactions(address, tokenId, direct);
        adapter = new TxRecordAdapter(activity, transactions, R.layout.list_item_txrecord);
        lv_listview.setAdapter(adapter);
        checkEmpty();
    }

    @Override
    public void configViews() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        lv_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TransactionRecord data = transactions.get(position);
                Intent intent = new Intent(mContext, TransactionDetailActivity.class);
                intent.putExtra("walletType", data.getWalletType());
                intent.putExtra("amount", data.getValue());
                intent.putExtra("time", TimeUtils.date2String(data.getTimeStamp()));
                intent.putExtra("from", data.getFromAddr());
                intent.putExtra("to", data.getToAddr());
                intent.putExtra("txHash", data.getTxHash());
                intent.putExtra("blockNumber", data.getBlockNumber());
                intent.putExtra("gasFee", data.getGas());
                intent.putExtra("status", data.getStatus());
                intent.putExtra("diect", data.getDirect());
                startActivity(intent);
            }
        });

        emptyLayout.setEmptyViewButtonId(emptyLayout.getEmptyViewButtonId());
        emptyLayout.setShowEmptyButton(true);
        emptyLayout.setEmptyButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("11111");
            }
        });
    }

    private void refreshData() {
        transactions.clear();
        List<TransactionRecord> datas = txRecordDaoUtils.getTransactions(address, tokenId, direct);
        transactions.addAll(datas);
        adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
        checkEmpty();
    }

    public void checkEmpty() {
        if (transactions.size() == 0) {
            emptyLayout.showEmpty(R.mipmap.ic_no_record, "暂无记录");
            swipeRefresh.setVisibility(View.GONE);
            lv_listview.setVisibility(View.GONE);
        } else {
            lv_listview.setVisibility(View.VISIBLE);
            swipeRefresh.setVisibility(View.VISIBLE);
            emptyLayout.hide();
        }
    }
}
