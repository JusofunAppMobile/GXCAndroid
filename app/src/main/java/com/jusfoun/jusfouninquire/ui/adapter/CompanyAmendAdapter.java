package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.model.CompanyDetailMenuModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/13.
 * Description
 */
public class CompanyAmendAdapter extends RecyclerView.Adapter<CompanyAmendAdapter.CompanyAmendHodler> {

    private Context context;
    private List<CompanyDetailMenuModel> list;

    private Map<String, CompanyDetailMenuModel> selectMap;

    public CompanyAmendAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
        selectMap = new HashMap<>();
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

    public void refresh(List<CompanyDetailMenuModel> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public List<CompanyDetailMenuModel> getList() {
        return list;
    }

    public Map<String, CompanyDetailMenuModel> getSelectMap() {
        return selectMap;
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

        public void update(final CompanyDetailMenuModel model, final int position) {

            if (selectMap.containsKey(model.getMenuid())) {
                txt.setSelected(true);
            } else {
                txt.setSelected(false);
            }

            txt.setText(model.menuName);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(position);
                    }else {


                        if (selectMap.containsKey(model.getMenuid())) {
                            txt.setSelected(false);
                            selectMap.remove(model.getMenuid());
                        } else {
                            txt.setSelected(true);
                            selectMap.put(model.getMenuid(), model);
                        }
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


    public void setSelectItem(int position){
        selectMap.clear();
        selectMap.put(list.get(position).getMenuid(),list.get(position));
        notifyDataSetChanged();
    }


}
