package com.gxc.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.gxc.model.HomeMenuModel;
import com.gxc.model.UserModel;
import com.gxc.ui.activity.CreditCommitmentActivity;
import com.gxc.ui.activity.CreditReportActivity;
import com.gxc.ui.activity.LoginActivity;
import com.gxc.ui.activity.RelationActivity;
import com.gxc.ui.activity.ReportInfoActivity;
import com.gxc.ui.activity.RiskTipActivity;
import com.gxc.ui.activity.VisitorListActivity;
import com.gxc.ui.activity.WebActivity;
import com.gxc.ui.dialog.VIPDialog;
import com.jusfoun.jusfouninquire.ui.activity.CompanyAmendActivity;
import com.jusfoun.jusfouninquire.ui.activity.TypeSearchActivity;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/1809:08
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class GoActivityUtil {
    // 1：股东高管 2：主营产品 3：失信查询 4：查税号 5：招聘 6：企业通讯录 7：查关系
    // 8：风险分析  9:信用报告，10 信用异议，11 ：地址电话 12，信用承诺，13访客，14自主填报
    // 15 ：中标信息  16：裁判文书 17：行政处罚  18：商标查询  -1:H5
    public static void goActivityByType(Context mContext, HomeMenuModel model) {
        UserModel user = AppUtils.getUser();
        if (model.menuType == 7) { // 查关系
            if (user == null) {
                mContext.startActivity(new Intent(mContext, LoginActivity.class));
                return;
            }
            if (user.vipStatus == 0) {
                new VIPDialog((Activity) mContext).show();
            } else {
                mContext.startActivity(RelationActivity.getIntent(mContext, model));
            }
        } else if (model.menuType == 8) { // 风险分析
            if (user == null) {
                mContext.startActivity(new Intent(mContext, LoginActivity.class));
                return;
            }
            if (user.vipStatus == 0) {
                mContext.startActivity(new Intent(mContext, RiskTipActivity.class));
                return;
            } else {
                mContext.startActivity(TypeSearchActivity.getIntent(mContext, model.menuType));
            }
        } else if (model.menuType == -1) {// h5跳转
            mContext.startActivity(WebActivity.getIntent(mContext, model.menuName, model.menuUrl));
        } else if (model.menuType == 9) {
            Intent intent = new Intent(mContext, CreditReportActivity.class);
            mContext.startActivity(intent);
        } else if (model.menuType == 10) {
            Intent intent = new Intent(mContext, CompanyAmendActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("company", null);
            bundle.putInt(CompanyAmendActivity.TYPE, CompanyAmendActivity.TYPE_OBJECTION);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        } else if (model.menuType == 12) { // 信用承诺
            mContext.startActivity(new Intent(mContext, CreditCommitmentActivity.class));
        } else if (model.menuType == 13) { // 访客
            mContext.startActivity(new Intent(mContext, VisitorListActivity.class));
        } else if (model.menuType == 14) {
            Intent intent = new Intent(mContext, ReportInfoActivity.class);
//            if (companyInfo != null) {
//                intent.putExtra("companyName", companyInfo.companyName);
//                intent.putExtra("companyId", companyInfo.companyId);
//            }
            mContext.startActivity(intent);
        } else {
            mContext.startActivity(TypeSearchActivity.getIntent(mContext, model.menuType));
        }
    }
}
