package com.gxc.ui.view;

import android.app.Activity;
import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gxc.model.EditReportInfoTextModel;
import com.gxc.ui.activity.EditReportInfoActivity;
import com.gxc.utils.AppUtils;
import com.gxc.utils.GlideRoundTransform;
import com.jusfoun.jusfouninquire.R;
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
    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.img_icon)
    ImageView imgIcon;
    private RequestOptions requestOptions;

    private String imgUrl;

    private boolean editTable;

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
                .transform(new GlideRoundTransform(mContext, 5));
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
                if (editTable) {
                    AppUtils.pictureSelect((Activity) mContext, false, 1, null);
                }
            }
        });
        setEditTable(false);
    }

    private int type;

    public void setData(int type,EditReportInfoTextModel model) {
        this.type = type;
        if (type == EditReportInfoActivity.TYPE_INFO) {
            viewName.setData("企业名称",NUM_MAX2);
            viewHangye.setData("行业",NUM_MAX2);
            viewPhone.setData("联系电话",NUM_MAX2);
            viewEmail.setData("邮箱",NUM_MAX2);
            viewHttp.setData("网址",NUM_MAX2);
            textIcon.setText("LOGO");
            textDes.setText("公司介绍");

            if(model!=null){
                viewName.setEditText(model.companyName);
                viewHangye.setEditText(model.industry);
                viewPhone.setEditText(model.phone);
                viewEmail.setEditText(model.email);
                viewHttp.setEditText(model.webURL);
                editContent.setText(model.introduce);
                imgUrl = model.logo;
                Glide.with(mContext).load(model.logo).apply(requestOptions).into(imgIcon);

            }
        } else if (type == EditReportInfoActivity.TYPE_PRODUCE) {
            viewName.setData("所属公司",NUM_MAX2);
            viewHangye.setData("产品名称",NUM_MAX2);
            viewPhone.setData("所属领域",NUM_MAX2);
            viewEmail.setData("标签",NUM_MAX2);
            viewHttp.setData("链接地址",NUM_MAX2);
            textIcon.setText("产品图片");
            textDes.setText("简介");


            if(model!=null){
                viewName.setEditText(model.companyName);
                viewHangye.setEditText(model.product);
                viewPhone.setEditText(model.industry);
                viewEmail.setEditText(model.tag);
                viewHttp.setEditText(model.url);
                editContent.setText(model.introduce);
                imgUrl = model.image;
                Glide.with(mContext).load(model.image).apply(requestOptions).into(imgIcon);

            }
        }
    }

    public void setImageSrc(String imageUrl) {
        this.imgUrl = imageUrl;
        Glide.with(mContext).load(imageUrl).apply(requestOptions).into(imgIcon);
    }

    public void setEditTable(boolean editTable) {
        this.editTable = editTable;
        viewName.setEditTable(editTable);
        viewHangye.setEditTable(editTable);
        viewPhone.setEditTable(editTable);
        viewEmail.setEditTable(editTable);
        viewHttp.setEditTable(editTable);

        editContent.setFocusable(editTable);
        editContent.setFocusableInTouchMode(editTable);
        editContent.setLongClickable(editTable);
        editContent.setInputType(editTable ? InputType.TYPE_CLASS_TEXT : InputType.TYPE_NULL);
    }


    public EditReportInfoTextModel getData() {
        EditReportInfoTextModel model = new EditReportInfoTextModel();

        if (type == EditReportInfoActivity.TYPE_INFO) {
            model.companyName = viewName.getEditText();
            model.industry = viewHangye.getEditText();
            model.phone = viewPhone.getEditText();
            model.email = viewEmail.getEditText();
            model.webURL = viewHttp.getEditText();
            model.logo = imgUrl;
            model.introduce = editContent.getText().toString();
        }else if (type == EditReportInfoActivity.TYPE_PRODUCE) {
            model.companyName = viewName.getEditText();
            model.industry = viewPhone.getEditText();
            model.product = viewHangye.getEditText();
            model.tag = viewEmail.getEditText();
            model.url = viewHttp.getEditText();
            model.image = imgUrl;
            model.introduce = editContent.getText().toString();
        }
        return  model;
    }

}
