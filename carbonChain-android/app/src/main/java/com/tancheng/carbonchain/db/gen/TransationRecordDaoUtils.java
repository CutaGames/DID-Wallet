package com.tancheng.carbonchain.db.gen;

import com.blankj.utilcode.util.LogUtils;
import com.tancheng.carbonchain.MyApplication;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.TransactionRecord;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * created by tc_collin on 2020/3/28 12:23
 * Description: 交易记录Dao
 * version: 1.0
 */
public class TransationRecordDaoUtils {

    TransactionRecordDao transactionDao;
    private static TransationRecordDaoUtils transationDaoUtils;

    public static TransationRecordDaoUtils getIntence() {
        if (transationDaoUtils == null) {
            transationDaoUtils = new TransationRecordDaoUtils();
        }
        return transationDaoUtils;
    }

    private TransationRecordDaoUtils() {
        transactionDao = MyApplication.getmDaoSession().getTransactionRecordDao();
    }

    /**
     * 插入数据
     *
     * @param transactionRecord
     * @return
     */
    public boolean insert(TransactionRecord transactionRecord) {
        long insert = transactionDao.insert(transactionRecord);
        boolean isSuccess = insert > 0 ? true : false;
        return isSuccess;
    }

    /**
     * 获取交易记录
     * diect 方向 0发出 1收款
     *
     * @param diect
     * @param tokenId
     * @return
     */
    public List<TransactionRecord> getTransactions(String address, long tokenId, int diect) {
        LogUtils.i("获取交易记录");
        if (diect == 0) {
            QueryBuilder<TransactionRecord> queryBuilder = transactionDao.queryBuilder();
            queryBuilder.where(TransactionRecordDao.Properties.FromAddr.eq(address), TransactionRecordDao.Properties.Direct.eq(diect), TransactionRecordDao.Properties.TokenId.eq(tokenId));
            List<TransactionRecord> list = queryBuilder.build().list();
            return list;
        } else if (diect == 1) {
            QueryBuilder<TransactionRecord> queryBuilder = transactionDao.queryBuilder();
            queryBuilder.where(TransactionRecordDao.Properties.ToAddr.eq(address), TransactionRecordDao.Properties.Direct.eq(diect), TransactionRecordDao.Properties.TokenId.eq(tokenId));
            List<TransactionRecord> list = queryBuilder.build().list();
            return list;
        } else {
            QueryBuilder<TransactionRecord> queryBuilder = transactionDao.queryBuilder();
            queryBuilder.where(TransactionRecordDao.Properties.TokenId.eq(tokenId));
            List<TransactionRecord> list = queryBuilder.build().list();
            List<TransactionRecord> datas = new ArrayList<>();
            for (TransactionRecord record : list) {
                if (record.getFromAddr().equals(address) || record.getToAddr().equals(address)) {
                    datas.add(record);
                }
            }
            return datas;
        }
    }

    public List<TransactionRecord> getPaddingTx(int walletType) {
        QueryBuilder<TransactionRecord> queryBuilder = transactionDao.queryBuilder();
        queryBuilder.where(TransactionRecordDao.Properties.WalletType.eq(walletType),TransactionRecordDao.Properties.Status.eq(0));
        List<TransactionRecord> list = queryBuilder.build().list();
        return list;
    }


    /**
     * 更新数据
     *
     * @param transaction
     */
    public void updateTransation(TransactionRecord transaction) {
        transactionDao.update(transaction);
    }

}
