package netlib.util;

import android.widget.Toast;

import com.jusfoun.jusfouninquire.InquireApplication;

import static android.R.attr.duration;
import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * 说明
 *
 * @时间 2017/10/23
 * @作者 LiuGuangDan
 */

public class ToastUtils {

    private static Toast mToast;

    public static void show(String text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void show(String text, int duration) {
        if (text == null)
            text = "NULL";
        if (mToast == null) {
            // 这里使用Application的Context
            mToast = Toast.makeText(InquireApplication.application, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void show(int resid) {
        show(context.getResources().getString(resid));
    }

    public static void show(int resid, int duration) {
        show(context.getResources().getString(resid), duration);
    }

    public static void showHttpError() {
        show("网络连接异常，请检查网络", duration);
    }

}
