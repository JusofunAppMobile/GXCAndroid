package com.jusfoun.jusfouninquire.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.event.CompanySelectEvent;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.net.model.CompanyDetailMenuModel;
import com.jusfoun.jusfouninquire.net.model.CompanyDetailModel;
import com.jusfoun.jusfouninquire.net.model.HomeDataItemModel;
import com.jusfoun.jusfouninquire.net.model.SearchContactListModel;
import com.jusfoun.jusfouninquire.net.model.SearchHistoryItemModel;
import com.jusfoun.jusfouninquire.net.util.TouchUtil;
import com.jusfoun.jusfouninquire.ui.activity.CompanyDetailActivity;
import com.jusfoun.jusfouninquire.ui.activity.CompanyDetailsActivity;
import com.jusfoun.jusfouninquire.ui.activity.ShareHolderActivity;
import com.jusfoun.jusfouninquire.ui.view.ContactsOpenView;
import com.siccredit.guoxin.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;


/**
 * SearchResultAdapter
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/15
 * @Description :搜索结果适配器
 */
public class SearchResultAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {


    private Context mContext;
    private String mSearchType;// 0 正常搜索 1 通讯录
    public static int TYPE_NORMAL = 0;
    public static int TYPE_CONSTANCTS = 1;
    public static int TYPE_SHAREHOLDER = 2; // 股东穿透
    private int type;

    private int selectStateType = 0;// 0，不显示  1 显示
    public static int TYPE_STATE_SELECT_NO = 0;
    public static int TYPE_STATE_SELECT_SHOW = 1;
    private boolean isSelectAll = false;

    private Map<String, String> compaySelectMap, contactsSelectMap;

