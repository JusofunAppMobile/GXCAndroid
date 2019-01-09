package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.SearchContactListModel;

import java.util.ArrayList;
import java.util.List;

import netlib.util.AppUtil;

/**
 * @author zhaoyapeng
 * @version create time:18/5/717:20
 * @Email zyp@jusfoun.com
 * @Description ${企业通讯录 列表adapter}
 */
public class ContactsListAdapter extends BaseAdapter {
    private Context mContext;
    private List<SearchContactListModel.DataBean> list;

    public ContactsListAdapter(Context mContext) {
        this.mContext = mContext;
        list = new ArrayList<>();
    }

    @Override
    public int getCount() {
        if(list.size()>5){
            return 5;
        }
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
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_contact, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.update(list.get(position), position);
        return convertView;
    }

    public void refresh(List<SearchContactListModel.DataBean> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView nameText, phoneText, timeText;

        public ViewHolder(View view) {
            nameText = (TextView) view.findViewById(R.id.text_company_name);
            phoneText = (TextView) view.findViewById(R.id.text_phone);
            timeText = (TextView) view.findViewById(R.id.text_time);
        }

        public void update(SearchContactListModel.DataBean model, int position) {
            nameText.setText(model.name);
            if (model.phoneArr != null&&model.phoneArr.size()>0) {
                String phone = model.phoneArr.get(0);
                for (int i = 1; i < model.phoneArr.size(); i++) {
                    phone += "\n"+model.phoneArr.get(i);
                    if(i>=3){
                        phone = "...";
                        break;
                    }

                }

                phoneText.setText(phone);
            }


            if(!TextUtils.isEmpty(model.establishDate)){
                timeText.setText(model.establishDate);
            }else{
                timeText.setText("企业未公示");
            }
        }
    }
}
