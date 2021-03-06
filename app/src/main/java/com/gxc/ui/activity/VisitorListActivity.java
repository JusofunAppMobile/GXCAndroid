package com.gxc.ui.activity;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListActivity;
import com.gxc.impl.ListResponseCall;
import com.gxc.model.VisitorModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.adapter.VisitorAdapter;
import com.siccredit.guoxin.R;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author liuguangdan
 * @version create at 2019/1/10/010 9:14
 * @Description ${访客}
 */
public class VisitorListActivity extends BaseListActivity {

    @BindView(R.id.tvNum)
    TextView tvNum;

    @Override
    protected BaseQuickAdapter getAdapter() {
        return new VisitorAdapter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_visitor;
    }

    @Override
    protected void initUi() {

    }

    @Override
    public void refresh() {
        tvNum.setText(String.valueOf(0));
        super.refresh();
    }

    @Override
    public boolean isBarDark() {
        return false;
    }

    @Override
    public boolean isSetStatusBar() {
        return false;
    }

    @OnClick(R.id.ivLeft)
    public void click() {
        finish();
    }

    @Override
    protected void startLoadData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        RxManager.http(RetrofitUtils.getApi().visitorRecord(map), new ListResponseCall(this) {

            @Override
            public List getList(NetModel model) {
                return model.dataToList("list", VisitorModel.class);
            }

            @Override
            public void success(NetModel model) {
                if (model.success()) {
                    if (pageIndex == INIT_PAGE_INDEX) {
                        int count = model.dataToObject("count", Integer.class);
                        tvNum.setText(String.valueOf(count));
                    }
                }
                super.success(model);
            }
        });
    }
}
