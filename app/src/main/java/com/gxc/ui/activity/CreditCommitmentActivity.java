package com.gxc.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gxc.base.BaseActivity;
import com.gxc.inter.OnSimpleCompressListener;
import com.gxc.inter.OnUploadListener;
import com.gxc.model.UserModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.view.EmailSendDialog;
import com.gxc.utils.AppUtils;
import com.gxc.utils.GlideRoundTransform;
import com.gxc.utils.LogUtils;
import com.gxc.utils.ToastUtils;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.siccredit.guoxin.R;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/1115:11
 * @Email zyp@jusfoun.com
 * @Description ${信用承诺书}
 */
public class CreditCommitmentActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleView titlebar;
    @BindView(R.id.text_get_moban)
    TextView textGetMoban;
    @BindView(R.id.img_photo)
    ImageView imgPhoto;

    private String imagePath;
    private RequestOptions requestOptions;
    private UserModel userModel;

    private EmailSendDialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_credit_commitment;
    }

    @Override
    public void initActions() {
        userModel = AppUtils.getUser();
        titlebar.setTitle("信用承诺");
        requestOptions = new RequestOptions()
                .centerCrop()
                .circleCrop()//设置圆形
                .placeholder(R.color.white)
                .error(R.color.white)
                .transform(new GlideRoundTransform(activity, 5));

    }

    @OnClick({R.id.text_get_moban, R.id.img_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_get_moban:
                if (dialog == null) {
                    dialog = new EmailSendDialog(CreditCommitmentActivity.this, new EmailSendDialog.CallBack() {
                        @Override
                        public void onClick(String email) {
                            getTemplateByEmail(email);
                        }
                    });
                }
                dialog.show();

                break;
            case R.id.img_photo:
                AppUtils.pictureSelect(activity, false, 1, null);
                break;
        }
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
                            showLoading();
                            File file = new File(imagePath);
                            LogUtils.e("图片：" + file.getAbsolutePath() + ",大小" + AppUtils.byteToMB(file.length()));

                            AppUtils.compress(activity, file, new OnSimpleCompressListener() {
                                @Override
                                public void complete(String path) {
                                    if (!TextUtils.isEmpty(path)) {
                                        AppUtils.uploadPicture(path, "promise ", new OnUploadListener() {
                                            @Override
                                            public void complete(String url, String simple) {
                                                hideLoadDialog();
                                                submitCx(simple);
                                            }
                                        });
                                        Glide.with(activity).load(path).apply(requestOptions).into(imgPhoto);
                                    }
                                }
                            });
                        }
                    }
                    break;
            }
        }
    }


    private void submitCx(String Image) {

        UserModel userModel = AppUtils.getUser();
        HashMap<String, Object> map = new HashMap<>();
        map.put("companyname", userModel.authCompany);
        map.put("Image", Image);
        RxManager.http(RetrofitUtils.getApi().uploadLetter(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    showToast("提交成功");
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

    private void getTemplateByEmail(String email) {

        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("companyname", userModel.authCompany);
        map.put("email", email);

        RxManager.http(RetrofitUtils.getApi().getTemplateByEmail(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    showToast("模板已发送您的邮箱");
                    dialog.dismiss();
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
