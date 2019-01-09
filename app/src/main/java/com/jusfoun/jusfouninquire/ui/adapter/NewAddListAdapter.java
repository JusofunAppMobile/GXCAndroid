package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.HomeDataItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:18/1/1015:38
 * @Email zyp@jusfoun.com
 * @Description ${新增企业 页面 adapter}
 */
public class NewAddListAdapter extends BaseAdapter {
    private Context mContext;
    private List<HomeDataItemModel> list;

    public NewAddListAdapter(Context mContext){
        this.mContext=mContext;
        list = new ArrayList<>();
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
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_new_add_list_more, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.update(list.get(position), position);
        return convertView;
    }

    public class ViewHolder {
        private TextView mCompanyName, addressText, monetText, mCompanyState;
        public HomeDataItemModel data;
        private TextView legalText, dateText;
        private ImageView collectImg;

        public ViewHolder(View view) {
            mCompanyName = (TextView) view.findViewById(R.id.company_name);
            addressText = (TextView) view.findViewById(R.id.company_address);
            monetText = (TextView) view.findViewById(R.id.company_money);
            mCompanyState = (TextView) view.findViewById(R.id.company_state);
            legalText = (TextView) view.findViewById(R.id.company_legal);
            dateText = (TextView) view.findViewById(R.id.company_date);

        }

        public HomeDataItemModel getData() {
            return data;
        }

        public void update(HomeDataItemModel model, int position) {
            this.data = model;

            if (data != null) {
                if (!TextUtils.isEmpty(data.getCompanylightname())) {
                    mCompanyName.setText(Html.fromHtml(data.getCompanylightname()));
                } else {
                    mCompanyName.setText(data.getCompanyname());
                }
                if (!TextUtils.isEmpty(data.getLocation()) && !"未公布".equals(data.getLocation())) {
                    addressText.setText(data.getLocation());
                    addressText.setVisibility(View.VISIBLE);
                } else {
                    addressText.setVisibility(View.GONE);
                }

                if (!TextUtils.isEmpty(data.getFunds()) && !"未公布".equals(data.getFunds())) {
                    monetText.setText(data.getFunds());
                    monetText.setVisibility(View.VISIBLE);
                } else {
                    monetText.setVisibility(View.GONE);
                }

                //TODO 可能需要根据类型进行不同状态的显示
                mCompanyState.setText(data.getCompanystate());
                legalText.setText(data.legalPerson);
                dateText.setText(data.PublishTime);
            }
        }
    }

    public void refresh(List<HomeDataItemModel> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<HomeDataItemModel> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }
}
