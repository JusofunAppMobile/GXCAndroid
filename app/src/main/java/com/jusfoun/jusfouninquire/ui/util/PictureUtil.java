package com.jusfoun.jusfouninquire.ui.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.activity.CropImageViewAndUpload;
import com.jusfoun.jusfouninquire.ui.util.crawl.TakePhotoEvent;
import com.jusfoun.jusfouninquire.ui.widget.PublicDialog;

import de.greenrobot.event.EventBus;
import simplecropimage.CropImage;

public class PictureUtil {
	public static final int REQUEST_CODE_GALLERY = 0x3;
	public static final int REQUEST_CODE_TAKE_PICTURE = 0x4;
	public static final int REQUEST_CODE_CROP_IMAGE = 0x5;
	private Context context;
	private Boolean isUpload = false;
	private int ASPECT_X = 0;
	private int ASPECT_Y = 0;

	public PictureUtil(Context context) {
		this.context = context;
	}

	public void showImageClickDailog(final String currentPath) {

		PublicDialog dialog = new PublicDialog(context, R.style.my_dialog);
		dialog.setText("提示", "请选择头像");
		dialog.setButtonText("拍照", "本地");
		dialog.setListener(new PublicDialog.callBack() {
			
			@Override
			public void onRightClick() {
				// 本地图库
				openGallery();				
			}
			
			@Override
			public void onLeftClick() {
				takePicture(currentPath);
			}
		});
		dialog.show();
//		new AlertDialog.Builder(context).setTitle("选取图片")
//				.setPositiveButton("本地", new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						// 本地图库
//						openGallery();
//					}
//				}).setNegativeButton("拍照", new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						takePicture(currentPath);
//
//					}
//				}).create().show();

	}

	public void takePicture(String currentPath) {
		EventBus.getDefault().post(new TakePhotoEvent(currentPath));
	}

	private void openGallery() {
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		((Activity) context).startActivityForResult(photoPickerIntent, REQUEST_CODE_GALLERY);
	}

	public void startCropImage(String currentPath) {
		Intent intent = null;
		if (isUpload) {
			intent = new Intent(context, CropImageViewAndUpload.class);
		} else {
			intent = new Intent(context, CropImage.class);
		}
		intent.putExtra(CropImage.IMAGE_PATH, currentPath);
		intent.putExtra(CropImage.SCALE, true);
		// 任意形状的
		intent.putExtra(CropImage.ASPECT_X, ASPECT_X);
		intent.putExtra(CropImage.ASPECT_Y, ASPECT_Y);

		((Activity) context).startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
	}

	/**
	 * 两种模式 1.上传图片 2.不需要上传
	 * */
	public void setType(Boolean isUpload) {
		this.isUpload = isUpload;
	}

	public int getASPECT_X() {
		return ASPECT_X;
	}

	public void setASPECT_X(int aSPECT_X) {
		ASPECT_X = aSPECT_X;
	}

	public int getASPECT_Y() {
		return ASPECT_Y;
	}

	public void setASPECT_Y(int aSPECT_Y) {
		ASPECT_Y = aSPECT_Y;
	}

}
