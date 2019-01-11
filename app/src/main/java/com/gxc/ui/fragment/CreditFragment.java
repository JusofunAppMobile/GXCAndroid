package com.gxc.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.gxc.base.BaseFragment;
import com.gxc.model.HomeMenuModel;
import com.gxc.ui.activity.CertifiedCompanyActivity;
import com.gxc.ui.activity.CreditCommitmentActivity;
import com.gxc.ui.activity.CreditReportActivity;
import com.gxc.ui.activity.ReportInfoActivity;
import com.gxc.ui.activity.VisitorListActivity;
import com.gxc.ui.adapter.HomeMenuAdapter;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.activity.CompanyAmendActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
    ConstraintLayout layoutRoot;
    Unbinder unbinder;
    @BindView(R.id.line_chart)
    LineChart chart;
    private ImageView certificationImg;

    private RelativeLayout topLayout;

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


        List<HomeMenuModel> list = new ArrayList<>();

        //0网页，1信用报告，2信用评价，3信用异议，4信用修复5，信用承诺，6访客，7自主填报
        list.add(new HomeMenuModel("信用报告", 1));
        list.add(new HomeMenuModel("信用评价", 2));
        list.add(new HomeMenuModel("信用异议", 3));
        list.add(new HomeMenuModel("信用修复", 4));
        list.add(new HomeMenuModel("信用承诺", 5));
        list.add(new HomeMenuModel("访客", 6));
        list.add(new HomeMenuModel("自主填报", 7));

        serviceAdapter.addData(list);

        List<HomeMenuModel> inquirelist = new ArrayList<>();

        ////0网页，1税务案件查询，2裁判文书查询，3黑名单查询，4招投标查询，5商标查询，6著作权查询，7专利查询，8 企业备案信息查询
        inquirelist.add(new HomeMenuModel("税务案件查询", 1));
        inquirelist.add(new HomeMenuModel("裁判文书查询", 2));
        inquirelist.add(new HomeMenuModel("黑名单查询", 3));
        inquirelist.add(new HomeMenuModel("招投标查询", 4));
        inquirelist.add(new HomeMenuModel("商标查询", 5));
        inquirelist.add(new HomeMenuModel("著作权查询", 6));
        inquirelist.add(new HomeMenuModel("专利查询", 7));
        inquirelist.add(new HomeMenuModel("企业备案信息查询", 8));

        inquireAdapter.addData(inquirelist);
        serviceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

                HomeMenuModel model = (HomeMenuModel) baseQuickAdapter.getData().get(i);
                if (model.menuType == 1) {
                    Intent intent = new Intent(activity, CreditReportActivity.class);
                    startActivity(intent);
                } else if (model.menuType == 3) {
                    Intent intent = new Intent(activity, CompanyAmendActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("company", null);
                    bundle.putInt(CompanyAmendActivity.TYPE,CompanyAmendActivity.TYPE_OBJECTION);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (model.menuType == 7) {
                    Intent intent = new Intent(activity, ReportInfoActivity.class);
                    startActivity(intent);
                }else if(model.menuType == 6){ // 访客
                    startActivity(VisitorListActivity.class);
                }else if(model.menuType == 5){ // 访客
                    startActivity(CreditCommitmentActivity.class);
                }




            }
        });

        inquireAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
            }
        });


        textCompany.setText("大洋科技");
        nestedScrollView = (NestedScrollView) rootView.findViewById(R.id.nestedScrollView);
        textCompany.setTypeface(Typeface.DEFAULT_BOLD);


        certificationImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CertifiedCompanyActivity.class);
                startActivity(intent);
                topLayout.setVisibility(View.VISIBLE);
                certificationImg.setVisibility(View.GONE);


                textTitle.setTextColor(0xffffffff);
                layoutRoot.setBackgroundColor(0xffe83836);
            }
        });

        textTitle.setTypeface(Typeface.DEFAULT_BOLD);
        textTitle.setTextColor(0xff333333);
        layoutRoot.setBackgroundColor(0xffffffff);
        initChart();
        setData(6,150);

    }

    private void initChart(){
//        chart.setOnChartValueSelectedListener(this);

        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        chart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setHighlightPerDragEnabled(true);
        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);


        // set an alternative background color
        chart.setBackgroundColor(Color.WHITE);
        chart.getAxisRight().setEnabled(false);
//         add data
//        seekBarX.setProgress(20);
//        seekBarY.setProgress(30);

        chart.animateX(1500);

//        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();
        l.setEnabled(false);
//
//        // modify the legend ...
//        l.setForm(Legend.LegendForm.NONE);
////        l.setTypeface(tfLight);
//        l.setTextSize(11f);
//        l.setTextColor(Color.WHITE);
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        l.setYOffset(11f);

        XAxis xAxis = chart.getXAxis();
//        xAxis.setTypeface(tfLight);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(0xff333333);
        xAxis.setCenterAxisLabels(true);


        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaximum(200f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setTextColor(0xff333333);
        leftAxis.setTextSize(10f);

//        YAxis rightAxis = chart.getAxisRight();
////        rightAxis.setTypeface(tfLight);
//        rightAxis.setTextColor(Color.RED);
//        rightAxis.setAxisMaximum(900);
//        rightAxis.setAxisMinimum(-200);
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setDrawZeroLine(false);
//        rightAxis.setGranularityEnabled(false);


    }


    private void setData(int count, float range) {

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * (range / 2f)) + 50;
            values1.add(new Entry(i, val));
        }

        LineDataSet set1;

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
            set1.setCircleColor(0xfffacbce);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3.5f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(true);
            set1.setCircleHoleRadius(2.5f);
            set1.setCircleColorHole(0xfff20000);
            set1.setDrawValues(false);
            // create a data object with the data sets
            LineData data = new LineData(set1);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            // set data
            chart.setData(data);
        }
    }

}
