package netlib.util;

import android.util.Base64;
import android.util.Log;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author henzil
 * @version create time:2017/8/2_下午4:15
 * @Description 加密解密 方法util
 */

public class EncryptUtil {

    /**
     *
     * ****************** 以下是 AES 加密解密   ************************************
     *
     *
     *
     */

    // 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
    private static String AES_Key = "0123456WhoAreYou";
    private static String ivParameter = "abcd789WhoAreYou";

    // AES 带 IvParameterSpec偏移量值 加密
    public static String encryptAES(String value){
        String result = "";
        try {
            byte[] rawAES = AES_Key.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(rawAES, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes("utf-8"));
            // 此处使用BASE64做转码。
            result = new String(Base64.encode(encrypted,Base64.DEFAULT));
            //            result = new BASE64Encoder().encode(encrypted);
        } catch (Exception e) {
            Log.e("AES加密出错",e.toString());
        }
        return result;
    }

    // AES 解密 带 IvParameterSpec
    public static String decryptAES(String value) {
        String originalString = null;
        try {
            byte[] rawAES = AES_Key.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(rawAES, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = Base64.decode(value, Base64.DEFAULT); //先用base64解密
            //            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(value); //先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            originalString = new String(original, "utf-8");
        } catch (Exception e) {
            Log.e("AES解密出错", e.toString());
        }

        return originalString;
    }

    // AES 不带 IvParameterSpec偏移量值 加密
    public static String encryptAESNoIvParameterSpec(String value){
        String result = "";
        try {
            byte[] rawAES = AES_Key.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(rawAES, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

            byte[] encrypted = cipher.doFinal(value.getBytes("utf-8"));
            // 此处使用BASE64做转码。
            result = new String(Base64.encode(encrypted,Base64.DEFAULT));
        } catch (Exception e) {
            Log.e("AES加密出错",e.toString());
        }
        return result;
    }

    // AES 解密 不带 IvParameterSpec
    public static String decryptAESNoIvParameterSpec(String value){
        String originalString = null;
        try {
            byte[] rawAES = AES_Key.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(rawAES, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = Base64.decode(value, Base64.DEFAULT); //先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            originalString = new String(original, "utf-8");
        } catch (Exception e) {
            Log.e("AES解密出错", e.toString());
        }

        return originalString;
    }


    /**
     *
     * ****************** end AES 加密解密   ************************************
     *
     *
     *
     */


    /**
     *
     * ***********以下是DES 加密 解密   *****************************************************
     *
     *
     */
    // 密钥，长度需要为8的倍数
    private static String DES_KEY = "1234567WhoAreYou";
    private static String DES = "DES";

    public static byte[] encryptDES(byte[] src, byte[] key) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密匙数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密匙工厂，然后用它把DESKeySpec转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);

        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        // 现在，获取数据并加密
        // 正式执行加密操作
        return cipher.doFinal(src);
    }

    /**
     * 解密
     * @param src 数据源
     * @param key 密钥，长度必须是8的倍数
     * @return 返回解密后的原始数据
     * @throws Exception
     */
    public static byte[] decryptDES(byte[] src, byte[] key) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);

        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        // 现在，获取数据并解密
        // 正式执行解密操作
        return cipher.doFinal(src);

    }

    /**
     * 密码解密
     * @param data
     * @return
     * @throws Exception
     */
    public final static String decryptDES(String data) {
        try {
            return new String(decryptDES(hex2byte(data.getBytes()), DES_KEY.getBytes()));
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 密码加密
     * @param password
     * @return
     * @throws Exception
     */
    public final static String encryptDES(String password) {
        try {
            return byte2hex(encryptDES(password.getBytes(), DES_KEY.getBytes()));
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 二行制转字符串
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1){
                hs = hs + "0" + stmp;
            }else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    /**
     *
     * ***********    end  DES 加密 解密   ****************************************
     *
     *
     */



}
