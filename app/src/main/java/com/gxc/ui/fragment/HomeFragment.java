package com.gxc.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseFragment;
import com.gxc.model.HomeMenuModel;
import com.gxc.model.HomeModel;
import com.gxc.model.HomeMonitorModel;
import com.gxc.model.HomeNewsModel;
import com.gxc.model.UserModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.activity.HomeActivity;
import com.gxc.ui.activity.LoginActivity;
import com.gxc.ui.activity.MonitorDetailActivity;
import com.gxc.ui.activity.MoreNewsListActivity;
import com.gxc.ui.activity.PayActivity;
import com.gxc.ui.activity.RelationActivity;
import com.gxc.ui.activity.RiskTipActivity;
import com.gxc.ui.activity.WebActivity;
import com.gxc.ui.adapter.HomeMenuAdapter;
import com.gxc.ui.adapter.HomeMonitorAdapter;
import com.gxc.ui.adapter.HomeNewsAdapter;
import com.gxc.ui.adapter.ImagePagerAdapter;
import com.gxc.ui.dialog.VIPDialog;
import com.gxc.ui.widgets.AutoScrollViewPager;
import com.gxc.ui.widgets.MyCirclePageIndicator;
import com.gxc.ui.widgets.NavTitleView;
import com.gxc.utils.AppUtils;
import com.gxc.utils.ToastUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.SearchHistoryItemModel;
import com.jusfoun.jusfouninquire.ui.activity.SearchResultActivity;
import com.jusfoun.jusfouninquire.ui.activity.TypeSearchActivity;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author liuguangdan
 * @version create at 2019/1/4/004 19:39
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.pager)
    AutoScrollViewPager pager;
    @BindView(R.id.menuRecycler)
    RecyclerView menuRecycler;
    @BindView(R.id.monitorRecycler)
    RecyclerView monitorRecycler;
    @BindView(R.id.newsRecycler)
    RecyclerView newsRecycler;
    @BindView(R.id.indicator)
    MyCirclePageIndicator indicator;
    @BindView(R.id.vInput)
    View tvInput;
    @BindView(R.id.vTop2)
    View vTop2;
    @BindView(R.id.tvInput2)
    View tvInput2;

    HomeMenuAdapter homeMenuAdapter;
    HomeMonitorAdapter homeMonitorAdapter;
    HomeNewsAdapter homeNewsAdapter;
    @BindView(R.id.monitorNav)
    NavTitleView monitorNav;
    @BindView(R.id.newsNav)
    NavTitleView newsNav;
    @BindView(R.id.vHot)
    LinearLayout vHot;

    private int crisisY;

    private HomeModel homeModel;

    private HomeActivity homeActivity;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_home;
    }

    @Override
    protected void initView() {
        homeActivity = (HomeActivity) getActivity();
        homeMenuAdapter = new HomeMenuAdapter();
        menuRecycler.setAdapter(homeMenuAdapter);
        menuRecycler.setLayoutManager(new GridLayoutManager(activity, 4));
        homeMenuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                menuItemClick(homeMenuAdapter.getItem(i), i);
            }
        });

        homeMonitorAdapter = new HomeMonitorAdapter();
        monitorRecycler.setAdapter(homeMonitorAdapter);
        monitorRecycler.setLayoutManager(new LinearLayoutManager(activity));
        homeMonitorAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                monitorItemClick(homeMonitorAdapter.getItem(i), i);
            }
        });

        homeNewsAdapter = new HomeNewsAdapter(activity);
        newsRecycler.setAdapter(homeNewsAdapter);
        newsRecycler.setLayoutManager(new LinearLayoutManager(activity));
        homeNewsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                newsItemClick(homeNewsAdapter.getItem(i), i);
            }
        });

        scrollView.setNestedScrollingEnabled(false);

        monitorNav.setImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(MoreMonitorListActivity.class);
                homeActivity.selectTab(1);
            }
        });

        newsNav.setImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MoreNewsListActivity.class);
            }
        });

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (crisisY == 0)
                    crisisY = tvInput.getBottom() - vTop2.getHeight();
                if (crisisY > 0) {
                    doAnim(scrollY > crisisY);
                }
            }
        });

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    pager.stopAutoScroll();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    pager.startAutoScroll();
                }
                return false;
            }
        });

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                vTop2.setSelected(true);
                doAnim(false, 10);
            }
        }, 300);

        loadData();
    }

    private void initAutoPager() {
        ViewGroup parent = (ViewGroup) pager.getParent();
        parent.removeView(pager);

        pager.setAdapter(new ImagePagerAdapter(activity, homeModel.adImages).setInfiniteLoop(true));
        pager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % homeModel.adImages.size());
        pager.setInterval(5000);
        indicator.setViewPager(pager);
        indicator.setFillColor(Color.parseColor("#A3A3A3"));
        indicator.setPageColor(Color.parseColor("#464646"));
        indicator.setSnap(true);
        indicator.setRadius(8);
        indicator.setStrokeWidth(0);
        indicator.setPointPadding(4);
        pager.startAutoScroll();

        parent.addView(pager, 0);
    }

    private void verifyMenuList() {
        if (homeModel == null || homeModel.menu == null || homeModel.menu.isEmpty()) return;
        Iterator<HomeMenuModel> iterator = homeModel.menu.iterator();
        while (iterator.hasNext()) {
            HomeMenuModel menu = iterator.next();
            if (!isMenuTypeValid(menu))
                iterator.remove();
        }
    }

    /**
     * 验证menuType 是否有效
     * // 1：股东高管 2：主营产品 3：失信查询 4：查税号 5：招聘 6：企业通讯录 7：查关系  8：风险分析   11 ：地址电话 15 ：中标信息  16：裁判文书
     * 2019年1月17日16:36:35
     *
     * @param model
     * @return
     */
    private boolean isMenuTypeValid(HomeMenuModel model) {
        int[] types = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 11, -1};
        for (int type : types)
            if (type == model.menuType) return true;
        return false;
    }

    private void loadData() {
        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        RxManager.http(RetrofitUtils.getApi().getHomeData(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    homeModel = model.dataToObject(HomeModel.class);
                    verifyMenuList();
                    buildView();
                } else {
                    showToast(model.msg);
                }
            }

            @Override
            public void error() {
                hideLoadDialog();
                ToastUtils.showHttpError();
            }
        });
    }

    private void buildView() {
        if (homeModel != null) {
            homeMenuAdapter.setNewData(homeModel.menu);
            if (homeModel.adImages != null && !homeModel.adImages.isEmpty())
                initAutoPager();
            homeMonitorAdapter.addData(homeModel.monitor);
            homeNewsAdapter.addData(homeModel.news);
            buildHotSearch(homeModel.keywords);
        }
    }

    /**
     * 热搜，需要延时操作
     */
    private void buildHotSearch(List<String> list) {
        int maxWidth = vHot.getWidth();
        int curWidth = 0;
        for (final String val : list) {
            View view = View.inflate(activity, R.layout.view_hot_search, null);
            TextView text = view.findViewById(R.id.text);
            text.setText(val);
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(w, h);
            int width = view.getMeasuredWidth();
            if (curWidth + width > maxWidth)
                break;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    startActivity(TypeSearchActivity.getIntent(activity, 0));

                    Intent intent = new Intent(activity, SearchResultActivity.class);
//                    intent.putExtra(SearchResultActivity.SEARCH_RESULT, model);
                    intent.putExtra(SearchResultActivity.SEARCH_KEY, val);
                    intent.putExtra(SearchResultActivity.SEARCH_TYPE, SearchHistoryItemModel.SEARCH_COMMON);
                    startActivity(intent);
                }
            });
            vHot.addView(view);
            curWidth += width;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (homeModel != null)
            pager.startAutoScroll();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (pager.isBorderAnimation())
            pager.stopAutoScroll();
    }


    private void doAnim(boolean show) {
        doAnim(show, 200);
    }

    private void doAnim(boolean show, int duration) {

        if (show) {
            if (vTop2.isSelected()) return;
            vTop2.setSelected(true);
            PropertyValuesHolder translate = PropertyValuesHolder.ofFloat("translationY", -vTop2.getHeight(), 0);
            PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
            ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(vTop2, translate, alpha);
            animator.setDuration(duration);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.start();
        } else {
            if (!vTop2.isSelected()) return;
            vTop2.setSelected(false);
            PropertyValuesHolder translate = PropertyValuesHolder.ofFloat("translationY", 0, -vTop2.getHeight());
            PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", vTop2.getAlpha(), 0f);
            ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(vTop2, translate, alpha);
            animator.setDuration(duration);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.start();
        }
    }

    // 1：股东高管 2：主营产品 3：地址电话 4：失信查询 5：查税号 6：招聘 7：企业通讯录 8：查关系(待定) 9：风险分析(待定) -1: h5跳转
    // 1：股东高管 2：主营产品 3：失信查询 4：查税号 5：招聘 6：企业通讯录 7：查关系  8：风险分析 15 ：中标信息  16：裁判文书
    //17：行政处罚  18：商标查询
    private void menuItemClick(HomeMenuModel model, int position) {
        UserModel user = AppUtils.getUser();
        if (model.menuType == 7) { // 查关系
            if (user == null) {
                startActivity(LoginActivity.class);
                return;
            }
            if (user.vipStatus == 0)
                new VIPDialog(activity).show();
            else
                startActivity(RelationActivity.getIntent(activity, model));
        } else if (model.menuType == 8) { // 风险分析
            if (user == null) {
                startActivity(LoginActivity.class);
                return;
            }
            if (user.vipStatus == 0) {
                startActivity(RiskTipActivity.class);
                return;
            } else
                startActivity(TypeSearchActivity.getIntent(activity, model.menuType));
        } else if (model.menuType == -1) {// h5跳转
            startActivity(WebActivity.getIntent(activity, model.menuName, model.menuUrl));
        } else {
            startActivity(TypeSearchActivity.getIntent(activity, model.menuType));
        }
    }

    @OnClick({R.id.tvVip})
    public void goVip() {
        UserModel user = AppUtils.getUser();
        if (user == null) {
            startActivity(LoginActivity.class);
            return;
        }
        startActivity(PayActivity.class);
    }

    @OnClick({R.id.vInput, R.id.tvInput2})
    public void click() {
        startActivity(TypeSearchActivity.getIntent(activity, 0));
    }

    private void monitorItemClick(HomeMonitorModel model, int position) {
        startActivity(MonitorDetailActivity.getIntent(activity, model.companyId, model.companyName));
    }

    private void newsItemClick(HomeNewsModel model, int position) {
        startActivity(WebActivity.getIntent(activity, "行业资讯", homeNewsAdapter.getItem(position).newsURL));
    }
}
