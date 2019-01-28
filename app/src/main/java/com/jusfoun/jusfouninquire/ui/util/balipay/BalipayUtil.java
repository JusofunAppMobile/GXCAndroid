package com.jusfoun.jusfouninquire.ui.util.balipay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.constant.NetConstant;
import com.jusfoun.jusfouninquire.net.model.UserInfoModel;
import com.jusfoun.jusfouninquire.net.util.GetParamsUtil;
import com.jusfoun.jusfouninquire.service.event.PaySuccessEvent;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.activity.BaseInquireActivity;
import com.jusfoun.jusfouninquire.ui.util.LogUtil;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;
import com.jusfoun.jusfouninquire.ui.util.StringUtil;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;
import com.jusfoun.jusfouninquire.ui.widget.AgreementDialog;
import com.jusfoun.jusfouninquire.ui.widget.VipRenewDialog;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import netlib.util.AppUtil;

import com.jusfoun.jusfouninquire.TimeOut;

/**
 * @author zhaoyapeng
 * @version create time:17/11/2109:13
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class BalipayUtil {
//    /**
//     * 支付宝支付业务：入参app_id
//     */
//    public final String APPID = "2017110709779026";
//
//    /**
//     * 支付宝账户登录授权业务：入参pid值
//     */
//    public final String PID = "2088821652926803";
//    /**
//     * 支付宝账户登录授权业务：入参target_id值
//     */
//    public static final String TARGET_ID = "";
//
//    /** 商户私钥，pkcs8格式 */
//    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
//    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
//    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
//    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
//    /**
//     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
//     */
//    public final String RSA2_PRIVATE = "MIIEowIBAAKCAQEAtzgQ6DUjvnBJATP2LEWINnT0j6ysiQqy34GwkKpT4qMfNWdtp6qN2uQFLww8kyCqbcYlEcV2eMF8lHPNIlPGhmIEIhrDIiN9fl0BLlIeAjiElWIGMzuH686QgalZHvAD56CjINIwTF7FiHkaBxMgl3qq1aXR04Zu4IRriNwdFcc6xGDx4J4Kh2WJoSZIuvww0SjnCr/mwGhKXlnEeNTHSQm3k07UxgnqO/x9vr0Nk2fo7Jr0WVFlxoTdVN0MtiCdzeeBgQPPvrVMrfRRjGmxvsqlLAZelDBA/28xhoHSCYH9W95PcFIhbDq2hnjshPacPL0D6NGhTvR9JOtmWV7bLQIDAQABAoIBAQCk5ikRVQJem+CY3JNrNQlrOcgCp36BuMdUsfyftyzYhcfI4NWoWbBimWaw+WprYLMDKZqja/08oafmVHMDujKrL/xYVY3aY+bGnB47+lxX01ZAvICoC6RBbyBQEoLLfWmGRuWK3KHrmkBem9/5DhX/P8ARmbRHlG6mU4gVHUZwGeF3cywerGRZN69G1ro8+1oXomoyzMZDIB90EbiuNONAThF3bdvh39HA6qTLpcI/o3mAtKxYVK1C4n6bQ2xM0ab3eNCOjmH7s9fSrl8L6IIr5F8nN7Gvc23eZ9pxk9atasrL5DvqAMG4HSy0JOnrkmDtBY/aTnJH6U2wme6TD+9hAoGBAOGZpR0V8Tml7PEOY+XhS4xcreI3BDC9M6HQAHiLaXakQed74H4ldiXGuJCpu1T0WV+y4r4u1OV2kVAqviX/E2ijBhP6CWcP1KJl0PJCAg8yy7jLXkbjESpyJFnwUw8TsSOmEy96K8myMlS4PL3FDMxcqFHX26dVL0Y0UxCT0J/jAoGBAM/obj+WGtWl3zH6chsZn1IMZrvSAGt3Y95Abd2eQ+ZoozV9ECjvacYNU1FLivLlGXfho70j/TBQ/ntug0hH4ghfRqPnTLIt0/oCHPncIci4nnRUnQM1xO22NDM+n4Adxg0adGRV2yerLUUV26p1oDfVW8iZ1uptexhXlHwML2WvAoGAXFZOxoDcowPO3ztMmCKGkYS9hwGNrYVDRM+l4bDuEalZcC27zOX4Xa3gjRVrn3IDwq+tLrpsWR8WiwFzoomsn/9Z1YNZpd1M4stZok5yGVZpG9HhXJJDzGO7jTRYSqt8rWKkJ6KRmZReFV7zPZkRi/PA0P7Q4MR0/P8doINfuJECgYAUlEYtZxc65JNGKCsZQZ0CixW0K6I9APFSs3/setjvupXFGp753lDS+MixeE/FoSW3Nw91DlXmbW3zG8pE4lCeUYf1e1SdImokCgeCN+bkLloI28M66RyYBvv9wooZLVlIPMEemVz6/Tea6gH3SnNUc3sFTSz3wWR40VpyOOaMIwKBgAtEw5XY2OXd8wGnthALsZmNt5+DEwwwB+cDJto++76hBVxLgA6UdPor+G/KXE/bOvt7OlJQKvw1Bbba1VSwh1511tqHqIXmnRly05LSJKEbiOC2PhN2nH0YDEX9V8xSU3HQyhol3eD8Vg1ySBo073iGSlTbWn3Sci24eFwDGIGu";
//    public final String RSA_PRIVATE = "";


