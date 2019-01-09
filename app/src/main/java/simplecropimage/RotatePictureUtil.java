package simplecropimage;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import java.io.IOException;

/**
 * @author zhaoyapeng
 * @version create time:2014-11-28上午9:51:34
 * @Email zhaoyp@witmob.com
 * @Description 旋转图片 （解决三星手机 拍照 默认图不是竖图）
 */
public class RotatePictureUtil {
	/**
	 * 得到 图片旋转 的角度
	 * 
	 * @param filepath
	 * @return
	 */
	private int getExifOrientation(String filepath) {
		int degree = 0;
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(filepath);
		} catch (IOException ex) {
			Log.e("test", "cannot read exif", ex);
		}
		if (exif != null) {
			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
			if (orientation != -1) {
				switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
				}
			}
		}
		return degree;

	}

	public Bitmap getBitmap(String filepath, Bitmap photoViewBitmap) {
		int angle = getExifOrientation(filepath);
		if (angle != 0) { // 如果照片出现了 旋转 那么 就更改旋转度数
			Matrix matrix = new Matrix();
			matrix.postRotate(angle);
			photoViewBitmap = Bitmap.createBitmap(photoViewBitmap, 0, 0, photoViewBitmap.getWidth(),
					photoViewBitmap.getHeight(), matrix, true);
		}
		return photoViewBitmap;
	}

}
