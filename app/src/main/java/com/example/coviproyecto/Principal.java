package com.example.coviproyecto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
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

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Principal extends AppCompatActivity implements SintomasListener{
    TextView txtFechaact;
    RecyclerView rv_sintomas;
    MultiAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        txtFechaact= findViewById(R.id.txt_fechaact);
        rv_sintomas= findViewById(R.id.recycler_sintomas);

        txtFechaact.setText(fechaRegistro());
        
        setRecyclerView();
        CargarPreferencias();

    }
    private String CargarPreferencias(){
        SharedPreferences preferences= getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String ID= preferences.getString("ID", "NA");
        int tipo= preferences.getInt("Tipo", 3);
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

    private ArrayList<String> getSintomas(int tipo){
        ArrayList<String> arrayList = new ArrayList<>();
        if(tipo==0) {

            arrayList.add("Ninguno");
            arrayList.add("Fiebre o escalofrios");
            arrayList.add("Tos");
            arrayList.add("Dificultad para respirar");
            arrayList.add("Fatiga");
            arrayList.add("Dolores musculares");
            arrayList.add("Dolor de cabeza");
            arrayList.add("Perdida del olfato o/y el gusto");
            arrayList.add("Dolor de garganta");
            arrayList.add("Congestión o moqueo");
            arrayList.add("Nausas o vomito");
            arrayList.add("Diarrea");
        }
        if(tipo==1){
            arrayList.add("Ninguno");
            arrayList.add("Fiebre");
            arrayList.add("Tos");
            arrayList.add("Dificultad para respirar");
            arrayList.add("Dificultad para concentrarse");
            arrayList.add("Dolor de pecho o estomago");
            arrayList.add("Dolor de cabeza");
            arrayList.add("Perdida del olfato o/y el gusto");
            arrayList.add("Dolor de espalda");
            arrayList.add("Dolor de cuello");
            arrayList.add("Sensacion de hormigueo");
            arrayList.add("Diarrea");
            arrayList.add("Malestar general");
            arrayList.add("Problemas para dormir");
            arrayList.add("Corazon que late muy rapido/fuerte");
            arrayList.add("Mareos");
            arrayList.add("Sarpullido");
            arrayList.add("Cambios de animo");
            arrayList.add("Cambios de ciclo menstrual");
        }
        return arrayList;
    }

    private void setRecyclerView() {
        SharedPreferences preferences= getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        int tipo= preferences.getInt("Tipo", 3);
        rv_sintomas.setHasFixedSize(true);
        rv_sintomas.setLayoutManager(new LinearLayoutManager(this));
        adapter= new MultiAdapter(this, getSintomas(tipo), this);
        rv_sintomas.setAdapter(adapter);

    }

    /*
    for (int b=0; b<enfermedadList.size(); b++){
                indexms2=discapacidadList.get(b).toString();
                int ini= Integer.parseInt(indexms2)+1;
                InsertMultiselectDis(String.valueOf(ini), ID);
            }
     */

    public void GuardarSintomas(View view) {
        

    }


    @Override
    public void onSintomasChange(ArrayList<String> arrayList) {
        Toast.makeText(this, arrayList.toString(), Toast.LENGTH_SHORT).show();
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
                SharedPreferences.Editor editor = getSharedPreferences("credenciales", MODE_PRIVATE).edit();
                editor.clear().apply();
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