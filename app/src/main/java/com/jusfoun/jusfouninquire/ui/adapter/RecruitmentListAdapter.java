package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.model.RecruitmentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Author  zyp
 * CreateDate 2015/11/10.
 * Description  招聘adapter
 */
public class RecruitmentListAdapter extends BaseAdapter {

    private Context mContext;
    private List<RecruitmentModel.RecruitmentItemModel> mList = new ArrayList<>();

    public RecruitmentListAdapter(Context context) {
        mContext = context;
    }


    public List<RecruitmentModel.RecruitmentItemModel> getDatas() {
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
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_search_recruitment, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.update(mList.get(position));
        return convertView;
    }

    public void refresh(List<RecruitmentModel.RecruitmentItemModel> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<RecruitmentModel.RecruitmentItemModel> data) {
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder {

        protected TextView textName;
        protected TextView textSalary;
        protected TextView textDate;
        protected TextView textAddress;
        protected TextView textExperience;
        protected TextView companyText;
        public RecruitmentModel.RecruitmentItemModel model;

        public ViewHolder(View rootView) {
            textName = (TextView) rootView.findViewById(R.id.text_name);
            textSalary = (TextView) rootView.findViewById(R.id.text_salary);
            textDate = (TextView) rootView.findViewById(R.id.text_date);
            textAddress = (TextView) rootView.findViewById(R.id.text_address);
            textExperience = (TextView) rootView.findViewById(R.id.text_experience);
            companyText = (TextView) rootView.findViewById(R.id.text_company);

        }


        public void update(RecruitmentModel.RecruitmentItemModel model) {
            this.model= model;
            textName.setText(Html.fromHtml(model.job));
            textSalary.setText(Html.fromHtml(model.salary));
            textDate.setText(model.publishDate);
            textAddress.setText(model.workPlace);
            textExperience.setText(model.jobExperience);
            companyText.setText(model.companyName);
        }

    }
}
