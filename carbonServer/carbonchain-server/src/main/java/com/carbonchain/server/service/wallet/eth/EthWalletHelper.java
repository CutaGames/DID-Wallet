package com.carbonchain.server.service.wallet.eth;

import com.carbonchain.server.service.wallet.WalletType;
import com.carbonchain.server.service.wallet.WalletCreatorFactory;
import com.carbonchain.server.service.wallet.pojo.TransferData;
import com.carbonchain.server.service.wallet.pojo.Wallet;

/**
 * created by tc_collin on 2020/3/18 15:08
 * Description:
 * version: 1.0
 */
public class EthWalletHelper {

    public Wallet createWalletAccount(){

        return WalletCreatorFactory.getWalletCreator(WalletType.eth).createNewWallet();
    }

    public boolean transferToken(TransferData transferData) {
        return WalletCreatorFactory.getChainService(WalletType.eth).transferToken(transferData);
    }

}
