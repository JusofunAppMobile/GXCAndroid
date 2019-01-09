package com.gxc.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * @author liuguangdan
 * @version create at 2019/1/8/008 14:14
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class DESUtils {
    /**
     * 加密
     *
     * @param datasource byte[]
     * @param password   String
     * @return byte[]
     */
    public static String encryptDES(String data, String key) {
        String s = null;
        try {
            if (data != null) {
                // DES算法要求有一个可信任的随机数源
                SecureRandom sr = new SecureRandom();
                // 从原始密钥数据创建DESKeySpec对象
                DESKeySpec desKeySpec;
                desKeySpec = new DESKeySpec(key.getBytes());
                // 创建一个密钥工厂，用它将DESKeySpec转化成SecretKey对象
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
                SecretKey securekey = keyFactory.generateSecret(desKeySpec);
                // Cipher对象实际完成加密操作
                Cipher cipher = Cipher.getInstance("DES");
                // 用密钥初始化Cipher对象
                cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
                // 将加密后的数据编码成字符串
                Base64Utils base64Utils = new Base64Utils();
                s = base64Utils.encode(cipher.doFinal(data.getBytes()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;

    }

    /**
     * 解密
     *
     * @param src      byte[]
     * @param password String
     * @return byte[]
     * @throws Exception
     */
    public static String decryptDES(String data, String key) throws Exception {
        String s = null;
        if (data != null) {
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
            // 创建一个密钥工厂，用它将DESKeySpec转化成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKeySpec);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密钥初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
            // 将加密后的数据解码再解密
            Base64Utils base64Utils = new Base64Utils();
            byte[] buf = cipher.doFinal(base64Utils.decode(data));
            s = new String(buf);
        }
        return s;
    }
}
