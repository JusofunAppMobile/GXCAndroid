package com.jusfoun.jusfouninquire.net.route;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.model.ContactModel;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.BaseIndustryModel;
import com.jusfoun.jusfouninquire.net.model.BaseModel;
import com.jusfoun.jusfouninquire.net.model.LoginModel;
import com.jusfoun.jusfouninquire.net.model.SearchCompanyModel;
import com.jusfoun.jusfouninquire.net.util.GetParamsUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyErrorUtil;
import com.jusfoun.jusfouninquire.net.volley.VolleyGetRequest;
import com.jusfoun.jusfouninquire.net.volley.VolleyPostRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lee
 * @version create time:2015/11/1015:48
 * @Email email
 * @Description $登录注册系列
 */

/**
 * 注： Get请求无需 在 baseUrl 后添加 “/”
 */
public class LoginRegisterHelper {

    private static final String baseUrl = "";

    /**注册*/
    public static void doNetPOSTToRegister(Context mContext, final HashMap<String, String> map,String tag, final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url)+"/api/Reg/NewRegister?";
        VolleyPostRequest<LoginModel> request = new VolleyPostRequest<LoginModel>(url, LoginModel.class, new Response.Listener<LoginModel>() {
            @Override
            public void onResponse(LoginModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        },mContext){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };

        request.setShouldCache(false);
        request.setTag(tag);
        VolleyUtil.getQueue(mContext).add(request);
    }
    /**登录*/
    public static void doNetPostToLogin(Context mContext, final HashMap<String, String> map,String tag, final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url)+"/api/Login/NewLogin";
        VolleyPostRequest<LoginModel> request = new VolleyPostRequest<LoginModel>(url, LoginModel.class, new Response.Listener<LoginModel>() {
            @Override
            public void onResponse(LoginModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("tag","onErrorResponse="+error.getMessage()+" "+error.networkResponse+" "+error+" ");
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };

        request.setShouldCache(false);
        request.setTag(tag);
        VolleyUtil.getQueue(mContext).add(request);
    }
    /**修改个人信息*/
    public static void doNetPostUpdateUserInfo(Context mContext, final HashMap<String, String> map, String tag,final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url)+"/api/User/NewUpdateUserInfo";
        VolleyPostRequest<LoginModel> request = new VolleyPostRequest<LoginModel>(url, LoginModel.class, new Response.Listener<LoginModel>() {
            @Override
            public void onResponse(LoginModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };

        request.setShouldCache(false);
        request.setTag(tag);
        VolleyUtil.getQueue(mContext).add(request);
    }
    /**
     * 获取个人信息
     */
    public static void doNetGetUserAllInfo(Context mContext, final HashMap<String, String> map,String tag, final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url)+"/api/User/GetUserDetailInfo";
        VolleyGetRequest<LoginModel> request = new VolleyGetRequest<>(GetParamsUtil.getParmas(url, map), LoginModel.class, new Response.Listener<LoginModel>() {

            @Override
            public void onResponse(LoginModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext);
        request.setShouldCache(false);
        request.setTag(tag);
        VolleyUtil.getQueue(mContext).add(request);
    }
    /**
     * 获取通讯录个人信息
     */
    public static void getContactUser(Context mContext, final HashMap<String, String> map,String tag, final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url)+"/api/User/GetContactsLink";
        VolleyGetRequest<ContactModel> request = new VolleyGetRequest<>(GetParamsUtil.getParmas(url, map), ContactModel.class, new Response.Listener<ContactModel>() {

            @Override
            public void onResponse(ContactModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext);
        request.setShouldCache(false);
        request.setTag(tag);
        VolleyUtil.getQueue(mContext).add(request);
    }
    /**
     * 获取股东穿透
     */
    public static void getShareholderInfo(Context mContext, final HashMap<String, String> map,String tag, final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url)+"/api/User/GetGDCTLink";
        VolleyGetRequest<ContactModel> request = new VolleyGetRequest<>(GetParamsUtil.getParmas(url, map), ContactModel.class, new Response.Listener<ContactModel>() {

            @Override
            public void onResponse(ContactModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext);
        request.setShouldCache(false);
        request.setTag(tag);
        VolleyUtil.getQueue(mContext).add(request);
    }




    /**退出登录*/
    public static void doNetGetLogout(Context mContext, final HashMap<String, String> map,String tag, final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url)+"/api/SendPush/Quit";
        VolleyGetRequest<BaseModel> request = new VolleyGetRequest<>(GetParamsUtil.getParmas(url, map), BaseModel.class, new Response.Listener<BaseModel>() {

            @Override
            public void onResponse(BaseModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext);
        request.setShouldCache(false);
        request.setTag(tag);
        VolleyUtil.getQueue(mContext).add(request);
    }

    /**获取验证码*/
    public static void doNetGETAuthCode(Context mContext, final HashMap<String, String> map, final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url)+"/api/PhoneVerification/CreateVerifcode";
        VolleyGetRequest<BaseModel> request = new VolleyGetRequest<>(GetParamsUtil.getParmas(url, map), BaseModel.class, new Response.Listener<BaseModel>() {

            @Override
            public void onResponse(BaseModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext);
        request.setShouldCache(false);
        VolleyUtil.getQueue(mContext).add(request);
    }


    /**124	绑定手机验证码*/
    public static void verifyCodeForByMd5(Context mContext, final HashMap<String, String> map, final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url)+"/api/PhoneVerification/VerifyCodeForByMd5";
        VolleyGetRequest<BaseModel> request = new VolleyGetRequest<>(GetParamsUtil.getParmas(url, map), BaseModel.class, new Response.Listener<BaseModel>() {

            @Override
            public void onResponse(BaseModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext);
        request.setShouldCache(false);
        VolleyUtil.getQueue(mContext).add(request);
    }

    /**123	绑定手机*/
    public static void bindingPhone(Context mContext, final HashMap<String, String> map, final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url)+"/api/Login/BindingPhone";
        VolleyPostRequest<LoginModel> request = new VolleyPostRequest<>(GetParamsUtil.getParmas(url, map), LoginModel.class, new Response.Listener<LoginModel>() {

            @Override
            public void onResponse(LoginModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext);
        request.setShouldCache(false);
        VolleyUtil.getQueue(mContext).add(request);
    }

    /**获取加密验证码*/
    public static void getVerificationMD5(Context mContext, final HashMap<String, String> map,String tag, final NetWorkCallBack callBck){
        String url = mContext.getResources().getString(R.string.req_url)+"/api/PhoneVerification/VerifyCodeByMd5";
        VolleyGetRequest<BaseModel> request = new VolleyGetRequest<>(GetParamsUtil.getParmas(url, map), BaseModel.class, new Response.Listener<BaseModel>() {

            @Override
            public void onResponse(BaseModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext);
        request.setShouldCache(false);
        request.setTag(tag);
        VolleyUtil.getQueue(mContext).add(request);
    }

    /**验证验证码*/
    public static void doNetGETVerification(Context mContext, final HashMap<String, String> map, final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url)+"/api/PhoneVerification/CheckPhoneVerification";
        VolleyGetRequest<BaseModel> request = new VolleyGetRequest<>(GetParamsUtil.getParmas(url, map), BaseModel.class, new Response.Listener<BaseModel>() {

            @Override
            public void onResponse(BaseModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext);
        request.setShouldCache(false);
        VolleyUtil.getQueue(mContext).add(request);
    }

    /**获取行业（选择职位）*/
    public static void doNetGetIndustry(Context mContext, final HashMap<String, String> map, String tag,final NetWorkCallBack callBck) {

        String url = mContext.getResources().getString(R.string.req_url)+"/api/config/QueryDic";
        VolleyGetRequest<BaseIndustryModel> request = new VolleyGetRequest<>(GetParamsUtil.getParmas(url, map), BaseIndustryModel.class, new Response.Listener<BaseIndustryModel>() {

            @Override
            public void onResponse(BaseIndustryModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext);
        request.setShouldCache(false);
        request.setTag(tag);
        VolleyUtil.getQueue(mContext).add(request);
    }

    /**（注册）选择所在公司*/
    public static void doNetGETChoiceCompany(Context mContext, final HashMap<String, String> map, final NetWorkCallBack callBck) {
        String url = baseUrl+"";
        VolleyGetRequest<SearchCompanyModel> request = new VolleyGetRequest<SearchCompanyModel>(url, SearchCompanyModel.class, new Response.Listener<SearchCompanyModel>() {
            @Override
            public void onResponse(SearchCompanyModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        },mContext){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };

        request.setShouldCache(false);
        VolleyUtil.getQueue(mContext).add(request);
    }
    /**忘记密码 提交*/
    public static void doNetPostForgetPwd(Context mContext, final HashMap<String, String> map,String tag, final NetWorkCallBack callBck) {
        String url = mContext.getResources().getString(R.string.req_url)+"/api/Login/ForgetPwd";
        VolleyPostRequest<LoginModel> request = new VolleyPostRequest<LoginModel>(url, LoginModel.class, new Response.Listener<LoginModel>() {
            @Override
            public void onResponse(LoginModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };

        request.setShouldCache(false);
        request.setTag(tag);
        VolleyUtil.getQueue(mContext).add(request);
    }

    /**忘记密码 获取验证码*/
    public static void doNetGETForgetPwdAuthCode(Context mContext, final HashMap<String, String> map, final NetWorkCallBack callBck) {

        String url = mContext.getResources().getString(R.string.req_url)+"/api/PhoneVerification/ForgetPwdVerifcode";
        VolleyGetRequest<BaseModel> request = new VolleyGetRequest<>(GetParamsUtil.getParmas(url, map), BaseModel.class, new Response.Listener<BaseModel>() {

            @Override
            public void onResponse(BaseModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext);
        request.setShouldCache(false);
        VolleyUtil.getQueue(mContext).add(request);
    }


    /**忘记密码 获取验证码*/
    public static void getForgetPwdAuthCodeMD5(Context mContext, final HashMap<String, String> map, String tag,final NetWorkCallBack callBck){
        String url = mContext.getResources().getString(R.string.req_url)+"/api/PhoneVerification/ForgetPwdVerifyCodeByMd5";
        VolleyGetRequest<BaseModel> request = new VolleyGetRequest<>(GetParamsUtil.getParmas(url, map), BaseModel.class, new Response.Listener<BaseModel>() {

            @Override
            public void onResponse(BaseModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext);
        request.setShouldCache(false);
        request.setTag(tag);
        VolleyUtil.getQueue(mContext).add(request);
    }

    /**检索所在公司 */
    public static void doNetGETSearchCompany(Context mContext, final HashMap<String, String> map,String tag, final NetWorkCallBack callBck) {

        String url = mContext.getResources().getString(R.string.req_url)+"/api/Search/SearchLightCompanyNews";
        VolleyPostRequest<SearchCompanyModel> request = new VolleyPostRequest<>(GetParamsUtil.getParmas(url, map), SearchCompanyModel.class, new Response.Listener<SearchCompanyModel>() {

            @Override
            public void onResponse(SearchCompanyModel response) {
                callBck.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBck.onFail(VolleyErrorUtil.disposeError(error));
            }
        }, mContext);
        request.setShouldCache(false);
        request.setTag(tag);
        VolleyUtil.getQueue(mContext).add(request);
    }

    /**
     *更新手机号
     * @param context
     * @param params
     * @param tag
     * @param netWorkCallBack
     */
    public static void updateNumber(Context context,final HashMap<String,String> params,String tag,final NetWorkCallBack netWorkCallBack){
        String url=context.getString(R.string.req_url)+"/Api/User/UpdatePhone";
        VolleyPostRequest<BaseModel> request=new VolleyPostRequest<>(GetParamsUtil.getParmas(url, params), BaseModel.class
                , new Response.Listener<BaseModel>() {
            @Override
            public void onResponse(BaseModel response) {
                netWorkCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkCallBack.onFail(VolleyErrorUtil.disposeError(error));
            }
        },context);
        request.setShouldCache(false);
        request.setTag(tag);
        VolleyUtil.getQueue(context).add(request);

    }




}
