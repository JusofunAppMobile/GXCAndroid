package com.jusfoun.jusfouninquire.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.constant.LoginConstant;
import com.jusfoun.jusfouninquire.net.constant.NetConstant;
import com.jusfoun.jusfouninquire.net.model.BaseModel;
import com.jusfoun.jusfouninquire.net.model.BottomMenuModel;
import com.jusfoun.jusfouninquire.net.model.ContactModel;
import com.jusfoun.jusfouninquire.net.model.ContactsModel;
import com.jusfoun.jusfouninquire.net.model.LoginModel;
import com.jusfoun.jusfouninquire.net.model.ShareModel;
import com.jusfoun.jusfouninquire.net.model.UserInfoModel;
import com.jusfoun.jusfouninquire.net.route.LoginRegisterHelper;
import com.jusfoun.jusfouninquire.net.route.PostPushId;
import com.jusfoun.jusfouninquire.service.event.CenterViewChangedEvent;
import com.jusfoun.jusfouninquire.service.event.CompleteLoginEvent;
import com.jusfoun.jusfouninquire.service.event.FollowSucceedEvent;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.service.event.LoginOutIconEvent;
import com.jusfoun.jusfouninquire.service.event.LogoutEvent;
import com.jusfoun.jusfouninquire.service.event.MsgChangeEvent;
import com.jusfoun.jusfouninquire.service.event.PaySuccessEvent;
import com.jusfoun.jusfouninquire.service.event.RefreshHomeEvent;
import com.jusfoun.jusfouninquire.service.event.UpdateMainEvent;
import com.jusfoun.jusfouninquire.sharedpreference.LastLoginSharePreference;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.activity.BaseInquireActivity;
import com.jusfoun.jusfouninquire.ui.activity.EditPersonActivity;
import com.jusfoun.jusfouninquire.ui.activity.ExportContactsActivity;
import com.jusfoun.jusfouninquire.ui.activity.FeedbackActivity;
import com.jusfoun.jusfouninquire.ui.activity.ForgetPwdActivity;
import com.jusfoun.jusfouninquire.ui.activity.LoginActivity;
import com.jusfoun.jusfouninquire.ui.activity.MyAttentionActivity;
import com.jusfoun.jusfouninquire.ui.activity.MyReportActivity;
import com.jusfoun.jusfouninquire.ui.activity.RegisterPhoneActivity;
import com.jusfoun.jusfouninquire.ui.activity.SupplementRegisterInfoActivity;
import com.jusfoun.jusfouninquire.ui.activity.SystemMessageActivity;
import com.jusfoun.jusfouninquire.ui.activity.WebActivity;
import com.jusfoun.jusfouninquire.ui.util.Md5Util;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;
import com.jusfoun.jusfouninquire.ui.util.ShareUtil;
import com.jusfoun.jusfouninquire.ui.util.ThirdLoginUtil;
import com.jusfoun.jusfouninquire.ui.view.LoginView;
import com.jusfoun.jusfouninquire.ui.view.PersonCenterView;
import com.jusfoun.jusfouninquire.ui.view.SettingBottomView;
import com.jusfoun.jusfouninquire.ui.widget.BindPhoneDialog;
import com.jusfoun.jusfouninquire.ui.widget.EmailSendDialog;
import com.jusfoun.jusfouninquire.ui.widget.GeneralDialog;
import com.jusfoun.jusfouninquire.ui.widget.ShareDialog;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;
import netlib.util.EventUtils;


/**
 * @author zhaoyapeng
 * @version create time:15/10/30上午10:37
 * @Email zyp@jusfoun.com
 * @Description ${个人中心fragment}
 */
public class PersonalFragment extends BaseInquireFragment implements LoginView.LoginViewListener, PersonCenterView.PersonCenterListener {
    /**
     * 常量
     */
    private static final int CHANGE_USER_INFO = 1;

    /**
     * 组件
     */
    private LoginView mLoginView;
    private PersonCenterView personCenterView;
    private GeneralDialog dialog;
    private ShareDialog shareDialog;
    /**
     * 对象
     */

    private ValueAnimator valueAnimator1, valueAnimator2, valueAnimator3, valueAnimator4, animLoginOut, animLoginIn;
    private ShareUtil shareUtil;
    /**
     * 变量
     */
    private String account, password;

