package com.jusfoun.jusfouninquire.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.AppModel;
import com.jusfoun.jusfouninquire.net.model.BottomMenuModel;
import com.jusfoun.jusfouninquire.net.model.CheckVersionModel;
import com.jusfoun.jusfouninquire.net.model.HelpModel;
import com.jusfoun.jusfouninquire.net.model.ShareModel;
import com.jusfoun.jusfouninquire.net.model.VersionModel;
import com.jusfoun.jusfouninquire.net.route.HelpRoute;
import com.jusfoun.jusfouninquire.net.route.PersonCenterHelper;
import com.jusfoun.jusfouninquire.net.update.UpdateServiceHelper;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.service.event.UpdateMainEvent;
import com.jusfoun.jusfouninquire.sharedpreference.CheckVersionSharedPreference;
import com.jusfoun.jusfouninquire.sharedpreference.CompanyCountSharedPreference;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.sharedpreference.TimeSharePreference;
import com.jusfoun.jusfouninquire.ui.activity.CommonProblemActivity;
import com.jusfoun.jusfouninquire.ui.activity.FeedbackActivity;
import com.jusfoun.jusfouninquire.ui.activity.WebActivity;
import com.jusfoun.jusfouninquire.ui.animation.SceneAnimation;
import com.jusfoun.jusfouninquire.ui.util.AppUtil;
import com.jusfoun.jusfouninquire.ui.util.KeyBoardUtil;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;
import com.jusfoun.jusfouninquire.ui.util.ScrollUtil;
import com.jusfoun.jusfouninquire.ui.util.ShareUtil;
import com.jusfoun.jusfouninquire.ui.util.TimerClockUtil;
import com.jusfoun.jusfouninquire.ui.view.CustomScrollView;
import com.jusfoun.jusfouninquire.ui.view.MarqueeVerticalView;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.jusfoun.jusfouninquire.ui.view.PhoneCallDialog;
import com.jusfoun.jusfouninquire.ui.view.SettingBottomView;
import com.jusfoun.jusfouninquire.ui.widget.GeneralDialog;
import com.jusfoun.jusfouninquire.ui.widget.ShareDialog;
import com.jusfoun.library.flipdigit.Flipmeter;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import netlib.util.EventUtils;


/**
 * @author zhaoyapeng
 * @version create time:15/10/30上午10:34
 * @Email zyp@jusfoun.com
 * @Description ${设置fragment}
 */
public class SetFragement extends BaseInquireFragment implements TimerClockUtil.TimerImpl {
    /**常量*/


    /**
     * 组件
     */
    private Button checkVersionBtn;
    private MarqueeVerticalView paomaView;
    private ShareDialog shareDialog;
    private View line3, line4;
    private GeneralDialog generalDialog;

    private TextView mVersion, mDescription;
    private LinearLayout mTopLayout;

    private SettingBottomView mShare, mCommonProblem;

    /**变量*/


    /**
     * 对象
     */
    private LayoutInflater inflater;
    private ShareDialog.ShareListener shareListener;

    private static final int CHANGE_UPDATE_TYPE = 1;
    private static final int MUST_UPDATE_TYPE = 2;

    private int padding = 0;


    private Flipmeter mFlipMeter = null;
    private boolean isFirstIn = false;
    private int remainingTime = 0;
    private TimerClockUtil timeUtil;

    private static final int TOTAL_TIME = 20 * 60 * 1000;
    private static final int INTERVAl_TIME = 1000;

    private RelativeLayout topLayout,titleBarLayout,mCommon_problem,mMassage_return;
    private ImageView topImage;
    private CustomScrollView scrollView;


    private TextView qqText, qqGroupText, mailText, phoneText;
    private NetWorkErrorView mNetError;
    private ScrollUtil scrollUtil;


    private LinearLayout mQQContact,mQQGroupContact,mEmail;


    private ImageView  loadImage;
    private LinearLayout loading;
    private SceneAnimation sceneAnimation;
    private ShareUtil shareUtil;

