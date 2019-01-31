package com.gxc.ui.activity;

import android.view.View;

import com.gxc.base.BaseActivity;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.widgets.ItemView;
import com.gxc.utils.AppUtils;
import com.gxc.utils.DataCleanManager;
import com.gxc.utils.HtmlUrlUtils;
import com.gxc.utils.ToastUtils;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author liuguangdan
 * @version create at 2019/1/8/008 19:18
 * @Email lgd@jusfoun.com
 * @Description ${设置}
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.vCache)
    ItemView vCache;
    @BindView(R.id.vLogout)
    View vLogout;

    @Override
    protected int getLayoutId() {
        return R.layout.act_setting;
    }

    @Override
    public void initActions() {
        titleView.setTitle("设置");
        vLogout.setVisibility(AppUtils.getUser() != null ? View.VISIBLE : View.GONE);

        loadCache();
    }

    private void loadCache() {
        try {
            vCache.setValue(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.vLogout)
    public void logout() {
        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        RxManager.http(RetrofitUtils.getApi().logout(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    AppUtils.logout();
                    vLogout.setVisibility(View.GONE);
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

    @OnClick({R.id.vPush, R.id.vCache, R.id.vService, R.id.vSecret, R.id.vAbout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vPush:
                startActivity(PushSetActivity.class);
                break;
            case R.id.vCache:
                DataCleanManager.clearAllCache(this);
                showToast("缓存数据已清除");
                loadCache();
                break;
            case R.id.vService:
                startActivity(WebActivity.getIntent(this, "服务协议", HtmlUrlUtils.getAgreeUrl(), false));
                break;
            case R.id.vSecret:
                startActivity(WebActivity.getIntent(this, "隐私政策", HtmlUrlUtils.getSecretUrl(), false));
                break;
            case R.id.vAbout:
//                startActivity(AboutActivity.class);
                startActivity(WebActivity.getIntent(this, "关于我们", HtmlUrlUtils.getAboutUrl(), false));
                break;
        }
    }
}
