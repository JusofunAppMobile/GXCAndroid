package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.model.PatentModel;

import java.util.ArrayList;
import java.util.List;

public class PatentAdapter extends BaseAdapter {

    private Context mContext;
    private List<PatentModel> mList = new ArrayList<>();
    public PatentAdapter(Context context) {
        mContext = context;
    }


    public void addDatas(List<PatentModel> data){
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public List<PatentModel> getDatas(){
        return mList;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_patent, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.update(position);
        return convertView;
    }

    public void cleanAllData(){
        mList.clear();
        notifyDataSetChanged();
    }

    public void addData(List<PatentModel> data){
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder{
        public TextView tvTitle;
        public TextView tvType;
        public TextView tvTime;

        public ViewHolder(View view) {
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvType = (TextView) view.findViewById(R.id.tvType);
            tvTime = (TextView) view.findViewById(R.id.tvTime);
        }

        public void update(int position){
            PatentModel model = mList.get(position);
            tvTitle.setText(model.title);
            tvType.setText(model.patType);
            tvTime.setText(model.pubDate);
        }
    }
}
