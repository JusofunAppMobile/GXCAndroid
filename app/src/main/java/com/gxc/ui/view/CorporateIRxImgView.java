package com.gxc.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

import com.gxc.ui.activity.EditReportInfoActivity;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.BaseView;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/714:45
 * @Email zyp@jusfoun.com
 * @Description ${企业荣誉  view}
 */
public class CorporateIRxImgView extends BaseView {

    protected CorporateInfoItemView textTitle,zhiwuText;
    protected TextView textDes,titleImgText;
    protected EditText editCotent;

    public CorporateIRxImgView(Context context) {
        super(context);
    }

    public CorporateIRxImgView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CorporateIRxImgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_corporate_rx_img, this, true);
        textTitle = (CorporateInfoItemView) findViewById(R.id.text_title);
        zhiwuText = (CorporateInfoItemView) findViewById(R.id.text_zhiwu);
        textDes = (TextView) findViewById(R.id.text_des);
        editCotent = (EditText) findViewById(R.id.edit_cotent);
        titleImgText = (TextView) findViewById(R.id.text_img_title);
    }

    @Override
    protected void initActions() {

    }

    public void setData(int type) {
        if (type == EditReportInfoActivity.TYPE_RY) {
            textTitle.setData("企业荣誉");
            titleImgText.setText("荣誉图片");
            textDes.setText("荣誉图片");
            editCotent.setHint("请输入荣誉简介");
        }else if(type == EditReportInfoActivity.TYPE_HB){
            textTitle.setData("企业伙伴名称");
            titleImgText.setText("合作伙伴图片");
            textDes.setText("简介");
            editCotent.setHint("请输入简介");
        }else if(type == EditReportInfoActivity.TYPE_CY){
            textTitle.setData("企业姓名");
            titleImgText.setText("企业成员图片");
            textDes.setText("简介");
            editCotent.setHint("请输入简介");
            zhiwuText.setVisibility(VISIBLE);
            zhiwuText.setData("成员职务");
        }
    }

}
