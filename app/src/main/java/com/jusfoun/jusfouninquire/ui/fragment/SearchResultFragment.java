package com.jusfoun.jusfouninquire.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gxc.model.UserModel;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.TimeOut;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.BaseModel;
import com.jusfoun.jusfouninquire.net.model.ContactinFormationModel;
import com.jusfoun.jusfouninquire.net.model.ContactsModel;
import com.jusfoun.jusfouninquire.net.model.HomeDataItemModel;
import com.jusfoun.jusfouninquire.net.model.ReportModel;
import com.jusfoun.jusfouninquire.net.model.SearchContactListModel;
import com.jusfoun.jusfouninquire.net.model.SearchHistoryItemModel;
import com.jusfoun.jusfouninquire.net.model.SearchListModel;
import com.jusfoun.jusfouninquire.net.model.UserInfoModel;
import com.jusfoun.jusfouninquire.net.route.NetWorkCompanyDetails;
import com.jusfoun.jusfouninquire.net.route.SearchRoute;
import com.jusfoun.jusfouninquire.net.util.AppUtil;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.activity.BaseInquireActivity;
import com.jusfoun.jusfouninquire.ui.activity.ExportContactsActivity;
import com.jusfoun.jusfouninquire.ui.activity.LoginActivity;
import com.jusfoun.jusfouninquire.ui.activity.SearchResultActivity;
import com.jusfoun.jusfouninquire.ui.activity.WebActivity;
import com.jusfoun.jusfouninquire.ui.adapter.SearchResultAdapter;
import com.jusfoun.jusfouninquire.ui.view.ContactsTitleView;
import com.jusfoun.jusfouninquire.ui.view.SearchResultCountView;
import com.jusfoun.jusfouninquire.ui.view.XListView;
import com.jusfoun.jusfouninquire.ui.widget.EmailSendDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import netlib.util.EventUtils;
import netlib.util.ToastUtils;

import static com.jusfoun.jusfouninquire.R.id.register_time_sort;

/**
 * SearchResultFragment
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/10
 * @Description :搜索结果页面，配合抽屉显示的碎片
 */
public class SearchResultFragment extends BaseInquireFragment implements XListView.IXListViewListener {

    public static final String SEARCH_RESULT = "search_result";
    private BaseModel mData;
    //    private View mView;
    private XListView mResult;
    private SearchResultAdapter mAdapter;
    private RelativeLayout mFocusLayout;
    private ViewGroup mFundLayout, mTimeLayout;
    private LinearLayout mLooanywhere;
    private ImageView mFocusUp, mFocusDown;
    private TextView mFocusText, mFundText, mTimeText;
    private Button mLookanywhere, footWhere;

    //是否是升序排序
    private boolean mIsFocusUp, mIsFundUp, mIsTimeUp;


    private String mCurrentType;
    private String mSearchKey;
    private View footView;


    private String mSequence = "2";
    private String mCity = "", mProvince = "", mCapital = "", mTime = "", mIndustry = "";

    private int pageIndex = 1;
    private int pageSize = 20;
    private boolean isHasFoot = false;
    private String search_city = "全国";
    private View vArrowFund, vArrowTime;

    private LinearLayout menuTitlelayout;
    private ContactsTitleView contactsTitleView;

    private SearchResultCountView searchResultCountView;