    public static SetFragement getInstance(int padding) {
        SetFragement setFragement = new SetFragement();
        Bundle bundle = new Bundle();
        bundle.putInt("padding", padding);
        setFragement.setArguments(bundle);
        return setFragement;
    }

    @Override
    protected void initData() {
//        ShareSDK.initSDK(mContext);
        inflater = LayoutInflater.from(mContext);
        shareDialog = new ShareDialog(mContext, R.style.tool_dialog);
        shareDialog.setCancelable(true);
        Window shareWindow = shareDialog.getWindow();
        shareWindow.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        shareWindow.setWindowAnimations(R.style.share_dialog_style); // 添加动画

        generalDialog = new GeneralDialog(mContext, R.style.my_dialog);
        padding = getArguments().getInt("padding", 0);


        timeUtil = TimerClockUtil.getInstance();
        timeUtil.setRemainingTimeListener(this);
        scrollUtil = new ScrollUtil();
        shareUtil = new ShareUtil(mContext);

    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_about_our, container, false);
        checkVersionBtn = (Button) view.findViewById(R.id.checkVersionBtn);
        paomaView = (MarqueeVerticalView) view.findViewById(R.id.PaomaView);
        line3 = view.findViewById(R.id.line3);
        line4 = view.findViewById(R.id.line4);
        mVersion = (TextView) view.findViewById(R.id.versionText);
        mDescription = (TextView) view.findViewById(R.id.app_description);
        mTopLayout = (LinearLayout) view.findViewById(R.id.top_layout);
        mCommon_problem=(RelativeLayout) view.findViewById(R.id.common_problem);
        mMassage_return=(RelativeLayout) view.findViewById(R.id.massage_return);
        mShare = (SettingBottomView) view.findViewById(R.id.default_share);
        mCommonProblem = (SettingBottomView) view.findViewById(R.id.default_common_problem);
        mFlipMeter = (Flipmeter) view.findViewById(R.id.Flipmeter);
        qqText = (TextView) view.findViewById(R.id.text_qq);
        qqGroupText = (TextView) view.findViewById(R.id.text_qq_group);
        mailText = (TextView) view.findViewById(R.id.text_mail);
        phoneText = (TextView) view.findViewById(R.id.text_phone);
        topLayout = (RelativeLayout) view.findViewById(R.id.layout_top);
        topImage = (ImageView) view.findViewById(R.id.image_top);
        scrollView = (CustomScrollView) view.findViewById(R.id.setting_scrollview);
        titleBarLayout = (RelativeLayout)view.findViewById(R.id.layout_titlebar);
        mNetError = (NetWorkErrorView) view.findViewById(R.id.net_work_error);
        mQQContact = (LinearLayout) view.findViewById(R.id.layout_qq);
        mQQGroupContact = (LinearLayout) view.findViewById(R.id.layout_group);
        mEmail = (LinearLayout) view.findViewById(R.id.email);

        loading = (LinearLayout) view.findViewById(R.id.loading);
        loadImage = (ImageView) view.findViewById(R.id.loading_img);
        return view;
    }

    @Override
    protected void initWeightActions() {
        sceneAnimation = new SceneAnimation(loadImage, 75);
        mQQContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventUtils.event(mContext,EventUtils.HELP79);
            }
        });

        mQQGroupContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventUtils.event(mContext,EventUtils.HELP80);
            }
        });
        mEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventUtils.event(mContext,EventUtils.HELP81);
            }
        });

        checkVersionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext,EventUtils.SETTING01);
                CheckVersion();

            }
        });

        mShare.setViewText("推荐给好友");
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext,EventUtils.SETTING02);
                showShareDialog();
            }
        });
        mCommon_problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext,EventUtils.HELP77);
                Intent intent = new Intent(mContext, CommonProblemActivity.class);
                intent.putExtra(CommonProblemActivity.TYPE, CommonProblemActivity.COMMOMPROBLEM_TYPE);
                startActivity(intent);
            }
        });
        mMassage_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext,EventUtils.HELP78);
                Intent intent = new Intent(mContext, FeedbackActivity.class);
                startActivity(intent);
            }
        });
