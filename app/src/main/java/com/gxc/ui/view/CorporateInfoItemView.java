package com.gxc.ui.view;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.BaseView;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/714:45
 * @Email zyp@jusfoun.com
 * @Description ${企业信息 item view}
 */
public class CorporateInfoItemView extends BaseView {
    protected TextView textView13;
    protected EditText editText2;

    public CorporateInfoItemView(Context context) {
        super(context);
    }

    public CorporateInfoItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CorporateInfoItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_corporate_info_item, this, true);
        textView13 = (TextView) findViewById(R.id.vSendCode);
        editText2 = (EditText) findViewById(R.id.editText2);
    }

    @Override
    protected void initActions() {

    }

    /**
     *
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

        editText2.setHint("请用户输入" + title.replaceAll(" ", ""));
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

    public String getEditText() {
        return editText2.getText().toString();
    }


    public void setContent(String content) {
        editText2.setText(content);
    }
}