    public SearchResultAdapter(Context mContext, String mSearchType) {
        super(null);
        this.mSearchType = mSearchType;
        this.type = getType();
        if (type == TYPE_CONSTANCTS)
            mLayoutResId = R.layout.item_search_result_constancts;
        else if (type == TYPE_SHAREHOLDER)
            mLayoutResId = R.layout.item_search_shareholder;
        else
            mLayoutResId = R.layout.item_search_result;
        this.mContext = mContext;
        compaySelectMap = new HashMap<>();
        contactsSelectMap = new HashMap<>();
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        //TYPE_CONSTANCTS###########################################
        TextView mCompanyName = helper.getView(R.id.company_name);
        TextView mCompanyInfo = helper.getView(R.id.company_info);
        TextView mCompanyState = helper.getView(R.id.company_state);
        TextView tvLegal = helper.getView(R.id.tvLegal);
        TextView tvEstablish = helper.getView(R.id.tvEstablish);
        TextView tvRelate = helper.getView(R.id.tvRelate);
        LinearLayout mRelateLayout = helper.getView(R.id.relate_layout);

        View vTop = helper.getView(R.id.vTop);

        final ImageView selectImg = helper.getView(R.id.img_select);
        TextView phoneText1 = helper.getView(R.id.text_phone1);
        final TextView phoneText2 = helper.getView(R.id.text_phone2);
        final TextView pointText = helper.getView(R.id.text_point);

        //        private View mDivider;
        ContactsOpenView contactsOpenView = helper.getView(R.id.view_contacts);
        RelativeLayout rootLayout = helper.getView(R.id.layout_root);
        //TYPE_CONSTANCTS###########################################

        //TYPE_SHAREHOLDER###########################################
        View ivHolder = helper.getView(R.id.ivHolder);
        //TYPE_SHAREHOLDER###########################################

        //TYPE_NORMAL###########################################
        //        private View mDivider;
        TextView preViewText = helper.getView(R.id.text_report_preview);
        //TYPE_NORMAL###########################################


        if (type == TYPE_CONSTANCTS) {
            final SearchContactListModel.DataBean data = (SearchContactListModel.DataBean) item;
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

            if (contactsSelectMap.containsKey(data.id)) {
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
        } else if (type == TYPE_SHAREHOLDER) {
            final HomeDataItemModel data = (HomeDataItemModel) item;
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
                    intent.putExtra(ShareHolderActivity.COMPANY_ID, data.getCompanyid());
                    intent.putExtra(ShareHolderActivity.COMPANY_NAME, data.getCompanyname());
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
                        intent.putExtra(CompanyDetailActivity.COMPANY_ID, data.getCompanyid());
                        intent.putExtra(CompanyDetailActivity.COMPANY_NAME, data.getCompanyname());
                        mContext.startActivity(intent);
                    }
                }
            });
        } else {
            final HomeDataItemModel data = (HomeDataItemModel) item;
            if (!isContacts()) {
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
                            // 国信逻辑
                            if (mSearchType.equals(SearchHistoryItemModel.SEARCH_WINNING_BID) ||
                                    mSearchType.equals(SearchHistoryItemModel.SEARCH_REFEREE) ||
                                    mSearchType.equals(SearchHistoryItemModel.SEARCH_ADMINISTRATIVE) ||
                                    mSearchType.equals(SearchHistoryItemModel.SEARCH_TRADEMARK) ||
                                    mSearchType.equals(SearchHistoryItemModel.SEARCH_RISK) ||
                                    mSearchType.equals(SearchHistoryItemModel.SEARCH_RELATION)) {

                                // 查关系
                                if (mSearchType.equals(SearchHistoryItemModel.SEARCH_RELATION)) {
                                    EventBus.getDefault().post(new CompanySelectEvent(data.getCompanyname(), data.getCompanyid()));
                                    ((Activity) mContext).finish();
                                    return;
                                } else if (mSearchType.equals(SearchHistoryItemModel.SEARCH_RISK)) {// 风险分析
                                    mContext.startActivity(com.gxc.ui.activity.WebActivity.getIntent(mContext, ((Activity) mContext).getIntent().getStringExtra("menuName"),
                                            AppUtils.parseToGxMenuType(SearchHistoryItemModel.SEARCH_RISK), data.getCompanyname()));
                                    return;
                                }

                                CompanyDetailModel companyDetailModel = new CompanyDetailModel();
                                companyDetailModel.setCompanyname(data.getCompanyname());
                                companyDetailModel.setCompanyid(data.getCompanyid());
                                List<CompanyDetailMenuModel> subclassMenu = new ArrayList<>();
                                CompanyDetailMenuModel companyDetailMenuModel = new CompanyDetailMenuModel();
                                subclassMenu.add(companyDetailMenuModel);
                                companyDetailModel.setSubclassMenu(subclassMenu);

                                Bundle argument = new Bundle();
                                argument.putSerializable(CompanyDetailsActivity.COMPANY, companyDetailModel);
                                argument.putInt(CompanyDetailsActivity.POSITION, 0);
                                argument.putBoolean(CompanyDetailsActivity.IS_GXC, true);
                                Intent intent = new Intent(mContext, CompanyDetailsActivity.class);
                                intent.putExtras(argument);
                                mContext.startActivity(intent);

                            } else {
                                Intent intent = new Intent(mContext, CompanyDetailActivity.class);
                                intent.putExtra(CompanyDetailActivity.COMPANY_ID, data.getCompanyid());
                                intent.putExtra(CompanyDetailActivity.COMPANY_NAME, data.getCompanyname());
                                mContext.startActivity(intent);
                            }

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

    public int getType() {
        if (SearchHistoryItemModel.SEARCH_CONTACT.equals(mSearchType))
            return TYPE_CONSTANCTS;
        if (SearchHistoryItemModel.SEARCH_SHAREHOLDER_RIFT.equals(mSearchType))
            return TYPE_SHAREHOLDER;
        return TYPE_NORMAL;
    }

    private boolean isContacts() {
        return SearchHistoryItemModel.SEARCH_CONTACT.equals(mSearchType);
    }

    @Override
    public void setNewData(@Nullable List<Object> data) {
        compaySelectMap.clear();
        super.setNewData(data);
    }

    @Override
    public void addData(@NonNull Collection<?> newData) {
        super.addData(newData);
        if (isContacts())
            setConstanctAllSelect();
        else
            setReportAllSelect();
    }


    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private String getDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return sdf.format(calendar.getTime());
    }

    /**
     * 设置选择图标 状态 type
     */
    public void setStateType(int type) {
        selectStateType = type;
    }

    public List<SearchContactListModel.DataBean> getList() {
        List<SearchContactListModel.DataBean> list = new ArrayList<>();

        if (getData() == null || getData().isEmpty())
            return list;

        for (Object obj : getData()) {
            SearchContactListModel.DataBean bean = (SearchContactListModel.DataBean) obj;
            if (bean != null && contactsSelectMap.containsKey(bean.id)) {
                list.add(bean);
            }
        }
        return list;
    }

    public List<HomeDataItemModel> getShareholderList() {
        List<HomeDataItemModel> list = new ArrayList<>();

        List<Object> dataList = getData();
        if (dataList != null && !dataList.isEmpty()) {
            for (Object obj : dataList) {
                HomeDataItemModel model = (HomeDataItemModel) obj;
                if (model != null && compaySelectMap.containsKey(model.getCompanyid())) {
                    list.add(model);
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

        List<Object> dataList = getData();
        if (dataList != null && !dataList.isEmpty()) {
            for (Object obj : dataList) {
                HomeDataItemModel model = (HomeDataItemModel) obj;
                if (model != null && compaySelectMap.containsKey(model.getCompanyid())) {
                    list.add(model);
                }
            }
        }
        return list;
    }

    public int getSelectReportCount() {
        return compaySelectMap.size();
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

        List<Object> dataList = getData();
        if (dataList != null && !dataList.isEmpty()) {
            for (Object obj : dataList) {
                HomeDataItemModel model = (HomeDataItemModel) obj;
                if (model != null && compaySelectMap.size() < 100 && !compaySelectMap.containsKey(model.getCompanyid())) {
                    compaySelectMap.put(model.getCompanyid(), "");
                }
            }
        }
    }


    public void setAllState(boolean isSelect) {
        isSelectAll = isSelect;
        if (isContacts()) {
            if (getData() != null) {
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

        if (getData() == null || getData().isEmpty())
            return;

        for (Object obj : getData()) {
            SearchContactListModel.DataBean bean = (SearchContactListModel.DataBean) obj;
            if (contactsSelectMap.size() < 100 && !contactsSelectMap.containsKey(bean.id)) {
                contactsSelectMap.put(bean.id, "");
            }
        }
    }

    public void setShareholderAllSelect() {
        if (!isSelectAll)
            return;
        if (getData() == null || getData().isEmpty())
            return;

        for (Object obj : getData()) {
            HomeDataItemModel bean = (HomeDataItemModel) obj;
            if (contactsSelectMap.size() < 100 && !contactsSelectMap.containsKey(bean.getCompanyid())) {
                contactsSelectMap.put(bean.getCompanyid(), "");
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
