package com.gxc.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.gxc.base.BaseActivity;
import com.gxc.ui.view.CorporateIInfoImgItemView;
import com.gxc.ui.view.CorporateInfoItemView;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/717:43
 * @Email zyp@jusfoun.com
 * @Description ${认证企业}
 */
public class CertifiedCompanyActivity extends BaseActivity {
    @BindView(R.id.text_tip)
    TextView textTip;
    @BindView(R.id.view_name)
    CorporateInfoItemView viewName;
    @BindView(R.id.view_code)
    CorporateInfoItemView viewCode;
    @BindView(R.id.view_zhiwei)
    CorporateInfoItemView viewZhiwei;
    @BindView(R.id.view_phone)
    CorporateInfoItemView viewPhone;
    @BindView(R.id.view_email)
    CorporateInfoItemView viewEmail;
    @BindView(R.id.img_yyzz)
    CorporateIInfoImgItemView imgYyzz;
    @BindView(R.id.img_idfen)
    CorporateIInfoImgItemView imgIdfen;
    @BindView(R.id.title_bar)
    TitleView titleBar;
    @BindView(R.id.btn_commit)
    Button btnCommit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_certified_company;
    }

    @Override
    public void initActions() {
        viewName.setData("企业名称");
        viewCode.setData("身  份  证");
        viewZhiwei.setData("职        位");
        viewPhone.setData("手机号码");
        viewEmail.setData("邮        箱");
        imgYyzz.setData("营业执照", getString(R.string.text_img_carme_tip));
        imgIdfen.setData("本人身份证", getString(R.string.text_img_carme_top_identity));

        titleBar.setTitle("认证企业");
    }

}
