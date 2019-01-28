package com.jusfoun.jusfouninquire.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jusfoun.jusfouninquire.InquireApplication;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.MyReportModel;
import com.jusfoun.jusfouninquire.net.model.UserInfoModel;
import com.jusfoun.jusfouninquire.net.route.ReportRoute;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.service.event.RefreshMyReportEvent;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.adapter.MyReportAdapter;
import com.jusfoun.jusfouninquire.ui.animation.SceneAnimation;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.jusfoun.jusfouninquire.ui.view.XListView;

import java.util.HashMap;

import com.jusfoun.jusfouninquire.TimeOut;

import static com.jusfoun.jusfouninquire.InquireApplication.getUserInfo;

/**
 * @Description ${我的报告}
 */
public class MyReportActivity extends BaseInquireActivity implements XListView.IXListViewListener {
    private XListView mlistView;
    private TitleView titleView;
    private LinearLayout mLookany;
    private NetWorkErrorView netErrorLayout;


    private RelativeLayout mFrameLayout;
    private ImageView mFrameImage;
    private SceneAnimation mSceneAnimation;
    /**
     * 变量
     */
    private int pagenum = 1;
    private String userId = "";

    /**
     * 对象
     */
    private MyReportAdapter adapter;

    @Override
    protected void initData() {
        if ((LoginSharePreference.getUserInfo(mContext) != null) && !TextUtils.isEmpty(LoginSharePreference.getUserInfo(mContext).getUserid())) {
            userId = LoginSharePreference.getUserInfo(mContext).getUserid();
        }
        adapter = new MyReportAdapter(this);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_system_message);
        titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setTitle("我的报告");
        mlistView = (XListView) findViewById(R.id.system_msg_xlistview);
        mLookany = (LinearLayout) findViewById(R.id.look_any);
        netErrorLayout = (NetWorkErrorView) findViewById(R.id.neterrorlayout);

        mFrameLayout = (RelativeLayout) findViewById(R.id.image_frame_layout);
        mFrameImage = (ImageView) findViewById(R.id.image_frame);
        mSceneAnimation = new SceneAnimation(mFrameImage, 75);

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                i--;
                MyReportModel.DataResultBean bean = (MyReportModel.DataResultBean) adapter.getItem(i);
                if(bean == null) return;
                UserInfoModel model = InquireApplication.getUserInfo();
                if (model == null) return;
                if (bean != null) {
                    WebActivity.startCompanyReportActivity(MyReportActivity.this, bean.reportUrl, bean.entName, bean.entId, model.vipType == 1);
                }
            }
        });
    }

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof RefreshMyReportEvent)
            onRefresh();
    }

    @Override
    protected void initWidgetActions() {
        mlistView.setAdapter(adapter);
        mlistView.setXListViewListener(this);
        mlistView.setPullRefreshEnable(true);
        mlistView.setPullLoadEnable(true);

        netErrorLayout.setListener(new NetWorkErrorView.OnRefreshListener() {
            @Override
            public void OnNetRefresh() {
                netErrorLayout.setVisibility(View.GONE);
                loadData();
            }
        });
        loadData();
    }

    private int pageIndex = 1;


    private void loadData() {
        if (pageIndex == 1 && adapter.isDataEmpty()) {
            mFrameLayout.setVisibility(View.VISIBLE);
            mSceneAnimation.start();
        }

        HashMap<String, String> map = new HashMap<>();
        final TimeOut timeOut = new TimeOut(this);
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put("pageSize", "20");
        map.put("userId", getUserInfo().getUserid());
        map.put("t", timeOut.getParamTimeMollis() + "");
        map.put("m", timeOut.MD5time() + "");

        ReportRoute.myReport(this, map, getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                mFrameLayout.setVisibility(View.GONE);
                mSceneAnimation.stop();
                finishLoadMoreOrRefresh();
                MyReportModel model = (MyReportModel) data;
//                MyReportModel model = new Gson().fromJson("{\"totalCount\":1,\"dataResult\":[{\"entName\":\"小米科技有限责任公司\",\"entId\":\"\",\"reportTime\":\"2017年10月19日 3:26\",\"reportId\":\"3\",\"reportUrl\":\"http://192.168.1.6:9981/Html/report.html?entName=小米科技有限责任公司&userid=1\"}]}", MyReportModel.class);
                if (model.totalCount == 0) {
                    mlistView.setVisibility(View.GONE);
                    netErrorLayout.setVisibility(View.GONE);
                    mLookany.setVisibility(View.VISIBLE);
                } else {
                    mlistView.setVisibility(View.VISIBLE);
                    netErrorLayout.setVisibility(View.GONE);
                    mLookany.setVisibility(View.GONE);
                    if (pageIndex == 1) {
                        adapter.refresh(model.dataResult);
                    } else {
                        adapter.addMore(model.dataResult);
                    }
                    pageIndex++;
                    if (model.totalCount == adapter.getCount()) {
                        mlistView.setPullLoadEnable(false);
                    } else {
                        mlistView.setPullLoadEnable(true);
                    }
                }
            }

            @Override
            public void onFail(String error) {
                mFrameLayout.setVisibility(View.GONE);
                mSceneAnimation.stop();
                finishLoadMoreOrRefresh();
                mlistView.setVisibility(View.GONE);
                netErrorLayout.setNetWorkError();
                netErrorLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void finishLoadMoreOrRefresh() {
        hideLoadDialog();

        mlistView.stopRefresh();
        mlistView.stopLoadMore();
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        loadData();
    }

    @Override
    public void onLoadMore() {
        loadData();
    }
}
