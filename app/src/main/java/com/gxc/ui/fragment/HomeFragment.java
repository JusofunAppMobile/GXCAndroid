package com.gxc.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseFragment;
import com.gxc.model.HomeMenuModel;
import com.gxc.model.HomeMonitorModel;
import com.gxc.model.HomeNewsModel;
import com.gxc.ui.activity.MoreMonitorListActivity;
import com.gxc.ui.activity.MoreNewsListActivity;
import com.gxc.ui.activity.PayActivity;
import com.gxc.ui.activity.RelationActivity;
import com.gxc.ui.activity.RiskListActivity;
import com.gxc.ui.activity.RiskTipActivity;
import com.gxc.ui.activity.WebActivity;
import com.gxc.ui.adapter.HomeMenuAdapter;
import com.gxc.ui.adapter.HomeMonitorAdapter;
import com.gxc.ui.adapter.HomeNewsAdapter;
import com.gxc.ui.adapter.ImagePagerAdapter;
import com.gxc.ui.widgets.AutoScrollViewPager;
import com.gxc.ui.widgets.MyCirclePageIndicator;
import com.gxc.ui.widgets.NavTitleView;
import com.gxc.utils.AppUtils;
import com.gxc.utils.LogUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.SearchHistoryItemModel;
import com.jusfoun.jusfouninquire.ui.activity.TypeSearchActivity;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;

