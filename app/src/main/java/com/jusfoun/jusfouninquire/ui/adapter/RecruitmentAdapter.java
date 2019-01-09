package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.BiddingListModel;
import com.jusfoun.jusfouninquire.net.model.RecruitListModel;
import com.jusfoun.jusfouninquire.net.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:17/9/717:10
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class RecruitmentAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context mContext;
    private List<RecruitListModel.RecruitItemModel> list;
    public RecruitmentAdapter(Context mContext){
        this.mContext=mContext;
        layoutInflater = LayoutInflater.from(mContext);
        list = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BinddingViewHolder holder = null;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_recruit, null);
            holder =new  BinddingViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (BinddingViewHolder) convertView.getTag();
        }
        holder.update(list.get(position));
        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    class BinddingViewHolder {
        private TextView titleText,areaText,timeText,salaryText,educationText,experienceText;

        public BinddingViewHolder(View itemView) {
            titleText = (TextView) itemView.findViewById(R.id.text_title);
            areaText = (TextView) itemView.findViewById(R.id.text_area);
            timeText = (TextView) itemView.findViewById(R.id.text_time);
            salaryText = (TextView) itemView.findViewById(R.id.text_salary);
            educationText = (TextView) itemView.findViewById(R.id.text_education);
            experienceText = (TextView) itemView.findViewById(R.id.text_experience);
        }

        public void update(final RecruitListModel.RecruitItemModel model){
            titleText.setText(model.job);
            areaText.setText(model.workPlace);
            timeText.setText(model.publishDate);
            salaryText.setText(model.salary);
            educationText.setText(model.lowEducationBackgroud);
            experienceText.setText(model.jobExperience);
        }
    }

    public void refresh(List<RecruitListModel.RecruitItemModel> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<RecruitListModel.RecruitItemModel> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }
}
