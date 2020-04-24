package com.tancheng.carbonchain.activities.asset.wallet.utils;

import org.bitcoinj.crypto.LinuxSecureRandom;

import java.security.SecureRandom;

/**
 * created by tc_collin on 2020/3/18 14:31
 * Description: 安全随机数SecureRandom
 * version: 1.0
 */
public class SecureRandomUtil {

    private static final SecureRandom SECURE_RANDOM;
    private static int isAndroid = -1;

    static {
        if (isAndroidRuntime()) {
            new LinuxSecureRandom();
        }
        SECURE_RANDOM = new SecureRandom();
    }

    private SecureRandomUtil() {
    }

    public static SecureRandom secureRandom() {
        return SECURE_RANDOM;
    }

    static boolean isAndroidRuntime() {
        if (isAndroid == -1) {
            final String runtime = System.getProperty("java.runtime.name");
            isAndroid = (runtime != null && runtime.equals("Android Runtime")) ? 1 : 0;
        }
        return isAndroid == 1;
    }
}