//        mCommonProblem.setViewText("常见问题");
//        mCommonProblem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MobclickAgent.onEvent(mContext, "Setting04");
//                Intent intent = new Intent(mContext, CommonProblemActivity.class);
//                intent.putExtra(CommonProblemActivity.TYPE, CommonProblemActivity.COMMOMPROBLEM_TYPE);
//                startActivity(intent);
//            }
//        });
        phoneText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext,EventUtils.HELP82);
                new PhoneCallDialog(mContext, "4007008501").show();
            }
        });
        shareDialog.setShareListener(new ShareDialog.ShareListener() {
            @Override
            public void onFriendersShare() {
                ShareModel model = new ShareModel();
                model.setContent(mContext.getString(R.string.share_content_setting));
                model.setTitle(mContext.getString(R.string.share_title_setting));
                model.setUrl(mContext.getString(R.string.share_url_seting));
                shareUtil.shareByType(mContext, model, SHARE_MEDIA.WEIXIN_CIRCLE);
            }

            @Override
            public void onWechatShare() {
                ShareModel model = new ShareModel();
                model.setContent(mContext.getString(R.string.share_content_setting));
                model.setTitle(mContext.getString(R.string.share_title_setting));
                model.setUrl(mContext.getString(R.string.share_url_seting));
                shareUtil.shareByType(mContext, model, SHARE_MEDIA.WEIXIN);
            }

            @Override
            public void onSinaShare() {
                ShareModel model = new ShareModel();
                model.setContent(mContext.getString(R.string.share_content_setting));
                model.setTitle(mContext.getString(R.string.share_title_setting));
                model.setUrl(mContext.getString(R.string.share_url_seting));
                shareUtil.shareByType(mContext, model, SHARE_MEDIA.SINA);
            }
        });
        handler.sendEmptyMessageDelayed(1, 400);

        if (CheckVersionSharedPreference.getVersionInfo(mContext) != null) {
            VersionModel model = CheckVersionSharedPreference.getVersionInfo(mContext);
            if (!TextUtils.isEmpty(model.getVersionname())) {
                if (!model.getVersionname().equals(AppUtil.getVersionName(mContext))) {
                    checkVersionBtn.setText("更新(" + model.getVersionname() + "版本 " + model.getUpdatetime() + ")");
                }
            }
        } else {
            checkVersionBtn.setText("检查更新");
        }
        titleBarLayout.setPadding(0, padding, 0, 0);

        mVersion.setText("版本：" + AppUtil.getVersionName(mContext));


        mFlipMeter.setReachGoalListener(new Flipmeter.ReachGoalListener() {
            @Override
            public void onReachGoal() {
                getHelpeInfo(false);
                // 网络请求
            }
        });

        mFlipMeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext,EventUtils.HOMEPAGE11);
            }
        });

        titleBarLayout.getBackground().setAlpha(0);
        scrollView.setCallBack(new CustomScrollView.OnScrollListener() {
            @Override
            public void onScrollChangedListener(int leftOfVisibleView, int topOfVisibleView, int oldLeftOfVisibleView, int oldTopOfVisibleView) {
                int count = topOfVisibleView*2;
                Log.e("tag","topOfVisibleView="+count);
                if (count <= 255) {
                    titleBarLayout.getBackground().setAlpha(count);

                } else {
                    titleBarLayout.getBackground().setAlpha(255);
                }
            }

            @Override
            public void onPullScroll(int height) {
                scrollUtil.imageScale(topLayout, topImage, height, PhoneUtil.dip2px(mContext, 300));
            }
        });


        mNetError.setListener(new NetWorkErrorView.OnRefreshListener() {
            @Override
            public void OnNetRefresh() {
                getHelpeInfo(true);
            }
        });
    }

    /**
     * 检查更新
     */
    private void CheckVersion() {
        showLoading();
        ApplicationInfo appInfo = null;
        String ChannelValue = null;
        try {
            appInfo = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
            ChannelValue = appInfo.metaData.getString("UMENG_CHANNEL");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String versionName = AppUtil.getVersionName(mContext);
        int versionCode = AppUtil.getVersionCode(mContext);
        HashMap<String, String> map = new HashMap<>();
        map.put("versionname", versionName);
        map.put("versioncode", versionCode + "");
        map.put("from", "Android");
        map.put("channel", ChannelValue);

        PersonCenterHelper.doNetGetCheckVersion(mContext, map, ((Activity) mContext).getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                CheckVersionModel model = (CheckVersionModel) data;
                if (model.getResult() == 0) {
                    if (model.getVersionnumber() != null) {
                        if (model.getVersionnumber().getUpdate() == 0) {
                            showToast("当前版本已经是最新版本");
                        } else {
                            CheckVersionSharedPreference.saveVersionInfo(mContext, model.getVersionnumber());
                            checkVersionBtn.setText("更新(" + model.getVersionnumber().getVersionname() + "版本 " + model.getVersionnumber().getUpdatetime() + ")");
                            detailUpdate(model);
                        }

                    }

                } else {
                    showToast(model.getMsg() + "");
                }

                Log.e("tag", "onSuccess1");
//                VersionNumSharePreferences.deleteVerInfo(context);

            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                showToast(R.string.net_error);
            }
        });

    }

    private void setPaoMaViewIsShow(boolean isshow) {
//        if (isshow) {
//            paomaView.setVisibility(View.VISIBLE);
//            line3.setVisibility(View.VISIBLE);
//            line4.setVisibility(View.VISIBLE);
//        } else {
//            paomaView.setVisibility(View.GONE);
//            line3.setVisibility(View.GONE);
//            line4.setVisibility(View.GONE);
//        }
    }

    private void setView(List<AppModel> list) {
        Log.e("tag", "setView");
//        if (list.size() == 0) {
//            paomaView.setVisibility(View.GONE);
//        } else {
//            paomaView.setVisibility(View.VISIBLE);
//        }
        List<View> views = new ArrayList<View>();
        for (int i = 0; i < list.size(); i++) {
            AppModel model = list.get(i);
            View view = inflater.inflate(R.layout.focus_company_item_layout, null);
            SimpleDraweeView imageview = (SimpleDraweeView) view.findViewById(R.id.image_icon);
            imageview.setImageURI(Uri.parse(model.getAppicon()));
            imageview.setAspectRatio(1.0f);

            TextView appName = (TextView) view.findViewById(R.id.appName);
            appName.setText(model.getAppname());

            TextView appDescript = (TextView) view.findViewById(R.id.appDescript);
            appDescript.setText(model.getAppintro());
            view.setTag(model);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppModel appModel = (AppModel) v.getTag();
                    EventUtils.event(mContext,EventUtils.HELP83);
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(appModel.getAppurl());
                    intent.setData(content_url);
                    startActivity(intent);
                }
            });
            views.add(view);
        }
