package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.DisHonestItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Albert on 2015/11/11.
 * Mail : lbh@jusfoun.com
 * TODO :搜索失信适配器
 */
public class SearchDishonestAdapter extends BaseAdapter {
    private Context mContext;
    private List<DisHonestItemModel> mList;
    public SearchDishonestAdapter(Context context) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.search_dishonest_item_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.update(position);
        return convertView;
    }

    public void refresh(List<DisHonestItemModel> data){
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }
    public void cleanAllData(){
        mList.clear();
        notifyDataSetChanged();
    }

    public void addData(List<DisHonestItemModel> data){
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder{
        private TextView mName,mIDValue,mLocationValue,mLegalNumber;
        private DisHonestItemModel data;
        private ImageView id_image;
        public ViewHolder(View view) {
            mName = (TextView)view.findViewById(R.id.dishonest_name);
            mIDValue = (TextView)view.findViewById(R.id.id_value);
            mLocationValue = (TextView)view.findViewById(R.id.location_value);
            mLegalNumber = (TextView)view.findViewById(R.id.number_value);
            id_image= (ImageView) view.findViewById(R.id.id_image);
        }

        public DisHonestItemModel getData() {
            return data;
        }

        public void setData(DisHonestItemModel data) {
            this.data = data;
        }

        public void update(int position){
            setData(mList.get(position));
            if (data==null)
                return;
            if ("0".equals(data.getType())){
                id_image.setImageResource(R.mipmap.icon_dis_person);
            }else{
                id_image.setImageResource(R.mipmap.icon_dis_company);
            }
            mName.setText(Html.fromHtml(data.getName()));
            mIDValue.setText(data.getCredentials());
            mLocationValue.setText(data.getLocation());
            mLegalNumber.setText(data.getNumbering());
        }
    }
}