    @Override
    protected void initData() {
        if (getArguments() != null && getArguments().getSerializable(SEARCH_RESULT) != null) {
            mData = (BaseModel) getArguments().getSerializable(SEARCH_RESULT);
        }

        if (getArguments() != null) {
            mCurrentType = getArguments().getString(SearchResultActivity.SEARCH_TYPE);
            mSearchKey = getArguments().getString(SearchResultActivity.SEARCH_KEY);
        }

        mAdapter = new SearchResultAdapter(mContext, mCurrentType);
        mIsFundUp = true;
        mIsTimeUp = true;
        if (isContacts()) {
            mSequence = "6";
        }

    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        mFocusLayout = (RelativeLayout) view.findViewById(R.id.focus_sort_layout);
//        mView = view.findViewById(R.id.myself);
        mLooanywhere = (LinearLayout) view.findViewById(R.id.look_any);
        mLookanywhere = (Button) view.findViewById(R.id.look_anywhere);
        mFundLayout = (ViewGroup) view.findViewById(R.id.register_fund_sort_layout);
        mTimeLayout = (LinearLayout) view.findViewById(R.id.register_time_sort_layout);
        mFocusUp = (ImageView) view.findViewById(R.id.focus_sort_up_image);
        mFocusDown = (ImageView) view.findViewById(R.id.focus_sort_down_image);
//        mFundUp = (ImageView) view.findViewById(R.id.register_fund_sort_up_image);
//        mFundDown = (ImageView) view.findViewById(R.id.register_fund_sort_down_image);
//        mTimeUp = (ImageView) view.findViewById(R.id.register_time_sort_up_image);
//        mTimeDown = (ImageView) view.findViewById(R.id.register_time_sort_down_image);
        mResult = (XListView) view.findViewById(R.id.search_result_listview);
        mFocusText = (TextView) view.findViewById(R.id.focus_sort);
        mFundText = (TextView) view.findViewById(R.id.register_fund_sort);
        mTimeText = (TextView) view.findViewById(register_time_sort);

        footView = LayoutInflater.from(mContext).inflate(R.layout.layout_foot_search_result, null);
        footWhere = (Button) footView.findViewById(R.id.look_anywhere);

        vArrowFund = view.findViewById(R.id.vArrowFund);
        vArrowTime = view.findViewById(R.id.vArrowTime);


        searchResultCountView = (SearchResultCountView) view.findViewById(R.id.view_search_result);
        menuTitlelayout = (LinearLayout) view.findViewById(R.id.layout_title_menu);
        contactsTitleView = (ContactsTitleView) view.findViewById(R.id.view_title_contacts);

        if (SearchHistoryItemModel.SEARCH_SHAREHOLDER_RIFT.equals(mCurrentType)) {
            contactsTitleView.setLabel("导出数据");
        }


        if (mData != null)
            searchResultCountView.setData(String.valueOf(getCount()));

        return view;
    }

    private int getCount() {
        if (isContacts())
            return ((SearchContactListModel) mData).totalCount;
        else
            return ((SearchListModel) mData).getCount();
    }

    /**
     * 是否为企业通讯录页面
     *
     * @return
     */
    private boolean isContacts() {
        return SearchHistoryItemModel.SEARCH_CONTACT.equals(mCurrentType);
    }

    private void refresh() {
        if (isContacts())
            mAdapter.refresh2(((SearchContactListModel) mData).data);
        else
            mAdapter.refresh(((SearchListModel) mData).getBusinesslist());
    }

    private boolean isDataEmpty() {
        if (isContacts()) {
            if (mData == null || ((SearchContactListModel) mData).data == null ||
                    ((SearchContactListModel) mData).data.isEmpty())
                return true;
        } else {
            if (mData == null || ((SearchListModel) mData).getBusinesslist() == null ||
                    ((SearchListModel) mData).getBusinesslist().isEmpty())
                return true;
        }
        return false;
    }

