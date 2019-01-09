package com.jusfoun.jusfouninquire.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.BaseIndustryModel;
import com.jusfoun.jusfouninquire.net.model.IndustryModel;
import com.jusfoun.jusfouninquire.net.model.JobModel;
import com.jusfoun.jusfouninquire.net.route.LoginRegisterHelper;
import com.jusfoun.jusfouninquire.ui.adapter.ChooseAdapter;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.jusfoun.library.animationadapter.adapter.ScaleInAnimationAdapter;

import java.util.HashMap;

/**
 * @author lee
 * @version create time:2015/11/1815:16
 * @Email email
 * @Description $设置职位 或 行业
 */

public class SetJobActivity extends BaseInquireActivity {
    /**常量*/
    public static final String TYPE = "TYPE_KEY";
    public static final String TYPE_INDUSTRY_VALUE = "1";
    public static final String TYPE_JOB_VALUE = "3";


    /**组件*/
    private ListView mListView;
    private TitleView titleView;
    private View netErrorLayout;

    /**对象*/
    private ChooseAdapter mAdapter;
    private ScaleInAnimationAdapter mAnimAdapter;


    /**变量*/
    private String type = "3";


    private String mSelectedName,mSelectedID;
    @Override
    protected void initData() {
        super.initData();

        mAdapter = new ChooseAdapter(this);
        mAnimAdapter = new ScaleInAnimationAdapter(mAdapter);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_setjob_or_industry);
        titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setTitle("设置职业");


        mListView = (ListView) findViewById(R.id.mListView);
        mAnimAdapter.setAbsListView(mListView);
        mListView.setAdapter(mAnimAdapter);

        netErrorLayout = findViewById(R.id.netErrorLayout);
    }

    @Override
    protected void initWidgetActions() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChooseAdapter.ViewHolder holder = (ChooseAdapter.ViewHolder) view.getTag();
                if (holder == null) {
                    return;
                }
                JobModel model = holder.getData();
                if (model == null) {
                    return;
                }
                if ("0".equals(model.getHaschild())) {
                    mSelectedName = model.getName();
                    mSelectedID = model.getChildid();
                    backUpDate();
                } else {
                    getJobOrIndustry(model.getChildid(), type);
                }

            }
        });

        netErrorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","重新加载");
                getJobOrIndustry("0", type);
            }
        });
        getJobOrIndustry("0", type);
    }

    private void getJobOrIndustry(final String parentId, String type){
        HashMap<String,String> map = new HashMap<>();
        map.put("parentid",parentId);
        map.put("type", type);
        showLoading();
        LoginRegisterHelper.doNetGetIndustry(mContext, map, getLocalClassName(),new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                netErrorLayout.setVisibility(View.GONE);
                hideLoadDialog();
                BaseIndustryModel baseindustryModel = (BaseIndustryModel) data;
                if (baseindustryModel.getResult() == 0) {
                    if (baseindustryModel.getDatalist() == null) {
                        return;
                    }
                    if (baseindustryModel.getDatalist().size() == 0) {
                        return;
                    }
                    IndustryModel industryModel = baseindustryModel.getDatalist().get(0);
                    if (industryModel.getRlist() != null) {
                        if (industryModel.getRlist().size() > 0) {
                            mAnimAdapter.reset();
                            mAdapter.refresh(industryModel.getRlist());
                        }
                    }
                } else {
                    showToast(baseindustryModel.getMsg());
                }
            }
            @Override
            public void onFail(String error) {
                hideLoadDialog();
                if("0".equals(parentId)){
                    netErrorLayout.setVisibility(View.VISIBLE);
                }else {
                    showToast(R.string.net_error);
                }


            }
        });
    }

    public void backUpDate(){
        Intent intent = new Intent();

        intent.putExtra("jobid", mSelectedID);
        intent.putExtra("jobname", mSelectedName);

        setResult(RESULT_OK, intent);
        finish();
    }



}
