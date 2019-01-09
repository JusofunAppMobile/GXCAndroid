package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.HotConsultingItemModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsq on 2016/9/21.
 * 热门资讯的适配器
 */

public class HotConsultingListAdapter extends BaseAdapter {
    private Context mContext;
    private List<HotConsultingItemModel> list;

    public HotConsultingListAdapter(Context mContext){
        this.mContext=mContext;
        this.list=new ArrayList<>();
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_list_hotconsulting,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else
            viewHolder= (ViewHolder) convertView.getTag();
        viewHolder.update(list.get(position));
        return convertView;
    }
    public void refresh(List<HotConsultingItemModel> list){
        if (list==null)
            return;
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<HotConsultingItemModel> list){
        if (list==null)
            return;
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    public class ViewHolder{
        private TextView mTitle_important,mReader_count;
        private SimpleDraweeView mPictuer;
        public HotConsultingItemModel data;
        public ViewHolder(View view){
            mPictuer= (SimpleDraweeView) view.findViewById(R.id.pictuer);
            mTitle_important= (TextView) view.findViewById(R.id.title_important);
            mReader_count= (TextView) view.findViewById(R.id.reader_count);
        }
        public HotConsultingItemModel getData(){
            return data;
        }
        public void update(HotConsultingItemModel model){
            this.data = model;
            mPictuer.setImageURI(Uri.parse(model.getNewsimgurl()));
            mTitle_important.setText(model.getNewstitle());
            mReader_count.setText(model.getNewreadcount()+"阅读");
            //这个地方注意修改下计量单位
//            if(model.getNewreadcount()<10000){
//                mReader_count.setText(model.getNewreadcount()+"阅读");
//            }else{
//                mReader_count.setText(new DecimalFormat("#.00").format(Double.parseDouble(model.getNewreadcount()/10000+""))+"万阅读");
//            }

        }

        }

}
