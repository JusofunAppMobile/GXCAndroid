package com.jusfoun.jusfouninquire.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.database.DBUtil;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.constant.LoginConstant;
import com.jusfoun.jusfouninquire.net.model.AdResultModel;
import com.jusfoun.jusfouninquire.net.model.HomeDataItemModel;
import com.jusfoun.jusfouninquire.net.model.HomeDataModel;
import com.jusfoun.jusfouninquire.net.model.HomeInfoModel;
import com.jusfoun.jusfouninquire.net.model.HomeMyWatchModel;
import com.jusfoun.jusfouninquire.net.model.HomeVersionDataListModel;
import com.jusfoun.jusfouninquire.net.model.HomeVersionModel;
import com.jusfoun.jusfouninquire.net.model.UserInfoModel;
import com.jusfoun.jusfouninquire.net.route.GetHomeInfo;
import com.jusfoun.jusfouninquire.net.util.AsyncImageLoader;
import com.jusfoun.jusfouninquire.net.util.NetUtil;
import com.jusfoun.jusfouninquire.service.LoadNoticeImageService;
import com.jusfoun.jusfouninquire.service.event.CompleteLoginEvent;
import com.jusfoun.jusfouninquire.service.event.FollowSucceedEvent;
import com.jusfoun.jusfouninquire.service.event.HideUpdateEvent;
import com.jusfoun.jusfouninquire.service.event.HomeHotChangedEvent;
import com.jusfoun.jusfouninquire.service.event.HomeLeadEvent;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.service.event.NoticeImageLoadEvent;
import com.jusfoun.jusfouninquire.service.event.NoticeTextEvent;
import com.jusfoun.jusfouninquire.service.event.RefreshHomeEvent;
import com.jusfoun.jusfouninquire.service.event.UpdateAttentionEvent;
import com.jusfoun.jusfouninquire.service.event.UpdateMainEvent;
import com.jusfoun.jusfouninquire.sharedpreference.CompanyCountSharedPreference;
import com.jusfoun.jusfouninquire.sharedpreference.FirstStartAppSharePreference;
import com.jusfoun.jusfouninquire.sharedpreference.HomeVersionSharedPreference;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.sharedpreference.ShowedNoticePreference;
import com.jusfoun.jusfouninquire.sharedpreference.TimeSharePreference;
import com.jusfoun.jusfouninquire.ui.activity.AdvancedSearchActivity;
import com.jusfoun.jusfouninquire.ui.activity.CompanyDetailActivity;
import com.jusfoun.jusfouninquire.ui.activity.HotCompanyActivity;
import com.jusfoun.jusfouninquire.ui.activity.MyAttentionActivity;
import com.jusfoun.jusfouninquire.ui.activity.SearchActivity;
import com.jusfoun.jusfouninquire.ui.activity.SearchResultActivity;
import com.jusfoun.jusfouninquire.ui.activity.WebActivity;
import com.jusfoun.jusfouninquire.ui.animation.SceneAnimation;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;
import com.jusfoun.jusfouninquire.ui.util.ScrollUtil;
import com.jusfoun.jusfouninquire.ui.util.TimerClockUtil;
import com.jusfoun.jusfouninquire.ui.util.TimerClockUtil.TimerImpl;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;
import com.jusfoun.jusfouninquire.ui.view.CustomScrollView;
import com.jusfoun.jusfouninquire.ui.view.HomeAdImageDialog;
import com.jusfoun.jusfouninquire.ui.view.HomeAdTextDailog;
import com.jusfoun.jusfouninquire.ui.view.MarqueeVerticalView;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.jusfoun.jusfouninquire.ui.view.PropagandaView.ColorPointHintView;
import com.jusfoun.jusfouninquire.ui.view.PropagandaView.PropagandaAdapter;
import com.jusfoun.jusfouninquire.ui.view.PropagandaView.RollPagerView;
import com.jusfoun.library.flipdigit.Flipmeter;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import netlib.util.EventUtils;
import com.jusfoun.jusfouninquire.TimeOut;

/**
 * @author zhaoyapeng
 * @version create time:15/10/30上午10:31
 * @Email zyp@jusfoun.com
 * @Description 首页fragment
 */
public class HomeFragment extends BaseInquireFragment implements TimerImpl {

    /**
     * 常量
     */
    private static final int TOTAL_TIME = 20 * 60 * 1000;
    private static final int INTERVAl_TIME = 1000;

    private boolean restartFilp = false;

    private Flipmeter mFlipMeter = null;
    private MarqueeVerticalView marqueeFollow, marqueeHot, marqueeDishonesty;
    private TextView mMyFollow, mHotCompany, mDishonesty;

    private LinearLayout legalText, dishonestyText, advancedText;
    private TextView mFollowCompanyName, mFollowLegal, mFollowCompanyState;
    private TextView mHotCompanyName, mHotCompanyFollow;
    private RatingBar mHotRating;
    private TextView mDishonestyMonth, mDishonestyContent, mDishonestyYear;
    private HomeInfoModel model;
    private LinearLayout mFollowLayout, mDishonestyLayout, homeLayout, mErrorLayout, marqueeLayout;
    private UserInfoModel userInfo;
    private NetWorkErrorView mNetError;
    private RelativeLayout mFollowUpdateLayout, mQuery;
    private CustomScrollView scrollView;
    private RelativeLayout layout;
    private TextView mTempText;
    private View viewBottom, titleView;
    private boolean isBottom = false;
    private int index = 0;
    private ImageView imageView, loadImage;
    private LinearLayout loading;
    private SceneAnimation sceneAnimation;
    private int padding = 0;
    private RelativeLayout rootLayout;
    private LinearLayout backLayout;
    private ImageView mTopBackGround;

