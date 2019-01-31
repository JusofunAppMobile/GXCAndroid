package com.jusfoun.jusfouninquire.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gxc.model.CorporateInfoModel;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.model.CompanyDetailModel;
import com.jusfoun.jusfouninquire.net.model.ContactsModel;
import com.jusfoun.jusfouninquire.net.model.UserInfoModel;
import com.jusfoun.jusfouninquire.net.util.TouchUtil;
import com.jusfoun.jusfouninquire.ui.activity.BaiduMapActivity;
import com.jusfoun.jusfouninquire.ui.activity.ExportContactsActivity;
import com.jusfoun.jusfouninquire.ui.activity.LoginActivity;
import com.jusfoun.jusfouninquire.ui.activity.WebActivity;
import com.jusfoun.jusfouninquire.ui.util.RefreshAnimUtil;

import java.util.ArrayList;
import java.util.List;

import netlib.util.EventUtils;

/**
 * Author  wangchenchen
 * CreateDate 2016/8/10.
 * Email wcc@jusfoun.com
 * Description 企业详情
 */
public class CompanyDetailHeaderView extends LinearLayout {
    private TextView company_name, company_nature, scan_count, update,
            legal, scale, create_date, industry_content, capacity_content,
            address_content, phone_content, website_content, legalName, tvTaxid;

    private TextView tvScore;

    private RelativeLayout address_layout, phone_layout, website_layout, refreshLayout;

    private Drawable refresh;

    private CompanyDetailModel model;
    private Context context;

    private TextView mCompanyState, xinyongfenText;
    private LinearLayout locationWebLayout;
    private ImageView refreshImg;
    private RefreshAnimUtil refreshAnimUtil;
    private View vExport;
    private View moreText;
    private View vScore;
    private CorporateInfoModel.CompanyInfo companyInfo;

    public CompanyDetailHeaderView(Context context) {
        super(context);
        initView(context);
        initAction();
    }

    public CompanyDetailHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAction();
    }

    public CompanyDetailHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAction();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CompanyDetailHeaderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
        initAction();
    }

    private void initView(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.layout_company_header_view, this, true);
        company_name = (TextView) findViewById(R.id.company_name);
        vScore = findViewById(R.id.vScore);
        tvTaxid = (TextView) findViewById(R.id.tvTaxid);
        company_nature = (TextView) findViewById(R.id.company_nature);
        tvScore = (TextView) findViewById(R.id.tvScore);
        vExport = findViewById(R.id.vExport);
        scan_count = (TextView) findViewById(R.id.scan_count);

        update = (TextView) findViewById(R.id.update);
        legal = (TextView) findViewById(R.id.legal);
        legalName = (TextView) findViewById(R.id.legal_name);
        scale = (TextView) findViewById(R.id.scale);
        create_date = (TextView) findViewById(R.id.create_date);

        address_content = (TextView) findViewById(R.id.address_content);
        phone_content = (TextView) findViewById(R.id.phone_content);
        website_content = (TextView) findViewById(R.id.website_content);
        address_layout = (RelativeLayout) findViewById(R.id.address_layout);
        phone_layout = (RelativeLayout) findViewById(R.id.phone_layout);
        website_layout = (RelativeLayout) findViewById(R.id.website_layout);
        mCompanyState = (TextView) findViewById(R.id.company_state_content);
        locationWebLayout = (LinearLayout) findViewById(R.id.layout_location_website);
        refreshImg = (ImageView) findViewById(R.id.img_update);
        refreshLayout = (RelativeLayout) findViewById(R.id.layout_refresh);
        xinyongfenText = (TextView) findViewById(R.id.text_fen);
        moreText = findViewById(R.id.text_more);
        refreshAnimUtil = new RefreshAnimUtil(refreshImg);
    }

    private void initAction() {

        phone_content.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getCompanyphonelist() != null && model.getCompanyphonelist().size() > 0) {
                    new PhoneCallDialog(getContext(), phone_content.getText().toString()).show();
                }
            }
        });

        refresh = context.getResources().getDrawable(R.mipmap.company_update_refresh);
        refresh.setBounds(0, 0, refresh.getMinimumWidth(), refresh.getMinimumHeight());

        refreshLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model == null)
                    return;

                refreshAnimUtil.startAnim();
                if (updateListener != null) {
                    updateListener.onClick(v);
                }
            }
        });

