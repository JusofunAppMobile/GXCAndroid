package com.jusfoun.jusfouninquire.net.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageUtil {

	public static final int DEFAULT_MAX_WIDTH = 320;
	public static final int DEFAULT_MAX_HEIGHT = 480;

	//
	// public static Bitmap rotateBitmap(Bitmap bitmap, float degrees) {
	// if (bitmap == null)
	// return null;
	// if (bitmap.isRecycled())
	// return null;
	// Matrix matrix = new Matrix();
	// matrix.postRotate(degrees);
	//
	// Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
	// bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	// if (resizedBitmap != bitmap) {
	// bitmap.recycle();
	// bitmap = resizedBitmap;
	// }
	// return bitmap;
	// }

	public static Bitmap getBitmapFromMedia(Context context, String pathName) {
		return getBitmapFromMedia(context, pathName, DEFAULT_MAX_WIDTH, DEFAULT_MAX_HEIGHT);
	}

	//
	public static Bitmap getBitmapFromMedia(Context context, String pathName, int maxWidth, int maxHeight) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		try {
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(pathName, options);
			options.inJustDecodeBounds = false;

			int outputWidth = options.outWidth;
			int outputHeight = options.outHeight;
			// Log.e("ImageUtil", "&&&&&&&&pathName = " + pathName +
			// " outputHeight = " + outputHeight);
			if (maxWidth <= 0) {
				maxWidth = DEFAULT_MAX_WIDTH;
			}
			if (maxHeight <= 0) {
				maxHeight = DEFAULT_MAX_HEIGHT;
			}
			if (outputWidth < maxWidth && outputHeight < maxHeight) {
				bitmap = BitmapFactory.decodeFile(pathName);
			} else {
				int inSampleSize = 0;
				int widthSmapleSize = (int) (outputWidth / maxWidth);
				int heightSmapleSize = (int) (outputHeight / maxHeight);
				if (widthSmapleSize >= heightSmapleSize) {
					inSampleSize = widthSmapleSize;
				} else {
					inSampleSize = heightSmapleSize;
				}
				options.inSampleSize = inSampleSize;
				bitmap = BitmapFactory.decodeFile(pathName, options);
			}

		} catch (OutOfMemoryError oom) {
			Log.e("ImageUtil", oom.getMessage(), oom);
			System.gc();
			return null;
		} catch (Exception e) {
			Log.e("ImageUtil", e.getMessage(), e);
			return null;
		}
		return bitmap;
	}

	// public static Bitmap drawableToBitmap(Drawable drawable) {
	// Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
	// drawable.getIntrinsicHeight(),
	// drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 :
	// Bitmap.Config.RGB_565);
	// Canvas canvas = new Canvas(bitmap);
	// drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
	// drawable.getIntrinsicHeight());
	// drawable.draw(canvas);
	// return bitmap;
	// }

	public static int getBitmapByteCount(Bitmap bitmap) {
		if (bitmap == null || bitmap.isRecycled()) {
			return 0;
		}
		return bitmap.getRowBytes() * bitmap.getHeight();
	}

	// public static String getPathName(String url) {
	// return url.substring(url.lastIndexOf("/") + 1);
	// }
	//
	// public static String getExtentionName(String url) {
	// if ((url != null) && (url.length() > 0)) {
	// int dot = url.lastIndexOf('.');
	// if ((dot > -1) && (dot < (url.length() - 1))) {
	// return url.substring(dot + 1);
	// }
	// }
	// return null;
	// }
	//
	// public static Bitmap.CompressFormat getImageType(String imageType) {
	// if (imageType.equalsIgnoreCase("png")) {
	// return Bitmap.CompressFormat.PNG;
	// } else if (imageType.equalsIgnoreCase("jpg")) {
	// return Bitmap.CompressFormat.JPEG;
	// } else {
	// return Bitmap.CompressFormat.PNG;
	// }
	// }

	public static boolean compressBitmap(Bitmap bitmap, Bitmap.CompressFormat compressFormat, String compressPath) {
		if (bitmap == null)
			return false;
		OutputStream fileOutputStream = null;
		File file = new File(compressPath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			fileOutputStream = new FileOutputStream(file);

			bitmap.compress(compressFormat, 100, fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static Bitmap getRoundCornerBitmap(Bitmap bitmap, float roundPX) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Bitmap bitmap2 = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap2);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, width, height);
		final RectF rectF = new RectF(rect);

		paint.setColor(color);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawRoundRect(rectF, roundPX, roundPX, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return bitmap2;
	}

	@SuppressWarnings("unused")
	public static Bitmap copressImage(String imgPath) {
		Bitmap bmap = null;
		File picture = new File(imgPath);
		Options bitmapFactoryOptions = new BitmapFactory.Options();
		// 下面这个设置是将图片边界不可调节变为可调节
		bitmapFactoryOptions.inJustDecodeBounds = true;
		bitmapFactoryOptions.inSampleSize = 2;
		int outWidth = bitmapFactoryOptions.outWidth;
		int outHeight = bitmapFactoryOptions.outHeight;
		bmap = BitmapFactory.decodeFile(picture.getAbsolutePath(), bitmapFactoryOptions);
		float imagew = 150;
		float imageh = 150;
		int yRatio = (int) Math.ceil(bitmapFactoryOptions.outHeight / imageh);
		int xRatio = (int) Math.ceil(bitmapFactoryOptions.outWidth / imagew);
		if (yRatio > 1 || xRatio > 1) {
			if (yRatio > xRatio) {
				bitmapFactoryOptions.inSampleSize = yRatio;
			} else {
				bitmapFactoryOptions.inSampleSize = xRatio;
			}

		}
		bitmapFactoryOptions.inJustDecodeBounds = false;
		try {
			bmap = BitmapFactory.decodeFile(picture.getAbsolutePath(), bitmapFactoryOptions);
		} catch (OutOfMemoryError e) {
			System.gc();
			return null;
		}

		if (bmap != null) {
			// ivwCouponImage.setImageBitmap(bmap);
			return bmap;
		}
		return null;
	}
	
	// 将Bitmap存入SD卡
	public static File saveBitmapToSD(Bitmap mBitmap, String imagePath) {
		File f = new File(imagePath, System.currentTimeMillis()+".png");
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
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
		return f;
	}
}