//    /**
//     * 支付宝支付业务：入参app_id
//     */
//    public static final String APPID = "2016082700318896";
//
//    /**
//     * 支付宝账户登录授权业务：入参pid值
//     */
//    public static final String PID = "2088102173181048";
//    /**
//     * 支付宝账户登录授权业务：入参target_id值
//     */
//    public static final String TARGET_ID = "";
//
//    /** 商户私钥，pkcs8格式 */
//    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
//    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
//    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
//    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
//    /**
//     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
//     */
//    public final String RSA2_PRIVATE = "MIIEowIBAAKCAQEAtzgQ6DUjvnBJATP2LEWINnT0j6ysiQqy34GwkKpT4qMfNWdtp6qN2uQFLww8kyCqbcYlEcV2eMF8lHPNIlPGhmIEIhrDIiN9fl0BLlIeAjiElWIGMzuH686QgalZHvAD56CjINIwTF7FiHkaBxMgl3qq1aXR04Zu4IRriNwdFcc6xGDx4J4Kh2WJoSZIuvww0SjnCr/mwGhKXlnEeNTHSQm3k07UxgnqO/x9vr0Nk2fo7Jr0WVFlxoTdVN0MtiCdzeeBgQPPvrVMrfRRjGmxvsqlLAZelDBA/28xhoHSCYH9W95PcFIhbDq2hnjshPacPL0D6NGhTvR9JOtmWV7bLQIDAQABAoIBAQCk5ikRVQJem+CY3JNrNQlrOcgCp36BuMdUsfyftyzYhcfI4NWoWbBimWaw+WprYLMDKZqja/08oafmVHMDujKrL/xYVY3aY+bGnB47+lxX01ZAvICoC6RBbyBQEoLLfWmGRuWK3KHrmkBem9/5DhX/P8ARmbRHlG6mU4gVHUZwGeF3cywerGRZN69G1ro8+1oXomoyzMZDIB90EbiuNONAThF3bdvh39HA6qTLpcI/o3mAtKxYVK1C4n6bQ2xM0ab3eNCOjmH7s9fSrl8L6IIr5F8nN7Gvc23eZ9pxk9atasrL5DvqAMG4HSy0JOnrkmDtBY/aTnJH6U2wme6TD+9hAoGBAOGZpR0V8Tml7PEOY+XhS4xcreI3BDC9M6HQAHiLaXakQed74H4ldiXGuJCpu1T0WV+y4r4u1OV2kVAqviX/E2ijBhP6CWcP1KJl0PJCAg8yy7jLXkbjESpyJFnwUw8TsSOmEy96K8myMlS4PL3FDMxcqFHX26dVL0Y0UxCT0J/jAoGBAM/obj+WGtWl3zH6chsZn1IMZrvSAGt3Y95Abd2eQ+ZoozV9ECjvacYNU1FLivLlGXfho70j/TBQ/ntug0hH4ghfRqPnTLIt0/oCHPncIci4nnRUnQM1xO22NDM+n4Adxg0adGRV2yerLUUV26p1oDfVW8iZ1uptexhXlHwML2WvAoGAXFZOxoDcowPO3ztMmCKGkYS9hwGNrYVDRM+l4bDuEalZcC27zOX4Xa3gjRVrn3IDwq+tLrpsWR8WiwFzoomsn/9Z1YNZpd1M4stZok5yGVZpG9HhXJJDzGO7jTRYSqt8rWKkJ6KRmZReFV7zPZkRi/PA0P7Q4MR0/P8doINfuJECgYAUlEYtZxc65JNGKCsZQZ0CixW0K6I9APFSs3/setjvupXFGp753lDS+MixeE/FoSW3Nw91DlXmbW3zG8pE4lCeUYf1e1SdImokCgeCN+bkLloI28M66RyYBvv9wooZLVlIPMEemVz6/Tea6gH3SnNUc3sFTSz3wWR40VpyOOaMIwKBgAtEw5XY2OXd8wGnthALsZmNt5+DEwwwB+cDJto++76hBVxLgA6UdPor+G/KXE/bOvt7OlJQKvw1Bbba1VSwh1511tqHqIXmnRly05LSJKEbiOC2PhN2nH0YDEX9V8xSU3HQyhol3eD8Vg1ySBo073iGSlTbWn3Sci24eFwDGIGu";
//    public final String RSA_PRIVATE = "";

    private final int SDK_PAY_FLAG = 1;
    private final int SDK_AUTH_FLAG = 2;

    private Context mContext;

    private VipRenewDialog vipRenewDialog;

    private boolean isVip = true;
    private int type = 0;

    public BalipayUtil(final Context mContext) {
        this.mContext = mContext;
        vipRenewDialog = new VipRenewDialog((BaseInquireActivity) mContext);
        vipRenewDialog.setCallBack(new VipRenewDialog.CallBack() {
            @Override
            public void leftOnClick() {
                vipRenewDialog.dismiss();
            }

            @Override
            public void rightOnClick() {
                vipRenewDialog.dismiss();
                new AgreementDialog((BaseInquireActivity) mContext, new AgreementDialog.OnSelectListener() {
                    @Override
                    public void select() {
                        getPayOrder(mContext, isVip, type);
                    }

                }).show();
            }
        });
    }