//        phone_layout.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (model == null)
//                    return;
//                if (model.getCompanyphonelist() != null && model.getCompanyphonelist().size() > 0) {
//                    EventUtils.event(context, EventUtils.DETAIL61);
//
//                    new PhoneCallDialog(getContext(), model.getCompanyphonelist().get(0).getNumber()).show();
//                }
//            }
//        });

        website_layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (model == null)
                    return;
                if (model.getNeturl() != null && model.getNeturl().size() > 0) {
                    EventUtils.event(context, EventUtils.DETAIL62);
                    Intent intent = new Intent(context, WebActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(WebActivity.TITLE, "");
                    if (model.getNeturl().get(0).getUrl().startsWith("http://")) {
                        bundle.putString(WebActivity.URL, model.getNeturl().get(0).getUrl());
                    } else {
                        bundle.putString(WebActivity.URL, "http://" + model.getNeturl().get(0).getUrl());
                    }
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            }
        });

        address_layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (model == null)
                    return;

                try {

                    EventUtils.event(context, EventUtils.DETAIL60);
                    Double lat = Double.parseDouble(model.getLatitude());
                    Double lon = Double.parseDouble(model.getLongitude());
                    Intent intent = new Intent(context, BaiduMapActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(BaiduMapActivity.COMPANY_NAME, model.getCompanyname());
                    bundle.putString(BaiduMapActivity.LAT, model.getLatitude());
                    bundle.putString(BaiduMapActivity.LON, model.getLongitude());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } catch (Exception e) {

                }
            }
        });
        phone_layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (locationWebLayout.getVisibility() == View.GONE) {
//                    startOpen();
//                } else {
//                    startClose();
//                }
            }
        });

        vExport.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoModel user = InquireApplication.getUserInfo();
                if (user != null) {
                    List<ContactsModel> list = new ArrayList<>();
                    ContactsModel cm = new ContactsModel();
                    cm.id = model.getCompanyid();
                    cm.name = model.getCompanyname();
                    cm.time = model.getCratedate();
                    cm.phones = model.getCompanyphonelist();
                    list.add(cm);
                    Intent intent = new Intent(context, ExportContactsActivity.class);
                    intent.putExtra("model", new Gson().toJson(list));
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }

            }
        });

        moreText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (companyInfo != null && !TextUtils.isEmpty(companyInfo.InfoH5Address)) {
                    context.startActivity(com.gxc.ui.activity.WebActivity.getIntent(context, "企业信息", companyInfo.InfoH5Address));
                }
            }
        });

        TouchUtil.createTouchDelegate(update, 40);
    }

    public void setFollow_count(String string) {
//        follow_count.setText(" 关注:" + string);
    }

    public void setUpdate(CompanyDetailModel companyDetailModel) {
        refreshAnimUtil.stopAnim();
        if (companyDetailModel != null) {
            update.setText(companyDetailModel.getUpdatestate());
        }
//        update.setBackgroundResource(R.drawable.company_update_bg);
//        update.setCompoundDrawables(null, null, null, null);
        model.setIndustry("2");

    }

    public void setInfo(CompanyDetailModel model) {
        this.model = model;
        company_name.setText(model.getCompanyname());
        if (!TextUtils.isEmpty(model.getCompanynature())) {
            company_nature.setText(model.getCompanynature());
        }

//        follow_count.setText("(" + model.getAttentioncount() + ")");
        scan_count.setText("(" + model.getBrowsecount() + ")");
        update.setText(model.getUpdatestate());
        tvTaxid.setText(model.taxid);

//        if ("1".equals(model.getIsupdate())) {
//            update.setBackgroundResource(R.drawable.company_update_bg_un);
//            update.setCompoundDrawables(null, null, refresh, null);
//        } else {
//            update.setBackgroundResource(R.drawable.company_update_bg);
//            update.setCompoundDrawables(null, null, null, null);
//        }

        mCompanyState.setText(model.getStates());

        if (TextUtils.isEmpty(model.getCorporate())) {
            legalName.setText("未公布");
        } else {
            legalName.setText(model.getCorporate());
        }
        if (TextUtils.isEmpty(model.getRegistercapital())) {
            scale.setText("未公布");
        } else {
            scale.setText(model.getRegistercapital());
        }

        if (TextUtils.isEmpty(model.getCratedate())) {
            create_date.setText("未公布");
        } else {
            create_date.setText(model.getCratedate());
        }

        if (TextUtils.isEmpty(model.getAddress())) {
            address_content.setText("未公布");
        } else {
            address_content.setText(model.getAddress());
        }
        if (model.getCompanyphonelist() != null && model.getCompanyphonelist().size() > 0) {
            phone_content.setText(model.getCompanyphonelist().get(0).getNumber());
//            if (!TextUtils.isEmpty(phone_content.getText().toString().trim()))
//                vExport.setVisibility(View.VISIBLE);
//            phone_content.setTextColor(Color.parseColor("#ff8d57"));
        } else {
            phone_content.setText("企业选择不公示");
//            phone_content.setTextColor(Color.parseColor("#999999"));
        }
        if (model.getNeturl() != null && model.getNeturl().size() > 0) {
            website_content.setText(model.getNeturl().get(0).getUrl());
//            website_content.setTextColor(Color.parseColor("#ff8d57"));

        } else {
            website_content.setText("企业选择不公示");
//            website_content.setTextColor(Color.parseColor("#999999"));

        }

        try {
            Double lat = Double.parseDouble(model.getLatitude());
            Double lon = Double.parseDouble(model.getLongitude());
            address_content.setTextColor(Color.parseColor("#6182bd"));
        } catch (Exception e) {
            address_content.setTextColor(Color.parseColor("#333333"));
        }
    }

    private SpannableString getText(String str1, String str2) {
        SpannableString stringBuilder = new SpannableString(str1 + "\n" + str2);

        stringBuilder.setSpan(new AbsoluteSizeSpan(14, true), 0, str1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.setSpan(new AbsoluteSizeSpan(12, true), str1.length(), str1.length() + str2.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffff")), 0, str1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#d7d7d7")), str1.length(), str1.length() + str2.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return stringBuilder;
    }

    private OnClickListener updateListener;

    public void setUpdateListener(OnClickListener updateListener) {
        this.updateListener = updateListener;
    }

    public void setUpdateText(String text) {
        update.setText(text);
    }


    public void setGxcData(CorporateInfoModel.CompanyInfo companyInfo) {
        this.companyInfo = companyInfo;
        if(TextUtils.isEmpty(companyInfo.creditScore))
            vScore.setVisibility(View.GONE);
        tvScore.setText("信用分：");
        xinyongfenText.setText(TextUtils.isEmpty(companyInfo.creditScore) ? "暂无" : companyInfo.creditScore);
    }
}
