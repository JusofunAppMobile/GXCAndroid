package com.jusfoun.jusfouninquire.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.activity.AdvancedSearchActivity;
import com.jusfoun.jusfouninquire.ui.activity.ManuallySearchActivity;
import com.jusfoun.jusfouninquire.ui.activity.WebActivity;
import com.jusfoun.jusfouninquire.ui.activity.WebSearchCompanyActivity;

import netlib.util.AppUtil;
import netlib.util.EventUtils;

/**
 * Created by Albert on 2015/12/1.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description : 搜索结果列表尾部追加的View
 */
public class ResultAppendView extends RelativeLayout{

    //仅显示提交人工查询
    public final static int MANUAL = 1;

    //提交人工查询，引导高级搜索
    public final static int MANUAL_ADVANCED = 2;

    //优化搜索词
    public final static int OPTMISE = 3;

    //优化搜索词，引导高级搜索;
    public final static int OPTMISE_ADVANCED = 4;

    //优化搜索词，提交人工查询
    public final static int OPTMISE_MANUAL = 5;

    private LinearLayout mFirstLayout,mSecondLayout,mThirdLayout,mForthLayout,mOneLayout,mTwoLayout;
    private ImageView mFirstImage,mSecondImage,mThirdImage,mForthImage;
    private TextView mFirstText,mSecondText,searchBtn,mThirdText,mForthText;

    private int mViewType;

    private Context mContext;
    public ResultAppendView(Context context) {
        super(context);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    public ResultAppendView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    public ResultAppendView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ResultAppendView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    private void initData(){

    }

    private void initView(){
        LayoutInflater.from(mContext).inflate(R.layout.result_append_view_layout, this, true);
        mFirstLayout = (LinearLayout) findViewById(R.id.first_view);
        mSecondLayout = (LinearLayout) findViewById(R.id.second_view);

        mThirdLayout = (LinearLayout) findViewById(R.id.third_view);
        mForthLayout = (LinearLayout) findViewById(R.id.forth_view);

        mOneLayout = (LinearLayout) findViewById(R.id.one_layout);
        mTwoLayout = (LinearLayout) findViewById(R.id.two_layout);

        mFirstImage = (ImageView) findViewById(R.id.first_image);
        mSecondImage = (ImageView) findViewById(R.id.second_image);
        mThirdImage = (ImageView) findViewById(R.id.third_image);
        mForthImage = (ImageView) findViewById(R.id.forth_image);

        mFirstText = (TextView) findViewById(R.id.first_text);
        mSecondText = (TextView) findViewById(R.id.second_text);
        mThirdText = (TextView) findViewById(R.id.third_text);
        mForthText = (TextView) findViewById(R.id.forth_text);

    }

    private void initWidgetAction(){

    }

    public int getmViewType() {
        return mViewType;
    }

    /**
     *
     * @param mViewType 1 2 3 4 5 中3，4传值为空字符串
     * @param province
     * @param companyName
     */
    public void setmViewType(int mViewType,final String province, final String companyName) {
        this.mViewType = mViewType;
        switch (mViewType){
            case MANUAL:
                mFirstImage.setImageResource(R.mipmap.append_manual);
                mFirstText.setText(R.string.search_append_manual);
                mFirstLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventUtils.event(mContext,EventUtils.SEARCH05);
                        Intent intent = new Intent(mContext, ManuallySearchActivity.class);
                        mContext.startActivity(intent);
                    }
                });

