package com.jusfoun.jusfouninquire.ui.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.util.UploadUtil;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.widget.LoadingDialog;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import simplecropimage.CropImage;
import simplecropimage.Util;

/**
 * @author henzil
 * @version create time:2014-8-19_下午8:46:04
 * @Description 继承CropImage 保存完图片，并且上传图片，再finish当前的activity。
 */
@SuppressLint("HandlerLeak")
public class CropImageViewAndUpload extends CropImage {

	private static final String TAG = "CropImageViewAndUpload";

	public static final String IMAGE_NET_URL = "image_net_url";

	protected LoadingDialog loadingDialog;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		initDialog();
	}

	private void initDialog() {
		if (loadingDialog == null) {
			loadingDialog = new LoadingDialog(this, R.style.my_dialog);
			loadingDialog.setCancelable(true);
			loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK) {
						if (loadingDialog != null)
							loadingDialog.cancel();
					}
					return true;
				}
			});
			loadingDialog.setCanceledOnTouchOutside(false);
		}
	}

	@Override
	protected void saveOutput(Bitmap croppedImage) {

		if (mSaveUri != null) {
			OutputStream outputStream = null;
			try {
				outputStream = mContentResolver.openOutputStream(mSaveUri);
				if (outputStream != null) {
					croppedImage.compress(mOutputFormat, 90, outputStream);
				}
			} catch (IOException ex) {

				Log.e(TAG, "Cannot open file: " + mSaveUri, ex);
				setResult(RESULT_CANCELED);
				finish();
				return;
			} finally {

				Util.closeSilently(outputStream);
			}
			doUploadImage();

		} else {
			Log.e(TAG, "not defined image url");
			finish();
		}
		croppedImage.recycle();
	}

	private void doUploadImage() {
		Map<String, String> photoMap = new HashMap<String, String>();
		photoMap.put("files", mImagePath);
		postImage(photoMap);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				if (loadingDialog != null && !loadingDialog.isShowing()) {
					loadingDialog.show();
				}
				Map<String, String> photoMap = (Map<String, String>) msg.obj;
				UploadUtil uploadUtil = new UploadUtil(CropImageViewAndUpload.this);
				Map<String, String> reqMap = new HashMap<String, String>();
				if(LoginSharePreference.getUserInfo(CropImageViewAndUpload.this) != null &&
						!TextUtils.isEmpty(LoginSharePreference.getUserInfo(CropImageViewAndUpload.this).getUserid())){
					reqMap.put("userid", LoginSharePreference.getUserInfo(CropImageViewAndUpload.this).getUserid());
				}
				String actionUrl1 = getString(R.string.req_url) + "/api/UploadImage/UpLoadImage";
				uploadUtil.excute(actionUrl1, reqMap, photoMap, new UploadUtil.UploadListener() {

					@Override
					public void Success(String jsonStr) {
						if (loadingDialog != null && loadingDialog.isShowing()) {
							loadingDialog.dismiss();
						}
						// 上传成功
						// 处理返回数据，并注册
						Map<String, String> dataMap = new Gson().fromJson(jsonStr,
								new TypeToken<Map<String, String>>() {
								}.getType());
						String url = dataMap.get("photo");
						// TODO 下面的方法是返回的数据
						Bundle extras = new Bundle();
						Intent intent = new Intent(mSaveUri.toString());
						intent.putExtras(extras);
						intent.putExtra(IMAGE_PATH, mImagePath);
						intent.putExtra(IMAGE_NET_URL, url);
						intent.putExtra(ORIENTATION_IN_DEGREES,
								Util.getOrientationInDegree(CropImageViewAndUpload.this));
						setResult(RESULT_OK, intent);
						finish();
					}

					@Override
					public void Fail(String failInfo) {
						mSaving = false;
						// TODO 上传失败
						if (loadingDialog != null) {
							loadingDialog.dismiss();
						}
						Toast.makeText(CropImageViewAndUpload.this, "上传失败", Toast.LENGTH_SHORT).show();
					}
				});
				break;

			default:
				break;
			}
		}
	};

	/**
	 * 上传图片
	 * */
	private void postImage(Map<String, String> photoMap) {
		Message message = new Message();
		message.what = 0;
		message.obj = photoMap;
		handler.sendMessage(message);
	}

}