    @Override
    protected void initWeightActions() {
        mResult.setAdapter(mAdapter);
//        mAdapter.setSearchType(mCurrentType);
        if (mData != null) {
            refresh();
        }

//        if (isDataEmpty()) {
//            mLooanywhere.setVisibility(View.VISIBLE);
//        } else {
//            mLooanywhere.setVisibility(View.GONE);
//        }

        mResult.setXListViewListener(this);
        if (mData != null)
            mResult.setPullLoadEnable(getCount() > pageSize);
        if (mResult.isEnablePullLoad()) {
            if (isHasFoot) {
                isHasFoot = false;
                mResult.removeFooterView(footView);
            }
        } else {
            if (!isHasFoot && mData != null && getCount() > 0) {
                isHasFoot = true;
                mResult.addFooterView(footView);
            }
        }
//        mResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if (view.getTag() instanceof SearchResultAdapter.ViewHolder) {
//                    SearchResultAdapter.ViewHolder holder = (SearchResultAdapter.ViewHolder) view.getTag();
//                    if (holder == null) {
//                        return;
//                    }
//
//                    EventUtils.event(mContext, EventUtils.SEARCH46);
//                    if (!isContacts()) {
//                        HomeDataItemModel model =  holder.model;
//                        if (model != null) {
//                            Intent intent = new Intent(mContext, CompanyDetailActivity.class);
//                            intent.putExtra(CompanyDetailActivity.COMPANY_ID, model.getCompanyid());
//                            intent.putExtra(CompanyDetailActivity.COMPANY_NAME, model.getCompanyname());
//                            startActivity(intent);
//                        }
//                    }
//                }
//
////                else if (view.getTag() instanceof SearchResultAdapter.ConstactsViewHolder) {
////                    SearchResultAdapter.ConstactsViewHolder holder = (SearchResultAdapter.ConstactsViewHolder) view.getTag();
////                    if (holder == null) {
////                        return;
////                    }
////                    SearchContactListModel.DataBean model = holder.getData();
////                    if (model != null) {
////                        Intent intent = new Intent(mContext, CompanyDetailActivity.class);
////                        intent.putExtra(CompanyDetailActivity.COMPANY_ID, model.id);
////                        intent.putExtra(CompanyDetailActivity.COMPANY_NAME, model.name);
////                        startActivity(intent);
////                    }
////                }
//            }
//        });


        //        mFocusText.setTextColor(getResources().getColor(R.color.search_name_color));
        setSelect(mFocusText, true);


        mFocusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventUtils.event(mContext, EventUtils.SEARCH54);
                mIsFocusUp = !mIsFocusUp;
                mFocusUp.setImageResource(mIsFocusUp ? R.mipmap.sort_up_selected : R.mipmap.sort_up_unselected);
                mFocusDown.setImageResource(mIsFocusUp ? R.mipmap.sort_down_unselected : R.mipmap.sort_down_selected);
                mSequence = "2";
//                mFocusText.setTextColor(getResources().getColor(R.color.search_name_color));
                setSelect(mFocusText, true);

                vArrowFund.setSelected(false);

//                mFundUp.setImageResource(R.mipmap.sort_up_unselected);
//                mFundDown.setImageResource(R.mipmap.sort_down_unselected);
                mIsFundUp = true;
//                mFundText.setTextColor(getResources().getColor(R.color.black));
                setSelect(mFundText, false);

//                mTimeUp.setImageResource(R.mipmap.sort_up_unselected);
//                mTimeDown.setImageResource(R.mipmap.sort_down_unselected);
                vArrowTime.setSelected(false);
                mIsTimeUp = true;
//                mTimeText.setTextColor(getResources().getColor(R.color.black));
                setSelect(mTimeText, false);

                doSearch();

            }
        });

        mLookanywhere.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                searchWholeNet();
            }
        });

        footWhere.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                searchWholeNet();
            }
        });

        mFundLayout.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                EventUtils.event(mContext, EventUtils.SEARCH55);
                mIsFundUp = !mIsFundUp;

                vArrowFund.setSelected(mIsFundUp);
//                mFundUp.setImageResource(mIsFundUp ? R.mipmap.sort_up_selected : R.mipmap.sort_up_unselected);
//                mFundDown.setImageResource(mIsFundUp ? R.mipmap.sort_down_unselected : R.mipmap.sort_down_selected);
                mSequence = mIsFundUp ? "3" : "4";
//                mFundText.setTextColor(getResources().getColor(R.color.search_name_color));
                setSelect(mFundText, true);


                mFocusUp.setImageResource(R.mipmap.sort_up_unselected);
                mFocusDown.setImageResource(R.mipmap.sort_down_unselected);
                mIsFocusUp = true;
//                mFocusText.setTextColor(getResources().getColor(R.color.black));
                setSelect(mFocusText, false);


//                mTimeUp.setImageResource(R.mipmap.sort_up_unselected);
//                mTimeDown.setImageResource(R.mipmap.sort_down_unselected);
                vArrowTime.setSelected(false);
                mIsTimeUp = true;
