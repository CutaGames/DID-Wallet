package com.tancheng.carbonchain.activities.asset.wallet.service.eth;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.ETHWallet;
import com.tancheng.carbonchain.activities.asset.wallet.service.IwalletService;

import org.web3j.abi.datatypes.Address;

import java.util.List;

/**
 * created by collin on 2020/3/18 15:17
 * Description: 钱包创建、恢复相关
 * version: 1.0
 */
public class ETHWalletServiceImpl implements IwalletService {
    @Override
    public ETHWallet createNewWallet(String walletName, String pwd) {
        return ETHWalletUtil.generateMnemonic(walletName, pwd);
    }

    @Override
    public ETHWallet modifyName(long walletId, String newName) {
        return ETHWalletUtil.modifyName(walletId,newName);
    }

    @Override
    public ETHWallet modifyPassword(long walletId, String oldPassword, String newPassword) {
        return ETHWalletUtil.modifyPassword(walletId,oldPassword,newPassword);
    }

    @Override
    public boolean deleteWallet(long walletId) {
        return ETHWalletUtil.deleteWallet(walletId);
    }

    @Override
    public String derivePrivateKey(long walletId, String pwd) {
        return ETHWalletUtil.derivePrivateKey(walletId,pwd);
    }

    @Override
    public String deriveKeystore(long walletId, String pwd) {
        return ETHWalletUtil.deriveKeystore(walletId,pwd);
    }

    @Override
    public String deriveMnemonic(long walletId, String pwd) {
        return ETHWalletUtil.deriveMnemonic(walletId,pwd);
    }

    @Override
    public ETHWallet loadWalletByPrivatekey(String privateKey, String password) {
        return ETHWalletUtil.loadWalletByPrivateKey(privateKey,password);
    }

    @Override
    public ETHWallet loadWalletByMnemonic(String path, List<String> mnemonicList, String password) {
        return ETHWalletUtil.importMnemonic(path,mnemonicList,password);
    }

    @Override
    public ETHWallet loadWalletByKeystore(String keystore, String password) {
        return ETHWalletUtil.loadWalletByKeystore(keystore, password);
    }

    @Override
    public boolean verifyAddress(String address) {
        try {
            new Address(address);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(e.getMessage());
            ToastUtils.showShort("收款地址格式不正确!");
            return false;
        }
    }


//    /**
//     * 新建钱包
//     * @return
//     */
//    @Override
//    public ETHWallet createNewWallet(String walletName, String pwd) {
//        ETHWallet ethWallet = ETHWalletUtils.generateMnemonic(walletName, pwd);
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
//        return null;
//    }
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
//
//    /**
//     * 生成助记词
//     *
//     * @return
//     */
//    private String makeMnemonic() {
//        byte[] initialEntropy = new byte[16];
//        SecureRandom secureRandom = SecureRandomUtils.secureRandom();
//        secureRandom.nextBytes(initialEntropy);
//        return MnemonicUtils.generateMnemonic(initialEntropy);
//    }

}
