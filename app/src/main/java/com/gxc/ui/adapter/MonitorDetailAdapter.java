package com.gxc.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.model.MonitorDetailModel;
import com.gxc.ui.activity.MonitorSubListActivity;
import com.jusfoun.jusfouninquire.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author liuguangdan
 * @version create at 2019/1/11/011 15:36
 * @Description ${}
 */
public class MonitorDetailAdapter extends BaseQuickAdapter<MonitorDetailModel, MonitorDetailAdapter.MyViewHolder> {

    private Context context;

    public MonitorDetailAdapter(Context context) {
        super(R.layout.item_monitor_detail);
        this.context = context;
    }

    @Override
    protected void convert(MyViewHolder helper, MonitorDetailModel item) {
        helper.update(helper, item);
    }


    public class MyViewHolder extends BaseViewHolder {

        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvLabel)
        TextView tvLabel;
        @BindView(R.id.tvNum)
        TextView tvNum;
        @BindView(R.id.vTop)
        ConstraintLayout vTop;
        @BindView(R.id.tvDesc)
        TextView tvDesc;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.vBottom)
        ConstraintLayout vBottom;
        @BindView(R.id.vDivider)
        View vDivider;

        public MyViewHolder( View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.tvNum)
        public void onViewClicked() {
            context.startActivity(new Intent(context, MonitorSubListActivity.class));
        }

        public void update(MyViewHolder helper, MonitorDetailModel item) {
            if (item.type == 1) { // 1:父布局 2：子布局
                vTop.setVisibility(View.VISIBLE);
                vBottom.setVisibility(View.GONE);
                vDivider.setVisibility(helper.getLayoutPosition() == 0 ? View.GONE : View.VISIBLE);
                if (item.status == 1) { // 1:法律诉讼 2专利信息 3变更信息
                    tvLabel.setTextColor(Color.parseColor("#E02D35"));
                    tvLabel.setBackgroundResource(R.drawable.shape_stroke_red);
                } else if (item.status == 2) {
                    tvLabel.setTextColor(Color.parseColor("#90CC6C"));
                    tvLabel.setBackgroundResource(R.drawable.shape_stroke_green);
                } else {
                    tvLabel.setTextColor(Color.parseColor("#2D94EC"));
                    tvLabel.setBackgroundResource(R.drawable.shape_stroke_blue2);
                }
            } else {
                vDivider.setVisibility(View.GONE);
                vTop.setVisibility(View.GONE);
                vBottom.setVisibility(View.VISIBLE);
            }
        }

    }
}
