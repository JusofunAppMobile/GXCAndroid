package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.CompanyDetailMenuModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/13.
 * Description
 */
public class CompanyAmendAdapter extends RecyclerView.Adapter<CompanyAmendAdapter.CompanyAmendHodler> {

    private Context context;
    private List<HashMap<String, Object>> list;

    public CompanyAmendAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public CompanyAmendHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CompanyAmendHodler(LayoutInflater.from(context).inflate(R.layout.item_company_amend, null));
    }

    @Override
    public void onBindViewHolder(CompanyAmendHodler holder, int position) {
        holder.update(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void refresh(List<HashMap<String, Object>> list) {

        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public List<HashMap<String, Object>> getList() {
        return list;
    }

    class CompanyAmendHodler extends RecyclerView.ViewHolder {

        private TextView txt, img;
        private View view;

        public CompanyAmendHodler(View itemView) {
            super(itemView);
            view = itemView;
            txt = (TextView) itemView.findViewById(R.id.company_menu_item);
            img = (TextView) itemView.findViewById(R.id.image);
        }

        public void update(HashMap<String, Object> map, final int position) {

            txt.setSelected("true".equals(map.get("check").toString()));

//            if ("true".equals(map.get("check").toString())) {
//                img.setText("选中图片");
//                txt.setBackgroundResource(R.drawable.shape_table_button);
//                txt.setTextColor(context.getResources().getColor(R.color.edit_text_colors));
//            } else {
//                img.setText("未选中图片");
//                txt.setBackgroundResource(R.drawable.shape_table_button_normer);
//                txt.setTextColor(context.getResources().getColor(R.color.edit_text_colors));
//            }
            CompanyDetailMenuModel model = (CompanyDetailMenuModel) map.get("company");
            if (!TextUtils.isEmpty(model.getItems()))
                txt.setText(model.getItems());
            else
                txt.setText(model.getMenuname());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(position);
                    }
                }
            });
        }
    }

    private OnAmendAdapterOnClickListener onClickListener;

    public void setOnClickListener(OnAmendAdapterOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public static interface OnAmendAdapterOnClickListener {
        public void onClick(int position);
    }
}
