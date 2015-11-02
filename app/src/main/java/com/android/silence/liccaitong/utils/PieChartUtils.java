package com.android.silence.liccaitong.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.text.NumberFormat;
import java.util.Map;
import java.util.Random;

/**
 * Created by Silence on 2015/10/31 0031.
 */
public class PieChartUtils {
    private static final int[] COLORS = new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN,
            Color.YELLOW, Color.DKGRAY};
    // 设置图例字体大小
    private int legendTextSize = 28;
    //设置饼图图例高度
    private int legendHeight = 0;
    // 设置饼图百分比的颜色
    private int labelColor = Color.BLACK;
    //设置饼图百分比的字体大小
    private int labelTextSize = 30;
    // 设置饼图标题大小
    private int titleSize = 45;
    private Context context;
    // 用来显示PieChart
    private GraphicalView pieChartView = null;
    // PieChart的主要描绘器
    private DefaultRenderer mRenderer = new DefaultRenderer();
    private CategorySeries mSeries = new CategorySeries("");
    /**
     * 数据集 key：名称 value：数目
     */
    private Map<String, Float> dataMaps;
    /**
     * 设置饼图的标题
     */
    private String pieTitle;

    public PieChartUtils() {
    }

    public PieChartUtils(Map<String, Float> dataMaps, Context context, String pieTitle) {
        this.dataMaps = dataMaps;
        this.context = context;
        this.pieTitle = pieTitle;
    }

    private double getAllSum() {
        double sum = 0;
        for (Map.Entry<String, Float> entry : dataMaps.entrySet()) {
            sum += entry.getValue();
        }
        return sum;
    }

    public GraphicalView getPieChartView() {
        mRenderer.setZoomButtonsVisible(false);// 显示放大缩小功能按钮
        mRenderer.setDisplayValues(true);// 显示数据
        mRenderer.setStartAngle(180);// 设置为水平开始
        mRenderer.setFitLegend(true);// 设置是否显示图例
        mRenderer.setLegendTextSize(legendTextSize);//
        mRenderer.setLabelsTextSize(labelTextSize);
        mRenderer.setLabelsColor(labelColor);
        mRenderer.setLegendHeight(legendHeight);
        mRenderer.setChartTitle(pieTitle);// 设置饼图标题
        mRenderer.setChartTitleTextSize(titleSize);
        mRenderer.setPanEnabled(false);// 图表是否可以移动
        mRenderer.setZoomEnabled(false);// 图表是否可以缩放
        mRenderer.setBackgroundColor(Color.WHITE);

        double sum = getAllSum();
        int color_i = 0;
        for (Map.Entry<String, Float> entry : dataMaps.entrySet()) {
            mSeries.add(entry.getKey(), entry.getValue() / sum);
            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            if (color_i < COLORS.length) {
                renderer.setColor(COLORS[color_i++]);// 设置描绘器的颜色
            } else {
                renderer.setColor(getRandomColor());// 设置描绘器的颜色
            }
            renderer.setChartValuesFormat(NumberFormat.getPercentInstance());// 设置百分比
            mRenderer.addSeriesRenderer(renderer);// 将最新的描绘器添加到DefaultRenderer中
        }
        if (pieChartView == null) {
            pieChartView = ChartFactory.getPieChartView(context, mSeries, mRenderer);//构建mChartView
        } else {
            pieChartView.repaint();
        }
        return pieChartView;
    }

    public void onClick(boolean isEnabled) {
        mRenderer.setClickEnabled(isEnabled);// 允许点击事件
        pieChartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取当前的类别和指针
                SeriesSelection seriesSelection = pieChartView.getCurrentSeriesAndPoint();
                if (seriesSelection == null) {
                    Toast.makeText(context, "未选择数据", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < mSeries.getItemCount(); i++) {
                        mRenderer.getSeriesRendererAt(i).setHighlighted(i == seriesSelection.
                                getPointIndex());
                    }
                    mRenderer.getSeriesRendererAt(seriesSelection.getPointIndex()).setHighlighted(true);
                    pieChartView.repaint();
                    String selectedTitle = mSeries.getCategory(seriesSelection.getPointIndex());
                    float selectedFloat = dataMaps.get(selectedTitle);
                    int selectedInt = (int) selectedFloat;
                    StringBuffer stringBuffer;
                    if (selectedInt != selectedFloat) {
                        stringBuffer = new StringBuffer(selectedTitle + " " + selectedFloat + "元");
                    } else {
                        stringBuffer = new StringBuffer(selectedTitle + " " + selectedInt + "元");
                    }
                    Toast.makeText(context, stringBuffer, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private int getRandomColor() {// 分别产生RBG数值
        Random random = new Random();
        int R = random.nextInt(255);
        int G = random.nextInt(255);
        int B = random.nextInt(255);
        return Color.rgb(R, G, B);
    }

    public int getLegendTextSize() {
        return legendTextSize;
    }

    public void setLegendTextSize(int legendTextSize) {
        this.legendTextSize = legendTextSize;
    }

    public int getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(int labelColor) {
        this.labelColor = labelColor;
    }

    public int getLabelTextSize() {
        return labelTextSize;
    }

    public void setLabelTextSize(int labelTextSize) {
        this.labelTextSize = labelTextSize;
    }

    public int getTitleSize() {
        return titleSize;
    }

    public void setTitleSize(int titleSize) {
        this.titleSize = titleSize;
    }

    public Map<String, Float> getDataMaps() {
        return dataMaps;
    }

    public void setDataMaps(Map<String, Float> dataMaps) {
        this.dataMaps = dataMaps;
    }

    public int getLegendHight() {
        return legendHeight;
    }

    public void setLegendHight(int legendHeight) {
        this.legendHeight = legendHeight;
    }

    public String getPieTitle() {
        return pieTitle;
    }

    public void setPieTitle(String pieTitle) {
        this.pieTitle = pieTitle;
    }
}
