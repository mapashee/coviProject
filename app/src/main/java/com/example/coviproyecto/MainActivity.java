package com.example.coviproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //CargarPreferencias();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String id= preferences.getString("ID", "NA");
        if(!(id.equals("NA"))){
            Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_SHORT).show();
            Intent prin = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                prin = new Intent(this, Sintomasreg.class);
            }
            startActivity(prin);
        }
    }

    /*private void CargarPreferencias() {
        String id= preferences.getString("ID", "NA");
        if(!(id.equals("NA"))){
            Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_SHORT).show();
            Intent prin = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                prin = new Intent(this, Principal.class);
            }
            startActivity(prin);
        }
    }*/

    public void Login(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

    public void Register(View view) {
        Intent reg = new Intent(this, Register.class);
        startActivity(reg);
    }


    public void Paginaweb(View view) {
        Uri link= Uri.parse(url);
        Intent i= new Intent(Intent.ACTION_VIEW, link);
        startActivity(i);
    }

    public void Prueba(View view) {
        Intent reg = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            reg = new Intent(this, Principal.class);
        }
        startActivity(reg);
    }
}