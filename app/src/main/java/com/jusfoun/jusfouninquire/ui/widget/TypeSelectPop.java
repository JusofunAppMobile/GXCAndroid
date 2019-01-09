package com.jusfoun.jusfouninquire.ui.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.activity.CompanyDetailActivity;
import com.nineoldandroids.animation.ObjectAnimator;

public class TypeSelectPop extends PopupWindow implements View.OnClickListener {

    View animView;
    int type;

    ViewGroup v1, v2, v3;

    private OnSelectListener listener;

    public TypeSelectPop(Context context, View animView, int type, OnSelectListener listener) {
        super(context);
        this.animView = animView;
        this.type = type;
        this.listener = listener;

        setAnimationStyle(R.style.PopupWindow);

        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_type_select, null);

        setContentView(contentView);

        v1 = (ViewGroup) contentView.findViewById(R.id.v1);
        v2 = (ViewGroup) contentView.findViewById(R.id.v2);
        v3 = (ViewGroup) contentView.findViewById(R.id.v3);

        dealVisible(type);

        setBackgroundDrawable(new ColorDrawable(0x00000000));
        setFocusable(true);
        setOutsideTouchable(true);


        v1.setOnClickListener(this);
        v2.setOnClickListener(this);
        v3.setOnClickListener(this);
    }

    private void dealVisible(int type) {
        if (type == CompanyDetailActivity.TYPE_QIYEBAOGAO) {
            v3.setVisibility(View.GONE);
            v2.getChildAt(1).setVisibility(View.GONE);
        } else if (type == CompanyDetailActivity.TYPE_GUQUAN) {
            v2.setVisibility(View.GONE);
            v3.getChildAt(1).setVisibility(View.GONE);
        } else {
            v1.setVisibility(View.GONE);
            v3.getChildAt(1).setVisibility(View.GONE);
        }
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        showArrowAnim(true);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        showArrowAnim(false);
    }

    private void showArrowAnim(boolean show) {
        if (show) {
            ObjectAnimator ra = ObjectAnimator.ofFloat(animView, "rotation", 0f, 180f);
            ra.setDuration(400);
            ra.start();
        } else {
            ObjectAnimator ra = ObjectAnimator.ofFloat(animView, "rotation", 180f, 0f);
            ra.setDuration(400);
            ra.start();
        }
    }

    @Override
    public void onClick(View v) {
        TextView tv = (TextView) ((ViewGroup) ((ViewGroup) v).getChildAt(0)).getChildAt(1);
        String label = tv.getText().toString();
        switch (v.getId()) {
            case R.id.v1:
                if (listener != null)
                    listener.select(CompanyDetailActivity.TYPE_FENGXIAN, label);
                break;
            case R.id.v2:
                if (listener != null)
                    listener.select(CompanyDetailActivity.TYPE_GUQUAN, label);
                break;
            case R.id.v3:
                if (listener != null)
                    listener.select(CompanyDetailActivity.TYPE_QIYEBAOGAO, label);
                break;
        }
        dismiss();
    }


    public interface OnSelectListener {
        void select(int type, String text);
    }
}
