package com.jusfoun.jusfouninquire.ui.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.jusfoun.jusfouninquire.R;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/12.
 * Description
 */
public class MapUtil {

    public final static String GOOGLE = "com.google.android.apps.maps";
    public final static String BAIDU = "com.baidu.BaiduMap";
    public final static String AUTO = "com.autonavi.minimap";
    public final static String TENCENT = "com.tencent.map";
    public final static String SOGOU = "com.sogou.map.android.maps";
    private static MapUtil mapUtil;
    private Context context;

    private MapUtil(Context context) {
        this.context = context;
    }

    public static MapUtil getInstance(Context context) {
        if (mapUtil == null)
            mapUtil = new MapUtil(context);
        return mapUtil;
    }

    /**
     * 谷歌导航
     *
     * @param fromPoint
     * @param toPoint
     */
    public void startGoogleNavi(LatLng fromPoint, LatLng toPoint) {
        if (fromPoint==null||toPoint==null)
            return;
        String url = String.format(context.getString(R.string.map_google_url)
                , fromPoint.latitude + "," + fromPoint.longitude
                , toPoint.latitude + "," + toPoint.longitude);
//        if (wodeweizhiPoint != null) {
//            if (wodeweizhiPoint.getLatitudeE6() != 0) {
//                float chufajingdu = (float) (wodeweizhiPoint.getLongitudeE6() / 1E6);
//                float chufaweidu = (float) (wodeweizhiPoint.getLatitudeE6() / 1E6);
//                float daodajingdu = (float) (AppConstant.PointZuoBiao.liangxiangDaoHangPoint.getLongitudeE6() / 1E6);
//                float daodaweidu = (float) (AppConstant.PointZuoBiao.liangxiangDaoHangPoint.getLatitudeE6() / 1E6);
        // 标记一个点
        // Intent i = new
        // Intent(Intent.ACTION_VIEW,Uri.parse("http://ditu.google.cn/maps?hl=zh&mrt=loc&q=31.249351,121.45905"));

        // 从哪到哪的路线
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        //如果强制使用googlemap地图客户端打开，就加下面两句
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK & Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        i.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        context.startActivity(i);
//            }
//        } else {
//           // AlertUtils.ToastAlert(mContext, “由于无法获取到您的位置，所以暂时无法提供导航”);
//        }
    }

    /**
     * 百度导航
     *
     * @param fromPoint
     * @param toPoint
     * @param title
     */
    public void startBaiduNavi(LatLng fromPoint, LatLng toPoint, String title) {
        if (fromPoint==null||toPoint==null)
            return;
        Intent intent = null;
        try {
            String url = String.format(context.getString(R.string.map_baidu_url)
                    , fromPoint.latitude + "," + fromPoint.longitude
                    , toPoint.latitude + "," + toPoint.longitude,
                    title);
            intent = Intent.getIntent(url);
            context.startActivity(intent); //启动调用
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 高德导航
     *
     * @param fromPoint
     * @param toPoint
     * @param title
     */
    public void startAutoNavi(LatLng fromPoint, LatLng toPoint, String title) {
        if (fromPoint==null||toPoint==null)
            return;
        String url = String.format(context.getString(R.string.map_auto_url)
                , fromPoint.latitude, fromPoint.longitude
                , toPoint.latitude, toPoint.longitude, title);
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse(url));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }

    /**
     * 腾讯导航
     *
     * @param fromPoint
     * @param toPoint
     * @param title
     */
    public void startTencentNavi(LatLng fromPoint, LatLng toPoint, String title) {
        if (fromPoint==null||toPoint==null)
            return;
        String url = String.format(context.getString(R.string.map_tencent_url)
                , fromPoint.latitude + "," + fromPoint.longitude
                , toPoint.latitude + "," + toPoint.longitude, title);
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse(url));
        intent.setPackage("com.tencent.map");
        context.startActivity(intent);
    }

    /**
     * 搜狗导航
     *
     * @param toPoint
     */
    public void startSogouNavi(LatLng toPoint) {
        if (toPoint==null)
            return;
        Uri uri = Uri.parse("geo:" + toPoint.latitude + "," + toPoint.longitude + "");
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        it.setPackage("com.sogou.map.android.maps");
        context.startActivity(it);
    }

    /**
     * 获取手机上地图app
     *
     * @param list
     */
    public void getMapApplication(List<AppInfo> list) {
        // 获取手机已安装的所有应用package的信息(其中包括用户自己安装的，还有系统自带的)
        if (list == null)
            list = new ArrayList<AppInfo>();
        list.clear();
        List<PackageInfo> packages = context.getPackageManager()
                .getInstalledPackages(0);

        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            AppInfo tmpInfo = new AppInfo();
            tmpInfo.setAppName(packageInfo.applicationInfo.loadLabel(
                    context.getPackageManager()).toString());
            tmpInfo.setPackageName(packageInfo.packageName);
            tmpInfo.setVersionName(packageInfo.versionName);
            tmpInfo.setVersionCode(packageInfo.versionCode);
            tmpInfo.setAppIcon(packageInfo.applicationInfo
                    .loadIcon(context.getPackageManager()));
            // 如果属于非系统程序，则添加到列表显示
            if (SOGOU.equals(packageInfo.packageName)) {
                list.add(tmpInfo);
            } else if (BAIDU.equals(packageInfo.packageName)) {
                list.add(tmpInfo);
            } else if (AUTO.equals(packageInfo.packageName)) {
                list.add(tmpInfo);
            } else if (TENCENT.equals(packageInfo.packageName)) {
                list.add(tmpInfo);
            } else if (GOOGLE.equals(packageInfo.packageName)) {
                tmpInfo.setAppName("谷歌地图");
                list.add(tmpInfo);
            }
        }
    }

    public void getList(List<AppInfo> list) {
        PackageInfo packageInfo = null;

        try {
            packageInfo = context.getPackageManager().getPackageInfo(GOOGLE, 0);
            if (packageInfo != null) {
                AppInfo info = getAppInfo(packageInfo);
                list.add(info);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        try {
            packageInfo = context.getPackageManager().getPackageInfo(BAIDU, 0);
            if (packageInfo != null) {
                AppInfo info = getAppInfo(packageInfo);
                list.add(info);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        try {
            packageInfo = context.getPackageManager().getPackageInfo(AUTO, 0);
            if (packageInfo != null) {
                AppInfo info = getAppInfo(packageInfo);
                list.add(info);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        try {
            packageInfo = context.getPackageManager().getPackageInfo(TENCENT, 0);
            if (packageInfo != null) {
                AppInfo info = getAppInfo(packageInfo);
                list.add(info);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        try {
            packageInfo = context.getPackageManager().getPackageInfo(SOGOU, 0);
            if (packageInfo != null) {
                AppInfo info = getAppInfo(packageInfo);
                list.add(info);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private AppInfo getAppInfo(PackageInfo packageInfo) {
        AppInfo tmpInfo = new AppInfo();
        tmpInfo.setAppName(packageInfo.applicationInfo.loadLabel(
                context.getPackageManager()).toString());
        tmpInfo.setPackageName(packageInfo.packageName);
        tmpInfo.setVersionName(packageInfo.versionName);
        tmpInfo.setVersionCode(packageInfo.versionCode);
        tmpInfo.setAppIcon(packageInfo.applicationInfo
                .loadIcon(context.getPackageManager()));
        return tmpInfo;
    }
}
