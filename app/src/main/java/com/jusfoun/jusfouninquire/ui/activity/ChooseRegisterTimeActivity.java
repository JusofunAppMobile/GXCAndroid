package com.jusfoun.jusfouninquire.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.model.ChooseRegisterTimeItemModel;
import com.jusfoun.jusfouninquire.ui.adapter.ChooseRegisterTimeAdapter;
import com.jusfoun.jusfouninquire.ui.view.BackAndRightImageTitleView;
import com.jusfoun.library.animationadapter.adapter.ScaleInAnimationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Albert on 2015/11/18.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description: 高级搜索选择注册时间页面
 */
public class ChooseRegisterTimeActivity extends BaseInquireActivity {
    private BackAndRightImageTitleView mTitleView;
    private ListView mTimeList;

    private ChooseRegisterTimeAdapter mAdapter;
    private ScaleInAnimationAdapter mAnimAdapter;

    private List<ChooseRegisterTimeItemModel> mDataList;
    private String mTimeInterval;

    private Button mTest;

    private String[] tag,value;
    @Override
    protected void initData() {
        super.initData();
        if (getIntent() != null){
            Bundle bundle = getIntent().getBundleExtra(AdvancedSearchActivity.BACK_DATA);
            if (bundle != null){
                mTimeInterval = bundle.getString(AdvancedSearchActivity.REGISTER_TIME_INTERVAL,"");
            }
        }
        tag = getResources().getStringArray(R.array.createtime_tag);
        value = getResources().getStringArray(R.array.createtime_value);
        mAdapter = new ChooseRegisterTimeAdapter(this);
        mAnimAdapter = new ScaleInAnimationAdapter(mAdapter);
        mDataList = new ArrayList<>();
        for (int i = 0; i < tag.length; i++){
            ChooseRegisterTimeItemModel model = new ChooseRegisterTimeItemModel();
            model.setValue(tag[i]);
            model.setInterval(value[i]);
            if (!TextUtils.isEmpty(mTimeInterval)){
                if (mTimeInterval.equals(value[i])){
                    model.setChoosed(true);
                }
            }

            mDataList.add(model);
        }

        if (TextUtils.isEmpty(mTimeInterval)){
            mDataList.get(0).setChoosed(true);
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_choose_register_time);
        mTitleView = (BackAndRightImageTitleView)findViewById(R.id.choose_register_time_title);
        mTitleView.setmImageTxt("确定");
        mTimeList = (ListView)findViewById(R.id.register_time_listview);
        mAnimAdapter.setAbsListView(mTimeList);
        mTimeList.setAdapter(mAnimAdapter);
        mAdapter.refresh(mDataList);
    }

    @Override
    protected void initWidgetActions() {
        mTimeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dealDataList(position);
            }
        });


        mTitleView.setmRightClickListener(new BackAndRightImageTitleView.RightClickListener() {
            @Override
            public void onClick() {
                String timeinterval = "", timevalue = "";
                for (ChooseRegisterTimeItemModel model : mDataList) {
                    if (model.isChoosed()) {
                        timevalue = model.getValue();
                        timeinterval = model.getInterval();
                    }
                }
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(AdvancedSearchActivity.REGISTER_TIME, timevalue);
                bundle.putString(AdvancedSearchActivity.REGISTER_TIME_INTERVAL, timeinterval);
                intent.putExtra(AdvancedSearchActivity.BACK_DATA, bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void dealDataList(int position){
        for (int i = 0; i < mDataList.size(); i++){
            mDataList.get(i).setChoosed(i == position);
            mAdapter.refresh(mDataList);
        }
    }
}
