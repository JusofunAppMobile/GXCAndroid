package com.gxc.ui.fragment;

import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.gxc.base.BaseFragment;
import com.gxc.constants.Constants;
import com.gxc.event.AuthStatusChangeEvent;
import com.gxc.event.LoginChangeEvent;
import com.gxc.model.CreditDataModel;
import com.gxc.model.HomeMenuModel;
import com.gxc.model.UserModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.activity.CertifiedCompanyActivity;
import com.gxc.ui.activity.LoginActivity;
import com.gxc.ui.activity.MonitorDetailActivity;
import com.gxc.ui.adapter.HomeMenuAdapter;
import com.gxc.utils.AppUtils;
import com.gxc.utils.GoActivityUtil;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.ui.view.NetWorkErrorView;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import netlib.util.PreferenceUtils;

/**
 * @author liuguangdan
 * @version create at 2019/1/4/004 19:39
 * @Email lgd@jusfoun.com
 * @Description ${信用服务}
 */
public class CreditFragment extends BaseFragment {

    protected TextView textCompany;
    protected RecyclerView recycleviewService;
    protected RecyclerView recycleviewInquire;
    protected NestedScrollView nestedScrollView;
    HomeMenuAdapter serviceAdapter, inquireAdapter;
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.layout_root)
    RelativeLayout layoutRoot;
    @BindView(R.id.line_chart)
    LineChart chart;
    @BindView(R.id.layout_service)
    LinearLayout layoutService;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.layout_fangke)
    LinearLayout layoutFangke;
    @BindView(R.id.text_xinyong_code)
    TextView textXinyongCode;
    @BindView(R.id.text_compay_type)
    TextView textCompayType;
    @BindView(R.id.text_num)
    TextView textNum;
    @BindView(R.id.net_work_error)
    NetWorkErrorView netWorkError;
    private ImageView certificationImg;

    @BindView(R.id.img_shenhezhong)
    ImageView shImg;

    @BindView(R.id.layout2)
    ConstraintLayout changeLayout;
    @BindView(R.id.vChartBg)
    LinearLayout vChartBg;


    private RelativeLayout topLayout;

    public CreditDataModel.CompanyInfo companyInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_credit;
    }

    @Override
    protected void initView() {
        textCompany = (TextView) findViewById(R.id.text_company);
        recycleviewService = (RecyclerView) findViewById(R.id.recycleview_service);
        recycleviewInquire = (RecyclerView) findViewById(R.id.recycleview_inquire);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        certificationImg = (ImageView) findViewById(R.id.img_no_certification);
        topLayout = (RelativeLayout) findViewById(R.id.layout_top);


        nestedScrollView.setNestedScrollingEnabled(false);


        serviceAdapter = new HomeMenuAdapter();
        inquireAdapter = new HomeMenuAdapter();


        recycleviewService.setLayoutManager(new GridLayoutManager(activity, 4));
        recycleviewInquire.setLayoutManager(new GridLayoutManager(activity, 4));

        recycleviewService.setAdapter(serviceAdapter);
        recycleviewInquire.setAdapter(inquireAdapter);

        serviceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                HomeMenuModel model = (HomeMenuModel) baseQuickAdapter.getData().get(i);
                GoActivityUtil.goActivityByType(activity, model);

            }
        });

        inquireAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                HomeMenuModel model = (HomeMenuModel) baseQuickAdapter.getData().get(i);
                GoActivityUtil.goActivityByType(activity, model);
            }
        });


        nestedScrollView = rootView.findViewById(R.id.nestedScrollView);
        textCompany.setTypeface(Typeface.DEFAULT_BOLD);


        certificationImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppUtils.getUser() == null) {
                    startActivity(LoginActivity.class);
                    return;
                }
                startActivity(CertifiedCompanyActivity.class);
            }
        });

        changeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (companyInfo != null) {
                    startActivity(MonitorDetailActivity.getIntent(activity, companyInfo.companyId, companyInfo.companyName));
                }
            }
        });


        netWorkError.setListener(new NetWorkErrorView.OnGXCRefreshListener() {
            @Override
            public void OnNetRefresh() {
                getServiceData();
            }
        });
        initChart();
        getServiceData();
    }

    private boolean isSetChartBg = false;

    private void resetChartBg() {
        if (isSetChartBg) return;
        isSetChartBg = true;
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RectF rectF = chart.getContentRect();
                vChartBg.layout((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
                vChartBg.invalidate();
                vChartBg.requestLayout();

                int width = (int) (rectF.right - rectF.left);
                width += width / 11;

                vChartBg.getLayoutParams().width = width;
                vChartBg.getLayoutParams().height = (int) (rectF.bottom - rectF.top);

                PropertyValuesHolder animX = PropertyValuesHolder.ofFloat("translationX", rectF.left);
                PropertyValuesHolder animY = PropertyValuesHolder.ofFloat("translationY", rectF.top);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(vChartBg, animX, animY);
                animator.setDuration(0);
                animator.setInterpolator(new DecelerateInterpolator());
                animator.start();
            }
        }, 200);
    }

    private void initChart() {

//        chart.setOnChartValueSelectedListener(this);

        chart.setNoDataText("暂无数据");
        chart.setNoDataTextColor(Color.parseColor("#999999"));
        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

//        chart.setPadding(40, 0, 40, 0);
        chart.setExtraOffsets(0, 0, 20, 0);

//        chart.getViewPortHandler().setma

        // enable scaling and dragging
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
//        chart.setDrawGridBackground(false);
//        chart.setGridBackgroundColor(Color.parseColor("#F5F5F5"));
        chart.setHighlightPerDragEnabled(true);
        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);

