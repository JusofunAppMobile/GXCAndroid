package com.jusfoun.jusfouninquire.ui.activity;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.AppModel;
import com.jusfoun.jusfouninquire.net.model.AppRecommendModel;
import com.jusfoun.jusfouninquire.net.model.CheckVersionModel;
import com.jusfoun.jusfouninquire.net.route.PersonCenterHelper;
import com.jusfoun.jusfouninquire.ui.util.AppUtil;
import com.jusfoun.jusfouninquire.ui.view.MarqueeVerticalView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author lee
 * @version create time:2015/11/1015:32
 * @Email email
 * @Description $关于我们
 */

public class AboutOurActivity extends BaseInquireActivity {
    /**常量*/


    /**组件*/
    private Button checkVersionBtn;
    private RelativeLayout common_question,pushToFriendLayout;
    private MarqueeVerticalView paomaView;
    /**变量*/


    /**对象*/
    private LayoutInflater inflater;

    @Override
    protected void initData() {
        super.initData();
//        ShareSDK.initSDK(mContext);
//        inflater = getLayoutInflater().from(mContext);


    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_search);
//        checkVersionBtn = (Button) findViewById(R.id.checkVersionBtn);
//        common_question = (RelativeLayout) findViewById(R.id.common_question);
//        paomaView = (MarqueeVerticalView) findViewById(R.id.PaomaView);
//        pushToFriendLayout = (RelativeLayout) findViewById(R.id.pushToFriendLayout);
//        setAppData();
    }


    private void setAppData(){
        List<AppModel> list = new ArrayList<AppModel>();
        for(int i = 0;i < 3;i++){
            AppModel model = new AppModel();
            model.setAppicon("http://img4.imgtn.bdimg.com/it/u=1007848385,111971879&fm=15&gp=0.jpg");
            model.setAppintro("这是一款很特别的应用，虽然不知道干什么"+(i*3));
            model.setAppname("XX宝"+(i*3));
            list.add(model);
        }
        setView(list);
    }

    @Override
    protected void initWidgetActions() {
//        common_question.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goActivity(CommonProblemActivity.class);
//            }
//        });
//
//        checkVersionBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //CheckVersion();
//            }
//        });
//
//        pushToFriendLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
//                dialog.setTitle("分享到");
//                dialog.setMessage("三个分享口");
//                dialog.setPositiveButton("微博", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
////                        showShareWeibo();
//                    }
//                });
//                dialog.setNegativeButton("微信朋友圈", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
////                        shareToWechat();
//                    }
//                });
//                dialog.setNeutralButton("微信好友", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
////                        shareToWeWechatMoments();
//                    }
//                });
//                dialog.show();
//            }
//        });

    }

    /**
     * 检查更新
     */
    private  void CheckVersion(){
        String versionName = AppUtil.getVersionName(mContext);
        int versionCode = AppUtil.getVersionCode(mContext);
        HashMap<String,String> map = new HashMap<>();
        map.put("versionname", versionName);
        map.put("versioncode", versionCode + "");

        PersonCenterHelper.doNetGetCheckVersion(mContext, map,getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                CheckVersionModel model = (CheckVersionModel) data;
                if (model.getResult() == 0) {

                }


            }

            @Override
            public void onFail(String error) {

            }
        });

    }

    //获取推荐应用
    private void getRecommonedApp(){
        PersonCenterHelper.doNetGetRecommonApp(mContext, null,getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                AppRecommendModel model = (AppRecommendModel) data;
                if (model.getResult() == 0) {
                    if (model.getApprecommenlist() != null) {
                        if (model.getApprecommenlist().size() > 0) {
                            //setView(model.getApprecommenlist());
                        }
                    }
                }
            }

            @Override
            public void onFail(String error) {

            }
        });
    }


    private void setView(List<AppModel> list){
        if(list.size()==0){
            paomaView.setVisibility(View.GONE);
        }else{
            paomaView.setVisibility(View.VISIBLE);
        }
        List<View> views = new ArrayList<View>();
        for(int i = 0; i < list.size(); i++){
            AppModel model = list.get(i);
            View view = inflater.inflate(R.layout.focus_company_item_layout,null);
            SimpleDraweeView imageview = (SimpleDraweeView) view.findViewById(R.id.image_icon);
            imageview.setImageURI(Uri.parse(model.getAppicon()));
            TextView appName = (TextView) view.findViewById(R.id.appName);
            appName.setText(model.getAppname());
            TextView appDescript = (TextView) view.findViewById(R.id.appDescript);
            appDescript.setText(model.getAppintro());
            view.setTag(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("这是第"+(int)v.getTag()+"个");

                }
            });
            views.add(view);
        }
        paomaView.setDuration(2000);
        paomaView.setFliperViews(views);

    }

//    /**
//     * 演示调用ShareSDK执行分享
//     */
//    public void showShareWeibo() {
//        SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
//        sp.setText("测试的分享文字");
//        sp.setImageUrl("http://img4.imgtn.bdimg.com/it/u=1007848385,111971879&fm=15&gp=0.jpg");
//
//
//        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
//        weibo.setPlatformActionListener(new PlatformActionListener() {
//            @Override
//            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                Log.d("TAG","分享onComplete");
//            }
//
//            @Override
//            public void onError(Platform platform, int i, Throwable throwable) {
//                Log.d("TAG", "分享onError" + platform + "," + i + ",t===" + throwable);
//
//            }
//
//            @Override
//            public void onCancel(Platform platform, int i) {
//                Log.d("TAG","分享onCancel");
//            }
//        }); // 设置分享事件回调
//        // 执行图文分享
//        weibo.share(sp);
//    }
//
//    private  void shareToWechat(){
//        Wechat.ShareParams wchatShare = new Wechat.ShareParams();
//        wchatShare.setShareType(Platform.SHARE_TEXT);
//        wchatShare.setTitle("这是分享的标题");
//        wchatShare.setText("这是分享的内容");
//
//        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
//        wechat.setPlatformActionListener(new PlatformActionListener() {
//            @Override
//            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                Log.d("TAG","分享onComplete");
//            }
//
//            @Override
//            public void onError(Platform platform, int i, Throwable throwable) {
//                Log.d("TAG","分享onError"+platform+","+i+",t==="+throwable);
//
//            }
//
//            @Override
//            public void onCancel(Platform platform, int i) {
//                Log.d("TAG","分享onCancel");
//            }
//        });
//        wechat.share(wchatShare);
//    }
//
//    private  void shareToWeWechatMoments(){
//        WechatMoments.ShareParams wchatShare = new WechatMoments.ShareParams();
//        wchatShare.setShareType(Platform.SHARE_TEXT);
//        wchatShare.setTitle("这是分享的标题");
//        wchatShare.setText("这是分享的内容");
//
//        Platform wechat = ShareSDK.getPlatform(WechatMoments.NAME);
//        wechat.setPlatformActionListener(new PlatformActionListener() {
//            @Override
//            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                Log.d("TAG","分享onComplete");
//            }
//
//            @Override
//            public void onError(Platform platform, int i, Throwable throwable) {
//                Log.d("TAG","分享onError"+platform+","+i+",t==="+throwable);
//
//            }
//
//            @Override
//            public void onCancel(Platform platform, int i) {
//                Log.d("TAG","分享onCancel");
//            }
//        });
//        wechat.share(wchatShare);
//    }


}
