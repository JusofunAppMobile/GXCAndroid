package com.jusfoun.jusfouninquire.ui.activity;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gxc.model.ErrorMenuModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.utils.LogUtils;
import com.gxc.utils.ToastUtils;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.net.model.CompanyDetailMenuModel;
import com.jusfoun.jusfouninquire.net.model.UserInfoModel;
import com.jusfoun.jusfouninquire.ui.adapter.CompanyAmendAdapter;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.jusfoun.jusfouninquire.ui.widget.DisableMenuEditText;
import com.jusfoun.jusfouninquire.ui.widget.FullyGridLayoutManger;
import com.siccredit.guoxin.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/13.
 * Description 企业详情纠错页面
 */
public class CompanyAmendActivity extends BaseInquireActivity {
    /**
     * 常量
     */

    private static final int contactMaxLength = 20;
    private static final int contentMaxLength = 5000;
    public static final String POSITION = "position";
    protected TextView textCompany;
    protected TextView textXinyongCode;
    protected TextView textCompayType;
    protected DisableMenuEditText editName;
    protected DisableMenuEditText editCode;
    protected DisableMenuEditText editEmail;
    protected LinearLayout layoutUserInfo;


    /**
     * 组件
     */
    private RecyclerView recyclerView;
    private Button submitAmend;
    private TitleView titleView;
    private DisableMenuEditText mErrorContent, mContactEdit;
    /**
     * 变量
     */
    private String mCompanyid, mCompanyname, taxid, states;

    /**
     * 对象
     */
    private CompanyAmendAdapter amendAdapter, typeAdapter;
    private UserInfoModel userInfo;

    private int type;//

    public static final int TYPE_OBJECTION = 1, TYPE_ERROR = 2;

    public static String TYPE = "type";


    private RecyclerView typeRecycleView;

    private List<ErrorMenuModel.ObjectionItem> objectionList;//所有分类

    private NetWorkErrorView vNet;

    @Override
    protected void initData() {
        super.initData();
        type = getIntent().getExtras().getInt(TYPE, TYPE_ERROR);
        mCompanyname = getIntent().getStringExtra("companyName");
        mCompanyid = getIntent().getStringExtra("companyId");
        taxid = getIntent().getStringExtra("taxid");
        states = getIntent().getStringExtra("states");

        amendAdapter = new CompanyAmendAdapter(mContext);
        typeAdapter = new CompanyAmendAdapter(mContext);
        userInfo = InquireApplication.getUserInfo();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_company_amend);
        titleView = (TitleView) findViewById(R.id.titleView);
//        titleView.setLeftImage(R.mipmap.close_icon);
        titleView.setTitle("纠错");

