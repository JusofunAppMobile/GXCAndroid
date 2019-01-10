package com.gxc.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.gxc.base.BaseActivity;
import com.gxc.constants.Constants;
import com.gxc.inter.OnSimpleCompressListener;
import com.gxc.ui.widgets.InforInputView;
import com.gxc.utils.AppUtils;
import com.gxc.utils.LogUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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

    @OnClick({R.id.vHeadIcon, R.id.vPhone, R.id.vPassword, R.id.vEmail, R.id.vCompany, R.id.vJob, R.id.vDepartment, R.id.vTrade})
    public void onViewClicked(View view) {
        int type = 0;
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
                break;
            case R.id.vCompany:
                type = Constants.INFOR_TYPE_COMPANY;
                break;
            case R.id.vJob:
                type = Constants.INFOR_TYPE_JOB;
                break;
            case R.id.vDepartment:
                type = Constants.INFOR_TYPE_DEPARTMENT;
                break;
            case R.id.vTrade:
                type = Constants.INFOR_TYPE_TRADE;
                break;
        }

        startActivity(InforEditActivity.getIntent(this, type, null));
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

                            AppUtils.compress(activity, file, new OnSimpleCompressListener() {
                                @Override
                                public void complete(String path) {
                                    if(!TextUtils.isEmpty(path)){

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
