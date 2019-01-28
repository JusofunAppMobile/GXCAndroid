package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.model.JobModel;

import java.util.ArrayList;
import java.util.List;

public class ChooseAdapter extends BaseAdapter{

    private List<JobModel> mList;
    private Context mContext;

    public ChooseAdapter(Context context){
        this.mContext = context;
        mList = new ArrayList<>();
    }
    
    public void refresh(List<JobModel> data){
        mList.clear();
        mList.addAll(data);
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
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.choose_adapter_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.update(position);
        return convertView;
    }
    
    
    public class ViewHolder{
        TextView nameText,checkedText;
        ImageView arrowImage;
        JobModel data;

        public ViewHolder(View view) {
            nameText = (TextView) view.findViewById(R.id.item_industry_textview_name);
            checkedText = (TextView) view.findViewById(R.id.item_checkedText);
            arrowImage = (ImageView) view.findViewById(R.id.item_arrow_image);
        }

        public JobModel getData() {
            return data;
        }

        public void setData(JobModel data) {
            this.data = data;
        }

        public void update(int position){
            JobModel model = mList.get(position);
            setData(model);
            if (!TextUtils.isEmpty(model.getHaschild())){
                if(model.getHaschild().equals("0")){
                    arrowImage.setVisibility(View.GONE);
                }else {
                    arrowImage.setVisibility(View.VISIBLE);
                }
            }

            nameText.setText(model.getName());
        }
    }

    
}
