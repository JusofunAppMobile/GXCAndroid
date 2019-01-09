package com.gxc.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
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
                .transform(new GlideRoundTransform(mContext,5));
    }

    @Override
    protected void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_company_map_gxc, this, true);
        initView(this);
    }

    @Override
    protected void initActions() {
        Glide.with(mContext).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547010415324&di=2bff13d5f997a4baacc85df560c570db&imgtype=0&src=http%3A%2F%2Fwww.pptbz.com%2Fpptpic%2FUploadFiles_6909%2F201211%2F2012111719294197.jpg").apply(requestOptions).into(imgTupu);
        Glide.with(mContext).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547010415324&di=2bff13d5f997a4baacc85df560c570db&imgtype=0&src=http%3A%2F%2Fwww.pptbz.com%2Fpptpic%2FUploadFiles_6909%2F201211%2F2012111719294197.jpg").apply(requestOptions).into(imgGuanxi);
        Glide.with(mContext).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547010415324&di=2bff13d5f997a4baacc85df560c570db&imgtype=0&src=http%3A%2F%2Fwww.pptbz.com%2Fpptpic%2FUploadFiles_6909%2F201211%2F2012111719294197.jpg").apply(requestOptions).into(imgJiegou);
    }

    private void initView(View rootView) {
        imgTupu = (ImageView) rootView.findViewById(R.id.img_tupu);
        layoutTupu = (LinearLayout) rootView.findViewById(R.id.layout_tupu);
        imgGuanxi = (ImageView) rootView.findViewById(R.id.img_guanxi);
        layoutGuanxi = (LinearLayout) rootView.findViewById(R.id.layout_guanxi);
        imgJiegou = (ImageView) rootView.findViewById(R.id.img_jiegou);
        layoutJiegou = (LinearLayout) rootView.findViewById(R.id.layout_jiegou);
    }
}
