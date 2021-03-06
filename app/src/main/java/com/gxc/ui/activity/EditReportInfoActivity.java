package com.gxc.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.gxc.base.BaseActivity;
import com.gxc.event.WebRefreshEvent;
import com.gxc.inter.OnSimpleCompressListener;
import com.gxc.inter.OnUploadListener;
import com.gxc.model.EditReportInfoImgModel;
import com.gxc.model.EditReportInfoTextModel;
import com.gxc.model.UserModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.view.CorporateIRxImgView;
import com.gxc.ui.view.CorporateInfoView;
import com.gxc.utils.AppUtils;
import com.gxc.utils.LogUtils;
import com.gxc.utils.ToastUtils;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.siccredit.guoxin.R;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import de.greenrobot.event.EventBus;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/714:55
 * @Email zyp@jusfoun.com
 * @Description ${编辑自主填报页面}
 */
public class EditReportInfoActivity extends BaseActivity {


    @BindView(R.id.titlebar)
    TitleView titlebar;
    @BindView(R.id.layout_view)
    LinearLayout layoutView;


    @BindView(R.id.net_work_error)
    NetWorkErrorView netWorkError;

    private int type = 1;
    private String id = "";
    private String imagePath;

    private CorporateInfoView corporateInfoView;
    private CorporateIRxImgView corporateIRxImgView;

    private boolean isEdit = true;

    public static final int TYPE_INFO = 1;//企业信息
    public static final int TYPE_PRODUCE = 2;//企业产品
    public static final int TYPE_RY = 3;//企业荣誉
    public static final int TYPE_HB = 4;//企业伙伴
    public static final int TYPE_CY = 5;//企业成员

