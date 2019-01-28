package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.model.AreaModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Albert on 2015/11/11.
 * Mail : lbh@jusfoun.com
 * TODO :选择搜索省份适配器
 */
public class SearchScopeAdapter extends BaseAdapter {
    private Context mContext;
    private List<AreaModel> mList;
    private String mCurrentScope;
    public SearchScopeAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
        mCurrentScope = "0";
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.search_scope_item_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.update(position);
        return convertView;
    }

    public void refresh(List<AreaModel> data){
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public void setSelected(String scope){
        mCurrentScope = scope;
    }

    public class ViewHolder{
        private TextView mScope;
        private AreaModel mData;
        public ViewHolder(View view) {
            mScope = (TextView)view.findViewById(R.id.scope);
        }

        public void update(int position){
            setmData(mList.get(position));
            mScope.setText(mList.get(position).getName());
            if ((mCurrentScope.equals(mList.get(position).getName())) || (mCurrentScope.equals(mList.get(position).getId()))){
                mScope.setBackgroundColor(mContext.getResources().getColor(R.color.activity_background));
            }else {
                mScope.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
        }

        public AreaModel getmData() {
            return mData;
        }

        public void setmData(AreaModel mData) {
            this.mData = mData;
        }
    }
}
