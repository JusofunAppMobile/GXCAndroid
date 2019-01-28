package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.model.CompanyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lee
 * @version create time:2015年6月4日_下午2:41:49
 * @Description TODO
 */

public class SetCompanyAdapter extends BaseAdapter{

	private Context mContext;
	private List<CompanyModel> mlist;
	private LayoutInflater inflater;
	public SetCompanyAdapter(Context context){
		this.mContext = context;
		mlist = new ArrayList<CompanyModel>();
		inflater = LayoutInflater.from(mContext);
	}
	
	public void refresh(List<CompanyModel> list){
		mlist.clear();
		mlist.addAll(list);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return mlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.companylist_item, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		CompanyModel model = mlist.get(position);
		holder.update(model);
		
		return convertView;
		
	}

	public void addList(List<CompanyModel> list){
		if (list==null||mlist==null)
			return;
		mlist.addAll(list);
		notifyDataSetChanged();
	}
	
	class ViewHolder{
		private TextView companyName;
		public CompanyModel company;
		public ViewHolder(View convertView){
			companyName = (TextView) convertView.findViewById(R.id.companyName);
		}
		
		public void update(CompanyModel model){
			company = model;
			//companyName.setText(model.getCompanyname());
			companyName.setText(Html.fromHtml(model.getCompanyname()));
		}
	}

}
