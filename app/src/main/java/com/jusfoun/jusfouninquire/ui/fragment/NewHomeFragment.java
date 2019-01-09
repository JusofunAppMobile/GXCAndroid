package com.jusfoun.jusfouninquire.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.JniUtil;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.BaseModel;
import com.jusfoun.jusfouninquire.net.model.HomeDishonestyItemModel;
import com.jusfoun.jusfouninquire.net.model.HomeDishonestyModel;
import com.jusfoun.jusfouninquire.net.model.HomeRecentModel;
import com.jusfoun.jusfouninquire.net.model.NewHomeModel;
import com.jusfoun.jusfouninquire.net.model.SearchHistoryItemModel;
import com.jusfoun.jusfouninquire.net.route.GetHomeInfo;
import com.jusfoun.jusfouninquire.net.route.WageInfoModel;
import com.jusfoun.jusfouninquire.net.util.TouchUtil;
import com.jusfoun.jusfouninquire.service.event.FlipperAnimEvent;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.sharedpreference.FirstStartAppSharePreference;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.sharedpreference.QuestionnaireSharePreference;
import com.jusfoun.jusfouninquire.ui.activity.HotCompanyActivity;
import com.jusfoun.jusfouninquire.ui.activity.RecentChangeActivity;
import com.jusfoun.jusfouninquire.ui.activity.TypeSearchActivity;
import com.jusfoun.jusfouninquire.ui.activity.WebActivity;
import com.jusfoun.jusfouninquire.ui.animation.SceneAnimation;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;
import com.jusfoun.jusfouninquire.ui.util.ScrollUtil;
import com.jusfoun.jusfouninquire.ui.view.CustomScrollView;
import com.jusfoun.jusfouninquire.ui.view.HomeHotListView;
import com.jusfoun.jusfouninquire.ui.view.HomeRefreshHeaderView;
import com.jusfoun.jusfouninquire.ui.view.MarqueeVerticalView;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.jusfoun.jusfouninquire.ui.view.PropagandaView.NewAddAdapter;
import com.jusfoun.jusfouninquire.ui.view.PropagandaView.RollPagerView;
import com.jusfoun.jusfouninquire.ui.view.QuestionnairDialog;
import com.jusfoun.jusfouninquire.ui.view.RadarCountView;
import com.jusfoun.jusfouninquire.ui.view.WageInfoAndQuessionInfo;
import com.jusfoun.jusfouninquire.ui.view.WagePopBoxDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import netlib.util.AppUtil;
import netlib.util.EventUtils;
import com.jusfoun.jusfouninquire.TimeOut;

/**
 * @author zhaoyapeng
 * @version create time:16/8/1017:46
 * @Email zyp@jusfoun.com
 * @Description ${新首页fragment}
 */
public class NewHomeFragment extends BaseInquireFragment {
    private WagePopBoxDialog questionnaireDialog;
    private MarqueeVerticalView dishonestyView, recentChangeView;
    private View vRecentChangeParent;
    private TextView mSearcher;
    private View vSearchParent;
    private View vTopSearch;
    private CustomScrollView scrollView;
    private ImageView imageView;
    private RelativeLayout backLayout, titleBarLayout;
    private int padding = 0;
    private RelativeLayout titileBarText;
    private NetWorkErrorView mNetError;
    private ScrollUtil scrollUtil;
    private ImageView loadImage;
    private LinearLayout loading;
    private RelativeLayout vRecentChange;
    private SceneAnimation sceneAnimation;
    private PtrFrameLayout ptrFrameLayout;
    private HomeRefreshHeaderView myHeaderView;
    private QuestionnairDialog mQuestionnairDialog;


    private HomeHotListView homeHotListView;

    protected RollPagerView rollViewPager;
    private NewAddAdapter newAddAdapter;

    protected RelativeLayout layoutHot;
    private ImageView newAddMoreImg;

    private Handler handler;

    public static NewHomeFragment getInstance(int padding) {
        NewHomeFragment homeFragment = new NewHomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("padding", padding);
        homeFragment.setArguments(bundle);

        return homeFragment;
    }

