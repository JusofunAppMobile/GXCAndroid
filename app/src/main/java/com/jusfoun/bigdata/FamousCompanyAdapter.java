package com.jusfoun.bigdata;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.jusfoun.jusfouninquire.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lee
 * @version create time:2016/4/1314:52
 * @Email email
 * @Description 名企列表adapter
 */

public class FamousCompanyAdapter extends BaseAdapter {
    private Context mContext;
    private List<FamousCompanyModel> mList;
    private LayoutInflater inflater;
    private boolean isCenter;

    public FamousCompanyAdapter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
        inflater = LayoutInflater.from(mContext);

    }

    public void refresh(List<FamousCompanyModel> list, boolean isCenter){
        mList.clear();
        mList.addAll(list);
        this.isCenter = isCenter;
        notifyDataSetChanged();
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
        if(convertView == null){
            convertView = inflater.inflate(R.layout.famous_list_item,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        FamousCompanyModel model = mList.get(position);
        holder.update(model);
        return convertView;
    }

    public class ViewHolder{
        private TextView entNameText;
        public FamousCompanyModel model;

        public ViewHolder(View convertView){
            entNameText = (TextView) convertView.findViewById(R.id.famous_company_text);
        }

        public void update(FamousCompanyModel model){
            this.model = model;
            if(!TextUtils.isEmpty(model.getEntname())){
                entNameText.setText(model.getEntname());
            }
            if(isCenter){
                entNameText.setGravity(Gravity.CENTER);
            }else {
                entNameText.setGravity(Gravity.LEFT| Gravity.CENTER_VERTICAL);
            }
        }


    }
}
