package com.jusfoun.bigdata;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;


/**
 * @author lee
 * @version create time:2015年7月21日_上午11:09:39
 * @Description 打电话dialog
 */

public class CallPhoneDialog extends Dialog {

	private ImageView icon;
	private TextView titleTextView;
	private TextView descriptTextView;
	private TextView leftButton,rightButton;
	
	public CallPhoneDialog(Context context) {
		super(context);
		initView(context);
		initAction();
	}
	protected CallPhoneDialog(Context context, boolean cancelable,
                              OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		initView(context);
		initAction();
	}
	
	public CallPhoneDialog(Context context, int theme) {
		super(context, theme);
		initView(context);
		initAction();
	}
	

	private void initView(Context context){
		setContentView(R.layout.dialog_call_phone);
		
		Window window = this.getWindow();
		android.view.WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = (int) (PhoneUtil.getDisplayWidth(context) * 0.8);
		window.setGravity(Gravity.CENTER);
		window.setAttributes(lp);
		
		icon = (ImageView) findViewById(R.id.icon);
		titleTextView = (TextView) findViewById(R.id.titleTextView);
		descriptTextView = (TextView) findViewById(R.id.decriptTextView);
		leftButton = (TextView) findViewById(R.id.leftButton);
		rightButton = (TextView) findViewById(R.id.rightButton);
		
	}
	
	private void initAction(){
		leftButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(listener != null){
					listener.onLeftClick();
					dismiss();
				}
			}
		});
		rightButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(listener != null){
					listener.onRightClick();
					dismiss();
				}
			}
		});
	}
	
	private callBack listener;

	public void setListener(callBack listener) {
		this.listener = listener;
	}
	
	public void setText(String title, String message) {
		titleTextView.setText(title);
		descriptTextView.setText(message);
	}

	public void setButtonText(String left, String right) {
		leftButton.setText(left);
		rightButton.setText(right);
	}
	
	public void setImageVisiable(boolean isVisiable){
		if(isVisiable){
			icon.setVisibility(View.VISIBLE);
		}else {
			icon.setVisibility(View.GONE);
		}
		
	}

	public interface callBack {
		public void onLeftClick();

		public void onRightClick();
	}
}
