package com.example.coviproyecto;

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
        String IDuser= CargarPreferencias(), FechaRegistro= fechaRegistro(); ;
        RequestQueue conect = Volley.newRequestQueue(this);
        JsonObjectRequest peticion = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(Sintomasreg.this, response.length(), Toast.LENGTH_SHORT).show();
                Info.sintomasList.clear();

                try {
                    JSONArray jsonArray= response.getJSONArray("");
                    Toast.makeText(Sintomasreg.this, jsonArray.length(), Toast.LENGTH_SHORT).show();

                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonSintoma= jsonArray.getJSONObject(i);
                        Sintoma sintoma= new Sintoma();
                        sintoma.setID(jsonSintoma.getInt("ID"));
                        sintoma.setDato1(jsonSintoma.getInt("0"));
                        sintoma.setNombre(jsonSintoma.getString("Nombre"));
                        sintoma.setDato2(jsonSintoma.getInt("1"));
                        Toast.makeText(Sintomasreg.this, String.valueOf(sintoma.getID())+", "+ sintoma.getNombre(), Toast.LENGTH_SHORT).show();
                        Info.sintomasList.add(sintoma);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /*for (int i = 0; i < response.length(); i++) {
                    try {
                        Sintoma sintoma = new Sintoma();
                        JSONObject jsonObject = response;

                        //unpedido.setId(obj.getInt("ID_pedido"));
                        sintoma.setID(jsonObject.getInt(("ID")));
                        sintoma.setDato1(jsonObject.getInt((("0"))));
                        sintoma.setNombre(jsonObject.getString("Nombre"));
                        sintoma.setDato2(jsonObject.getInt(("1")));

                        Info.sintomasList.add(sintoma);

                    } catch (JSONException e) {
                        Toast.makeText(Sintomasreg.this, e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Sintomasreg.this, "Error: "+error, Toast.LENGTH_SHORT).show();
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
        conect.add(peticion);
    }

    public void registrarSintomas(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startActivity(new Intent(getApplicationContext(), Principal.class));
        }
    }
}