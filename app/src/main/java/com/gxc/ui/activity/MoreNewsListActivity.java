package com.gxc.ui.activity;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListActivity;
import com.gxc.model.HomeNewsModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.adapter.HomeNewsAdapter;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import java.util.HashMap;

import butterknife.BindView;

/**
 * @author liuguangdan
 * @version create at 2019/1/5/005 19:55
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class MoreNewsListActivity extends BaseListActivity {

    @BindView(R.id.titleView)
    TitleView titleView;

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new HomeNewsAdapter(this);
    }

    @Override
    protected void initUi() {
        titleView.setTitle("行业资讯");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        startActivity(WebActivity.getIntent(activity, "行业资讯", AppUtils.TEST_URL));
    }

    @Override
    protected void startLoadData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageSize", pageSize);
        map.put("pageIndex", pageIndex);
        RxManager.http(RetrofitUtils.getApi().moreNews(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                completeLoadData(model.dataToList("news", HomeNewsModel.class));
            }

            @Override
            public void error() {
                completeLoadDataError();
            }
        });
    }
}
