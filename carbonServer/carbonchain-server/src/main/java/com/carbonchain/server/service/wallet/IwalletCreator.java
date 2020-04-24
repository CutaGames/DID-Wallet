package com.carbonchain.server.service.wallet;
import com.carbonchain.server.service.wallet.pojo.Wallet;

/**
 * created by tc_collin on 2020/3/18 13:07
 * Description: 钱包创建接口
 * version: 1.0
 */
public interface IwalletCreator {

    Wallet createNewWallet();

    Wallet createWalletFromWords(String mnemonic);

    Wallet createWallet(String privateKey);

    String getPrivateKey(String mnemonic);

    String getAddress(String privateKey);

    String getPublicKey(String privateKey);

}
