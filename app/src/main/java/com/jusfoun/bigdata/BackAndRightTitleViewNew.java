package com.jusfoun.bigdata;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.ui.util.KeyBoardUtil;
import com.siccredit.guoxin.R;


/**
 * @author lee
 * @version create time:2015年7月15日_下午2:57:46
 * @Description title组件
 */

public class BackAndRightTitleViewNew extends LinearLayout {

	private RightClickListener listener;
	private LeftClickListener leftListener;
	private ImageView leftText;
	private TextView titleText, rightText;
	private RelativeLayout titleBackGroud;
	private Context mContext;
	public BackAndRightTitleViewNew(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public BackAndRightTitleViewNew(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public BackAndRightTitleViewNew(Context context) {
		super(context);
		initView(context);
	}
	
	private void initView(final Context context) {
		mContext = context;
		LayoutInflater.from(context).inflate(R.layout.back_right_titleview_new, this, true);

		titleBackGroud = (RelativeLayout) findViewById(R.id.titleBackgroud);
		leftText = (ImageView) findViewById(R.id.left_textview);
		leftText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (leftListener != null) {
					leftListener.onClick(v);
				} else {
					((Activity) context).finish();
					KeyBoardUtil.hideSoftKeyboard((Activity) context);
				}
				
			}
		});
		
		
		titleText = (TextView) findViewById(R.id.title_text);
		rightText = (TextView) findViewById(R.id.rightText);
		rightText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.onClick(v);
				}
			}
		});
	}
	public void setTitleBackGroud(Context context, String colorName, int colorRes){
//		SkinUtils.setSkinBackgroundColor(context,titleBackGroud,colorName,colorRes);
	}
	
	public void setTitle(int id) {
		titleText.setText(id);
	}

	public void setTitle(String title) {
		titleText.setText(title);
	}

	public void setRightText(int id) {
		rightText.setVisibility(View.VISIBLE);
		rightText.setText(id);
	}

	public void setRightText(String title) {
		rightText.setVisibility(View.VISIBLE);
		rightText.setText(title);
	}
	
	public void setRightImage(int resid) {
		rightText.setVisibility(View.VISIBLE);
		rightText.setBackgroundResource(resid);
	}

	public TextView getRightText(){
		return rightText;
	}
	
	public void setLeftImage(String resName, int resid) {
		leftText.setVisibility(View.VISIBLE);
//		SkinUtils.setSkinImageDrawable(mContext,leftText,resName,resid);
		//leftText.setBackgroundResource(resid);
	}

	public void setLeftIsShow(boolean isShow){
		if(isShow){
			leftText.setVisibility(View.VISIBLE);
		}else {
			leftText.setVisibility(View.GONE);
		}
	}

	public void setRightClickable(boolean clickable){
		rightText.setClickable(clickable);
	}
	
	
	public void setRightClickListener(RightClickListener listener) {
		this.listener = listener;
	}

	public void setLeftClickListener(LeftClickListener leftListener) {
		this.leftListener = leftListener;
	}

	public interface RightClickListener {
		public void onClick(View v);
	}
	
	public interface LeftClickListener {
		public void onClick(View v);
	}
	
}
