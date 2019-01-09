package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.util.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lee
 * @version create time:2015/10/2010:59
 * @Email email
 * @Description ${TODO}
 */

public class MapAppAdapter extends BaseAdapter {

    private List<AppInfo> mlist;

    private Context mContext;

    private LayoutInflater inflater;

    public MapAppAdapter(Context context){
        this.mContext = context;
        mlist = new ArrayList<AppInfo>();
        inflater = LayoutInflater.from(context);
    }

    public void refresh(List<AppInfo> list){
        mlist.clear();
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder  = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_choice_dialog,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.update(mlist.get(position));

        return convertView;
    }

    public class ViewHolder {
        private ImageView imageIcon;
        private TextView appName;
        private View itemView;
        public AppInfo mAppInfo;

        public ViewHolder(View view){
            this.itemView = view;

            imageIcon = (ImageView) itemView.findViewById(R.id.image_icon);
            appName = (TextView) itemView.findViewById(R.id.appName);
        }

        public void update(AppInfo appInfo){
            mAppInfo = appInfo;
            imageIcon.setImageDrawable(appInfo.getAppIcon());
            appName.setText(appInfo.getAppName());
        }
    }
}
