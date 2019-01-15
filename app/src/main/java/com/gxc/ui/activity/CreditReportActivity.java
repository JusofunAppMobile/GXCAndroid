package com.gxc.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListActivity;
import com.gxc.constants.Constants;
import com.gxc.event.LoginSucEvent;
import com.gxc.model.CreditReportCountModel;
import com.gxc.model.CreditReportModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.adapter.CreditReportAdapter;
import com.gxc.utils.AppUtils;
import com.gxc.utils.DESUtils;
import com.gxc.utils.ToastUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.TimeOut;
import com.jusfoun.jusfouninquire.ui.util.RegexUtils;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import netlib.util.PreferenceUtils;

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

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new CreditReportAdapter();
    }

    @Override
    protected void initUi() {
        titleView.setTitle("信用报告");
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

        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId","1");
        map.put("companyid", "1");
        map.put("companyname", "小米科技有限责任公司");

        RxManager.http(RetrofitUtils.getApi().getCreditReport(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    CreditReportModel.CreditReportItemModel creditReportCountModel = model.dataToObject(CreditReportModel.CreditReportItemModel.class);
                    List<CreditReportModel.CreditReportItemModel>list = new ArrayList<>();
                    list.add(creditReportCountModel);
                    list.add(creditReportCountModel);

                    completeLoadData(list);
                } else {
                    showToast(model.msg);
                }
            }

            @Override
            public void error() {
                hideLoadDialog();
                ToastUtils.showHttpError();
            }
        });

    }
}