    private boolean willDoLogout = false;//下次将要执行的操作（本次执行完毕）是否是“退出”，即 已经登录则将要执行的操作是退出，反之，亦然

    private int CODE_NET_GET_INFO = 1002;
    private int padding = 0;
    private LinearLayout rootLayout;

    private ContactModel contactModel, shareholderModel;

    public static PersonalFragment getInstance(int padding) {
        PersonalFragment personalFragment = new PersonalFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("padding", padding);
        personalFragment.setArguments(bundle);
        return personalFragment;
    }

    @Override
    protected void initData() {
        shareDialog = new ShareDialog(mContext, R.style.tool_dialog);
        shareDialog.setCancelable(true);
        Window shareWindow = shareDialog.getWindow();
        shareWindow.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        shareWindow.setWindowAnimations(R.style.share_dialog_style); // 添加动画

        shareUtil = new ShareUtil(mContext);

        padding = getArguments().getInt("padding", 0);
        padding = getArguments().getInt("padding", 0);
        if (InquireApplication.getUserInfo() != null
                && !TextUtils.isEmpty(InquireApplication.getUserInfo().getUserid())) {
            willDoLogout = true;
        }
        dialog = new GeneralDialog(mContext, R.style.my_dialog);

    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        mLoginView = (LoginView) view.findViewById(R.id.loginView);
        personCenterView = (PersonCenterView) view.findViewById(R.id.personCenterView);
        rootLayout = (LinearLayout) view.findViewById(R.id.layout_root);
        return view;
    }

    @Override
    protected void initWeightActions() {

        valueAnimator1 = ObjectAnimator.ofFloat(mLoginView, "rotationY", 0, 90f);
        valueAnimator2 = ObjectAnimator.ofFloat(personCenterView, "rotationY", -90, 0f);
        valueAnimator3 = ObjectAnimator.ofFloat(mLoginView, "rotationY", 90f, 0f);
        valueAnimator4 = ObjectAnimator.ofFloat(personCenterView, "rotationY", 0f, -90f);

        animLoginOut = ObjectAnimator.ofFloat(mLoginView, "alpha", 1, 0.5f);
        animLoginIn = ObjectAnimator.ofFloat(mLoginView, "alpha", 0.5f, 1);

        personCenterView.setPersonViewListener(this);
        mLoginView.setLoginViewListener(this);

        if (willDoLogout) {
            mLoginView.setVisibility(View.GONE);
            personCenterView.setVisibility(View.VISIBLE);
        } else {
            mLoginView.setAccountEdit(LastLoginSharePreference.getUserAccount(mContext));
        }
        WindowManager window = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = window.getDefaultDisplay().getWidth();

        mLoginView.setCameraDistance(10 * screenWidth);
        personCenterView.setCameraDistance(10 * screenWidth);
        setViewData();

        flipCard();
        flipCardTwo();


        Message msg = new Message();
        msg.what = CODE_NET_GET_INFO;
        handler.sendMessageDelayed(msg, 200);
        personCenterView.setPadding(padding);
        mLoginView.setPadding(padding);
//        rootLayout.setPadding(0,padding,0,0);

        shareDialog.setShareListener(new ShareDialog.ShareListener() {
            @Override
            public void onFriendersShare() {
                ShareModel model = new ShareModel();
                model.setContent(mContext.getString(R.string.share_content_setting));
                model.setTitle(mContext.getString(R.string.share_title_setting));
                model.setUrl(mContext.getString(R.string.share_url_seting));
                shareUtil.shareByType(mContext, model, SHARE_MEDIA.WEIXIN_CIRCLE);
            }

            @Override
            public void onWechatShare() {
                ShareModel model = new ShareModel();
                model.setContent(mContext.getString(R.string.share_content_setting));
                model.setTitle(mContext.getString(R.string.share_title_setting));
                model.setUrl(mContext.getString(R.string.share_url_seting));
                shareUtil.shareByType(mContext, model, SHARE_MEDIA.WEIXIN);
            }

            @Override
            public void onSinaShare() {
                ShareModel model = new ShareModel();
                model.setContent(mContext.getString(R.string.share_content_setting));
                model.setTitle(mContext.getString(R.string.share_title_setting));
                model.setUrl(mContext.getString(R.string.share_url_seting));
                shareUtil.shareByType(mContext, model, SHARE_MEDIA.SINA);
            }
        });
    }