//        chart.fitScreen();


        // set an alternative background color
        chart.getAxisRight().setEnabled(false);
//         add data
//        seekBarX.setProgress(20);
//        seekBarY.setProgress(30);

        chart.animateX(1200);
//        chart.setBackgroundColor(Color.GRAY);
        //是否显示边界
//        chart.setDrawBorders(false);

//        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();
        l.setEnabled(false);
//
        XAxis xAxis = chart.getXAxis();
//        xAxis.setTypeface(tfLight);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(true);
//        xAxis.setGridLineWidth(40);
//        xAxis.setGridColor(Color.parseColor("#F5F5F5"));
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(0xff333333);
//        xAxis.setLabelCount(3);
//        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1);
        xAxis.setAxisMinimum(-0.3f);
        xAxis.setGridColor(Color.parseColor("#CCCCCC"));
//        xAxis.setYOffset(-10);
//        xAxis.setXOffset(-10);

        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);
//        leftAxis.setGridDashedLine(new DashPathEffect(new float[]{30f, 30f}, 10f));
        leftAxis.setGranularityEnabled(true);
        leftAxis.setTextColor(0xff333333);
        leftAxis.setLabelCount(4);
//        leftAxis.setXOffset(100);
//        leftAxis.setYOffset(100);
//        leftAxis.setMaxWidth(200);
//        leftAxis.setMinWidth(200);

//        leftAxis.setGridLineWidth(10);
//        leftAxis.setGridColor(Color.parseColor("#F5F5F5"));

        leftAxis.setTextSize(10f);


    }


    private void setData(final List<CreditDataModel.VisitorItem> list) {

        if (list == null) {
            return;
        }

        float max = 0;

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            values1.add(new Entry(i, list.get(i).count));
            if (max < list.get(i).count)
                max = list.get(i).count;
//            values1.add(new Entry(i, AppUtils.getRandom(20, 100)));
        }

        if (max < 10)
            max = 10;
        else
            max += max * 0.15;
        chart.getAxisLeft().setAxisMaximum(max);

        LineDataSet set1;

        chart.getXAxis().setValueFormatter(new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (value < 0 || value > (list.size() - 1))
                    return "";
                return list.get((int) value % list.size()).date;
            }
        });


        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values1);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values1, "");
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(0xfff20000);
            set1.setCircleColor(0xfff20000);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3.5f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            set1.setCircleHoleRadius(2f);
            set1.setCircleColorHole(0xfff20000);
            set1.setDrawValues(false);
            // create a data object with the data sets
            LineData data = new LineData(set1);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            // set data
            chart.setData(data);
            chart.invalidate();
        }
