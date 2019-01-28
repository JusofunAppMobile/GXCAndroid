package com.gxc.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gxc.base.BaseActivity;
import com.gxc.inter.OnSimpleCompressListener;
import com.gxc.inter.OnUploadListener;
import com.gxc.model.AddressModel;
import com.gxc.model.CertificationModel;
import com.gxc.model.UserModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.dialog.AddressDialog;
import com.gxc.ui.view.CorporateIInfoImgItemView;
import com.gxc.ui.view.CorporateInfoItemView;
import com.gxc.utils.AppUtils;
import com.gxc.utils.LogUtils;
import com.gxc.utils.ToastUtils;
import com.jusfoun.jusfouninquire.ui.util.RegexUtils;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.siccredit.guoxin.R;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.gxc.constants.Constants.NUM_MAX3;

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
    @BindView(R.id.vAddress)
    CorporateInfoItemView vAddress;

    public static final int PHOTO_YINGYE = 10001;//营业执照
    public static final int PHOTO_IDENTITY = 10002;//身份证


    private String yeUrl = "", cardUrl = "";

    private List<AddressModel> addressList;

    private AddressDialog addressDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_certified_company;
    }

    @Override
    public void initActions() {
        setEditable(false);
        viewName.setData("企业名称", NUM_MAX3);
        viewCode.setData("法人身份证", NUM_MAX3);
        viewPhone.setData("手机号码", NUM_MAX3);
        viewPhone.setInputTypeNum();
        viewPhone.setMaxInputLength(11);
        viewEmail.setData("邮箱", NUM_MAX3);
        imgYyzz.setData("营业执照", getString(R.string.text_img_carme_tip), PHOTO_YINGYE);
        imgIdfen.setData("本人身份证", getString(R.string.text_img_carme_top_identity), PHOTO_IDENTITY);
        viewRealName.setData("真实姓名", NUM_MAX3);
        vAddress.setData("企业注册地址", NUM_MAX3);

        titleBar.setTitle("企业认证");

        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                renzhengNet();
            }
        });

        vAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addressDialog != null)
                    addressDialog.show();
            }
        });
        getAddressInfor();
    }

    private void setEditable(boolean enable) {
        viewName.setEnabled(enable);
        viewCode.setEnabled(enable);
        viewPhone.setEnabled(enable);
        viewEmail.setEnabled(enable);
        imgYyzz.setEnabled(enable);
        imgIdfen.setEnabled(enable);
        viewRealName.setEnabled(enable);
        btnCommit.setVisibility(enable ? View.VISIBLE : View.GONE);
        vAddress.setEnabled(enable);
        if (enable)
            vAddress.setSelectType();
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
                                AppUtils.uploadPicture(path, "certification", new OnUploadListener() {
                                    @Override
                                    public void complete(String url, String simple) {
                                        yeUrl = simple;
                                    }
                                });
                            } else if (PHOTO_IDENTITY == requestCode) {
                                imgIdfen.setImageSrc(path);
                                AppUtils.uploadPicture(path, "idcard", new OnUploadListener() {
                                    @Override
                                    public void complete(String url, String simple) {
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


    private void getAddressInfor() {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> subscriber) {
                addressList = AppUtils.getAddressList(activity);
                subscriber.onNext("");
                subscriber.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())     //切换上游线程操作到io线程
                .observeOn(AndroidSchedulers.mainThread())  //切换下游线程到主线程
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object channel) {
                        addressDialog = new AddressDialog(activity, addressList, new AddressDialog.OnSelectListener() {
                            @Override
                            public void select(AddressModel province, AddressModel city) {
                                vAddress.setTag(province.id + (city != null ? "#" + city.id : ""));
                                vAddress.setData(province.name + (city != null ? " " + city.name : ""));
                            }
                        });
                        UserModel user = AppUtils.getUser();
                        if (user != null && user.authStatus != 0)
                            getRZData();
                        else {// 未认证过
                            setEditable(true);
                        }
                    }
                });
    }


    private void renzhengNet() {

        if (isEmptyAndToast(viewName.getEditText(), "请输入企业名称")) return;
        if (isEmptyAndToast(viewCode.getEditText(), "请输入法人身份证")) return;
        if (!RegexUtils.checkIdCard(viewCode.getEditText())) {
            showToast("身份证格式不正确");
            return;
        }
        if (isEmptyAndToast(viewRealName.getEditText(), "请输入真实姓名")) return;
        if (vAddress.getTag() == null) {
            showToast("请选择公司地址");
            return;
        }
        if (isEmptyAndToast(viewPhone.getEditText(), "请输入手机号")) return;
        if(!RegexUtils.checkMobile(viewPhone.getEditText())){
            showToast("手机号格式不正确");
            return;
        }
        if (isEmptyAndToast(viewEmail.getEditText(), "请输入邮箱")) return;
        if(!RegexUtils.checkEmail(viewEmail.getEditText())){
            showToast("邮箱格式不正确");
            return;
        }
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
        String[] tags = vAddress.getTag().toString().split("#");
        map.put("provinceId", tags[0]);
        if (tags.length == 2)
            map.put("cityId", tags[1]);
        else
            map.put("cityId", "");

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

    private void setStatusTip(int status, String error) {
        // // 0：未认证 1：审核中 2：审核失败 3：审核成功
        switch (status) {
            case 3:
                textTip.setText("认证成功");
                break;
            case 2:
                textTip.setText(error);
                break;
            case 1:
                textTip.setText("资料已提交、审核中");
                break;
            default:
                textTip.setText("填写身份证信息，快速认证企业");
                break;
        }

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
                    yeUrl = model.licenseimgpath;
                    cardUrl = model.licenseimgpath;
                    setStatusTip(model.status, model.promptxt);
                    if (model.status != 1 && model.status != 3)
                        setEditable(true);
                    loadAddress(model);
                    vAddress.setData(model.provinceName + " " + model.cityName);
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

    private void loadAddress(CertificationModel model) {
        if (addressList != null && !addressList.isEmpty() && !TextUtils.isEmpty(model.provinceName)) {
            StringBuffer sb = new StringBuffer();
            int provinceIndex = 0;
            int cityIndex = 0;
            for (int i = 0; i < addressList.size(); i++) {
                AddressModel province = addressList.get(i);
                if (province.name.equals(model.provinceName)) {
                    provinceIndex = i;
                    sb.append(province.id);
                    if (province.children != null && !province.children.isEmpty()) {
                        for (int j = 0; j < province.children.size(); j++) {
                            AddressModel city = province.children.get(j);
                            if (city.name.equals(model.cityName)) {
                                cityIndex = j;
                                sb.append("#" + city.id);
                                break;
                            }
                        }
                    }
                    break;
                }
            }
            if (sb.length() > 0)
                vAddress.setTag(sb.toString());
            if (provinceIndex != 0) {
                addressDialog.setSelectPosition(provinceIndex, cityIndex);
            }
        }
    }
}
