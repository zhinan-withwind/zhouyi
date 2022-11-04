package com.zhinan.zhouyi.out;

import com.zhinan.zhouyi.date.DateFormatType;
import com.zhinan.zhouyi.date.DateTimeFormatter;
import com.zhinan.zhouyi.date.DateType;
import com.zhinan.zhouyi.date.SolarDateTime;
import com.zhinan.zhouyi.fate.luck.运势;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class LuckOutputter {

    public static void output(String name, List<? extends 运势> luckList, String fileName) throws IOException {
        String title = generateChartTitle(name, luckList);
        JFreeChart chart = ChartFactory.createXYLineChart(title, "时间", "运势",
                generateDataset(luckList), PlotOrientation.VERTICAL, false, false, false);

        // 设置图表标题的字体
        Font font = new Font("报隶-繁", Font.BOLD,20);
        chart.getTitle().setFont(font);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlinePaint(Color.GRAY);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setLowerMargin(0.1);// 设置距离图片左端距离此时为10%
        domainAxis.setUpperMargin(0.1);// 设置距离图片右端距离此时为百分之10
        domainAxis.setNumberFormatOverride(new DecimalFormat("0"));// 设置y轴以百分比方式显示


        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLowerMargin(0.1);// 设置距离图片左端距离此时为10%
        rangeAxis.setUpperMargin(0.1);// 设置最高的一个柱与图片顶端的距离(最高柱的10%)
        rangeAxis.setNumberFormatOverride(new DecimalFormat("0"));// 设置y轴以百分比方式显示

        //平滑曲线
        XYSplineRenderer renderer = new XYSplineRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);
        plot.setRenderer(renderer);

        File file = new File(fileName);
        if (file.isDirectory()) {
            file = new File(fileName + "/" + title + ".jpg");
        }
        ChartUtils.saveChartAsJPEG(file, chart, 1000, 400);
    }

    public static XYDataset generateDataset(List<? extends 运势> luckList) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("运势");
        for (运势 luck : luckList) {
            switch (luck.getType()) {
                case 大运:
                case 年运:
                    series.add(luck.getStartTime().getYear(), luck.getScore());
                    break;
                case 月运:
                    series.add(luck.getStartTime().getMonthValue(), luck.getScore());
                    break;
                case 日运:
                    series.add(luck.getStartTime().getDayOfMonth(), luck.getScore());
                    break;
                case 时运:
                    series.add(luck.getStartTime().getHour(), luck.getScore());
            }
        }
        dataset.addSeries(series);
        return dataset;
    }

    public static String generateChartTitle(String name, List<? extends 运势> luckList) {
        StringBuilder title = new StringBuilder();
        if (luckList.size() > 0) {
            运势 luck =  luckList.get(0);
            title.append(name);
            switch (luck.getType()) {
                case 大运:
                    title.append("人生『大运走势图』");
                    break;
                case 年运:
                    if (luckList.size() > 10) {
                        title.append("人生");
                    } else {
                        title.append(luck.getStartTime().getYear()).append("年 - ")
                             .append(luckList.get(luckList.size() - 1).getStartTime().getYear()).append("年");
                    }
                    title.append("『流年走势图』");
                    break;
                case 月运:
                    title.append(luck.getStartTime().getYear()).append("年");
                    title.append("『月运走势图』");
                    break;
                case 日运:
                    title.append(luck.getStartTime().getYear()).append("年");
                    title.append(luck.getStartTime().getMonthValue()).append("月");
                    title.append("『日运走势图』");
                    break;
                case 时运:
                    title.append(DateTimeFormatter.getInstance(SolarDateTime.of(luck.getStartTime())).format(DateFormatType.ARABIC_NUMBER, DateType.FULL_DATE));
                    title.append("『时运走势图』");
            }
        }
        return title.toString();
    }

}