//        chart.moveViewToX(5);
        resetChartBg();


    }

    private void getServiceData() {
        netWorkError.showLoading();

        RxManager.http(RetrofitUtils.getApi().getCreditService(), new ResponseCall() {

            @Override
            public void success(NetModel data) {

                if (data.success()) {
                    netWorkError.success();
                    CreditDataModel model = data.dataToObject(CreditDataModel.class);
                    if (model.companyInfo != null) {
                        companyInfo = model.companyInfo;
                        //认证状态  0：未认证 1：审核中 2：审核失败 3：审核成功
                        if ("0".equals(model.companyInfo.status)) {
                            initUICredit(false);
                            certificationImg.setVisibility(View.VISIBLE);
                            topLayout.setVisibility(View.GONE);
//                            textTitle.setTextColor(0xff333333);
//                            layoutRoot.setBackgroundColor(0xffffffff);
                        } else if ("1".equals(model.companyInfo.status)) {
                            shImg.setVisibility(View.VISIBLE);
                            initUICredit(false);
                            topLayout.setVisibility(View.VISIBLE);
                        } else if ("2".equals(model.companyInfo.status)) {
                            initUICredit(false);
                            certificationImg.setVisibility(View.VISIBLE);
                            topLayout.setVisibility(View.GONE);
//                            textTitle.setTextColor(0xff333333);
//                            layoutRoot.setBackgroundColor(0xffffffff);
                        } else if ("3".equals(model.companyInfo.status)) {
                            shImg.setVisibility(View.GONE);
                            initUICredit(true);
                            topLayout.setVisibility(View.VISIBLE);
                        }

                        textNum.setText(model.companyInfo.changeNum);

                        textCompany.setText(model.companyInfo.companyName);
                        textXinyongCode.setText(model.companyInfo.code);
                        textCompayType.setText(model.companyInfo.type);


                        UserModel userModel = AppUtils.getUser();
                        if (userModel != null) {
                            userModel.taxid = model.companyInfo.code;
                            userModel.states = model.companyInfo.type;
                            if (!TextUtils.isEmpty(model.companyInfo.companyId)) {
                                userModel.companyId = model.companyInfo.companyId;
                            }
                            PreferenceUtils.setString(activity, Constants.USER, new Gson().toJson(userModel));
                        }
                        setData(model.VisitorList);
                        serviceAdapter.setNewData(model.serviceList);
                        inquireAdapter.setNewData(model.inquiryList);
                    } else {
                        netWorkError.error();
                    }
                } else {
                    netWorkError.error();
                }
            }

            @Override
            public void error() {
                netWorkError.error();
            }
        });


    }

    private boolean isFirst = true;

    @Override
    public void onResume() {
        super.onResume();
        if (netWorkError.getVisibility() == View.GONE && !isFirst)
            AppUtils.checkUserStatus(null);
        isFirst = false;
    }

    private void initUICredit(boolean isCredit) {

//        textTitle.setTextColor(0xffffffff);
//        layoutRoot.setBackgroundColor(0xffe83836);

        if (isCredit) {
            viewLine.setVisibility(View.VISIBLE);
            layoutFangke.setVisibility(View.VISIBLE);
            certificationImg.setVisibility(View.GONE);
            changeLayout.setVisibility(View.VISIBLE);
            topLayout.setVisibility(View.VISIBLE);
            layoutService.setVisibility(View.VISIBLE);
        } else {

            layoutService.setVisibility(View.GONE);
            viewLine.setVisibility(View.GONE);
            layoutFangke.setVisibility(View.GONE);
            certificationImg.setVisibility(View.GONE);
            changeLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof LoginChangeEvent || event instanceof AuthStatusChangeEvent) {
            getServiceData();
        }
    }

}
