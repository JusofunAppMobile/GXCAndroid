package netlib.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author zhaoyapeng
 * @version create time:15/12/1下午3:32
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class SetUtil {
    public static final String PREFERENCES_NAME = "setting_name";
    public static final String WIFI_IMAGE = "wifi_image";
    /**
     * 设置是否在wifi下加载图片
     *
     * */
    public static void setWifiImage(Context context, boolean wifiImage) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppUtil.getPackageName(context)
                + PREFERENCES_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(WIFI_IMAGE, wifiImage).commit();
    }

    /**
     * 获取是否在wifi下获取图片
     *
     * */
    public static boolean getWifiImage(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppUtil.getPackageName(context)
                + PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(WIFI_IMAGE, false);
    }
}
