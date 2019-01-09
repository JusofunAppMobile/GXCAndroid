package com.jusfoun.jusfouninquire.ui.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.route.WageInfoModel;
import com.jusfoun.jusfouninquire.ui.activity.CommonProblemActivity;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;

import java.util.List;

import netlib.util.EventUtils;

/**
 * Created by lsq on 2016/8/9.N
 * 问卷调查命名与工资和查询对调了
 */
public class WagePopBoxDialog extends Dialog {
    private Context mContext;
    private TextView title, contentText;
    private ImageView showImage;
    private Button agree, hope;
    private List<WageInfoModel> wageInfoModelList;
    private SimpleDraweeView contentImg;
    private String url;
    private WageInfoModel model;

    public WagePopBoxDialog(Context context) {
        super(context);
        mContext = context;
        initViews();
        initAation();
    }

    public WagePopBoxDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
        initViews();
        initAation();
    }

    protected WagePopBoxDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
        initViews();
        initAation();
    }

    private void initViews() {
        setContentView(R.layout.wage_query_popup_box);
        title = (TextView) findViewById(R.id.title);
        TextPaint tp = title.getPaint();
        tp.setFakeBoldText(true);
        contentText = (TextView) findViewById(R.id.text_content);
//        showImage = (ImageView) findViewById(R.id.image_content);
        hope = (Button) findViewById(R.id.positiveButton);
        agree = (Button) findViewById(R.id.negativeButton);
        contentImg = (SimpleDraweeView) findViewById(R.id.image_content);


        Window window = this.getWindow();
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (PhoneUtil.getDisplayWidth(mContext) * 0.9);
        lp.height = (int) (PhoneUtil.getDisplayHeight(mContext) * 0.8);
        window.setGravity(Gravity.CENTER);
    }

    private void initAation() {

        hope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext,EventUtils.ADVERT23);
                if (url != null && !url.equals("")) {
                    Intent intent = new Intent(mContext, CommonProblemActivity.class);
                    intent.putExtra(CommonProblemActivity.TYPE, CommonProblemActivity.QUESTION_TYPE);
                    intent.putExtra(CommonProblemActivity.APP_URL, url);
                    if (model != null && model.getDataResult() != null)
                        intent.putExtra(CommonProblemActivity.QURSION__TITLE, model.getDataResult().getTitle());

                    ((Activity) mContext).startActivity(intent);

                    dismiss();

                }
            }
        });
        agree.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EventUtils.event(mContext,EventUtils.ADVERT24);
                dismiss();
            }
        });
    }

    public void setData(WageInfoModel model) {

        if (model == null || model.getDataResult() == null) {
            return;
        }
        this.model = model;
        title.setText(model.getDataResult().getTitle());

        contentText.setText(model.getDataResult().getContent());
        hope.setText(model.getDataResult().getJoin());
        agree.setText(model.getDataResult().getCancle());
        url = model.getDataResult().getHtmlurl();
        if (model.getDataResult().getImageurl() != null) {
            contentImg.setImageURI(Uri.parse(model.getDataResult().getImageurl()));
        }


    }
//    @Override
//    public void show() {
//        WindowManager.LayoutParams alp = ((Activity) mContext).getWindow().getAttributes();
//        alp.alpha = 0.4f;
//        ((Activity) mContext).getWindow().setAttributes(alp);
//        super.show();
//    }
//
//    @Override
//    public void cancel() {
//        WindowManager.LayoutParams alp = ((Activity) mContext).getWindow().getAttributes();
//        alp.alpha = 1f;
//        ((Activity) mContext).getWindow().setAttributes(alp);
//        super.cancel();
//    }


}


