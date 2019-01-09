package com.jusfoun.jusfouninquire.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.AdvancedConfigModel;
import com.jusfoun.jusfouninquire.net.route.SearchRequestRouter;
import com.jusfoun.jusfouninquire.ui.view.BackAndRightImageTitleView;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.jusfoun.jusfouninquire.ui.view.TextChooseView;

/**
 * Created by Albert on 2015/11/17.
 * Mail : lbh@jusfoun.com
 * TODO :高级搜索页面
 * Description:高级搜索页面
 */
public class AdvancedSearchActivity extends BaseInquireActivity implements View.OnClickListener{
    private BackAndRightImageTitleView mTitleView;
    private EditText mCompanyName,mCompanyProducts,mLegal,mStreet,mBuilding;
    private TextChooseView mIndustry,mArea,mRegisterFund,mRegisterTime;
    private ScrollView mScrollView;
    private Button mSubmit;

    private LinearLayout mNameLayout,mProductLayout,mLegalLayout,mIndustryLayout,mAreaLayout,mStreetLayout,mBuildingLayout,mFundLayout,mTimeLayout;

    private NetWorkErrorView mNetWorkError;

    private final int REQUEST_INDUSTRY = 1;
    private final int REQUEST_AREA = 2;
    private final int REQUEST_REGISTER_FUND = 3;
    private final int REQUEST_REGISTER_TIME = 4;

    public final static String INDUSTRY_ID = "industry_id";
    public final static String INDUSTRY_NAME = "industry_name";
    public final static String PROVINCE_ID = "province_id";
    public final static String PROVINCE_NAME = "province_name";
    public final static String CITY_ID = "city_id";
    public final static String AREA_ID = "area_id";
    public final static String AREA_NAME = "area_name";
    public final static String REGISTER_FUND = "register_fund";
    public final static String REGISTER_FUND_INTERVAL = "register_fund_interval";
    public final static String REGISTER_TIME = "register_time";
    public final static String REGISTER_TIME_INTERVAL = "register_time_interval";

    public final static String BACK_DATA = "back_data";

    private String mProvinceID,mCityID,mRegisterFundValue,mRegisterTimeValue,mIndustryName,mProvince;

    private String mAreaID,mRegisterFundInterval,mRegisterTimeInterval,mIndustryID;

    private String[] mConfigs;
    private boolean[] mVisiable;

    @Override
    protected void initData() {
        super.initData();
        mConfigs = getResources().getStringArray(R.array.advanced_search_config);
        mVisiable = new boolean[mConfigs.length];
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_advanced_search_layout);
        mScrollView = (ScrollView)findViewById(R.id.advanced_scrollview);
        mTitleView = (BackAndRightImageTitleView)findViewById(R.id.advanced_search_titleview);
        mCompanyName = (EditText)findViewById(R.id.search_company_name);
        mCompanyProducts = (EditText)findViewById(R.id.search_company_products);
        mLegal = (EditText)findViewById(R.id.search_legal_shareholder);
        mStreet = (EditText)findViewById(R.id.company_street);
        mBuilding = (EditText)findViewById(R.id.company_building);

        mIndustry = (TextChooseView)findViewById(R.id.industry_type);
        mArea = (TextChooseView)findViewById(R.id.area);
        mRegisterFund = (TextChooseView)findViewById(R.id.registered_fund);
        mRegisterTime = (TextChooseView)findViewById(R.id.register_time);

        mNameLayout = (LinearLayout) findViewById(R.id.name_layout);
        mProductLayout = (LinearLayout) findViewById(R.id.product_layout);
        mLegalLayout = (LinearLayout) findViewById(R.id.legal_layout);
        mIndustryLayout = (LinearLayout) findViewById(R.id.industry_layout);
        mAreaLayout = (LinearLayout) findViewById(R.id.area_layout);
        mStreetLayout = (LinearLayout) findViewById(R.id.street_layout);
        mBuildingLayout = (LinearLayout) findViewById(R.id.building_layout);
        mFundLayout = (LinearLayout) findViewById(R.id.fund_layout);