    @Override
    protected void initData() {
        questionnaireDialog = new WagePopBoxDialog(mContext, R.style.tool_dialog);
        mQuestionnairDialog = new QuestionnairDialog(mContext, R.style.tool_dialog);

        padding = getArguments().getInt("padding");
        scrollUtil = new ScrollUtil();
        handler = new Handler();
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_new_home_fragment, null);
        mSearcher = (TextView) view.findViewById(R.id.search);
        vSearchParent = view.findViewById(R.id.vSearchParent);
        vTopSearch = view.findViewById(R.id.vTopSearch);
        dishonestyView = (MarqueeVerticalView) view.findViewById(R.id.marquee_dishonesty);
        recentChangeView = (MarqueeVerticalView) view.findViewById(R.id.marquee_recent_change);
        vRecentChangeParent = view.findViewById(R.id.vRecentChangeParent);

        scrollView = (CustomScrollView) view.findViewById(R.id.scrollview);

        vRecentChange = (RelativeLayout) view.findViewById(R.id.vRecentChange);
        imageView = (ImageView) view.findViewById(R.id.img_top_back);
        backLayout = (RelativeLayout) view.findViewById(R.id.layout_image);
        titleBarLayout = (RelativeLayout) view.findViewById(R.id.layout_titlebar);
        titileBarText = (RelativeLayout) view.findViewById(R.id.search_titile_bar);
        mNetError = (NetWorkErrorView) view.findViewById(R.id.net_work_error);
        loading = (LinearLayout) view.findViewById(R.id.loading);
        loadImage = (ImageView) view.findViewById(R.id.loading_img);
        ptrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.scrollView);
        homeHotListView = (HomeHotListView) view.findViewById(R.id.view_home_hot);
        layoutHot = (RelativeLayout) view.findViewById(R.id.layout_add);
        rollViewPager = (RollPagerView) view.findViewById(R.id.roll_view_pager1);
        newAddMoreImg = (ImageView) view.findViewById(R.id.img_new_add_more);

        return view;
    }

    @Override
    protected void initWeightActions() {
        sceneAnimation = new SceneAnimation(loadImage, 75);
        newAddAdapter = new NewAddAdapter(getActivity(), rollViewPager);
        rollViewPager.setHintView(null);
        rollViewPager.setAdapter(newAddAdapter);
        // 近期变更、企业雷达
        vRecentChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MobclickAgent.onEvent(mContext, "Home92");

                Intent intent = new Intent(mContext, RecentChangeActivity.class);
                Object object = recentChangeView.getCurrentModel();
                if(object == null) return;
                if (object != null && object instanceof HomeRecentModel.RecentBean) {
                    intent.putExtra("bean", new Gson().toJson(object));
                }
                mContext.startActivity(intent);
            }
        });

        titileBarText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext, EventUtils.HOME01);
                Intent intent = new Intent(mContext, TypeSearchActivity.class);
                intent.putExtra(TypeSearchActivity.SEARCH_TYPE, SearchHistoryItemModel.SEARCH_COMMON);
                startActivity(intent);
            }
        });

        mSearcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TypeSearchActivity.class);
                intent.putExtra(TypeSearchActivity.SEARCH_TYPE, SearchHistoryItemModel.SEARCH_COMMON);
                startActivity(intent);

            }
        });
        vTopSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TypeSearchActivity.class);
                intent.putExtra(TypeSearchActivity.SEARCH_TYPE, SearchHistoryItemModel.SEARCH_COMMON);
                startActivity(intent);

            }
        });

        titleBarLayout.getBackground().setAlpha(0);
        titileBarText.setAlpha(0f);
        scrollView.setCallBack(new CustomScrollView.OnScrollListener() {
            @Override
            public void onScrollChangedListener(int leftOfVisibleView, int topOfVisibleView, int oldLeftOfVisibleView, int oldTopOfVisibleView) {
                //TODO 兼容版本有问题，5.0后要计算通知栏高度,暂时设置固定值
                int count = topOfVisibleView * 2;
                if (count <= PhoneUtil.dip2px(mContext, 110)) {
                    vSearchParent.setVisibility(View.VISIBLE);
                    titileBarText.setAlpha(0f);
                    titileBarText.setVisibility(View.GONE);
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) vSearchParent.getLayoutParams();
                    if (layoutParams != null) {
                        int scale = (int) (count * 1f / PhoneUtil.dip2px(mContext, 125)
                                * PhoneUtil.dip2px(mContext, 50));
//                        layoutParams.rightMargin = layoutParams.leftMargin = PhoneUtil.dip2px(mContext, 24) + scale;
                        layoutParams.rightMargin = layoutParams.leftMargin = scale;
                        vSearchParent.setLayoutParams(layoutParams);
                    }
                    titleBarLayout.getBackground().setAlpha(count < 255 ? count : 255);

                } else {
                    titileBarText.setVisibility(View.VISIBLE);
                    titileBarText.setAlpha(1f);
                    titleBarLayout.getBackground().setAlpha(255);
                    vSearchParent.setVisibility(View.INVISIBLE);
                }


            }

            @Override
            public void onPullScroll(int height) {
                scrollUtil.imageScale(backLayout, imageView, height, PhoneUtil.dip2px(mContext, 167));
            }
        });


        titleBarLayout.setPadding(0, padding, 0, 0);


        mQuestionnairDialog.setListener(new QuestionnairDialog.onQuestionActionListener() {
            @Override
            public void onCancel() {
                EventUtils.event(mContext, EventUtils.ADVERT24);
            }

            @Override
            public void onJoin(String id, String url) {
                QuestionnaireSharePreference.setQuestionnaireUrl(mContext, url);
                markJoined(id);
                EventUtils.event(mContext, EventUtils.ADVERT23);
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra(WebActivity.URL, url);
                startActivity(intent);
            }
        });


        getHomeInfo(true);
        mNetError.setListener(new NetWorkErrorView.OnRefreshListener() {
            @Override
            public void OnNetRefresh() {
                getHomeInfo(true);

            }
        });


        // 2.0.6
        myHeaderView = new HomeRefreshHeaderView(mContext);
        myHeaderView.setData(padding);
        ptrFrameLayout.setDurationToCloseHeader(200);
        ptrFrameLayout.setHeaderView(myHeaderView);
        ptrFrameLayout.addPtrUIHandler(myHeaderView);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