import java.util.ArrayList;
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

    @Override
    protected int getLayoutId() {
        return R.layout.frag_home;
    }

    @Override
    protected void initView() {
        homeMenuAdapter = new HomeMenuAdapter();
        List<HomeMenuModel> list = new ArrayList<>();
        list.add(new HomeMenuModel("股东高管", 1));
        list.add(new HomeMenuModel("主营产品", 2));
        list.add(new HomeMenuModel("失信查询", 3));
        list.add(new HomeMenuModel("查关系", 4));
        list.add(new HomeMenuModel("风险分析", 5));
        list.add(new HomeMenuModel("查税号", 6));
        list.add(new HomeMenuModel("招聘", 7));
        list.add(new HomeMenuModel("全部", 0));
        homeMenuAdapter.addData(list);
        menuRecycler.setAdapter(homeMenuAdapter);
        menuRecycler.setLayoutManager(new GridLayoutManager(activity, 4));
        homeMenuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                menuItemClick(homeMenuAdapter.getItem(i), i);
            }
        });

        homeMonitorAdapter = new HomeMonitorAdapter();
        homeMonitorAdapter.addData(AppUtils.getTestList(HomeMonitorModel.class, 3));
        monitorRecycler.setAdapter(homeMonitorAdapter);
        monitorRecycler.setLayoutManager(new LinearLayoutManager(activity));
        homeMonitorAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                monitorItemClick(homeMonitorAdapter.getItem(i), i);
            }
        });

        homeNewsAdapter = new HomeNewsAdapter(activity);
        homeNewsAdapter.addData(AppUtils.getTestList(HomeNewsModel.class, 5));
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
                startActivity(MoreMonitorListActivity.class);
            }
        });

        newsNav.setImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MoreNewsListActivity.class);
            }
        });

        List<String> imageIdList = new ArrayList<>();
        imageIdList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546931637746&di=b6f2a88e293aca04290c2c80dd429dbf&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fe7cd7b899e510fb31dfc8516d433c895d0430c4d.jpg");
        imageIdList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546931637746&di=b223981d4037353228647ad303232344&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F58ee3d6d55fbb2fbbc6b4796424a20a44723dcf6.jpg");
        imageIdList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546931637745&di=c2b4a56050c1864bca1d11a2a326c40e&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F1c950a7b02087bf49661186dffd3572c10dfcfa1.jpg");
        pager.setAdapter(new ImagePagerAdapter(activity, imageIdList).setInfiniteLoop(true));

        pager.setInterval(5000);
        pager.startAutoScroll();
        pager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % AppUtils.getSize(imageIdList));
        indicator.setViewPager(pager);
        indicator.setFillColor(Color.parseColor("#A8ACB0"));
        indicator.setPageColor(Color.WHITE);
        indicator.setSnap(true);
        indicator.setRadius(8);
        indicator.setStrokeWidth(0);
        indicator.setPointPadding(4);

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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                buildHotSearch();
            }
        }, 500);
    }

    /**
     * 热搜，需要延时操作
     */
    private void buildHotSearch() {
        String[] arr = new String[]{"马云1", "马云2", "马云3", "马化腾4", "马云5", "雷军6", "马云7", "马云8"};
        int maxWidth = vHot.getWidth();
        int curWidth = 0;
        for (final String val : arr) {
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
                    showToast(val);
                }
            });
            vHot.addView(view);
            curWidth += width;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        pager.startAutoScroll();
    }

    @Override
    public void onStop() {
        super.onStop();
        pager.stopAutoScroll();
    }


    private void doAnim(boolean show) {
        doAnim(show, 200);
    }

    private void doAnim(boolean show, int duration) {

        if (show) {
            if (vTop2.isSelected()) return;
            vTop2.setSelected(true);
            LogUtils.e(">>>>>显示动画...");
            PropertyValuesHolder translate = PropertyValuesHolder.ofFloat("translationY", -vTop2.getHeight(), 0);
            PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
            ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(vTop2, translate, alpha);
            animator.setDuration(duration);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.start();
        } else {
            if (!vTop2.isSelected()) return;
            vTop2.setSelected(false);
            LogUtils.e(">>>>>隐藏动画...");
            PropertyValuesHolder translate = PropertyValuesHolder.ofFloat("translationY", 0, -vTop2.getHeight());
            PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", vTop2.getAlpha(), 0f);
            ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(vTop2, translate, alpha);
            animator.setDuration(duration);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.start();
        }
    }

    // 1：股东高管 2：主营产品 3：失信查询 4：查关系 5：风险分析 6：查税号 7：招聘
    private void menuItemClick(HomeMenuModel model, int position) {
        Intent intent = new Intent(activity, TypeSearchActivity.class);
        String type = null;
        switch (model.menuType) {
            case 1:// 股东高管
                type = SearchHistoryItemModel.SEARCH_SHAREHOLDER;
                break;
            case 2:// 主营产品
                type = SearchHistoryItemModel.SEARCH_PRODUCT;
                break;
            case 3:// 失信查询
                type = SearchHistoryItemModel.SEARCH_DISHONEST;
                break;
            case 4:// 查关系
                startActivity(RelationActivity.class);
                return;
            case 5:// 风险分析
                startActivity(RiskTipActivity.class);
                startActivity(RiskListActivity.class);
                return;
            case 6:// 查税号
                type = SearchHistoryItemModel.SEARCH_TAXID;
                break;
            case 7:// 招聘
                type = SearchHistoryItemModel.SEARCH_RECRUITMENT;
                break;
            case 0:// 全部
                return;
        }
        intent.putExtra(TypeSearchActivity.SEARCH_TYPE, type);
        startActivity(intent);
    }

    @OnClick({R.id.tvVip, R.id.tvInput2})
    public void goVip() {
        startActivity(PayActivity.class);
    }

    @OnClick({R.id.vInput})
    public void click() {
        Intent intent = new Intent(activity, TypeSearchActivity.class);
        intent.putExtra(TypeSearchActivity.SEARCH_TYPE, SearchHistoryItemModel.SEARCH_COMMON);
        startActivity(intent);
    }

    private void monitorItemClick(HomeMonitorModel model, int position) {

    }

    private void newsItemClick(HomeNewsModel model, int position) {
        startActivity(WebActivity.getIntent(activity, "行业资讯", AppUtils.TEST_URL));
    }
}
