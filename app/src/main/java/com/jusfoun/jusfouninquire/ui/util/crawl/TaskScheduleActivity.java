package com.jusfoun.jusfouninquire.ui.util.crawl;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.ui.BaseInquireActivity;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;
import com.jusfoun.jusfouninquire.ui.util.crawl.service.WebService;

import java.text.SimpleDateFormat;
import java.util.Date;

import netlib.util.EncryptUtil;


public class TaskScheduleActivity extends BaseInquireActivity {

    private TextView stateText,urlText,timeText,contentText,diText;
    private Button reBtn;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_taskschedule);
        stateText = (TextView) findViewById(R.id.text_state);
        urlText = (TextView) findViewById(R.id.text_url);
        timeText = (TextView) findViewById(R.id.text_time);
        contentText = (TextView) findViewById(R.id.text_content);
        reBtn = (Button)findViewById(R.id.btn_restart);
        diText = (TextView)findViewById(R.id.text_di);
    }

    @Override
    protected void initWidgetActions() {
        reBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskScheduleActivity.this, WebService.class);
                startService(intent);
            }
        });

        SimpleDateFormat myFmt=new SimpleDateFormat("yyyyMMdd");
        String di =  myFmt.format(new Date(System.currentTimeMillis()))+"_"+ PhoneUtil.getIMEI(mContext);

        diText.setText("设备id:"+di);
    }

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if(event instanceof TaskEvent){
            TaskEvent taskEvent = (TaskEvent)event;

            if(!TextUtils.isEmpty(taskEvent.getState())){
                stateText.setText("当前状态："+taskEvent.getState());
            }
            if(!TextUtils.isEmpty(taskEvent.getUrl())){

                urlText.setText("请求url："+  EncryptUtil.decryptAES(taskEvent.getUrl()));
            }
            if(!TextUtils.isEmpty(taskEvent.getTime())){
                timeText.setText("下一次启动时间间隔："+taskEvent.getTime());
            }
            if(!TextUtils.isEmpty(taskEvent.getContent())){
                contentText.setText(taskEvent.getContent());
            }
        }
    }
}
