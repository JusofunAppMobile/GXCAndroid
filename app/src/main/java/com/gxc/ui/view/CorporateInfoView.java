package com.gxc.ui.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gxc.ui.activity.EditReportInfoActivity;
import com.gxc.utils.AppUtils;
import com.gxc.utils.GlideRoundTransform;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.BaseView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/714:45
 * @Email zyp@jusfoun.com
 * @Description ${企业信息 item view}
 */
public class CorporateInfoView extends BaseView {

    @BindView(R.id.view_name)
    CorporateInfoItemView viewName;
    @BindView(R.id.view_hangye)
    CorporateInfoItemView viewHangye;
    @BindView(R.id.view_phone)
    CorporateInfoItemView viewPhone;
    @BindView(R.id.view_email)
    CorporateInfoItemView viewEmail;
    @BindView(R.id.view_http)
    CorporateInfoItemView viewHttp;
    @BindView(R.id.text_icon)
    TextView textIcon;
    @BindView(R.id.text_des)
    TextView textDes;
    @BindView(R.id.text_content)
    TextView textContent;
    @BindView(R.id.img_icon)
    ImageView imgIcon;
    private RequestOptions requestOptions;

    public CorporateInfoView(Context context) {
        super(context);
    }

    public CorporateInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CorporateInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initData() {
        requestOptions = new RequestOptions()
                .centerCrop()
                .circleCrop()//设置圆形
                .placeholder(R.color.white)
                .error(R.color.white)
                .transform(new GlideRoundTransform(mContext,5));
    }

    @Override
    protected void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_corporate_info, this, true);
        ButterKnife.bind(this);
    }

    @Override
    protected void initActions() {
        imgIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.pictureSelect((Activity) mContext, false, 1, null);
            }
        });
    }

    public void setData(int type) {

        if (type == EditReportInfoActivity.TYPE_INFO) {
            viewName.setData("企业名称");
            viewHangye.setData("行        业");
            viewPhone.setData("联系电话");
            viewEmail.setData("邮        箱");
            viewHttp.setData("网        址");
            textIcon.setText("LOGO");
            textDes.setText("公司介绍");

        } else if (type == EditReportInfoActivity.TYPE_PRODUCE) {
            viewName.setData("所属公司");
            viewHangye.setData("产品名称");
            viewPhone.setData("所属领域");
            viewEmail.setData("标        签");
            viewHttp.setData("链接地址");
            textIcon.setText("产品图片");
            textDes.setText("简介");
        }
    }
    public void setImageSrc(String imageUrl){
        Glide.with(mContext).load(imageUrl).apply(requestOptions).into(imgIcon);
    }

}
