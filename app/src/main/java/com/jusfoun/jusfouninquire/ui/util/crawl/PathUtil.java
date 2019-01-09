package com.jusfoun.jusfouninquire.ui.util.crawl;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

/**
 * @author zhaoyapeng
 * @version create time:17/8/110:03
 * @Email zyp@jusfoun.com
 * @Description ${sd卡路径 工具类}
 */
public class PathUtil {

    public static String FS = "/";
    public static String TXT = "txt";
    public static String ZIP = "zip";


    /**
     * 是否安装SD卡
     * true 安装
     */
    public static boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * SD 根目录
     */
    public static String getSDKbasePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static String getBasePath(Context mContext) {
        String path = "";
        if (isSDCardMounted()) {
            path = getSDKbasePath();
        } else {
            path = mContext.getFilesDir().getAbsolutePath();
        }

        return path;
    }


    /**
     * 获取文件路径
     */
    public static String getFilePath(Context mContext) {
        return getBasePath(mContext) + FS + mContext.getPackageName() + FS + TXT + FS;
    }

    /**
     * 获取ZIP文件路径
     */
    public static String getZipFilePath(Context mContext, String name) {
        return getBasePath(mContext) + FS + mContext.getPackageName() + FS + ZIP + FS + name;
    }

    public static boolean stringToFile(Context mContext, String res, TaskModel model) {
        String filePath = getFilePath(mContext) + System.currentTimeMillis()+".txt";
        boolean flag = true;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            File distFile = new File(filePath);
            if (!distFile.getParentFile().exists())
                distFile.getParentFile().mkdirs();
            bufferedReader = new BufferedReader(new StringReader(res));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile));
            char buf[] = new char[1024];         //字符缓冲区
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
            return flag;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }


}
