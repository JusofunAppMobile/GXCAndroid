package com.gxc.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.BaseView;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/717:55
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class CorporateIInfoImgItemView extends BaseView {
    protected TextView  titleImgText,desText;

    public CorporateIInfoImgItemView(Context context) {
        super(context);
    }

    public CorporateIInfoImgItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CorporateIInfoImgItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_corporate_info_img_item, this, true);
        titleImgText = (TextView) findViewById(R.id.text_img_title);
        desText= (TextView)findViewById(R.id.text_img_des);
    }

    @Override
    protected void initActions() {

    }

    public void setData(String title,String des) {
        titleImgText.setText(title);
        desText.setText(des);
    }
}
