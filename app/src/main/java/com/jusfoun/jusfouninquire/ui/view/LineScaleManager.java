package com.jusfoun.jusfouninquire.ui.view;

import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoma on 2018/1/12/012.
 */

public class LineScaleManager {

    private List<EditText> editTextList = new ArrayList<>();
    private List<LineScaleView> lineList = new ArrayList<>();

    private LineScaleView lastLine;

    public void setEditText(Map<EditText, LineScaleView> map) {
        for (EditText editText : map.keySet()) {
            editTextList.add(editText);

            final LineScaleView view = map.get(editText);
            view.scaleInit();
            lineList.add(view);

            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                        view.scaleBig();
                        if(lastLine != null)
                            lastLine.scaleSmall();
                        lastLine = view;
                    }else{
                        view.scaleSmall();
                        lastLine = null;
                    }
                }
            });
        }
    }

}
