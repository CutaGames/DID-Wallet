package com.carbonchain.server.service.wallet;
import org.web3j.crypto.MnemonicUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.web3j.crypto.Hash.sha256;

/**
 * created by tc_collin on 2020/3/18 13:50
 * Description: 生成助记词工具类
 * version: 1.0
 */
public class AndroidMnemonicUtils {
    private static final int SEED_ITERATIONS = 2048;//循环计算次数
    private static final int SEED_KEY_SIZE = 512;//密钥长度
    private static final List<String> WORD_LIST = populateWordList();//助记词代词源

    /**
     * The mnemonic must encode entropy in a multiple of 32 bits. With more entropy security is
     * improved but the sentence length increases. We refer to the initial entropy length as ENT.
     * The allowed size of ENT is 128-256 bits.
     * <p>
     * <h3>Mnemonic generation algorithm</h3>
     * Given a randomly generated initial entropy of size ENT, first a checksum is generated by
     * taking the first {@code ENT / 32} bits of its SHA256 hash. This checksum is appended to
     * the end of the initial entropy. Next, these concatenated bits are split into groups of
     * 11 bits, each encoding a number from 0-2047, serving as an index into a wordlist. Finally,
     * we convert these numbers into words and use the joined words as a mnemonic sentence.
     *
     * @param initialEntropy The initial entropy to generate mnemonic from
     * @return The generated mnemonic
     */
    public static String generateMnemonic(byte[] initialEntropy) {
        validateInitialEntropy(initialEntropy);

        int ent = initialEntropy.length * 8;
        int checksumLength = ent / 32;

        byte checksum = calculateChecksum(initialEntropy);
        boolean[] bits = convertToBits(initialEntropy, checksum);

        int iterations = (ent + checksumLength) / 11;
        StringBuilder mnemonicBuilder = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            int index = toInt(nextElevenBits(bits, i));
            mnemonicBuilder.append(WORD_LIST.get(index));

            boolean notLastIteration = i < iterations - 1;
            if (notLastIteration) {
                mnemonicBuilder.append(" ");
            }
        }

        return mnemonicBuilder.toString();
    }

    /**
     * To create a binary seed from the mnemonic, we use the PBKDF2 function with a
     * mnemonic sentence (in UTF-8 NFKD) used as the password and the string "mnemonic"
     * + passphrase (again in UTF-8 NFKD) used as the salt. The iteration count is set
     * to 2048 and HMAC-SHA512 is used as the pseudo-random function. The length of the
     * derived key is 512 bits (= 64 bytes).
     *
     * @param mnemonic   The input mnemonic which should be 128-160 bits in length containing
     *                   only valid words
     * @param passphrase The passphrase which will be used as part of salt for PBKDF2
     *                   function
     * @return Byte array representation of the generated seed
     */
    public static byte[] generateSeed(String mnemonic, String passphrase) {
        validateMnemonic(mnemonic);
        passphrase = passphrase == null ? "" : passphrase;
       return  MnemonicUtils.generateSeed(mnemonic,passphrase);

//        String salt = String.format("mnemonic%s", passphrase);
//        PKCS5S2ParametersGenerator gen = new PKCS5S2ParametersGenerator(new SHA512Digest());
//        gen.init(mnemonic.getBytes(Charset.forName("UTF-8")), salt.getBytes(Charset.forName("UTF-8")), SEED_ITERATIONS);
//
//        return ((KeyParameter) gen.generateDerivedParameters(SEED_KEY_SIZE)).getKey();
    }

    private static void validateMnemonic(String mnemonic) {
        if (mnemonic == null || mnemonic.trim().isEmpty()) {
            throw new IllegalArgumentException("Mnemonic is required to generate a seed");
        }
    }

    private static boolean[] nextElevenBits(boolean[] bits, int i) {
        int from = i * 11;
        int to = from + 11;
        return Arrays.copyOfRange(bits, from, to);
    }

    private static void validateInitialEntropy(byte[] initialEntropy) {
        if (initialEntropy == null) {
            throw new IllegalArgumentException("Initial entropy is required");
        }

        int ent = initialEntropy.length * 8;
        if (ent < 128 || ent > 256 || ent % 32 != 0) {
            throw new IllegalArgumentException("The allowed size of ENT is 128-256 bits of "
                    + "multiples of 32");
        }
    }

    private static boolean[] convertToBits(byte[] initialEntropy, byte checksum) {
        int ent = initialEntropy.length * 8;
        int checksumLength = ent / 32;
        int totalLength = ent + checksumLength;
        boolean[] bits = new boolean[totalLength];

        for (int i = 0; i < initialEntropy.length; i++) {
            for (int j = 0; j < 8; j++) {
                byte b = initialEntropy[i];
                bits[8 * i + j] = toBit(b, j);
            }
        }

        for (int i = 0; i < checksumLength; i++) {
            bits[ent + i] = toBit(checksum, i);
        }

        return bits;
    }

    private static boolean toBit(byte value, int index) {
        return ((value >>> (7 - index)) & 1) > 0;
    }

    private static int toInt(boolean[] bits) {
        int value = 0;
        for (int i = 0; i < bits.length; i++) {
            boolean isSet = bits[i];
            if (isSet) {
                value += 1 << bits.length - i - 1;
            }
        }

        return value;
    }

    private static byte calculateChecksum(byte[] initialEntropy) {
        int ent = initialEntropy.length * 8;
        byte mask = (byte) (0xff << 8 - ent / 32);
        byte[] bytes = sha256(initialEntropy);

        return (byte) (bytes[0] & mask);
    }

    private static List<String> populateWordList() {
       return MnemonicUtils.getWords();

//        try {
//
//            InputStream fis = Utils.getApp().getAssets().open("en-mnemonic-word-list.txt");
//            return readAllLines(fis);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return Collections.emptyList();
    }

    public static List<String> readAllLines(InputStream path) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(path));
        List<String> data = new ArrayList<String>();
        for (String line; (line = br.readLine()) != null; ) {
            data.add(line);
        }
        return data;
    }

}
