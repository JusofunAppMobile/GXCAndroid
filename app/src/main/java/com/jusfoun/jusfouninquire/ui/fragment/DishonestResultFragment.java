package com.jusfoun.jusfouninquire.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListFragment;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.DisHonestItemModel;
import com.jusfoun.jusfouninquire.net.model.SearchDisHonestModel;
import com.jusfoun.jusfouninquire.net.route.SearchRequestRouter;
import com.jusfoun.jusfouninquire.service.event.DishonestResultEvent;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.ui.activity.SearchResultActivity;
import com.jusfoun.jusfouninquire.ui.activity.WebActivity;
import com.jusfoun.jusfouninquire.ui.adapter.SearchDishonestAdapter;

import java.util.HashMap;

import netlib.util.EventUtils;

/**
 * Author  wangchenchen
 * CreateDate 2016/8/31.
 * Email wcc@jusfoun.com
 * Description
 */
public class DishonestResultFragment extends BaseListFragment {
    public static final String TYPE = "type";
    public static final String MODEL = "model";
    private String province = "";
    private int type;
    private String searchKey = "";

    public static DishonestResultFragment getInstance(Bundle argument) {
        DishonestResultFragment fragment = new DishonestResultFragment();
        fragment.setArguments(argument);
        return fragment;
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        DisHonestItemModel model = (DisHonestItemModel) adapter.getItem(position);
        if (model != null) {
            EventUtils.event(activity, EventUtils.SEARCH28);
            Intent intent = new Intent(activity, WebActivity.class);
            intent.putExtra(WebActivity.URL, model.getUrl());
            intent.putExtra(WebActivity.TITLE, "失信详情");
            activity.startActivity(intent);
        }
    }

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof DishonestResultEvent) {
            HashMap<String, String> map = ((DishonestResultEvent) event).getParams();
            if (map != null && !map.isEmpty()) {
                if (map.containsKey("province") && !TextUtils.isEmpty(map.get("province")))
                    province = map.get("province");
                else if (map.containsKey("city") && !TextUtils.isEmpty(map.get("city")))
                    province = map.get("city");
                else
                    province = "";
            }
            refresh();
        }
    }

    @Override
    protected void initUi() {

    }

    @Override
    protected void startLoadData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("type", type + "");
        params.put("searchkey", searchKey);
        params.put("province", province);
        params.put("pageSize", String.valueOf(pageSize));
        params.put("pageIndex", String.valueOf(pageIndex));
        SearchRequestRouter.SearchDishonest(activity, params, activity.getClass().getSimpleName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                SearchDisHonestModel model = (SearchDisHonestModel) data;
                if (model.getResult() == 0) {
                    completeLoadData(model.getDishonestylist());
                } else {
                    completeLoadDataError();
                }
            }

            @Override
            public void onFail(String error) {
                completeLoadDataError();
            }
        });
    }

    private void initArguments() {
        if (getArguments() != null) {
            type = getArguments().getInt(TYPE);
            searchKey = getArguments().getString(SearchResultActivity.SEARCH_KEY);
        }
    }

    @Override
    protected BaseQuickAdapter getAdapter() {
        initArguments();
        return new SearchDishonestAdapter();
    }
}
