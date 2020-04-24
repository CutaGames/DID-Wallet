package com.tancheng.carbonchain.activities.asset.wallet.service.btc;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.ETHWallet;
import com.tancheng.carbonchain.activities.asset.wallet.domain.WalletType;
import com.tancheng.carbonchain.activities.asset.wallet.utils.AESCrypt;
import com.tancheng.carbonchain.activities.asset.wallet.utils.SecureRandomUtil;
import com.tancheng.carbonchain.db.gen.WalletDaoUtils;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.DumpedPrivateKey;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.utils.Numeric;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * 比特币钱包创建工具类
 */
public class BTCWalletUtil {

    /**
     * 安全随机生成器
     */
    private static final SecureRandom secureRandom = SecureRandomUtil.secureRandom();

    /**
     * 创建助记词，并通过助记词创建钱包
     *
     * @param walletName
     * @param pwd
     * @return
     */
    public static ETHWallet generateMnemonic(String walletName, String pwd) {
        String[] pathArray = WalletType.BTC.getPath().split("/");
        String passphrase = "";
        long creationTimeSeconds = System.currentTimeMillis() / 1000;

        DeterministicSeed ds = new DeterministicSeed(secureRandom, 128, passphrase, creationTimeSeconds);
        return generateWalletByMnemonic(walletName, ds, pathArray, pwd);
    }


    @NonNull
    private static String generateNewWalletName() {
        char letter1 = (char) (int) (Math.random() * 26 + 97);
        char letter2 = (char) (int) (Math.random() * 26 + 97);

        String walletName = String.valueOf(letter1) + String.valueOf(letter2) + "-新钱包";
        while (WalletDaoUtils.getIntence().walletNameIsExist(walletName)) {
            letter1 = (char) (int) (Math.random() * 26 + 97);
            letter2 = (char) (int) (Math.random() * 26 + 97);
            walletName = String.valueOf(letter1) + String.valueOf(letter2) + "-新钱包";
        }
        return walletName;
    }

    /**
     * @param walletName 钱包名称
     * @param ds         助记词加密种子
     * @param pathArray  助记词标准
     * @param pwd        密码
     * @return
     */
    @Nullable
    public static ETHWallet generateWalletByMnemonic(String walletName, DeterministicSeed ds,
                                                     String[] pathArray, String pwd) {

        ETHWallet wallet = generateWallet(walletName, pwd);
        if (wallet != null) {
//            wallet.setMnemonic(convertMnemonicList(mnemonic));
        }
        return wallet;
    }