//                updateCompanyInfo();

//                MobclickAgent.onEvent(mContext, "Businessdetails04");
                getHomeInfo(false);

            }
        });

        layoutHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HotCompanyActivity.class);
                intent.putExtra(HotCompanyActivity.TYPE, HotCompanyActivity.TYPE_NEW);
                mContext.startActivity(intent);
            }
        });



        TouchUtil.createTouchDelegate(newAddMoreImg, 40);
        ptrFrameLayout.refreshComplete();
        backLayout.setFocusable(true);
        backLayout.setFocusableInTouchMode(true);
        backLayout.requestFocus();


    }

    /**
     * 通知服务器，本次活动已经参加，本活动不再进行显示
     */
    private void markJoined(String id) {
        if (getActivity() == null) {
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        WageInfoAndQuessionInfo.NoticeServer(mContext, getActivity().getLocalClassName(), params, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                BaseModel model = (BaseModel) data;
                if (model.getResult() == 0) {

                }
            }

            @Override
            public void onFail(String error) {

            }
        });

    }

    private void getHomeInfo(final boolean showloading) {

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
        mNetError.setVisibility(View.GONE);
        if (showloading) {
            loading.setVisibility(View.VISIBLE);
//            scrollView.setVisibility(View.GONE);
            sceneAnimation.start();
        }
        final long startTime = System.currentTimeMillis();
        GetHomeInfo.getNewHomeInfo(mContext, params, ((Activity) mContext).getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(final Object data) {
//                handler.sendEmptyMessageDelayed(1, 500);
                long delay = 0;
                if (System.currentTimeMillis() - startTime > 2000) {
                    delay = 0;
                } else {
                    delay = 2000 - (System.currentTimeMillis() - startTime);
                }


                final NewHomeModel model = (NewHomeModel) data;

                if (model.getResult() == 0) {
                    homeHotListView.setData(model);
//                    model.getNewaddlist().clear();
                    if (model.getNewaddlist() != null && model.getNewaddlist().size() > 0) {
                        layoutHot.setVisibility(View.VISIBLE);
                        rollViewPager.setVisibility(View.VISIBLE);
                        newAddAdapter.refresh(model.getNewaddlist());

                    } else {
                        layoutHot.setVisibility(View.GONE);
                        rollViewPager.setVisibility(View.GONE);
                    }
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (showloading) {
                                sceneAnimation.stop();
                                loading.setVisibility(View.GONE);
                            }
                            ptrFrameLayout.refreshComplete();
                            dealRecentChangeModule();
                        }
                    }, delay);
                } else {
                    ptrFrameLayout.refreshComplete();
                    mNetError.setNetWorkError();
                    mNetError.setVisibility(View.VISIBLE);
                    if (showloading) {
                        sceneAnimation.stop();
                        loading.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFail(String error) {
                ptrFrameLayout.refreshComplete();
                mNetError.setNetWorkError();
                mNetError.setVisibility(View.VISIBLE);
                if (showloading) {
                    sceneAnimation.stop();
                    loading.setVisibility(View.GONE);
                }

            }
        });
