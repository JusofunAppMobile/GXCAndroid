package com.jusfoun.bigdata;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static String toHexString(byte[] b) { // String to byte
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	public static String md5(String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest
					.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			return toHexString(messageDigest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 将字符串转成MD5值
	 *
	 * @param string
	 * @return
	 */
	public static String stringToMD5(String string) {

		byte[] hash;
	    try {
	        hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException("Huh, MD5 should be supported?", e);
	    } catch (UnsupportedEncodingException e) {
	        throw new RuntimeException("Huh, UTF-8 should be supported?", e);
	    }

	    StringBuilder hex = new StringBuilder(hash.length * 2);
	    for (byte b : hash) {
	        if ((b & 0xFF) < 0x10) hex.append("0");
	        hex.append(Integer.toHexString(b & 0xFF));
	    }
	    return hex.toString();
	}

	public static String getFileRootPath() {
		String path = Environment.getExternalStorageDirectory()
				+ java.io.File.separator + "jusfoun";

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return path;
	}
	//转码utf-8
	public static String encodeUTF8(String url)
 	{
		if(url == null || url.equals(""))
		{
			return "";
		}
 		try {
 			return   URLEncoder.encode(url, "utf-8");
 		} catch (UnsupportedEncodingException e) {
 			e.printStackTrace();
 			return "";
 		}
 	}

	public static boolean delImg() {

		String imagePath = getFileRootPath() + java.io.File.separator
				+ "loading";
		File file = new File(imagePath);
		if (file.exists() && file.canRead()) {
			file.delete();
		}
		return true;
	}

	public static Bitmap getImg() {
		String imagePath = getFileRootPath() + java.io.File.separator
				+ "loading";
		File file = new File(imagePath);
		if (file.exists() && file.canRead()) {
			return BitmapFactory.decodeFile(imagePath);
		} else
			return null;
	}

	public static boolean saveJpegImage(Bitmap imageData) {
		String imagePath = getFileRootPath() + java.io.File.separator
				+ "loading";
		File f = new File(imagePath);
		if (f.exists()) {
			f.delete();
			return false;
		}
		try {
			f.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		imageData.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	//
	// if (convertView == null) {
	// convertView = LayoutInflater.from(context)
	// .inflate(R.layout.banana_phone, parent, false);
	// }
	//
	// ImageView bananaView = ViewHolder.get(convertView, R.id.banana);
	// TextView phoneView = ViewHolder.get(convertView, R.id.phone);
	//
	// BananaPhone bananaPhone = getItem(position);
	// phoneView.setText(bananaPhone.getPhone());
	// bananaView.setImageResource(bananaPhone.getBanana());
	//
	// return convertView;
	// }

	/**
	 * adapter viewHolder 优化方法
	 * 
	 * @param view
	 *            getView方法传递的参数view
	 * @param id
	 *            需要获取的view Id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends View> T getViewHolderView(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}

	/**
	 * 获取程序版本Code
	 * 
	 * @param ct
	 * @return
	 */
	public static int getVersionCode(Context ct) {
		try {
			PackageManager packageManager = ct.getPackageManager();
			// getPackageName()是当前类的包名，0代表是获取版本信�?
			PackageInfo packInfo = packageManager.getPackageInfo(
					ct.getPackageName(), 0);
			return packInfo.versionCode;
		} catch (Exception e) {
			return 1;
		}
	}
	/**
	 * 获取程序版本名称?
	 * 
	 * @param ct
	 * @return
	 */
	public static String getVersionName(Context ct) {
		try {
			PackageManager packageManager = ct.getPackageManager();
			// getPackageName()是当前类的包名，0代表是获取版本信�?
			PackageInfo packInfo = packageManager.getPackageInfo(
					ct.getPackageName(), 0);
			return packInfo.versionName;
		} catch (Exception e) {
			return "1.1.1";
		}
	}

	public void getSingInfo(Context ct) {
		try {

			PackageManager packageManager = ct.getPackageManager();
			// getPackageName()是当前类的包名，0代表是获取版本信�?
			PackageInfo packageInfo = packageManager.getPackageInfo(
					ct.getPackageName(), PackageManager.GET_SIGNATURES);

			// PackageInfo packageInfo = getPackageManager().getPackageInfo(
			// "wabao.et.master", PackageManager.GET_SIGNATURES);
			Signature[] signs = packageInfo.signatures;
			// Signature sign = signs[0];
			// parseSignature(sign.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 读取assets下的某文件内容返回String
	 * @param ct
	 * @param fileName
	 * @return
	 */
	public static String getResData(Context ct, String fileName) {
		String data = "";
		try {
			// Return an AssetManager instance for your application's package
			InputStream is = ct.getAssets().open(fileName);
			int size = is.available();

			// Read the entire asset into a local byte buffer.
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();

			// Convert the buffer into a string.
			data = new String(buffer, "UTF-8");

			// Finally stick the string into the text view.
		} catch (IOException e) {
			// Should never happen!
			throw new RuntimeException(e);
		}
//		System.out.println("data=" + data);
		return data;
	}

	// public void parseSignature(byte[] signature) {
	// try {
	// CertificateFactory certFactory = CertificateFactory
	// .getInstance("X.509");
	// X509Certificate cert = (X509Certificate) certFactory
	// .generateCertificate(new ByteArrayInputStream(signature));
	// String pubKey = cert.getPublicKey().toString();
	// String signNumber = cert.getSerialNumber().toString();
	// testTextView.setText("key="+pubKey+"\nsign="+signNumber);
	//
	// } catch (CertificateException e) {
	// e.printStackTrace();
	// }
	// }

	public static Bitmap getBitmapFromView(View view) {
		view.destroyDrawingCache();
		view.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = view.getDrawingCache(true);
		return bitmap;
	}

	public static final void showSimpleDialog(Context ct, String info) {
		Toast t = Toast.makeText(ct, info, Toast.LENGTH_LONG);
		t.show();
	}
	public static final void showSimpleDialog1(Context ct, String info) {
		Toast t = Toast.makeText(ct, info, Toast.LENGTH_SHORT);
		t.show();
	}

	public static final void showSimpleDialog(Context ct, int id) {
		Toast t = Toast.makeText(ct, ct.getResources().getString(id),
				Toast.LENGTH_LONG);
		t.show();
	}
}