    private static String convertMnemonicList(List<String> mnemonics) {
        StringBuilder sb = new StringBuilder();
        int size = mnemonics.size();

        for (int i = 0; i < size; i++) {
            sb.append(mnemonics.get(i));
            if (i != size - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    @Nullable
    public static ETHWallet generateWallet(String walletName, String password) {
        try {
            NetworkParameters networkParameters = BTCClintUtil.getNetworkParams();
            String passphrase = password;
            long creationTimeSeconds = System.currentTimeMillis() / 1000;
            DeterministicSeed seed = new DeterministicSeed(secureRandom, DeterministicSeed.DEFAULT_SEED_ENTROPY_BITS, passphrase, creationTimeSeconds);

//            Wallet wallet = Wallet.fromSeed(networkParameters, seed);
//            String privateKey = wallet.currentReceiveKey().getPrivateKeyAsWiF(networkParameters);
//            String mnemonics = wallet.getKeyChainSeed().getMnemonicCode().toString();
//            String publicKey = Hex.toHexString(ECKey.publicKeyFromPrivate(wallet.currentReceiveKey().getPrivKey(), true));
//            String address = wallet.currentReceiveAddress().toBase58();

            //通过path方式
            DeterministicKeyChain keyChain = DeterministicKeyChain.builder().seed(seed).build();
            DeterministicKey deterministicKey = keyChain.getKeyByPath(getChildNumbers(), true);
            //私钥  该种格式可以导入到imToken
            String privateKey = deterministicKey.getPrivateKeyAsWiF(networkParameters);
            String mnemonics = keyChain.getMnemonicCode().toString();
            String publicKey = Hex.toHexString(ECKey.publicKeyFromPrivate(deterministicKey.getPrivKey(), true));
            Address address = deterministicKey.toAddress(networkParameters);
            String addressBase58 = address.toBase58();

//            ECKey ecKey = ECKey.fromPrivate(privateKey.getBytes());
//            List<ECKey> keys = new ArrayList<>();
//            keys.add(ecKey);
//            Wallet wallet = Wallet.fromKeys(networkParameters, keys);
//            LogUtils.i("助记词: " + wallet.currentReceiveAddress().toBase58());

            //助记词
            LogUtils.i("助记词: " + mnemonics);
            LogUtils.i("私钥: " + privateKey);
            LogUtils.i("公钥: " + publicKey);
            LogUtils.i("地址: " + addressBase58);

            if (!StringUtils.isEmpty(mnemonics)){
                mnemonics.replace("[","");
                mnemonics.replace("]","");
            }

            ETHWallet wallet = new ETHWallet();
            wallet.setName(walletName);
            wallet.setAddress(addressBase58);
            wallet.setPassword(EncryptUtils.encryptMD5ToString(password));
            String eccrytyPrivate = AESCrypt.encrypt(privateKey, password);
            String eccrytyMnemonics = AESCrypt.encrypt(mnemonics, password);
            wallet.setMnemonic(eccrytyMnemonics);
            wallet.setPrivateKey(eccrytyPrivate);
            return wallet;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<ChildNumber> getChildNumbers() {
        String path = BTCClintUtil.getWalletPath();
        String[] pathArray = path.split("/");
        List<ChildNumber> childNumbers = new ArrayList<>();
        for (int i = 1; i < pathArray.length; i++) {
            ChildNumber childNumber;
            if (pathArray[i].endsWith("'")) {
                int number = Integer.parseInt(pathArray[i].substring(0,
                        pathArray[i].length() - 1));
                childNumber = new ChildNumber(number, true);
            } else {
                int number = Integer.parseInt(pathArray[i]);
                childNumber = new ChildNumber(number, false);
            }
            childNumbers.add(childNumber);
        }
        return childNumbers;
    }


    /**
     * 通过明文私钥导入钱包
     *
     * @param privateKey
     * @param password
     * @return
     */
    public static ETHWallet loadWalletByPrivateKey(String privateKey, String password) {
        ECKey key = DumpedPrivateKey.fromBase58(BTCClintUtil.getNetworkParams(), privateKey).getKey();
        Address address = key.toAddress(BTCClintUtil.getNetworkParams());
        String addressToBase58 = address.toBase58();
        String walletName = generateNewWalletName();
        ETHWallet wallet = new ETHWallet();
        wallet.setName(walletName);
        wallet.setAddress(addressToBase58);
        wallet.setPassword(EncryptUtils.encryptMD5ToString(password));
        String eccrytyPrivate = AESCrypt.encrypt(privateKey, password);
        wallet.setPrivateKey(eccrytyPrivate);
        return wallet;
    }

    public static boolean isTooSimplePrivateKey(String privateKey) {

        if (Numeric.toBigInt(privateKey).intValue() < 100000000) {
            return true;
        } else {
            return true;
        }
    }

    /**
     * 通过导入助记词，导入钱包
     *
     * @param path     路径
     * @param list     助记词
     * @param password 密码
     * @return
     */
    public static ETHWallet importMnemonic(String path, List<String> list, String password) {
        if (!path.startsWith("m") && !path.startsWith("M")) {
            //参数非法
            return null;
        }
        String[] pathArray = path.split("/");
        if (pathArray.length <= 1) {
            //内容不对
            return null;
        }
        String passphrase = "";
        long creationTimeSeconds = System.currentTimeMillis() / 1000;
        DeterministicSeed seed = new DeterministicSeed(list, null, passphrase, creationTimeSeconds);
        //通过path方式
        DeterministicKeyChain keyChain = DeterministicKeyChain.builder().seed(seed).build();
        DeterministicKey deterministicKey = keyChain.getKeyByPath(getChildNumbers(), true);
        //私钥  该种格式可以导入到imToken
        String privateKey = deterministicKey.getPrivateKeyAsWiF(BTCClintUtil.getNetworkParams());
        String mnemonics = keyChain.getMnemonicCode().toString();
        String publicKey = Hex.toHexString(ECKey.publicKeyFromPrivate(deterministicKey.getPrivKey(), true));
        Address address = deterministicKey.toAddress(BTCClintUtil.getNetworkParams());
        String addressBase58 = address.toBase58();
        String walletName = generateNewWalletName();

        ETHWallet wallet = new ETHWallet();
        wallet.setName(walletName);
        wallet.setAddress(addressBase58);
        wallet.setPassword(EncryptUtils.encryptMD5ToString(password));
        String eccrytyPrivate = AESCrypt.encrypt(privateKey, password);
        String eccrytyMnemonics = AESCrypt.encrypt(mnemonics, password);
        wallet.setMnemonic(eccrytyMnemonics);
        wallet.setPrivateKey(eccrytyPrivate);
        return wallet;
    }

    public static ETHWallet modifyName(long walletId, String newName) {
        ETHWallet wallet = WalletDaoUtils.getIntence().getWalletInfo(walletId);
        if (wallet != null) {
            wallet.setName(newName);
            WalletDaoUtils.getIntence().updateWallet(wallet);
        }
        return wallet;
    }

    /**
     * 修改钱包密码
     *
     * @param walletId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public static ETHWallet modifyPassword(long walletId, String oldPassword, String newPassword) {
        ETHWallet wallet = WalletDaoUtils.getIntence().getWalletInfo(walletId);
        try {
            String oldPrivateKey = wallet.getPrivateKey();
            String oldMnemonic = wallet.getMnemonic();
            String newMnemonic = "";
            if (!StringUtils.isEmpty(oldMnemonic)) {
                String rawMnemonic = AESCrypt.decrypt(oldMnemonic, oldPassword);
                if (!StringUtils.isEmpty(rawMnemonic)) {
                    {
                        newMnemonic = AESCrypt.encrypt(rawMnemonic, newPassword);
                    }
                }
            }
            String newPrivatekey = "";
            if (!StringUtils.isEmpty(oldPrivateKey)) {
                String rawwPrivateKey = AESCrypt.decrypt(oldMnemonic, oldPassword);
                if (!StringUtils.isEmpty(rawwPrivateKey)) {
                    {
                        newPrivatekey = AESCrypt.encrypt(rawwPrivateKey, newPassword);
                    }
                }
            }

            wallet.setPrivateKey(newPrivatekey);
            wallet.setMnemonic(newMnemonic);
            wallet.setPassword(EncryptUtils.encryptMD5ToString(newPassword));
            WalletDaoUtils.getIntence().updateWallet(wallet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wallet;
    }

    public static boolean deleteWallet(long walletId) {
        try {
            WalletDaoUtils.getIntence().delByPrimkey(walletId);
        }catch (Exception e){
            LogUtils.e(e.getMessage());
            return false;
        }
       return true;
    }

    /**
     * 导出明文私钥
     *
     * @param walletId 钱包Id
     * @param pwd      钱包密码
     * @return
     */
    public static String derivePrivateKey(long walletId, String pwd) {
        ETHWallet wallet = WalletDaoUtils.getIntence().getWalletInfo(walletId);
        String privateKey = null;
        try {
            String encodePrivateKey = wallet.getPrivateKey();
            if (!StringUtils.isEmpty(encodePrivateKey)) {
                privateKey = AESCrypt.decrypt(encodePrivateKey, pwd);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(e.getMessage());
        }
        return privateKey;
    }


    /**
     * 导出助记词
     *
     * @param walletId 钱包Id
     * @param pwd      钱包密码
     * @return
     */
    public static String deriveMnemonic(long walletId, String pwd) {
        ETHWallet wallet = WalletDaoUtils.getIntence().getWalletInfo(walletId);
        String mnemonic = null;
        try {
            String encodeMnemonic = wallet.getMnemonic();
            if (!StringUtils.isEmpty(encodeMnemonic)) {
                mnemonic = AESCrypt.decrypt(encodeMnemonic, pwd);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(e.getMessage());
        }
        return mnemonic;
    }
}
