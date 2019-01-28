package com.jusfoun.jusfouninquire.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.BaseIndustryModel;
import com.jusfoun.jusfouninquire.net.model.IndustryModel;
import com.jusfoun.jusfouninquire.net.model.JobModel;
import com.jusfoun.jusfouninquire.net.route.LoginRegisterHelper;
import com.jusfoun.jusfouninquire.ui.adapter.ChooseAdapter;
import com.jusfoun.library.animationadapter.adapter.ScaleInAnimationAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Albert on 2015/11/19.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:高级搜索选择行业页面，与注册选择职位页面分离
 */
public class SetIndustryActivity extends BaseInquireActivity {

    /**组件*/
    private ListView mListView;

    /**对象*/
    private ChooseAdapter mAdapter;
    private ScaleInAnimationAdapter mAnimAdapter;

    private String mSelectedName,mSelectedID,mCurrentName;


    /**变量*/
    private String mType = "1";
    @Override
    protected void initData() {
        super.initData();
        mAdapter = new ChooseAdapter(this);
        mAnimAdapter = new ScaleInAnimationAdapter(mAdapter);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_setjob_or_industry);
        mListView = (ListView) findViewById(R.id.mListView);
        mAnimAdapter.setAbsListView(mListView);
        mListView.setAdapter(mAnimAdapter);
    }

    @Override
    protected void initWidgetActions() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChooseAdapter.ViewHolder holder = (ChooseAdapter.ViewHolder) view.getTag();
                if (holder == null){
                    return;
                }
                JobModel model = holder.getData();
                if (model == null){
                    return;
                }

                if ("0".equals(model.getHaschild())){
                    if ("不限".equals(model.getName())){
                        mSelectedName = mCurrentName;
                    }else {
                        mSelectedName = model.getName();
                    }
                    mSelectedID = model.getChildid();
                    backUpDate();
                }else {
                    mCurrentName = model.getName();
                    getJobOrIndustry(model.getChildid(), mType);
                }
            }
        });
        getJobOrIndustry("0", mType);
    }

    private void getJobOrIndustry(final String parentId,String type){
        HashMap<String,String> map = new HashMap<>();
        map.put("parentid", parentId);
        map.put("type", type);

        showLoading();
        LoginRegisterHelper.doNetGetIndustry(mContext, map,getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
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
                        List<JobModel> list = new ArrayList<JobModel>();
                        if (industryModel.getRlist().size() > 0) {
                            list.addAll(industryModel.getRlist());
                            if (!"0".equals(parentId)){
                                JobModel model = new JobModel();
                                model.setHaschild("0");
                                model.setChildid(industryModel.getRlist().get(0).getParentid());
                                model.setName("不限");
                                list.add(0,model);
                            }
                            mAnimAdapter.reset();
                            mAdapter.refresh(list);
                        }
                    }

                } else {
                    showToast(baseindustryModel.getMsg());
                }
            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                showToast("网络异常");
            }
        });
    }

    public void backUpDate(){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(AdvancedSearchActivity.INDUSTRY_NAME,mSelectedName);
        bundle.putString(AdvancedSearchActivity.INDUSTRY_ID,mSelectedID);
        intent.putExtra(AdvancedSearchActivity.BACK_DATA,bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

}
