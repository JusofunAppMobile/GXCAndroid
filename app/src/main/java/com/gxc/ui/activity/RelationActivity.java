package com.gxc.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gxc.base.BaseActivity;
import com.gxc.event.CompanySelectEvent;
import com.gxc.model.HomeMenuModel;
import com.gxc.model.WebModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.utils.ToastUtils;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.ui.activity.TypeSearchActivity;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.just.agentweb.AgentWeb;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author liuguangdan
 * @version create at 2019/1/8/008 16:11
 * @Email lgd@jusfoun.com
 * @Description ${查关系}
 */
public class RelationActivity extends BaseActivity {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.tvFirst)
    TextView tvFirst;
    @BindView(R.id.tvSecond)
    TextView tvSecond;
    @BindView(R.id.webLayout)
    LinearLayout webLayout;
    @BindView(R.id.vBig)
    View vBig;

    private HomeMenuModel menuModel;

    private AgentWeb mAgentWeb;
    private AgentWeb.PreAgentWeb preAgentWeb;

    private TextView curTextView;

    @Override
    protected int getLayoutId() {
        return R.layout.act_relation;
    }

    @Override
    public void initActions() {
        titleView.setTitle("查关系");
        menuModel = gson.fromJson(getIntent().getStringExtra("menu"), HomeMenuModel.class);
    }

    public static Intent getIntent(Context context, HomeMenuModel model) {
        Intent intent = new Intent(context, RelationActivity.class);
        intent.putExtra("menu", new Gson().toJson(model));
        return intent;
    }

    private void loadUrl(String url) {
        if (preAgentWeb == null) {
            preAgentWeb = AgentWeb.with(this)
                    .setAgentWebParent(webLayout, new LinearLayout.LayoutParams(-1, -1))
                    .useDefaultIndicator(Color.TRANSPARENT)
                    .createAgentWeb()
                    .ready();
        }
        if (mAgentWeb == null)
            mAgentWeb = preAgentWeb.go(url);
        else
            mAgentWeb.getUrlLoader().loadUrl(url);
    }


    private void getUrl() {
        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", getIntent().getIntExtra("menuType", 7));
        map.put("name_one", getValue(tvFirst));
        map.put("name_two", getValue(tvSecond));
        map.put("route_num", 3);// TEST TODO

        RxManager.http(RetrofitUtils.getApi().getH5Address(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    WebModel webModel = model.dataToObject(WebModel.class);
                    if (webModel != null) {
                        loadUrl(webModel.H5Address);
                    }
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

    @OnClick({R.id.tvFirst, R.id.tvSecond, R.id.bt, R.id.vBig})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvFirst:
                curTextView = tvFirst;
                startActivity(TypeSearchActivity.getIntent(activity, menuModel.menuType));
                break;
            case R.id.tvSecond:
                curTextView = tvSecond;
                startActivity(TypeSearchActivity.getIntent(activity, menuModel.menuType));
                break;
            case R.id.bt:
                if (isEmptyAndToast(tvFirst, "请选择企业")) return;
                if (isEmptyAndToast(tvSecond, "请选择企业")) return;
                if (!TextUtils.isEmpty(getValue(tvFirst)) && !TextUtils.isEmpty(getValue(tvSecond))) {
                    webLayout.setVisibility(View.VISIBLE);
                    vBig.setVisibility(View.VISIBLE);
                    getUrl();
                }
                break;
            case R.id.vBig:
                startActivity(WebActivity.getRelationIntent(activity, mAgentWeb.getWebCreator().getWebView().getUrl()));
//                startActivity(WebActivity.getRelationIntent(activity, AppUtils.TEST_URL));
                break;
        }
    }

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof CompanySelectEvent) {
            CompanySelectEvent comEvent = (CompanySelectEvent) event;
            if (curTextView != null) {
                curTextView.setText(comEvent.name);
                curTextView.setTag(comEvent.id);
            }
        }
    }

    @Override
    public void onPause() {
        if (mAgentWeb != null)
            mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAgentWeb != null)
            mAgentWeb.getWebLifeCycle().onDestroy();
    }

    @Override
    public void onResume() {
        if (mAgentWeb != null)
            mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (mAgentWeb != null && mAgentWeb.back())
            mAgentWeb.getWebCreator().getWebView().goBack();
        else
            super.onBackPressed();
    }
}