        recyclerView = (RecyclerView) findViewById(R.id.company_menu);
        vNet = findViewById(R.id.vNet);
        submitAmend = (Button) findViewById(R.id.submit_amend);
        mErrorContent = (DisableMenuEditText) findViewById(R.id.error_content);
        mContactEdit = (DisableMenuEditText) findViewById(R.id.contact_edit);
        textCompany = (TextView) findViewById(R.id.text_company);
        textXinyongCode = (TextView) findViewById(R.id.text_xinyong_code);
        textCompayType = (TextView) findViewById(R.id.text_compay_type);
        editName = (DisableMenuEditText) findViewById(R.id.edit_name);
        editCode = (DisableMenuEditText) findViewById(R.id.edit_code);
        editEmail = (DisableMenuEditText) findViewById(R.id.edit_email);
        layoutUserInfo = (LinearLayout) findViewById(R.id.layout_user_info);
        typeRecycleView = (RecyclerView) findViewById(R.id.recyclerView_type);

    }


    @Override
    protected void initWidgetActions() {

        recyclerView.setLayoutManager(new FullyGridLayoutManger(mContext, 4));
        recyclerView.setAdapter(amendAdapter);

        typeRecycleView.setLayoutManager(new FullyGridLayoutManger(mContext, 4));
        typeRecycleView.setAdapter(typeAdapter);

        typeAdapter.setOnClickListener(new CompanyAmendAdapter.OnAmendAdapterOnClickListener() {
            @Override
            public void onClick(int position) {
                if (objectionList != null) {
                    amendAdapter.refresh(objectionList.get(position).menuList);
                    typeAdapter.setSelectItem(position);

                }

            }
        });


        submitAmend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == TYPE_ERROR) {
                    postCompanyAmend();
                } else {
                    postServiceAmend();
                }

            }
        });

        mErrorContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Editable editable = mErrorContent.getText();
                int len = editable.length();
                if (len > contentMaxLength) {
                    Selection.setSelection(editable, contentMaxLength);
                    Toast.makeText(getApplicationContext(), getString(R.string.feedback_contact_max), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mContactEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Editable editable = mContactEdit.getText();
                int len = editable.length();
                if (len >= contactMaxLength) {
                    Selection.setSelection(editable, contactMaxLength);
                    Toast.makeText(getApplicationContext(), getString(R.string.feedback_contact_max), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        mErrorContent.setLongClickable(false);
        mContactEdit.setLongClickable(false);

        textCompany.setText(mCompanyname);
        textXinyongCode.setText(taxid);
        textCompayType.setText(states);

        if (type == TYPE_ERROR) {
            layoutUserInfo.setVisibility(View.VISIBLE);
            titleView.setTitle("异议纠错");
        } else {
            layoutUserInfo.setVisibility(View.GONE);
            titleView.setTitle("企业异议");
        }

        textCompany.setTypeface(Typeface.DEFAULT_BOLD);

        getErrorTypeList();

        vNet.setListener(new NetWorkErrorView.OnGXCRefreshListener() {
            @Override
            public void OnNetRefresh() {
                getErrorTypeList();
            }
        });
    }


    /**
     * 异议纠错
     */
    private void postCompanyAmend() {
        Map<String, CompanyDetailMenuModel> map = amendAdapter.getSelectMap();

        if (map.isEmpty()) {
            ToastUtils.show("请选择异议信息");
            return;
        }

        if (TextUtils.isEmpty(getValue(editName))) {
            ToastUtils.show("请输入真实姓名");
            return;
        }

        if (TextUtils.isEmpty(getValue(editCode))) {
            ToastUtils.show("请输入身份证号");
            return;
        }

        if (TextUtils.isEmpty(getValue(mContactEdit))) {
            ToastUtils.show("请输入联系电话");
            return;
        }

        if (TextUtils.isEmpty(getValue(editEmail))) {
            ToastUtils.show("请输入电子邮箱");
            return;
        }

        if (TextUtils.isEmpty(mErrorContent.getText())) {
            ToastUtils.show("请输入详细描述信息");
            return;
        }

        StringBuffer menuIds = new StringBuffer();
        StringBuffer menuNames = new StringBuffer();
        if (map != null) {
            for (CompanyDetailMenuModel model : map.values()) {
                menuIds.append(model.getMenuid() + ",");
                menuNames.append(model.menuName + ",");
            }
        }
        if (menuIds.toString().endsWith(","))
            menuIds.deleteCharAt(menuIds.lastIndexOf(","));
        if (menuNames.toString().endsWith(","))
            menuNames.deleteCharAt(menuNames.lastIndexOf(","));

        LogUtils.e("menuIds=" + menuIds);
        LogUtils.e("menuNames=" + menuNames);


        showLoading();
        HashMap<String, Object> params = new HashMap<>();
        params.put("CompanyName", mCompanyname);
        params.put("menuid", menuIds);
        params.put("menuName", menuNames);
        params.put("name", editName.getText().toString());
        params.put("IDCard", editCode.getText().toString());
        params.put("phone", mContactEdit.getText().toString());
        params.put("Email", editEmail.getText().toString());
        params.put("errorMsg", mErrorContent.getText().toString());

        RxManager.http(RetrofitUtils.getApi().ObjectionError(params), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    showToast("感谢您的反馈");
                    finish();
                } else {
                    Toast.makeText(mContext, model.getMsg(), Toast.LENGTH_SHORT).show();
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
     * 企业异议
     */
    private void postServiceAmend() {
        Map<String, CompanyDetailMenuModel> map = amendAdapter.getSelectMap();

        if (map.isEmpty()) {
            ToastUtils.show("请选择异议信息");
            return;
        }

        if (TextUtils.isEmpty(mErrorContent.getText())) {
            ToastUtils.show("请输入详细描述信息");
            return;
        }

        StringBuffer menuIds = new StringBuffer();
        StringBuffer menuNames = new StringBuffer();
        if (map != null) {
            for (CompanyDetailMenuModel model : map.values()) {
                menuIds.append(model.getMenuid() + ",");
                menuNames.append(model.menuName + ",");
            }
        }
        if (menuIds.toString().endsWith(","))
            menuIds.deleteCharAt(menuIds.lastIndexOf(","));
        if (menuNames.toString().endsWith(","))
            menuNames.deleteCharAt(menuNames.lastIndexOf(","));

        showLoading();
        HashMap<String, Object> params = new HashMap<>();
        params.put("CompanyName", mCompanyname);
        params.put("menuid", menuIds);
        params.put("menuName", menuNames);
        params.put("errorMsg", mErrorContent.getText().toString());

        RxManager.http(RetrofitUtils.getApi().creditError(params), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    showToast("感谢您的反馈");
                    finish();
                } else {
                    Toast.makeText(mContext, model.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void error() {
                hideLoadDialog();
                ToastUtils.showHttpError();
            }
        });
    }

    private void getErrorTypeList() {
        vNet.showLoading();
        RxManager.http(RetrofitUtils.getApi().getErrorTypeList(), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                if (model.success()) {
                    vNet.success();
                    ErrorMenuModel errorMenuModel = model.dataToObject(ErrorMenuModel.class);

                    List<ErrorMenuModel.ObjectionItem> objectionList = errorMenuModel.objectionList;
                    CompanyAmendActivity.this.objectionList = objectionList;
                    if (objectionList != null && objectionList.size() > 0) {
                        amendAdapter.refresh(objectionList.get(0).menuList);

                        List<CompanyDetailMenuModel> typeMenuList = new ArrayList<>();
                        for (int i = 0; i < objectionList.size(); i++) {
                            CompanyDetailMenuModel companyDetailMenuModel = new CompanyDetailMenuModel();
                            companyDetailMenuModel.setMenuid(objectionList.get(i).id);
                            companyDetailMenuModel.menuName = objectionList.get(i).typeName;
                            typeMenuList.add(companyDetailMenuModel);
                        }
                        typeAdapter.refresh(typeMenuList);
                        typeAdapter.setSelectItem(0);

                    }

                } else {
                    vNet.error();
                }
            }

            @Override
            public void error() {
                vNet.error();
            }
        });

    }
}
