package com.carbonchain.server.service.wallet.db;
import com.carbonchain.server.service.wallet.db.entity.Account;
import lombok.extern.slf4j.Slf4j;

/**
 * created by tc_collin on 2020/3/18 16:44
 * Description:
 * version: 1.0
 */
@Slf4j
public class WalletDbHelper {
    private static final long VERSION_WALLET_DB = 0;
    private static WalletDbHelper sInstance;

    public WalletDbHelper() {

    }

    public static WalletDbHelper inst() {
        if (sInstance == null) {
            synchronized (WalletDbHelper.class) {
                if (sInstance == null) {
                    sInstance = new WalletDbHelper();
                }
            }
        }
        return sInstance;
    }

    private byte[] getKey() {
//        String spKey = "wallet_key";
//        String key ="ssssss";
//        if (StringUtils.isEmpty(key)) {
//            byte[] k = new byte[64];
//            new SecureRandom().nextBytes(k);
//            key = Base64.encodeToString(k, Base64.NO_WRAP);
//            SharedPreferenceUtils.inst().setValue(spKey,key);
//        }
//        return Base64.decode(key, Base64.NO_WRAP);
        return "".getBytes();
    }

    public synchronized void insertAccount( Account account) {

//        Realm realm = Realm.getInstance(mConfig);
//        realm.beginTransaction();
//        realm.copyToRealmOrUpdate(account);
//        realm.commitTransaction();
//        DaoSession daoInstant = CarbonApplication.getDaoInstant();
//        AccountDao accountDao = daoInstant.getAccountDao();
//        accountDao.insert(account);

    }

//    public synchronized boolean dropAccount(String id, String passwd) {
//        boolean success = false;
//        Realm realm = Realm.getInstance(mConfig);
//        realm.beginTransaction();
//        Account account = realm.where(Account.class).equalTo("id", id).findFirst();
//        if (account != null) {
//            account.deleteFromRealm();
//            success = true;
//        }
//        realm.commitTransaction();
//        return success;
//    }
//
//    public synchronized Account query(String id) {
//        Realm realm = Realm.getInstance(mConfig);
//        return realm.where(Account.class).equalTo("id", id).findFirst();
//    }
}
