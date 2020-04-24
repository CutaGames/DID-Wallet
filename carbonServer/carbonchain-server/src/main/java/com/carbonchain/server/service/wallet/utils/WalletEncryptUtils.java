package com.carbonchain.server.service.wallet.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by jackQ on 2018/6/12.
 */
@Slf4j
public class WalletEncryptUtils {
    private static final String CipherMode = "AES/CFB/NoPadding";
    private static String PUBLIC_KEY = "MIIBtjCCASsGByqGSM44BAEwggEeAoGBANUM+pyh6bWPgS963DYk+Ba606hqnO8MoC/U7wFmfYLh";

    /**
     * 加密
     * @param data
     * @param walletId
     * @param passwd
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String walletId, String passwd) throws Exception {
//        if (StringUtils.isEmpty(data)) {
//            return data;
//        }
//        byte[] bytesDataAES = data.getBytes("UTF-8");
//        Pair<byte[], byte[]> firstTimeEncryptKey = genKey(passwd);
//        Pair<byte[], byte[]> secondTimeKey = genKey( EncryptUtils.encryptMD5ToString(walletId + PUBLIC_KEY));
//        byte[] firstEncryptData = EncryptUtils.encryptAES(bytesDataAES, firstTimeEncryptKey.first,
//                CipherMode, firstTimeEncryptKey.second);
//        byte[] encrypted = EncryptUtils.encryptAES(firstEncryptData, secondTimeKey.first, CipherMode, secondTimeKey.second);
//        return Base64.encodeToString(encrypted, Base64.NO_WRAP);
        //todo 加密
        return "";
    }


    /**
     * 解密
     * @param origin
     * @param walletId
     * @param passwd
     * @return
     * @throws Exception
     */
    public static String decrypt(String origin, String walletId, String passwd) throws Exception {
//        if (StringUtils.isEmpty(origin)) {
//            return origin;
//        }
//        byte[] originData = Base64.decode(origin, Base64.NO_WRAP);
//        Pair<byte[], byte[]> first = genKey(EncryptUtils.encryptMD2ToString(walletId + PUBLIC_KEY));
//        Pair<byte[], byte[]> second = genKey(passwd);
//        byte[] firstEncryptData = EncryptUtils.decryptAES(originData, first.first, CipherMode, first.second);
//        return new String(EncryptUtils.decryptAES(firstEncryptData, second.first, CipherMode, second.second), "UTF-8");
        //todo 解密
        return "";
    }

//    private static Pair<byte[], byte[]> genKey(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
//        byte[] encrypt = EncryptUtils.encryptSHA384(password.getBytes("UTF-8"));
//        byte[] key = Arrays.copyOfRange(encrypt, 0, 32);
//        byte[] iv = Arrays.copyOfRange(encrypt, 32, 48);
//        return new Pair<>(key, iv);
//    }

    /**
     * 计算钱包id
     * @param address
     * @return
     */
    public static String getWalletId(String address) {
        try {
            return EncryptUtils.encryptMD5ToString(address);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getWalletId Error:",e.getMessage());
        }
        //todo 计算钱包id
          return null;
    }

    public static boolean isValidMnemonic(String mnemonic) {
        if (StringUtils.isEmpty(mnemonic)) {
            return false;
        }

        String[] words = mnemonic.split(" ");
        if (words.length != 12) {
            return false;
        }
        return  true;
    }
}
