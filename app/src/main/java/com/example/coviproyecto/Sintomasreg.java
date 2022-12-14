package com.example.coviproyecto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Sintomasreg extends AppCompatActivity {
    RecyclerView Sintomasreg;
    TextView fecha;
    int tipo;
    String url= "http://coviapp.atwebpages.com/project/Prueba.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomasreg);
        Sintomasreg= findViewById(R.id.rv_sintomasreg);
        fecha = findViewById(R.id.txt_sfactual);
        fecha.setText(fechaAcct());
        mostrarSintomas();

    }

    private void configRecycler(){
        //Toast.makeText(getApplicationContext(),Info.sintomasList.size(), Toast.LENGTH_SHORT).show();
        Sintomasreg.setHasFixedSize(true);
        Adaptersinr adaptersinr= new Adaptersinr();
        adaptersinr.context=this;
        LinearLayoutManager llm= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        Sintomasreg.setLayoutManager(llm);
        Sintomasreg.setAdapter(adaptersinr);
    }

    private String CargarPreferencias(){
        SharedPreferences preferences= getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String ID= preferences.getString("ID", "NA");
        tipo= preferences.getInt("Tipo", 3);
        return ID;
    }

    private String fechaRegistro(){
        String FechaRegistro;
        Time hoy= new Time(Time.getCurrentTimezone());
        hoy.setToNow();
        int dia= hoy.monthDay;
        int mes= hoy.month;
        int an= hoy.year;
        mes=mes+1;
        FechaRegistro= String.valueOf(an)+"-"+String.valueOf(mes)+"-"+String.valueOf(dia);
        return FechaRegistro;
    }

    private String fechaAcct(){
        String FechaRegistro;
        Time hoy= new Time(Time.getCurrentTimezone());
        hoy.setToNow();
        int dia= hoy.monthDay;
        int mes= hoy.month;
        int an= hoy.year;
        mes=mes+1;
        FechaRegistro= String.valueOf(dia)+" de "+String.valueOf(mes)+" del "+String.valueOf(an);
        return FechaRegistro;
    }

    private void mostrarSintomas(){
        String IDuser= CargarPreferencias(), FechaRegistro= fechaRegistro();

        StringRequest request= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                if(!response.isEmpty()) {
                    Info.sintomasList.clear();

                    String[] sintomas = response.split(",");
                    for (int i = 0; i < sintomas.length; i++) {

                        Info.sintomasList.add(sintomas[i]);
                    }
                    //Toast.makeText(getApplicationContext(), String.valueOf(sintomas.length), Toast.LENGTH_SHORT).show();
                    Info.sintomasList.remove(sintomas[sintomas.length-1]);
                    //Toast.makeText(getApplicationContext(), String.valueOf(Info.sintomasList.size()), Toast.LENGTH_SHORT).show();
                    configRecycler();
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
                params.put("FechaRegistro", FechaRegistro);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Sintomasreg.this);
        requestQueue.add(request);
    }

    public void registrarSintomas(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startActivity(new Intent(getApplicationContext(), Principal.class));
        }
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