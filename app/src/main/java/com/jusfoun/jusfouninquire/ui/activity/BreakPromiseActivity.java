/*
package com.jusfoun.jusfouninquire.ui.activity;


import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.FocusedModel;
import com.jusfoun.jusfouninquire.net.model.HomeDishonestyItemModel;
import com.jusfoun.jusfouninquire.net.route.GetHomeInfo;
import com.jusfoun.jusfouninquire.ui.adapter.InvestmentBrachAdapter;
import com.jusfoun.jusfouninquire.ui.view.MyTitleView;
import com.jusfoun.jusfouninquire.ui.view.XListView;

import java.util.HashMap;

*/
/**
 * @author lee
 * @version create time:2015/11/2515:38
 * @Email email
 * @Description ${失信榜单列表
 *//*


public class BreakPromiseActivity extends BaseInquireActivity implements XListView.IXListViewListener{

    */
/**常量 *//*



    */
/**组件*//*

    private MyTitleView titleView;
    private XListView mlistView;
    private View nodatalayout,netErrorLayout;
    private TextView nodataText,netErrorText;

    */
/**变量 *//*

    private  int pagenum = 1;

    */
/**对象 *//*

    private InvestmentBrachAdapter adapter;


    @Override
    protected void initData() {
        super.initData();
        adapter = new InvestmentBrachAdapter(mContext);
    }
    @Override
    protected void initView() {
        setContentView(R.layout.activity_break_promise);
        titleView = (MyTitleView) findViewById(R.id.titleView);
        titleView.setLeftImage(R.mipmap.back_image);
        titleView.setTitle("失信榜单");
        mlistView = (XListView) findViewById(R.id.xlistView);

        nodatalayout = findViewById(R.id.no_data_layout);
        nodataText = (TextView) nodatalayout.findViewById(R.id.nodata_text);
        netErrorLayout = findViewById(R.id.net_work_error);
        netErrorText = (TextView) netErrorLayout.findViewById(R.id.error_text);

    }
    @Override
    protected void initWidgetActions() {
        mlistView.setAdapter(adapter);
        mlistView.setPullRefreshEnable(true);
        mlistView.setPullLoadEnable(false);
        mlistView.setXListViewListener(this);

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (view.getTag() instanceof InvestmentBrachAdapter.ViewHolder) {
                    HomeDishonestyItemModel model = ((InvestmentBrachAdapter.ViewHolder) view.getTag()).mDishonestyModel;
                }

            }
        });

        netErrorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //获取我的关注或刷新我的关注
    private void getBreakPromist(){
        HashMap<String,String> map = new HashMap<>();
        map.put("pageindex", "1");
        map.put("pagesize", "10");
        showLoading();
        GetHomeInfo.getHomeInfo(mContext, map, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {

                finishLoadMoreOrRefresh();
                pagenum = 1;
                FocusedModel model = (FocusedModel) data;
                if (model.getResult() == 0) {
                    if ("true".equals(model.getIsmore())) {
                        mlistView.setPullLoadEnable(true);
                    } else {
                        mlistView.setPullLoadEnable(false);
                    }
                    if (model.getMywatchlist() != null && model.getMywatchlist().size() > 0) {

                    } else {
                        nodatalayout.setVisibility(View.VISIBLE);
                        nodataText.setText("暂无关注");
                    }
                } else {
                    netErrorLayout.setVisibility(View.VISIBLE);
                    netErrorText.setText(model.getMsg() + "");
                }
            }

            @Override
            public void onFail(String error) {
                Log.d("TAG", error + "");
                finishLoadMoreOrRefresh();
                netErrorLayout.setVisibility(View.VISIBLE);
            }
        });
    }
    //获取我的关注
    private void getMoreBreakPromise(int pageIndex){
        HashMap<String,String> map = new HashMap<>();
        map.put("pageindex",pageIndex+"");
        map.put("pagesize","10");

        showLoading();
        GetHomeInfo.getHomeInfo(mContext, map, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                finishLoadMoreOrRefresh();
                pagenum++;
                FocusedModel model = (FocusedModel) data;
                if (model.getResult() == 0) {
                    if ("true".equals(model.getIsmore())) {
                        mlistView.setPullLoadEnable(true);
                    } else {
                        mlistView.setPullLoadEnable(false);
                    }
                    if (model.getMywatchlist() != null && model.getMywatchlist().size() > 0) {
                    }
                }
            }

            @Override
            public void onFail(String error) {
                finishLoadMoreOrRefresh();
                showToast("获取失败");
            }
        });
    }

    private void finishLoadMoreOrRefresh(){
        hideLoadDialog();
        nodatalayout.setVisibility(View.GONE);
        netErrorLayout.setVisibility(View.GONE);
        mlistView.stopRefresh();
        mlistView.stopLoadMore();
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
*/
