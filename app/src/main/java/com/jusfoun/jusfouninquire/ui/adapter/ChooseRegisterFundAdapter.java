package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.ChooseRegisterFundItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Albert on 2015/11/18.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:高级搜索选择注册资金适配器
 */
public class ChooseRegisterFundAdapter extends BaseAdapter {
    private Context mContext;
    private List<ChooseRegisterFundItemModel> mList;

    public ChooseRegisterFundAdapter(Context mContext) {
        this.mContext = mContext;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.choose_register_fund_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.update(position);
        return convertView;
    }

    public void refresh(List<ChooseRegisterFundItemModel> data){
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder{
        private ChooseRegisterFundItemModel data;
        private TextView mValue;
        private CheckBox mCheckBox;
        private RelativeLayout mRowLayout;
        public ViewHolder(View view) {
            mValue = (TextView)view.findViewById(R.id.fund_value);
            mCheckBox = (CheckBox)view.findViewById(R.id.checkbox);
            mRowLayout = (RelativeLayout) view.findViewById(R.id.row_layout);
        }

        public ChooseRegisterFundItemModel getData() {
            return data;
        }

        public void setData(ChooseRegisterFundItemModel data) {
            this.data = data;
        }

        public void update(int position){
            setData(mList.get(position));
            mValue.setText(data.getValue());
            if (data.isChoosed()){
                mCheckBox.setChecked(true);
                mRowLayout.setBackgroundColor(mContext.getResources().getColor(R.color.activity_background));
            }else {
                mCheckBox.setChecked(false);
                mRowLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
        }
    }
}