                /*********** twolayout 隐藏，secondLayout显示并加“全网搜索”****************************/
                mTwoLayout.setVisibility(View.GONE);
                mSecondLayout.setVisibility(VISIBLE);
                mSecondImage.setImageResource(R.mipmap.icon_all_search);
                mSecondText.setText(R.string.all_internet_search);
                mSecondLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventUtils.event(mContext,EventUtils.SEARCH16);
                        Intent intent = new Intent(mContext, WebSearchCompanyActivity.class);
                        intent.putExtra(WebSearchCompanyActivity.COMPANY_NAME, companyName);
                        intent.putExtra(WebSearchCompanyActivity.COMPANY_AREA, province);
                        mContext.startActivity(intent);
                    }
                });
                /***************************************/

                break;
            case MANUAL_ADVANCED:
                mFirstImage.setImageResource(R.mipmap.append_manual);
                mFirstText.setText(R.string.search_append_manual);
                mFirstLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ManuallySearchActivity.class);
                        mContext.startActivity(intent);
                    }
                });
                mSecondImage.setImageResource(R.mipmap.append_advanced);
                mSecondText.setText(R.string.search_append_advanced);
                mSecondLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, AdvancedSearchActivity.class);
                        mContext.startActivity(intent);
                    }
                });
                /*********** twolayout 显示，thirdLayout显示并加“全网搜索”****************************/
                mTwoLayout.setVisibility(View.VISIBLE);
                mThirdText.setText(R.string.all_internet_search);
                mThirdImage.setImageResource(R.mipmap.icon_all_search);
                mThirdLayout.setVisibility(View.VISIBLE);
                mThirdLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventUtils.event(mContext,EventUtils.SEARCH16);
                        Intent intent = new Intent(mContext, WebSearchCompanyActivity.class);
                        intent.putExtra(WebSearchCompanyActivity.COMPANY_NAME, companyName);
                        intent.putExtra(WebSearchCompanyActivity.COMPANY_AREA, province);
                        mContext.startActivity(intent);
                    }
                });
                /***************************************/
                break;
            case OPTMISE:
                mFirstImage.setImageResource(R.mipmap.append_optmise);
                mFirstText.setText(R.string.search_append_optmise);
                mFirstLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventUtils.event(mContext,EventUtils.SEARCH06);
                        Intent intent = new Intent(mContext, WebActivity.class);
                        intent.putExtra(WebActivity.URL, mContext.getString(R.string.req_url)
                                + mContext.getString(R.string.optmise_searchkey_url)
                                +"?appType=0&version="+ AppUtil.getVersionName(mContext));
                        mContext.startActivity(intent);
                    }
                });
                mFirstLayout.setGravity(Gravity.CENTER);
                mSecondLayout.setVisibility(GONE);
                /*********** twolayout 隐藏，secondLayout显示并加“全网搜索”****************************/
                mTwoLayout.setVisibility(View.GONE);
                /***************************************/
                break;
            case OPTMISE_ADVANCED:
                mFirstImage.setImageResource(R.mipmap.append_optmise);
                mFirstText.setText(R.string.search_append_optmise);
                mFirstLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventUtils.event(mContext,EventUtils.SEARCH06);
                        Intent intent = new Intent(mContext, WebActivity.class);
                        intent.putExtra(WebActivity.URL, mContext.getString(R.string.req_url)
                                + mContext.getString(R.string.optmise_searchkey_url)
                                +"?appType=0&version="+ AppUtil.getVersionName(mContext));
                        mContext.startActivity(intent);
                    }
                });
                mSecondImage.setImageResource(R.mipmap.append_advanced);
                mSecondText.setText(R.string.search_append_advanced);
                mSecondLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventUtils.event(mContext,EventUtils.SEARCH07);
                        Intent intent = new Intent(mContext, AdvancedSearchActivity.class);
                        mContext.startActivity(intent);
                    }
                });

                mTwoLayout.setVisibility(View.GONE);
                break;
            case OPTMISE_MANUAL:
                mFirstImage.setImageResource(R.mipmap.append_optmise);
                mFirstText.setText(R.string.search_append_optmise);
                mFirstLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, WebActivity.class);
                        intent.putExtra(WebActivity.URL, mContext.getString(R.string.req_url)
                                + mContext.getString(R.string.optmise_searchkey_url)
                                +"?appType=0&version="+ AppUtil.getVersionName(mContext));
                        mContext.startActivity(intent);
                    }
                });
                mSecondImage.setImageResource(R.mipmap.append_manual);
                mSecondText.setText(R.string.search_append_manual);
                mSecondLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ManuallySearchActivity.class);
                        mContext.startActivity(intent);
                    }
                });

                /*********** twolayout 隐藏，secondLayout显示并加“全网搜索”****************************/
                mTwoLayout.setVisibility(View.VISIBLE);
                mThirdText.setText(R.string.all_internet_search);
                mThirdImage.setImageResource(R.mipmap.icon_all_search);
                mThirdLayout.setVisibility(View.VISIBLE);
                mThirdLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventUtils.event(mContext,EventUtils.SEARCH16);
                        Intent intent = new Intent(mContext, WebSearchCompanyActivity.class);
                        intent.putExtra(WebSearchCompanyActivity.COMPANY_NAME, companyName);
                        intent.putExtra(WebSearchCompanyActivity.COMPANY_AREA, province);
                        mContext.startActivity(intent);
                    }
                });

                /***************************************/

                break;
            default:
                break;
        }
    }

    public void setSearchBtnListener(OnClickListener listener){
        if (listener!=null)
            searchBtn.setOnClickListener(listener);
    }
}
