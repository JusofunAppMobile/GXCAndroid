package com.jusfoun.jusfouninquire.ui.view;

import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.UserInfoModel;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.util.LogUtil;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;
import com.jusfoun.jusfouninquire.ui.util.ScrollUtil;

import netlib.util.EventUtils;

/**
 * @author lee
 * @version create time:2015/11/169:54
 * @Email email
 * @Description $个人中心的view
 */

public class PersonCenterView extends LinearLayout implements View.OnClickListener {

    /**
     * 常量
     */
    public static final int ATTENTION_BTN = 1;
    public static final int SYSTEM_MES_BTN = 2;
    public static final int FEED_BACK_BTN = 3;
    public static final int ABOUT_BTN = 4;
    public static final int LOGOUT_BTN = 5;
    public static final int EDIT_USERINFO = 6;
    public static final int SHARE = 7;
    public static final int VIP = 8;
    public static final int MY_REPORT = 9;
    public static final int VIP_CON = 10;
    public static final int VIP_SHARE = 11;


    /**
     * 组件
     */
    private RelativeLayout attentionBtn, messageBtn, edit_layout, layout_share, feedback;
    private SimpleDraweeView headImageView;
    private TextView userName, text_attention, userCompany, userjob, text_record;
    private ImageView ivVipType;
    private View mUnreadIndicator;
    private View vVipRedPoint;

    /**
     * 对象
     */
    private Context mContext;
    private LayoutInflater inflater;
    private PersonCenterListener personCenterListener;
    private View vLeft, vRight, vBottom;


    /**
     * 变量
     */


    private CustomScrollView scrollView;
    private RelativeLayout topLayout, titleBarLayout;
    private ImageView topImage;

    private ScrollUtil scrollUtil;

    private ValueAnimator animLeftOflr, animLeftOfrl, animRightOflr, animRightOfrl, animBottomOftb, animBottomOfbt;

    public PersonCenterView(Context context) {
        super(context);
        mContext = context;
        inflater = LayoutInflater.from(context);
        initView();
    }

