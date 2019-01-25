package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.CompanyDetailMenuModel;
import com.jusfoun.jusfouninquire.ui.constant.CompanyDetailTypeConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/10.
 * Description
 */
public class CompanyMenuAdapter extends RecyclerView.Adapter<CompanyMenuAdapter.CompanyHolder> implements CompanyDetailTypeConstant {

    private Context context;
    private List<CompanyDetailMenuModel> list;

    public CompanyMenuAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public CompanyMenuAdapter.CompanyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CompanyHolder(LayoutInflater.from(context).inflate(R.layout.item_company_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(CompanyMenuAdapter.CompanyHolder holder, int position) {
        holder.updateView(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void refresh(List<CompanyDetailMenuModel> menuList) {
        if (menuList == null) {
            return;
        }
        this.list.clear();
        for (CompanyDetailMenuModel model : menuList) {
            if (types.contains(model.getType())) {
                this.list.add(model);
            }
        }
        if (list.size() % 4 != 0) {
            int last = 4 - list.size() % 4;
            for (int i = 0; i < last; i++)
                list.add(new CompanyDetailMenuModel());
        }
        notifyDataSetChanged();
    }


    class CompanyHolder extends RecyclerView.ViewHolder {

        private TextView txt, countText;
        private SimpleDraweeView img;
        private View view;
        private View vWhite;

        public CompanyHolder(View itemView) {
            super(itemView);
            view = itemView;
            txt = itemView.findViewById(R.id.company_menu_txt);
            img = itemView.findViewById(R.id.company_menu_img);
            countText = itemView.findViewById(R.id.text_count);
            vWhite = itemView.findViewById(R.id.vWhite);
        }

        public void updateView(final CompanyDetailMenuModel model, final int position) {

            if (TextUtils.isEmpty(model.getMenuname())) { // 填充空白item
                vWhite.setVisibility(View.VISIBLE);
                view.setEnabled(false);
                return;
            }
            vWhite.setVisibility(View.GONE);

            if (!TextUtils.isEmpty(model.getMenuname()))
                this.txt.setText(model.getMenuname());


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(position, model.getUmeng());
                }
            });

            view.setEnabled("1".equals(model.getHasData()));

            img.setImageURI(Uri.parse(model.getIcon()));
            if (!TextUtils.isEmpty(model.getCount()) && Integer.valueOf(model.getCount()) <= 99) {
                countText.setText(model.getCount());
            } else {
                countText.setText("99+");
            }

        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String umeng);
    }
}
