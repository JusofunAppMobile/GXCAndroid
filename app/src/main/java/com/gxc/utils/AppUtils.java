package com.gxc.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.gxc.constants.Constants;
import com.gxc.inter.OnSimpleCompressListener;
import com.gxc.model.UserModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import netlib.util.PreferenceUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.gxc.utils.ToastUtils.showHttpError;

/**
 * @author liuguangdan
 * @version create at 2019/1/5/005 11:44
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class AppUtils {

    public final static String TEST_URL = "https://mp.weixin.qq.com/s?__biz=MzUxMDcwMDcyNQ==&mid=2247484087&idx=1&sn=533ba1192a8263b2c19146d546467e5c&chksm=f97fbf6dce08367bedc8cdfbea9da1222e265b6447a1584fbf7e78b5475b76297e05c8b2ceea&scene=0#rd";

    public static List getTestList(Class<?> clazz, int size) {
        try {
            List list = new ArrayList<>();
            for (int i = 0; i < size; i++)
                list.add(clazz.newInstance());
            return list;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取版本号
     */
    public static int getVersionCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }

    /**
     * 获取版本名
     */
    public static String getVersionName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    /**
     * 保留有效位数
     *
     * @param value
     * @param length 有效位长度, 6位以内是保证精度不损失的.
     * @return
     */
    public static String parseDouble(double value, int length) {
        return String.format("%." + length + "f", value);
    }


    public static <V> int getSize(List<V> sourceList) {
        return sourceList == null ? 0 : sourceList.size();
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(Activity activity) {
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 显示软键盘
     */
    public static void showSoftInput(Activity activity) {
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public static void pictureSelect(final Activity activity, boolean enableCrop, int maxSelectNum, List<String> selectList) {
        PictureSelectionModel model = PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .enableCrop(enableCrop)// 是否裁剪 true or false
                .imageSpanCount(3)// 每行显示个数 int
                .maxSelectNum(maxSelectNum);
        if (enableCrop) {
            model.withAspectRatio(1, 1)
                    .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
                    .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                    .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                    .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
            ;
        }
        if (maxSelectNum == 1)
            model.selectionMode(PictureConfig.SINGLE);
        else
            model.selectionMode(PictureConfig.MULTIPLE);

        if (selectList != null && !selectList.isEmpty()) {
            model.selectionMedia(parseToMediaList(selectList));
        }
        model.forResult(PictureConfig.CHOOSE_REQUEST);
    }


    public static void pictureSelect(final Activity activity, boolean enableCrop, int maxSelectNum, List<String> selectList, int requestCode) {
        PictureSelectionModel model = PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .enableCrop(enableCrop)// 是否裁剪 true or false
                .imageSpanCount(3)// 每行显示个数 int
                .maxSelectNum(maxSelectNum);
        if (enableCrop) {
            model.withAspectRatio(1, 1)
                    .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
                    .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                    .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                    .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
            ;
        }
        if (maxSelectNum == 1)
            model.selectionMode(PictureConfig.SINGLE);
        else
            model.selectionMode(PictureConfig.MULTIPLE);

        if (selectList != null && !selectList.isEmpty()) {
            model.selectionMedia(parseToMediaList(selectList));
        }
        model.forResult(requestCode);
    }

    public static void compress(Context context, File file, final OnSimpleCompressListener listener) {
        Luban.with(context)
                .load(file)
                .ignoreBy(100) // 忽略不压缩图片的大小 ，单位kb
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        LogUtils.e("压缩后：" + file.getAbsolutePath() + ",大小" + AppUtils.byteToMB(file.length()));
                        if (listener != null)
                            listener.complete(file.getAbsolutePath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        showHttpError();
                        if (listener != null)
                            listener.complete(null);
                    }
                }).launch();
    }

    public static List<LocalMedia> parseToMediaList(List<String> list) {
        List<LocalMedia> mediaList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (String s : list) {
                if (!TextUtils.isEmpty(s)) {
                    LocalMedia localMedia = new LocalMedia();
                    localMedia.setPath(s);
                    mediaList.add(localMedia);
                }
            }
        }
        return mediaList;
    }

    /**
     * 流量格式转化
     *
     * @param size
     * @return
     */
    public static String byteToMB(long size) {
        long kb = 1024;
        long mb = kb << 10;
        long gb = mb << 10;
        if (size >= gb)
            return parseDouble((float) size / gb, 2) + "G";
        else if (size >= mb)
            return parseDouble((float) size / mb, 2) + "M";
        else if (size > kb)
            return parseDouble((float) size / kb, 0) + "K";
        else
            return parseDouble((float) size, 0) + "B";
    }

    public static UserModel getUser() {
        String user = PreferenceUtils.getString(InquireApplication.application, Constants.USER);
        if (!TextUtils.isEmpty(user))
            return new Gson().fromJson(user, UserModel.class);
        return null;
    }

    public static void uploadPicture(String path, String type) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder
                .addFormDataPart("type", type);
        File file = new File(path);
        builder.addFormDataPart("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        RxManager.http(RetrofitUtils.getApi().upload(builder.build()), new ResponseCall() {

            @Override
            public void success(NetModel model) {
            }

            @Override
            public void error() {
                showHttpError();
            }
        });
    }

    public static void logout() {
        PreferenceUtils.setString(InquireApplication.application, Constants.USER, null);
    }
}
