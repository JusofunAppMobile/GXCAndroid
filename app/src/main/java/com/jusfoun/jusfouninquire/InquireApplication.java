package com.jusfoun.jusfouninquire;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gxc.model.UserModel;
import com.gxc.utils.AppUtils;
import com.iflytek.cloud.SpeechUtility;
import com.jusfoun.jusfouninquire.net.model.AreaModel;
import com.jusfoun.jusfouninquire.net.model.UserInfoModel;
import com.jusfoun.jusfouninquire.ui.util.LibIOUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * @author zhaoyapeng
 * @version create time:15/11/2上午10:42
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class InquireApplication extends MultiDexApplication {
    public static Context application = null;
    private static UserInfoModel mUserInfo;
    protected static List<AreaModel> mSearchScopeList;


    /***
     * 寄存整个应用Activity
     **/
    private final Stack<WeakReference<Activity>> activitys = new Stack<WeakReference<Activity>>();

    @Override
    public void onCreate() {
        initXF();
        super.onCreate();
        application = this;
        Fresco.initialize(this);

        SDKInitializer.initialize(this);
        MobclickAgent.openActivityDurationTrack(false);
//        JPushInterface.init(this);
//        if (JPushInterface.isPushStopped(this))
//            JPushInterface.resumePush(this);
        Config.DEBUG = true;
        PlatformConfig.setWeixin(getString(R.string.wx_appid),
                getString(R.string.wx_appSecret));
        //微信 appid appsecret
        PlatformConfig.setSinaWeibo(getString(R.string.sina_appid),
                getString(R.string.sina_appSecret),"http://sns.whalecloud.com");

        boolean debug = getResources().getBoolean(R.bool.debug);

        if (debug) {
            FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                    .showThreadInfo(false)
                    .methodCount(0)
                    .tag("guoxincha")
                    .build();
            com.orhanobut.logger.Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        }
    }

    public static void deleteUserInfo() {
        if (mUserInfo != null) {
            mUserInfo = null;
        }
    }

    public static void setUserInfo(UserInfoModel pUserInfo) {
        mUserInfo = pUserInfo;
    }


    public static UserInfoModel getUserInfo() {
        if (mUserInfo == null) {
            UserModel userModel =  AppUtils.getUser();
            if(userModel!=null){
                // 兼容企信宝userId
                mUserInfo = new UserInfoModel();
                mUserInfo.setUserid(userModel.userId);
                mUserInfo.setCompany(userModel.company);
                return mUserInfo;
            }
        } else {
//            Log.d("TAG", "全局userInfo" + mUserInfo.toString());
        }
        return mUserInfo;
    }



    public static List<AreaModel> getLocationList() {
        if (mSearchScopeList == null) {
            mSearchScopeList = new ArrayList<>();
            try {
                String value = LibIOUtil.convertStreamToJson(application.getResources().openRawResource(R.raw.provice_city_area));
                Type listType = new TypeToken<ArrayList<AreaModel>>() {
                }.getType();
                List<AreaModel> list = new Gson().fromJson(value, listType);
                for (AreaModel model : list) {
                    mSearchScopeList.add(model);
                }

                AreaModel country = new AreaModel();
                country.setName("全国");
                country.setId("0");
                mSearchScopeList.add(0, country);

            } catch (Exception exception) {

            } finally {

            }
            return mSearchScopeList;
        } else {
            return mSearchScopeList;
        }
    }

    private void initXF(){
        // 应用程序入口处调用，避免手机内存过小，杀死后台进程后通过历史intent进入Activity造成SpeechUtility对象为null
        // 如在Application中调用初始化，需要在Mainifest中注册该Applicaiton
        // 注意：此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，请增加参数：SpeechConstant.FORCE_LOGIN+"=true"
        // 参数间使用半角“,”分隔。
        // 设置你申请的应用appid,请勿在'='与appid之间添加空格及空转义符

        // 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误

        SpeechUtility.createUtility(this, "appid=" + getString(R.string.app_id));

        // 以下语句用于设置日志开关（默认开启），设置成false时关闭语音云SDK日志打印
        // Setting.setShowLog(false);
    }

    private static HashMap<String, BDLocation> mCacheLocation = new HashMap<>();

    /**
     * 监听定位获取经纬度
     *
     * @param location
     */
    public static void setLocationData(BDLocation location) {
        mCacheLocation.put("location", location);
    }

    /**
     * 获取缓存定位数据
     *
     * @return
     */
    public static BDLocation getLocationData() {
        return mCacheLocation.get("location");
    }

    /**
     * 将Activity压入Application栈
     *
     * @param task 将要压入栈的Activity对象
     */
    public void pushTask(WeakReference<Activity> task) {
        activitys.push(task);
    }

    /**
     * 将传入的Activity对象从栈中移除
     *
     * @param task
     */
    public void removeTask(WeakReference<Activity> task) {
        activitys.remove(task);
    }

}
