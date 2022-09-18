package com.example.coviproyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class Estadistica extends AppCompatActivity {
    private BarChart barChart;
    private String[] sintomas= new String[]{"Fiebre", "Tos", "Fatiga", "Dolor de cabeza", "Congestion o moqueo", "Nauseas vomito", "Diarrea"};
    private int[] dias= new int[]{3,2,3,4,1,6,1};
    private int[] color= new int[]{Color.CYAN, Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.RED, Color.BLUE, Color.GRAY};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadistica);
        barChart= (BarChart) findViewById(R.id.bar_chart);
        createCharts();
    }
    private Chart getSameChart(Chart chart, String description, int textColor, int background, int animateY){
        chart.getDescription().setText(description);
        chart.getDescription().setTextSize(15);
        chart.setBackgroundColor(background);
        chart.animateY(animateY);
        legend(chart);
        return chart;
    }

    private  void legend(Chart chart){
        Legend legend= chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<LegendEntry> entries = new ArrayList<>();
        for(int i=0; i< sintomas.length; i++){
            LegendEntry entry=new LegendEntry();
            entry.formColor= color[i];
            entry.label= sintomas[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }
    private  ArrayList<BarEntry> getBarEntries(){
        ArrayList<BarEntry> entries= new ArrayList<>();
        for (int i=0; i< dias.length; i++){
            entries.add(new BarEntry(i,dias[i]));
        }
        return entries;
    }
    private  void axisX(XAxis axis){
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(sintomas));
        //axis.setEnabled(false);
    }

    private  void axisLeft(YAxis axis){
        axis.setSpaceTop(30);
        axis.setAxisMinimum(0);
    }
    private  void axisRight(YAxis axis){
        axis.setEnabled(false);
    }

    public  void createCharts(){
        barChart= (BarChart)getSameChart(barChart, "Series",Color.BLACK, Color.WHITE,  3000);
        barChart.setDrawBarShadow(true);
        barChart.setDrawGridBackground(true);
        barChart.setData(getBarData());
        barChart.invalidate();
        axisX(barChart.getXAxis());
        axisLeft(barChart.getAxisLeft());
        axisRight(barChart.getAxisRight());
    }
    private DataSet getData(DataSet dataSet){
        dataSet.setColors(color);
        dataSet.setValueTextSize(Color.BLACK);
        dataSet.setValueTextSize(10);
        return dataSet;
    }
    private BarData getBarData(){
        BarDataSet barDataSet= (BarDataSet)getData(new BarDataSet(getBarEntries(),""));
        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData= new BarData(barDataSet);
        barData.setBarWidth(0.45f);
        return barData;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.estadistica:
                Intent cambio3 = new Intent(this, Estadistica.class);
                startActivity(cambio3);
                return true;

            case R.id.sesion:
                Intent cambio1 = new Intent(this, MainActivity.class);
                startActivity(cambio1);
                return true;
            case R.id.cuenta:
                Intent cambio2 = new Intent(this, Configuracion.class);
                startActivity(cambio2);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}