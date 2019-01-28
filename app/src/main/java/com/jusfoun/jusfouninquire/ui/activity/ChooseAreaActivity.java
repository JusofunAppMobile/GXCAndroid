package com.jusfoun.jusfouninquire.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.model.AreaModel;
import com.jusfoun.jusfouninquire.ui.adapter.ChooseAreaAdapter;
import com.jusfoun.jusfouninquire.ui.util.LibIOUtil;
import com.jusfoun.library.animationadapter.adapter.ScaleInAnimationAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Albert on 2015/11/17.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:高级搜索，选择区域页面
 */
public class ChooseAreaActivity extends BaseInquireActivity {

    private String mProvinceID,mCityID,mAreaID,mProvinceName,mCityName,mAreaName;
    private ListView mProvinceList,mCityList,mAreaList;
    private ChooseAreaAdapter mProvinceAdapter,mCityAdapter,mAreaAdapter;
    private ScaleInAnimationAdapter mProvinceAnimAdapter,mCityAnimAdapter,mAreaAnimAdapter;
    private List<AreaModel> mSearchScopeList;

    private LinearLayout mCityLayout,mAreaLayout;

    private AreaModel mProvinceModel,mCityModel;

    @Override
    protected void initData() {
        super.initData();
        if (getIntent() != null){
            Bundle bundle = getIntent().getBundleExtra(AdvancedSearchActivity.BACK_DATA);
            if (bundle != null){
                mProvinceID = bundle.getString(AdvancedSearchActivity.PROVINCE_ID,"");
                mCityID = bundle.getString(AdvancedSearchActivity.CITY_ID,"");
                mAreaID = bundle.getString(AdvancedSearchActivity.AREA_ID,"");
            }
        }


        mProvinceModel = new AreaModel();
        mCityModel = new AreaModel();

        mProvinceAdapter = new ChooseAreaAdapter(this);
        mProvinceAnimAdapter = new ScaleInAnimationAdapter(mProvinceAdapter);

        mCityAdapter = new ChooseAreaAdapter(this);
        mCityAnimAdapter = new ScaleInAnimationAdapter(mCityAdapter);

        mAreaAdapter = new ChooseAreaAdapter(this);
        mAreaAnimAdapter = new ScaleInAnimationAdapter(mAreaAdapter);

        mSearchScopeList = new ArrayList<>();
        try {
            String value = LibIOUtil.convertStreamToJson(mContext.getResources().openRawResource(R.raw.provice_city_area));
            Type listType = new TypeToken<ArrayList<AreaModel>>() {
            }.getType();
            List<AreaModel> list = new Gson().fromJson(value, listType);
            for (AreaModel model : list){
                mSearchScopeList.add(model);
            }

            AreaModel areaModel = new AreaModel();
            areaModel.setId("0");
            areaModel.setName("不限");
            areaModel.setChildren(null);
            mSearchScopeList.add(0,areaModel);

        } catch (Exception exception) {

        } finally {

        }

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_choosearea_layout);

        mCityLayout = (LinearLayout)findViewById(R.id.cityLayout);
        mAreaLayout = (LinearLayout)findViewById(R.id.areaLayout);

        mProvinceList = (ListView)findViewById(R.id.provinceListView);
        mProvinceAnimAdapter.setAbsListView(mProvinceList);
        mProvinceList.setAdapter(mProvinceAnimAdapter);
        mProvinceAdapter.refresh(mSearchScopeList);

        mCityList = (ListView)findViewById(R.id.cityListView);
        mCityAnimAdapter.setAbsListView(mCityList);
        mCityList.setAdapter(mCityAnimAdapter);

        mAreaList = (ListView)findViewById(R.id.areaListView);
        mAreaAnimAdapter.setAbsListView(mAreaList);
        mAreaList.setAdapter(mAreaAnimAdapter);

        if (!TextUtils.isEmpty(mProvinceID)){
            mProvinceAdapter.setSelectedID(mProvinceID,1);
            for (AreaModel model : mSearchScopeList){
                if (model.getId().equals(mProvinceID)){
                    model.setChoosed(true);
                    mProvinceModel = model;
                    mProvinceName = model.getName();
                    break;
                }
            }
        }

        if (!TextUtils.isEmpty(mCityID)){
            mCityAdapter.setSelectedID(mCityID,2);
            if (mProvinceModel.getChildren() == null){

            }else {
                List<AreaModel> list = new ArrayList<>();
                list.addAll(mProvinceModel.getChildren());
                AreaModel model = new AreaModel();
                model.setId(mProvinceID);
                model.setName("不限");
                model.setChildren(null);
                list.add(0, model);
                mCityLayout.setVisibility(View.VISIBLE);
                mCityAdapter.refresh(list);
                for (AreaModel areaModel : mProvinceModel.getChildren()){
                    if (areaModel.getId().equals(mCityID)){
                        mCityModel = areaModel;
                        mCityName = areaModel.getName();
                        break;
                    }
                }
            }

        }

