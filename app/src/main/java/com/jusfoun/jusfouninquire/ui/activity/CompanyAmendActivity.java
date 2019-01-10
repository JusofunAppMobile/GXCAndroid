package com.jusfoun.jusfouninquire.ui.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.BaseModel;
import com.jusfoun.jusfouninquire.net.model.CompanyDetailMenuModel;
import com.jusfoun.jusfouninquire.net.model.CompanyDetailModel;
import com.jusfoun.jusfouninquire.net.model.QuestResultModel;
import com.jusfoun.jusfouninquire.net.model.UserInfoModel;
import com.jusfoun.jusfouninquire.net.route.PostCompanyAmend;
import com.jusfoun.jusfouninquire.ui.adapter.CompanyAmendAdapter;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.jusfoun.jusfouninquire.ui.widget.DisableMenuEditText;
import com.jusfoun.jusfouninquire.ui.widget.FullyGridLayoutManger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;

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
    private List<HashMap<String, Object>> list = new ArrayList<>();
    private String mCompanyid, mSuggest, mContactInformation, mCompanyname;
    private boolean isHasCheck = false;

    /**
     * 对象
     */
    private CompanyAmendAdapter amendAdapter;
    private CompanyDetailModel model;
    private UserInfoModel userInfo;
    private int position;

    private ViewGroup vQuestion;


    private int type;//

    public static final int TYPE_OBJECTION = 1, TYPE_ERROR = 2;

    public static String TYPE = "type";

    @Override
    protected void initData() {
        super.initData();
        if ((getIntent() != null) && (getIntent().getExtras() != null)) {
            model = (CompanyDetailModel) getIntent().getExtras().getSerializable("company");
            Log.e("tag", "model===" + new Gson().toJson(model));
            type = getIntent().getExtras().getInt(TYPE, TYPE_ERROR);
        }


        position = getIntent().getExtras().getInt(POSITION, -1);
        if (model != null) {
            mCompanyid = model.getCompanyid();
            mCompanyname = model.getCompanyname();
            for (int i = 0; i < model.getSubclassMenu().size(); i++) {
                HashMap<String, Object> map = new HashMap<>();
                if (i == position)
                    map.put("check", "true");
                else
                    map.put("check", "false");
                map.put("company", model.getSubclassMenu().get(i));
                list.add(map);
            }
        }
        amendAdapter = new CompanyAmendAdapter(mContext);
        userInfo = InquireApplication.getUserInfo();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_company_amend);
        titleView = (TitleView) findViewById(R.id.titleView);
        vQuestion = (ViewGroup) findViewById(R.id.vQuestion);
