package com.gxc.ui.activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gxc.base.BaseListActivity;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.adapter.RecentChangeAdapter;
import com.gxc.utils.ToastUtils;
import com.jusfoun.jusfouninquire.net.model.RecentChangeItemModel;

import java.util.HashMap;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/1117:32
 * @Email zyp@jusfoun.com
 * @Description ${近期变更}
 */
public class RecentChangeActivity extends BaseListActivity {
    @Override
    protected BaseQuickAdapter getAdapter() {
        return new RecentChangeAdapter();
    }

    @Override
    protected void initUi() {

        setTitle("");
    }

    @Override
    protected void startLoadData() {
        getData();
    }

    private void getData() {

//        showLoading();
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("pageSize", pageSize + "");
//        map.put("pageIndex", pageIndex + "");
//        RxManager.http(RetrofitUtils.getApi().CorporateInfoChange(), new ResponseCall() {
//
//            @Override
//            public void success(NetModel data) {
//                hideLoadDialog();
//                if (data.success()) {
//                    RecentChangeItemModel model = data.dataToObject(RecentChangeItemModel.class);
//                    completeLoadData(model.changeList);
//                } else {
//                    showToast(data.msg);
//                    completeLoadDataError();
//                }
//            }
//
//            @Override
//            public void error() {
//                hideLoadDialog();
//                ToastUtils.showHttpError();
//                completeLoadDataError();
//            }
//        });

    }
}
