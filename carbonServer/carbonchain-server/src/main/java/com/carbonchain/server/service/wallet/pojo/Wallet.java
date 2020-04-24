package com.carbonchain.server.service.wallet.pojo;


import com.carbonchain.server.service.wallet.WalletType;
import com.carbonchain.server.service.wallet.WalletCreatorFactory;
import com.carbonchain.server.service.wallet.WalletException;
import com.carbonchain.server.service.wallet.utils.WalletEncryptUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * created by tc_collin on 2020/3/18 13:08
 * Description: 钱包实体
 * version: 1.0
 */
public class Wallet {
    private final String privateKey;
    private final String publicKey;
    private String address;
    private String mnemonic;
    private String walletType;//钱包类型 eth、btc、eos
    private WalletType type;
    private String path;//bip44路径
    private String pwdTips;//密码提示语
    private List<Erc20Coin> coins;
    private boolean isBackup;//是否已备份

    public Wallet(String mnemonic, String privateKey, WalletType type) {
        this.mnemonic = mnemonic;
        this.privateKey = privateKey;
        this.type = type;
        this.publicKey = WalletCreatorFactory.getWalletCreator(type).getPublicKey(privateKey);
        this.address = WalletCreatorFactory.getWalletCreator(type).getAddress(privateKey);
    }


    public static final class Builder {
        private final WalletType mType;
        private String mnemonic = "";
        private String privateKey;
        private Builder(WalletType coinType) {
            this.mType = coinType;
        }

        public static Builder create(WalletType coinType) {
            return new Builder(coinType);
        }

        public Builder mnemonic(String mnemonic) {
            if (!StringUtils.isEmpty(mnemonic)) {
                this.mnemonic = mnemonic;
            }
            return this;
        }

        public Builder privateKey(String privateKey) {
            this.privateKey = privateKey;
            return this;
        }

        public Wallet build() throws WalletException {
            if (StringUtils.isEmpty(mnemonic) && StringUtils.isEmpty(privateKey)) {
                throw new IllegalArgumentException("助记词和私钥不能同时为空");
            }
            try {
                if (StringUtils.isEmpty(privateKey)) {
                    if (!WalletEncryptUtils.isValidMnemonic(mnemonic)) {
                        throw new WalletException("助记词错误");
                    }
                    privateKey = WalletCreatorFactory.getWalletCreator(mType).getPrivateKey(mnemonic);
                }
                return new Wallet(mnemonic, privateKey, mType);

            } catch (Exception e) {
                e.printStackTrace();
                throw new WalletException("导入错误"+e.getMessage());
            }
        }
    }


    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic( String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public WalletType getType() {
        return type;
    }

    public void setType(WalletType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "privateKey='" + privateKey + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", address='" + address + '\'' +
                ", mnemonic='" + mnemonic + '\'' +
                ", type=" + type +
                '}';
    }

}
