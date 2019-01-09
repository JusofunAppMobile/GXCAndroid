package com.jusfoun.jusfouninquire.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.ContactinFormationModel;
import com.jusfoun.jusfouninquire.net.model.ContactsModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TableRowView extends LinearLayout {

    private TextView name, time, phone;

    public TableRowView(Context context) {
        super(context);
        init();
    }

    public TableRowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TableRowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_table_row, this, true);
        name = (TextView) findViewById(R.id.name);
        time = (TextView) findViewById(R.id.time);
        phone = (TextView) findViewById(R.id.phone);
    }

    public void setModel(ContactsModel model) {
        name.setText(model.name);
        time.setText(model.time);
        phone.setText(Html.fromHtml(getPhone(model)));
    }

    private String getPhone(ContactsModel model) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < model.phones.size(); i++) {
            sb.append(model.phones.get(i).getNumber());
            if (i >= 3) {
                sb.append("<br>...");
                break;
            }
            if (i != model.phones.size() - 1) {
                sb.append("<br>");
            }
        }
        return sb.toString();
    }

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private String getTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return sdf.format(calendar.getTime());
    }

}
