package com.jusfoun.jusfouninquire.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;

/**
 * @author
 * @version create time:2015年7月15日_下午7:02:52
 * @Description 类似 |tag value  箭头|   样式的 组件
 */

public class TextChooseView extends RelativeLayout {

	private TextView chooseText_tag,chooseText_value;
	
	private String hint;
	public TextChooseView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context,attrs);
	}

	public TextChooseView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context,attrs);
	}

	private void initView(final Context context,final AttributeSet attrs) {
		LayoutInflater.from(context).inflate(R.layout.textchoose_view, this, true);

		chooseText_tag = (TextView) findViewById(R.id.textChoose_tag);
		chooseText_value = (TextView) findViewById(R.id.Textchoose_value);
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.textchooseview);
		
		String text = (String)a.getText(R.styleable.textchooseview_tagchoosetext);
		hint = (String)a.getText(R.styleable.textchooseview_choosetext);
		
		chooseText_tag.setText(text);
		chooseText_value.setText("不限");
		a.recycle();
	}
	
	public void setTextViewTagName(String tagString)
	{
		chooseText_tag.setText(tagString);
	}
	public void setTextViewValue(String tagString)
	{
		chooseText_value.setText(tagString);
	}

	public void setTextViewValueColor(){
		if("不限".equals(chooseText_value.getText().toString().trim())){
			//chooseText_value.setTextColor(getResources().getColor(R.color.choosetextvalue_hint));
		}else {
			//chooseText_value.setTextColor(getResources().getColor(R.color.choosetextvalue_textcolor));
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
}
