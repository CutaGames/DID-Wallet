package com.tancheng.carbonchain.activities.asset.wallet.service.btc;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.ETHWallet;
import com.tancheng.carbonchain.activities.asset.wallet.service.IwalletService;

import org.bitcoinj.core.Address;

import java.util.List;

/**
 * created by collin on 2020/3/18 15:17
 * Description: 钱包创建、恢复相关
 * version: 1.0
 */
public class BTCWalletServiceImpl implements IwalletService {
    @Override
    public ETHWallet createNewWallet(String walletName, String pwd) {
        ETHWallet ethWallet = BTCWalletUtil.generateWallet(walletName, pwd);
        return ethWallet;
    }

    @Override
    public ETHWallet modifyName(long walletId, String newName) {
        return BTCWalletUtil.modifyName(walletId,newName);
    }

    @Override
    public ETHWallet modifyPassword(long walletId, String oldPassword, String newPassword) {
        return BTCWalletUtil.modifyPassword(walletId,oldPassword,newPassword);
    }

    @Override
    public boolean deleteWallet(long walletId) {
        return BTCWalletUtil.deleteWallet(walletId);
    }

    @Override
    public String derivePrivateKey(long walletId, String pwd) {
        return BTCWalletUtil.derivePrivateKey(walletId,pwd);
    }

    @Override
    public String deriveKeystore(long walletId, String pwd) {

        return null;
    }

    @Override
    public String deriveMnemonic(long walletId, String pwd) {
        return  BTCWalletUtil.deriveMnemonic(walletId,pwd);
    }

    @Override
    public ETHWallet loadWalletByPrivatekey(String privateKey, String password) {
        return BTCWalletUtil.loadWalletByPrivateKey(privateKey,password);
    }

    @Override
    public ETHWallet loadWalletByMnemonic(String path, List<String> mnemonicList, String password) {
        return BTCWalletUtil.importMnemonic(path,mnemonicList,password);
    }

    @Override
    public ETHWallet loadWalletByKeystore(String keystore, String password) {
        return null;
    }

    @Override
    public boolean verifyAddress(String address) {
        try {
            new Address(BTCClintUtil.getNetworkParams(),address);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(e.getMessage());
            ToastUtils.showShort("地址格式不正确!");
            return false;
        }
    }

//    /**
//     * 新建钱包
//     * @return
//     */
//    @Override
//    public ETHWallet createNewWallet(String walletName, String pwd) {
//        ETHWallet ethWallet = BTCWalletUtils.generateWallet(walletName, pwd);
//        return ethWallet;
//    }
//
//    /**
//     * 通过助记词恢复钱包
//     *
//     * @param mnemonic
//     * @return
//     */
//    @Override
//    public ETHWallet createWalletFromWords(String mnemonic) {
//        try {
//            ETHWallet wallet = new ETHWallet();
//            wallet.setWalletType(WalletType.ETH.getWalletType());
//            wallet.setMnemonic(mnemonic);
//            return wallet;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 通过私钥恢复钱包
//     *
//     * @param privateKey
//     * @return
//     */
//    @Override
//    public ETHWallet createWallet(String privateKey) {
//        BTCWalletUtils.loadWalletByPrivateKey(privateKey,);
//        return null;
//    }
//
//
//    /**
//     * 获取私钥
//     *
//     * @param mnemonic
//     * @return
//     */
//    @Override
//    public String getPrivateKey(String mnemonic) {
//        String[] wordArr = mnemonic.split(" ");
//        List<String> wordsList = Arrays.asList(wordArr);
//        DeterministicSeed deterministicSeed = new DeterministicSeed(wordsList, null, "", 0);
//        DeterministicKeyChain deterministicKeyChain = DeterministicKeyChain.builder().seed(deterministicSeed).build();
//        BigInteger privateKey = deterministicKeyChain.getKeyByPath(parsePath(convert2bitCoinjPath(WalletType.ETH.getPath())), true).getPrivKey();
//        return Numeric.toHexStringWithPrefix(privateKey);
//    }
//
//    private String convert2bitCoinjPath(String path) {
//        return path.toUpperCase().replace("'", "H");
//    }
//
//    /**
//     * 获取地址
//     *
//     * @param privateKey
//     * @return
//     */
//    @Override
//    public String getAddress(String privateKey) {
//        ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
//        return Numeric.prependHexPrefix(Keys.getAddress(ecKeyPair));
//    }
//
//    /**
//     * 获取公钥
//     *
//     * @param privateKey
//     * @return
//     */
//    @Override
//    public String getPublicKey(String privateKey) {
//        return Numeric.toHexStringNoPrefix(ECKeyPair.create(Numeric.toBigInt(privateKey)).getPublicKey());
//    }
}
