package com.tancheng.carbonchain.db.gen;

import com.blankj.utilcode.util.SPUtils;
import com.tancheng.carbonchain.MyApplication;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.ETHWallet;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.WalletBalance;

import java.math.BigDecimal;
import java.util.List;

/**
 * created by tc_collin on 2020/3/28 12:23
 * Description: 余额信息
 * version: 1.0
 */
public class WalletBalnceDaoUtils {

    WalletBalanceDao walletBalanceDao;
    private static WalletBalnceDaoUtils WalletBlanceDaoUtils;

    public static WalletBalnceDaoUtils getIntence() {
        if (WalletBlanceDaoUtils == null) {
            WalletBlanceDaoUtils = new WalletBalnceDaoUtils();
        }
        return WalletBlanceDaoUtils;
    }

    private WalletBalnceDaoUtils() {
        walletBalanceDao = MyApplication.getmDaoSession().getWalletBalanceDao();
    }

    /**
     * 插入数据
     *
     * @param WalletBalance
     * @return
     */
    public boolean insert(WalletBalance WalletBalance) {
        long insert = walletBalanceDao.insert(WalletBalance);
        boolean isSuccess = insert > 0 ? true : false;
        return isSuccess;
    }

    public void upadteBalance(WalletBalance walletBalance) {
        walletBalanceDao.update(walletBalance);
    }

    public void deleteByWalletAddr(String walletAddr){
        WalletBalance walletBalance = new WalletBalance();
        walletBalance.setWalletAddress(walletAddr);
        walletBalanceDao.delete(walletBalance);
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    public WalletBalance getWalletBalanceById(long id) {
        List<WalletBalance> list = walletBalanceDao.queryBuilder().where(WalletBalanceDao.Properties.Id.eq(id)).build().list();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 获取钱包余额
     * @param symbol 币种简称
     * @return
     */
    public BigDecimal getBalanceBySymbol(String symbol) {
        List<WalletBalance> list = walletBalanceDao.queryBuilder().where(WalletBalanceDao.Properties.Symbol.eq(symbol)).build().list();
        BigDecimal total = BigDecimal.ZERO;
        for (WalletBalance entity : list) {
            double balance = entity.getBalance();
            // double price = entity.getPrice();
            BigDecimal bigBalance = BigDecimal.valueOf(balance);
            String priceStr = SPUtils.getInstance().getString(entity.getSymbol() + "_price", "0");
            BigDecimal bigPrice = new BigDecimal(priceStr);
            BigDecimal multiply = bigBalance.multiply(bigPrice);
            total = total.add(multiply);
        }
        return total;
    }

    /**
     * 获取详情
     *
     * @param address 钱包地址
     * @param symbol  币种symbol
     * @return
     */
    public WalletBalance getWalletBalance(String address, String symbol) {
        List<WalletBalance> list = walletBalanceDao.queryBuilder().where(WalletBalanceDao.Properties.WalletAddress.eq(address),
                WalletBalanceDao.Properties.Symbol.eq(symbol)).build().list();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 指定钱包余额总计
     *
     * @return
     */
    public BigDecimal getWalletTotalBalance(String walletAddress) {
        List<WalletBalance> list = walletBalanceDao.queryBuilder().where(WalletBalanceDao.Properties.WalletAddress.eq(walletAddress)).build().list();
        BigDecimal total = BigDecimal.ZERO;
        for (WalletBalance entity : list) {
            double balance = entity.getBalance();
            // double price = entity.getPrice();
            BigDecimal bigBalance = BigDecimal.valueOf(balance);
            String priceStr = SPUtils.getInstance().getString(entity.getSymbol() + "_price", "0");
            BigDecimal bigPrice = new BigDecimal(priceStr);
            BigDecimal multiply = bigBalance.multiply(bigPrice);
            total = total.add(multiply);
        }
        return total;
    }

    /**
     * 所有钱包余额总计
     *
     * @return
     */
    public BigDecimal getAllWalletTotalBalance() {
        List<ETHWallet> wallets = WalletDaoUtils.getIntence().getWallets();
        BigDecimal total = BigDecimal.ZERO;
        for (ETHWallet wallet : wallets) {
            String address = wallet.getAddress();
            BigDecimal walletAllTokenBalance = getWalletTotalBalance(address);
            total = total.add(walletAllTokenBalance);
        }
        return total;
    }


}
