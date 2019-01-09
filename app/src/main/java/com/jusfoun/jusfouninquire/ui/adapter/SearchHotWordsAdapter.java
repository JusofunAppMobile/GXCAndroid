package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.HotWordItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * SearchHotWordsAdapter
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/9
 * @Description :搜索热词列表适配器
 */
public class SearchHotWordsAdapter extends BaseAdapter{
    private Context mContext;
    private List<HotWordItemModel> mData;

    public SearchHotWordsAdapter(Context mContext) {
        this.mContext = mContext;
        this.mData = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_search_hot, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.update(position);
        return convertView;
    }

    public void refresh(List<HotWordItemModel> data){
        this.mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder{
        private HotWordItemModel data;
        private TextView mHotWord;
        public ViewHolder(View view) {
            mHotWord = (TextView) view.findViewById(R.id.hot_content);
        }

        public HotWordItemModel getData() {
            return data;
        }

        public void setData(HotWordItemModel data) {
            this.data = data;
        }

        public void update(int position){
            setData(mData.get(position));
            if (data != null){
                mHotWord.setText(Html.fromHtml(data.getHotword()));
                data.setSearchkey(mHotWord.getText().toString());
            }

        }
    }
}
