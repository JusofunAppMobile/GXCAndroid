package com.jusfoun.jusfouninquire.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.AdItemModel;
import com.jusfoun.jusfouninquire.net.model.HotCompanyListModel;
import com.jusfoun.jusfouninquire.net.model.HotConsultingItemModel;
import com.jusfoun.jusfouninquire.net.model.HotConsultingListModel;
import com.jusfoun.jusfouninquire.net.route.GetHotConsultingInfo;
import com.jusfoun.jusfouninquire.ui.adapter.HotConsultingListAdapter;
import com.jusfoun.jusfouninquire.ui.view.BackAndRightImageTitleView;
import com.jusfoun.jusfouninquire.ui.view.XListView;

import java.util.HashMap;

import netlib.util.EventUtils;

/**
 * Created by lsq on 2016/9/21.
 * 热门咨询页面
 */

public class HotConsultingActivity extends BaseInquireActivity implements XListView.IXListViewListener {
    private BackAndRightImageTitleView title;
    private XListView listView;
    private HotCompanyListModel model;
    private HotConsultingListAdapter adapter;
    private static final int PAGESIZE = 20;
    private int pageIndex = 0;
    @Override
    protected void initData() {
        super.initData();
        adapter=new HotConsultingListAdapter(mContext);

    }
    @Override
    protected void initView() {
        setContentView(R.layout.activity_hot_consulting);
        listView = (XListView) findViewById(R.id.list_hot_consulting);
        title = (BackAndRightImageTitleView) findViewById(R.id.hot_consulting);
    }

    @Override
    protected void initWidgetActions() {
       title.setTitleText("热门资讯");
        listView.setAdapter(adapter);
        listView.setPullLoadEnable(false);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HotConsultingItemModel model= (HotConsultingItemModel) parent.getItemAtPosition(position);
                if (model==null)
                    return;

                EventUtils.event(mContext, EventUtils.HOME94);
                Intent intent=new Intent(mContext,WebActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString(WebActivity.URL,model.getNewdetailurl());
                bundle.putString(WebActivity.TITLE,"热门资讯");
                AdItemModel shareModel=new AdItemModel();
                shareModel.setImgUrl(model.getNewsimgurl());
                shareModel.setTitle(model.getNewstitle());
                shareModel.setReUrl(model.getNewdetailurl());
                shareModel.setDescribe(model.getNewstitle());
                bundle.putSerializable(WebActivity.SHAREDATA,shareModel);
                intent.putExtras(bundle);
                startActivity(intent);
                GetHotConsultingInfo.getHotConsultingStatistics(mContext,model.getNewsid(),TAG);
            }
        });
        getHotConsulting(true);
    }
    private void getHotConsulting(final boolean refresh){
        HashMap<String, String> params = new HashMap<>();
        if (InquireApplication.getUserInfo() != null
                && !TextUtils.isEmpty(InquireApplication.getUserInfo().getUserid()))
            params.put("userid", InquireApplication.getUserInfo().getUserid());
        params.put("pagesize", PAGESIZE + "");
        params.put("pageindex", (refresh?1:(pageIndex+1)) + "");
        GetHotConsultingInfo.getHotConsultingInfo(mContext,TAG,params,new NetWorkCallBack(){

            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                listView.stopRefresh();
                listView.stopLoadMore();
                Log.i("sss", data + "sss");
                if (data instanceof HotConsultingListModel) {
                    HotConsultingListModel model = (HotConsultingListModel) data;
                    if (model.getResult() == 0) {
                        if (refresh) {
                            if (model.getHotnewslist() != null){
                                adapter.refresh(model.getHotnewslist());
                                pageIndex=1;
                            }
                        } else {
                            adapter.addData(model.getHotnewslist());
                            pageIndex++;
                        }
                        if (adapter.getCount() >= model.getCount()) {
                            listView.setPullLoadEnable(false);
                        } else {
                            listView.setPullLoadEnable(true);
                        }
                    } else {
                        Toast.makeText(mContext, model.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFail(String error) {
                listView.stopRefresh();
                listView.stopLoadMore();
                hideLoadDialog();

            }
        });
    }
    @Override
    public void onRefresh() {
        getHotConsulting(true);
    }

    @Override
    public void onLoadMore() {
        getHotConsulting(false);
    }
}
