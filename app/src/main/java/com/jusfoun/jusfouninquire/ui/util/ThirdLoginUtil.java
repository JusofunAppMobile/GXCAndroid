package com.jusfoun.jusfouninquire.ui.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.umeng.socialize.Config;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * @author zhaoyapeng
 * @version create time:15/11/26上午9:52
 * @Email zyp@jusfoun.com
 * @Description ${第三方登录 util}
 */
public class ThirdLoginUtil {

    public static ThirdLoginUtil thirdLoginUtil = null;
    private Context mContext;
    private String UID = "uid";
    private String SCREEN_NAME = "screen_name";
    private String PROFILE_IMAGE_URL = "profile_image_url";
    private String UNIONID = "unionid";
    private String NICKNAME = "nickname";
    private String HEADIMGURL = "headimgurl";
    private String OPENID = "openid";

    private String NICKNAME_WX = "name";
    private String HEADIMGURL_WX = "iconurl";


    private UMShareAPI mShareAPI;

    public static ThirdLoginUtil getThirdLoginUtil(Context mContext) {
        if (thirdLoginUtil == null) {
            thirdLoginUtil = new ThirdLoginUtil(mContext);
        }
        return thirdLoginUtil;
    }

    public ThirdLoginUtil(Context mContext) {
        this.mContext = mContext;
        mShareAPI = UMShareAPI.get(mContext);
//        Config.isNeedAuth = true;
//        mShareAPI.setShareConfig(new UMShareConfig().isNeedAuthOnGetUserInfo(true));

    }


    public void thirdLogin(final Callback callback, final SHARE_MEDIA type) {

        if( SHARE_MEDIA.WEIXIN==type&& !mShareAPI.isInstall((Activity) mContext,SHARE_MEDIA.WEIXIN)){
            Toast.makeText(mContext,"您没有安装微信",Toast.LENGTH_SHORT).show();
           return;
        }
        if( SHARE_MEDIA.WEIXIN==type){
            mShareAPI.deleteOauth((Activity)mContext, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                @Override
                public void onStart(SHARE_MEDIA share_media) {

                }

                @Override
                public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
//                    Toast.makeText(mContext,"删除成功.", Toast.LENGTH_SHORT).show();
                    thirdLoginDetail(callback,type);
                }

                @Override
                public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                }

                @Override
                public void onCancel(SHARE_MEDIA share_media, int i) {

                }
            });
        }else{
            thirdLoginDetail(callback,type);
        }



    }

    private void thirdLoginDetail(final Callback callback, final SHARE_MEDIA type){
        mShareAPI.doOauthVerify((Activity) mContext, type, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int i, Map<String, String> map) {
                Log.e("tag","thirdLoginDetailthirdLoginDetail2="+map);
                if (map != null) {
//                    Toast.makeText(mContext, "授权成功.", Toast.LENGTH_SHORT).show();
                    mShareAPI.getPlatformInfo((Activity) mContext, type, new UMAuthListener() {
                        @Override

                        public void onStart(SHARE_MEDIA share_media) {

                        }

                        @Override
                        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                            Log.e("tag","thirdLoginDetailthirdLoginDetail3="+map);
                            if (map != null) {
                                if (type == SHARE_MEDIA.SINA) {
                                    callback.callback(map.get(UID) + "", map.get(SCREEN_NAME) + "", map.get(PROFILE_IMAGE_URL) + "", "");
                                } else {


                                    String nickName = "";
                                    String headImg = "";
                                    if (TextUtils.isEmpty(map.get(NICKNAME))) {
                                        nickName = map.get(NICKNAME_WX) + "";
                                    } else {
                                        nickName = map.get(NICKNAME) + "";
                                    }

                                    if (TextUtils.isEmpty(map.get(HEADIMGURL))) {
                                        headImg = map.get(HEADIMGURL_WX) + "";
                                    } else {
                                        headImg = map.get(HEADIMGURL) + "";
                                    }
                                    callback.callback(map.get(OPENID) + "", nickName, headImg, map.get(UNIONID) + "");
                                }
                            }
                        }

                        @Override
                        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                            Log.e("tag","thirdLoginDetailthirdLoginDetail3="+throwable+" "+i);
                        }

                        @Override
                        public void onCancel(SHARE_MEDIA share_media, int i) {

                        }
                    });
                } else {
                    Toast.makeText(mContext, "授权失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.e("tag", "throwable=" + throwable);
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.e("tag","thirdLoginDetailthirdLoginDetail3");
            }
        });
    }

    public interface Callback {
        void callback(String openid, String nickname, String headImg, String uid);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }
}
