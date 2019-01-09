package com.jusfoun.jusfouninquire.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.CompanyModel;
import com.jusfoun.jusfouninquire.net.model.SearchCompanyModel;
import com.jusfoun.jusfouninquire.net.route.LoginRegisterHelper;
import com.jusfoun.jusfouninquire.ui.adapter.SetCompanyAdapter;
import com.jusfoun.jusfouninquire.ui.util.AppUtil;
import com.jusfoun.jusfouninquire.ui.util.KeyBoardUtil;
import com.jusfoun.jusfouninquire.ui.util.LibIOUtil;
import com.jusfoun.jusfouninquire.ui.view.BackAndRightImageTitleView;
import com.jusfoun.jusfouninquire.ui.view.XListView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lee
 * @version create time:2015/11/1811:55
 * @Email email
 * @Description $设置公司
 */

public class SetCompanyActivity extends BaseInquireActivity{
    /**常量*/
    public static final String COMPANY_KEY = "companyName";

    /**对象*/
    private SetCompanyAdapter adapter;

    /**变量*/
    private String searchkey;
    private Map<String, Object> map;
    private String alreadSaveCompany_Name = "";

    private String companyId = "";
    private String companyName = "";

    /**组件*/
    private XListView mListView;
    private EditText query_company_key;
    private View emptylayout;
    private TextView fail_data_text;
    private ProgressBar progress;
    private BackAndRightImageTitleView titleView;
    private int pageindex;
    private ImageView cencel;

    @Override
    protected void initData() {
        super.initData();
        pageindex=0;
        map = new HashMap<String, Object>();
        alreadSaveCompany_Name = getIntent().getStringExtra(COMPANY_KEY);

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_set_company);
        titleView = (BackAndRightImageTitleView) findViewById(R.id.titleView);
        titleView.setLeftViewBackgroundResource(R.mipmap.back_image);
        titleView.setTitleText("设置公司");
        titleView.setmImageTxt("确定");
        titleView.setRightTextColor(getResources().getColor(R.color.white));

        query_company_key = (EditText) findViewById(R.id.query_company_key);
        searchkey = alreadSaveCompany_Name;
        mListView = (XListView) findViewById(R.id.gl_company_list);
        emptylayout = findViewById(R.id.emptylayout);
        fail_data_text = (TextView) emptylayout.findViewById(R.id.fail_data_text);
        cencel = (ImageView)findViewById(R.id.cencel);
        progress = (ProgressBar) emptylayout.findViewById(R.id.progress);