//        listview.setFocusable(false);
    }


    /**
     * version 2.0.6 问卷调查接口
     */
    private void questionnaire() {
        if (getActivity() == null) {
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        String isFirst = FirstStartAppSharePreference.isFirstStart(mContext) ? "1" : "0";
        params.put("isFirst", isFirst);
        WageInfoAndQuessionInfo.getQuestionnair(mContext, getActivity().getLocalClassName(), params, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                WageInfoModel model = (WageInfoModel) data;
                if (model.getResult() == 0) {
                    //业务逻辑：用户参加活动后不再显示，但是如果重新安装应用启动的话需要显示活动通告
                    if (model.getDataResult() != null && model.getDataResult().getIsshow() != null && model.getDataResult().getHtmlurl() != null) {
                        if (model.getDataResult().getIsshow().equals("0") || FirstStartAppSharePreference.isFirstStart(mContext)) {
                            mQuestionnairDialog.setData(model);
                            mQuestionnairDialog.show();
                        }
                    }
                } else {
//                    Toast.makeText(mContext, model.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail(String error) {

            }
        });

        FirstStartAppSharePreference.saveFirstStart(mContext);

    }

    /**
     * 问卷调查
     */
    private void questionnaireNet() {
        HashMap<String, String> params = new HashMap<>();
        if (InquireApplication.getUserInfo() != null
                && !TextUtils.isEmpty(InquireApplication.getUserInfo().getUserid()))
            params.put("userid", InquireApplication.getUserInfo().getUserid());
        else {
            params.put("userid", "");
        }
        params.put("type", "1");
        WageInfoAndQuessionInfo.getWageInfo(mContext, TAG, params, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                WageInfoModel model = (WageInfoModel) data;
                if (model.getResult() == 0) {
                    if (model.getDataResult() != null && model.getDataResult().getIsshow() != null && model.getDataResult().getHtmlurl() != null) {
                        if (model.getDataResult().getIsshow().equals("0") && !model.getDataResult().getHtmlurl().equals(QuestionnaireSharePreference.getQuestionnaireUrl(mContext))) {
                            FirstStartAppSharePreference.saveFirstQuestionnaire(mContext);
                            questionnaireDialog.setData(model);
                            questionnaireDialog.show();
                        }
                        QuestionnaireSharePreference.setQuestionnaireUrl(mContext, model.getDataResult().getHtmlurl());
                    }
                } else {
                    Toast.makeText(mContext, model.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail(String error) {

            }
        });

    }

    /**
     * 失信榜单
     * <p>
     * 2017年11月24日10:16:19  liuguangdan  隐藏， 不再显示失信榜单
     *
     * @param
     * @param
     */
    @Deprecated
    private void marqueeDishonestyView(HomeDishonestyModel data) {
        if (isDetached()) {
            return;
        }
        if (data == null)
            return;

        if (data.getDishonestylist() == null)
            return;

        List<HomeDishonestyItemModel> dishonestylist = data.getDishonestylist();
        ArrayList<View> list = new ArrayList<>();
        for (final HomeDishonestyItemModel model : dishonestylist) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_marquee_dishonesty, null);
            LinearLayout mDishonestyLayout = (LinearLayout) view.findViewById(R.id.dishonest_layout);
            TextView mDishonestyMonth = (TextView) view.findViewById(R.id.dishonesty_month);
            TextView mDishonestyYear = (TextView) view.findViewById(R.id.dishonest_year);
            TextView mDishonestyContent = (TextView) view.findViewById(R.id.dishonesty_content);
            mDishonestyYear.setText(model.getYear());
            SpannableString spannableString = setTextSize(model.getMonth());
            if (spannableString != null)
                mDishonestyMonth.setText(spannableString);
            mDishonestyContent.setText(model.getTitle());

//            mDishonestyLayout.setBackgroundResource(R.drawable.bg_dishonesty_date_one);
//            mDishonestyYear.setTextColor(getResources().getColor(R.color.color_dishonesty_bg_one));
//            mDishonestyMonth.setTextColor(getResources().getColor(R.color.color_dishonesty_bg_one));
            view.setTag(model.getUrl());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    MobclickAgent.onEvent(mContext, model.getUmeng_analytics());
                    EventUtils.event(mContext, EventUtils.HOME17);
                    String url = v.getTag().toString();
                    Bundle bundle = new Bundle();
                    bundle.putString(WebActivity.URL, url);
                    bundle.putString(WebActivity.TITLE, model.getTitle());
                    goActivity(WebActivity.class, bundle);
                }
            });
            list.add(view);
        }
        dishonestyView.setEnableTumble(false);
        dishonestyView.setFliperViews(list);
        dishonestyView.stopAnim();
        dishonestyView.startAnim(2900, 1900);
    }

    /**
     * 近期变更（近7天）
     *
     * @param
     * @param
     */
    private void dealRecentChangeModule() {
//        if (recentChangeView.getCount() > 0) {
//            return;
//        }

        HashMap<String, String> params = new HashMap<>();
        if (InquireApplication.getUserInfo() != null
                && !TextUtils.isEmpty(InquireApplication.getUserInfo().getUserid()))
            params.put("userid", InquireApplication.getUserInfo().getUserid());
        else {
            params.put("userid", "");
        }
        GetHomeInfo.getRecentChange(mContext, TAG, params, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                HomeRecentModel model = (HomeRecentModel) data;
//                model.setResult(1);
                if (model.getResult() == 0) {

                    // 企业雷达数据为空时不显示
                    if (model.list == null || model.list.isEmpty())
                        return;

                    vRecentChangeParent.setVisibility(View.VISIBLE);

                    List<HomeRecentModel.RecentBean> dishonestylist = model.list;
                    ArrayList<View> list = new ArrayList<>();
                    for (final HomeRecentModel.RecentBean bean : dishonestylist) {

                        String typeValue = AppUtil.getChangeType(bean.type);
                        if (TextUtils.isEmpty(typeValue)) continue; // 过滤不确定的类型

                        View view = LayoutInflater.from(mContext).inflate(R.layout.item_marquee_recent_change, null);
                        RadarCountView radarCountView = (RadarCountView) view.findViewById(R.id.view_radra);
//                        TextView tvType = (TextView) view.findViewById(R.id.tvType);
                        TextView des = (TextView) view.findViewById(R.id.text_des);
                        TextView tvTip = (TextView) view.findViewById(R.id.tvTip);

                        // 1.法人变更 2.股东变更 3.资本变更 4.公司名称 5.经营范围

                        radarCountView.setData(bean.count + "");
//                        tvNum.setText(bean.count);
                        tvTip.setText(bean.title);
                        des.setText("家企业进行了" + bean.title);
//                        tvType.setText(typeValue);
                        view.setTag(bean);

                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(mContext, RecentChangeActivity.class);
                                intent.putExtra("bean", new Gson().toJson(bean));
                                mContext.startActivity(intent);
                            }
                        });
                        list.add(view);
                    }
                    recentChangeView.stopAnim();
                    recentChangeView.setFliperViews(list);
                    recentChangeView.startAnim(2900, 0);
                    recentChangeView.setVisibility(View.VISIBLE);
                } else {
//                    Toast.makeText(mContext, model.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail(String error) {

            }
        });
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
//        dishonestyView.startAnim(2900, 1900);
        recentChangeView.startAnim(2900, 1900);
    }

    /**
     * 停止掉所有动画。
     * <p/>
     * 当前页不在最前端的时候，关闭。
     */
    private void stopAllAnim() {
//        dishonestyView.stopAnim();
        recentChangeView.stopAnim();
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

//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.e("tag", "onResumeonResume1");
//        stopAllAnim();
//        startAllAnim();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        Log.e("tag", "onResumeonResume2");
//        KeyBoardUtil.hideSoftKeyboard(getActivity());
//        stopAllAnim();
//    }


//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            //questionnaireNet();
//            questionnaire();
//        }
//    };

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof FlipperAnimEvent) {
            if (((FlipperAnimEvent) event).isStart) {
                stopAllAnim();
                startAllAnim();
            } else {
                stopAllAnim();
            }
        }
    }
}
