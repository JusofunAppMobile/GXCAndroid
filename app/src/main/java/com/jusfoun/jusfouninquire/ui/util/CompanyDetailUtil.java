package com.jusfoun.jusfouninquire.ui.util;

import android.os.Bundle;

import com.jusfoun.jusfouninquire.net.model.CompanyDetailMenuModel;
import com.jusfoun.jusfouninquire.ui.activity.CompanyDetailsActivity;
import com.jusfoun.jusfouninquire.ui.fragment.BaseInquireFragment;
import com.jusfoun.jusfouninquire.ui.fragment.BiddingFragment;
import com.jusfoun.jusfouninquire.ui.fragment.BrandListFragment;
import com.jusfoun.jusfouninquire.ui.fragment.CompanyInvestmentFragment;
import com.jusfoun.jusfouninquire.ui.fragment.CompanyListFragment;
import com.jusfoun.jusfouninquire.ui.fragment.CompanyMapFragment;
import com.jusfoun.jusfouninquire.ui.fragment.CompanyWebFragment;
import com.jusfoun.jusfouninquire.ui.fragment.PatentListFragment;
import com.jusfoun.jusfouninquire.ui.fragment.RecruitmentFragment;
import com.jusfoun.jusfouninquire.ui.fragment.SlideFragment;
import com.jusfoun.jusfouninquire.ui.fragment.TaxationListFragment;

import java.util.List;

import static com.jusfoun.jusfouninquire.ui.constant.CompanyDetailTypeConstant.TYPE_BIDS;
import static com.jusfoun.jusfouninquire.ui.constant.CompanyDetailTypeConstant.TYPE_PUBLISH;
import static com.jusfoun.jusfouninquire.ui.constant.CompanyDetailTypeConstant.TYPE_STOCK;

/**
 * Author  JUSFOUN
 * CreateDate 2015/11/9.
 * Description
 */
public class CompanyDetailUtil {

//    public static final int WEB_TYPE = 0;
//    public static final int COMPANY_MAP_TYPE = 1;
//    public static final int TYPE = 2;
//    public static final int TYPE_BRAND = 3; // 无形资产-商标
//    public static final int TYPE_PATENT = 4; // 无形资产-专利
//    public static final int TYPE_TAB = 5; // 选项卡
//    public static final int TYPE_BIDDING = 6; // 招投标
//    public static final int TYPE_RECRUITMENT = 7; // 招聘
//    //    public static final int TYPE_TAB = 5; // 选项卡


    public static BaseInquireFragment getInstance(int position, Bundle argument, List<CompanyDetailMenuModel> subclassMenu) {
        BaseInquireFragment fragment = null;
        if (subclassMenu != null && subclassMenu.get(position) != null)
            switch (subclassMenu.get(position).getType()) {
                case CompanyDetailsActivity.TYPE_WEB:
                    fragment = CompanyWebFragment.getInstance(argument);
                    break;
                case CompanyDetailsActivity.TYPE_MAP:
                    fragment = CompanyMapFragment.getInstance(argument);
                    break;
                case CompanyDetailsActivity.TYPE_INVEST:
                case CompanyDetailsActivity.TYPE_BRANCH:
                    fragment = CompanyInvestmentFragment.getInstance(argument);
                    break;
                case CompanyDetailsActivity.TYPE_TAB: // 选项卡
                    fragment = SlideFragment.getInstance(argument);
                    break;

                case CompanyDetailsActivity.TYPE_BIDDING: // 招投标
                    fragment = BiddingFragment.getInstace(argument);
                    break;
                case CompanyDetailsActivity.TYPE_RECRUITMENT: // 招聘
                    fragment = RecruitmentFragment.getInstace(argument);
                    break;
                case CompanyDetailsActivity.TYPE_BRAND: // 商标
                    fragment = BrandListFragment.getInstace(argument);
                    break;
                case TYPE_PUBLISH: // 行政处罚
                    fragment = CompanyListFragment.getInstace(argument, TYPE_PUBLISH);
                    break;
                case TYPE_STOCK: // 股权出质
                    fragment = CompanyListFragment.getInstace(argument, TYPE_STOCK);
                    break;
                case TYPE_BIDS: //  招标
                    fragment = CompanyListFragment.getInstace(argument, TYPE_BIDS);
                    break;
                case CompanyDetailsActivity.TYPE_PATENT: // 专利
                    fragment = PatentListFragment.getInstace(argument);
                    break;
                case CompanyDetailsActivity.TYPE_TAXATION: // 税务公告
                    fragment = TaxationListFragment.getInstace(argument);
                    break;


            }
        return fragment;
    }

    public static boolean canAlwaysSelectMenu(int type) {
        if (type == CompanyDetailsActivity.TYPE_BRAND || type == CompanyDetailsActivity.TYPE_PATENT || type == CompanyDetailsActivity.TYPE_TAB)
            return true;
        return false;
    }
}