//                mTimeText.setTextColor(getResources().getColor(R.color.black));
                setSelect(mTimeText, false);

                doSearch();

            }
        });

        mTimeLayout.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                EventUtils.event(mContext, EventUtils.SEARCH56);
                mIsTimeUp = !mIsTimeUp;
//                mTimeUp.setImageResource(mIsTimeUp ? R.mipmap.sort_up_selected : R.mipmap.sort_up_unselected);
//                mTimeDown.setImageResource(mIsTimeUp ? R.mipmap.sort_down_unselected : R.mipmap.sort_down_selected);
                vArrowTime.setSelected(mIsTimeUp);
                mSequence = mIsTimeUp ? "5" : "6";
//                mTimeText.setTextColor(getResources().getColor(R.color.search_name_color));
                setSelect(mTimeText, true);

                mFocusUp.setImageResource(R.mipmap.sort_up_unselected);
//                mFundDown.setImageResource(R.mipmap.sort_down_unselected);
                mIsFocusUp = true;
//                mFocusText.setTextColor(getResources().getColor(R.color.black));
                setSelect(mFocusText, false);

//                mFundUp.setImageResource(R.mipmap.sort_up_unselected);
//                mFundDown.setImageResource(R.mipmap.sort_down_unselected);
                vArrowFund.setSelected(false);

                mIsFundUp = true;
