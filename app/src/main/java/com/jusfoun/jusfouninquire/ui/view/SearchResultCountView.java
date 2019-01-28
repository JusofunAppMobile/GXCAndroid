package com.jusfoun.jusfouninquire.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.siccredit.guoxin.R;

/**
 * @author zhaoyapeng
 * @version create time:18/7/2010:38
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class SearchResultCountView extends BaseView {
    protected TextView tvLabelInfo;
    protected TextView tvLabelType;
    protected TextView textDcReport;
    protected TextView textCancle;
    protected ImageView imgIconSelect;
    protected TextView textSelectAll;
    protected LinearLayout layoutSelect;
    protected TextView textDaochu,countText;
    protected RelativeLayout layoutDaochu;
    private TextView tvCount;
    private boolean isSelectAll=false;
    private RelativeLayout rootLayout;

    public SearchResultCountView(Context context) {
        super(context);
    }

    public SearchResultCountView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchResultCountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_list_header, this, true);
        initView(this);
    }

    @Override
    protected void initActions() {
        layoutSelect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isSelectAll){
                    isSelectAll = false;
                    imgIconSelect.setImageResource(R.drawable.img_constant_select_no);
                    callback.cancleSelectAll();
                }else{
                    isSelectAll = true;
                    imgIconSelect.setImageResource(R.drawable.img_constant_select_yes);
                    callback.selectAll();
                }
            }
        });

        textCancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                layoutDaochu.setVisibility(GONE);
                isSelectAll = false;
                imgIconSelect.setImageResource(R.drawable.img_constant_select_no);

                if (callback != null)
                    callback.hideSelect();

            }
        });
        textDcReport.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutDaochu.setVisibility(VISIBLE);
                if(callback!=null){
                    callback.showSelect();
                }
            }
        });

        textDaochu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback!=null){
                    callback.goExport();
                }
            }
        });
        textDcReport.setVisibility(VISIBLE);

        rootLayout.setVisibility(View.VISIBLE);
    }

    public void setData(String count) {
        tvCount.setText(count);
    }

    private void initView(View rootView) {
        tvLabelInfo = (TextView) rootView.findViewById(R.id.tvLabelInfo);
        tvCount = (TextView) rootView.findViewById(R.id.tvCount);
        tvLabelType = (TextView) rootView.findViewById(R.id.tvLabelType);
        textDcReport = (TextView) rootView.findViewById(R.id.text_dc_report);
        textCancle = (TextView) rootView.findViewById(R.id.text_cancle);
        imgIconSelect = (ImageView) rootView.findViewById(R.id.img_icon_select);
        textSelectAll = (TextView) rootView.findViewById(R.id.text_select_all);
        layoutSelect = (LinearLayout) rootView.findViewById(R.id.layout_select);
        textDaochu = (TextView) rootView.findViewById(R.id.text_daochu);
        layoutDaochu = (RelativeLayout) rootView.findViewById(R.id.layout_daochu);
        countText=(TextView)rootView.findViewById(R.id.text_dc_count);
        rootLayout = (RelativeLayout)rootView.findViewById(R.id.layout_head_root);
    }


    public interface Callback {
        void selectAll();

        void cancleSelectAll();

        void goExport();

        void showSelect();

        void hideSelect();
    }

    private Callback callback;

    public void setCallBack(Callback callback) {
        this.callback = callback;
    }

    public void setDaochuGone(){
        textDcReport.setVisibility(GONE);
    }

    public void setCancleSelect(){
        imgIconSelect.setImageResource(R.drawable.img_constant_select_no);
        isSelectAll = false;
    }


    public void setSelectText(String count){
        countText.setText(count+"/100");
    }

    public void reSet(){
        countText.setText("0/100");
        isSelectAll = false;
        imgIconSelect.setImageResource(R.drawable.img_constant_select_no);
        callback.cancleSelectAll();
    }

}
