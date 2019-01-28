package com.jusfoun.jusfouninquire.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.model.ChooseRegisterFundItemModel;
import com.jusfoun.jusfouninquire.ui.adapter.ChooseRegisterFundAdapter;
import com.jusfoun.jusfouninquire.ui.view.BackAndRightImageTitleView;
import com.jusfoun.library.animationadapter.adapter.ScaleInAnimationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Albert on 2015/11/18.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:高级搜索选择注册资金页面
 */
public class ChooseRegisterFundActivity extends BaseInquireActivity {
    private BackAndRightImageTitleView mTitleView;
    private ListView mFundListView;
    private ChooseRegisterFundAdapter mAdapter;
    private ScaleInAnimationAdapter mAnimAdapter;
    private List<ChooseRegisterFundItemModel> mDataList;

    private String mFundInterval;

    private Button mTestSubmit;

    private String[] tag,value;
    @Override
    protected void initData() {
        super.initData();
        if (getIntent() != null){
            Bundle bundle = getIntent().getBundleExtra(AdvancedSearchActivity.BACK_DATA);
            if (bundle != null){
                mFundInterval = bundle.getString(AdvancedSearchActivity.REGISTER_FUND_INTERVAL,"");
            }

        }
        mAdapter = new ChooseRegisterFundAdapter(this);
        mAnimAdapter = new ScaleInAnimationAdapter(mAdapter);
        mDataList = new ArrayList<>();
        tag = getResources().getStringArray(R.array.register_fund);
        value = getResources().getStringArray(R.array.register_fund_value);

        for (int i = 0; i < tag.length; i++){
            ChooseRegisterFundItemModel model = new ChooseRegisterFundItemModel();
            model.setChoosed(false);
            model.setValue(tag[i]);
            model.setInterval(value[i]);
            mDataList.add(model);
        }

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_choose_register_fund_layout);
        mTitleView = (BackAndRightImageTitleView)findViewById(R.id.choose_register_fund_title);
        mTitleView.setmImageTxt("确定");
        mFundListView = (ListView)findViewById(R.id.register_fund_list);
        mAnimAdapter.setAbsListView(mFundListView);
        mFundListView.setAdapter(mAnimAdapter);
        if (!TextUtils.isEmpty(mFundInterval)){
            String[] choosed = mFundInterval.split(";");
            for (String interval : choosed){
                for (ChooseRegisterFundItemModel model : mDataList){
                    if (interval.equals(model.getInterval())){
                        model.setChoosed(true);
                    }
                }
            }
        }else {
            mDataList.get(0).setChoosed(true);
        }
        mAdapter.refresh(mDataList);
    }

    @Override
    protected void initWidgetActions() {
        mFundListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dealDataList(position);

            }
        });

        mTitleView.setmRightClickListener(new BackAndRightImageTitleView.RightClickListener() {
            @Override
            public void onClick() {
                String backvalue = "", backinterval = "";
                for (ChooseRegisterFundItemModel model : mDataList) {
                    if (model.isChoosed()) {
                        if (TextUtils.isEmpty(backvalue)) {
                            backvalue += model.getValue();
                        } else {
                            backvalue = backvalue + ";" + model.getValue();
                        }

                        if (TextUtils.isEmpty(backinterval)) {
                            backinterval += model.getInterval();
                        } else {
                            backinterval = backinterval + ";" + model.getInterval();
                        }
                    }
                }
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(AdvancedSearchActivity.REGISTER_FUND, backvalue);
                bundle.putString(AdvancedSearchActivity.REGISTER_FUND_INTERVAL, backinterval);
                intent.putExtra(AdvancedSearchActivity.BACK_DATA, bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });



    }

    private void dealDataList(int position){
        if ((position == 0) && (!mDataList.get(position).isChoosed())){
            mDataList.get(position).setChoosed(true);
            for (int i = 1; i < mDataList.size(); i ++){
                mDataList.get(i).setChoosed(false);
                mAdapter.refresh(mDataList);
            }

        }else {
            mDataList.get(0).setChoosed(false);
            mDataList.get(position).setChoosed(!mDataList.get(position).isChoosed());
            mAdapter.refresh(mDataList);
        }
    }
}
