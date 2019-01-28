package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
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
 * Created by Albert on 2015/11/17.
 * Mail : lbh@jusfoun.com
 * TODO :
 */
public class ChooseAreaAdapter extends BaseAdapter {
    private Context mContext;
    private List<AreaModel> mList;
    private String mSelectedID;
    private int mLevel;
    public ChooseAreaAdapter(Context context) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.choice_area_list_item, null);
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

    public void setSelectedID(String mSelectedID,int mLevel) {
        this.mSelectedID = mSelectedID;
        this.mLevel = mLevel;
        notifyDataSetChanged();
    }

    public class ViewHolder{
        private AreaModel data;
        private TextView mAreaName;
        public ViewHolder(View view) {
            mAreaName = (TextView)view.findViewById(R.id.areaName);
        }

        public AreaModel getData() {
            return data;
        }

        public void setData(AreaModel data) {
            this.data = data;
        }

        public void update(int position){
            setData(mList.get(position));
            mAreaName.setText(data.getName());
            if (!TextUtils.isEmpty(mSelectedID)){
                if (!mSelectedID.equals(data.getId())){
                    if (mLevel == 1){
                        mAreaName.setBackgroundColor(mContext.getResources().getColor(R.color.area_level_three_color));
                    }else if (mLevel == 2){
                        mAreaName.setBackgroundColor(mContext.getResources().getColor(R.color.area_level_two_color));
                    }else {
                        mAreaName.setBackgroundColor(mContext.getResources().getColor(R.color.area_level_one_color));
                    }
                }else {
                    mAreaName.setBackgroundColor(mContext.getResources().getColor(R.color.area_level_one_color));
                }
            }


        }
    }

}