        query_company_key.requestFocus();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AppUtil.showSoftInput(SetCompanyActivity.this, query_company_key);
            }
        }, 300);
    }

    @Override
    protected void initWidgetActions() {
        titleView.setmRightClickListener(new BackAndRightImageTitleView.RightClickListener() {
            @Override
            public void onClick() {
                companyName = query_company_key.getText().toString().trim();
                if (TextUtils.isEmpty(companyName)) {
                    showToast("请输入公司名称");
                    return;
                }

                backUpCompany();
            }
        });

        cencel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        adapter = new SetCompanyAdapter(mContext);
        mListView.setAdapter(adapter);
        query_company_key.setText(alreadSaveCompany_Name);
        query_company_key.setSelection(query_company_key.getText().length());
        String libkey = LibIOUtil.convertStreamToStr(getResources().openRawResource(R.raw.nosearchdata));
        String[] keys = libkey.split(",");
        for (String str : keys) {
            map.put(str, null);
        }

        mListView.setPullRefreshEnable(false);
        mListView.setPullLoadEnable(false);
        mListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                getCompany(false);
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CompanyModel model = (CompanyModel) parent.getItemAtPosition(position);
                if (model==null)
                    return;
                companyId = model.getCompanyid();
                companyName = Html.fromHtml(model.getCompanyname()).toString();
                /*if(isUpdate){
                    updateCompany("cname", companyName);

                }else {
                    backUpCompany();
                }*/
                backUpCompany();
                KeyBoardUtil.hideSoftKeyboard(SetCompanyActivity.this);
            }
        });

        query_company_key.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if (arg0.toString().trim().length() < 2) {
                    if(arg0.toString().trim().length() == 0){
                        emptylayout.setVisibility(View.VISIBLE);
                        fail_data_text.setVisibility(View.VISIBLE);
                        fail_data_text.setText("输入所要查找的公司名称");
                        progress.setVisibility(View.GONE);
                        delayHandler.removeMessages(start_);
                    }
                    return;
                }

                if (map.containsKey(arg0.toString().trim())) {
                    emptylayout.setVisibility(View.VISIBLE);
                    fail_data_text.setVisibility(View.VISIBLE);
                    fail_data_text.setText("无法查询地名、前缀或后缀性的关键字");
                    progress.setVisibility(View.GONE);
                    delayHandler.removeMessages(start_);
                    showToast("查询条件不能少于2个字，或为地名等特殊词");
                    return;
                }
                searchkey = arg0.toString().trim();
                getCompanyList();
            }
        });

        query_company_key.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (query_company_key.getImeOptions() == EditorInfo.IME_ACTION_SEARCH) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return false;
                    }
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        return false;
                    }

                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                        if (isShowKeyboard(query_company_key)) {
                            KeyBoardUtil.hideSoftKeyboard(SetCompanyActivity.this);
                        }
                    }
                }
                return false;
            }
        });

        query_company_key.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            if (query_company_key.getText().length() < 2) {
                                break;
                            }
                            if (map.containsKey(query_company_key.getText().toString().trim())) {
                                emptylayout.setVisibility(View.VISIBLE);
                                fail_data_text.setVisibility(View.VISIBLE);
                                fail_data_text.setText("无法查询地名、前缀或后缀性的关键字");
                                progress.setVisibility(View.GONE);
                                delayHandler.removeMessages(start_);
                                showToast("查询条件不能少于2个字，或为地名等特殊词");
                                break;
                            }
                            searchkey = query_company_key.getText().toString().trim();
                            getCompanyList();
                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }
        });

    }

    /**
     * 获取公司列表
     */
    private void getCompanyList(){
        delayHandler.sendEmptyMessage(do_);
        emptylayout.setVisibility(View.VISIBLE);
        fail_data_text.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
    }

    private final static int start_ = 0;
    private final static int do_ = 1;

    private Handler delayHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case start_:
                    if(!TextUtils.isEmpty(searchkey)){
                        getCompany(true);
                    }

                    break;

                case do_:
                    delayHandler.removeMessages(start_);
                    delayHandler.sendEmptyMessageDelayed(start_, 2*1000);
                    break;
                default:
                    break;
            }

        }
    };

    private void getCompany(final boolean refresh){

        HashMap<String,String> map = new HashMap<>();
        map.put("searchkey",searchkey);
        map.put("pageSize","20");
        map.put("pageIndex",(refresh?1:(pageindex+1))+"");
        map.put("type","0");
        LoginRegisterHelper.doNetGETSearchCompany(mContext, map,getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                SearchCompanyModel model = (SearchCompanyModel) data;
                if(model.getResult() == 0){
                    if (refresh)
                        pageindex=1;
                    if(model.getCompanylist() != null){
                        if(model.getCount()==0){
                            emptylayout.setVisibility(View.VISIBLE);
                            fail_data_text.setText("未找到“"+searchkey+"”");
                            fail_data_text.setVisibility(View.VISIBLE);
                            progress.setVisibility(View.GONE);
                        }else {
                            if (refresh)
                                adapter.refresh(model.getCompanylist());
                            else
                                adapter.addList(model.getCompanylist());
                            emptylayout.setVisibility(View.GONE);
                            mListView.stopLoadMore();
                            if (adapter.getCount()>=model.getCount())
                                mListView.setPullLoadEnable(false);
                            else {
                                if (!refresh)
                                    pageindex++;
                                mListView.setPullLoadEnable(true);
                            }
                        }
                    }else {
                        emptylayout.setVisibility(View.VISIBLE);
                        fail_data_text.setText("未找到“"+searchkey+"”");
                        fail_data_text.setVisibility(View.VISIBLE);
                        progress.setVisibility(View.GONE);
                    }
                }else {
                    emptylayout.setVisibility(View.VISIBLE);
                    fail_data_text.setText(model.getMsg());
                    fail_data_text.setVisibility(View.VISIBLE);
                    progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFail(String error) {
                emptylayout.setVisibility(View.VISIBLE);
                fail_data_text.setText("网络异常");
                fail_data_text.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
            }
        });
    }


    private boolean isShowKeyboard(EditText editText) {
        return KeyBoardUtil.openSoftKeyboard(editText, this);
    }

    /**
     * 返回该次选中的公司id and name
     */
    private void backUpCompany(){
        Intent intent = new Intent();
        Log.d("TAG", "companyid==" + companyId + "companyName::" + companyName);
        intent.putExtra("companyid", companyId);
        intent.putExtra("companyname", companyName);
        setResult(RESULT_OK, intent);
        finish();
    }
}
