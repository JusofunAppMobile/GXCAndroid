package com.jusfoun.jusfouninquire.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.gxc.base.BaseListFragment;
import com.gxc.model.UserModel;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.InquireApplication;
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
import com.jusfoun.jusfouninquire.ui.activity.ExportContactsActivity;
import com.jusfoun.jusfouninquire.ui.activity.LoginActivity;
import com.jusfoun.jusfouninquire.ui.activity.SearchResultActivity;
import com.jusfoun.jusfouninquire.ui.activity.WebActivity;
import com.jusfoun.jusfouninquire.ui.adapter.SearchResultAdapter;
import com.jusfoun.jusfouninquire.ui.view.ContactsTitleView;
import com.jusfoun.jusfouninquire.ui.view.SearchResultCountView;
import com.siccredit.guoxin.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import netlib.util.EventUtils;
import netlib.util.ToastUtils;

import static com.jusfoun.jusfouninquire.ui.activity.TypeSearchActivity.isBlurrySearch;


/**
 * SearchResultFragment
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/10
 * @Description :搜索结果页面，配合抽屉显示的碎片
 */
public class SearchResultFragment extends BaseListFragment {

    public static final String SEARCH_RESULT = "search_result";
    private RelativeLayout mFocusLayout;
    private ViewGroup mFundLayout, mTimeLayout;
    private ImageView mFocusUp, mFocusDown;
    private TextView mFocusText, mFundText, mTimeText;

    //是否是升序排序
    private boolean mIsFocusUp, mIsFundUp, mIsTimeUp;

    private String mCurrentType;
    private String mSearchKey;


    private String mSequence = "2";
    private String mCity = "", mProvince = "", mCapital = "", mTime = "", mIndustry = "", isHavWebSite = "";

    private View vArrowFund, vArrowTime;

    private LinearLayout menuTitlelayout;
    private ContactsTitleView contactsTitleView;

    private SearchResultCountView searchResultCountView;
    private SearchResultAdapter adapter;

    @Override
    protected BaseQuickAdapter getAdapter() {
        getArgumentsData();
        return adapter = new SearchResultAdapter(activity, mCurrentType);
    }

    private void getArgumentsData() {
        if (getArguments() == null) return;
        mCurrentType = getArguments().getString(SearchResultActivity.SEARCH_TYPE);
        mSearchKey = getArguments().getString(SearchResultActivity.SEARCH_KEY);

        if (isContacts()) {
            mSequence = "6";
        }
    }

