package com.gxc.ui.activity;

import android.view.View;

import com.gxc.base.BaseActivity;
import com.gxc.constants.Constants;
import com.gxc.model.UserModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.utils.AppUtils;
import com.gxc.utils.ToastUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import netlib.util.PreferenceUtils;

/**
 * @author liuguangdan
 * @version create at 2019/1/14/014 10:00
 * @Description ${}
 */
public class PushSetActivity extends BaseActivity {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.vSet)
    View vSet;

    @Override
    protected int getLayoutId() {
        return R.layout.act_push_set;
    }

    @Override
    public void initActions() {
        titleView.setTitle("消息推送");
        UserModel user = AppUtils.getUser();
        if (user != null) {
            vSet.setSelected(user.pushStatus == 0);
        }
    }

    @OnClick(R.id.vSet)
    public void switchClick(View view) {
        view.setSelected(!view.isSelected());
        HashMap<String, Object> map = new HashMap<>();
        map.put("pushStatus", !vSet.isSelected() ? 1 : 0);
        RxManager.http(RetrofitUtils.getApi().updateInfo(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    UserModel user = AppUtils.getUser();
                    if (user != null) {
                        user.pushStatus = (vSet.isSelected() ? 0 : 1);
                        PreferenceUtils.setString(activity, Constants.USER, gson.toJson(user));
                    }
                } else {
                    showToast(model.msg);
                }
            }

            @Override
            public void error() {
                ToastUtils.showHttpError();
            }
        });
    }
}