        if (!TextUtils.isEmpty(mAreaID)){
            if (mCityModel.getChildren() == null){

            }else {
                List<AreaModel> list = new ArrayList<>();
                list.addAll(mCityModel.getChildren());
                AreaModel areaModel = new AreaModel();
                areaModel.setId(mCityID);
                areaModel.setName("不限");
                areaModel.setChildren(null);
                list.add(0, areaModel);
                mAreaLayout.setVisibility(View.VISIBLE);
                mAreaAdapter.refresh(list);

            }

        }
    }

    @Override
    protected void initWidgetActions() {
        mProvinceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChooseAreaAdapter.ViewHolder holder = (ChooseAreaAdapter.ViewHolder) view.getTag();
                if (holder == null){
                    return;
                }
                if (holder.getData() != null){
                    mProvinceID = holder.getData().getId();
                    mProvinceName = holder.getData().getName();
                    mProvinceAdapter.setSelectedID(mProvinceID,1);
                    if (holder.getData().getChildren() != null){
                        mCityLayout.setVisibility(View.VISIBLE);
                        mAreaLayout.setVisibility(View.GONE);
                        mCityAnimAdapter.reset();
                        List<AreaModel> list = new ArrayList<AreaModel>();
                        list.addAll(holder.getData().getChildren());
                        AreaModel model = new AreaModel();
                        model.setId(mProvinceID);
                        model.setName("不限");
                        model.setChildren(null);
                        list.add(0,model);
                        mCityAdapter.refresh(list);
                    }else {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString(AdvancedSearchActivity.AREA_ID,mProvinceID);
                        bundle.putString(AdvancedSearchActivity.CITY_ID,mProvinceID);
                        bundle.putString(AdvancedSearchActivity.AREA_NAME,mProvinceName);
                        bundle.putString(AdvancedSearchActivity.PROVINCE_NAME,"全国");
                        intent.putExtra(AdvancedSearchActivity.BACK_DATA,bundle);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }
        });

        mCityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChooseAreaAdapter.ViewHolder holder = (ChooseAreaAdapter.ViewHolder) view.getTag();
                if (holder == null){
                    return;
                }
                if (holder.getData() != null){
                    mCityID = holder.getData().getId();
                    mCityName = holder.getData().getName();
                    mCityAdapter.setSelectedID(mCityID,2);
                    if (holder.getData().getChildren() != null){
                        mAreaLayout.setVisibility(View.VISIBLE);
                        mAreaAnimAdapter.reset();
                        List<AreaModel> list = new ArrayList<AreaModel>();
                        list.addAll(holder.getData().getChildren());
                        AreaModel model = new AreaModel();
                        model.setId(mCityID);
                        model.setName("不限");
                        model.setChildren(null);
                        list.add(0,model);
                        mAreaAdapter.refresh(list);
                    }else {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString(AdvancedSearchActivity.PROVINCE_ID,mProvinceID);
                        bundle.putString(AdvancedSearchActivity.CITY_ID,mProvinceID);
                        bundle.putString(AdvancedSearchActivity.AREA_ID,mCityID);
                        bundle.putString(AdvancedSearchActivity.AREA_NAME,mProvinceName);
                        bundle.putString(AdvancedSearchActivity.PROVINCE_NAME,mProvinceName);
                        intent.putExtra(AdvancedSearchActivity.BACK_DATA,bundle);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }

            }
        });

        mAreaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChooseAreaAdapter.ViewHolder holder = (ChooseAreaAdapter.ViewHolder) view.getTag();
                if (holder == null){
                    return;
                }
                AreaModel model = holder.getData();
                if (model != null){
                    mAreaID = model.getId();
                }
                if (position == 0){
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString(AdvancedSearchActivity.PROVINCE_ID,mProvinceID);
                    bundle.putString(AdvancedSearchActivity.CITY_ID,mCityID);
                    bundle.putString(AdvancedSearchActivity.AREA_ID,mCityID);
                    bundle.putString(AdvancedSearchActivity.AREA_NAME,mCityName);
                    bundle.putString(AdvancedSearchActivity.PROVINCE_NAME,mProvinceName);
                    intent.putExtra(AdvancedSearchActivity.BACK_DATA,bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }else {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString(AdvancedSearchActivity.PROVINCE_ID,mProvinceID);
                    bundle.putString(AdvancedSearchActivity.CITY_ID,mCityID);
                    bundle.putString(AdvancedSearchActivity.AREA_ID,mAreaID);
                    bundle.putString(AdvancedSearchActivity.AREA_NAME,model.getName());
                    bundle.putString(AdvancedSearchActivity.PROVINCE_NAME,mProvinceName);
                    intent.putExtra(AdvancedSearchActivity.BACK_DATA,bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
    }
}
