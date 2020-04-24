package com.tancheng.carbonchain.activities.storage.mine;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.tancheng.carbonchain.R;
import com.tancheng.carbonchain.customview.PopupInputReject;

import java.util.ArrayList;

import static com.github.mikephil.charting.components.Legend.LegendPosition.LEFT_OF_CHART_CENTER;

/**
 *用户存储信息页面
 */
public class UserStorageActivity extends AppCompatActivity implements PopupInputReject.PopupRejectInteface {

    //饼状图
    private PieChart mPieChart;
    private PopupInputReject popupInputReject;
    private TextView tvDeviceUnBind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_storage);
        initView();
    }

    private void initView() {
        //饼状图
        mPieChart =  findViewById(R.id.mPieChart);
        setPieChart();
        tvDeviceUnBind = findViewById(R.id.tvDeviceUnBind);
        popupInputReject = new PopupInputReject(this,this);

        tvDeviceUnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupInputReject.showPopupWindow();
            }
        });
    }

    /**
     * 饼状图设置参数
     */
    private void setPieChart()
    {
        //是否使用百分比
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        //设置不显示图中文字
        mPieChart.setDrawSliceText(false);
        //是否可以旋转
        mPieChart.setRotationEnabled(true);
        //圆环距离屏幕上下左右的距离
        mPieChart.setExtraOffsets(0, 0, 0, 0);
        ////设置滑动时的摩擦系数（值越小摩擦系数越大）
        mPieChart.setDragDecelerationFrictionCoef(0.95f);
//      //是否显示圆环中间的洞
        mPieChart.setDrawHoleEnabled(true);
        //是否显示洞中间文本
        mPieChart.setDrawCenterText(true);
        mPieChart.setCenterText(generateCenterSpannableText());
        //设置中间洞的颜色
        mPieChart.setHoleColor(Color.WHITE);
        //设置圆环透明度及半径
        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);
        mPieChart.setTransparentCircleRadius(110f);
        //设置圆环中间洞的半径
        mPieChart.setHoleRadius(65f);
        mPieChart.setRotationAngle(0);
        // 触摸是否可以旋转以及松手后旋转的度数
        mPieChart.setRotationEnabled(true);
        mPieChart.setRotationAngle(20);
        mPieChart.setHighlightPerTapEnabled(true);

//        //add a selection listener 值改变时候的监听
//        mPieChart.setOnChartValueSelectedListener(this);

        setPieChartData(5,7);


    }
    /**
     * 设置饼状图数据
     */
    private void setPieChartData(int count, float range)
    {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) (Math.random() * range) + range / 5, "图片 46.9G"));
        entries.add(new PieEntry((float) (Math.random() * range) + range / 5, "视频 28.4G"));
        entries.add(new PieEntry((float) (Math.random() * range) + range / 5, "音频 18.5G"));
        entries.add(new PieEntry((float) (Math.random() * range) + range / 5, "文档 42.2G"));
        entries.add(new PieEntry((float) (Math.random() * range) + range / 5, "其它 43.7G"));
        // 输入标签样式
//        mPieChart.setEntryLabelColor(Color.WHITE);
        //高亮效果设置
        mPieChart.setEntryLabelTextSize(15f);
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(12f);

        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add( Color.rgb(55, 81, 254));
        colors.add( Color.rgb(124, 141, 255));
        colors.add( Color.rgb(3, 228, 138));
        colors.add( Color.rgb(157, 251, 135));
        colors.add( Color.rgb(255, 231, 136));
//        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        mPieChart.setUsePercentValues(true);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.WHITE);
        mPieChart.setData(data);
        mPieChart.highlightValues(null);



        //设置标签
        Legend l = mPieChart.getLegend();
        l.setEnabled(true);//是否显示Legend
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setDrawInside(false);
//        l.setTextSize(14f);
        l.setXEntrySpace(10f);//设置在水平轴上 legend-entries 的间隙
        l.setYEntrySpace(10f);//设置在垂直轴上 legend-entries 的间隙
        l.setTextColor(getResources().getColor(R.color.color_4A4A4A));
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(15f);
        l.setFormToTextSpace(10f);//设置 legend-form 和 legend-label 之间的空间。
//        l.setExtra(mPieChart.getData().getColors(),mPieChart.getData().getDataSetLabels());
        l.setPosition(LEFT_OF_CHART_CENTER);
        //刷新
        mPieChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("已用空间\n179.7G");
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 4, 0);
        s.setSpan(new RelativeSizeSpan(2.3f), 4, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 4, s.length(), 0);
        s.setSpan(new RelativeSizeSpan(.8f), 4, s.length(), 0);
//        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
//        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }
    /**
     * 确定弹框
     * @param content
     */
    @Override
    public void onComplete(String content) {
        if (popupInputReject != null){
            popupInputReject.dismiss();
        }
        startActivity(new Intent(this,TransferAdminActivity.class));
    }


}