    public static String TYPE = "type";
    public static String ID = "id";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_repory_info;
    }

    @Override
    public void initActions() {
        corporateInfoView = new CorporateInfoView(this);
        corporateIRxImgView = new CorporateIRxImgView(this);
        type = getIntent().getIntExtra(TYPE, TYPE_INFO);
        id = getIntent().getStringExtra(ID);

        netWorkError.setListener(new NetWorkErrorView.OnGXCRefreshListener() {
            @Override
            public void OnNetRefresh() {
                loadByType();
            }
        });

        titlebar.setRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit) {
                    setEditable();
                } else {
//                    UpdateReoprtInfoEvent event = new UpdateReoprtInfoEvent();
//                    event.type = type;

//                    Log.e("tag", "typetypetypetype2=" + type);
//                    event.editReportInfoTextModel = corporateInfoView.getData();
//                    event.editReportInfoImgModel = corporateIRxImgView.getData();
//                    EventBus.getDefault().post(event);

                    if (type == TYPE_INFO) {
                        editCompantInfo();
                    } else if (type == TYPE_PRODUCE) {
                        productEditor();
                    } else if (type == TYPE_RY) {
                        honorEditor();
                    } else if (type == TYPE_HB) {
                        partnerEditor();
                    } else if (type == TYPE_CY) {
                        CompanyEmpEditor();
                    }
                }
            }
        });
        loadByType();
    }

    private void loadByType() {
        if (type == TYPE_INFO) {
            titlebar.setTitle("企业信息");
            if (!TextUtils.isEmpty(id))  // 修改
                getCompanyDetail();
            else {
                corporateInfoView.setData(TYPE_INFO, null);
                layoutView.addView(corporateInfoView); // 添加
                setEditable();
            }

        } else if (type == TYPE_PRODUCE) {
            titlebar.setTitle("企业产品");
            if (!TextUtils.isEmpty(id))  // 修改
                getProductDetail();
            else {
                corporateInfoView.setData(TYPE_PRODUCE, null);
                layoutView.addView(corporateInfoView); // 添加
                setEditable();
            }

        } else if (type == TYPE_RY) {
            titlebar.setTitle("企业荣誉");
            if (!TextUtils.isEmpty(id)) {
                getRy();
            } else {
                corporateIRxImgView.setData(TYPE_RY, null);
                layoutView.addView(corporateIRxImgView);
                setEditable();
            }
        } else if (type == TYPE_HB) {
            titlebar.setTitle("企业伙伴");
            if (!TextUtils.isEmpty(id)) {
                getHB();
            } else {
                corporateIRxImgView.setData(TYPE_HB, null);
                layoutView.addView(corporateIRxImgView);
                setEditable();
            }
        } else if (type == TYPE_CY) {
            titlebar.setTitle("企业成员");
            if (!TextUtils.isEmpty(id)) {
                getCy();
            } else {
                corporateIRxImgView.setData(TYPE_CY, null);
                layoutView.addView(corporateIRxImgView);
                setEditable();
            }
        }
    }

    private void setEditable() {
        isEdit = false;
        titlebar.setRightText("完成");
        corporateInfoView.setEditTable(true);
        corporateIRxImgView.setEditTable(true);
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

                            AppUtils.compress(activity, file, new OnSimpleCompressListener() {
                                @Override
                                public void complete(String path) {
                                    if (!TextUtils.isEmpty(path)) {
                                        if (type == TYPE_INFO || type == TYPE_PRODUCE) {
                                            corporateInfoView.setImageSrc(path);
                                            AppUtils.uploadPicture(path, getUploadKey(), new OnUploadListener() {
                                                @Override
                                                public void complete(String url, String simple) {
                                                    corporateInfoView.setImagePath(simple);
                                                }
                                            });
                                        } else {
                                            corporateIRxImgView.setImageSrc(path);
                                            AppUtils.uploadPicture(path, getUploadKey(), new OnUploadListener() {
                                                @Override
                                                public void complete(String url, String simple) {
                                                    corporateIRxImgView.setImagePath(simple);
                                                }
                                            });
                                        }
                                    }
                                }
                            });
                        }
                    }
                    break;
            }
        }
    }

    private String getUploadKey() {
        switch (type) {
            case TYPE_INFO:
                return "logo";
            case TYPE_PRODUCE:
                return "product";
            case TYPE_RY:
                return "honor";
            case TYPE_HB:
                return "partner";
            case TYPE_CY:
                return "member";
        }
        return "";
    }

    /**
     * 编辑企业信息
     */
    private void editCompantInfo() {
        EditReportInfoTextModel model = corporateInfoView.getData();
        if (model == null) {
            return;
        }

        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("industry", model.industry);
        map.put("phone", model.phone);
        map.put("email", model.email);
        map.put("webURL", model.webURL);
        map.put("logo", model.urlComplete);
        map.put("industry", model.industry);
        map.put("introduce", model.introduce);
        map.put("companyName", model.companyName);
        if (!TextUtils.isEmpty(id))
            map.put("companyId", id);


        RxManager.http(RetrofitUtils.getApi().CompanyInfoEditor(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    ToastUtils.show("提交成功");
                    setStates();
                    finishDelay();
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

    /**
     * 编辑企业产品
     */
    private void productEditor() {
        EditReportInfoTextModel model = corporateInfoView.getData();
        if (model == null) {
            return;
        }

        showLoading();
        HashMap<String, Object> map = new HashMap<>();

        map.put("companyName", model.companyName);
        map.put("industry", model.industry);
        map.put("product", model.product);
        map.put("tag", model.tag);
        map.put("url", model.url);
        map.put("image", model.urlComplete);
        map.put("introduce", model.introduce);
        if (!TextUtils.isEmpty(id))
            map.put("productId", id);

        RxManager.http(RetrofitUtils.getApi().productEditor(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    ToastUtils.show("提交成功");
                    setStates();
                    finishDelay();
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

    /**
     * 编辑企业荣誉
     */
    private void honorEditor() {
        EditReportInfoImgModel model = corporateIRxImgView.getData();
        if (model == null) {
            return;
        }

        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("honor", model.honor);
        map.put("image", model.urlComplete);
        map.put("introduce", model.introduce);
        if (!TextUtils.isEmpty(id))
            map.put("honorId", id);
        RxManager.http(RetrofitUtils.getApi().honorEditor(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    ToastUtils.show("提交成功");
                    setStates();
                    finishDelay();
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


    /**
     * 编辑企业伙伴
     */
    private void partnerEditor() {
        EditReportInfoImgModel model = corporateIRxImgView.getData();
        if (model == null) {
            return;
        }
        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("partner", model.partner);
        map.put("image", model.urlComplete);
        map.put("introduce", model.introduce);
        if (!TextUtils.isEmpty(id))
            map.put("partnerId", id);


        RxManager.http(RetrofitUtils.getApi().partnerEditor(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    ToastUtils.show("提交成功");
                    setStates();
                    finishDelay();
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

    /**
     * 编辑企业成员
     */
    private void CompanyEmpEditor() {
        EditReportInfoImgModel model = corporateIRxImgView.getData();
        if (model == null) {
            return;
        }
        showLoading();

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", model.name);
        map.put("position", model.position);
        map.put("image", model.urlComplete);
        map.put("introduce", model.introduce);
        if (!TextUtils.isEmpty(id))
            map.put("empId", id);

        RxManager.http(RetrofitUtils.getApi().CompanyEmpEditor(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    ToastUtils.show("提交成功");
                    setStates();
                    finishDelay();
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


    private void setStates() {
        EventBus.getDefault().post(new WebRefreshEvent());
        isEdit = true;
        titlebar.setRightText("编辑");
        corporateInfoView.setEditTable(false);
        corporateIRxImgView.setEditTable(false);
    }


    /**
     * 获取企业信息
     */
    private void getCompanyDetail() {
        netWorkError.showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("companyId", id);
        RxManager.http(RetrofitUtils.getApi().getCompanyInfo(map), new ResponseCall() {

            @Override
            public void success(NetModel data) {
                if (data.success()) {
                    titlebar.setRightText("编辑");
                    netWorkError.success();
                    EditReportInfoTextModel model = data.dataToObject(EditReportInfoTextModel.class);
                    corporateInfoView.setData(TYPE_INFO, model);
                    layoutView.addView(corporateInfoView);
                } else {
                    netWorkError.error();
                }
            }

            @Override
            public void error() {
                netWorkError.error();
            }
        });
    }

    /**
     * 获取企业产品信息
     */
    private void getProductDetail() {
        netWorkError.showLoading();
        HashMap<String, Object> map = new HashMap<>();
        UserModel userModel = AppUtils.getUser();
        if (userModel != null) {
            map.put("productId", id);
        }
        RxManager.http(RetrofitUtils.getApi().getProductList(map), new ResponseCall() {

            @Override
            public void success(NetModel data) {
                if (data.success()) {
                    titlebar.setRightText("编辑");
                    netWorkError.success();
                    EditReportInfoTextModel model = data.dataToObject(EditReportInfoTextModel.class);
                    corporateInfoView.setData(TYPE_PRODUCE, model);
                    layoutView.addView(corporateInfoView);

                } else {
                    netWorkError.error();
                }
            }

            @Override
            public void error() {
                netWorkError.error();
            }
        });
    }

    /**
     * 获取企业荣誉
     */
    private void getRy() {
        netWorkError.showLoading();
        HashMap<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(id))
            map.put("honorId", id);
        RxManager.http(RetrofitUtils.getApi().getHonorList(map), new ResponseCall() {

            @Override
            public void success(NetModel data) {
                if (data.success()) {
                    titlebar.setRightText("编辑");
                    netWorkError.success();
                    EditReportInfoImgModel model = data.dataToObject(EditReportInfoImgModel.class);
                    corporateIRxImgView.setData(TYPE_RY, model);
                    layoutView.addView(corporateIRxImgView);
                } else {
                    netWorkError.error();
                }
            }

            @Override
            public void error() {
                netWorkError.error();
            }
        });
    }

    /**
     * 获取伙伴
     */
    private void getHB() {
        netWorkError.showLoading();
        HashMap<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(id))
            map.put("partnerId", id);
        RxManager.http(RetrofitUtils.getApi().getpartnerList(map), new ResponseCall() {

            @Override
            public void success(NetModel data) {
                if (data.success()) {
                    titlebar.setRightText("编辑");
                    netWorkError.success();
                    EditReportInfoImgModel model = data.dataToObject(EditReportInfoImgModel.class);
                    corporateIRxImgView.setData(TYPE_HB, model);
                    layoutView.addView(corporateIRxImgView);
                } else {
                    netWorkError.error();
                }
            }

            @Override
            public void error() {
                netWorkError.error();
            }
        });
    }

    /**
     * 获取企业成员
     */
    private void getCy() {
        netWorkError.showLoading();
        HashMap<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(id))
            map.put("empId", id);
        RxManager.http(RetrofitUtils.getApi().getEmployerList(map), new ResponseCall() {

            @Override
            public void success(NetModel data) {
                if (data.success()) {
                    titlebar.setRightText("编辑");
                    netWorkError.success();
                    EditReportInfoImgModel model = data.dataToObject(EditReportInfoImgModel.class);
                    corporateIRxImgView.setData(TYPE_CY, model);
                    layoutView.addView(corporateIRxImgView);
                } else {
                    netWorkError.error();
                }
            }

            @Override
            public void error() {
                netWorkError.error();
            }
        });
    }

    private void showHttpError() {
        netWorkError.error();
    }
}