        mSubmit = (Button)findViewById(R.id.advanced_search_submit);

        mNetWorkError = (NetWorkErrorView) findViewById(R.id.net_work_error);
    }

    @Override
    protected void initWidgetActions() {


        mIndustry.setOnClickListener(this);
        mArea.setOnClickListener(this);
        mRegisterFund.setOnClickListener(this);
        mRegisterTime.setOnClickListener(this);
        mSubmit.setOnClickListener(this);
        mNetWorkError.setListener(new NetWorkErrorView.OnRefreshListener() {
            @Override
            public void OnNetRefresh() {
                getConfig();
            }
        });

        getConfig();
    }

    private void getConfig(){
        showLoading();
        SearchRequestRouter.getAdvancedConfig(mContext, null,getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                AdvancedConfigModel model = (AdvancedConfigModel) data;
                if (model.getResult() == 0) {
                    mScrollView.setVisibility(View.VISIBLE);
                    mNetWorkError.setVisibility(View.GONE);
                    if (model.getSearchlist() != null) {
                        if (model.getSearchlist().size() > 0) {
                            for (int i = 0; i < model.getSearchlist().size(); i++) {
                                for (int j = 0; j < mConfigs.length; j++) {
                                    if (mConfigs[j].equals(model.getSearchlist().get(i).getSearchName())) {
                                        mVisiable[i] = model.getSearchlist().get(i).isShow();
                                    }
                                }
                            }
                            refreshWidget();
                        }
                    }
                }else {
                    mNetWorkError.setServerError();
                    mNetWorkError.setVisibility(View.VISIBLE);
                    mScrollView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                mNetWorkError.setNetWorkError();
                mNetWorkError.setVisibility(View.VISIBLE);
                mScrollView.setVisibility(View.GONE);
            }
        });
    }

    private void refreshWidget(){
        mNameLayout.setVisibility(mVisiable[0] ? View.VISIBLE : View.GONE);
        mProductLayout.setVisibility(mVisiable[1] ? View.VISIBLE : View.GONE);
        mLegalLayout.setVisibility(mVisiable[2] ? View.VISIBLE : View.GONE);
        mIndustryLayout.setVisibility(mVisiable[3] ? View.VISIBLE : View.GONE);
        mStreetLayout.setVisibility(mVisiable[4] ? View.VISIBLE : View.GONE);
        mBuildingLayout.setVisibility(mVisiable[5] ? View.VISIBLE : View.GONE);

        mAreaLayout.setVisibility(mVisiable[6] ? View.VISIBLE : View.GONE);
        mFundLayout.setVisibility(mVisiable[7] ? View.VISIBLE : View.GONE);
        mRegisterTime.setVisibility(mVisiable[8] ? View.VISIBLE : View.GONE);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.industry_type:
                Intent intent_industry = new Intent(this,SetIndustryActivity.class);
                intent_industry.putExtra(SetJobActivity.TYPE, SetJobActivity.TYPE_INDUSTRY_VALUE);
                startActivityForResult(intent_industry, REQUEST_INDUSTRY);
                break;
            case R.id.area:
                Intent intent = new Intent(this,ChooseAreaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(PROVINCE_ID,mProvinceID);
                bundle.putString(CITY_ID,mCityID);
                bundle.putString(AREA_ID,mAreaID);
                intent.putExtra(BACK_DATA,bundle);
                startActivityForResult(intent, REQUEST_AREA);
                break;
            case R.id.registered_fund:
                Intent intent1 = new Intent(this,ChooseRegisterFundActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString(REGISTER_FUND_INTERVAL,mRegisterFundInterval);
                intent1.putExtra(BACK_DATA,bundle1);
                startActivityForResult(intent1, REQUEST_REGISTER_FUND);
                break;
            case R.id.register_time:
                Intent intent2 = new Intent(this,ChooseRegisterTimeActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString(REGISTER_TIME_INTERVAL,mRegisterTimeInterval);
                intent2.putExtra(BACK_DATA,bundle2);
                startActivityForResult(intent2, REQUEST_REGISTER_TIME);
                break;
            case R.id.advanced_search_submit:
                Intent intent3 = new Intent(this,DoAdvancedSearchActivity.class);
                Bundle bundle3 = new Bundle();
                bundle3.putString(DoAdvancedSearchActivity.NAME,mCompanyName.getText().toString());
                bundle3.putString(DoAdvancedSearchActivity.TSG,mCompanyProducts.getText().toString());
                bundle3.putString(DoAdvancedSearchActivity.LEGALNAME,mLegal.getText().toString());
                bundle3.putString(DoAdvancedSearchActivity.INDUSTRYID,mIndustryID);
                bundle3.putString(DoAdvancedSearchActivity.AREA,mAreaID);
                bundle3.putString(DoAdvancedSearchActivity.STREETADDRESS,mStreet.getText().toString());
                bundle3.putString(DoAdvancedSearchActivity.OFFICEBUILDING, mBuilding.getText().toString());
                bundle3.putString(DoAdvancedSearchActivity.REGISTEREDDCAPITAL, mRegisterFundInterval);
                bundle3.putString(DoAdvancedSearchActivity.REGTIME, mRegisterTimeInterval);
                bundle3.putString(DoAdvancedSearchActivity.PROVINCE,mProvince);
                intent3.putExtra(DoAdvancedSearchActivity.CONDITION, bundle3);
                if (TextUtils.isEmpty(mCompanyName.getText().toString().trim()) &&
                        TextUtils.isEmpty(mCompanyProducts.getText().toString()) &&
                        TextUtils.isEmpty(mLegal.getText().toString().trim()) &&
                        TextUtils.isEmpty(mIndustryID) && (TextUtils.isEmpty(mAreaID) || "0".equals(mAreaID)) &&
                        TextUtils.isEmpty(mStreet.getText().toString()) &&
                        TextUtils.isEmpty(mBuilding.getText().toString()) &&
                        TextUtils.isEmpty(mRegisterTimeInterval) && TextUtils.isEmpty(mRegisterFundInterval)
                        ){
                    showToast("请至少输入一项搜索条件");
                }else {
                    startActivity(intent3);
                }

                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (data != null){
                Bundle bundle = data.getBundleExtra(BACK_DATA);
                if (bundle != null){
                    switch (requestCode){
                        case REQUEST_AREA:
                            mProvinceID = bundle.getString(PROVINCE_ID,"");
                            mCityID = bundle.getString(CITY_ID,"");
                            mAreaID = bundle.getString(AREA_ID,"");
                            mProvince = bundle.getString(PROVINCE_NAME,"");
                            mArea.setTextViewValue(bundle.getString(AREA_NAME, ""));
                            break;
                        case REQUEST_INDUSTRY:
                            mIndustryName = bundle.getString(INDUSTRY_NAME,"");
                            mIndustryID = bundle.getString(INDUSTRY_ID,"");
                            mIndustry.setTextViewValue(mIndustryName);
                            break;
                        case REQUEST_REGISTER_FUND:
                            mRegisterFundValue = bundle.getString(REGISTER_FUND,"");
                            mRegisterFundInterval = bundle.getString(REGISTER_FUND_INTERVAL,"");
                            mRegisterFund.setTextViewValue(mRegisterFundValue);
                            break;
                        case REQUEST_REGISTER_TIME:
                            mRegisterTimeValue = bundle.getString(REGISTER_TIME,"");
                            mRegisterTimeInterval = bundle.getString(REGISTER_TIME_INTERVAL,"");
                            mRegisterTime.setTextViewValue(mRegisterTimeValue);
                            break;
                        default:
                            break;
                    }
                }
            }

        }
    }
}
