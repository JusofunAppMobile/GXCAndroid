package com.gxc.ui.activity;

import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListActivity;
import com.gxc.model.CreditReportModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.adapter.CreditReportAdapter;
import com.jusfoun.jusfouninquire.R;
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
    @BindView(R.id.text_title)
    TextView textTitle;
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
        textTitle.setText(Html.fromHtml("国信查支持<font  color=\"#df282d\">发票开取</font>，你可以在购买报告后开取发票"));


    }

    @Override
    protected void startLoadData() {
        getData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_credit_report;
    }


    private void getData() {

//        showLoading();
        HashMap<String, Object> map = new HashMap<>();
//        map.put("companyid", companyId == null ? "" : companyId);
        map.put("companyname", companyName == null ? "" : companyName);

        RxManager.http(RetrofitUtils.getApi().getCreditReport(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
//                hideLoadDialog();
                if (model.success()) {
                    CreditReportModel.CreditReportItemModel creditReportCountModel = model.dataToObject(CreditReportModel.CreditReportItemModel.class);
                    List<CreditReportModel.CreditReportItemModel> list = new ArrayList<>();
                    creditReportCountModel.companyName = companyName;
//                    creditReportCountModel.companyId = companyId;

                    Log.e("tag","companyName="+companyName+" "+companyId);

                    list.add(creditReportCountModel);
                    list.add(creditReportCountModel);
                    completeLoadData(list);
                } else {
                    completeLoadDataError();
//                    showToast(model.msg);
                }
            }

            @Override
            public void error() {
                completeLoadDataError();
//                hideLoadDialog();
//                ToastUtils.showHttpError();
            }
        });

    }
}
