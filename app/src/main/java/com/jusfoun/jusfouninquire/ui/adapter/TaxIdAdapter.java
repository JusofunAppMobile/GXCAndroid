package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.model.TaxationItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Author  zyp
 * CreateDate 2015/11/10.
 * Description  查税号adapter
 */
public class TaxIdAdapter extends BaseAdapter {

    private Context mContext;
    private List<TaxationItemModel> mList = new ArrayList<>();
    public TaxIdAdapter(Context context) {
        mContext = context;
    }


    public List<TaxationItemModel> getDatas(){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_taxid, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.update(mList.get(position));
        return convertView;
    }

    public void refresh(List<TaxationItemModel> data){
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }
    public void addData(List<TaxationItemModel> data){
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder{
        public TextView numText;
        public TextView companyText;

        public TaxationItemModel model;

        public ViewHolder(View view) {
            numText = (TextView) view.findViewById(R.id.text_num);
            companyText = (TextView) view.findViewById(R.id.text_company);
        }


        public void update(TaxationItemModel model){
            this.model=model;
            companyText.setText(model.companyname);
            numText.setText(model.identifierid);
        }
    }
}
