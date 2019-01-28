package com.jusfoun.jusfouninquire.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siccredit.guoxin.R;

/**
 * @author zhaoyapeng
 * @version create time:18/5/714:19
 * @Email zyp@jusfoun.com
 * @Description ${通讯录title view}
 */
public class ContactsTitleView extends BaseView {
    protected View rootView;
    protected ImageView imgArrow, iconSelectImg;
    protected LinearLayout layoutZhucezijin;
    protected TextView textDaochu;
    protected TextView textCancle;
    protected TextView textSelectAll;
    protected ImageView imgIconSelect;
    private boolean isAes = false; //是否为升序
    private String label = "导出通讯录";

    private LinearLayout selectLayout;

    public ContactsTitleView(Context context) {
        super(context);
    }

    public ContactsTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContactsTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_contacts_title, this, true);
        initView(this);
    }

    @Override
    protected void initActions() {
        textDaochu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.goExport();

            }
        });
    }

    private void initView(View rootView) {
        imgArrow = (ImageView) rootView.findViewById(R.id.img_arrow);
        layoutZhucezijin = (LinearLayout) rootView.findViewById(R.id.layout_zhucezijin);
        textDaochu = (TextView) rootView.findViewById(R.id.text_daochu);
        textCancle = (TextView) rootView.findViewById(R.id.text_cancle);
        textSelectAll = (TextView) rootView.findViewById(R.id.text_select_all);
        iconSelectImg = (ImageView) rootView.findViewById(R.id.img_icon_select);
        selectLayout = (LinearLayout) rootView.findViewById(R.id.layout_select);

        selectLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    if (textSelectAll.getTag() == null || "取消导出".equals(textSelectAll.getTag().toString())) {
                        textSelectAll.setTag(label);
                        iconSelectImg.setImageResource(R.drawable.img_constant_select_yes);
                        callback.selectAll();
                    } else {
                        iconSelectImg.setImageResource(R.drawable.img_constant_select_no);
                        textSelectAll.setTag("取消导出");
                        callback.cancleSelectAll();
                    }

                }
            }
        });

        textCancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (textCancle.getTag() == null || label.equals(textCancle.getText().toString())) {
                    textCancle.setText("取消导出");
                    textCancle.setTag("取消导出");
                    textCancle.setTextColor(mContext.getResources().getColor(R.color.delete_search_history));
                    textSelectAll.setVisibility(VISIBLE);
                    textDaochu.setVisibility(VISIBLE);
                    iconSelectImg.setVisibility(VISIBLE);
                    if (callback != null)
                        callback.select();
                } else {
                    textCancle.setText(label);
                    textCancle.setTag(label);
                    textCancle.setTextColor(mContext.getResources().getColor(R.color.item_option_text));
                    textSelectAll.setVisibility(INVISIBLE);
                    textDaochu.setVisibility(INVISIBLE);
                    iconSelectImg.setVisibility(INVISIBLE);
                    iconSelectImg.setImageResource(R.drawable.img_constant_select_no);
                    if (callback != null)
                        callback.cancleSelect();
                }
            }
        });
        imgIconSelect = (ImageView) rootView.findViewById(R.id.img_icon_select);


        layoutZhucezijin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAes) {
                    imgArrow.animate().rotation(0);
                    isAes = false;
                    if (callback != null) {
                        callback.searchDes();
                    }
                } else {
                    imgArrow.animate().rotation(180);

                    isAes = true;
                    if (callback != null) {
                        callback.searchAse();
                    }
                }

            }
        });
    }

    public interface Callback {
        void selectAll();

        void cancleSelectAll();

        void cancleSelect();

        void select();

        void goExport();

        void searchAse();

        void searchDes();

    }

    private Callback callback;

    public void setCallBack(Callback callback) {
        this.callback = callback;
    }

    public void setCancleSelect() {
        iconSelectImg.setImageResource(R.drawable.img_constant_select_no);
        textSelectAll.setTag("取消导出");
    }


    public void reSet() {
        imgIconSelect.setImageResource(R.drawable.img_constant_select_no);
        callback.cancleSelectAll();
    }

    public void setLabel(String label) {
        this.label = label;
        textCancle.setText(label);
    }


}
