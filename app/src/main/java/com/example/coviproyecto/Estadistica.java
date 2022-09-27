package com.example.coviproyecto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import java.util.HashMap;
import java.util.Map;

public class Estadistica extends AppCompatActivity {
    private BarChart barChart;
    private String[] sintomas;
    private int[] cuenta;
    private int[] color= new int[]{Color.CYAN, Color.YELLOW, Color.GREEN, Color.RED, Color.BLUE, Color.MAGENTA, Color.GRAY, Color.LTGRAY, Color.BLACK, Color.DKGRAY, Color.CYAN, Color.YELLOW, Color.GREEN, Color.RED, Color.BLUE};

    Spinner opciongraf;
    TextView selecgraf;
    int grafica;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadistica);
        barChart= (BarChart) findViewById(R.id.bar_chart);
        opciongraf= findViewById(R.id.spin_grafica);
        selecgraf=findViewById(R.id.txtgraf_s);
        //createCharts();

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource
                (this,R.array.opgrafica, android.R.layout.simple_spinner_item);
        opciongraf.setAdapter(adapter);
        opciongraf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int posicion, long id) {
                selecgraf.setText(parent.getItemAtPosition(posicion).toString());
                grafica=posicion+1;
                switch (grafica){
                    case 1:
                        ObtenerXYgraf(1);
                        break;
                    case 2:
                        ObtenerXYgraf(2);
                        break;
                    case 3:
                        ObtenerXYgraf(3);
                        break;
                    case 4:
                        ObtenerXYgraf(4);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private String CargarPreferencias(){
        SharedPreferences preferences= getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String ID= preferences.getString("ID", "NA");
        int tipo= preferences.getInt("Tipo", 3);
        return ID;
    }

    private void ObtenerXYgraf(int opcion){
        String ID=CargarPreferencias();
        String url="";
        String urll="";
        switch (opcion){
            case 1:

                break;
            case 2:
                url="http://coviapp.atwebpages.com/project/GraficaEjex.php";
                urll= "http://coviapp.atwebpages.com/project/GraficaEjey.php";
                datosX(url, ID, "1");
                datosY(urll, ID, "1");
                //Toast.makeText(getApplicationContext(), sintomas.toString(), Toast.LENGTH_SHORT).show();
                try {
                    createCharts();
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

                break;
            case 3:
                url="http://coviapp.atwebpages.com/project/GraficaEjex.php";
                urll= "http://coviapp.atwebpages.com/project/GraficaEjey.php";
                datosX(url, ID, "2");
                datosY(urll, ID, "2");
                //Toast.makeText(getApplicationContext(), cuenta.toString(), Toast.LENGTH_SHORT).show();
                try {
                    createCharts();
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            case 4:
                break;
        }

    }

    private void datosX(String url, String IDuser, String opcion){
        Toast.makeText(getApplicationContext(), IDuser+" "+opcion, Toast.LENGTH_SHORT).show();
        StringRequest request= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Response: "+response, Toast.LENGTH_SHORT).show();
                if(!response.isEmpty()) {
                    String[] sin = response.split(",");
                    sintomas= new String[sin.length-1];
                    for (int i = 0; i < sin.length-1; i++) {
                        sintomas[i]=sin[i];
                        //Toast.makeText(getApplicationContext(), sintomas[i], Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Aun no hay sintomas registrados", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();
                params.put("IDuser", IDuser);
                params.put("opcion", opcion);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Estadistica.this);
        requestQueue.add(request);
    }

    private void datosY(String url, String IDuser, String opcion){
        StringRequest request= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Response: "+response, Toast.LENGTH_SHORT).show();
                if(!response.isEmpty()) {
                    String[] sin = response.split(",");
                    cuenta= new int[sin.length-1];
                    for (int i = 0; i < sin.length-1; i++) {

                        //Toast.makeText(getApplicationContext(), sin[i], Toast.LENGTH_SHORT).show();
                        try {
                            cuenta[i]= Integer.parseInt(sin[i].trim());
                        }catch (Exception ex){
                            Toast.makeText(getApplicationContext(), "Error"+ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(getApplicationContext(), String.valueOf(cuenta[i]), Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Aun no hay sintomas registrados", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();
                params.put("IDuser", IDuser);
                params.put("opcion", opcion);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Estadistica.this);
        requestQueue.add(request);
    }

    public  void createCharts(){
        barChart= (BarChart)getSameChart(barChart, "",Color.BLACK, Color.WHITE,  3000);
        barChart.setDrawBarShadow(true);
        barChart.setDrawGridBackground(true);
        barChart.setData(getBarData());
        barChart.invalidate();
        axisX(barChart.getXAxis());
        axisLeft(barChart.getAxisLeft());
        axisRight(barChart.getAxisRight());
    }

    private Chart getSameChart(Chart chart, String description, int textColor, int background, int animateY){
        chart.getDescription().setText(description);
        chart.getDescription().setTextSize(10);
        chart.setBackgroundColor(background);
        chart.animateY(animateY);
        //legend(chart);
        return chart;
    }

    private  void legend(Chart chart){
        Legend legend= chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<LegendEntry> entries = new ArrayList<>();
        for(int i=0; i< sintomas.length; i++){
            LegendEntry entry=new LegendEntry();
            entry.formColor= Color.CYAN;
            entry.label= sintomas[i];
            entry.formSize= 5;
            entries.add(entry);
        }
        legend.setCustom(entries);
    }
    private  ArrayList<BarEntry> getBarEntries(){
        ArrayList<BarEntry> entries= new ArrayList<>();
        for (int i=0; i< cuenta.length; i++){
            entries.add(new BarEntry(i,cuenta[i]));
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

    private DataSet getData(DataSet dataSet){
        dataSet.setColors(color);
        dataSet.setValueTextSize(Color.BLACK);
        dataSet.setValueTextSize(10);
        return dataSet;
    }
    private BarData getBarData(){
        BarDataSet barDataSet= (BarDataSet)getData(new BarDataSet(getBarEntries(),""));
        barDataSet.setBarShadowColor(Color.WHITE);
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