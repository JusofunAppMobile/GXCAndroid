package com.gxc.ui.adapter;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.model.MonitorDetailModel;
import com.siccredit.guoxin.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author liuguangdan
 * @version create at 2019/1/11/011 15:36
 * @Description ${}
 */
public class MonitorDetailAdapter extends BaseQuickAdapter<MonitorDetailModel, MonitorDetailAdapter.MyViewHolder> {

    public MonitorDetailAdapter() {
        super(R.layout.item_monitor_detail);
    }

    @Override
    protected void convert(MyViewHolder helper, MonitorDetailModel item) {
        helper.update(item);
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

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void update(MonitorDetailModel item) {
            if (item.type == 1) { // 1:父布局 2：子布局
                tvTitle.setText(item.contont);
                vTop.setVisibility(View.VISIBLE);
                vBottom.setVisibility(View.GONE);
                vDivider.setVisibility(getLayoutPosition() == 0 ? View.GONE : View.VISIBLE);
                // /图标类型lcon：1：变更信息    2：警示信息 3：利好信息
                if (item.status == 2) { // 1:法律诉讼 2专利信息 3变更信息 (旧)
                    tvLabel.setTextColor(Color.parseColor("#E02D35"));
                    tvLabel.setBackgroundResource(R.drawable.shape_stroke_red);
                    tvLabel.setText("警示信息");
                } else if (item.status == 3) {
                    tvLabel.setTextColor(Color.parseColor("#90CC6C"));
                    tvLabel.setBackgroundResource(R.drawable.shape_stroke_green);
                    tvLabel.setText("利好信息");
                } else {
                    tvLabel.setTextColor(Color.parseColor("#2D94EC"));
                    tvLabel.setBackgroundResource(R.drawable.shape_stroke_blue2);
                    tvLabel.setText("提示信息");
                }
                tvNum.setText("共" + item.total + "条");
            } else {
                vDivider.setVisibility(View.GONE);
                vTop.setVisibility(View.GONE);
                vBottom.setVisibility(View.VISIBLE);

                tvDesc.setText(item.contont);
                tvTime.setText(item.time);
            }
        }

    }
}
