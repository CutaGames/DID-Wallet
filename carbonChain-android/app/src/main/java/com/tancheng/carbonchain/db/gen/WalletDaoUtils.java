package com.tancheng.carbonchain.db.gen;

import com.blankj.utilcode.util.StringUtils;
import com.tancheng.carbonchain.MyApplication;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.ETHWallet;
import com.tancheng.carbonchain.activities.asset.wallet.domain.TokenType;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletConstants;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * created by tc_collin on 2020/3/28 12:23
 * Description: 钱包Dao
 * version: 1.0
 */
public class WalletDaoUtils {

    ETHWalletDao walletDao;
    private static WalletDaoUtils walletDaoUtils;

    public static WalletDaoUtils getIntence() {
        if (walletDaoUtils == null) {
            walletDaoUtils = new WalletDaoUtils();
        }
        return walletDaoUtils;
    }

    private WalletDaoUtils() {
        walletDao = MyApplication.getmDaoSession().getETHWalletDao();
    }


    /**
     * 插入数据
     *
     * @param ethWallet
     * @return
     */
    public boolean insert(ETHWallet ethWallet) {
        long insert = walletDao.insert(ethWallet);
        boolean isSuccess = insert > 0 ? true : false;
        return isSuccess;
    }

    public void delByPrimkey(long walletId){
        walletDao.deleteByKey(walletId);
    }

    /**
     * 获取钱包详情
     *
     * @param walletID
     * @return
     */
    public ETHWallet getWalletInfo(long walletID) {
        List<ETHWallet> list = walletDao.queryBuilder().where(ETHWalletDao.Properties.Id.eq(walletID)).build().list();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 获取钱包详情
     *
     * @param address
     * @return
     */
    public ETHWallet getWalletByAddr(String address) {
        List<ETHWallet> list = walletDao.queryBuilder().where(ETHWalletDao.Properties.Address.eq(address)).build().list();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 获取钱包列表
     *
     * @param walletType
     * @return
     */
    public  List<ETHWallet> getWalletListByType(int  walletType) {
        List<ETHWallet> list = walletDao.queryBuilder().where(ETHWalletDao.Properties.WalletType.eq(walletType)).build().list();
        return list;
    }


    /**
     * 已创建的钱包列表
     *
     * @return
     */
    public List<ETHWallet> getWallets() {
        return walletDao.queryBuilder().list();
    }

    /**
     * 设置当前钱包
     *
     * @param walletId
     * @return
     */
    public boolean setCurrentWallet(long walletId) {
        List<ETHWallet> list = walletDao.queryBuilder().list();
        for (ETHWallet wallet : list) {
            boolean isCurrent = wallet.getId() == walletId ? true : false;
            wallet.setCurrent(isCurrent);
            walletDao.update(wallet);
        }
        return true;
    }

    /**
     * 获取当前钱包
     *
     * @return
     */
    public ETHWallet getCurrentWallet() {
        List<ETHWallet> list = walletDao.queryBuilder().list();
        for (ETHWallet wallet : list) {
            boolean isCurrent = wallet.getIsCurrent();
            if (isCurrent)
                return wallet;
        }
        return null;
    }

    public void updateWallet(ETHWallet wallet) {
        walletDao.update(wallet);
    }

    /**
     * 钱包名称是否已存在
     *
     * @param walletName
     * @return
     */
    public boolean walletNameIsExist(String walletName) {
        List<ETHWallet> list = walletDao.queryBuilder().list();
        for (ETHWallet wallet : list) {
            boolean equals = wallet.getName().equals(walletName);
            if (equals) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取钱包中代币
     *
     * @param walletId
     * @return
     */
    public List<TokenType> getWalletCoins(long walletId) {
        List<TokenType> coins = new ArrayList<>();
        QueryBuilder<ETHWallet> where = walletDao.queryBuilder().where(ETHWalletDao.Properties.Id.eq(walletId));
        List<ETHWallet> list = where.build().list();
        if (list != null && list.size() > 0) {
            ETHWallet wallet = list.get(0);
            String coinIds = wallet.getCoinIds();
            if (!StringUtils.isEmpty(coinIds)) {
                String[] split = coinIds.split(WalletConstants.COIIN_SPLIT_CHAR);
                for (String coinId : split) {
                    TokenType tokenType = TokenType.of(Integer.parseInt(coinId));
                    coins.add(tokenType);
                }
                return coins;
            }
        }
        return coins;
    }
}
