package com.tancheng.carbonchain.db.gen;

import com.tancheng.carbonchain.MyApplication;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.WalletCoin;
import java.util.List;

/**
 * created by tc_collin on 2020/3/28 12:23
 * Description: 代币信息
 * version: 1.0
 */
public class TokenDaoUtils {

    WalletCoinDao tokenDao;
    private static TokenDaoUtils TokenDaoUtils;

    public static TokenDaoUtils getIntence() {
        if (TokenDaoUtils == null) {
            TokenDaoUtils = new TokenDaoUtils();
        }
        return TokenDaoUtils;
    }

    private TokenDaoUtils() {
        tokenDao = MyApplication.getmDaoSession().getWalletCoinDao();
    }

    /**
     * 插入数据
     *
     * @param walletCoin
     * @return
     */
    public boolean insert(WalletCoin walletCoin) {
        long insert = tokenDao.insert(walletCoin);
        boolean isSuccess = insert > 0 ? true : false;
        return isSuccess;
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    public WalletCoin getTokenInfo(long id) {
        List<WalletCoin> list = tokenDao.queryBuilder().where(AddressBookDao.Properties.Id.eq(id)).build().list();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 已创建的地址簿
     *
     * @return
     */
    public List<WalletCoin> getTokens() {
        return tokenDao.queryBuilder().list();
    }


    public void updateBook(WalletCoin walletCoin) {
        tokenDao.update(walletCoin);
    }

    /**
     * 删除
     *
     * @param tokenId
     */
    public void delToken(long tokenId) {
        tokenDao.deleteByKey(tokenId);
    }

}
