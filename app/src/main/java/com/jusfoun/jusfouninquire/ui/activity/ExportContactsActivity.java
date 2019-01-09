package com.jusfoun.jusfouninquire.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.ContactModel;
import com.jusfoun.jusfouninquire.net.model.ContactsModel;
import com.jusfoun.jusfouninquire.net.model.LoginModel;
import com.jusfoun.jusfouninquire.net.model.UserInfoModel;
import com.jusfoun.jusfouninquire.net.route.LoginRegisterHelper;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.jusfoun.jusfouninquire.ui.widget.EmailSendDialog;
import com.jusfoun.jusfouninquire.ui.widget.TableRowView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 通讯录导出页面
 */
public class ExportContactsActivity extends BaseInquireActivity {

    TitleView titleView;
    TableLayout tableLayout;


    private List<ContactsModel> models = new ArrayList<>();
    private TextView moreText;


    @Override
    protected void initView() {
        setContentView(R.layout.act_export_contacts);
        moreText = (TextView) findViewById(R.id.text_look_more);
        titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setTitle("企业通讯录");
        titleView.setRightImage(R.mipmap.img_ig);
        titleView.setRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onClick(View v) {
                export();
            }
        });

        tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        String model = getIntent().getStringExtra("model");
        try {
            JSONArray array = new JSONArray(model);
            if (array.length() > 0) {
                Gson gson = new Gson();

                if (array.length() > 5) {
                    moreText.setVisibility(View.VISIBLE);
                }

                for (int i = 0; i < array.length(); i++) {
                    ContactsModel cm = gson.fromJson(array.getString(i), ContactsModel.class);
                    if(i<5) {
                        TableRowView row = new TableRowView(this);
                        row.setModel(cm);
                        tableLayout.addView(row);
                    }
                    models.add(cm);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        moreText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                export();
            }
        });

    }

    private void export() {
        UserInfoModel userModel = InquireApplication.getUserInfo();
        if (userModel == null || TextUtils.isEmpty(userModel.getUserid())) {
            goActivity(LoginActivity.class);
            return;
        }

        getUserInfoFromNet(userModel.getUserid());
    }

    /**
     * 获取个人信息
     *
     * @param userid
     */
    private void getUserInfoFromNet(String userid) {
        showLoading();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userid", userid);
        showLoading();
        LoginRegisterHelper.getContactUser(mContext, map, ((Activity) mContext).getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                ContactModel model = (ContactModel) data;

                UserInfoModel userModel = InquireApplication.getUserInfo();
                if (!TextUtils.isEmpty(userModel.getUserid())) {
                    if (model.getResult() == 0) {
                        if (model.data != null && model.data.contactsType == 1) {
                            if (models != null && !models.isEmpty()) {
                                StringBuffer sb = new StringBuffer();
                                for (ContactsModel m : models) {
                                    sb.append(m.id + ",");
                                }
                                if (sb.toString().endsWith(","))
                                    sb.deleteCharAt(sb.length() - 1);
                                new EmailSendDialog(ExportContactsActivity.this, "",sb.toString(),EmailSendDialog.TYPE_CONTENT).show();
                            }
                        } else {
                            WebActivity.contactPay(ExportContactsActivity.this, model.data.contactsUrl);
                        }
                    } else {
                        showToast(model.getMsg() + "");
                    }
                }
            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                showToast(error);
            }
        });

    }


    @Override
    protected void initWidgetActions() {

    }
}
