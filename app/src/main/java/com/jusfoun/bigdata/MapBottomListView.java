package com.jusfoun.bigdata;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jusfoun.bigdata.xrecycleview.XRecyclerView;
import com.siccredit.guoxin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 附近 地图 底部 出现列表的view
 */
public class MapBottomListView extends LinearLayout {

    private XRecyclerView mRecyclerView;
    private TextView currentAll_Company, distanceCompany;
    private ImageView packUp_ListView;
    private RelativeLayout no_dataLayout;

    private CompanyListMapAdapter mAdapter;

    private List<XListDataModel> companyList = new ArrayList<>();

    public MapBottomListView(Context context) {
        super(context);
        initView(context);
    }

    public MapBottomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MapBottomListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.map_bottom_list_view, this, true);
        mRecyclerView = (XRecyclerView) view.findViewById(R.id.recyclerView_nearby_map);
        currentAll_Company = (TextView) view.findViewById(R.id.currentAll_Company);
        distanceCompany = (TextView) view.findViewById(R.id.distance_Company);
        packUp_ListView = (ImageView) view.findViewById(R.id.packUp_ListView);

        no_dataLayout = (RelativeLayout) view.findViewById(R.id.no_dataLayout);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new CompanyListMapAdapter(context, companyList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                onClickLisner.bottomListViewRefresh();
            }

            @Override
            public void onLoadMore() {
                onClickLisner.bottomListViewLoadMore();
            }
        });
        mAdapter.setOnItemViewClickListener(new BaseRecyclerAdapter.OnItemViewClickListener<XListDataModel>() {
            @Override
            public void onItemClick(XListDataModel xListDataModel, int position) {
                if (onClickLisner != null) {
                    onClickLisner.onClick(xListDataModel);
                }
            }
        });


        packUp_ListView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickLisner != null)
                    onClickLisner.onPackUpListClick();
            }
        });

        no_dataLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onErrorViewClickLisner.onClick();
            }
        });

    }

    /**
     * 控制是否显示无数据的图层
     *
     * @param isShow
     */
    public void isShowNoDataLayout(boolean isShow) {
        if (isShow) {
            no_dataLayout.setVisibility(View.VISIBLE);
            mAdapter.clear();
        } else {
            no_dataLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 刷新数据调用
     *
     * @param model
     */
    public void setData(XListModel model, int pageNumber) {
        if (pageNumber == 1) {
            companyList.clear();
        }
        companyList.addAll(model.getData());
        mAdapter.setData(companyList);

        boolean hasNextPage = mAdapter.getItemCount() < model.getTotal();
        if (hasNextPage) {
            mRecyclerView.setLoadingMoreEnabled(true);
        } else {
            mRecyclerView.setLoadingMoreEnabled(false);
        }
    }

    /**
     * 摄者当前坐标的企业数量
     *
     * @param currentAllCompany
     */
    public void setCurrentAll_Company(String currentAllCompany) {
        currentAll_Company.setText(currentAllCompany);
    }

    /**
     * 控制当前距离的显示与否
     *
     * @param distance
     */
    public void setDistanceCompany(String distance) {
        if (!TextUtils.isEmpty(distance)) {
            distanceCompany.setVisibility(View.VISIBLE);
        } else {
            distanceCompany.setVisibility(View.GONE);
        }
    }


    /**
     * 处理加载完或刷新完成时停止加载和刷新的等待
     */
    public void onButtomLoadFinish() {
        mRecyclerView.refreshComplete();
        mRecyclerView.loadMoreComplete();
    }

    public OnBottomListViewItemClickListener onClickLisner;

    public void setOnBottemListViewItemClickLisner(OnBottomListViewItemClickListener onItemClickLisner) {
        this.onClickLisner = onItemClickLisner;
    }

    public interface OnBottomListViewItemClickListener {
        /**
         * listview item 的点击事件处理
         *
         * @param object
         */
        void onClick(Object object);

        /**
         * 收起buttomListview 的事件
         */
        void onPackUpListClick();

        /**
         * buttom ListView 的下拉刷新操作事件
         */
        void bottomListViewRefresh();

        /**
         * buttom ListView 的加载更多的操作事件
         */
        void bottomListViewLoadMore();

    }

    private OnErrorViewClickLisner onErrorViewClickLisner;

    public void setOnErrorViewClickLisner(OnErrorViewClickLisner onErrorViewClickLisner) {
        this.onErrorViewClickLisner = onErrorViewClickLisner;
    }

    public interface OnErrorViewClickLisner {
        void onClick();
    }

}
