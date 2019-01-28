package com.jusfoun.bigdata;

import android.widget.Toast;

import com.jusfoun.jusfouninquire.InquireApplication;
import com.siccredit.guoxin.R;

/**
 * 吐司
 * 
 * @author liuzx
 * 
 * @since 2016-6-17 10:05:46
 */
public class Toaster {

	private static Toast toast;

	/**
	 * 链接错误，超时...
	 */
	public static void showToastError() {
		if (toast == null) {
			toast = Toast.makeText(InquireApplication.application, R.string.register_login_neterror, Toast.LENGTH_SHORT);
		} else {
			toast.setText(R.string.register_login_neterror);
		}
		toast.show();
	}

	/**
	 * 自定义吐司内容
	 * 
	 * @param message 吐司内容
	 */
	public static void showToast(String message) {
		if (toast == null) {
			toast = Toast.makeText(InquireApplication.application, message, Toast.LENGTH_SHORT);
		} else {
			toast.setText(message);
		}
		toast.show();
	}

	/**
	 * 自定义吐司内容
	 * 
	 * @param resId The resource id of the string resource to use. Can be
	 *            formatted text.
	 * 
	 */
	public static void showToast(int resId) {
		if (toast == null) {
			toast = Toast.makeText(InquireApplication.application, resId, Toast.LENGTH_SHORT);
		} else {
			toast.setText(resId);
		}
		toast.show();
	}
}
