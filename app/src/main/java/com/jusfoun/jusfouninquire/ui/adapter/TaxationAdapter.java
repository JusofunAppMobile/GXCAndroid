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
import com.jusfoun.jusfouninquire.net.model.BrandModel;
import com.jusfoun.jusfouninquire.net.model.TaxationItemModel;
import com.jusfoun.jusfouninquire.net.model.TaxationModel;
import com.jusfoun.jusfouninquire.net.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

public class TaxationAdapter extends BaseAdapter {

    private Context mContext;
    private List<TaxationItemModel> mList = new ArrayList<>();
    public TaxationAdapter(Context context) {
        mContext = context;
    }

    public void addDatas(List<TaxationItemModel> data){
        mList.addAll(data);
        notifyDataSetChanged();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_taxation, null);
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

    public void addData(List<TaxationItemModel> data){
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder{
        public TextView numText;
        public TextView typeText;
        public TextView amountText;
        public TextView timeText;

        public ViewHolder(View view) {
            numText = (TextView) view.findViewById(R.id.text_num);
            typeText = (TextView) view.findViewById(R.id.text_type);
            amountText = (TextView) view.findViewById(R.id.text_amount);
            timeText = (TextView) view.findViewById(R.id.text_time);
        }


        public void update(int position){
            TaxationItemModel model = mList.get(position);
            numText.setText(model.taxCode);
            amountText.setText(model.balance);
            if(!TextUtils.isEmpty(model.confirmDate)) {
                try {
                    long time = Long.parseLong(model.confirmDate);
                     timeText.setText(netlib.util.AppUtil.getTime(time));
                }catch (Exception e){

                }
            }else{
                timeText.setText("");
            }
            typeText.setText(model.entType);
        }
    }
}
