package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.FocusedItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/20.
 * Description
 */
public class AttentionAdapter extends BaseAdapter {

    private Context mContext;
    private List<FocusedItemModel> list;
    public AttentionAdapter(Context mContext){
        this.mContext=mContext;
        list=new ArrayList<>();
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
        if (convertView==null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_attention, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.update(list.get(position));
        return convertView;
    }

    public void refresh(List<FocusedItemModel> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addMore(List<FocusedItemModel> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder{
        private TextView mCompanyName,mLegal,mCompanyState;
        private RelativeLayout isUpdateLayout;
        public FocusedItemModel mFocuseModel;

        public ViewHolder(View view){
            mCompanyName= (TextView) view.findViewById(R.id.attention_company_name);
            mLegal= (TextView) view.findViewById(R.id.attention_legal);
            mCompanyState= (TextView) view.findViewById(R.id.attention_company_state);
            isUpdateLayout = (RelativeLayout) view.findViewById(R.id.update_layout);
        }

        public void update(FocusedItemModel model){
            mFocuseModel = model;
            if(!TextUtils.isEmpty(model.getCompanyname())){
                mCompanyName.setText(model.getCompanyname());
            }else {
                mCompanyName.setText("");
            }

            if(!TextUtils.isEmpty(model.getLegal())){
                mLegal.setText(model.getLegal());
            }/*else{
                mLegal.setText("法人：-");
            }*/
            if(!TextUtils.isEmpty(model.getCompanystate())){
                mCompanyState.setText(""+model.getCompanystate());
            }else {
                mCompanyState.setText("");
            }

            if(model.getIsupdate() == 1){
                isUpdateLayout.setVisibility(View.VISIBLE);
            }else {
                isUpdateLayout.setVisibility(View.GONE);
            }
        }
        }

}
