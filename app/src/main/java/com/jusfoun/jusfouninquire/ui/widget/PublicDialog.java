package com.jusfoun.jusfouninquire.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;


/**
 * @author zhaoyapeng
 * @version create time:2015-6-30下午2:31:40
 * @Email zyp@jusfoun.com
 * @Description
 */
public class PublicDialog extends Dialog {
	private Context mContext;
	private Button oneBtn;

	private int TYPE_ONE = 1, TYPE_TWO = 2, TYPE_THREE = 3;
	private TextView titleText,leftBtn, rightBtn, messgaeText;
	private View line;
	private LinearLayout linelayout;

	public PublicDialog(Context context) {
		super(context);
		mContext = context;
		initData();
		initViews();
		initAction();
	}

	public PublicDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
		initData();
		initViews();
		initAction();
	}

	protected PublicDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		mContext = context;
		initData();
		initViews();
		initAction();
	}

	private void initData() {

	}

	private void initViews() {
		setContentView(R.layout.dialog_public);
		
		Window window = this.getWindow();
		android.view.WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = (int) (PhoneUtil.getDisplayWidth(mContext) * 0.8);
		window.setGravity(Gravity.CENTER);
		window.setAttributes(lp);
		
		leftBtn = (TextView) findViewById(R.id.btn_center);
		rightBtn = (TextView) findViewById(R.id.btn_right);
		oneBtn = (Button) findViewById(R.id.btn_one);
		titleText = (TextView) findViewById(R.id.text_title);
		messgaeText = (TextView) findViewById(R.id.text_message);
		line  = findViewById(R.id.line);
		linelayout = (LinearLayout)findViewById(R.id.layout_btn);
	}

	private void initAction() {
		leftBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (listener != null) {
					listener.onLeftClick();
					dismiss();
				}

			}
		});
		rightBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (listener != null) {
					listener.onRightClick();
					dismiss();
				}

			}
		});
		oneBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (listener != null) {
					listener.onRightClick();
					dismiss();
				}

			}
		});
	}

	public interface callBack {
		public void onLeftClick();

		public void onRightClick();
	}

	private callBack listener;

	public void setListener(callBack listener) {
		this.listener = listener;
	}

	public void setText(String title, String message) {
		titleText.setText(title);
		messgaeText.setText(message);
	}

	public void setButtonText(String left, String right) {
		leftBtn.setText(left);
		rightBtn.setText(right);
	}
	
	public void setLeftBtnGone(){
		oneBtn.setVisibility(View.VISIBLE);
		linelayout.setVisibility(View.GONE);
	}

	public void setMessageVisiblity(boolean value){
		if(!value){
			messgaeText.setVisibility(View.GONE);
		}

	}
	
}
