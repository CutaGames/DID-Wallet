package com.tancheng.carbonchain.activities.asset.wallet.service;
import com.tancheng.carbonchain.activities.asset.wallet.db.entity.ETHWallet;

import java.util.List;

/**
 * created by tc_collin on 2020/3/18 13:07
 * Description: 钱包服务接口
 * version: 1.0
 */
public interface IwalletService {
    //创建钱包
    ETHWallet createNewWallet(String walletName,String pwd);
    //修改钱包名称
    ETHWallet modifyName(long walletId, String newName);
    //修改密码
    ETHWallet modifyPassword(long walletId, String oldPassword, String newPassword);
    //删除钱包
    boolean deleteWallet(long walletId);
    //导出钱包
    String derivePrivateKey(long walletId, String pwd);
    String deriveKeystore(long walletId, String pwd);
    String deriveMnemonic(long walletId, String pwd);
    //导入钱包
    ETHWallet loadWalletByPrivatekey(String privateKey,String password);
    ETHWallet loadWalletByMnemonic(String path, List<String> mnemonicList, String password);
    ETHWallet loadWalletByKeystore(String keystore, String password);
    //验证地址格式
    boolean verifyAddress(String address);

}
