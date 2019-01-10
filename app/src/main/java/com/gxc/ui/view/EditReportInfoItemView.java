package com.gxc.ui.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gxc.model.EditReportInfoTextModel;
import com.gxc.ui.activity.EditReportInfoActivity;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.BaseView;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/517:25
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class EditReportInfoItemView extends BaseView {
    protected TextView textEdit;
    protected TextView textTitle;

    private int type;

    private RelativeLayout addLayout;

    public EditReportInfoItemView(Context context) {
        super(context);
    }

    public EditReportInfoItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditReportInfoItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.item_edit_report_info, this, true);
        textEdit = (TextView) findViewById(R.id.text_edit);
        textTitle = (TextView) findViewById(R.id.text_title);
        addLayout = (RelativeLayout)findViewById(R.id.layout_add);

    }

    @Override
    protected void initActions() {
        textEdit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditReportInfoActivity.class);
                intent.putExtra(EditReportInfoActivity.TYPE,type);
                mContext.startActivity(intent);
            }
        });
    }

    public void setData(int type,Object model) {
        this.type = type;
        if (type == EditReportInfoActivity.TYPE_INFO) {
            textTitle.setText("企业信息");
            if(model!=null) {
                addLayout.removeAllViews();
                CorporateInfoView corporateInfoView = new CorporateInfoView(mContext);
                corporateInfoView.setData(type, (EditReportInfoTextModel) model);
                addLayout.addView(corporateInfoView);
            }

        } else if (type == EditReportInfoActivity.TYPE_PRODUCE) {
            textTitle.setText("企业产品");
            if(model!=null) {
                addLayout.removeAllViews();
                CorporateInfoView corporateInfoView = new CorporateInfoView(mContext);
                corporateInfoView.setData(type, (EditReportInfoTextModel) model);
                addLayout.addView(corporateInfoView);
            }
        } else if (type == EditReportInfoActivity.TYPE_RY) {
            textTitle.setText("企业荣誉");
        } else if (type == EditReportInfoActivity.TYPE_HB) {
            textTitle.setText("企业伙伴");
        } else if (type == EditReportInfoActivity.TYPE_CY) {
            textTitle.setText("企业成员");
        }
    }
}
