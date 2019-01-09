package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.SearchHistoryItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Albert on 2015/11/10.
 * Mail : lbh@jusfoun.com
 * TODO :
 */
public class SearchHistoryAdapter extends BaseAdapter {
    private Context mContext;
    private List<SearchHistoryItemModel> mSearchHistoryList;
    public SearchHistoryAdapter(Context context) {
        mContext = context;
        mSearchHistoryList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mSearchHistoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return mSearchHistoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.search_history_item_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.update(position);
        return convertView;
    }

    public void refresh(List<SearchHistoryItemModel> data){
        mSearchHistoryList.clear();
        mSearchHistoryList.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder{
        private SearchHistoryItemModel data;
        private TextView mKey,mScope;
        public ViewHolder(View view) {
            mKey = (TextView)view.findViewById(R.id.search_key);
            mScope = (TextView)view.findViewById(R.id.search_scope);
        }

        public SearchHistoryItemModel getData() {
            return data;
        }

        public void setData(SearchHistoryItemModel data) {
            this.data = data;
        }

        public void update(int position){
            setData(mSearchHistoryList.get(position));
            mKey.setText(data.getSearchkey());
            mScope.setText(data.getScopename());
        }
    }
}