    /**
     * 展示分享框
     */
    public void showShareDialog() {
        shareDialog.show();
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = shareDialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); // 设置宽度
        shareDialog.getWindow().setAttributes(lp);
    }

    public void startLoginAnim() {
        animLoginOut.setInterpolator(new LinearInterpolator());
        animLoginOut.setDuration(500);
        animLoginOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mLoginView.setVisibility(View.GONE);
                personCenterView.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        personCenterView.startLoginAnim();
                    }
                }, 100);
            }
        });
        animLoginOut.start();
    }

    public void startLogoutAnim() {
        personCenterView.startLogoutAnim(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                personCenterView.setVisibility(View.GONE);
                mLoginView.setVisibility(View.VISIBLE);

                animLoginIn.setInterpolator(new LinearInterpolator());
                animLoginIn.setDuration(500);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animLoginIn.start();
                    }
                }, 100);
            }
        });
    }


    /**
     * 登录与未登录切换
     */
    private void flipCard() {

        valueAnimator2.setInterpolator(new LinearInterpolator());
        valueAnimator2.setDuration(500);
        valueAnimator2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Log.e("tag", "flipCard1 end   ");
                /*new Thread(new Runnable() {
                    @Override
                    public void run() {

                    }
                }).start();   李亮改，线程中不能发送event*/
                EventBus.getDefault().post(new CompleteLoginEvent(LoginConstant.LOGIN));

            }
        });

        valueAnimator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mLoginView.setVisibility(View.GONE);
                personCenterView.setVisibility(View.VISIBLE);
                valueAnimator2.start();
            }
        });
        valueAnimator1.setInterpolator(new LinearInterpolator());
        valueAnimator1.setDuration(500);
    }

    private void setViewData() {
        UserInfoModel userinfo = InquireApplication.getUserInfo();
        if (userinfo != null) {
            personCenterView.setUserCompany(userinfo.getCompany());
            personCenterView.setUserName(userinfo.getNickname());
            personCenterView.setUserJob(userinfo.getJob());
            personCenterView.setBackAlpha();
            Log.d("TAG", "设置新个人信息");
            personCenterView.setUserHeadImage(userinfo.getPhoto() + "");
            personCenterView.setFollow(userinfo.getMyfocuscount());
            personCenterView.dealUnreadMsg();
            if (TextUtils.isEmpty(userinfo.getSystemmessageunread()))
                personCenterView.setMessage("0");
            else
                personCenterView.setMessage(userinfo.getSystemmessageunread());
            personCenterView.setIsVip(userinfo.vipType == 1);

            getContactInfor();
            getShareholderInfo();
//           refreshView();
        }

    }

    /**
     * 获取股东穿透信息
     */
    private void getShareholderInfo() {

        UserInfoModel userModel = InquireApplication.getUserInfo();
        if (userModel == null || TextUtils.isEmpty(userModel.getUserid())) {
            goActivity(LoginActivity.class);
            return;
        }

        showLoading();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userid", userModel.getUserid());
        showLoading();
        LoginRegisterHelper.getShareholderInfo(mContext, map, ((Activity) mContext).getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                shareholderModel = (ContactModel) data;
            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                showToast(error);
            }
        });

    }


    /**
     * 获取个人通讯录信息
     */
    private void getContactInfor() {

        UserInfoModel userModel = InquireApplication.getUserInfo();
        if (userModel == null || TextUtils.isEmpty(userModel.getUserid())) {
            goActivity(LoginActivity.class);
            return;
        }

        showLoading();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userid", userModel.getUserid());
        showLoading();
        LoginRegisterHelper.getContactUser(mContext, map, ((Activity) mContext).getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                contactModel = (ContactModel) data;
            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                showToast(error);
            }
        });

    }

    /**
     * 切换至登录
     */
    private void flipCardTwo() {
        valueAnimator3.setInterpolator(new LinearInterpolator());
        valueAnimator3.setDuration(500);
        valueAnimator3.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                Log.e("tag", "flipCardTwo2");
                //退出动画执行 发送event到首页
                EventBus.getDefault().post(new CompleteLoginEvent(LoginConstant.LOGIN_OUT));
            }
        });

        valueAnimator4.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                Log.e("tag", "flipCardTwo1");
                mLoginView.setVisibility(View.VISIBLE);
                personCenterView.setVisibility(View.GONE);
                valueAnimator3.start();
            }
        });

        valueAnimator4.setInterpolator(new LinearInterpolator());
        valueAnimator4.setDuration(500);

    }


    //Loginview 页面的一些点击监听事件
    @Override
    public void viewOnClick(int viewId) {
        switch (viewId) {
            case LoginView.LOGIN_BTN:
                EventUtils.event(mContext, EventUtils.SEARCH02);
                if (!valueAnimator1.isRunning() &&
                        !valueAnimator2.isRunning() && !valueAnimator3.isRunning() && !valueAnimator4.isRunning()) {
                    regularLogin();
                }
                break;
            case LoginView.REGISTER_BTN:
                Intent intent = new Intent(mContext, RegisterPhoneActivity.class);
                intent.putExtra(RegisterPhoneActivity.REGISTER_TAG, true);
                startActivity(intent);
                //goActivity(RegisterPhoneActivity.class);
                break;
            case LoginView.FORGET_BTN:
                goActivity(ForgetPwdActivity.class);
                break;
            case LoginView.WEIBO_LOGIN_BTN:
                ThirdLoginUtil.getThirdLoginUtil(mContext).thirdLogin(new ThirdLoginUtil.Callback() {
                    @Override
                    public void callback(String openid, String nickname, String headImg, String unionid) {
                        Log.e("tag", "uid-" + openid + "nickname-" + nickname + "headImg-" + headImg);
                        loginFromOther(NetConstant.TYPE_SINA, openid, nickname, headImg, unionid);
                    }
                }, SHARE_MEDIA.SINA);
                break;
            case LoginView.WECHAT_LOGIN_BTN:
                Log.d("TAG", "微信登录");
                ThirdLoginUtil.getThirdLoginUtil(mContext).thirdLogin(new ThirdLoginUtil.Callback() {
                    @Override
                    public void callback(String openid, String nickname, String headImg, String unionid) {
                        Log.e("tag", "uid-" + openid + "nickname-" + nickname + "headImg-" + headImg);
                        loginFromOther(NetConstant.TYPE_WECHAT, openid, nickname, headImg, unionid);
                    }
                }, SHARE_MEDIA.WEIXIN);
                break;
            default:
                break;
        }
    }

    //个人中心 点击事件
    @Override
    public void personViewOnClick(int viewId) {
        switch (viewId) {
            case PersonCenterView.ATTENTION_BTN:
                EventUtils.event(mContext, EventUtils.USER01);
                goActivity(MyAttentionActivity.class);
                break;
            case PersonCenterView.SYSTEM_MES_BTN:
                EventUtils.event(mContext, EventUtils.USER02);
                goActivity(SystemMessageActivity.class);
                break;
            case PersonCenterView.FEED_BACK_BTN:
                EventUtils.event(mContext, EventUtils.USER03);
                goActivity(FeedbackActivity.class);
                break;
            case PersonCenterView.LOGOUT_BTN:

                if (!valueAnimator1.isRunning() &&
                        !valueAnimator2.isRunning() && !valueAnimator3.isRunning() && !valueAnimator4.isRunning()) {
                    dialog.setTitleTextViewIsShow(false);
                    dialog.setButtonText("取消", "确定");
                    dialog.setMessageText("是否确定退出？");
                    dialog.setLeftListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.setRightListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            logout();
                        }
                    });
                    dialog.show();
                }
                break;
            case PersonCenterView.EDIT_USERINFO:
                UserInfoModel model = InquireApplication.getUserInfo();
                if (model == null) return;
                Intent intent = new Intent(mContext, EditPersonActivity.class);
                intent.putExtra(EditPersonActivity.REGISTER_TAG, false);
                intent.putExtra(EditPersonActivity.PHONE_NUM_KEY, model.getMobile());
                startActivityForResult(intent, CHANGE_USER_INFO);
                break;
            case PersonCenterView.SHARE:
                showShareDialog();
                break;
            case PersonCenterView.MY_REPORT:
                goActivity(MyReportActivity.class);
                break;
            case PersonCenterView.VIP:
                WebActivity.startVipPage(getActivity());
                break;
            case PersonCenterView.VIP_CON:
                if (contactModel != null && contactModel.data != null)
                    WebActivity.startVipContactPage(getActivity(), contactModel.data.contactsUrl);
                break;
            case PersonCenterView.VIP_SHARE:
                if (shareholderModel != null && shareholderModel.data != null)
                    WebActivity.startVipContactPage(getActivity(), shareholderModel.data.url);
                break;
        }
    }

    /**
     * 修改用户信息
     */
    private void goToCompleteUserinfo() {
        Intent intent = new Intent(mContext, EditPersonActivity.class);
        intent.putExtra(SupplementRegisterInfoActivity.REGISTER_TAG, false);
        intent.putExtra(SupplementRegisterInfoActivity.PHONE_NUM_KEY, InquireApplication.getUserInfo().getMobile());
        startActivityForResult(intent, CHANGE_USER_INFO);
    }

    /**
     * 第三方登录
     *
     * @param type
     * @param nikeName
     * @param photo
     */
    private void loginFromOther(String type, String openid, String nikeName, String photo, String unionid) {
        Log.d("TAG", "第三方登录");
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("thirdToken", openid);
        map.put("logintype", type);
        map.put("pushid", JPushInterface.getRegistrationID(mContext));
        map.put("nickname", nikeName);
        map.put("photo", photo);
        map.put("unionid", unionid);

        showLoading();
        LoginRegisterHelper.doNetPostToLogin(mContext, map, ((Activity) mContext).getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                LoginModel model = (LoginModel) data;
                if (model.getResult() == 0) {
                    if (TextUtils.isEmpty(model.getUserinfo().getMobile())) {
                        new BindPhoneDialog((BaseInquireActivity) mContext, model.getUserinfo().getUserid(), new BindPhoneDialog.OnSuccessListener() {
                            @Override
                            public void suc(LoginModel model) {
                                loginSucByThird(model);
                            }
                        }).show();
                    } else {
                        loginSucByThird(model);
                    }
                } else {
                    showToast(model.getMsg() + "");
                }
            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                showToast(error);
            }
        });
    }

    private void loginSucByThird(LoginModel model) {
        EventBus.getDefault().post(new RefreshHomeEvent());
        LoginSharePreference.saveUserInfo(model.getUserinfo(), mContext);
        InquireApplication.setUserInfo(model.getUserinfo());
        setViewData();
        willDoLogout = false;
        LoginOutIconEvent loginOutIconEvent = new LoginOutIconEvent();
        loginOutIconEvent.setLogin(true);
        EventBus.getDefault().post(loginOutIconEvent);
        startAnim();
    }

    /**
     * 正常登录
     */
    private void regularLogin() {
        Log.e("tag", "JPushInterface-RegistrationID=" + JPushInterface.getRegistrationID(mContext));
        account = mLoginView.getAccountEdit();
        password = mLoginView.getPasswordEdit();

        if (TextUtils.isEmpty(account)) {
            showToast("请输入帐号");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            showToast("请输入密码");
            return;
        }
        showLoading();
        mLoginView.setLoginBtnEnble(false);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("phonenumber", account);
        map.put("password", Md5Util.getMD5Str(password));
        map.put("logintype", NetConstant.TYPE_PHONE);
        map.put("pushid", JPushInterface.getRegistrationID(mContext));
//        Log.e("tag", "map=" + map);
        LoginRegisterHelper.doNetPostToLogin(mContext, map, ((Activity) mContext).getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                mLoginView.setLoginBtnEnble(true);
                LoginModel model = (LoginModel) data;
                if (model.getResult() == 0) {
                    EventBus.getDefault().post(new RefreshHomeEvent());
                    LastLoginSharePreference.saveUserAccount(account, mContext);

                    LoginSharePreference.saveUserInfo(model.getUserinfo(), mContext);
                    InquireApplication.setUserInfo(model.getUserinfo());
                    setViewData();
                    willDoLogout = false;

                    LoginOutIconEvent loginOutIconEvent = new LoginOutIconEvent();
                    loginOutIconEvent.setLogin(true);
                    EventBus.getDefault().post(loginOutIconEvent);
                    startAnim();

                } else {
                    showToast(model.getMsg() + "");
                }
            }

            @Override
            public void onFail(String error) {

                Log.e("tag", "errorerrorerror=" + error);
                mLoginView.setLoginBtnEnble(true);
                hideLoadDialog();
                Toast.makeText(mContext, R.string.net_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 启动时重新获取个人信息
     *
     * @param userid
     */
    private void getUserInfoFromNet(String userid) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userid", userid);

        LoginRegisterHelper.doNetGetUserAllInfo(mContext, map, ((Activity) mContext).getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                LoginModel model = (LoginModel) data;
                if (model.getResult() == 0) {
                    LastLoginSharePreference.saveUserAccount(account, mContext);
                    LoginSharePreference.saveUserInfo(model.getUserinfo(), mContext);
                    InquireApplication.setUserInfo(model.getUserinfo());
                    setViewData();

                } else {
                    showToast(model.getMsg() + "");
                }
            }

            @Override
            public void onFail(String error) {

            }
        });

    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                willDoLogout = false;
            /*personCenterView.setVisibility(View.VISIBLE);
            mLoginView.setVisibility(View.GONE);*/

                setViewData();
                startAnim();
            } else if (msg.what == CODE_NET_GET_INFO) {

                if (LoginSharePreference.getUserInfo(mContext) != null && LoginSharePreference.getUserInfo(mContext).getUserid() != null && !LoginSharePreference.getUserInfo(mContext).getUserid().equals("")) {
                    getUserInfoFromNet(LoginSharePreference.getUserInfo(mContext).getUserid());
                }
            }
        }
    };

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof CompleteLoginEvent) {
            //登录状态：本页面+其他页面登录成功或注册成功||||退出状态：本页面退出成功
            if (((CompleteLoginEvent) event).getIsLogin() == LoginConstant.REGISTER) {
//                postPushId();
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessageDelayed(msg, 1000);


            }
        } else if (event instanceof CenterViewChangedEvent) {
//            refreshView();
        } else if (event instanceof UpdateMainEvent) {
           /* UpdateMainEvent ev = (UpdateMainEvent) event;
            if ((ev.getModel() != null) && (!TextUtils.isEmpty(ev.getModel().getPage_name()))){
                if ("profilePage".equals(ev.getModel().getPage_name())){
                    if (InquireApplication.getUserInfo() != null){
                        refreshBottomView(ev);
                    }
                }
            }*/

        } else if (event instanceof FollowSucceedEvent) {
            FollowSucceedEvent succeedEvent = (FollowSucceedEvent) event;
            personCenterView.setFollow(succeedEvent.getCount() + "");
        } else if (event instanceof LogoutEvent) {
            logout();
        } else if (event instanceof MsgChangeEvent) {
            MsgChangeEvent msgChangeEvent = (MsgChangeEvent) event;
            UserInfoModel userInfoModel = LoginSharePreference.getUserInfo(mContext);
            if (userInfoModel != null && !TextUtils.isEmpty(userInfoModel.getSystemmessageunread())) {
                try {
                    int count = Integer.parseInt(userInfoModel.getSystemmessageunread());
                    userInfoModel.setSystemmessageunread(count + msgChangeEvent.getDelta() + "");
                    LoginSharePreference.saveUserInfo(userInfoModel, mContext);
                    personCenterView.dealUnreadMsg();
                } catch (Exception e) {

                }
            }
        } else if (event instanceof PaySuccessEvent) {
            if (LoginSharePreference.getUserInfo(mContext) != null && LoginSharePreference.getUserInfo(mContext).getUserid() != null && !LoginSharePreference.getUserInfo(mContext).getUserid().equals("")) {
                getUserInfoFromNet(LoginSharePreference.getUserInfo(mContext).getUserid());
            }
        }
    }

    private void refreshBottomView(UpdateMainEvent event) {
        List<BottomMenuModel> list = new ArrayList<>();
        list.addAll(event.getModel().getBottom_menu());
        if (list.size() == 0) {
            return;
        }
        personCenterView.removeAllViews();
        for (final BottomMenuModel model : list) {
            SettingBottomView view = new SettingBottomView(mContext);
            view.setViewText(model.getTitle());
            if ((ViewType.RangeBegin.ordinal() < model.getTag()) && (model.getTag() < ViewType.RangeEnd.ordinal())) {
                personCenterView.addView(view);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(model.getUmeng_analytics())) {
                        EventUtils.event2(mContext, model.getUmeng_analytics());
                    }
                    if (model.getTag() == ViewType.URL.ordinal()) {
                        Intent intent = new Intent(mContext, WebActivity.class);
                        intent.putExtra(WebActivity.TITLE, model.getTitle());
                        intent.putExtra(WebActivity.URL, model.getUrl());
                        mContext.startActivity(intent);
                    } else if (model.getTag() == ViewType.FeedBack.ordinal()) {
                        Intent intent = new Intent(mContext, FeedbackActivity.class);
                        startActivity(intent);
                    } else if (model.getTag() == ViewType.ShareDialog.ordinal()) {

                    } else if (model.getTag() == ViewType.EDITPERSON.ordinal()) {
                        //跳转到修改个人信息界面

                    }
                }
            });
        }
    }

    private void refreshView() {
        UserInfoModel userinfo = LoginSharePreference.getUserInfo(mContext);
        if (userinfo != null) {
            if (!TextUtils.isEmpty(userinfo.getMyfocusunread())) {
                int myfocusunread = Integer.parseInt(userinfo.getMyfocusunread());
                /*if(myfocusunread > 0){
                    personCenterView.setAttentionTipPoint(true);
                }else {
                    personCenterView.setAttentionTipPoint(false);
                }*/
            }
            if (!TextUtils.isEmpty(userinfo.getSystemmessageunread())) {
                int systemmessageunread = Integer.parseInt(userinfo.getSystemmessageunread());
                /*if(systemmessageunread > 0){
                    personCenterView.setSystemMsgTipPoint(true);
                }else {
                    personCenterView.setSystemMsgTipPoint(false);
                }*/
            }
        }

    }

    /**
     * 发送唯一标示
     */
    private void postPushId() {
        HashMap<String, String> params = new HashMap<>();
        params.put("pushid", JPushInterface.getRegistrationID(mContext));
        if (LoginSharePreference.getUserInfo(mContext) != null
                && !TextUtils.isEmpty(LoginSharePreference.getUserInfo(mContext).getUserid())) {
            params.put("userid", LoginSharePreference.getUserInfo(mContext).getUserid());
        } else {
            params.put("userid", "");
        }
        params.put("deveiceid", PhoneUtil.getIMEI(mContext));
        showLoading();
        PostPushId.postPushId(mContext, params, new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                BaseModel baseModel = (BaseModel) data;
                Toast.makeText(mContext, baseModel.getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String error) {
                Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 退出登录
     */
    private void logout() {
        String userId = "";
        if (InquireApplication.getUserInfo() != null) {
            if (!TextUtils.isEmpty(InquireApplication.getUserInfo().getUserid())) {
                userId = InquireApplication.getUserInfo().getUserid();
            }
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("userid", userId);
        LoginRegisterHelper.doNetGetLogout(mContext, map, ((Activity) mContext).getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                BaseModel model = (BaseModel) data;
                if (model.getResult() == 0) {
                    mLoginView.setPasswordEdit("");

                    LoginOutIconEvent loginOutIconEvent = new LoginOutIconEvent();
                    loginOutIconEvent.setLogin(false);
                    EventBus.getDefault().post(loginOutIconEvent);
                } else {
                    Log.d("TAG", model.getMsg());
                }
            }

            @Override
            public void onFail(String error) {
                Log.d("TAG", "退出失败");
            }
        });
        //无论接口调不调的成功都执行退出
        InquireApplication.deleteUserInfo();
        LoginSharePreference.deleteUserInfo(mContext);
        mLoginView.setAccountEdit(LastLoginSharePreference.getUserAccount(mContext));
        willDoLogout = true;
        startAnim();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == CHANGE_USER_INFO) {
            setViewData();
        }
    }

    /**
     * 开始翻转动画
     */
    protected void startAnim() {
        if (willDoLogout) {
//            valueAnimator4.start();
            startLogoutAnim();
        } else {
//            valueAnimator1.start();
            startLoginAnim();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

//        refreshView();
    }



 /*   @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            if (isVisibleToUser){
                try {
                    String value = LibIOUtil.convertStreamToJson(getResources().openRawResource(R.raw.test));
                    Type listType = new TypeToken<List<BottomMenuModel>>() {
                    }.getType();
                    List<BottomMenuModel> list = new Gson().fromJson(value, listType);
                    UpdateMainEvent event = new UpdateMainEvent();
                    UpdateMainModel mainModel = new UpdateMainModel();
                    mainModel.setPage_name("profilePage");
                    mainModel.setBottom_menu(list);
                    event.setModel(mainModel);
                    EventBus.getDefault().post(event);

                } catch (Exception exception) {

                }
            }
        }

    }*/

}
