package com.jusfoun.jusfouninquire.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.siccredit.guoxin.R;



/**
 * @author lee
 * @version create time:2015年7月15日_下午7:53:23
 * @Description 自定义  tag  value  的一个组件，例：大数据页面 的企业分析组件
 */

public class TextEditView extends RelativeLayout {

	private TextView textEdit_tag;
	private EditText textEdit_value;
	private String hint;

	public TextEditView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context,attrs);
	}

	public TextEditView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs);
	}
	
	private void initView(final Context context,final AttributeSet attrs) {
		LayoutInflater.from(context).inflate(R.layout.textedit_view, this, true);

		textEdit_tag = (TextView) findViewById(R.id.textedit_tag);
		textEdit_value = (EditText) findViewById(R.id.textedit_value);


		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.textedit);
		
		String text = (String)a.getText(R.styleable.textedit_tagtext);
		String value = (String)a.getText(R.styleable.textedit_editvaluetext);
		hint = (String)a.getText(R.styleable.textedit_hit);
		
		textEdit_tag.setText(text);
		textEdit_value.setHint(hint);
		textEdit_value.setText(value);
		a.recycle();
	}
	public void setTextEditTag(String text){
		textEdit_tag.setText(text);
	}
	public void setTextEditTag(int resid){
		textEdit_tag.setText(resid);
	}
	public void setTextEditValue(String text){
		textEdit_value.setText(text);
	}
	public void setTextEditValue(int resid){
		textEdit_value.setText(resid);
	}
	public String getTextEditValue(){
		return textEdit_value.getText().toString().trim();
	}
	public void setTextEditValueHint(String text){
		textEdit_value.setHint(text);
	}
	public void setTextEditValueHint(int resid){
		textEdit_value.setHint(resid);
	}
	public void setFocusChangeListener(OnFocusChangeListener listener){
		textEdit_value.setOnFocusChangeListener(listener);
	}


	public void setValueFocusChangeListener(OnFocusChangeListener listener){
		textEdit_value.setOnFocusChangeListener(listener);
	}

	public void setValueTextWatcher(TextWatcher watcher){
		textEdit_value.addTextChangedListener(watcher);
	}
}
