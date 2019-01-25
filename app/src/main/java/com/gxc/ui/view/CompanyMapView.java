package com.gxc.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.request.RequestOptions;
import com.gxc.model.CorporateInfoModel;
import com.gxc.model.UserModel;
import com.gxc.ui.activity.LoginActivity;
import com.gxc.ui.activity.WebActivity;
import com.gxc.ui.dialog.VIPDialog;
import com.gxc.utils.AppUtils;
import com.gxc.utils.GlideRoundTransform;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.BaseView;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/909:22
 * @Email zyp@jusfoun.com
 * @Description ${企业图谱View}
 */
public class CompanyMapView extends BaseView {
    protected ImageView imgTupu;
    protected ImageView imgGuanxi;
    protected ImageView imgJiegou;
    protected LinearLayout layoutTupu;
    protected LinearLayout layoutGuanxi;
    protected LinearLayout layoutJiegou;
    private RequestOptions requestOptions;
    private CorporateInfoModel.CompanyInfo model;

    public CompanyMapView(Context context) {
        super(context);
    }

    public CompanyMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CompanyMapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initData() {
        requestOptions = new RequestOptions()
                .centerCrop()
                .circleCrop()//设置圆形
                .placeholder(R.color.white)
                .error(R.color.white)
                .transform(new GlideRoundTransform(mContext, 5));
    }

    @Override
    protected void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_company_map_gxc, this, true);
        initView(this);
    }

    @Override
    protected void initActions() {

        layoutTupu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVipUser() && model != null && !TextUtils.isEmpty(model.AtlasH5Address)) {
                    mContext.startActivity(WebActivity.getIntent(mContext, "企业图谱", model.AtlasH5Address));
                }
            }
        });

        layoutGuanxi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVipUser() && model != null && !TextUtils.isEmpty(model.CorrelationH5Address)) {
                    mContext.startActivity(WebActivity.getIntent(mContext, "企业图谱", model.CorrelationH5Address));
                }
            }
        });

        layoutJiegou.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVipUser() && model != null && !TextUtils.isEmpty(model.OwnershipStructureH5Address)) {
                    mContext.startActivity(WebActivity.getIntent(mContext, "企业图谱", model.OwnershipStructureH5Address));
                }
            }
        });
    }

    /**
     * 是否为VIP用户
     *
     * @return
     */
    private boolean isVipUser() {
        UserModel user = AppUtils.getUser();
        if (user == null) {
            mContext.startActivity(new Intent(mContext, LoginActivity.class));
            return false;
        }
        if (user.vipStatus == 1)
            return true;
        new VIPDialog((Activity) mContext).show();
        return false;
    }

    private void initView(View rootView) {
        imgTupu = (ImageView) rootView.findViewById(R.id.img_tupu);
        layoutTupu = (LinearLayout) rootView.findViewById(R.id.layout_tupu);
        imgGuanxi = (ImageView) rootView.findViewById(R.id.img_guanxi);
        layoutGuanxi = (LinearLayout) rootView.findViewById(R.id.layout_guanxi);
        imgJiegou = (ImageView) rootView.findViewById(R.id.img_jiegou);
        layoutJiegou = (LinearLayout) rootView.findViewById(R.id.layout_jiegou);
    }


    public void setData(CorporateInfoModel.CompanyInfo companyInfo) {
        model = companyInfo;
    }
}
