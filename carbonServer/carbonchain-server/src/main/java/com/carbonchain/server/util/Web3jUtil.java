package com.carbonchain.server.util;

import java.math.BigInteger;
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;
import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Sign.SignatureData;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.utils.Numeric;
import com.carbonchain.server.dto.AuthorizationKeyPair;

/**
 * 
 * @author panzi
 *
 */
public class Web3jUtil {
	/**
	 * 创建ECKey
	 * @return
	 */
	public static AuthorizationKeyPair createEcKeyPair(){
		try {
			ECKeyPair ecKeyPair = Keys.createEcKeyPair();
			return new AuthorizationKeyPair(ecKeyPair.getPublicKey(), ecKeyPair.getPrivateKey());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 数据签名
	 * @param message
	 * @param privateKey
	 * @return
	 */
	public static String signMsg(byte[] message,String privateKey){
		ECKeyPair ecKeyPair = ECKeyPair.create(new BigInteger(privateKey, 16));
		SignatureData signature = Sign.signMessage(message, ecKeyPair);
		byte[] r = signature.getR();
        byte[] s = signature.getS();
        byte[] v = signature.getV();
        byte[] signatureBytes = ArrayUtils.addAll(ArrayUtils.addAll(r, s), v);
        return Numeric.toHexStringNoPrefix(signatureBytes);
	}
	
	/**
	 * 数据验签
	 * @param signature
	 * @param message
	 * @param publicKey
	 * @return
	 */
	public static boolean verifyMsg(String signature, byte[] message, String publicKey){
		byte[] msgHash = Hash.sha3(message);
		byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
        byte v = signatureBytes[64];
        if (v < 27) {
            v += 27;
        }
        SignatureData sd = new SignatureData(
                v,
                Arrays.copyOfRange(signatureBytes, 0, 32),
                Arrays.copyOfRange(signatureBytes, 32, 64));

        boolean match = false;
        for (int i = 0; i < 4; i++) {
            BigInteger puk = Sign.recoverFromSignature(
                    (byte) i,
                    new ECDSASignature(new BigInteger(1, sd.getR()), new BigInteger(1, sd.getS())),
                    msgHash);

            if (puk != null) {
                if (puk.toString(16).equals(publicKey)) {
                    match = true;
                    break;
                }
            }
        }
        return match;
	}
	
	
	public static void main(String s12[]){
	    try {
	        ECKeyPair ecKeyPair = Keys.createEcKeyPair();
	        BigInteger publicKeyInDec = ecKeyPair.getPublicKey();
	        BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
	        String publicKey = publicKeyInDec.toString(16);
	        String privateKey = privateKeyInDec.toString(16);
	        WalletFile aWallet = Wallet.createLight("123", ecKeyPair);
	        String address = aWallet.getAddress();
	        if (address.startsWith("0x")) {
	            address = address.substring(2).toLowerCase();
	        } else {
	            address = address.toLowerCase();
	        }
	        
	        byte[] b = "123".getBytes("utf-8");
	        SignatureData sign = Sign.signMessage(b, ECKeyPair.create(privateKeyInDec));
	        
	        byte[] r = sign.getR();
	        byte[] s = sign.getS();
	        byte[] v = sign.getV();
	        
	        byte[] s1 =  ArrayUtils.addAll(ArrayUtils.addAll(r, s), v);
	        
	        String message = Numeric.toHexStringNoPrefix(s1);
	        
	        boolean result = verifyMsg(message, "123".getBytes("utf-8"), publicKey);
	        
	        System.out.println(result);
	        System.out.println("签名：" + message);
	        System.out.println("地址：" + "0x" + address);
	        System.out.println("私钥：" + privateKey);
	        System.out.println("公钥：" + publicKey);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private Web3jUtil(){}
}