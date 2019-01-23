package com.gxc.ui.activity;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListActivity;
import com.gxc.impl.ListResponseCall;
import com.gxc.model.HomeModel;
import com.gxc.model.HomeNewsModel;
import com.gxc.model.NewsAdModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.adapter.HomeNewsAdapter;
import com.gxc.ui.adapter.ImagePagerAdapter;
import com.gxc.ui.widgets.AutoScrollViewPager;
import com.gxc.ui.widgets.MyCirclePageIndicator;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * @author liuguangdan
 * @version create at 2019/1/5/005 19:55
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class MoreNewsListActivity extends BaseListActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.titleView)
    TitleView titleView;

    private AutoScrollViewPager pager;
    private MyCirclePageIndicator indicator;
    private View headView;
    private TextView tvPagerTitle;

    private List<NewsAdModel> newsAdList;

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new HomeNewsAdapter(this);
    }

    @Override
    public int getLayoutId() {
        return super.getLayoutId();
    }

    @Override
    protected void initUi() {
        titleView.setTitle("行业资讯");
        titleView.setBackgroudRed();
        titleView.hideLineView();
        setStatusBarRed();

        headView = View.inflate(activity, R.layout.view_news_header, null);
        pager = headView.findViewById(R.id.pager);
        tvPagerTitle = headView.findViewById(R.id.tvPagerTitle);
        indicator = headView.findViewById(R.id.indicator);

        pager.setInterval(5000);
        indicator.setFillColor(Color.parseColor("#A3A3A3"));
        indicator.setPageColor(Color.parseColor("#464646"));
        indicator.setSnap(true);
        indicator.setRadius(8);
        indicator.setStrokeWidth(0);
        indicator.setPointPadding(4);
        indicator.setOnPageChangeListener(this);
    }

    private void buildViewPager() {
        ViewGroup parent = (ViewGroup) pager.getParent();
        if (parent != null)
            parent.removeView(pager);

        List<HomeModel.AdverModel> list = new ArrayList<>();
        for (NewsAdModel adModel : newsAdList)
            list.add(new HomeModel.AdverModel(adModel.newsImage, adModel.newsURL));
        pager.setAdapter(new ImagePagerAdapter(activity, list).setInfiniteLoop(true));
        pager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % list.size());
        indicator.setViewPager(pager);
        pager.stopAutoScroll();
        pager.startAutoScroll();

        if (pager.getParent() == null)
            parent.addView(pager, 0);

        tvPagerTitle.setText(newsAdList.get(0).newsName);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (pager != null)
            pager.startAutoScroll();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (pager != null && pager.isBorderAnimation())
            pager.stopAutoScroll();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        startActivity(WebActivity.getIntent(activity, "行业资讯", ((HomeNewsModel)adapter.getItem(position)).newsURL));
    }

    @Override
    public boolean isBarDark() {
        return false;
    }

    @Override
    protected void showEmptyView(boolean isError) {
        if (headView == null || headView.getParent() == null)
            super.showEmptyView(isError);
    }

    @Override
    protected void startLoadData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageSize", pageSize);
        map.put("pageIndex", pageIndex);
        RxManager.http(RetrofitUtils.getApi().moreNews(map), new ListResponseCall(this) {

            @Override
            public List getList(NetModel model) {
                return model.dataToList("news", HomeNewsModel.class);
            }

            @Override
            public void success(NetModel model) {
                if (model.success()) {
                    if (pageIndex == 1) {
                        newsAdList = model.dataToList("rollNews", NewsAdModel.class);
                        if (newsAdList != null && !newsAdList.isEmpty() && pageIndex == 1 && headView.getParent() == null)
                            adapter.addHeaderView(headView);
                        if (pageIndex == 1 && (newsAdList == null || newsAdList.isEmpty()))
                            adapter.removeHeaderView(headView);
                        if (newsAdList != null && !newsAdList.isEmpty() && pageIndex == 1)
                            buildViewPager();
                    }
                } else {
                    adapter.removeHeaderView(headView);
                }
                super.success(model);
            }
        });
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (newsAdList != null && !newsAdList.isEmpty() && newsAdList.size() > i) {
            NewsAdModel model = newsAdList.get(i);
            tvPagerTitle.setText(model.newsName);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
