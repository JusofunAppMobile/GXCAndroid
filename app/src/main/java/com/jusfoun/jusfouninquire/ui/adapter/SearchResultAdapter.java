package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.HomeDataItemModel;
import com.jusfoun.jusfouninquire.net.model.SearchContactListModel;
import com.jusfoun.jusfouninquire.net.model.SearchHistoryItemModel;
import com.jusfoun.jusfouninquire.net.util.TouchUtil;
import com.jusfoun.jusfouninquire.ui.activity.CompanyDetailActivity;
import com.jusfoun.jusfouninquire.ui.activity.ShareHolderActivity;
import com.jusfoun.jusfouninquire.ui.view.ContactsOpenView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jusfoun.jusfouninquire.R.id.company_info;

/**
 * SearchResultAdapter
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/15
 * @Description :搜索结果适配器
 */
public class SearchResultAdapter extends BaseAdapter {
    private Context mContext;
    private List<HomeDataItemModel> mData;
    private List<SearchContactListModel.DataBean> mData2;
    private String mSearchType;// 0 正常搜索 1 通讯录
    public static int TYPE_NORMAL = 0;
    public static int TYPE_CONSTANCTS = 1;
    public static int TYPE_SHAREHOLDER = 2; // 股东穿透

    private int selectStateType = 0;// 0，不显示  1 显示
    public static int TYPE_STATE_SELECT_NO = 0;
    public static int TYPE_STATE_SELECT_SHOW = 1;
    private boolean isSelectAll = false;

    private Map<String, String> compaySelectMap, contactsSelectMap;

    public SearchResultAdapter(Context mContext, String mSearchType) {
        this.mContext = mContext;
        mData = new ArrayList<>();
        mData2 = new ArrayList<>();
        this.mSearchType = mSearchType;
        compaySelectMap = new HashMap<>();
        contactsSelectMap = new HashMap<>();
    }

    private boolean isContacts() {
        return SearchHistoryItemModel.SEARCH_CONTACT.equals(mSearchType);
    }

    @Override
    public int getCount() {
        if (isContacts())
            return mData2.size();
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        if (isContacts())
            return mData2.get(i);
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        if (getItemViewType(position) == TYPE_CONSTANCTS) {
            ConstactsViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_search_result_constancts, null);
                holder = new ConstactsViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ConstactsViewHolder) convertView.getTag();
            }
            holder.update(position);
            return convertView;
        } else if (getItemViewType(position) == TYPE_SHAREHOLDER) {
            ShareHolderViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_search_shareholder, null);
                holder = new ShareHolderViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ShareHolderViewHolder) convertView.getTag();
            }
            holder.update(position);
            return convertView;
        } else {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_search_result, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.update(position);
            return convertView;
        }
    }

