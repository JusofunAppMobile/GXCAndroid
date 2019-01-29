package com.gxc.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.inter.OnCompleteListener;
import com.gxc.utils.LogUtils;
import com.siccredit.guoxin.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener, OnCompleteListener {

    protected RecyclerView recyclerView;

    protected BaseQuickAdapter adapter;

    protected SwipeRefreshLayout refreshLayout;

    private Context context;

    private View vEmpty;
    private TextView tvEmpty, tvError, tvReload;
    private ImageView ivEmpty;

    public final static int INIT_PAGE_INDEX = 1;

    protected int pageIndex = INIT_PAGE_INDEX;
    protected int pageSize = 20;

    private boolean isLoadingData = false;


    protected abstract BaseQuickAdapter getAdapter();


    private boolean isLoadMoreData = false;

    /**
     * 初始化UI,在主线程
     */
    protected abstract void initUi();

    /**
     * 开始异步请求数据，数据请求完成后调用 completeLoadData 加载数据
     */
    protected abstract void startLoadData();

    @Override
    public void initActions() {

    }

    protected boolean isAutoLoad() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(getLayoutId());
        ButterKnife.bind(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        vEmpty = findViewById(R.id.vEmpty);
        tvEmpty = (TextView) findViewById(R.id.tvEmpty);
        tvError = (TextView) findViewById(R.id.tvError);
        tvReload = (TextView) findViewById(R.id.tvReload);
        ivEmpty = findViewById(R.id.ivEmpty);
        adapter = getAdapter();

        if (recyclerView == null)
            throw new RuntimeException("not found R.id.recycle.");

        if (adapter == null)
            throw new RuntimeException("adapter can not be null.");

        adapter.setEnableLoadMore(isLoadMoreEnable());
        if (isLoadMoreEnable())
            adapter.setOnLoadMoreListener(this, recyclerView);

        if (!isLoadMoreEnable())
            pageSize = Integer.MAX_VALUE;

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!isLoadingData)
                    BaseListActivity.this.onItemClick(adapter, view, position);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(getLayoutManager());

        if (refreshLayout != null) {
            refreshLayout.setColorSchemeColors(getResources().getColor(R.color.common));
            refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);

            refreshLayout.setOnRefreshListener(this);
            refreshLayout.setRefreshing(true);
        }

        if (vEmpty != null) {
            vEmpty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refresh();
                }
            });
        }
        initUi();
        if (isAutoLoad())
            onRefresh();
    }


    public int getLayoutId() {
        return R.layout.act_recycler_common;
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(context);
    }

    @Override
    public void completeLoadData(List list, int totalCount) {
        completeLoadData(list, false, totalCount);
    }

    /**
     * 网络错误时调用
     */
    @Override
    public void completeLoadDataError() {
        completeLoadData(null, true, 0);
    }

    protected String getEmptyTipText() {
        return "没有查找到相关信息";
    }

    protected String getHttpErrorTip() {
        return "网络奔溃了";
    }

    protected boolean isLoadMoreEnable() {
        return true;
    }

    protected void showEmptyView(boolean isError) {
        String text = isError ? getHttpErrorTip() : getEmptyTipText();
        adapter.setNewData(new ArrayList());
        if (vEmpty != null) {
            vEmpty.setVisibility(View.VISIBLE);
            tvError.setText(text);
            if (isError) {
                tvEmpty.setVisibility(View.VISIBLE);
                tvReload.setVisibility(View.VISIBLE);
            } else {
                tvEmpty.setVisibility(View.GONE);
                tvReload.setVisibility(View.GONE);
            }
        }
    }

    /**
     * @param list
     * @param isHttpError
     * @param count       count 每页总数，如果为0， 按返回数据条数 和 pageSize 对比
     */
    private void completeLoadData(List list, boolean isHttpError, int count) {
        refreshLayout.setRefreshing(false);
        if (isHttpError) {
            if (isLoadMoreData)
                adapter.loadMoreFail();
            if (pageIndex == INIT_PAGE_INDEX)
                showEmptyView(true);
        } else {
            if (list == null || list.isEmpty()) {
                if (pageIndex == INIT_PAGE_INDEX)
                    showEmptyView(false);
                else
                    adapter.loadMoreEnd(true);
            } else {
                if (isLoadMoreData)
                    adapter.addData(list);
                else
                    adapter.setNewData(list);

                if ((count != 0 && adapter.getItemCount() >= count) || (count == 0 && list.size() < pageSize))
                    adapter.loadMoreEnd(true);
                else
                    adapter.loadMoreComplete();
                pageIndex++;
            }
        }

        if (isLoadMoreData)
            refreshLayout.setEnabled(true);
        else {
            if (isLoadMoreEnable())
                adapter.setEnableLoadMore(true);
        }

        isLoadMoreData = false;
        if (isLoadingData)
            isLoadingData = false;
    }

    private void reset() {
        pageIndex = INIT_PAGE_INDEX;
    }

    public void refresh() {
        refreshLayout.setRefreshing(true);
        vEmpty.setVisibility(View.GONE);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        if (isLoadingData)
            return;
        isLoadingData = true;
        reset();
        LogUtils.e(">>>正在刷新, pageIndex=" + pageIndex);
        adapter.setEnableLoadMore(false);
        delayLoadData();
    }


    private void delayLoadData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startLoadData();
            }
        }, 800);
    }

    @Override
    public void onLoadMoreRequested() {
        if (isLoadingData)
            return;
        isLoadingData = true;
        LogUtils.e(">>>加载更多, pageIndex=" + pageIndex);
        isLoadMoreData = true;
        refreshLayout.setEnabled(false);
        delayLoadData();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
