package com.carbonchain.server.service.wallet.eth;

import com.carbonchain.server.service.wallet.*;
import com.carbonchain.server.service.wallet.WalletType;
import com.carbonchain.server.service.wallet.pojo.Wallet;
import com.carbonchain.server.service.wallet.utils.SecureRandomUtils;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import static org.bitcoinj.crypto.HDUtils.parsePath;

/**
 * created by collin on 2020/3/18 15:17
 * Description: 钱包创建、恢复相关
 * version: 1.0
 */
@Slf4j
public class EthWalletCreator implements IwalletCreator {
    /**
     * 新建钱包
     * @return
     */
    @Override
    public Wallet createNewWallet() {
        Wallet wallet = createWalletFromWords(makeMnemonic());
        System.out.println("钱包创建成功，" + wallet.getMnemonic());
        return wallet;
    }

    /**
     * 通过助记词恢复钱包
     * @param mnemonic
     * @return
     */
    @Override
    public Wallet createWalletFromWords(String mnemonic) {
        try {
            return Wallet.Builder.create(WalletType.eth).mnemonic(mnemonic).build();
        } catch (WalletException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过私钥恢复钱包
     * @param privateKey
     * @return
     */
    @Override
    public Wallet createWallet(String privateKey) {
        return null;
    }

    /**
     * 获取私钥
     * @param mnemonic
     * @return
     */
    @Override
    public String getPrivateKey(String mnemonic) {
        String[] wordArr = mnemonic.split(" ");
        List<String> wordsList = Arrays.asList(wordArr);
        DeterministicSeed deterministicSeed = new DeterministicSeed(wordsList, null, "", 0);
        DeterministicKeyChain deterministicKeyChain = DeterministicKeyChain.builder().seed(deterministicSeed).build();
        BigInteger privateKey = deterministicKeyChain.getKeyByPath(parsePath(convert2bitCoinjPath(WalletType.eth.getPath())), true).getPrivKey();
        return Numeric.toHexStringWithPrefix(privateKey);
    }

    private String convert2bitCoinjPath(String path) {
        return path.toUpperCase().replace("'", "H");
    }

    /**
     * 获取地址
     * @param privateKey
     * @return
     */
    @Override
    public String getAddress(String privateKey) {
        ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
        return Numeric.prependHexPrefix(Keys.getAddress(ecKeyPair));
    }

    /**
     * 获取公钥
     * @param privateKey
     * @return
     */
    @Override
    public String getPublicKey(String privateKey) {
        return Numeric.toHexStringNoPrefix(ECKeyPair.create(Numeric.toBigInt(privateKey)).getPublicKey());
    }

    /**
     * 生成助记词
     *
     * @return
     */
    private String makeMnemonic() {
        byte[] initialEntropy = new byte[16];
        SecureRandom secureRandom = SecureRandomUtils.secureRandom();
        secureRandom.nextBytes(initialEntropy);
        return AndroidMnemonicUtils.generateMnemonic(initialEntropy);
    }

}
