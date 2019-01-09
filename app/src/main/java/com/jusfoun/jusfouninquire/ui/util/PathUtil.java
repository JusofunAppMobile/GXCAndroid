package com.jusfoun.jusfouninquire.ui.util;

import android.content.Context;
import android.os.Environment;

/**
 * @author zhaoyapeng
 * @version create time:17/8/110:03
 * @Email zyp@jusfoun.com
 * @Description ${sd卡路径 工具类}
 */
public class PathUtil {

    public static String FS="/";


    /**
     * 是否安装SD卡
     * true 安装
     * */
    public static boolean isSDCardMounted(){
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
    * SD 根目录
    * */
    public static String getSDKbasePath(){
            return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static String getBasePath(Context mContext){
        String path = "";
        if(isSDCardMounted()){
            path = getSDKbasePath();
        }else{
            path = mContext.getFilesDir().getAbsolutePath();
        }

        return path;
    }


    /**
     *  获取文件路径
     * */
    public static String getFilePath(Context mContext){
        return  getBasePath(mContext)+FS+mContext.getPackageName()+FS;
    }


//    public void writeFiles(String content, String fileName) {
//        // 初始化文件输出流
//        FileOutputStream fileOutputStream = null;
//        try {
//            // 以追加模式打开文件输出流
//            fileOutputStream = openFileOutput(fileName, MODE_APPEND);
//            fileOutputStream.write(content.getBytes());
//            // 关闭文件输出流
//            fileOutputStream.close();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public static String



}
