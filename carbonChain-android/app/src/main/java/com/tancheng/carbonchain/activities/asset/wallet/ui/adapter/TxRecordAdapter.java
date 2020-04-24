package com.tancheng.carbonchain.activities.asset.wallet.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.TransactionRecord;

import java.math.BigDecimal;
import java.util.List;

/**
 * created by collin on 2020/3/30 10:35
 * Description: 交易记录adapter
 * version: 1.0
 */
public class TxRecordAdapter extends BaseAdapter {
    private Activity mActivity;
    private int layoutId;
    private List<TransactionRecord> items;

    public TxRecordAdapter(Activity context, List<TransactionRecord> tokenItems, int layoutId) {
        this.mActivity = context;
        this.layoutId = layoutId;
        this.items = tokenItems;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = ViewHolder.get(mActivity, view, viewGroup,
                layoutId, position);
        TransactionRecord transactionRecord = items.get(position);
        Integer direct = transactionRecord.getDirect();
        String amount = BigDecimal.valueOf(transactionRecord.getValue()).toPlainString();
        if (direct == 0) {//转出
            holder.setImageResource(R.id.iv_diect, R.mipmap.ic_record_out);
            holder.setTextColor(R.id.tv_amount, mActivity.getResources().getColor(R.color.record_out_color));
            holder.setText(R.id.tv_amount, "-" + amount);
        } else {
            holder.setImageResource(R.id.iv_diect, R.mipmap.ic_record_in);
            holder.setTextColor(R.id.tv_amount, mActivity.getResources().getColor(R.color.record_in_color));
            holder.setText(R.id.tv_amount, "+" + amount);
        }
        holder.setText(R.id.tv_txhash, transactionRecord.getTxHash());
        String dateTime = TimeUtils.date2String(transactionRecord.getTimeStamp());
        holder.setText(R.id.tv_tx_datetime, dateTime);
        if (!StringUtils.isEmpty(transactionRecord.getRemark())) {
            holder.setText(R.id.tv_remark, "备注：" + transactionRecord.getRemark());
            holder.setVisible(R.id.tv_remark,true);
        }else{
            holder.setVisible(R.id.tv_remark,false);
        }
        String status = "进行中";
        if (transactionRecord.getStatus() == 1) {
            status = "完成";
        } else if (transactionRecord.getStatus() == 2) {
            status = "失败";
        }
        holder.setText(R.id.tv_status, status);
        return holder.getConvertView();
    }
}


