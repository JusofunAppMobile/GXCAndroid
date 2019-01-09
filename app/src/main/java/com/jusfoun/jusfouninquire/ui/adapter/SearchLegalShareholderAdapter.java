package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.BusinessItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Albert on 2015/11/11.
 * Mail : lbh@jusfoun.com
 * TODO :搜索法人股东适配器
 */
public class SearchLegalShareholderAdapter extends BaseAdapter {
    private Context mContext;
    private List<BusinessItemModel> mList;
    public SearchLegalShareholderAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.search_legal_shareholder_item_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.update(position);
        return convertView;
    }

    public void refresh(List<BusinessItemModel> data){
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<BusinessItemModel> data){
        mList.addAll(data);
        notifyDataSetChanged();

    }

    public class ViewHolder{
        public ViewHolder(View view) {

        }

        public void update(int position){

        }
    }
}
