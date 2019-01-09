package com.jusfoun.jusfouninquire.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.util.AppUtil;
import com.jusfoun.jusfouninquire.ui.util.ResourceUtil;

/**
 * @author zhaoyapeng
 * @version create time:17/9/2514:17
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class VoiceDialog extends RelativeLayout {
    private Context mContext;
    private ImageView img_voice;
    private TextView tishiText;
    public VoiceDialog(Context context) {
        super(context);
        mContext= context;
        initViews();
        initActions();
    }

    public VoiceDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext= context;
        initViews();
        initActions();
    }

    public VoiceDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext= context;
        initViews();
        initActions();
    }

    private  void initViews(){
        LayoutInflater.from(mContext).inflate(R.layout.layout_voide, this, true);
        img_voice = (ImageView) findViewById(R.id.img_voice);
        tishiText = (TextView)findViewById(R.id.text_tishi);
    }

    private void initActions(){

    }

    public void setImageState(int index){
        Log.e("tag","img_voice_="+("R.mipmap.img_voice_"+index%7));
        img_voice.setImageResource(ResourceUtil.getInstance(mContext).getMipmapId("img_voice_"+index%7));
    }

    public void upState(){
        img_voice.setImageResource(R.mipmap.img_voice_7);
        tishiText.setText("上滑取消");
    }

    public void downState(){
        img_voice.setImageResource(R.mipmap.img_voice_0);
        tishiText.setText("请您说话");
    }
}