    private List<MarqueeVerticalView> marquees;

    private List<HomeMyWatchModel> focusDataList;

    private String mHomeVersion;


    private int remainingTime = 0;
    private boolean isFirstIn = false;


    private int mSpecialImageCount = 0;
    private final String FOCUS_VIEW_TAG = "focus_view_tag";
    private LinearLayout mFakeLayout;
    private HomeInfoModel mHomeModel = null;

    private TimerClockUtil timeUtil;

    private SimpleDraweeView loadImg;


    private HomeAdImageDialog mImageNoticeDialog;
    private HomeAdTextDailog mTextNoticeDialog;
    private int successImageCount = 0;
    private String noticeID;

    private String hideUpdateCompanyID = "";//onResume()状态之后，从本地数据库读取首页数据，"更新"状态依然存在，需要进行刷新操作



    private RollPagerView mRollPagerView;

    private ScrollUtil scrollUtil;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            getHomeInfo(false, false);
        }
    };


    public static HomeFragment getInstance(int padding) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("padding", padding);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }


    @Override
    protected void initData() {
        userInfo = LoginSharePreference.getUserInfo(mContext);
        padding = getArguments().getInt("padding", 0);
        focusDataList = new ArrayList<>();
        marquees = new ArrayList<>();
        scrollUtil = new ScrollUtil();

    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        legalText = (LinearLayout) view.findViewById(R.id.text_legal);
        dishonestyText = (LinearLayout) view.findViewById(R.id.text_shixin);
        advancedText = (LinearLayout) view.findViewById(R.id.text_gaoji);
        mFlipMeter = (Flipmeter) view.findViewById(R.id.Flipmeter);
        marqueeFollow = (MarqueeVerticalView) view.findViewById(R.id.marquee_follow);
        marqueeHot = (MarqueeVerticalView) view.findViewById(R.id.marquee_hot);
        marqueeDishonesty = (MarqueeVerticalView) view.findViewById(R.id.marquee_dishonesty);
        layout = (RelativeLayout) view.findViewById(R.id.layout);

        mMyFollow = (TextView) view.findViewById(R.id.myfollow);
        mHotCompany = (TextView) view.findViewById(R.id.hot_company);
        mQuery = (RelativeLayout) view.findViewById(R.id.query);
        mFollowLayout = (LinearLayout) view.findViewById(R.id.linear_myfollow);
        mNetError = (NetWorkErrorView) view.findViewById(R.id.net_work_error);
        homeLayout = (LinearLayout) view.findViewById(R.id.home);
        scrollView = (CustomScrollView) view.findViewById(R.id.scrollview);
        mTempText = (TextView) view.findViewById(R.id.temp_text);
        viewBottom = view.findViewById(R.id.viewBottom);
        imageView = (ImageView) view.findViewById(R.id.image);

        loading = (LinearLayout) view.findViewById(R.id.loading);
        loadImage = (ImageView) view.findViewById(R.id.loading_img);
        titleView = view.findViewById(R.id.view_title);
        rootLayout = (RelativeLayout) view.findViewById(R.id.layout_root);
        backLayout = (LinearLayout) view.findViewById(R.id.layout_back);

        mErrorLayout = (LinearLayout) view.findViewById(R.id.error);
        marqueeLayout = (LinearLayout) view.findViewById(R.id.marquee_layout);

        mTopBackGround = (ImageView) view.findViewById(R.id.image);
        mFakeLayout = (LinearLayout) view.findViewById(R.id.fake_image_set);

        mRollPagerView = (RollPagerView) view.findViewById(R.id.roll_view_pager);
        return view;
    }

    @Override
    protected void initWeightActions() {
//        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.new_home_back);


        scrollView.setCallBack(new CustomScrollView.OnScrollListener() {
            @Override
            public void onScrollChangedListener(int leftOfVisibleView, int topOfVisibleView, int oldLeftOfVisibleView, int oldTopOfVisibleView) {
                int count = topOfVisibleView;
                if (count < 255) {
                    titleView.setBackgroundColor(Color.parseColor("#ff6307"));
                    titleView.getBackground().setAlpha(count);
                } else {
                    titleView.setBackgroundColor(Color.parseColor("#ff6307"));
                    titleView.getBackground().setAlpha(255);
                }
            }

            @Override
            public void onPullScroll(int height) {
                scrollUtil.imageScale(layout, imageView, height, PhoneUtil.dip2px(mContext, 204));
            }
        });

        legalText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext,EventUtils.HOMEPAGE02);
                Intent intent = new Intent(mContext, SearchActivity.class);
                intent.putExtra(SearchActivity.SEARCH_TYPE, SearchActivity.LEGAL_SHAREHOLDER);
                startActivity(intent);
            }
        });

        mQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext,EventUtils.HOMEPAGE01);
                //goActivity(SearchActivity.class);
                goActivity(SearchResultActivity.class);
            }
        });
        dishonestyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventUtils.event(mContext,EventUtils.HOMEPAGE03);

                Intent intent = new Intent(mContext, SearchActivity.class);

                intent.putExtra(SearchActivity.SEARCH_TYPE, SearchActivity.DISHONEST);
                startActivity(intent);
            }
        });

        advancedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext,EventUtils.HOMEPAGE04);
                goActivity(AdvancedSearchActivity.class);
            }
        });

        mMyFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext,EventUtils.HOMEPAGE05);
                goActivity(MyAttentionActivity.class);
            }
        });

        mHotCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext,EventUtils.HOMEPAGE07);
                Bundle bundle = new Bundle();
                bundle.putSerializable(HotCompanyActivity.MODEL, model);
                goActivity(HotCompanyActivity.class, bundle);
            }
        });

        mDishonesty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext,EventUtils.HOMEPAGE09);
                Bundle bundle = new Bundle();
                if (model != null) {
                    bundle.putString(WebActivity.URL
                            , mContext.getString(R.string.req_url) + mContext.getString(R.string.dishonesty_url));
                    bundle.putString(WebActivity.TITLE, model.getDishonesty().getName());
                }
                goActivity(WebActivity.class, bundle);
            }
        });

        mFlipMeter.setReachGoalListener(new Flipmeter.ReachGoalListener() {
            @Override
            public void onReachGoal() {
                getHomeInfo(false, false);
            }
        });

        mFlipMeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext,EventUtils.HOMEPAGE11);
            }
        });

        mNetError.setListener(new NetWorkErrorView.OnRefreshListener() {
            @Override
            public void OnNetRefresh() {
                getHomeInfo(true, true);

            }
        });

        sceneAnimation = new SceneAnimation(loadImage, 75);
        timeUtil = TimerClockUtil.getInstance();
        timeUtil.setRemainingTimeListener(this);
        homeLayout.setPadding(0, padding, 0, 0);
        getHomeInfo(true, true);
        setViewHeight();
        adHandler.sendEmptyMessageDelayed(1001, 800);


    }

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof CompleteLoginEvent) {
            if (((CompleteLoginEvent) event).getIsLogin() == LoginConstant.LOGIN ||
                    ((CompleteLoginEvent) event).getIsLogin() == LoginConstant.REGISTER) {
                userInfo = LoginSharePreference.getUserInfo(mContext);
                getHomeInfo(true, true);
            } else if (((CompleteLoginEvent) event).getIsLogin() == LoginConstant.LOGIN_OUT) {
                for (int i = 0; i < marqueeLayout.getChildCount(); i++) {
                    View view = marqueeLayout.getChildAt(i);
                    if (FOCUS_VIEW_TAG.equals(view.getTag())) {
                        marqueeLayout.removeView(view);
                        break;
                    }
                }
                mFollowLayout.setVisibility(View.GONE);//退出登录，隐藏我的关注
            }
        } else if (event instanceof FollowSucceedEvent) {
//            关注、取消关注后，不再调用首页接口进行刷新
            /**
             * 2016年04月25日再次打开，关注、取消关注后，刷新首页。
             */
             getHomeInfo(true,false);
        } else if (event instanceof HideUpdateEvent) {
            HideUpdateEvent ev = (HideUpdateEvent) event;
            hideUpdateStatus(ev.getCompanyid());
            hideUpdateCompanyID = ev.getCompanyid();
        } else if (event instanceof UpdateAttentionEvent) {
            UpdateAttentionEvent ev = (UpdateAttentionEvent) event;
            addFollowCount(ev);

        } else if (event instanceof UpdateMainEvent) {
            final UpdateMainEvent ev = (UpdateMainEvent) event;
            if (ev.getModel() != null) {
                if ("mainPage".equals(ev.getModel().getPage_name())) {
                    if (!TextUtils.isEmpty(ev.getModel().getHeader_picture()) /*&& !TextUtils.isEmpty(ev.getModel().getHeader_picture_link())*/) {
                        loadTopBackground(ev.getModel().getHeader_picture(), ev.getModel().getHeader_picture_link());

                    }
                }

            }

        } else if (event instanceof RefreshHomeEvent) {
            getNewHomeData();//登录后调用接口获取显示数据
        }else if (event instanceof NoticeImageLoadEvent){
            NoticeImageLoadEvent ev = (NoticeImageLoadEvent) event;
            successImageCount += ev.getCount();
            if (successImageCount > 1){
                //应用首次安装启动，需要先显示遮罩层，遮罩层消失后再进行通知显示
                if ((mImageNoticeDialog != null) && (!mImageNoticeDialog.isHasShown())){
                    if (isVisible() && (!FirstStartAppSharePreference.isHomeFirstStart(mContext))){
                        mImageNoticeDialog.setData();
                        mImageNoticeDialog.show();
                        EventUtils.event(mContext,EventUtils.NOTICE01);
                        ShowedNoticePreference.saveNotice(noticeID,mContext);
                        successImageCount = 0;
                    }
                }


            }
        }else if (event instanceof NoticeTextEvent){
            if ((mTextNoticeDialog != null) && (!mTextNoticeDialog.isHasShown()) ){
                if (isVisible() && (mTextNoticeDialog.isReadyToShow()) && (!FirstStartAppSharePreference.isHomeFirstStart(mContext))){
                    mTextNoticeDialog.show();
                    EventUtils.event(mContext,EventUtils.NOTICE01);
                    ShowedNoticePreference.saveNotice(noticeID, mContext);
                }
            }

        }
    }

    private void loadTopBackground(String url, final String loadURL) {
        AsyncImageLoader.getInstance(mContext).loadAsync(mContext.getPackageName(), url,
                new AsyncImageLoader.BitmapCallback() {

                    @Override
                    public void onImageLoaded(final String path, String url) {
                        if (path == null && NetUtil.isNetworkConnected(mContext.getApplicationContext())) {

                        } else if (path != null) {
                            ((Activity) mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        mTopBackGround.setImageURI(Uri.parse(path));
                                        mTopBackGround.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (!TextUtils.isEmpty(loadURL)) {
                                                    EventUtils.event(mContext,EventUtils.HOMEPAGE15);
                                                    Intent intent = new Intent(mContext, WebActivity.class);
                                                    intent.putExtra(WebActivity.URL, loadURL);
                                                    startActivity(intent);
                                                } else {

                                                }

                                            }
                                        });

                                    } catch (Exception e) {
                                        Log.e("tag", "Exception=" + e);
                                    }
                                }
                            });

                        }
                    }
                });
    }


    private void hideUpdateStatus(String companyid) {

        for (HomeDataModel dataModel : model.getDataList()) {
            int index = 0;
            for (HomeDataItemModel itemModel : dataModel.getList()) {
                if (companyid.equals(itemModel.getCompanyid())) {
                    itemModel.setIsupdate("0");
                    if (index < marquees.size()) {
                        marqueeFollowView(dataModel.getList(), marquees.get(index));
                    }
                    return;
                }
                index++;
            }
        }
    }

    private void getHomeInfo(final boolean showloading, final boolean refreshview) {
        TimeOut timeOut = new TimeOut(mContext);
        HashMap<String, String> params = new HashMap<>();
        if (LoginSharePreference.getUserInfo(mContext) != null
                && !TextUtils.isEmpty(LoginSharePreference.getUserInfo(mContext).getUserid())) {
            params.put("userid", InquireApplication.getUserInfo().getUserid());
        } else {
            params.put("userid", "");
        }
        params.put("t", timeOut.getParamTimeMollis() + "");
        params.put("m", timeOut.MD5time() + "");
        if (showloading) {
            loading.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            sceneAnimation.start();
        }
        GetHomeInfo.getHomeInfo(mContext, params, ((Activity) mContext).getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
//                String string=LibIOUtil.convertStreamToJson(getResources().openRawResource(R.raw.home_info));
//                HomeInfoModel model=new Gson().fromJson(string,HomeInfoModel.class);
//                updateView(model);

                startTimerEvent();
                dealPropagandaView((HomeInfoModel) data);
                updateView((HomeInfoModel) data, refreshview);
                HomeLeadEvent event = new HomeLeadEvent();
                event.setPadding(padding);
                EventBus.getDefault().post(event);
            }

            @Override
            public void onFail(String error) {
                if (showloading) {
                    dealFlipDigitView();
                    sceneAnimation.stop();
                    mNetError.setNetWorkError();
                    mErrorLayout.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    homeLayout.setVisibility(View.GONE);
                    mNetError.setVisibility(View.VISIBLE);
                } else {
                    startTimerEvent();
                }

            }
        });
    }

    private void updateView(HomeInfoModel model, boolean refreshview) {
        if (isDetached()) {
            return;
        }
        if (!isAdded()){
            return;
        }

        mHomeModel = model;
        scrollView.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
        sceneAnimation.stop();
        this.model = model;


        if (model.getResult() == 0) {
            if (!TextUtils.isEmpty(model.getVersion())) {
                mHomeVersion = model.getVersion();
                if (!model.getVersion().equals(HomeVersionSharedPreference.getHomeVersion(mContext))) {
                    getNewHomeData();
                } else {
                    loadLocalData(GetHomeInfo.DYNAMIC_CONTENT_URL, null);
                }
            }

            if (refreshview) {
                marqueeLayout.removeAllViews();
                marquees.clear();
            }

            HomeInfoModel finalModel = DBUtil.SpliceHomeModel(mContext, model);
            if (finalModel != null) {
                //TODO 处理拼接好的model显示，同时进行此次网络请求的返回model的处理
                dealHomeModules(finalModel, refreshview, this.model);
            }

            homeLayout.setVisibility(View.VISIBLE);
            mNetError.setVisibility(View.GONE);
            mErrorLayout.setVisibility(View.GONE);
            if ((model.getTopData() != null) && (!restartFilp)) {
                dealFlipDigit(model.getTopData().getStartnumber(), model.getTopData().getBignumber(), model.getTopData().getRate());
                restartFilp = false;
            }

            startAllAnim();
        } else {
            mNetError.setServerError();
            mErrorLayout.setVisibility(View.VISIBLE);
            homeLayout.setVisibility(View.GONE);
            mNetError.setVisibility(View.VISIBLE);
        }
    }

    private void dealHomeModules(HomeInfoModel model, boolean refreshview, HomeInfoModel originModel) {
        if ((model != null) && (model.getDataList() != null)) {
            if (refreshview) {
                //TODO 首先读取数据库，将接口返回的非专题模块与数据进行拼接显示，然后进行图片记载，如果成功，则存储整个接口数据
                for (final HomeDataModel dataModel : model.getDataList()) {

                    Log.e("tag", "dealHomeModules");
                    if (dataModel.getType().equals(HomeDataModel.TYPE_FOL)) {
//                        dealFocusModule(dataModel);
                    } else if (dataModel.getType().equals(HomeDataModel.TYPE_HOT)) {
                        dealHotModule(dataModel);
                    } else if (dataModel.getType().equals(HomeDataModel.TYPE_DIS)) {
                        dealDishonestModule(dataModel);
                    }
                }
            }

            //dealPropagandaView(model);

//            dealDynamicModule(model, refreshview, originModel);

        }

    }


    /**
     * 处理首页的运营活动的view
     * @param model
     */
    private void dealPropagandaView(HomeInfoModel model){
        if (isDetached()) {
            return;
        }
        if (!isAdded()){
            return;
        }

        if (model != null && model.getAdlist() != null){
            if (model.getAdlist().size() > 0){
                mRollPagerView.setVisibility(View.VISIBLE);
                PropagandaAdapter adapter = new PropagandaAdapter(getActivity(),mRollPagerView);
                //如果只有一个运营块，隐藏indicator
                if (model.getAdlist().size() == 1){
                    mRollPagerView.setHintView(null);
                }else {
                    mRollPagerView.setHintView(new ColorPointHintView(getActivity(), getResources().getColor(R.color.color_home_propaganda_selected),getResources().getColor(R.color.color_home_propaganda_unselected)));

                }
                mRollPagerView.setAdapter(adapter);
                adapter.refresh(model.getAdlist());
            }
        }

    }

    private void dealFocusModule(final HomeDataModel dataModel) {
        View view = null;
        TextView title = null;
        MarqueeVerticalView marqueeVerticalView = null;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_layout_myfollow, null, false);
        marqueeVerticalView = (MarqueeVerticalView) view.findViewById(R.id.marquee_follow);
        title = (TextView) view.findViewById(R.id.myfollow);
        title.setText(dataModel.getName());
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event2(mContext,dataModel.getUmeng_analytics());
                goActivity(MyAttentionActivity.class);
            }
        });

        marqueeFollowView(dataModel.getList(), marqueeVerticalView);

        if (dataModel.getList().size() == 0)
            view.setVisibility(View.GONE);
        else
            view.setVisibility(View.VISIBLE);

        view.setTag(FOCUS_VIEW_TAG);
        Log.e("tag", "marqueeFollowView1");
        marqueeLayout.addView(view);
        marquees.add(marqueeVerticalView);
        Log.e("tag", "marqueeFollowView2");
    }

    private void dealHotModule(final HomeDataModel dataModel) {
        View view = null;
        TextView title = null;
        MarqueeVerticalView marqueeVerticalView = null;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_layout_hotcompany, null, false);
        marqueeVerticalView = (MarqueeVerticalView) view.findViewById(R.id.marquee_hot);
        title = (TextView) view.findViewById(R.id.hot_company);
        title.setText(dataModel.getName());
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event2(mContext,dataModel.getUmeng_analytics());
                Bundle bundle = new Bundle();
                bundle.putSerializable(HotCompanyActivity.MODEL, dataModel);
                goActivity(HotCompanyActivity.class, bundle);
            }
        });
        marqueeHotView(dataModel.getList(), marqueeVerticalView);
        HomeHotChangedEvent event = new HomeHotChangedEvent();
        event.setList(dataModel.getList());
        EventBus.getDefault().post(event);
        if (dataModel.getList().size() == 0)
            view.setVisibility(View.GONE);
        else
            view.setVisibility(View.VISIBLE);

        marqueeLayout.addView(view);
        marquees.add(marqueeVerticalView);
    }

    private void dealDishonestModule(final HomeDataModel dataModel) {
        View view = null;
        TextView title = null;
        MarqueeVerticalView marqueeVerticalView = null;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_layout_dishonesty, null, false);
        marqueeVerticalView = (MarqueeVerticalView) view.findViewById(R.id.marquee_dishonesty);
