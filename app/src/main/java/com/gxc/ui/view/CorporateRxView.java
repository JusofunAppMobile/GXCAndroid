package com.gxc.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.gxc.ui.activity.EditReportInfoActivity;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.ui.view.BaseView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.gxc.constants.Constants.NUM_MAX2;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/714:45
 * @Email zyp@jusfoun.com
 * @Description ${企业信息 item view}
 */
public class CorporateRxView extends BaseView {

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


    public CorporateRxView(Context context) {
        super(context);
    }

    public CorporateRxView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CorporateRxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_corporate_info, this, true);
        ButterKnife.bind(this);
    }

    @Override
    protected void initActions() {

    }

    public void setData(int type) {

        if (type == EditReportInfoActivity.TYPE_INFO) {
            viewName.setData("企业名称",NUM_MAX2);
            viewHangye.setData("行业",NUM_MAX2);
            viewPhone.setData("联系电话",NUM_MAX2);
            viewEmail.setData("邮箱",NUM_MAX2);
            viewHttp.setData("网址",NUM_MAX2);
            textIcon.setText("LOGO");
            textDes.setText("公司介绍");

        } else if (type == EditReportInfoActivity.TYPE_PRODUCE) {
            viewName.setData("所属公司",NUM_MAX2);
            viewHangye.setData("产品名称",NUM_MAX2);
            viewPhone.setData("所属领域",NUM_MAX2);
            viewEmail.setData("标签",NUM_MAX2);
            viewHttp.setData("链接地址",NUM_MAX2);
            textIcon.setText("产品图片");
            textDes.setText("简介");
        }
    }

}
