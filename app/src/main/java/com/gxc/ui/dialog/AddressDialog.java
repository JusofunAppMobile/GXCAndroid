package com.gxc.ui.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.gxc.model.AddressModel;
import com.gxc.plugin.wheelview.adapter.ArrayWheelAdapter;
import com.gxc.plugin.wheelview.widget.WheelView;
import com.jusfoun.jusfouninquire.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressDialog extends BaseDialog {

    @BindView(R.id.wheelView1)
    WheelView wheelView1;
    @BindView(R.id.wheelView2)
    WheelView wheelView2;

    private OnSelectListener listener;
    private List<AddressModel> addressList;

    public AddressDialog(Activity activity, final List<AddressModel> addressList, OnSelectListener listener) {
        super(activity);
        this.addressList = addressList;
        this.listener = listener;
        setContentView(R.layout.dialog_address);
        getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(true);
        setWindowStyle(10, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        initWheel(wheelView1, getNameList(addressList));
        initWheel(wheelView2, getNameList(addressList.get(0).children));
        wheelView1.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                wheelView2.setWheelData(getNameList(addressList.get(position).children));
                if (cIndex != -1) {
                    wheelView2.setSelection(cIndex, 200);
                    cIndex = -1;
                }
            }
        });
    }

    private List<String> getNameList(List<AddressModel> list) {
        List<String> mList = new ArrayList<>();
        if (list != null && !list.isEmpty())
            for (AddressModel model : list)
                mList.add(model.name);
        else
            mList.add("");
        return mList;
    }

    private int cIndex = -1;

    public void setSelectPosition(final int pIndex, final int cIndex) {
        this.cIndex = cIndex;
        wheelView1.setSelection(pIndex, 200);
//        if (cIndex != 0) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    wheelView2.setSelection(cIndex, 200);
//                }
//            }, 1200);
//        }
    }

    private void initWheel(WheelView wheelView, List<String> list) {
        wheelView.setVisibility(View.VISIBLE);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextSize = 16;
        style.textSize = 16;
        style.selectedTextColor = Color.parseColor("#EC5540");
        style.textColor = Color.parseColor("#666666");
        style.holoBorderColor = Color.parseColor("#DCDCDA");
        style.holoBorderWidth = 1;

        wheelView.setWheelAdapter(new ArrayWheelAdapter(getContext())); // 文本数据源
        wheelView.setSkin(WheelView.Skin.Holo); // common皮肤
        wheelView.setWheelData(list);  // 数据集合
        wheelView.setStyle(style);
//        wheelView.setExtraText("周一", Color.RED, (int) getContext().getResources().getDimension(R.dimen.wheel_label_size), 160);
        wheelView.setWheelSize(5);
        wheelView.setLoop(false);
        wheelView.setWheelClickable(false);
    }

    @OnClick({R.id.vCancel, R.id.vSure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vCancel:
                dismiss();
                break;
            case R.id.vSure:
                if (listener != null) {
                    AddressModel province = addressList.get(wheelView1.getCurrentPosition());
                    AddressModel city = null;
                    if (province.children != null && !province.children.isEmpty())
                        city = province.children.get(wheelView2.getCurrentPosition());
                    listener.select(province, city);
                }
//                if (wheelView2.getVisibility() == View.VISIBLE) {
//                    textView.setText(wheelView1.getSelectionItem().toString().replaceAll("时", "") + ":" + wheelView2.getSelectionItem().toString().replaceAll("分", ""));
//                    textView.setTag(textView.getText().toString());
//                } else {
//                    textView.setText(wheelView1.getSelectionItem().toString());
//                    if (isSetIndexTag)
//                        textView.setTag(wheelView1.getCurrentPosition());
//                    else
//                        textView.setTag(textView.getText().toString());
//                }
                dismiss();
                break;
        }
    }

    public interface OnSelectListener {
        void select(AddressModel province, AddressModel city);
    }
}
