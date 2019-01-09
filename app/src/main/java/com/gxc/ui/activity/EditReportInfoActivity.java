package com.gxc.ui.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.gxc.base.BaseActivity;
import com.gxc.ui.view.CorporateIRxImgView;
import com.gxc.ui.view.CorporateInfoView;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/714:55
 * @Email zyp@jusfoun.com
 * @Description ${编辑自主填报页面}
 */
public class EditReportInfoActivity extends BaseActivity {


    @BindView(R.id.titlebar)
    TitleView titlebar;
    @BindView(R.id.layout_view)
    LinearLayout layoutView;

    public static final int TYPE_INFO = 1;//企业信息
    public static final int TYPE_PRODUCE = 2;//企业产品
    public static final int TYPE_RY = 3;//企业荣誉
    public static final int TYPE_HB = 4;//企业伙伴
    public static final int TYPE_CY = 5;//企业成员

    public static String TYPE = "type";

    private int  type =1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_repory_info;
    }

    @Override
    public void initActions() {
        type = getIntent().getIntExtra(TYPE,TYPE_INFO);

        if(type==TYPE_INFO){
            CorporateInfoView corporateInfoView = new CorporateInfoView(this);
            corporateInfoView.setData(TYPE_INFO);
            layoutView.addView(corporateInfoView);
            titlebar.setTitle("企业信息");
        }else if(type ==TYPE_PRODUCE){
            CorporateInfoView corporateInfoView = new CorporateInfoView(this);
            corporateInfoView.setData(TYPE_PRODUCE);
            layoutView.addView(corporateInfoView);
            titlebar.setTitle("企业产品");
        }else if(type ==TYPE_RY){
            CorporateIRxImgView corporateInfoView = new CorporateIRxImgView(this);
            corporateInfoView.setData(TYPE_RY);
            layoutView.addView(corporateInfoView);
            titlebar.setTitle("企业荣誉");
        }else if(type ==TYPE_HB){
            CorporateIRxImgView corporateInfoView = new CorporateIRxImgView(this);
            corporateInfoView.setData(TYPE_HB);
            layoutView.addView(corporateInfoView);
            titlebar.setTitle("企业伙伴");
        }else if(type ==TYPE_CY){
            CorporateIRxImgView corporateInfoView = new CorporateIRxImgView(this);
            corporateInfoView.setData(TYPE_CY);
            layoutView.addView(corporateInfoView);
            titlebar.setTitle("企业成员");
        }
    }
}
