package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.FocusedItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Albert on 2015/11/10.
 * Mail : lbh@jusfoun.com
 * TODO :我的关注列表适配器
 */
public class MyFocusesAdapter extends BaseAdapter {
    private Context mContext;
    private List<FocusedItemModel> mFocusesList;
    public MyFocusesAdapter(Context context) {
        mContext = context;
        mFocusesList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mFocusesList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFocusesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.focus_company_item_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.update(position);
        return convertView;
    }

    public void refresh(List<FocusedItemModel> data){
        mFocusesList.clear();
        mFocusesList.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<FocusedItemModel> data){
        mFocusesList.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder{
        private FocusedItemModel data;
        public ViewHolder(View view) {

        }

        public FocusedItemModel getData() {
            return data;
        }

        public void setData(FocusedItemModel data) {
            this.data = data;
        }

        public void update(int position){
            setData(mFocusesList.get(position));
        }
    }

}
