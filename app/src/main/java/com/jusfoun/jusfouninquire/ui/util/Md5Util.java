package com.jusfoun.jusfouninquire.ui.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @author zhaoyapeng
 * @version create time:15/11/18下午3:38
 * @Email zyp@jusfoun.com
 * @Description ${对字符串 进行MD5加密}
 */
public class Md5Util {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f'};

    // 得到一个url的md5值
    public static String getMD5Str(String url) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(url.getBytes());
            return getFormattedText(digest.digest());
        } catch (NoSuchAlgorithmException ex) {
            return url;
        }
    }

    /**
     * Takes the raw bytes from the digest and formats them correct.
     *
     * @param bytes the raw bytes from the digest.
     * @return the formatted bytes.
     */
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    /**
     * Takes the raw bytes from the digest and formats them correct.
     *
     * @param 位数
     * @return 返回n位数的随机数.
     */
    public static String getRandom(int n){
        String ret = "";
        Random random = new Random();
        for(int i= 0;i < n;i++){
            ret = ret + Math.abs(random.nextInt(10));
        }
        return ret;
    }
}