//
//    /**
//     * 支付宝支付业务
//     *
//     * @param v
//     */
//    public void payV2() {
//        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
//            new AlertDialog.Builder(mContext).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialoginterface, int i) {
//                            //
//                        }
//                    }).show();
//            return;
//        }
//
//        /**
//         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
//         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
//         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
//         *
//         * orderInfo的获取必须来自服务端；
//         */
//        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
//        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//
//        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
//        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
//        final String orderInfo = orderParam + "&" + sign;
//
//        Log.e("tag", "orderInfo=" + orderInfo);
//        Runnable payRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                PayTask alipay = new PayTask((Activity) mContext);
//                Map<String, String> result = alipay.payV2(orderInfo, true);
//                Log.i("msp", result.toString());
//
//                Message msg = new Message();
//                msg.what = SDK_PAY_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        };
//
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
//    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new PaySuccessEvent(msg.arg1 == 0));
                        if (callBack != null) {
                            callBack.paySuccess();
                        }
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                        if (callBack != null) {
                            callBack.payFail();
                        }
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(mContext,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(mContext,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };


    /**
     * 获取 订单信息
     */
    public void getPayOrder(Context context, final boolean isVip, int type) {
        HashMap<String, String> params = new HashMap<>();
        UserInfoModel userInfoModel = LoginSharePreference.getUserInfo(mContext);
        String userid = "";
        if (userInfoModel != null && !TextUtils.isEmpty(userInfoModel.getUserid())) {
            userid = userInfoModel.getUserid();
        }
        params.put("userid", userid);

        TimeOut timeOut = new TimeOut(InquireApplication.application);
        params.put("t", timeOut.getParamTimeMollis() + "");
        params.put("m", timeOut.MD5time() + "");

        String url = "/api/pay/Ali_GenOrder";
        if (type == 0) {
            if (!isVip)
                url = "/api/pay/Ali_GenOrder_Contacts";
        } else {
            url = "/api/Pay/Ali_GenOrder_Common";
            params.put("type", type + "");
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, GetParamsUtil.getParmas(context.getString(R.string.req_url) + url, params),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        LogUtil.e("Alipay", response);

//                        Log.e("tag","StringRequestStringRequest="+response);
                        try {
                            if (!TextUtils.isEmpty(response)) {
                                statrPay(Html.fromHtml(response).toString(), isVip);
                            } else {
                                Toast.makeText(mContext, "请重试", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerParams = new HashMap<>();
                headerParams.put("Connection", "keep-alive");
                headerParams.put("Version", AppUtil.getVersionName(mContext));
                headerParams.put("VersionCode", AppUtil.getVersionCode(mContext) + "");
                headerParams.put("AppType", "0");
                headerParams.put("Channel", StringUtil.getChannelName(mContext));
                headerParams.put("DeviceId", PhoneUtil.getIMEI(mContext));
                headerParams.put("Deviceid", PhoneUtil.getIMEI(mContext));
                Log.e("tag", "deviceid=" + PhoneUtil.getIMEI(mContext));
                return headerParams;
            }
        };
        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(NetConstant.TIMEOUT, 1, 1.0f));
        VolleyUtil.getQueue(context).add(stringRequest);
    }

    protected void statrPay(final String orderInfo, final boolean isVip) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask((Activity) mContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                msg.arg1 = isVip ? 0 : 1;
                mHandler.sendMessage(msg);
            }
        };

        ThreadPoolUtil.threadPool.execute(payRunnable);
    }

    public interface CallBack {
        void paySuccess();

        void payFail();
    }

    public CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }


    public void showRenewDialog(boolean isVip, int type) {
        this.isVip = isVip;
        this.type = type;
        vipRenewDialog.show();
    }
}
