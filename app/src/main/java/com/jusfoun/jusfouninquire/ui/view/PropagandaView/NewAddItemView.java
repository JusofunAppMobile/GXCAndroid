package com.jusfoun.jusfouninquire.ui.view.PropagandaView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.model.HomeDataItemModel;
import com.jusfoun.jusfouninquire.ui.activity.CompanyDetailActivity;

/**
 * PropagandaItemView
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/2
 * @Description :首页运营 单个轮播显示，抽象成view，准备之后进行扩展
 */
public class NewAddItemView extends RelativeLayout {
    protected View rootView;
    protected LinearLayout layoutInfo;
    protected RelativeLayout layoutRoot;
    private Context mContext;

    private TextView mCompanyName, addressText, monetText, mCompanyState;

    private TextView legalText, dateText;

    private HomeDataItemModel homeDataItemModel;

    public NewAddItemView(Context context) {
        super(context);
        initData(context);
        initView();
        initWidgetAction();
    }

    public NewAddItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
        initView();
        initWidgetAction();
    }

    public NewAddItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
        initView();
        initWidgetAction();
    }


    private void initData(Context context) {
        this.mContext = context;
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.item_new_add_list, this, true);

        mCompanyName = (TextView) findViewById(R.id.company_name);
        addressText = (TextView) findViewById(R.id.company_address);
        monetText = (TextView) findViewById(R.id.company_money);
        mCompanyState = (TextView) findViewById(R.id.company_state);
        legalText = (TextView) findViewById(R.id.company_legal);
        dateText = (TextView) findViewById(R.id.company_date);
        layoutRoot = (RelativeLayout) findViewById(R.id.layout_root);

    }

    private void initWidgetAction() {

        layoutRoot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (homeDataItemModel != null) {
                    Bundle bundle = new Bundle();
                    if (!TextUtils.isEmpty(homeDataItemModel.getCompanyid())) {
                        bundle.putString(CompanyDetailActivity.COMPANY_ID, homeDataItemModel.getCompanyid());
                    } else {
                        bundle.putString(CompanyDetailActivity.COMPANY_ID, "");
                    }
                    if (!TextUtils.isEmpty(homeDataItemModel.getCompanyname())) {
                        bundle.putString(CompanyDetailActivity.COMPANY_NAME, homeDataItemModel.getCompanyname());
                    } else {
                        bundle.putString(CompanyDetailActivity.COMPANY_NAME, "");
                    }
                    Intent intent = new Intent(mContext, CompanyDetailActivity.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);

                }


            }
        });
    }


    /**
     * 设置宣传viewpager 的 item view的内容
     *
     * @param data
     */
    public void setData(HomeDataItemModel data) {
        homeDataItemModel = data;
        if (data != null) {
            if (!TextUtils.isEmpty(data.getCompanylightname())) {
                mCompanyName.setText(Html.fromHtml(data.getCompanylightname()));
            } else {
                mCompanyName.setText(data.getCompanyname());
            }
//            mRelateLayout.setVisibility(TextUtils.isEmpty(data.getRelated()) ? View.GONE : View.VISIBLE);
//
//            try {
//                mRelated.setText(Html.fromHtml(data.getRelated()));
//            } catch (Exception e) {
//
//            }

            //改版需求,搜索列表去除行业字段
            //mCompanyInfo.setText(data.getIndustry() + " | " + data.getLocation() + " | " + data.getFunds());
            //mCompanyInfo.setText(data.getLocation() + " | " + data.getFunds());
            if (!TextUtils.isEmpty(data.getLocation()) && !"未公布".equals(data.getLocation())) {
                addressText.setText(data.getLocation());
                addressText.setVisibility(VISIBLE);
            } else {
                addressText.setVisibility(GONE);
            }

            if (!TextUtils.isEmpty(data.getFunds()) && !"未公布".equals(data.getFunds())) {
                monetText.setText(data.getFunds());
                monetText.setVisibility(VISIBLE);
            } else {
                monetText.setVisibility(GONE);
            }

            //TODO 可能需要根据类型进行不同状态的显示
            mCompanyState.setText(data.getCompanystate());
//
//            if (TextUtils.isEmpty(data.getSocialcredit()) || "null".equals(data.getSocialcredit())) {
//                socialcreditText.setText("统一社会信用代码/注册号：");
//            } else {
//                socialcreditText.setText("统一社会信用代码/注册号：" + data.getSocialcredit());
//            }

            if(TextUtils.isEmpty(data.legalPerson)){
                legalText.setText("法定代表人: 未公布");
            }else{
                legalText.setText("法定代表人: " + data.legalPerson);
            }

            if(TextUtils.isEmpty(data.PublishTime)){
                dateText.setText("注册日期: 未公布" );
            }else{
                dateText.setText("注册日期: " + data.PublishTime);
            }


        }
    }
}
