package com.example.coviproyecto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText et1, et2;
    String txtuser, txtpass, txtid;
    String url="http://coviapp.atwebpages.com/project/Login.php";
    Button btnLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et1= findViewById(R.id.txtuser);
        et2= findViewById(R.id.txtpass);
        btnLog= findViewById(R.id.buttonLog);

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

    public void Ingreso(View view) {

        if(et1.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Rellene el Campo de Usuario porfavor", Toast.LENGTH_SHORT).show();
        }
        else if(et2.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Rellene el Campo de Contraseña porfavor", Toast.LENGTH_SHORT).show();
        }
        else{
            txtuser= et1.getText().toString().trim();
            txtpass= et2.getText().toString().trim();
            StringRequest request= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.startsWith("Ingreso correctamente")) {
                        et1.setText("");
                        et2.setText("");
                        String[] particion= response.split("\\s+");
                        String id= particion[2].trim();
                        int tipo= Integer.valueOf(particion[3].trim());
                        GuardarPreferencias(id, tipo);
                        Toast.makeText(getApplicationContext(),id, Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            startActivity(new Intent(getApplicationContext(), Principal.class));
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
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
                    params.put("User", txtuser);
                    params.put("Password", txtpass);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
            requestQueue.add(request);
        }
    }


    public void Registro(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startActivity(new Intent(getApplicationContext(), Principal.class));
        }
    }
}