//    public void setSearchType(String mSearchType) {
//        this.mSearchType = mSearchType;
//    }

    public void refresh(List<HomeDataItemModel> data) {
        compaySelectMap.clear();
        mData.clear();
        if (data != null)
            mData.addAll(data);
        notifyDataSetChanged();
    }

    public void refresh2(List<SearchContactListModel.DataBean> data) {
        compaySelectMap.clear();
        mData2.clear();
        if (data != null)
            mData2.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<HomeDataItemModel> data) {
        mData.addAll(data);
        setReportAllSelect();
        notifyDataSetChanged();
    }

    public void addData2(List<SearchContactListModel.DataBean> data) {
        mData2.addAll(data);
        setConstanctAllSelect();
        notifyDataSetChanged();
    }


    public class ViewHolder {
        private TextView mCompanyName, mCompanyInfo, mCompanyState, tvLegal, tvEstablish, tvRelate;
        private LinearLayout mRelateLayout;
        private View vTop;
        //        private View mDivider;
        public HomeDataItemModel model;
        private ImageView selectImg;
        private RelativeLayout rootLayout;
        private TextView preViewText;

        public ViewHolder(View view) {
            mCompanyName = (TextView) view.findViewById(R.id.company_name);
            mCompanyInfo = (TextView) view.findViewById(company_info);
            mCompanyState = (TextView) view.findViewById(R.id.company_state);
            tvLegal = (TextView) view.findViewById(R.id.tvLegal);
            tvEstablish = (TextView) view.findViewById(R.id.tvEstablish);
//            mRelated = (TextView) view.findViewById(R.id.related_text);
            mRelateLayout = (LinearLayout) view.findViewById(R.id.relate_layout);
            tvRelate = (TextView) view.findViewById(R.id.tvRelate);
            vTop = view.findViewById(R.id.vTop);
//            mDivider = view.findViewById(R.id.divider_view);
            selectImg = (ImageView) view.findViewById(R.id.img_select);
            rootLayout = (RelativeLayout) view.findViewById(R.id.layout_root);
            preViewText = (TextView) view.findViewById(R.id.text_report_preview);
        }

        public Object getData(int position) {
            return mData.get(position);
        }

        public void update(int position) {

            if (!isContacts()) {
                final HomeDataItemModel data = mData.get(position);
                model = data;
                tvLegal.setText(data.getLegal());
                mCompanyInfo.setText(data.getFunds());
                tvEstablish.setText(data.establish);
//            tvRelate.setText(data.relatednofont);

                if (!TextUtils.isEmpty(data.getRelated()))
                    tvRelate.setText(Html.fromHtml(data.getRelated()));

                mRelateLayout.setVisibility(TextUtils.isEmpty(data.getRelated()) ? View.GONE : View.VISIBLE);

                vTop.setBackgroundResource(mRelateLayout.getVisibility() == View.VISIBLE ? R.drawable.img_item_2 : R.drawable.img_item_bg);

                if (!TextUtils.isEmpty(data.getCompanylightname())) {
                    mCompanyName.setText(Html.fromHtml(data.getCompanylightname()));
                } else {
                    mCompanyName.setText(data.getCompanyname());
                }


                mCompanyState.setText(data.getCompanystate());


                if (TYPE_STATE_SELECT_NO == selectStateType) {
                    selectImg.setVisibility(View.GONE);
                    preViewText.setVisibility(View.GONE);
                } else if (TYPE_STATE_SELECT_SHOW == selectStateType) {
                    selectImg.setVisibility(View.VISIBLE);
                    preViewText.setVisibility(View.VISIBLE);
                    selectImg.setImageResource(R.drawable.img_constant_select_no);
                }
//                selectImg.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (compaySelectMap.containsKey(data.getCompanyid())) {
//                            compaySelectMap.remove(data.getCompanyid());
//                            selectImg.setImageResource(R.drawable.img_constant_select_no);
//                            if (callBack != null) {
//                                callBack.cancleSelectAll();
//                            }
//                            compaySelectMap.remove(data.getCompanyid());
//                        } else {
//
//                            Log.e("tag", "compaySelectMap=" + compaySelectMap.size());
//                            if (compaySelectMap.size() < 100) {
//                                compaySelectMap.put(data.getCompanyid(), "");
//                                selectImg.setImageResource(R.drawable.img_constant_select_yes);
//                            }
//
//                            if (callBack != null) {
//                                callBack.select();
//                            }
//                        }
//
//                    }
//                });

                if (compaySelectMap.containsKey(data.getCompanyid())) {
//                    data.isSelect = true;
                    selectImg.setImageResource(R.drawable.img_constant_select_yes);
                } else {
                    selectImg.setImageResource(R.drawable.img_constant_select_no);
                }
                TouchUtil.createTouchDelegate(selectImg, 40);


                rootLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TYPE_STATE_SELECT_SHOW == selectStateType) {
                            if (compaySelectMap.containsKey(data.getCompanyid())) {
                                compaySelectMap.remove(data.getCompanyid());
                                selectImg.setImageResource(R.drawable.img_constant_select_no);
                                if (callBack != null) {
                                    callBack.cancleSelectAll();
                                }
                                isSelectAll = false;
                            } else {
                                if (compaySelectMap.size() < 100) {
                                    compaySelectMap.put(data.getCompanyid(), "");
                                    selectImg.setImageResource(R.drawable.img_constant_select_yes);
                                }
                                if (callBack != null) {
                                    callBack.select();
                                }
                            }
                        } else {
                            Intent intent = new Intent(mContext, CompanyDetailActivity.class);
                            intent.putExtra(CompanyDetailActivity.COMPANY_ID, model.getCompanyid());
                            intent.putExtra(CompanyDetailActivity.COMPANY_NAME, model.getCompanyname());
                            mContext.startActivity(intent);
                        }
                    }
                });

                preViewText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callBack != null) {
                            callBack.preView(data);
                        }
                    }
                });

            }
        }
    }

    public class ConstactsViewHolder {
        private TextView mCompanyName, mCompanyInfo, mCompanyState, tvLegal, tvEstablish, tvRelate;
        private LinearLayout mRelateLayout;
        private View vTop;
        private ImageView selectImg;
        private TextView phoneText1, phoneText2, pointText;
        //        private View mDivider;
        public SearchContactListModel.DataBean data;
        private ContactsOpenView contactsOpenView;
        private RelativeLayout rootLayout;

        public ConstactsViewHolder(View view) {
            mCompanyName = (TextView) view.findViewById(R.id.company_name);
            mCompanyInfo = (TextView) view.findViewById(company_info);
            mCompanyState = (TextView) view.findViewById(R.id.company_state);
            tvLegal = (TextView) view.findViewById(R.id.tvLegal);
            tvEstablish = (TextView) view.findViewById(R.id.tvEstablish);
            mRelateLayout = (LinearLayout) view.findViewById(R.id.relate_layout);
            tvRelate = (TextView) view.findViewById(R.id.tvRelate);
            vTop = view.findViewById(R.id.vTop);
            selectImg = (ImageView) view.findViewById(R.id.img_select);
            phoneText1 = (TextView) view.findViewById(R.id.text_phone1);
            phoneText2 = (TextView) view.findViewById(R.id.text_phone2);
            pointText = (TextView) view.findViewById(R.id.text_point);
            contactsOpenView = (ContactsOpenView) view.findViewById(R.id.view_contacts);
            rootLayout = (RelativeLayout) view.findViewById(R.id.layout_root);
        }

        public SearchContactListModel.DataBean getData() {
            return data;
        }

        public void update(int position) {
            data = mData2.get(position);

//            mCompanyInfo.setText(data.getFunds());
//
//            if (!TextUtils.isEmpty(data.getRelated()))
//                tvRelate.setText(Html.fromHtml(data.getRelated()));

//            mRelateLayout.setVisibility(TextUtils.isEmpty(data.getRelated()) ? View.GONE : View.VISIBLE);
            mRelateLayout.setVisibility(View.GONE);
            vTop.setBackgroundResource(mRelateLayout.getVisibility() == View.VISIBLE ? R.drawable.img_item_2 : R.drawable.img_item_bg);


            if (!TextUtils.isEmpty(data.establishDate)) {
                tvEstablish.setText(data.establishDate);
            } else {
                tvEstablish.setText("企业未公示");
            }

            mCompanyName.setText(data.name);
            mCompanyState.setText(data.type);
            tvLegal.setText(data.legalPerson);

            if (TYPE_STATE_SELECT_NO == selectStateType) {
                selectImg.setVisibility(View.GONE);
            } else if (TYPE_STATE_SELECT_SHOW == selectStateType) {
                selectImg.setVisibility(View.VISIBLE);
                selectImg.setImageResource(R.drawable.img_constant_select_no);
            }

//            else if (TYPE_STATE_SELECT_SHOW_ALL == selectStateType) {
//                selectImg.setVisibility(View.VISIBLE);
//                selectImg.setImageResource(R.drawable.img_constant_select_yes);
//            } else if (TYPE_STATE_SELECT_SHOW_CANCLE == selectStateType) {
//                selectImg.setVisibility(View.VISIBLE);
//                selectImg.setImageResource(R.drawable.img_constant_select_no);
//            }
            phoneText2.setText("");
            phoneText1.setText("");

            if (data.phoneArr != null) {
                if (data.phoneArr.size() >= 1) {
                    phoneText1.setText(data.phoneArr.get(0));
                }
                if (data.phoneArr.size() >= 2) {
                    phoneText2.setText(data.phoneArr.get(1));
                }
            }

            if (data.isOpen) {
                pointText.setVisibility(View.GONE);
                phoneText2.setVisibility(View.VISIBLE);
                contactsOpenView.setOpen();
            } else {
                if (data.phoneArr != null && data.phoneArr.size() > 2) {
                    pointText.setVisibility(View.VISIBLE);
                    phoneText2.setVisibility(View.GONE);
                } else {
                    pointText.setVisibility(View.GONE);
                    if (data.phoneArr != null && data.phoneArr.size() == 2) {
                        phoneText2.setVisibility(View.VISIBLE);
                    }
                }
                contactsOpenView.setClose();
            }


            contactsOpenView.setCallBack(new ContactsOpenView.CallBack() {
                @Override
                public void openView() {
                    data.isOpen = true;
                    pointText.setVisibility(View.GONE);
                    phoneText2.setVisibility(View.VISIBLE);
                }

                @Override
                public void closeView() {
                    data.isOpen = false;
                    pointText.setVisibility(View.VISIBLE);
                    phoneText2.setVisibility(View.GONE);
                }
            });
            contactsOpenView.setData(data);


//            selectImg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (data.isSelect) {
//                        data.isSelect = false;
//                        isSelectAll = false;
//                        selectImg.setImageResource(R.drawable.img_constant_select_no);
//                        if (callBack != null) {
//                            callBack.cancleSelectAll();
//                        }
//                    } else {
//                        data.isSelect = true;
//                        selectImg.setImageResource(R.drawable.img_constant_select_yes);
//                    }
//
//                }
//            });

            if (contactsSelectMap.containsKey(data.id)) {
//                    data.isSelect = true;
                selectImg.setImageResource(R.drawable.img_constant_select_yes);
            } else {
                selectImg.setImageResource(R.drawable.img_constant_select_no);
            }
            TouchUtil.createTouchDelegate(selectImg, 40);


            rootLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TYPE_STATE_SELECT_SHOW == selectStateType) {
                        if (contactsSelectMap.containsKey(data.id)) {
                            contactsSelectMap.remove(data.id);
                            selectImg.setImageResource(R.drawable.img_constant_select_no);
                            if (callBack != null) {
                                callBack.cancleSelectAll();
                            }
                            isSelectAll = false;
                        } else {
                            if (contactsSelectMap.size() < 100) {
                                contactsSelectMap.put(data.id, "");
                                selectImg.setImageResource(R.drawable.img_constant_select_yes);
                            }
                            if (callBack != null) {
                                callBack.select();
                            }
                        }
                    } else {
                        Intent intent = new Intent(mContext, CompanyDetailActivity.class);
                        intent.putExtra(CompanyDetailActivity.COMPANY_ID, data.id);
                        intent.putExtra(CompanyDetailActivity.COMPANY_NAME, data.name);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }


    public class ShareHolderViewHolder {
        private TextView mCompanyName, mCompanyInfo, mCompanyState, tvLegal, tvEstablish;
        public HomeDataItemModel model;
        private ImageView selectImg;
        private View ivHolder;
        private RelativeLayout rootLayout;

        public ShareHolderViewHolder(View view) {
            mCompanyName = (TextView) view.findViewById(R.id.company_name);
            mCompanyInfo = (TextView) view.findViewById(company_info);
            mCompanyState = (TextView) view.findViewById(R.id.company_state);
            tvLegal = (TextView) view.findViewById(R.id.tvLegal);
            ivHolder = view.findViewById(R.id.ivHolder);
            tvEstablish = (TextView) view.findViewById(R.id.tvEstablish);
            selectImg = (ImageView) view.findViewById(R.id.img_select);
            rootLayout = (RelativeLayout) view.findViewById(R.id.layout_root);
        }

        public Object getData(int position) {
            return mData.get(position);
        }

        public void update(int position) {

            final HomeDataItemModel data = mData.get(position);
            model = data;
            tvLegal.setText(data.getLegal());
            mCompanyInfo.setText(data.getFunds());
            tvEstablish.setText(data.establish);

            if (!TextUtils.isEmpty(data.getCompanylightname())) {
                mCompanyName.setText(Html.fromHtml(data.getCompanylightname()));
            } else {
                mCompanyName.setText(data.getCompanyname());
            }

            data.ishasshareholder = true;
            ivHolder.setVisibility(data.ishasshareholder ? View.VISIBLE : View.INVISIBLE);

            ivHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(mContext, ShareHolderActivity.class);
                    intent.putExtra(ShareHolderActivity.COMPANY_ID, model.getCompanyid());
                    intent.putExtra(ShareHolderActivity.COMPANY_NAME, model.getCompanyname());
                    mContext.startActivity(intent);
                }
            });
            mCompanyState.setText(data.getCompanystate());


            if (TYPE_STATE_SELECT_NO == selectStateType) {
                selectImg.setVisibility(View.GONE);
            } else if (TYPE_STATE_SELECT_SHOW == selectStateType) {
                selectImg.setVisibility(View.VISIBLE);
                selectImg.setImageResource(R.drawable.img_constant_select_no);
            }

            if (compaySelectMap.containsKey(data.getCompanyid())) {
                selectImg.setImageResource(R.drawable.img_constant_select_yes);
            } else {
                selectImg.setImageResource(R.drawable.img_constant_select_no);
            }
            TouchUtil.createTouchDelegate(selectImg, 40);


            rootLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TYPE_STATE_SELECT_SHOW == selectStateType) {
                        if (compaySelectMap.containsKey(data.getCompanyid())) {
                            compaySelectMap.remove(data.getCompanyid());
                            selectImg.setImageResource(R.drawable.img_constant_select_no);
                            if (callBack != null) {
                                callBack.cancleSelectAll();
                            }
                            isSelectAll = false;
                        } else {
                            if (compaySelectMap.size() < 100) {
                                compaySelectMap.put(data.getCompanyid(), "");
                                selectImg.setImageResource(R.drawable.img_constant_select_yes);
                            }
                            if (callBack != null) {
                                callBack.select();
                            }
                        }
                    } else {
                        Intent intent = new Intent(mContext, CompanyDetailActivity.class);
                        intent.putExtra(CompanyDetailActivity.COMPANY_ID, model.getCompanyid());
                        intent.putExtra(CompanyDetailActivity.COMPANY_NAME, model.getCompanyname());
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private String getDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return sdf.format(calendar.getTime());
    }

    @Override
    public int getItemViewType(int position) {
        if (SearchHistoryItemModel.SEARCH_CONTACT.equals(mSearchType))
            return TYPE_CONSTANCTS;
        if (SearchHistoryItemModel.SEARCH_SHAREHOLDER_RIFT.equals(mSearchType))
            return TYPE_SHAREHOLDER;
        return TYPE_NORMAL;
    }

    /**
     * 设置选择图标 状态 type
     */
    public void setStateType(int type) {
        selectStateType = type;
    }

    public List<SearchContactListModel.DataBean> getList() {
        List<SearchContactListModel.DataBean> list = new ArrayList<>();

        if (mData2 != null) {
            for (int i = 0; i < mData2.size(); i++) {
                if (mData2.get(i) != null && contactsSelectMap.containsKey(mData2.get(i).id)) {
                    list.add(mData2.get(i));
                }
            }
        }

        return list;
    }

    public List<HomeDataItemModel> getShareholderList() {
        List<HomeDataItemModel> list = new ArrayList<>();

        if (mData != null) {
            for (int i = 0; i < mData.size(); i++) {
                if (mData.get(i) != null && compaySelectMap.containsKey(mData.get(i).getCompanyid())) {
                    list.add(mData.get(i));
                }
            }
        }

        return list;
    }


    /**
     * 获取导企业报告 选中list
     */
    public List<HomeDataItemModel> getSelectReportList() {
        List<HomeDataItemModel> list = new ArrayList<>();

        if (mData != null) {
            for (int i = 0; i < mData.size(); i++) {
                if (mData.get(i) != null && compaySelectMap.containsKey(mData.get(i).getCompanyid())) {
                    list.add(mData.get(i));
                }
            }
        }

        return list;
    }

    public int getSelectReportCount() {
        return compaySelectMap.size();
    }

    public List<SearchContactListModel.DataBean> getmData2List() {

        return mData2;
    }


    /**
     * 导出报告 设置全选 前100条
     */
    public void setCommonAllState(boolean isSelect) {
        isSelectAll = isSelect;
        if (mData != null) {
            if (!isSelect) {
                compaySelectMap.clear();
                return;
            }
            setReportAllSelect();
        }
    }


    public void setReportAllSelect() {
        if (!isSelectAll)
            return;

        for (int i = 0; i < mData.size(); i++) {
            if (compaySelectMap.size() < 100 && !compaySelectMap.containsKey(mData.get(i).getCompanyid())) {
                compaySelectMap.put(mData.get(i).getCompanyid(), "");
            }
        }
    }


    public void setAllState(boolean isSelect) {
        isSelectAll = isSelect;
        if (isContacts()) {
            if (mData2 != null) {
                if (!isSelect) {
                    contactsSelectMap.clear();
                    return;
                }
                setConstanctAllSelect();
            }
        } else if (SearchHistoryItemModel.SEARCH_SHAREHOLDER_RIFT.equals(mSearchType)) {
            if (mData != null) {
                if (!isSelect) {
                    compaySelectMap.clear();
                    return;
                }
                setShareholderAllSelect();
            }
        }
    }

    public void setConstanctAllSelect() {
        if (!isSelectAll)
            return;

        for (int i = 0; i < mData2.size(); i++) {
            if (contactsSelectMap.size() < 100 && !contactsSelectMap.containsKey(mData2.get(i).id)) {
                contactsSelectMap.put(mData2.get(i).id, "");
            }
        }
    }

    public void setShareholderAllSelect() {
        if (!isSelectAll)
            return;

        for (int i = 0; i < mData.size(); i++) {
            if (compaySelectMap.size() < 100 && !compaySelectMap.containsKey(mData.get(i).getCompanyid())) {
                compaySelectMap.put(mData.get(i).getCompanyid(), "");
            }
        }
    }


    public interface CallBack {
        void cancleSelectAll();

        void preView(HomeDataItemModel dataItemModel);//预览

        void select();
    }

    public CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
}
