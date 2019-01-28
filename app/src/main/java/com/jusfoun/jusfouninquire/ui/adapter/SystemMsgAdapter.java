package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.net.model.SystemMsgItemModel;
import com.jusfoun.jusfouninquire.ui.widget.SwipeRightMenuLayout;
import com.siccredit.guoxin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lee
 * @version create time:2015/11/2414:52
 * @Email email
 * @Description $系统消息
 */

public class SystemMsgAdapter extends BaseAdapter{
    private Context mContext;
    private LayoutInflater inflater;
    private List<SystemMsgItemModel> mList;
    public SystemMsgAdapter(Context context){
        this.mContext = context;
        mList = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public void refresh(List<SystemMsgItemModel> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addMore(List<SystemMsgItemModel> list){
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
            convertView=inflater.inflate(R.layout.item_swipe_right_menu,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        SystemMsgItemModel model = mList.get(position);
        holder.updateView(model,position);
        return convertView;
    }




    /**
     * 删除消息成功之后进行刷新列表的操作
     * @param position
     * @param view
     */
    public void resetData(int position,View view){
        delect(position);
        if (view instanceof OnCloseMenuListener){
            ((OnCloseMenuListener) view).onClose();
        }
    }

    public class ViewHolder{
        private TextView msgName,msgContact,msgTime,delect;
        private View msgUnread;
        public SystemMsgItemModel mSysMsgModel;
        private View convertView;

        public ViewHolder(View convertView){

            this.convertView=convertView;

            View del=inflater.inflate(R.layout.layout_delect_menu,null);

            View view=inflater.inflate(R.layout.system_msg_list_item,null);

            msgName = (TextView) convertView.findViewById(R.id.msg_name);
            msgContact = (TextView) convertView.findViewById(R.id.msg_contact);
            msgTime = (TextView) convertView.findViewById(R.id.time);
            msgUnread = convertView.findViewById(R.id.msg_unread_indicator);
            delect= (TextView) convertView.findViewById(R.id.delect);



        }

        public void updateView(final SystemMsgItemModel model, final int position){
            mSysMsgModel = model;

            if (convertView instanceof SwipeRightMenuLayout){
                ((SwipeRightMenuLayout) convertView).setListener(new SwipeRightMenuLayout.OnClosedMenuListener() {
                    @Override
                    public void onClose() {
                        if (listener!=null) {
                            listener.onClose();
                        }
                    }
                });
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDeleteListener != null){
                        mDeleteListener.onStatusChanged(model,position);
                    }
                }
            });

            delect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDeleteListener != null){
                        mDeleteListener.onDelete(position,model.getId());
                    }
//                    delect(position);
//                    if (convertView instanceof OnCloseMenuListener){
//                        ((OnCloseMenuListener) convertView).onClose();
//                    }
                }
            });

            if(!TextUtils.isEmpty(model.getTitle())){
                msgName.setText(model.getTitle());
            }else {
                msgName.setText("");
            }
            if(!TextUtils.isEmpty(model.getContent())){
                msgContact.setVisibility(View.VISIBLE);
                msgContact.setText(model.getContent());
            }else {
                msgContact.setVisibility(View.GONE);
            }
            if(!TextUtils.isEmpty(model.getTime())){
                msgTime.setText(model.getTime());
            }else {
                msgTime.setText("");
            }

            msgUnread.setVisibility(model.isRead() ? View.INVISIBLE : View.VISIBLE);

        }
    }




    /**
     * 设置某条消息为已读状态
     * @param position
     */
    public void setRead(int position){
        if (mList != null && mList.get(position) != null){
            ((SystemMsgItemModel)mList.get(position)).setRead(true);
            notifyDataSetChanged();
        }
    }

    public interface OnCloseMenuListener{
        void onClose();
    }

    /**
     * 删除消息的接口
     */
    public interface OnDeleteListener{
        void onDelete(int position,String msgid);
        void onStatusChanged(SystemMsgItemModel model,int position);
    }




    private SwipeRightMenuLayout.OnClosedMenuListener listener;

    private OnDeleteListener mDeleteListener;

    public void setListener(SwipeRightMenuLayout.OnClosedMenuListener listener) {
        this.listener = listener;
    }

    public void setDeleteListener(OnDeleteListener mDeleteListener) {
        this.mDeleteListener = mDeleteListener;
    }
}