//        paomaView.setFliperViews(views);
//        paomaView.startAnim(2000, 0);

    }


    public void showShareDialog() {
        shareDialog.show();
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = shareDialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); // 设置宽度
        shareDialog.getWindow().setAttributes(lp);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            getRecommonedApp();

            getHelpeInfo(false);
        }
    };


    private void getHelpeInfo(final boolean showloading) {
        HashMap<String, String> params = new HashMap<>();
        if (LoginSharePreference.getUserInfo(mContext) != null
                && !TextUtils.isEmpty(LoginSharePreference.getUserInfo(mContext).getUserid())) {
            params.put("userid", InquireApplication.getUserInfo().getUserid());
        } else {
            params.put("userid", "");
        }
        mNetError.setVisibility(View.GONE);
        if (showloading) {
            loading.setVisibility(View.VISIBLE);
            sceneAnimation.start();
        }


        HelpRoute.getNewHomeInfo(mContext, params, ((Activity) mContext).getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                if (showloading) {
                    sceneAnimation.stop();
                    loading.setVisibility(View.GONE);
                }

                HelpModel model = (HelpModel) data;
                if (model.getResult() == 0) {
                    if ((model.getTopData() != null)) {
                        dealFlipDigit(model.getTopData().getStartnumber(), model.getTopData().getBignumber(), model.getTopData().getRate());
                        qqText.setText(model.getCustomerqq());
                        qqGroupText.setText(model.getQqgroup());
                        mailText.setText(model.getCustomermail());
                        phoneText.setText(model.getCustomerphone());
                        startTimerEvent();
                    }
                    setPaoMaViewIsShow(true);
                    if (model.getApprecommenlist() != null && model.getApprecommenlist().size() > 0) {
                        setView(model.getApprecommenlist());
                    } else {
                        setPaoMaViewIsShow(false);
                    }
                } else {
                    setPaoMaViewIsShow(false);
                }
            }

            @Override
            public void onFail(String error) {
                dealFlipDigitView();
                setPaoMaViewIsShow(false);
                mNetError.setNetWorkError();
                mNetError.setVisibility(View.VISIBLE);
                if (showloading) {
                    sceneAnimation.stop();
                    loading.setVisibility(View.GONE);
                }

            }
        });
    }

    private void detailUpdate(CheckVersionModel model) {
        hideLoadDialog();
        final VersionModel localVersion = model.getVersionnumber();
        if (localVersion.getUpdate() == CHANGE_UPDATE_TYPE || localVersion.getUpdate() == MUST_UPDATE_TYPE) {

            final Dialog dialog = new AlertDialog.Builder(mContext)
                    .setTitle(localVersion.getTitletext())
                    .setCancelable(false)
                    .setMessage(localVersion.getDescribe())
                    .setPositiveButton(localVersion.getUpdatetext(),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (!UpdateServiceHelper.getState(mContext)) {// 在更新则不更新
                                        UpdateServiceHelper.startOSService(mContext, -1,
                                                localVersion.getUrl());
                                    }
                                    dialog.dismiss();
                                    if (localVersion.getUpdate() == MUST_UPDATE_TYPE) {
                                        ((Activity) mContext).finish();
                                        return;
                                    }
                                }
                            })
                    .setNeutralButton(localVersion.getCacletext(),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    if (localVersion.getUpdate() == MUST_UPDATE_TYPE) {
                                        ((Activity) mContext).finish();
                                        return;
                                    }
                                }
                            }).create();
            dialog.show();


        } else {
            showToast("已经是最新版本了");
        }
    }


    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof UpdateMainEvent) {
            UpdateMainEvent ev = (UpdateMainEvent) event;
            if (ev.getModel() != null) {
                if ("settingPage".equals(ev.getModel().getPage_name())) {
                    if (!TextUtils.isEmpty(ev.getModel().getApp_introduction())) {
                        mDescription.setText(ev.getModel().getApp_introduction());
                    }
                    List<BottomMenuModel> list = new ArrayList<>();
                    list.addAll(ev.getModel().getBottom_menu());
                    refreshBottomView(list);
                }
            }

        }
    }

    private void refreshBottomView(List<BottomMenuModel> list) {
        if (list.size() == 0) {
            return;
        }
        mTopLayout.removeAllViews();
        for (final BottomMenuModel model : list) {
            SettingBottomView view = new SettingBottomView(mContext);
            view.setViewText(model.getTitle());
            if ((ViewType.RangeBegin.ordinal() < model.getTag()) && (model.getTag() < ViewType.RangeEnd.ordinal())) {
                mTopLayout.addView(view);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(model.getUmeng_analytics())) {
                        EventUtils.event2(mContext,model.getUmeng_analytics());
                    }
                    if (model.getTag() == ViewType.URL.ordinal()) {
                        Intent intent = new Intent(mContext, WebActivity.class);
                        intent.putExtra(WebActivity.TITLE, model.getTitle());
                        intent.putExtra(WebActivity.URL, model.getUrl());
                        mContext.startActivity(intent);
                    } else if (model.getTag() == ViewType.FeedBack.ordinal()) {
                        Intent intent = new Intent(mContext, CommonProblemActivity.class);
                        intent.putExtra(CommonProblemActivity.TYPE, CommonProblemActivity.COMMOMPROBLEM_TYPE);
                        startActivity(intent);
                    } else if (model.getTag() == ViewType.ShareDialog.ordinal()) {
                        showShareDialog();
                    }
                }
            });
        }
    }

    private void dealFlipDigitView() {
        long companyCount = CompanyCountSharedPreference.getCompanyCount(mContext);
        if (companyCount >= 0) {
            mFlipMeter.setStaticData(companyCount);
        } else {
            mFlipMeter.setErrorView();
        }
    }

    private void dealFlipDigit(String from, String to, String ratio) {
        if (TextUtils.isEmpty(from) || TextUtils.isEmpty(to) || TextUtils.isEmpty(ratio) || (from.length() > 9) || (to.length() > 9)) {
            showToast("获取企业数量异常");
            return;
        }

        if (Integer.parseInt(from) > Integer.parseInt(to)) {
            showToast("获取企业数量异常");
            return;
        }

        CompanyCountSharedPreference.saveCompanyCount(mContext, to);
        //速率以 秒 为单位
        if (Float.parseFloat(ratio) < 1.0f) {
            mFlipMeter.start(Long.parseLong(from), Long.parseLong(to), 1000, (int) (1 / Float.parseFloat(ratio) + 1));
        } else {
            mFlipMeter.start(Long.parseLong(from), Long.parseLong(to), (int) (Float.parseFloat(ratio) * 1000), 1);
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        if (isFirstIn) {//当未层开启过计时器不进入，也就是“是否第一次进入”
            String lastDateStr = TimeSharePreference.getTimeNode(mContext);//离开页面的时间点
            remainingTime = TimeSharePreference.getIntervalTime(mContext);
            if (remainingTime > 0) {
                long lastDate = 0;
                if (!TextUtils.isEmpty(lastDateStr)) {
                    lastDate = Long.parseLong(lastDateStr);
                }
                long timeD = (System.currentTimeMillis() - lastDate);//离开页面的时间
                boolean isGreaterThan = timeD - remainingTime > 0 ? true : false;//离开页面的时间是否大于计时器剩余时间
                if (isGreaterThan) {
                    mFlipMeter.stop();
                    getHelpeInfo(false);
//                    getHomeInfo(false, false);
                    // TODO 网络请求
                } else {
                    timeUtil.cancel();
                    int continueTime = (int) (remainingTime - timeD);
                    timeUtil.start(continueTime, INTERVAl_TIME);
                }
            }
        }
    }

    /**
     * 获取当前时间
     *
     * @return 时间字符串
     */
    private long getCurrentTime() {
        long date = System.currentTimeMillis();
        return date;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (KeyBoardUtil.openSoftKeyboard(mVersion, getActivity())) {
            KeyBoardUtil.hideSoftKeyboard(getActivity());
        }
        if (mFlipMeter.getCurrentValue() > 0) {
            mFlipMeter.restart();
        }
        isFirstIn = true;
    }


    @Override
    public void onStop() {
        super.onStop();
        TimeSharePreference.saveIntervalTime(remainingTime, mContext);
        TimeSharePreference.saveTimeNode(getCurrentTime() + "", mContext);
//        Log.d("TAG", "onStop remainingTime:" + remainingTime);

        timeUtil.cancel();
    }

    private void startTimerEvent() {
        timeUtil.cancel();
        timeUtil.start(TOTAL_TIME, INTERVAl_TIME);
    }

    @Override
    public void changeRemainingTime(int time) {
        remainingTime = time;
//        Log.d("TAG", "remainingTime:" + time / 1000);
    }

    @Override
    public void timerFinish() {
        Message message = new Message();
        message.what = 1;
        handler.sendMessage(message);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFlipMeter.getCurrentValue() > 0) {
            mFlipMeter.stop();
        }
    }

}