//                mFundText.setTextColor(getResources().getColor(R.color.black));
                setSelect(mFundText, false);

                doSearch();
            }
        });

        if (mData == null) {
            doSearch();
        }else
            searchResultCountView.setVisibility(View.VISIBLE);


        searchResultCountView.setCallBack(new SearchResultCountView.Callback() {
            @Override
            public void selectAll() {
                mAdapter.setCommonAllState(true);
                mAdapter.notifyDataSetChanged();
                searchResultCountView.setSelectText(mAdapter.getSelectReportCount() + "");
            }

            @Override
            public void cancleSelectAll() {
                mAdapter.setCommonAllState(false);
                mAdapter.notifyDataSetChanged();
                searchResultCountView.setSelectText(mAdapter.getSelectReportCount() + "");
            }

            @Override
            public void goExport() {
                sendReport();
            }

            @Override
            public void showSelect() {
                mAdapter.setStateType(SearchResultAdapter.TYPE_STATE_SELECT_SHOW);
                mAdapter.setCommonAllState(false);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void hideSelect() {
                mAdapter.setStateType(SearchResultAdapter.TYPE_STATE_SELECT_NO);
                mAdapter.setCommonAllState(false);
                mAdapter.notifyDataSetChanged();
            }
        });
        contactsTitleView.setCallBack(new ContactsTitleView.Callback() {
            @Override
            public void selectAll() {
                mAdapter.setAllState(true);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void cancleSelectAll() {
                mAdapter.setAllState(false);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void cancleSelect() {
                mAdapter.setStateType(SearchResultAdapter.TYPE_STATE_SELECT_NO);
                mAdapter.setAllState(false);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void select() {
                mAdapter.setStateType(SearchResultAdapter.TYPE_STATE_SELECT_SHOW);

                mAdapter.setAllState(false);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void goExport() {
//                Intent intent = new Intent(mContext, ContactsListActivity.class);
//                SearchContactListModel model = new SearchContactListModel();
//                model.data = mAdapter.getList();
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("model", model);
//                intent.putExtras(bundle);
//                mContext.startActivity(intent);


                if (isContacts()) {

                    List<ContactsModel> list = new ArrayList<>();

                    List<SearchContactListModel.DataBean> dataBeansList = mAdapter.getList();
                    if (dataBeansList != null)
                        for (int i = 0; i < dataBeansList.size(); i++) {
                            ContactsModel cm = new ContactsModel();
                            cm.id = dataBeansList.get(i).id;
                            cm.name = dataBeansList.get(i).name;
                            if (!TextUtils.isEmpty(dataBeansList.get(i).establishDate)) {
                                cm.time = dataBeansList.get(i).establishDate;
                            } else {
                                cm.time = "企业未公示";
                            }


                            List<String> phones = dataBeansList.get(i).phoneArr;
                            List<ContactinFormationModel> phonesList = new ArrayList<>();
                            if (phones != null)
                                for (int j = 0; j < phones.size(); j++) {
                                    ContactinFormationModel contactinFormationModel = new ContactinFormationModel();
                                    contactinFormationModel.setNumber(phones.get(j));
                                    phonesList.add(contactinFormationModel);
                                }
                            cm.phones = phonesList;
                            list.add(cm);
                        }

                    if (list.size() == 0) {
                        Toast.makeText(mContext, "未选中企业", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(mContext, ExportContactsActivity.class);
                    intent.putExtra("model", new Gson().toJson(list));
                    mContext.startActivity(intent);
                } else {
//                    List<ContactsModel> list = new ArrayList<>();

                    List<HomeDataItemModel> list = mAdapter.getShareholderList();
                    if (list != null) {
                        showToast("选中" + list.size() + "个");
                    }

                }
            }

            @Override
            public void searchAse() {
                mSequence = "5";
                doSearch();
            }

            @Override
            public void searchDes() {
                mSequence = "6";
                doSearch();
            }
        });
//        mAdapter.setSearchType(mCurrentType);
        if (SearchHistoryItemModel.SEARCH_CONTACT.equals(mCurrentType) || SearchHistoryItemModel.SEARCH_SHAREHOLDER_RIFT.equals(mCurrentType)) {
            menuTitlelayout.setVisibility(View.GONE);
            contactsTitleView.setVisibility(View.VISIBLE);
            searchResultCountView.setDaochuGone();
        }

        mAdapter.setCallBack(new SearchResultAdapter.CallBack() {
            @Override
            public void cancleSelectAll() {
                contactsTitleView.setCancleSelect();
                searchResultCountView.setCancleSelect();
                searchResultCountView.setSelectText(mAdapter.getSelectReportCount() + "");
            }

            @Override
            public void preView(HomeDataItemModel dataItemModel) {
                if (dataItemModel != null) {
                    reprewReport(dataItemModel);
                }
            }

            @Override
            public void select() {
                searchResultCountView.setSelectText(mAdapter.getSelectReportCount() + "");
            }
        });


    }

    private void setSelect(TextView textView, boolean select) {
        textView.setSelected(select);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, select ? 16 : 14);
        textView.setTypeface(select ? Typeface.defaultFromStyle(Typeface.BOLD) : Typeface.defaultFromStyle(Typeface.NORMAL));
    }

    /**
     * 跳转到全网搜索
     */
    private void searchWholeNet() {
        String url = String.format(mContext.getString(R.string.search_whole_net), search_city, mSearchKey, AppUtil.getVersionName(mContext));
        Intent intent = new Intent(mContext, WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(WebActivity.TITLE, "企业查询");
        bundle.putString(WebActivity.URL, mContext.getString(R.string.req_url) + url);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 网络搜索
     */
    private void doSearch() {
        pageIndex = 1;
        final TimeOut timeOut = new TimeOut(mContext);
        HashMap<String, String> params = new HashMap<>();
        if (LoginSharePreference.getUserInfo(mContext) != null
                && !TextUtils.isEmpty(LoginSharePreference.getUserInfo(mContext).getUserid())) {
            params.put("userid", LoginSharePreference.getUserInfo(mContext).getUserid());
        } else {
            params.put("userid", "");
        }

        if (!TextUtils.isEmpty(mCity)) {
            search_city = mCity;
        } else if (!TextUtils.isEmpty(mProvince)) {
            search_city = mProvince;
        } else {
            search_city = "全国";
        }

        params.put("t", timeOut.getParamTimeMollis() + "");
        params.put("m", timeOut.MD5time() + "");

        params.put("searchkey", mSearchKey);
        if (mCurrentType.equals(SearchHistoryItemModel.SEARCH_SHAREHOLDER_RIFT))
            params.put("type", "6");
        else
            params.put("type", mCurrentType);
        params.put("pageSize", pageSize + "");
        params.put("pageIndex", pageIndex + "");
        params.put("sequence", mSequence);
        params.put("city", mCity);
        params.put("province", mProvince);
        params.put("registeredcapital", mCapital);
        params.put("regtime", mTime);
        params.put("industryid", mIndustry);
        showLoading();
        SearchRoute.searchNet(mContext, params, getActivity().getLocalClassName().toString(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                searchResultCountView.setVisibility(View.VISIBLE);
                mResult.stopRefresh();
                if (!isContacts()) {
                    SearchListModel model = (SearchListModel) data;
                    searchResultCountView.setData(String.valueOf(model.getCount()));
                    if (model.getResult() == 0) {
                        searchResultCountView.reSet();
                        if (model.getBusinesslist() != null && model.getBusinesslist().size() > 0) {
                            mAdapter.refresh(model.getBusinesslist());
                            mResult.setPullLoadEnable(model.getCount() > pageSize);
                            mResult.setVisibility(View.VISIBLE);
                            mLooanywhere.setVisibility(View.GONE);
                        } else {
                            mAdapter.refresh(model.getBusinesslist());
                            mResult.setPullLoadEnable(false);
//                            showToast(mContext.getString(R.string.search_result_none));
                            mResult.setVisibility(View.GONE);
                            mLooanywhere.setVisibility(View.VISIBLE);
//                            mView.setVisibility(View.GONE);
                        }
                        if (mResult.isEnablePullLoad()) {
                            if (isHasFoot) {
                                isHasFoot = false;
                                mResult.removeFooterView(footView);
                            }
                        } else {
                            if (!isHasFoot) {
                                isHasFoot = true;
                                mResult.addFooterView(footView);
                            }
                        }
                    } else {
                        if (model.getResult() != -1) {
                            if (!TextUtils.isEmpty(model.getMsg())) {
                                showToast(model.getMsg());
                            } else {
                                showToast("网络不给力，请稍后重试");
                            }
                        }

                    }
                } else {
                    SearchContactListModel model = (SearchContactListModel) data;
                    searchResultCountView.setData(String.valueOf(model.totalCount));
                    if (model.getResult() == 0) {
                        contactsTitleView.reSet();
                        if (model.data != null && model.data.size() > 0) {
                            mAdapter.refresh2(model.data);
                            mResult.setPullLoadEnable(model.totalCount > pageSize);
                            mResult.setVisibility(View.VISIBLE);
                            mLooanywhere.setVisibility(View.GONE);
                        } else {
                            mAdapter.refresh2(model.data);
                            mResult.setPullLoadEnable(false);
//                            showToast(mContext.getString(R.string.search_result_none));
                            mResult.setVisibility(View.GONE);
                            mLooanywhere.setVisibility(View.VISIBLE);
//                            mView.setVisibility(View.GONE);
                        }
                        if (mResult.isEnablePullLoad()) {
                            if (isHasFoot) {
                                isHasFoot = false;
                                mResult.removeFooterView(footView);
                            }
                        } else {
                            if (!isHasFoot) {
                                isHasFoot = true;
                                mResult.addFooterView(footView);
                            }
                        }
                    } else {
                        if (model.getResult() != -1) {
                            if (!TextUtils.isEmpty(model.getMsg())) {
                                showToast(model.getMsg());
                            } else {
                                showToast("网络不给力，请稍后重试");
                            }
                        }

                    }
                }
            }

            @Override
            public void onFail(String error) {
                mResult.stopRefresh();
                showToast("网络不给力，请稍后重试");
                hideLoadDialog();
            }
        });
    }

    /**
     * 根据筛选条件进行重新搜索
     *
     * @param params
     */
    public void doFilterSearch(HashMap<String, String> params) {
        if (params.get("city") != null) {
            mCity = params.get("city").toString();
        }
        if (params.get("province") != null) {
            mProvince = params.get("province").toString();
        }

        if (params.get("registeredcapital") != null) {
            mCapital = params.get("registeredcapital").toString();
        }

        if (params.get("isHavWebSite") != null) {
            mCapital = params.get("isHavWebSite").toString();
        }

        if (params.get("regtime") != null) {
            mTime = params.get("regtime").toString();
        }

        if (params.get("industryid") != null) {
            mIndustry = params.get("industryid").toString();
        }

        doSearch();
    }

    /**
     * 加载更多网络请求
     */
    private void doLoadMore() {
        TimeOut timeOut = new TimeOut(mContext);
        HashMap<String, String> params = new HashMap<>();
        if (LoginSharePreference.getUserInfo(mContext) != null
                && !TextUtils.isEmpty(LoginSharePreference.getUserInfo(mContext).getUserid())) {
            params.put("userid", LoginSharePreference.getUserInfo(mContext).getUserid());
        } else {
            params.put("userid", "");
        }

        params.put("t", timeOut.getParamTimeMollis() + "");
        params.put("m", timeOut.MD5time() + "");

        params.put("searchkey", mSearchKey);
        if (mCurrentType.equals(SearchHistoryItemModel.SEARCH_SHAREHOLDER_RIFT))
            params.put("type", "6");
        else
            params.put("type", mCurrentType);
        params.put("pageSize", pageSize + "");//业务逻辑是每次上拉加载10条
        params.put("pageIndex", pageIndex + 1 + "");
        params.put("sequence", mSequence);
        params.put("city", mCity);
        params.put("province", mProvince);
        params.put("registeredcapital", mCapital);
        params.put("regtime", mTime);
        params.put("industryid", mIndustry);

        SearchRoute.searchNet(mContext, params, getActivity().getLocalClassName().toString(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                mResult.stopLoadMore();
                if (!isContacts()) {
                    if (data instanceof SearchListModel) {
                        SearchListModel model = (SearchListModel) data;
                        if (model.getResult() == 0) {
                            pageIndex = pageIndex + 1;
                            mResult.setPullLoadEnable(model.getCount() > (pageSize * pageIndex));
                            if (mResult.isEnablePullLoad()) {
                                if (isHasFoot) {
                                    isHasFoot = false;
                                    mResult.removeFooterView(footView);
                                }
                            } else {
                                if (!isHasFoot) {
                                    isHasFoot = true;
                                    mResult.addFooterView(footView);
                                }
                            }
                            if (model.getBusinesslist() != null && model.getBusinesslist().size() > 0) {
                                mAdapter.addData(model.getBusinesslist());
                                searchResultCountView.setSelectText(mAdapter.getSelectReportCount() + "");
                            } else {

                            }
                        } else {
                            if (model.getResult() != -1) {
                                if (!TextUtils.isEmpty(model.getMsg())) {
                                    showToast(model.getMsg());
                                } else {
                                    showToast("网络不给力，请稍后重试");
                                }
                            }
                        }
                    }
                } else {
                    if (data instanceof SearchContactListModel) {
                        SearchContactListModel model = (SearchContactListModel) data;
                        if (model.getResult() == 0) {
                            pageIndex = pageIndex + 1;
                            mResult.setPullLoadEnable(model.totalCount > (pageSize * pageIndex));
                            if (mResult.isEnablePullLoad()) {
                                if (isHasFoot) {
                                    isHasFoot = false;
                                    mResult.removeFooterView(footView);
                                }
                            } else {
                                if (!isHasFoot) {
                                    isHasFoot = true;
                                    mResult.addFooterView(footView);
                                }
                            }
                            if (model.data != null && model.data.size() > 0) {
                                mAdapter.addData2(model.data);
                            } else {

                            }

//                            saveCrashInfo2File();
                        } else {
                            if (model.getResult() != -1) {
                                if (!TextUtils.isEmpty(model.getMsg())) {
                                    showToast(model.getMsg());
                                } else {
                                    showToast("网络不给力，请稍后重试");
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFail(String error) {
                mResult.stopLoadMore();
                showToast("网络不给力，请稍后重试");

            }
        });

    }

    @Override
    public void onRefresh() {

        doSearch();
    }

    @Override
    public void onLoadMore() {
        doLoadMore();
    }

    private void reprewReport(final HomeDataItemModel homeDataItemModel) {
        UserInfoModel userInfo = InquireApplication.getUserInfo();
        if (userInfo == null) {
            goActivity(LoginActivity.class);
            return;
        }

        showLoading();

        final String mCompanyId = homeDataItemModel.getCompanyid();
        final String mCompanyName = homeDataItemModel.getCompanyname();

        TimeOut timeOut = new TimeOut(mContext);
        HashMap<String, String> params = new HashMap<>();
        params.put("entId", mCompanyId);
        params.put("entName", mCompanyName == null ? "" : mCompanyName);

        if (userInfo != null && !TextUtils.isEmpty(userInfo.getUserid()))
            params.put("userid", userInfo.getUserid());
        else {
            params.put("userid", "");
        }
        params.put("t", timeOut.getParamTimeMollis() + "");
        params.put("m", timeOut.MD5time() + "");
        NetWorkCompanyDetails.getReportUrl(mContext, params, ((Activity) mContext).getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                ReportModel model = (ReportModel) data;
                if (((ReportModel) data).getResult() == 0) {
                    if (model.getData() != null && !TextUtils.isEmpty(model.getData().getReportUrl())) {

                        String cId = "";
                        if (!TextUtils.isEmpty(mCompanyId)) {
                            cId = mCompanyId;
                        } else {
                            if (!TextUtils.isEmpty(homeDataItemModel.getCompanyid())) {
                                cId = homeDataItemModel.getCompanyid();
                            }
                        }


                        WebActivity.startCompanyReportActivity(mContext, model.getData().getReportUrl(), mCompanyName, cId, "1".equals(model.getData().getVipType()));
                    }
                } else {
                    Toast.makeText(mContext, model.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail(String error) {
                Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
                hideLoadDialog();
            }
        });
    }


    /**
     * 判断用户是否 企业报告vip
     */
    private void sendReport() {


        if (mContext == null || !isAdded()) {
            return;
        }
        UserInfoModel model = InquireApplication.getUserInfo();
//        if (model == null) {
//            goActivity(LoginActivity.class);
//            return;
//        }

        UserModel user = AppUtils.getUser();
        if (user == null) {
            goActivity(com.gxc.ui.activity.LoginActivity.class);
            return;
        }

        final List<HomeDataItemModel> list = mAdapter.getSelectReportList();

        if (list == null || list.size() == 0) {
            ToastUtils.show("您未选中企业");
            return;
        }


        showLoading();
        TimeOut timeOut = new TimeOut(mContext);
        HashMap<String, String> params = new HashMap<String, String>();
        params = new HashMap<>();

        HomeDataItemModel homeDataItemModel = list.get(0);
        String mCompanyId = "", mCompanyName = "";
        if (homeDataItemModel != null) {
            mCompanyId = homeDataItemModel.getCompanyid();
            mCompanyName = homeDataItemModel.getCompanyname();
        }


        params.put("entId", mCompanyId);

        params.put("entName", mCompanyName == null ? "" : mCompanyName);

        if (!TextUtils.isEmpty(model.getUserid()))
            params.put("userid", model.getUserid());
        else {
            params.put("userid", "");
        }
        params.put("t", timeOut.getParamTimeMollis() + "");
        params.put("m", timeOut.MD5time() + "");
        NetWorkCompanyDetails.getReportUrl(mContext, params, ((Activity) mContext).getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                ReportModel model = (ReportModel) data;
                if (((ReportModel) data).getResult() == 0) {
                    if (model.getData() != null) {

                        if ("1".equals(model.getData().getVipType())) {

                            List<String> companyIds = new ArrayList<>();
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i) != null)
                                    companyIds.add(list.get(i).getCompanyid());
                            }
                            if (!companyIds.isEmpty()) {
                                StringBuffer sb = new StringBuffer();
                                for (String m : companyIds) {
                                    sb.append(m + ",");
                                }
                                if (sb.toString().endsWith(","))
                                    sb.deleteCharAt(sb.length() - 1);
                                new EmailSendDialog(((BaseInquireActivity) mContext), "", sb.toString(), EmailSendDialog.TYPE_REPORT_MORE).show();
                            }
                        } else {
                            WebActivity.startVipPage(getActivity());
                        }

                    }
                } else {
                    Toast.makeText(mContext, model.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail(String error) {
                Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
                hideLoadDialog();
            }
        });

    }

}