    public PersonCenterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        inflater = LayoutInflater.from(context);
        initView();
    }

    public PersonCenterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        inflater = LayoutInflater.from(context);
        initView();
    }

    private void initView() {
        View view = inflater.inflate(R.layout.activity_personal, this, true);

        attentionBtn = (RelativeLayout) view.findViewById(R.id.btn_attention);
        ivVipType = (ImageView) view.findViewById(R.id.ivVipType);
        vVipRedPoint = view.findViewById(R.id.vVipRedPoint);
        messageBtn = (RelativeLayout) view.findViewById(R.id.btn_message);
        edit_layout = (RelativeLayout) view.findViewById(R.id.edit_layout);
        layout_share = (RelativeLayout) view.findViewById(R.id.layout_share);
        feedback = (RelativeLayout) view.findViewById(R.id.feedback);

        headImageView = (SimpleDraweeView) view.findViewById(R.id.headImageView);
        userName = (TextView) view.findViewById(R.id.userName);
        text_attention = (TextView) view.findViewById(R.id.text_attention);
        text_record = (TextView) view.findViewById(R.id.text_record);
        userCompany = (TextView) findViewById(R.id.userCompany);
        userjob = (TextView) findViewById(R.id.userjob);
        scrollView = (CustomScrollView) view.findViewById(R.id.setting_scrollview);
        topLayout = (RelativeLayout) view.findViewById(R.id.layout_top);
        topImage = (ImageView) view.findViewById(R.id.image_top);
        titleBarLayout = (RelativeLayout) view.findViewById(R.id.layout_titlebar);
        mUnreadIndicator = view.findViewById(R.id.unread_indicator);

        vLeft = view.findViewById(R.id.vLeft);
        vRight = view.findViewById(R.id.vRight);
        vBottom = view.findViewById(R.id.vBottom);

        scrollUtil = new ScrollUtil();
        initViewAction();

        view.findViewById(R.id.vVip).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        view.findViewById(R.id.vVip).setOnClickListener(this);
        view.findViewById(R.id.vMyReport).setOnClickListener(this);

        view.findViewById(R.id.vVip).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                MobclickAgent.onEvent(mContext, "Me84");
                personCenterListener.personViewOnClick(VIP);
            }
        });

        view.findViewById(R.id.vVipCon).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                MobclickAgent.onEvent(mContext, "Me84");
                personCenterListener.personViewOnClick(VIP_CON);
            }
        });

        view.findViewById(R.id.vVipShare).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                MobclickAgent.onEvent(mContext, "Me84");
                personCenterListener.personViewOnClick(VIP_SHARE);
            }
        });

        view.findViewById(R.id.vMyReport).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                MobclickAgent.onEvent(mContext, "Me84");
                personCenterListener.personViewOnClick(MY_REPORT);
            }
        });

        //        animLeftOflr, animLeftOfrl,animRightOflr, animRightOfrl;


        startLogoutAnim(null);
    }

    private void initAnim() {
        animLeftOflr = ObjectAnimator.ofFloat(vLeft, "translationX", -vLeft.getWidth(), 0);
        animLeftOfrl = ObjectAnimator.ofFloat(vLeft, "translationX", 0, -vLeft.getWidth());

        animRightOflr = ObjectAnimator.ofFloat(vRight, "translationX", 0, vRight.getWidth());
        animRightOfrl = ObjectAnimator.ofFloat(vRight, "translationX", vRight.getWidth(), 0.0f);

        animBottomOftb = ObjectAnimator.ofFloat(vBottom, "translationY", 0, vRight.getHeight());
        animBottomOfbt = ObjectAnimator.ofFloat(vBottom, "translationY", vRight.getHeight(), 0);

        animLeftOflr.setInterpolator(new LinearInterpolator());
        animLeftOflr.setDuration(500);

        animLeftOfrl.setInterpolator(new LinearInterpolator());
        animLeftOfrl.setDuration(500);

        animBottomOfbt.setInterpolator(new LinearInterpolator());
        animBottomOfbt.setDuration(600);

        animBottomOftb.setInterpolator(new LinearInterpolator());
        animBottomOftb.setDuration(600);
    }

    public void startLoginAnim() {
        initAnim();
        animLeftOflr.start();
        animRightOfrl.start();
        animBottomOfbt.start();
    }

    public void startLogoutAnim(AnimatorListenerAdapter adapter) {
        initAnim();
        if (adapter != null)
            animBottomOftb.addListener(adapter);
        animLeftOfrl.start();
        animRightOflr.start();
        animBottomOftb.start();
    }

    public void setIsVip(boolean isVip) {
        ivVipType.setVisibility(View.VISIBLE);
        ivVipType.setSelected(isVip);
        vVipRedPoint.setVisibility(isVip ? View.GONE : View.VISIBLE);
    }

    public void setUserCompany(String company) {
        if (!TextUtils.isEmpty(company)) {
            userCompany.setText(company);
        } else {
            userCompany.setText("暂无公司信息");
        }
    }

    public void setUserName(String name) {
        if (!TextUtils.isEmpty(name)) {
            userName.setText(name);
        } else {
            userName.setText("姓名");
        }
    }

    public void setUserJob(String job) {
        if (!TextUtils.isEmpty(job)) {
            userjob.setText(job);
        } else {
            userjob.setText("暂无职位信息");
        }
    }

    public void setUserHeadImage(String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            headImageView.setImageURI(Uri.parse(imageUrl));
        } else {
            headImageView.setImageURI(Uri.parse("res://com.jusfoun.jusfouninquire/" + R.mipmap.default_head_image));
        }
    }

    public void setFollow(String string) {
        text_attention.setText(string);
    }

    public void setMessage(String str) {
        text_record.setText(str);
    }

    private void initViewAction() {
        attentionBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext, EventUtils.ME84);
                personCenterListener.personViewOnClick(ATTENTION_BTN);
            }
        });
        messageBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext, EventUtils.ME85);
                personCenterListener.personViewOnClick(SYSTEM_MES_BTN);
            }
        });

        feedback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext, EventUtils.ME87);
                personCenterListener.personViewOnClick(FEED_BACK_BTN);
            }
        });

        edit_layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext, EventUtils.ME86);
                personCenterListener.personViewOnClick(EDIT_USERINFO);
            }
        });

        layout_share.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext, EventUtils.ME88);
                personCenterListener.personViewOnClick(SHARE);
            }
        });

        titleBarLayout.getBackground().setAlpha(0);
        scrollView.setCallBack(new CustomScrollView.OnScrollListener() {
            @Override
            public void onScrollChangedListener(int leftOfVisibleView, int topOfVisibleView, int oldLeftOfVisibleView, int oldTopOfVisibleView) {
                int count = topOfVisibleView;
//                Log.e("tag", "topOfVisibleView_PersonCenterView=" + count);
                if (count <= 255) {
                    titleBarLayout.getBackground().setAlpha(count);
                } else {
                    titleBarLayout.getBackground().setAlpha(255);
                }
            }

            @Override
            public void onPullScroll(int height) {
                scrollUtil.imageScale(topLayout, topImage, height, PhoneUtil.dip2px(mContext, 430));
            }
        });

        //dealUnreadMsg();


    }

    /**
     * 处理未读消息指示view
     */
    public void dealUnreadMsg() {
        UserInfoModel userInfoModel = LoginSharePreference.getUserInfo(mContext);
        if (userInfoModel != null && !TextUtils.isEmpty(userInfoModel.getSystemmessageunread())) {
            try {
                int count = Integer.parseInt(userInfoModel.getSystemmessageunread());
                mUnreadIndicator.setVisibility((count > 0) ? VISIBLE : GONE);
            } catch (Exception e) {
                mUnreadIndicator.setVisibility(GONE);
            }
        }
    }

    public void setPersonViewListener(PersonCenterListener listener) {
        this.personCenterListener = listener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vVip:
                LogUtil.e("ABC", "vVip:");
                break;

            case R.id.vMyReport:
                LogUtil.e("ABC", "vMyReport:");
                break;
        }
    }


    public interface PersonCenterListener {
        public void personViewOnClick(int viewId);
    }


    public void setPadding(int padding) {
        titleBarLayout.setPadding(0, padding, 0, 0);
    }

    public void setBackAlpha() {
        titleBarLayout.getBackground().setAlpha(0);
    }

}