//        titleView.setLeftImage(R.mipmap.close_icon);
        titleView.setTitle("纠错");

        recyclerView = (RecyclerView) findViewById(R.id.company_menu);
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


    }

    /**
     * 回答问题
     *
     * @param view
     * @param questionid
     * @param answerid
     */
    private void askQuestion(final View view, final View subView, String questionid, String answerid) {
        showLoading();
        HashMap<String, String> params = new HashMap<>();
        if (userInfo != null && !TextUtils.isEmpty(userInfo.getUserid())) {
            params.put("userid", userInfo.getUserid());
        } else {
            params.put("userid", "");
        }
        params.put("questionid", questionid);
        params.put("answerid", answerid);
        PostCompanyAmend.askQuestion(mContext, params, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                BaseModel model = (BaseModel) data;
                if (model.success()) {
                    view.setVisibility(View.GONE);
                    showToast("已提交");
                } else {
                    subView.setSelected(false);
                    showToast(model.getMsg());
                }
            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                subView.setSelected(false);
                Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 加载问题列表
     */
    private void loadQuestion() {

        showLoading();
        HashMap<String, String> params = new HashMap<>();
        if (userInfo != null && !TextUtils.isEmpty(userInfo.getUserid())) {
            params.put("userid", userInfo.getUserid());
        } else {
            params.put("userid", "");
        }
        PostCompanyAmend.getQuestionList(mContext, params, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                QuestResultModel model = (QuestResultModel) data;
                buildQuestionList(model);
            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buildQuestionList(QuestResultModel model) {

        if (model == null || model.questionlist == null || model.questionlist.isEmpty())
            return;

        for (final QuestResultModel.QuestionlistBean bean : model.questionlist) {
            final View view = View.inflate(this, R.layout.view_question, null);
            TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            ViewGroup vAnswer = (ViewGroup) view.findViewById(R.id.vAnswer);
            tvTitle.setText(bean.title);
            if (bean.answerlist != null && !bean.answerlist.isEmpty()) {
                int num = bean.answerlist.size() / 2;
                for (int i = 0; i < num; i++) {
                    View subView = View.inflate(this, R.layout.view_aswer, null);
                    final View v1 = subView.findViewById(R.id.v1);
                    final View v2 = subView.findViewById(R.id.v2);

                    TextView tv1 = (TextView) subView.findViewById(R.id.tv1);
                    TextView tv2 = (TextView) subView.findViewById(R.id.tv2);

                    final QuestResultModel.QuestionlistBean.AnswerlistBean answerBean1 = bean.answerlist.get(i * 2);
                    tv1.setText(answerBean1.content);

                    v1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v1.setSelected(!v1.isSelected());
                            askQuestion(view, v, bean.questionid, answerBean1.answerid);
                        }
                    });

                    if (i * 2 + 1 <= bean.answerlist.size() - 1) {
                        final QuestResultModel.QuestionlistBean.AnswerlistBean answerBean2 = bean.answerlist.get(i * 2 + 1);
                        tv2.setText(answerBean2.content);

                        v2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                v2.setSelected(!v2.isSelected());
                                askQuestion(view, v, bean.questionid, answerBean2.answerid);
                            }
                        });
                    }

                    vAnswer.addView(subView);
                }
            }
            vQuestion.addView(view);
        }
    }

    @Override
    protected void initWidgetActions() {

        recyclerView.setLayoutManager(new FullyGridLayoutManger(mContext, 4));
        recyclerView.setAdapter(amendAdapter);
        amendAdapter.refresh(list);
        amendAdapter.setOnClickListener(new CompanyAmendAdapter.OnAmendAdapterOnClickListener() {
            @Override
            public void onClick(int position) {
                if (list.get(position).get("check").toString().equals("true")) {
                    list.get(position).put("check", "false");
                } else {
                    list.get(position).put("check", "true");
                }
                amendAdapter.refresh(list);
            }
        });

        loadQuestion();

        submitAmend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postCompanyAmend();
            }
        });

        mErrorContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Editable editable = mErrorContent.getText();
                int len = editable.length();
                if (len > contentMaxLength) {
                    Selection.setSelection(editable, contentMaxLength);
                    Toast.makeText(getApplicationContext(), getString(R.string.feedback_contact_max), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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

        if (model != null) {
            textCompany.setText(model.getCompanyname());
            textXinyongCode.setText(model.taxid);
            textCompayType.setText(model.getStates());
        }

        if (type == TYPE_ERROR) {
            layoutUserInfo.setVisibility(View.VISIBLE);
            titleView.setTitle("异议纠错");
        } else {
            layoutUserInfo.setVisibility(View.GONE);
            titleView.setTitle("信用异议");
        }

        textCompany.setTypeface(Typeface.DEFAULT_BOLD);
    }

    private void postCompanyAmend() {

        JSONArray array = new JSONArray();
        for (int i = 0; i < amendAdapter.getList().size(); i++) {
            String str = amendAdapter.getList().get(i).get("check").toString();
            CompanyDetailMenuModel menuItem = (CompanyDetailMenuModel) amendAdapter.getList().get(i).get("company");
            if ("true".equals(str)) {
                isHasCheck = true;
                JSONObject object = new JSONObject();
                try {
                    object.put("id", menuItem.getMenuid());
                    array.put(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.e(TAG, array.toString());

        if (TextUtils.isEmpty(mErrorContent.getText()) && TextUtils.isEmpty(mContactEdit.getText())
                && !isHasCheck) {
            Toast.makeText(mContext, "请纠正我们的错误，或留下联系方式方便我们联系您", Toast.LENGTH_SHORT).show();
            return;
        }

        showLoading();
        HashMap<String, String> params = new HashMap<>();
        params.put("companyid", mCompanyid);
        params.put("companyname", mCompanyname);
        params.put("suggest", mErrorContent.getText().toString());
        params.put("contactinformation", mContactEdit.getText().toString());
        params.put("ids", array.toString());
        if (userInfo != null && !TextUtils.isEmpty(userInfo.getUserid())) {
            params.put("userid", userInfo.getUserid());
        } else {
            params.put("userid", "");
        }
        PostCompanyAmend.postCompanyAmend(mContext, params, getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                BaseModel model = (BaseModel) data;
                if (model.getResult() == 0) {
                    Toast.makeText(mContext, model.getMsg(), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(mContext, model.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
