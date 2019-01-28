package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.model.BrandModel;

import java.util.ArrayList;
import java.util.List;

public class BrandAdapter extends BaseAdapter {

    private Context mContext;
    private List<BrandModel> mList = new ArrayList<>();
    public BrandAdapter(Context context) {
        mContext = context;
    }


    public void addDatas(List<BrandModel> data){
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public List<BrandModel> getDatas(){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_brand, null);
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

    public void addData(List<BrandModel> data){
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder{
        public TextView tvName;
        public TextView tvStatus;
        public TextView tvType;
        public SimpleDraweeView ivIcon;

        public ViewHolder(View view) {
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvStatus = (TextView) view.findViewById(R.id.tvStatus);
            tvType = (TextView) view.findViewById(R.id.tvType);
            ivIcon = (SimpleDraweeView) view.findViewById(R.id.ivIcon);
        }


        public void update(int position){
            BrandModel model = mList.get(position);
            tvName.setText(model.name);
            tvStatus.setText(model.stauts);
            tvType.setText(model.category);
            if(!TextUtils.isEmpty(model.imgPath)) {
                ivIcon.setImageURI(Uri.parse(model.imgPath));
            }else{
                Uri uri = Uri.parse("res://" +
                        mContext.getPackageName() +
                        "/" +R.mipmap.img_brand_def);
                ivIcon.setImageURI(uri);
            }
        }
    }
}
