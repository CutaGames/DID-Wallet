package com.carbonchain.server.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class SecurityUtil {
	
	/** 
     * 3DES加密 
     * @param key 密钥
     * @param srcStr 将加密的字符串 
     * @return 
     * 
     */  
    public static String encode3Des(String key,String srcStr) throws Exception{    
    	byte[] keybyte = hex(key);  
        byte[] src = srcStr.getBytes();  
        //生成密钥    
        SecretKey deskey = new SecretKeySpec(keybyte, "DESede");  
        //加密    
        Cipher c1 = Cipher.getInstance("DESede");  
        c1.init(Cipher.ENCRYPT_MODE, deskey);    
        String pwd = Base64.encodeBase64String(c1.doFinal(src));
        return pwd;
    }

    /** 
     * 3DES解密 
     * @param key 加密密钥
     * @param desStr 解密后的字符串 
     * @return 
     * @throws Exception 
     */   
     public static String decode3Des(String key, String desStr) throws Exception{    
    	 Base64 base64 = new Base64();  
         byte[] keybyte = hex(key);  
         byte[] src = base64.decode(desStr);
         //生成密钥    
         SecretKey deskey = new SecretKeySpec(keybyte, "DESede");    
         //解密
         Cipher c1 = Cipher.getInstance("DESede");    
         c1.init(Cipher.DECRYPT_MODE, deskey);
         String pwd = new String(c1.doFinal(src));  
         return pwd;
     }

    /** 
     * 转换成十六进制字符串 
     * @param username 
     * @return 
     * 
     */  
	public static byte[] hex(String key){    
		String f = DigestUtils.md5Hex(key);    
        byte[] bkeys = new String(f).getBytes();    
        byte[] enk = new byte[24];    
        for (int i = 0;i < 24;i++){    
            enk[i] = bkeys[i];    
        }    
        return enk;
    }

	public static void main(String s[]) throws Exception{
		String se = encode3Des("1232222222222222222222222", "xdgdfgdf双方的发生的");
		System.out.println(decode3Des("1232222222222222222222222", se));
	}
	
	private SecurityUtil(){}
}
