package com.example.coviproyecto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Multiselect1 extends AppCompatActivity {

    TextView tvenfermedad;
    boolean[] selectedEnfermedad;
    ArrayList<Integer> enfermedadList = new ArrayList<>();
    String[] Enfermedadarray = {
            "Ninguna",
            "Alzheimer o Demecia",
            "Artritis",
            "Asma",
            "Cancer",
            "EPOC",
            "Enfermedad de Crohm",
            "Fibrosis quistica",
            "Diabtes",
            "Epilepsia",
            "Enfermedades del corazon",
            "VIH/sida",
            "Transtorno de humor",
            "Esclerosis multiple",
            "Parkinson"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiselect1);

        //Assign variable
        tvenfermedad = findViewById(R.id.tv_enfermedad);

        //Initialize selected day array
        selectedEnfermedad= new boolean[Enfermedadarray.length];

        tvenfermedad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Initialize alert dailog
                AlertDialog.Builder builder= new AlertDialog.Builder(
                        Multiselect1.this
                );
                //Set title
                builder.setTitle("Seleccione una opcion");
                //Set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(Enfermedadarray, selectedEnfermedad, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        //Check condition
                        if(b){
                            //When checkbox selected
                            //Add position in enfermedad list
                            enfermedadList.add(i);
                            //Sort enfermedad List
                            Collections.sort(enfermedadList);
                        } else {
                            //When checkbox unselected
                            //Remove position
                            enfermedadList.remove(i);
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Initialize string builder
                        StringBuilder stringBuilder= new StringBuilder();
                        //Use for loop
                        for(int j=0; j<enfermedadList.size(); j++){
                            //Concat array value
                            stringBuilder.append(Enfermedadarray[enfermedadList.get(j)]);
                            //Check condition
                            if(j !=enfermedadList.size()-1){
                                //When j value not equal to enfermedad list size -1
                                stringBuilder.append(", ");
                            }
                        }
                        //Set text on text view
                        tvenfermedad.setText(stringBuilder.toString());
                        Toast.makeText(getApplicationContext(),stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Eliminar seleccion", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j=0; j<selectedEnfermedad.length; j++){
                            //remove all selection
                            selectedEnfermedad[j]= false;
                            //Clear enfermedad list
                            enfermedadList.clear();
                            //Clear text view value
                            tvenfermedad.setText("");
                        }
                    }
                });
                //Show dialog
                builder.show();
            }
        });
    }
}