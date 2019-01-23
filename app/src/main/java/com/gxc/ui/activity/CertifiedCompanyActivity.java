package com.gxc.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gxc.base.BaseActivity;
import com.gxc.inter.OnSimpleCompressListener;
import com.gxc.inter.OnUploadListener;
import com.gxc.model.CertificationModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.view.CorporateIInfoImgItemView;
import com.gxc.ui.view.CorporateInfoItemView;
import com.gxc.utils.AppUtils;
import com.gxc.utils.LogUtils;
import com.gxc.utils.ToastUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

import static com.gxc.constants.Constants.NUM_MAX;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/717:43
 * @Email zyp@jusfoun.com
 * @Description ${认证企业}
 */
public class CertifiedCompanyActivity extends BaseActivity {
    @BindView(R.id.text_tip)
    TextView textTip;
    @BindView(R.id.view_name)
    CorporateInfoItemView viewName;
    @BindView(R.id.view_code)
    CorporateInfoItemView viewCode;
    @BindView(R.id.view_phone)
    CorporateInfoItemView viewPhone;
    @BindView(R.id.view_email)
    CorporateInfoItemView viewEmail;
    @BindView(R.id.img_yyzz)
    CorporateIInfoImgItemView imgYyzz;
    @BindView(R.id.img_idfen)
    CorporateIInfoImgItemView imgIdfen;
    @BindView(R.id.title_bar)
    TitleView titleBar;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.view_real_name)
    CorporateInfoItemView viewRealName;

    public static final int PHOTO_YINGYE = 10001;//营业执照
    public static final int PHOTO_IDENTITY = 10002;//身份证


    private String yeUrl = "", cardUrl = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_certified_company;
    }

    @Override
    public void initActions() {
        viewName.setData("企业名称",NUM_MAX);
        viewCode.setData("法人身份证",NUM_MAX);
        viewPhone.setData("手机号码",NUM_MAX);
        viewEmail.setData("邮箱",NUM_MAX);
        imgYyzz.setData("营业执照", getString(R.string.text_img_carme_tip), PHOTO_YINGYE);
        imgIdfen.setData("本人身份证", getString(R.string.text_img_carme_top_identity), PHOTO_IDENTITY);
        viewRealName.setData("真实姓名",NUM_MAX);

        titleBar.setTitle("认证企业");

        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                renzhengNet();
            }
        });

        getRZData();
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String imagePath = null;
        if (resultCode == RESULT_OK) {

            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            if (selectList != null && !selectList.isEmpty()) {
                LocalMedia media = selectList.get(0);
                if (media.isCut()) {
                    imagePath = media.getCutPath();
                } else {
                    imagePath = media.getPath();
                }

            }

            if (!TextUtils.isEmpty(imagePath)) {
                File file = new File(imagePath);
                LogUtils.e("图片：" + file.getAbsolutePath() + ",大小" + AppUtils.byteToMB(file.length()));
                AppUtils.compress(activity, file, new OnSimpleCompressListener() {
                    @Override
                    public void complete(String path) {
                        if (!TextUtils.isEmpty(path)) {
                            if (PHOTO_YINGYE == requestCode) {
                                imgYyzz.setImageSrc(path);
                                showLoading();
                                AppUtils.uploadPicture(path, "certification", new OnUploadListener() {
                                    @Override
                                    public void complete(String url, String simple) {
                                        hideLoadDialog();
                                        yeUrl = simple;
                                    }
                                });
                            } else if (PHOTO_IDENTITY == requestCode) {
                                showLoading();
                                imgIdfen.setImageSrc(path);
                                AppUtils.uploadPicture(path, "idcard", new OnUploadListener() {
                                    @Override
                                    public void complete(String url, String simple) {
                                        hideLoadDialog();
                                        cardUrl = simple;
                                    }
                                });
                            }
                        }
                    }
                });
            }
        }
    }


    private void renzhengNet() {

        if (isEmptyAndToast(viewName.getEditText(), "请输入企业名称")) return;
        if (isEmptyAndToast(viewCode.getEditText(), "请输入法人身份证")) return;
        if (isEmptyAndToast(viewRealName.getEditText(), "请输入真实姓名")) return;
        if (isEmptyAndToast(viewPhone.getEditText(), "请输入手机号")) return;
        if (isEmptyAndToast(viewEmail.getEditText(), "请输入邮箱")) return;
        if (isEmptyAndToast(yeUrl, "请上传营业执照")) return;
        if (isEmptyAndToast(cardUrl, "请上传身份证")) return;


        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("companyname", viewName.getEditText());
        map.put("name", viewRealName.getEditText());
        map.put("idcard", viewCode.getEditText());
        map.put("phone", viewPhone.getEditText());
        map.put("email", viewEmail.getEditText());
        map.put("licenseImage", yeUrl);
        map.put("idcardImage", cardUrl);
        map.put("provinceId", "11");
        map.put("cityId", "1101");

        RxManager.http(RetrofitUtils.getApi().subCompanyMsg(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    showToast("提交成功");
                    finish();
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

    private void getRZData() {

        showLoading();

        RxManager.http(RetrofitUtils.getApi().getCompanyMsg(), new ResponseCall() {

            @Override
            public void success(NetModel data) {
                hideLoadDialog();
                if (data.success()) {
                    CertificationModel model = data.dataToObject(CertificationModel.class);
                    viewName.setContent(model.companyname);
                    viewPhone.setContent(model.phone);
                    viewEmail.setContent(model.email);
                    imgYyzz.setImageSrc(model.licenseImage);
                    imgIdfen.setImageSrc(model.idcardImage);
                    viewCode.setContent(model.idcard);
                    viewRealName.setContent(model.name);

                } else {
                    showToast(data.msg);
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