//        title = (TextView) view.findViewById(R.id.dishonesty);
        title.setText(dataModel.getName());
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event2(mContext,dataModel.getUmeng_analytics());
                Bundle bundle = new Bundle();
                bundle.putString(WebActivity.URL, dataModel.getUrl());
                bundle.putString(WebActivity.TITLE, dataModel.getName());
                goActivity(WebActivity.class, bundle);
            }
        });
        marqueeDishonestyView(dataModel.getList(), marqueeVerticalView);
        if (dataModel.getList().size() == 0)
            view.setVisibility(View.GONE);
        else
            view.setVisibility(View.VISIBLE);

        marqueeLayout.addView(view);
        marquees.add(marqueeVerticalView);
    }

    private void dealDynamicModule(HomeInfoModel model, boolean refreshview, HomeInfoModel originModel) {
        View view = null;
        TextView title = null;
        MarqueeVerticalView marqueeVerticalView = null;

        mSpecialImageCount = 0;
        if (originModel != null) {
            for (final HomeDataModel dataModel : originModel.getDataList()) {
                if (HomeDataModel.TYPE_SPE.equals(dataModel.getType())) {
                    if (dataModel.getList() != null) {
                        mSpecialImageCount += dataModel.getList().size();
                    }
                }
            }
        }

        for (final HomeDataModel dataModel : model.getDataList()) {
            if (HomeDataModel.TYPE_SPE.equals(dataModel.getType())) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_layout_special, null, false);
                marqueeVerticalView = (MarqueeVerticalView) view.findViewById(R.id.marquee_special);
                title = (TextView) view.findViewById(R.id.special_title);
                title.setText(dataModel.getName());
                title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventUtils.event2(mContext,dataModel.getUmeng_analytics());
                        Bundle bundle = new Bundle();
                        bundle.putString(WebActivity.URL, dataModel.getUrl());
                        bundle.putString(WebActivity.TITLE, dataModel.getName());
                        goActivity(WebActivity.class, bundle);
                    }
                });

                marqueeSpecialView(dataModel.getList(), marqueeVerticalView);
                if (dataModel.getList().size() == 0)
                    view.setVisibility(View.GONE);
                else
                    view.setVisibility(View.VISIBLE);

                if (refreshview) {
                    marqueeLayout.addView(view);
                    marquees.add(marqueeVerticalView);
                }
            }
        }

        if (mSpecialImageCount == 0) {
            DBUtil.insertHome(mContext, originModel);
        } else {
            dealNetModel(originModel);
        }


    }


    //加载专题图片
    private void dealNetModel(final HomeInfoModel models) {
        for (final HomeDataModel datamodel : models.getDataList()) {
            if (HomeDataModel.TYPE_SPE.equals(datamodel.getType())) {

                for (HomeDataItemModel item : datamodel.getList()) {
                    final SimpleDraweeView image = new SimpleDraweeView(mContext);
                    mFakeLayout.addView(image);
                    try {
                        Uri uri = Uri.parse(item.getImage());
                        ControllerListener listener = new BaseControllerListener() {
                            @Override
                            public void onIntermediateImageFailed(String id, Throwable throwable) {
                                super.onIntermediateImageFailed(id, throwable);
                                mFakeLayout.removeView(image);
                            }

                            @Override
                            public void onFailure(String id, Throwable throwable) {
                                super.onFailure(id, throwable);
                                mFakeLayout.removeView(image);
                                Log.e("tag", "onFailure");
                            }

                            @Override
                            public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
                                super.onFinalImageSet(id, imageInfo, animatable);
                                mFakeLayout.removeView(image);
                                //TODO 发送通知，本次加载图片成功
                                mSpecialImageCount--;
                                Log.e("tag", "onFinalImageSet1");
                                if (mSpecialImageCount == 0) {
                                    Log.e("tag", "onFinalImageSe2");
                                    DBUtil.insertHome(mContext, models);
                                }
                            }
                        };
                        DraweeController controller = Fresco.newDraweeControllerBuilder()
                                .setUri(uri)
                                .setTapToRetryEnabled(true)
                                .setOldController(image.getController())
                                .setControllerListener(listener)
                                .build();

                        image.setController(controller);
                    } catch (Exception e) {

                    }


                }

            }
        }

    }

    private void loadLocalData(String url, Map<String, String> params) {
        if (VolleyUtil.getQueue(mContext).getCache().get(mContext.getString(R.string.req_url) + url) != null) {
            //response exists
            String cachedResponse = new String(VolleyUtil.getQueue(mContext).getCache().get(mContext.getString(R.string.req_url) + url).data);
            Type modelType = new TypeToken<HomeVersionDataListModel>() {
            }.getType();
            HomeVersionDataListModel model = new Gson().fromJson(cachedResponse, modelType);

            if (model != null && model.getContent() != null) {
                for (HomeVersionModel data : model.getContent()) {
                    UpdateMainEvent event = new UpdateMainEvent();
                    event.setModel(data);
                    EventBus.getDefault().post(event);
                }
            }

        } else {
            getNewHomeData();
        }

    }

    private void getNewHomeData() {
        GetHomeInfo.getNewVersionData(mContext, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                if (data instanceof HomeVersionDataListModel) {
                    Log.e("tag", "HomeVersionDataListModel=" + new Gson().toJson(data));
                    HomeVersionDataListModel datamodel = (HomeVersionDataListModel) data;
                    refreshHomeView(datamodel);
                }
            }

            @Override
            public void onFail(String error) {

            }
        });
    }

    private void refreshHomeView(HomeVersionDataListModel model) {
        if (model == null) {
            return;
        }
        if (model.getResult() == 0) {
            HomeVersionSharedPreference.saveHomeVersion(mContext, mHomeVersion);
            if (model.getContent() == null)
                return;
            for (HomeVersionModel data : model.getContent()) {
                UpdateMainEvent event = new UpdateMainEvent();
                event.setModel(data);
                EventBus.getDefault().post(event);
            }
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

    /**
     * 我的关注
     *
     * @param models
     * @param marquee
     */
    private void marqueeFollowView(List<HomeDataItemModel> models, MarqueeVerticalView marquee) {
        if (isDetached()) {
            return;
        }

        try {

            ArrayList<View> list = new ArrayList<>();
            for (final HomeDataItemModel model : models) {
                if (hideUpdateCompanyID.equals(model.getCompanyid())){
                    model.setIsupdate("0");
                }
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_marquee_myfollow, null);
                mFollowCompanyName = (TextView) view.findViewById(R.id.follow_company_name);
                mFollowUpdateLayout = (RelativeLayout) view.findViewById(R.id.follow_update_layout);
                mFollowLegal = (TextView) view.findViewById(R.id.follow_legal);
                mFollowCompanyState = (TextView) view.findViewById(R.id.follow_company_state);


                if (!TextUtils.isEmpty(model.getCompanyname())) {
                    mFollowCompanyName.setText(model.getCompanyname());
                } else {
                    mFollowCompanyName.setText("");
                }

                if (!TextUtils.isEmpty(model.getLegal())) {
                    mFollowLegal.setText("法人：" + model.getLegal());
                } else {
                    mFollowLegal.setText("法人：-");
                }
                if (!TextUtils.isEmpty(model.getCompanystate())) {
                    if (model.getCompanystate().equals("-")) {
                        mFollowCompanyState.setText("");
                    } else {
                        mFollowCompanyState.setText(model.getCompanystate());
                    }
                } else {
                    mFollowCompanyState.setText("");
                }

                if ("1".equals(model.getIsupdate())) {
                    mFollowUpdateLayout.setVisibility(View.VISIBLE);
                } else {
                    mFollowUpdateLayout.setVisibility(View.GONE);
                }
                view.setTag(model);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventUtils.event2(mContext,model.getUmeng_analytics());
                        Bundle bundle = new Bundle();
                        bundle.putString(CompanyDetailActivity.COMPANY_ID, model.getCompanyid());
                        bundle.putString(CompanyDetailActivity.COMPANY_NAME, model.getCompanyname());
                        goActivity(CompanyDetailActivity.class, bundle);
                    }
                });
                list.add(view);
            }
            marquee.setEnableTumble(false);
            marquee.setFliperViews(list);

        } catch (Exception e) {

        } catch (StackOverflowError error) {

        }
    }

    /**
     * 热门企业
     *
     * @param models
     * @param marquee
     */
    private void marqueeHotView(List<HomeDataItemModel> models, MarqueeVerticalView marquee) {
        if (isDetached()) {
            return;
        }
        ArrayList<View> list = new ArrayList<>();
        int size = models.size();
        size = size > 5 ? 5 : size;
        for (int i = 0; i < size; i++) {
            final HomeDataItemModel model = models.get(i);
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_marquee_hotcompany, null);
            mHotCompanyName = (TextView) view.findViewById(R.id.hot_company_name);
            mHotCompanyFollow = (TextView) view.findViewById(R.id.hot_company_follow);
            mHotRating = (RatingBar) view.findViewById(R.id.hot_company_rating);

            mHotCompanyName.setText(model.getCompanyname());
            if (!TextUtils.isEmpty(model.getAttentioncount())) {
                SpannableString spannableString = setTextViewColor(model.getAttentioncount());
                if (spannableString != null)
                    mHotCompanyFollow.setText(spannableString);
            }
            if (!TextUtils.isEmpty(model.getRatings())) {
                try {
                    mHotRating.setRating(Integer.parseInt(model.getRatings()));
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
            view.setTag(model);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventUtils.event2(mContext,model.getUmeng_analytics());
                    HomeDataItemModel model = (HomeDataItemModel) v.getTag();
                    Bundle bundle = new Bundle();
                    bundle.putString(CompanyDetailActivity.COMPANY_ID, model.getCompanyid());
                    bundle.putString(CompanyDetailActivity.COMPANY_NAME, model.getCompanyname());
                    goActivity(CompanyDetailActivity.class, bundle);
                }
            });
            list.add(view);
        }
        marquee.setEnableTumble(false);
        marquee.setFliperViews(list);
    }

    /**
     * 失信榜单
     *
     * @param models
     * @param marquee
     */
    private void marqueeDishonestyView(final List<HomeDataItemModel> models, MarqueeVerticalView marquee) {
        if (isDetached()) {
            return;
        }
        if (models == null)
            return;
        ArrayList<View> list = new ArrayList<>();
        for (final HomeDataItemModel model : models) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_marquee_dishonesty, null);
            mDishonestyLayout = (LinearLayout) view.findViewById(R.id.dishonest_layout);
            mDishonestyMonth = (TextView) view.findViewById(R.id.dishonesty_month);
            mDishonestyYear = (TextView) view.findViewById(R.id.dishonest_year);
            mDishonestyContent = (TextView) view.findViewById(R.id.dishonesty_content);

            mDishonestyYear.setText(model.getYear() + mContext.getString(R.string.year));
            SpannableString spannableString = setTextSize(model.getMonth());
            if (spannableString != null)
                mDishonestyMonth.setText(spannableString);
            mDishonestyContent.setText(model.getTitle());

            mDishonestyLayout.setBackgroundResource(R.drawable.bg_dishonesty_date_one);
            mDishonestyYear.setTextColor(getResources().getColor(R.color.color_dishonesty_bg_one));
            mDishonestyMonth.setTextColor(getResources().getColor(R.color.color_dishonesty_bg_one));
            view.setTag(model.getUrl());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventUtils.event2(mContext,model.getUmeng_analytics());
                    String url = v.getTag().toString();
                    Bundle bundle = new Bundle();
                    bundle.putString(WebActivity.URL, url);
                    bundle.putString(WebActivity.TITLE, model.getTitle());
                    goActivity(WebActivity.class, bundle);
                }
            });
            list.add(view);
        }
        marquee.setEnableTumble(false);
        marquee.setFliperViews(list);
    }


    private void marqueeSpecialView(List<HomeDataItemModel> models, MarqueeVerticalView marquee) {
        if (isDetached()) {
            return;
        }
        ArrayList<View> list = new ArrayList<>();
        for (final HomeDataItemModel model : models) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_marquee_special, null);
            SimpleDraweeView image = (SimpleDraweeView) view.findViewById(R.id.image_special);
            Uri uri = Uri.parse(model.getImage());
            image.setImageURI(uri);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventUtils.event2(mContext,model.getUmeng_analytics());
                    Bundle bundle = new Bundle();
                    bundle.putString(WebActivity.URL, model.getUrl());
                    bundle.putString(WebActivity.TITLE, model.getTitle());
                    goActivity(WebActivity.class, bundle);
                }
            });
            list.add(view);
        }
        marquee.setEnableTumble(false);
        marquee.setFliperViews(list);
    }


    private SpannableString setTextViewColor(String string) {
        SpannableString spannableString = null;
        if (isAdded()) {
            spannableString = new SpannableString(string +mContext.getString(R.string.people_follow));
            spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_text_yellow)), 0,
                    string.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        return spannableString;
    }

    private SpannableString setTextSize(String string) {
        SpannableString spannableString = null;
        if (isAdded()) {
            spannableString = new SpannableString(string + mContext.getString(R.string.month));
            spannableString.setSpan(new AbsoluteSizeSpan(PhoneUtil.dip2px(mContext, 18)), 0, string.length(),
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        return spannableString;
    }

    /**
     * 开始所有动画
     * <p/>
     * 在翻转到当前页且处于最前端的时候，才会激活
     */
    private void startAllAnim() {

        for (int i = 0; i < marquees.size(); i++) {
            marquees.get(i).startAnim(2900 + i * 1000, 1900 * i);
        }
    }

    /**
     * 停止掉所有动画。
     * <p/>
     * 当前页不在最前端的时候，关闭。
     */
    private void stopAllAnim() {
        for (int i = 0; i < marquees.size(); i++) {
            marquees.get(i).stopAnim();
        }
    }

    private boolean isInit;// 是否可以开始加载数据

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isInit = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // 每次切换fragment时调用的方法
        if (isInit) {
            if (isVisibleToUser) {
                startAllAnim();
            } else {
                stopAllAnim();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().post(new NoticeTextEvent());
        EventBus.getDefault().post(new NoticeImageLoadEvent(0));
        if (mFlipMeter.getCurrentValue() > 0) {
            mFlipMeter.restart();
        }

//        if (getUserVisibleHint()) {
        // TODO  这里逻辑有隐患，先这样。回头李震重写这块。
        startAllAnim();
        if (mHomeModel != null) {
            HomeInfoModel model = DBUtil.getHomeData(mContext);
            if (model != null) {
                restartFilp = true;
                updateView(model, true);
            }
        }
//        }


        isFirstIn = true;


    }

    @Override
    public void onPause() {
        super.onPause();
        stopAllAnim();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFlipMeter.getCurrentValue() > 0) {
            mFlipMeter.stop();
        }
    }

    private void setViewHeight() {
        ViewGroup.LayoutParams layoutParams = titleView.getLayoutParams();
        layoutParams.height = PhoneUtil.dip2px(mContext, 50) + padding;
        titleView.setLayoutParams(layoutParams);
    }

    private void addFollowCount(UpdateAttentionEvent event) {
        if (model == null)
            return;
        String companyId = event.getCompanyId();
        String attention = event.getAttention();
        Log.e(TAG, companyId);
        if (TextUtils.isEmpty(companyId) || TextUtils.isEmpty(attention))
            return;
        int i = 0;
        for (HomeDataModel dataModel : model.getDataList()) {
            if (dataModel.getType().equals(HomeDataModel.TYPE_HOT)) {
                for (HomeDataItemModel itemModel : dataModel.getList()) {
                    if (companyId.equals(itemModel.getCompanyid())) {
                        itemModel.setAttentioncount(attention);
                        HomeHotChangedEvent homeHotChangedEvent = new HomeHotChangedEvent();
                        homeHotChangedEvent.setList(dataModel.getList());
                        EventBus.getDefault().post(homeHotChangedEvent);
//                        marqueeHotView(model.getHotbusinesslist());
                        marqueeHotView(dataModel.getList(), marquees.get(i));
                        return;
                    }
                }
            }
            i++;
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

    private Date convertDateString(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
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
                Log.d("TAG", "onStart System.currentTimeMillis():" + System.currentTimeMillis());
                long timeD = (System.currentTimeMillis() - lastDate);//离开页面的时间
                boolean isGreaterThan = timeD - remainingTime > 0 ? true : false;//离开页面的时间是否大于计时器剩余时间
                if (isGreaterThan) {
                    mFlipMeter.stop();
                    getHomeInfo(false, false);
                } else {
                    timeUtil.cancel();
                    int continueTime = (int) (remainingTime - timeD);
                    timeUtil.start(continueTime, INTERVAl_TIME);
                }
            }
        }


    }

    @Override
    public void onStop() {
        super.onStop();
        TimeSharePreference.saveIntervalTime(remainingTime, mContext);
        TimeSharePreference.saveTimeNode(getCurrentTime() + "", mContext);
//        Log.d("TAG", "onStop remainingTime:" + remainingTime);

        timeUtil.cancel();
        Log.d("TAG", "停止剩余时间cancel");
        Log.d("TAG", "停止剩余时间计时任务");
    }

    private void startTimerEvent() {
        timeUtil.cancel();

        timeUtil.start(TOTAL_TIME, INTERVAl_TIME);
        Log.d("TAG", "startTimerEvent启动20分钟计时任务");
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
        Log.d("TAG", "onFinish");
    }


    private Handler adHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getAdNet();
        }
    };



    private void getAdNet() {
        GetHomeInfo.getAdData(mContext, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                if ((data != null) && (data instanceof AdResultModel)) {
                    AdResultModel model = (AdResultModel) data;
                    if ((model.getDataResult() != null) && (!TextUtils.isEmpty(model.getDataResult().getId()))){
                        if (ShowedNoticePreference.getNoticeId(mContext).equals(model.getDataResult().getId()) ){
                            //之前已经展示过该通知，此次忽略
                        }else {
                            noticeID = model.getDataResult().getId();
                            if (model.getDataResult().getType().equals("1")) {
                                mTextNoticeDialog = new HomeAdTextDailog(mContext, R.style.my_dialog);
                                mTextNoticeDialog.setData(model);
                                EventBus.getDefault().post(new NoticeTextEvent());
                            } else if (model.getDataResult().getType().equals("2")) {
                                mImageNoticeDialog = new HomeAdImageDialog(mContext, R.style.my_dialog);
                                mImageNoticeDialog.setIntentURL(model.getDataResult().getTitle(), model.getDataResult().getUrl());
                                Intent intent = new Intent(mContext, LoadNoticeImageService.class);
                                intent.putExtra(LoadNoticeImageService.CONTENT_IMAGE_URL,model.getDataResult().getImageUrl());
                                intent.putExtra(LoadNoticeImageService.CONTENT_BTN_URL,model.getDataResult().getBtnImgUrl());
                                mContext.startService(intent);

                            }
                        };
                    }

                }
            }

            @Override
            public void onFail(String error) {

            }
        });
    }

}
