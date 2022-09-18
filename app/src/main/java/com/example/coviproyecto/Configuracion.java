package com.example.coviproyecto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Configuracion extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText txt;
    private Button guardar1, cancelar1, guardar2, cancelar2;
    String ID, url="http://coviapp.atwebpages.com/project/Config.php", url1= "http://coviapp.atwebpages.com/project/ObtenerUserPass.php";
    String user, pass;
    TextView txtuser, txtpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        CargarPreferencias();

        txtuser= findViewById(R.id.ctxt_user);
        txtpass= findViewById(R.id.ctxt_pass);

        MostrarDatos("1");
        MostrarDatos("2");

    }

    private void CargarPreferencias(){
        SharedPreferences preferences= getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        ID= preferences.getString("ID", "NA");
    }


    private void MostrarDatos(String opcion){

        StringRequest request= new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("Error")){
                    Toast.makeText(getApplicationContext(), "Algo salio mal", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                    if(opcion=="1"){
                        txtuser.setText(response.toString());
                    }
                    if(opcion=="2"){
                        txtpass.setText(response.toString());
                    }
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
                params.put("ID", ID);
                params.put("opcion", opcion);
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(Configuracion.this);
        requestQueue.add(request);
    }


    private void GuardarCambios(String input, int opcion){
        user= txtuser.getText().toString();
        pass= txtpass.getText().toString();
        //Toast.makeText(getApplicationContext(), ID+" "+input+" "+pass+" "+user+" "+opcion, Toast.LENGTH_SHORT).show();

        StringRequest request1= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                
                if (response.equalsIgnoreCase("Nombre de usuario modifcado")) {
                    Toast.makeText(getApplicationContext(), "Se guardaron los cambios", Toast.LENGTH_SHORT).show();
                }
                if (response.equalsIgnoreCase("Contraseña modificada")) {
                    Toast.makeText(getApplicationContext(), "Se guardaron los cambios", Toast.LENGTH_SHORT).show();
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
                params.put("ID", ID);
                if (opcion==1){
                    params.put("User", input);
                    params.put("Password", pass);
                }else {
                    params.put("Password", input);
                    params.put("User", user);
                }
                params.put("t",String.valueOf(opcion));

                return params;
            }
        };

        RequestQueue requestQueue1= Volley.newRequestQueue(Configuracion.this);
        requestQueue1.add(request1);
    }

    public  void createNewContactDialog(){
        dialogBuilder= new AlertDialog.Builder(this);
        final View contactPopupView= getLayoutInflater().inflate(R.layout.popupuser, null);

        txt= (EditText)contactPopupView.findViewById(R.id.edit_user);
        guardar1= (Button) contactPopupView.findViewById(R.id.button_save1);
        cancelar1= (Button) contactPopupView.findViewById(R.id.button_cancel1);

        dialogBuilder.setView(contactPopupView);
        dialog= dialogBuilder.create();
        dialog.show();

        guardar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuardarCambios(txt.getText().toString(), 1);
            }
        });

        cancelar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    public  void createNewContactDialog2(){
        dialogBuilder= new AlertDialog.Builder(this);
        final View contactPopupView= getLayoutInflater().inflate(R.layout.popuppass, null);

        txt= (EditText)contactPopupView.findViewById(R.id.edit_pass);
        guardar2= (Button) contactPopupView.findViewById(R.id.button_save2);
        cancelar2= (Button) contactPopupView.findViewById(R.id.button_cancel2);

        dialogBuilder.setView(contactPopupView);
        dialog= dialogBuilder.create();
        dialog.show();

        guardar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuardarCambios(txt.getText().toString(), 2);
            }
        });

        cancelar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }


    public void EditUser(View view) {

        createNewContactDialog();
    }

    public void Editpass(View view) {

        createNewContactDialog2();
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