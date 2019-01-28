package com.jusfoun.jusfouninquire.ui.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.model.ContactinFormationModel;
import com.jusfoun.jusfouninquire.net.model.ContactsModel;
import com.jusfoun.jusfouninquire.net.model.SearchContactListModel;
import com.jusfoun.jusfouninquire.ui.activity.ExportContactsActivity;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:18/5/715:22
 * @Email zyp@jusfoun.com
 * @Description ${通讯录下方 展开view}
 */
public class ContactsOpenView extends BaseView {
    protected RelativeLayout layoutRoot, arrowLayout;
    protected ImageView imgArrowState;
    private TextView phoneText3, moreText;

    private SearchContactListModel.DataBean model;

    public ContactsOpenView(Context context) {
        super(context);
    }

    public ContactsOpenView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContactsOpenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {

        LayoutInflater.from(mContext).inflate(R.layout.view_contacts_open, this, true);
        initView();
    }

    @Override
    protected void initActions() {
        arrowLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutRoot.getTag() == null || "CLOSE".equals(layoutRoot.getTag().toString())) {
                    imgArrowState.setImageResource(R.drawable.img_constants_arrow_up);
                    startOpen();
                } else {
                    imgArrowState.setImageResource(R.drawable.img_constants_arrow_down);
                    startClose();
                }

            }
        });

        moreText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, ContactsListActivity.class);
//                SearchContactListModel searchContactListModel = new SearchContactListModel();
//
//                List<SearchContactListModel.DataBean> list = new ArrayList<>();
//                list.add(model);
//                searchContactListModel.data = list;
//
//
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("model", searchContactListModel);
//                intent.putExtras(bundle);
//                mContext.startActivity(intent);


                List<ContactsModel> list = new ArrayList<>();
                ContactsModel cm = new ContactsModel();
                cm.id = model.id;
                cm.name = model.name;

                if(!TextUtils.isEmpty(model.establishDate)){
                    cm.time=model.establishDate;
                }else{
                    cm.time="企业未公示";
                }

                List<String> phones = model.phoneArr;
                List<ContactinFormationModel> phonesList = new ArrayList<>();
                if (phones != null)
                    for (int j = 0; j < phones.size(); j++) {
                        ContactinFormationModel contactinFormationModel = new ContactinFormationModel();
                        contactinFormationModel.setNumber(phones.get(j));
                        phonesList.add(contactinFormationModel);
                    }
                cm.phones = phonesList;
                list.add(cm);


                Intent intent = new Intent(mContext, ExportContactsActivity.class);
                intent.putExtra("model", new Gson().toJson(list));
                mContext.startActivity(intent);

            }
        });
    }

    private void initView() {
        layoutRoot = (RelativeLayout) findViewById(R.id.layout_root);
        imgArrowState = (ImageView) findViewById(R.id.img_arrow_state);
        phoneText3 = (TextView) findViewById(R.id.phone_text3);
        moreText = (TextView) findViewById(R.id.text_more);
        arrowLayout = (RelativeLayout) findViewById(R.id.layout_more);


    }

    public void startOpen() {
        layoutRoot.setTag("OPEN");
        ValueAnimator animator = ValueAnimator.ofInt(PhoneUtil.dip2px(mContext, 30), PhoneUtil.dip2px(mContext, 60));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                LayoutParams params = (LayoutParams) layoutRoot.getLayoutParams();
                params.height = value;
                layoutRoot.setLayoutParams(params);
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
//                mCompanyMenu.setVisibility(GONE);
                if (callBack != null) {
                    callBack.openView();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }


    public void setOpen() {
        LayoutParams params = (LayoutParams) layoutRoot.getLayoutParams();
        params.height = PhoneUtil.dip2px(mContext, 60);
        layoutRoot.setLayoutParams(params);
        layoutRoot.setTag("OPEN");
        imgArrowState.setImageResource(R.drawable.img_constants_arrow_up);
    }

    public void setClose() {
        LayoutParams params = (LayoutParams) layoutRoot.getLayoutParams();
        params.height = PhoneUtil.dip2px(mContext, 30);
        layoutRoot.setLayoutParams(params);
        layoutRoot.setTag("CLOSE");
        imgArrowState.setImageResource(R.drawable.img_constants_arrow_down);
    }

    public void startClose() {
        layoutRoot.setTag("CLOSE");
        ValueAnimator animator = ValueAnimator.ofInt(PhoneUtil.dip2px(mContext, 60), PhoneUtil.dip2px(mContext, 30));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                LayoutParams params = (LayoutParams) layoutRoot.getLayoutParams();
                params.height = value;
                layoutRoot.setLayoutParams(params);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
//                mCompanyMenu.setVisibility(GONE);
                if (callBack != null) {
                    callBack.closeView();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

    public interface CallBack {
        void openView();

        void closeView();
    }

    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public void setData(SearchContactListModel.DataBean model) {
        phoneText3.setText("");
        this.model = model;
        if (model != null&&model.phoneArr != null) {
            if ( model.phoneArr.size() > 3) {
                setVisibility(VISIBLE);
                moreText.setVisibility(VISIBLE);
                phoneText3.setVisibility(VISIBLE);
                phoneText3.setText(model.phoneArr.get(2));
            } else if(model.phoneArr.size()==3){
                setVisibility(VISIBLE);
                moreText.setVisibility(GONE);
                phoneText3.setVisibility(VISIBLE);
                phoneText3.setText(model.phoneArr.get(2));
            }else{
                setVisibility(GONE);
            }
        }else{
            setVisibility(GONE);
        }
    }

}
