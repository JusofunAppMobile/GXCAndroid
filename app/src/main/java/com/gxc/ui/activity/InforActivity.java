package com.gxc.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.gxc.base.BaseActivity;
import com.gxc.constants.Constants;
import com.gxc.inter.OnSimpleCompressListener;
import com.gxc.inter.OnUploadListener;
import com.gxc.model.UserModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.widgets.InforInputView;
import com.gxc.utils.AppUtils;
import com.gxc.utils.LogUtils;
import com.gxc.utils.ToastUtils;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import netlib.util.PreferenceUtils;

/**
 * @author liuguangdan
 * @version create at 2019/1/7/007 18:16
 * @Email lgd@jusfoun.com
 * @Description ${个人设置}
 */
public class InforActivity extends BaseActivity {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.vHeadIcon)
    InforInputView vHeadIcon;
    @BindView(R.id.vPhone)
    InforInputView vPhone;
    @BindView(R.id.vPassword)
    InforInputView vPassword;
    @BindView(R.id.vEmail)
    InforInputView vEmail;
    @BindView(R.id.vCompany)
    InforInputView vCompany;
    @BindView(R.id.vJob)
    InforInputView vJob;
    @BindView(R.id.vDepartment)
    InforInputView vDepartment;
    @BindView(R.id.vTrade)
    InforInputView vTrade;

    String imagePath;

    @Override
    protected int getLayoutId() {
        return R.layout.act_infor;
    }

    @Override
    public void initActions() {
        titleView.setTitle("个人设置");
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUser();
    }

    private void loadUser() {
        UserModel user = AppUtils.getUser();
        if (user != null) {
            vEmail.setValue(user.email);
            vCompany.setValue(user.company);
            vDepartment.setValue(user.department);
            vJob.setValue(user.job);
            vTrade.setValue(user.trade);
            vPhone.setValue(user.phone);
            if (TextUtils.isEmpty(imagePath))
                vHeadIcon.loadImage(user.headIcon);

            // 认证成功后的公司名称不能修改
            if (user.authStatus == 3) {
                vCompany.setEnabled(false);
                vCompany.setValue(user.authCompany);
                vCompany.hideArrow();
            }
        }
    }

    @OnClick({R.id.vHeadIcon, R.id.vPhone, R.id.vPassword, R.id.vEmail, R.id.vCompany, R.id.vJob, R.id.vDepartment, R.id.vTrade})
    public void onViewClicked(View view) {
        int type = 0;
        String value = null;
        switch (view.getId()) {
            case R.id.vHeadIcon:
                AppUtils.pictureSelect(activity, true, 1, null);
                return;
            case R.id.vPhone:
                startActivity(BindPhoneActivity.getIntent(this, BindPhoneActivity.TYPE_UPDATE_PHONE));
                return;
            case R.id.vPassword:
                startActivity(BindPhoneActivity.getIntent(this, BindPhoneActivity.TYPE_UPDATE_PASSWORD));
                return;
            case R.id.vEmail:
                type = Constants.INFOR_TYPE_EMAIL;
                value = vEmail.getValue();
                break;
            case R.id.vCompany:
                type = Constants.INFOR_TYPE_COMPANY;
                value = vCompany.getValue();
                break;
            case R.id.vJob:
                type = Constants.INFOR_TYPE_JOB;
                value = vJob.getValue();
                break;
            case R.id.vDepartment:
                type = Constants.INFOR_TYPE_DEPARTMENT;
                value = vDepartment.getValue();
                break;
            case R.id.vTrade:
                type = Constants.INFOR_TYPE_TRADE;
                value = vTrade.getValue();
                break;
        }

        startActivity(InforEditActivity.getIntent(this, type, value));
    }

    private void upload() {
        AppUtils.uploadPicture(imagePath, "Icon", new OnUploadListener() {
            @Override
            public void complete(String url, String simple) {
                if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(simple))
                    updateHeadIcon(url, simple);
                else {
                    hideLoadDialog();
                    ToastUtils.showHttpError();
                }
            }
        });
    }

    private void updateHeadIcon(final String url, String simple) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("headIcon", simple);
        RxManager.http(RetrofitUtils.getApi().updateInfo(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    showToast("修改成功");
                    imagePath = null;
                    UserModel user = AppUtils.getUser();
                    if (user != null) {
                        user.headIcon = url;
                        PreferenceUtils.setString(activity, Constants.USER, gson.toJson(user));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList != null && !selectList.isEmpty()) {
                        LocalMedia media = selectList.get(0);
                        if (media.isCut())
                            imagePath = media.getCutPath();
                        else
                            imagePath = media.getPath();
                        if (!TextUtils.isEmpty(imagePath)) {
                            File file = new File(imagePath);
                            LogUtils.e("图片：" + file.getAbsolutePath() + ",大小" + AppUtils.byteToMB(file.length()));
                            vHeadIcon.loadImage(imagePath);
                            showLoading();
                            AppUtils.compress(activity, file, new OnSimpleCompressListener() {
                                @Override
                                public void complete(String path) {
                                    if (!TextUtils.isEmpty(path)) {
                                        imagePath = path;
                                        upload();
                                    } else {
                                        hideLoadDialog();
                                        ToastUtils.showHttpError();
                                    }
                                }
                            });
                        }
                    }
                    break;
            }
        }
    }
}
