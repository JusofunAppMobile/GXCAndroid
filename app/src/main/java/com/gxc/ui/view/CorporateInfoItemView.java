package com.gxc.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.ui.view.BaseView;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/714:45
 * @Email zyp@jusfoun.com
 * @Description ${企业信息 item view}
 */
public class CorporateInfoItemView extends BaseView {
    protected TextView textView13;
    protected TextView editText2;
    protected ImageView ivArrow;

    public CorporateInfoItemView(Context context) {
        super(context);
        init(null);
    }

    public CorporateInfoItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CorporateInfoItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {

    }

    private void init(AttributeSet attrs) {
        int layoutId = R.layout.view_corporate_info_item;
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.info_item);
            boolean isTextView = array.getBoolean(R.styleable.info_item_is_text_view, false);
            if (isTextView)
                layoutId = R.layout.view_corporate_info_item2;
        }
        LayoutInflater.from(mContext).inflate(layoutId, this, true);
        textView13 = (TextView) findViewById(R.id.vSendCode);
        editText2 = findViewById(R.id.editText2);
        ivArrow = findViewById(R.id.ivArrow);
    }

    @Override
    protected void initActions() {

    }

    /**
     * @param title
     * @param maxNum 该页面中需要对齐的最大汉字个数
     */
    public void setData(final String title, final int maxNum) {
        textView13.post(new Runnable() {
            @Override
            public void run() {
                textView13.setText(getEmptyValue(maxNum));
                int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                textView13.measure(spec, spec);
                textView13.getLayoutParams().width = textView13.getMeasuredWidth();
                textView13.setText(title + "");
            }
        });

        editText2.setHint("请输入" + title.replaceAll(" ", ""));
    }


    public void setData(String value) {
        editText2.setText(value);
    }

    private String getEmptyValue(int num) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++)
            sb.append("\u3000"); // （一个中文宽度）
        return sb.toString();
    }

    public void setEditText(String str) {
        editText2.setText(str);
    }

    public void setEditTable(boolean editTable) {
        editText2.setFocusable(editTable);
        editText2.setFocusableInTouchMode(editTable);
        editText2.setLongClickable(editTable);
        editText2.setInputType(editTable ? InputType.TYPE_CLASS_TEXT : InputType.TYPE_NULL);
    }

    public TextView getTextView(){
        return editText2;
    }

    public String getEditText() {
        return editText2.getText().toString();
    }


    public void setContent(String content) {
        editText2.setText(content);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (editText2 instanceof EditText)
            editText2.setEnabled(enabled);
    }

    public void setSelectType() {
        if (editText2 instanceof EditText)
            editText2.setEnabled(false);
        ivArrow.setVisibility(View.VISIBLE);
    }

    public void setInputTypeNum() {
        editText2.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    public void setMaxInputLength(int length) {
        editText2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }

    @Override
    public void setOnClickListener(View.OnClickListener l) {
        super.setOnClickListener(l);
        if (!(editText2 instanceof EditText))
            editText2.setOnClickListener(l);
    }
}

