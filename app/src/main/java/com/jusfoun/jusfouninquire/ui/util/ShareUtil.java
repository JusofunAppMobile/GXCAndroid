package com.jusfoun.jusfouninquire.ui.util;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.model.ShareModel;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * @author zhaoyapeng
 * @version create time:15/11/25下午11:10
 * @Email zyp@jusfoun.com
 * @Description ${分享 util}
 */
public class ShareUtil {
    private UMShareListener umShareListener;
    private Context mContext;
    private UMShareAPI mShareAPI;



    // 初始化
    public ShareUtil(Context context) {
        mContext = context;
        mShareAPI = UMShareAPI.get(context);
        umShareListener = new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA platform) {
                Toast.makeText(mContext,"分享成功",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {
                Toast.makeText(mContext,"分享失败",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(mContext,"取消分享",
                        Toast.LENGTH_SHORT).show();
            }
        };


    }

    // 微信分享
    public void shareByType(final Context context, ShareModel model,SHARE_MEDIA type) {

        UMWeb umWeb = new UMWeb(model.getUrl());
        umWeb.setTitle(model.getTitle());
        umWeb.setDescription(model.getContent());

        Log.e("tag","title="+model.getTitle() +" "+model.getContent());
        if (!TextUtils.isEmpty(model.getImage())){
            //有图时
            umWeb.setThumb(new UMImage(context, model.getImage()));

        }else {
            //无图时
            umWeb.setThumb(new UMImage(context, R.mipmap.icon_share));
        }

        new ShareAction((Activity) context)
                .setPlatform(type)
                .setCallback(umShareListener)
                .withText(model.getTitle())
                .withMedia(umWeb)
                .share();
    }
//
//    // 朋友圈分享
//    public void addPengyou(final Context context, ShareModel model) {
//        // 设置微信朋友圈分享内容
//        CircleShareContent circleMedia = new CircleShareContent();
//        circleMedia.setShareContent(model.getContent());
//        // 设置朋友圈title
//        circleMedia.setTitle(model.getTitle());
////        Log.e("share",model.getTitle());
//        circleMedia.setTargetUrl(model.getUrl());
//        if (!TextUtils.isEmpty(model.getImage())){
//            //有图时
//            circleMedia.setShareImage(new UMImage(context, model.getImage()));
//        }else {
//            //无图时
//            circleMedia.setShareImage(new UMImage(context, BitmapFactory.decodeResource(context.getResources(),
//                    R.mipmap.icon_share)));
//        }
//
//        mController.setShareMedia(circleMedia);
//        mController.postShare(((Activity) context), SHARE_MEDIA.WEIXIN_CIRCLE, new SnsPostListener() {
//
//            @Override
//            public void onStart() {
//                // Toast.makeText(context,
//                // mContext.getResources().getString(R.string.share_start),
//                // Toast.LENGTH_SHORT)
//                // .show();
//            }
//
//            @Override
//            public void onComplete(SHARE_MEDIA arg0, int arg1, SocializeEntity arg2) {
//                if (arg1 == 200) {
//                    Toast.makeText(context, "分享成功",
//                            Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT)
//                            .show();
//                    mController.getConfig().cleanListeners();
//                    Log.e("tag", "stCode=" + arg1);
//                }
//            }
//        });
//    }
//
//    //    // 新浪分享
//    public void addXinlang(final Context context, ShareModel model) {
//        // mController.getConfig().setSsoHandler(new SinaSsoHandler());
//        SinaShareContent sinaMedia = new SinaShareContent();
//        sinaMedia.setShareContent(model.getContent() + model.getUrl());
//
//        if (!TextUtils.isEmpty(model.getImage())){
//            //有图时
//            sinaMedia.setShareImage(new UMImage(context, model.getImage()));
//        }else {
//            //无图时
//            sinaMedia.setShareImage(new UMImage(context, BitmapFactory.decodeResource(context.getResources(),
//                    R.mipmap.icon_share)));
//        }
//  /*      sinaMedia.setShareImage(new UMImage(context, BitmapFactory.decodeResource(context.getResources(),
//                R.mipmap.icon_share)));*/
//
//        mController.setShareMedia(sinaMedia);
//        mController.postShare(context, SHARE_MEDIA.SINA, new SnsPostListener() {
//
//            @Override
//            public void onStart() {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onComplete(SHARE_MEDIA arg0, int ecode, SocializeEntity arg2) {
//                if (ecode == 200)
//                    Toast.makeText(context, "分享成功",
//                            Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT)
//                            .show();
//                mController.getConfig().cleanListeners();
//            }
//        });
//    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

}
