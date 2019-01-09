package com.jusfoun.jusfouninquire.ui.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.AdItemModel;
import com.jusfoun.jusfouninquire.net.model.HotConsultingItemModel;
import com.jusfoun.jusfouninquire.net.route.GetHotConsultingInfo;
import com.jusfoun.jusfouninquire.ui.activity.WebActivity;

import java.util.List;

import netlib.util.EventUtils;

/**
 * @author zhaoyapeng
 * @version create time:17/9/2111:32
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class ConsultingView extends RelativeLayout {
    private Context mContext;
    private List<HotConsultingItemModel> list;

    private SimpleDraweeView img1, img2, img3;
    private TextView titleText1, titleText2, titleText3,countText1,countText2,countText3;
    private LinearLayout consultLayout1, consultLayout2, consultLayout3;

    public ConsultingView(Context context) {
        super(context);
        mContext = context;
        initData();
        initViews();
        initActions();
    }

    public ConsultingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initData();
        initViews();
        initActions();
    }

    public ConsultingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initData();
        initViews();
        initActions();
    }

    private void initData() {

    }

    private void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_consulting, this, true);
        img1 = (SimpleDraweeView) findViewById(R.id.pictuer1);
        img2 = (SimpleDraweeView) findViewById(R.id.pictuer2);
        img3 = (SimpleDraweeView) findViewById(R.id.pictuer3);

        titleText1 = (TextView) findViewById(R.id.title_important1);
        titleText2 = (TextView) findViewById(R.id.title_important2);
        titleText3 = (TextView) findViewById(R.id.title_important3);

        countText1 = (TextView) findViewById(R.id.reader_count1);
        countText2 = (TextView) findViewById(R.id.reader_count2);
        countText3 = (TextView) findViewById(R.id.reader_count3);



        consultLayout1 = (LinearLayout) findViewById(R.id.layout_1);
        consultLayout2 = (LinearLayout) findViewById(R.id.layout_2);
        consultLayout3 = (LinearLayout) findViewById(R.id.layout_3);


        consultLayout1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                click(0);
            }
        });
        consultLayout2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                click(1);
            }
        });
        consultLayout3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                click(2);
            }
        });
    }

    private void initActions() {

    }

    public void setData(List<HotConsultingItemModel> list) {
        this.list = list;
        consultLayout1.setVisibility(INVISIBLE);
        consultLayout2.setVisibility(INVISIBLE);
        consultLayout3.setVisibility(INVISIBLE);
        if (list.size() == 0) {
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);
        }
        if (list.size() >= 1) {
            HotConsultingItemModel model = list.get(0);
            consultLayout1.setVisibility(VISIBLE);
            if (model != null) {
                if (!TextUtils.isEmpty(model.getNewdetailurl()))
                    img1.setImageURI(Uri.parse(model.getNewsimgurl()));
                titleText1.setText(model.getNewstitle());
                countText1.setText(model.getNewreadcount()+"阅读");
            }
        }

        if (list.size() >= 2) {
            HotConsultingItemModel model = list.get(1);
            consultLayout2.setVisibility(VISIBLE);
            if (model != null) {
                if (!TextUtils.isEmpty(model.getNewdetailurl()))
                    img2.setImageURI(Uri.parse(model.getNewsimgurl()));
                titleText2.setText(model.getNewstitle());
                countText2.setText(model.getNewreadcount()+"阅读");
            }
        }

        if (list.size() >= 3) {
            HotConsultingItemModel model = list.get(2);
            consultLayout3.setVisibility(VISIBLE);
            if (model != null) {
                if (!TextUtils.isEmpty(model.getNewdetailurl()))
                    img3.setImageURI(Uri.parse(model.getNewsimgurl()));
                titleText3.setText(model.getNewstitle());
                countText3.setText(model.getNewreadcount()+"阅读");
            }
        }
    }

    private void click(int index){
        if (list != null) {
            HotConsultingItemModel model = list.get(index);
            if (model == null)
                return;
            EventUtils.event(mContext,EventUtils.HOME93);
            Intent intent = new Intent(mContext, WebActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(WebActivity.URL, model.getNewdetailurl());
            bundle.putString(WebActivity.TITLE, "热门资讯");
            AdItemModel shareModel = new AdItemModel();
            shareModel.setImgUrl(model.getNewsimgurl());
            shareModel.setTitle(model.getNewstitle());
            shareModel.setReUrl(model.getNewdetailurl());
            shareModel.setDescribe(model.getNewstitle());
            bundle.putSerializable(WebActivity.SHAREDATA, shareModel);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
            GetHotConsultingInfo.getHotConsultingStatistics(mContext, model.getNewsid(), "ConsultingView");
        }

    }
}
