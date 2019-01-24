package com.gxc.ui.activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListActivity;
import com.gxc.event.CreditSendSucEvent;
import com.gxc.impl.ListResponseCall;
import com.gxc.model.CreditReportModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.adapter.CreditReportAdapter;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/718:43
 * @Email zyp@jusfoun.com
 * @Description ${信用服务-信用报告页面}
 */
public class CreditReportActivity extends BaseListActivity {

    @BindView(R.id.titleView)
    TitleView titleView;
    private String companyName = "", companyId = "";

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new CreditReportAdapter();
    }

    @Override
    protected void initUi() {
        titleView.setTitle("信用报告");
        companyName = getIntent().getStringExtra("companyName");
        companyId = getIntent().getStringExtra("companyId");
    }

    @Override
    protected void startLoadData() {
        getData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_recycler_common;
    }


    private void getData() {

//        showLoading();
        HashMap<String, Object> map = new HashMap<>();
//        map.put("companyid", companyId == null ? "" : companyId);
        map.put("companyname", companyName == null ? "" : companyName);

        RxManager.http(RetrofitUtils.getApi().getCreditReport(map), new ListResponseCall(this) {

            @Override
            public List getList(NetModel model) {
                CreditReportModel.CreditReportItemModel creditReportCountModel = model.dataToObject(CreditReportModel.CreditReportItemModel.class);
                List<CreditReportModel.CreditReportItemModel> list = new ArrayList<>();
                creditReportCountModel.companyName = companyName;
                list.add(creditReportCountModel);
                list.add(creditReportCountModel);
                return list;
            }
        });

    }

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof CreditSendSucEvent)
            finish();
    }
}
