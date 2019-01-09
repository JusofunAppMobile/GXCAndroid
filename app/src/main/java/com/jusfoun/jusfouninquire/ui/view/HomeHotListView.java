package com.jusfoun.jusfouninquire.ui.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.HomeDataItemModel;
import com.jusfoun.jusfouninquire.net.model.NewHomeModel;
import com.jusfoun.jusfouninquire.ui.activity.CompanyDetailActivity;
import com.jusfoun.jusfouninquire.ui.activity.HotCompanyActivity;
import com.jusfoun.jusfouninquire.ui.adapter.HotCompanyListAdapter;

import netlib.util.EventUtils;

/**
 * @author zhaoyapeng
 * @version create time:17/12/2817:02
 * @Email zyp@jusfoun.com
 * @Description ${首页热门企业}
 */
public class HomeHotListView extends BaseView {
    protected ImageView imgIconFlagHot;
    protected TextView textHotCompany;
    protected RelativeLayout layoutHot;
    protected HotCompanyListView listviewHot;
    private HotCompanyListAdapter adapter;

    public HomeHotListView(Context context) {
        super(context);
    }

    public HomeHotListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeHotListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initData() {
        adapter = new HotCompanyListAdapter(mContext, true);
    }

    @Override
    protected void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_home_hot, this, true);
        imgIconFlagHot = (ImageView) findViewById(R.id.img_icon_flag_hot);
        textHotCompany = (TextView) findViewById(R.id.text_hot_company);
        layoutHot = (RelativeLayout) findViewById(R.id.layout_hot);
        listviewHot = (HotCompanyListView) findViewById(R.id.listview_hot);
    }

    @Override
    protected void initActions() {
        listviewHot.setAdapter(adapter);
        listviewHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeDataItemModel homeDataItemModel = (HomeDataItemModel) parent.getItemAtPosition(position);
                if (homeDataItemModel == null)
                    return;
                EventUtils.event(mContext, EventUtils.HOME15);
                Intent intent = new Intent(mContext, CompanyDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(CompanyDetailActivity.COMPANY_ID, homeDataItemModel.getCompanyid());
                bundle.putString(CompanyDetailActivity.COMPANY_NAME, homeDataItemModel.getCompanyname());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        layoutHot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HotCompanyActivity.class);
                intent.putExtra(HotCompanyActivity.TYPE, HotCompanyActivity.TYPE_HOT);
                mContext.startActivity(intent);
            }
        });
        listviewHot.setFooterDividersEnabled(false);
    }

    public void setData(NewHomeModel model ){
        if (model.getHotlist() != null && model.getHotlist().size() > 0) {
            setVisibility(VISIBLE);
            adapter.refersh(model.getHotlist());
            setListViewHeightBasedOnChildren(listviewHot);
        }else{
            setVisibility(GONE);
        }

    }


    public void setListViewHeightBasedOnChildren(final ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);

        if (listAdapter.getCount() == 0) {
            listView.setVisibility(View.GONE);
        } else {
            listView.setVisibility(View.VISIBLE);
        }
        Log.e("tag", "totalHeight=" + totalHeight + " " + listAdapter.getCount());

    }
}
