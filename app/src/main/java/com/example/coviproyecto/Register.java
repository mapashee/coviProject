package com.example.coviproyecto;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    String url="http://coviapp.atwebpages.com/project/Registro.php";
    EditText user, pass, passconf;
    String tuser, tpass, tipo, fecha1= "Fecha desde que sabe que tiene covid", fecha2="Fecha desde que sabe que ya no tiene covid", tpassconf;
    RadioButton r1, r2;
    boolean Cvodistatus= true;
    boolean validUser=false;

    //SharedPreferences preferences= getSharedPreferences("Preferencias", Context.MODE_PRIVATE);

    /*
    sharedPreferences=getSharedPreferences(NAME, Context.MODE_PRIVATE);
if(!sharedPreferences.getString(NAME,"Default value").equals("Default value")){
  startActivity(new Intent(userPreferences.this, Welcome.class));
  finish();
}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        user= findViewById(R.id.txt_ruser);
        pass= findViewById(R.id.txt_rpass);
        passconf= findViewById(R.id.txt_rpasscon);

        r1= (RadioButton) findViewById(R.id.radio_1);
        r2= (RadioButton) findViewById(R.id.radio_2);
        //preferences = getSharedPreferences("Preferencias", MODE_PRIVATE);
        //if (!preferences.getString("ID", "Default value").equals("Default value")){
            //startActivity(new Intent(Register.this, Cuestionariouno.class));
        //}
        validarPreferences();


    }

    private void GuardarPreferencias(String id, int tipo){
        SharedPreferences preferences= getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putString("ID", id);
        editor.putInt("Tipo", tipo);
        editor.commit();
    }
    private void validarPreferences(){
        SharedPreferences preferences= getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String idd=preferences.getString("ID", "NA");
        if(!idd.equals("NA")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startActivity(new Intent(getApplicationContext(), Principal.class));
            }
        }
    }

    private void GuardarUsuario(String User, String Password, String Tipo){
        StringRequest request= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.startsWith("registro")) {
                    String id= response.substring(22);
                    GuardarPreferencias(id, Integer.valueOf(Tipo));
                    //SharedPreferences.Editor editor=preferences.edit();
                    //editor.putString("ID", id);
                    //editor.commit();
                    Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Los datos se registraron correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();
                params.put("User", User);
                params.put("Password", Password);
                params.put("Tipo", Tipo);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(Register.this);
        requestQueue.add(request);
    }

    private boolean ValidarUser(String User){
        boolean val=false;
       String urll="http://coviapp.atwebpages.com/project/VerificarUser.php";
       StringRequest request= new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               if(!response.contains("Username ya registrado")){
                   validUser=true;
               }
               else{
                   Toast.makeText(getApplicationContext(), "Ingrese otro nombre de usuario que no exista ya", Toast.LENGTH_SHORT).show();
               }
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
           }
       }){
           @Nullable
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               Map<String, String> params= new HashMap<>();
               params.put("User", User);
               return params;
           }
       };
        RequestQueue requestQueue= Volley.newRequestQueue(Register.this);
        requestQueue.add(request);
        val=validUser;
        return val;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private boolean ValidarPass(String pass){
        boolean validacion=false;
        boolean val_simbolo=false;
        long numchar= pass.chars().count();
        byte[] letras= pass.getBytes(StandardCharsets.US_ASCII);
        if(numchar>8){
            if(pass.contains("1") || pass.contains("2") || pass.contains("3") || pass.contains("4") || pass.contains("5") || pass.contains("6") || pass.contains("7") || pass.contains("8") || pass.contains("9") || pass.contains("0") ){
                for(int i=0; i<letras.length; i++){
                    if(letras[i]>=33 && letras[i]<=47 || letras[i]>=58 && letras[i]<=64 || letras[i]>=91 && letras[i]<=96 || letras[i]>=123 && letras[i]<=126){
                        val_simbolo=true;
                        i=letras.length;
                    }
                }
                if(val_simbolo==true) {
                    validacion = true;
                }
            }
        }
        else {
            Toast.makeText(getApplicationContext(),"La contraseña debe contener al menos 8 Caracteres, un numero, un simbolo y mayusculas y minisculas", Toast.LENGTH_SHORT).show();
        }
        return validacion;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void custionario(View view) {
        if(user.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Rellene el Campo de Usuario porfavor", Toast.LENGTH_SHORT).show();
        }
        if(pass.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Rellene el Campo de Contraseña porfavor", Toast.LENGTH_SHORT).show();
        }
        if(passconf.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Confirme su contraseña porfavor", Toast.LENGTH_SHORT).show();
        }
        else {
            tuser= user.getText().toString().trim();
            tpass= pass.getText().toString().trim();
            tpassconf= passconf.getText().toString().trim();
            Toast.makeText(getApplicationContext(),tpass +" "+ tpassconf, Toast.LENGTH_SHORT).show();
            if(tpass.equals(tpassconf)) {
                if((ValidarPass(tpass)) && ValidarUser(tuser)){
                    if(r1.isChecked()){
                        tipo="1";
                        Toast.makeText(getApplicationContext(),tipo.toString(), Toast.LENGTH_SHORT).show();
                        GuardarUsuario(tuser, tpass, tipo);
                        Intent reg = new Intent(this, Cuestionariouno.class);
                        reg.putExtra("user", tuser);
                        reg.putExtra("pass", tpass);
                        reg.putExtra("Covidstatus", tipo);
                        reg.putExtra("fecha", fecha1);
                        startActivity(reg);
                    }
                    if(r2.isChecked()){
                        tipo="0";
                        Toast.makeText(getApplicationContext(),tipo.toString(), Toast.LENGTH_SHORT).show();
                        GuardarUsuario(tuser, tpass, tipo);
                        Intent reg = new Intent(this, Cuestionariouno.class);
                        reg.putExtra("user", tuser);
                        reg.putExtra("pass", tpass);
                        reg.putExtra("Covidstatus", false);
                        reg.putExtra("fecha", fecha2);
                        startActivity(reg);
                    }
                    if(!r1.isChecked() && !r2.isChecked()) {
                        Toast.makeText(getApplicationContext(),"Complete el registro porfavor", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else{
                Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }

        }

    }


}