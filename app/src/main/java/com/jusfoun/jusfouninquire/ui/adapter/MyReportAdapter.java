package com.jusfoun.jusfouninquire.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.model.MyReportModel;
import com.jusfoun.jusfouninquire.ui.activity.BaseInquireActivity;
import com.jusfoun.jusfouninquire.ui.widget.EmailSendDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description $我的报告
 */

public class MyReportAdapter extends BaseAdapter{
    private BaseInquireActivity mContext;
    private LayoutInflater inflater;
    private List<MyReportModel.DataResultBean> mList;
    public MyReportAdapter(BaseInquireActivity context){
        this.mContext = context;
        mList = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public void refresh(List<MyReportModel.DataResultBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addMore(List<MyReportModel.DataResultBean> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void delect(int position){
        mList.remove(position);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mList.size() == 0 ? 0: mList.size();
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
            convertView=inflater.inflate(R.layout.item_my_report,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        MyReportModel.DataResultBean model = mList.get(position);
        holder.updateView(model,position);
        return convertView;
    }


    public class ViewHolder{
        private View convertView;
        private View vSend;
        private TextView tvTime;
        private TextView tvName;

        public ViewHolder(View convertView){

            this.convertView=convertView;
            vSend = convertView.findViewById(R.id.vSend);
            tvName = (TextView) convertView.findViewById(R.id.tvName);
            tvTime = (TextView) convertView.findViewById(R.id.tvTime);

        }

        public void updateView(final MyReportModel.DataResultBean model, final int position){

            tvName.setText(model.entName);
            tvTime.setText(model.reportTime);

            vSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new EmailSendDialog(mContext, model.entName, model.entId,EmailSendDialog.TYPE_REPORT_ONE).show();
                }
            });
        }
    }

    public boolean isDataEmpty() {
        return mList == null || mList.isEmpty();
    }
}