    @Override
    protected void initUi() {
        mIsFundUp = true;
        mIsTimeUp = true;
        mFocusLayout = (RelativeLayout) rootView.findViewById(R.id.focus_sort_layout);
        mFundLayout = (ViewGroup) rootView.findViewById(R.id.register_fund_sort_layout);
        mTimeLayout = (LinearLayout) rootView.findViewById(R.id.register_time_sort_layout);
        mFocusUp = (ImageView) rootView.findViewById(R.id.focus_sort_up_image);
        mFocusDown = (ImageView) rootView.findViewById(R.id.focus_sort_down_image);
        mFocusText = (TextView) rootView.findViewById(R.id.focus_sort);
        mFundText = (TextView) rootView.findViewById(R.id.register_fund_sort);
        mTimeText = (TextView) rootView.findViewById(R.id.register_time_sort);

        vArrowFund = rootView.findViewById(R.id.vArrowFund);
        vArrowTime = rootView.findViewById(R.id.vArrowTime);

        searchResultCountView = (SearchResultCountView) rootView.findViewById(R.id.view_search_result);
        menuTitlelayout = (LinearLayout) rootView.findViewById(R.id.layout_title_menu);
        contactsTitleView = (ContactsTitleView) rootView.findViewById(R.id.view_title_contacts);

        if (SearchHistoryItemModel.SEARCH_SHAREHOLDER_RIFT.equals(mCurrentType)) {
            contactsTitleView.setLabel("导出数据");
        }

        setSelect(mFocusText, true);

        mFocusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventUtils.event(activity, EventUtils.SEARCH54);
                mIsFocusUp = !mIsFocusUp;
                mFocusUp.setImageResource(mIsFocusUp ? R.mipmap.sort_up_selected : R.mipmap.sort_up_unselected);
                mFocusDown.setImageResource(mIsFocusUp ? R.mipmap.sort_down_unselected : R.mipmap.sort_down_selected);
                mSequence = "2";
                setSelect(mFocusText, true);

                vArrowFund.setSelected(false);

                mIsFundUp = true;
                setSelect(mFundText, false);

                vArrowTime.setSelected(false);
                mIsTimeUp = true;
                setSelect(mTimeText, false);

                refresh();
            }
        });

        mFundLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventUtils.event(activity, EventUtils.SEARCH55);
                mIsFundUp = !mIsFundUp;

                vArrowFund.setSelected(mIsFundUp);
                mSequence = mIsFundUp ? "3" : "4";
                setSelect(mFundText, true);


                mFocusUp.setImageResource(R.mipmap.sort_up_unselected);
                mFocusDown.setImageResource(R.mipmap.sort_down_unselected);
                mIsFocusUp = true;
                setSelect(mFocusText, false);


                vArrowTime.setSelected(false);
                mIsTimeUp = true;
                setSelect(mTimeText, false);

                refresh();

            }
        });

        mTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventUtils.event(activity, EventUtils.SEARCH56);
                mIsTimeUp = !mIsTimeUp;
                vArrowTime.setSelected(mIsTimeUp);
                mSequence = mIsTimeUp ? "5" : "6";
                setSelect(mTimeText, true);

                mFocusUp.setImageResource(R.mipmap.sort_up_unselected);
                mIsFocusUp = true;
                setSelect(mFocusText, false);

                vArrowFund.setSelected(false);

                mIsFundUp = true;
                setSelect(mFundText, false);

                refresh();
            }
        });


        searchResultCountView.setCallBack(new SearchResultCountView.Callback() {
            @Override
            public void selectAll() {
                adapter.setCommonAllState(true);
                adapter.notifyDataSetChanged();
                searchResultCountView.setSelectText(adapter.getSelectReportCount() + "");
            }

            @Override
            public void cancleSelectAll() {
                adapter.setCommonAllState(false);
                adapter.notifyDataSetChanged();
                searchResultCountView.setSelectText(adapter.getSelectReportCount() + "");
            }

            @Override
            public void goExport() {
                sendReport();
            }

            @Override
            public void showSelect() {
                adapter.setStateType(SearchResultAdapter.TYPE_STATE_SELECT_SHOW);
                adapter.setCommonAllState(false);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void hideSelect() {
                adapter.setStateType(SearchResultAdapter.TYPE_STATE_SELECT_NO);
                adapter.setCommonAllState(false);
                adapter.notifyDataSetChanged();
            }
        });

        contactsTitleView.setCallBack(new ContactsTitleView.Callback() {
            @Override
            public void selectAll() {
                adapter.setAllState(true);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void cancleSelectAll() {
                adapter.setAllState(false);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void cancleSelect() {
                adapter.setStateType(SearchResultAdapter.TYPE_STATE_SELECT_NO);
                adapter.setAllState(false);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void select() {
                adapter.setStateType(SearchResultAdapter.TYPE_STATE_SELECT_SHOW);

                adapter.setAllState(false);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void goExport() {

                if (isContacts()) {

                    List<ContactsModel> list = new ArrayList<>();

                    List<SearchContactListModel.DataBean> dataBeansList = adapter.getList();
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
                        Toast.makeText(activity, "未选中企业", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(activity, ExportContactsActivity.class);
                    intent.putExtra("model", new Gson().toJson(list));
                    activity.startActivity(intent);
                } else {
                    List<HomeDataItemModel> list = adapter.getShareholderList();
                    if (list != null) {
                        showToast("选中" + list.size() + "个");
                    }

                }
            }

            @Override
            public void searchAse() {
                mSequence = "5";
                refresh();
            }

            @Override
            public void searchDes() {
                refresh();
            }
        });
        if (SearchHistoryItemModel.SEARCH_CONTACT.equals(mCurrentType) || SearchHistoryItemModel.SEARCH_SHAREHOLDER_RIFT.equals(mCurrentType)) {
            searchResultCountView.setDaochuGone();
        }

        adapter.setCallBack(new SearchResultAdapter.CallBack() {
            @Override
            public void cancleSelectAll() {
                contactsTitleView.setCancleSelect();
                searchResultCountView.setCancleSelect();
                searchResultCountView.setSelectText(adapter.getSelectReportCount() + "");
            }

            @Override
            public void preView(HomeDataItemModel dataItemModel) {
                if (dataItemModel != null) {
                    reprewReport(dataItemModel);
                }
            }

            @Override
            public void select() {
                searchResultCountView.setSelectText(adapter.getSelectReportCount() + "");
            }
        });
    }

    @Override
    protected void startLoadData() {
        final TimeOut timeOut = new TimeOut(activity);
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", "");

        params.put("t", timeOut.getParamTimeMollis() + "");
        params.put("m", timeOut.MD5time() + "");

        params.put("searchkey", mSearchKey);
        if (mCurrentType.equals(SearchHistoryItemModel.SEARCH_SHAREHOLDER_RIFT))
            params.put("type", "6");
        else if (isBlurrySearch(mCurrentType)) {
            params.put("type", SearchHistoryItemModel.SEARCH_COMMON);
        } else {
            params.put("type", mCurrentType);
        }
        params.put("pageSize", pageSize + "");
        params.put("pageIndex", pageIndex + "");
        params.put("sequence", mSequence);
        params.put("city", mCity);
        params.put("province", mProvince);
        params.put("registeredcapital", mCapital);
        params.put("regtime", mTime);
        params.put("industryid", mIndustry);
        params.put("isHavWebSite", isHavWebSite);
        SearchRoute.searchNet(activity, params, getActivity().getLocalClassName().toString(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                BaseModel baseModel = (BaseModel) data;
                if (baseModel.getResult() == 0) {
                    if (!isContacts()) {
                        SearchListModel model = (SearchListModel) data;
                        if (pageIndex == 1 && model.getCount() > 0)
                            handleTopView(true);
                        searchResultCountView.setData(String.valueOf(model.getCount()));
                        searchResultCountView.reSet();
                        completeLoadData(model.getBusinesslist(), model.getCount());
                    } else {
                        SearchContactListModel model = (SearchContactListModel) data;
                        if (pageIndex == 1 && model.totalCount > 0)
                            handleTopView(true);
                        searchResultCountView.setData(String.valueOf(model.totalCount));
                        contactsTitleView.reSet();
                        completeLoadData(model.data, model.totalCount);
                    }
                } else {
                    handleTopView(false);
                    completeLoadDataError();
                }
            }

            @Override
            public void onFail(String error) {
                handleTopView(false);
                completeLoadDataError();
            }
        });
    }

    private void handleTopView(boolean show) {
        if (SearchHistoryItemModel.SEARCH_CONTACT.equals(mCurrentType) || SearchHistoryItemModel.SEARCH_SHAREHOLDER_RIFT.equals(mCurrentType)) {
            if (show) {
                mTimeLayout.setVisibility(View.VISIBLE);
                contactsTitleView.setVisibility(View.VISIBLE);
                searchResultCountView.setVisibility(View.VISIBLE);
            } else {
                mTimeLayout.setVisibility(View.GONE);
                contactsTitleView.setVisibility(View.GONE);
                searchResultCountView.setVisibility(View.GONE);

            }
        } else {
            if (show) {
                searchResultCountView.setVisibility(View.VISIBLE);
                menuTitlelayout.setVisibility(View.VISIBLE);
            } else {
                searchResultCountView.setVisibility(View.GONE);
                menuTitlelayout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search_result;
    }

    /**
     * 是否为企业通讯录页面
     *
     * @return
     */
    private boolean isContacts() {
        return SearchHistoryItemModel.SEARCH_CONTACT.equals(mCurrentType);
    }


    private void setSelect(TextView textView, boolean select) {
        textView.setSelected(select);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, select ? 16 : 14);
        textView.setTypeface(select ? Typeface.defaultFromStyle(Typeface.BOLD) : Typeface.defaultFromStyle(Typeface.NORMAL));
    }


    /**
     * 根据筛选条件进行重新搜索
     *
     * @param params
     */
    public void doFilterSearch(HashMap<String, String> params) {
        mCity = getMapValue(params, "city");
        mProvince = getMapValue(params, "province");
        mCapital = getMapValue(params, "registeredcapital");
        isHavWebSite = getMapValue(params, "isHavWebSite");
        mTime = getMapValue(params, "regtime");
        mIndustry = getMapValue(params, "industryid");
        refresh();
    }

    private String getMapValue(HashMap<String, String> params, String key) {
        if (params.containsKey(key))
            return params.get(key);
        else
            return "";
    }

    private void reprewReport(final HomeDataItemModel homeDataItemModel) {
        UserInfoModel userInfo = InquireApplication.getUserInfo();
        if (userInfo == null) {
            startActivity(LoginActivity.class);
            return;
        }

        showLoading();

        final String mCompanyId = homeDataItemModel.getCompanyid();
        final String mCompanyName = homeDataItemModel.getCompanyname();

        TimeOut timeOut = new TimeOut(activity);
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
        NetWorkCompanyDetails.getReportUrl(activity, params, ((Activity) activity).getLocalClassName(), new NetWorkCallBack() {
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


                        WebActivity.startCompanyReportActivity(activity, model.getData().getReportUrl(), mCompanyName, cId, "1".equals(model.getData().getVipType()));
                    }
                } else {
                    Toast.makeText(activity, model.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail(String error) {
                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();
                hideLoadDialog();
            }
        });
    }


    /**
     * 判断用户是否 企业报告vip
     */
    private void sendReport() {


        if (activity == null || !isAdded()) {
            return;
        }
        UserInfoModel model = InquireApplication.getUserInfo();

        UserModel user = AppUtils.getUser();
        if (user == null) {
            startActivity(com.gxc.ui.activity.LoginActivity.class);
            return;
        }

        final List<HomeDataItemModel> list = adapter.getSelectReportList();

        if (list == null || list.size() == 0) {
            ToastUtils.show("您未选中企业");
            return;
        }


        showLoading();
        TimeOut timeOut = new TimeOut(activity);
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
        NetWorkCompanyDetails.getReportUrl(activity, params, ((Activity) activity).getLocalClassName(), new NetWorkCallBack() {
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
//                                new EmailSendDialog(activity), "", sb.toString(), EmailSendDialog.TYPE_REPORT_MORE).show();
                            }
                        } else {
                            WebActivity.startVipPage(getActivity());
                        }

                    }
                } else {
                    Toast.makeText(activity, model.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail(String error) {
                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();
                hideLoadDialog();
            }
        });

    }

}
