package com.gxc.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.gxc.constants.Constants;
import com.gxc.event.AuthStatusChangeEvent;
import com.gxc.event.LoginChangeEvent;
import com.gxc.inter.OnCallListener;
import com.gxc.inter.OnSimpleCompressListener;
import com.gxc.inter.OnUploadListener;
import com.gxc.model.AddressModel;
import com.gxc.model.UserModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.widgets.MediaGridInset;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.TimeOut;
import com.jusfoun.jusfouninquire.net.model.SearchHistoryItemModel;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.siccredit.guoxin.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import de.greenrobot.event.EventBus;
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

    public final static String TEST_URL = "https://www.jianshu.com/p/2694f9a64502";
    public final static String TEST_IMAGE = "http://t2.hddhhn.com/uploads/tu/201707/6/71.jpg";
    public final static String TEST_APK = "https://download.sj.qq.com/upload/connAssitantDownload/upload/MobileAssistant_1.apk";

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


    public static String getMetaData(String key) {
        try {
            ApplicationInfo appInfo = InquireApplication.application.getPackageManager()
                    .getApplicationInfo(getPackageName(InquireApplication.application),
                            PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(key);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getStatusHeight() {
        int height = 0;
        int resourceId = InquireApplication.application.getApplicationContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = InquireApplication.application.getApplicationContext().getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    /**
     * 获取应用包名
     */
    public static String getPackageName(Context context) {
        String packageName = "";
        try {
            packageName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageName;
    }

    /**
     * 获取应用名称
     *
     * @param context
     * @return
     */
    public static String getApplicationName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        return (String) packageManager.getApplicationLabel(applicationInfo);
    }

    public static String getChannel() {
        return getMetaData("UMENG_CHANNEL");
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


    /**
     * 图片上传
     *
     * @param path
     * @param type
     * @param listener
     */
    public static void uploadPicture(String path, String type, final OnUploadListener listener) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        HashMap<String, Object> map = new HashMap<>();
        TimeOut timeOut = new TimeOut(InquireApplication.application);
        map.put("t", timeOut.getParamTimeMollis() + "");
        map.put("type", type);

        builder.addFormDataPart("data", new Gson().toJson(map));
        builder.addFormDataPart("m", timeOut.MD5GXCtime(new Gson().toJson(map)));

        File file = new File(path);
        builder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        RxManager.http(RetrofitUtils.getApi().upload(builder.build()), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                if (listener != null) {
                    try {
                        listener.complete(model.getDataJSONObject().getString("filehttp"), model.getDataJSONObject().getString("filepath"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void error() {
                if (listener != null)
                    listener.complete(null, null);
            }
        });
    }

    public static void logout() {
        EventBus.getDefault().post(new LoginChangeEvent());
        PreferenceUtils.setString(InquireApplication.application, Constants.USER, null);
    }

    /**
     * 检查用户VIP状态、企业认证状态
     */
    public static void checkUserStatus(final OnCallListener listener) {
        final UserModel user = getUser();
        if (user == null) return;
        HashMap<String, Object> map = new HashMap<>();

        RxManager.http(RetrofitUtils.getApi().getIdentVip(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                if (model.success()) {
                    UserModel tmp = model.dataToObject(UserModel.class);
                    user.authStatus = tmp.authStatus;
                    user.authCompany = tmp.authCompany;
                    user.vipStatus = tmp.vipStatus;
                    PreferenceUtils.setString(InquireApplication.application, Constants.USER, new Gson().toJson(user));
                    int authStatus = user.authStatus;
                    if (authStatus != tmp.authStatus)
                        EventBus.getDefault().post(new AuthStatusChangeEvent());
                }
                if (listener != null)
                    listener.call();
            }

            @Override
            public void error() {
                if (listener != null)
                    listener.call();
            }
        });
    }

    public static Spanned getNumFont(Context context, int num) {
        return Html.fromHtml("共" + context.getResources().getString(R.string.font_color) + num + "</font>条动态");
    }

    public static Spanned getNumFont2(Context context, int num) {
        return Html.fromHtml("数量：" + context.getResources().getString(R.string.font_color) + num + "</font>条");
    }


    public static List<AddressModel> getAddressList(Context context) {
        String result = getAssetJson(context, "provice_city_area.json");
        if (!TextUtils.isEmpty(result)) {
            try {
                JSONArray arr = new JSONArray(result);
                List<AddressModel> list = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < arr.length(); i++) {
                    list.add(gson.fromJson(arr.get(i).toString(), AddressModel.class));
                }
                return list;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getAssetJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static void addItemDecoration(Context context, RecyclerView recyclerView) {
        recyclerView.addItemDecoration(new MediaGridInset(4, DensityUtils.dp2px(context, 0.8f), false));
    }

    public static int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 企信宝的类型 转化成 国信 类型
     * // 1：股东高管 2：主营产品 3：失信查询 4：查税号 5：招聘 6：企业通讯录 7：查关系  8：风险分析   11 ：地址电话 15 ：中标信息  16：裁判文书 17：行政处罚  18：商标查询
     *
     * @param type
     * @return
     */
    public static int parseToGxMenuType(String type) {
        switch (type) {
            case SearchHistoryItemModel.SEARCH_COMMON:
                return 0;
            case SearchHistoryItemModel.SEARCH_SHAREHOLDER:
                return 1;
            case SearchHistoryItemModel.SEARCH_PRODUCT:
                return 2;
            case SearchHistoryItemModel.SEARCH_DISHONEST:
                return 3;
            case SearchHistoryItemModel.SEARCH_TAXID:
                return 4;
            case SearchHistoryItemModel.SEARCH_RECRUITMENT:
                return 5;
            case SearchHistoryItemModel.SEARCH_CONTACT:
                return 6;
            case SearchHistoryItemModel.SEARCH_RELATION:
                return 7;
            case SearchHistoryItemModel.SEARCH_RISK:
                return 8;
            case SearchHistoryItemModel.SEARCH_WINNING_BID:
                return 15;
            case SearchHistoryItemModel.SEARCH_REFEREE:
                return 16;
            case SearchHistoryItemModel.SEARCH_ADMINISTRATIVE:
                return 17;
            case SearchHistoryItemModel.SEARCH_TRADEMARK:
                return 18;

        }
        LogUtils.e(">>>>>>>注意：企信宝的类型 转化成 国信 类型 未匹配");
        return 0;
    }

    /**
     * 国信的类型 转化成 企信宝 类型
     *
     * @param menuType
     * @return
     */
    public static String parseToQxb(int menuType) {
        switch (menuType) {
            case 1:
                return SearchHistoryItemModel.SEARCH_SHAREHOLDER;
            case 2:
                return SearchHistoryItemModel.SEARCH_PRODUCT;
            case 3:
                return SearchHistoryItemModel.SEARCH_DISHONEST;
            case 4:
                return SearchHistoryItemModel.SEARCH_TAXID;
            case 5:
                return SearchHistoryItemModel.SEARCH_RECRUITMENT;
            case 6:
                return SearchHistoryItemModel.SEARCH_CONTACT;
            case 7:
                return SearchHistoryItemModel.SEARCH_RELATION;
            case 8:
                return SearchHistoryItemModel.SEARCH_RISK;
            case 15:
                return SearchHistoryItemModel.SEARCH_WINNING_BID;
            case 16:
                return SearchHistoryItemModel.SEARCH_REFEREE;
            case 17:
                return SearchHistoryItemModel.SEARCH_ADMINISTRATIVE;
            case 18:
                return SearchHistoryItemModel.SEARCH_TRADEMARK;
            default:
                return SearchHistoryItemModel.SEARCH_COMMON;

        }
    }

}
