package com.example.coviproyecto;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Cuestionariouno extends AppCompatActivity implements View.OnClickListener {

    //SharedPreferences preferences;
    //String ID = preferences.getString("ID", "NA");
    //String ID="8";
    int sexo, vacuna, vacunar, variante;
    String user, pass, url= "http://coviapp.atwebpages.com/project/CompletarReg.php", Covidstatus;
    private int dia, mes, anio;
    TextView txtpeso,txtedad, txtndosis, setDate;
    RadioButton rr1, rr2;

    //Date selector
    Button guardar;
    ImageButton btnfecha1;
    TextView txtFecha1;
    //Spinners
    Spinner opcionsexo, opvacuna1, opvacuna2, opvariante;
    TextView selecsexo, selecvacu1, selecvacu2, selecvariante;
    //Listas multiseleccion
    TextView tvenfermedad, tvdiscapacidad;
    boolean[] selectedEnfermedad, selectedDiscapacidad;
    ArrayList<Integer> enfermedadList = new ArrayList<>();
    ArrayList<Integer> discapacidadList = new ArrayList<>();
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
    String[] Discapacidadarray = {
            "Ninguna",
            "Física-motora",
            "Intelectual",
            "Psiquica"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestionariouno);

        //preferences = getSharedPreferences("Preferencias", MODE_PRIVATE);

        //Spinners
        opcionsexo= findViewById(R.id.spin_sexo);
        selecsexo= findViewById(R.id.txtspin_s);
        opvacuna1=findViewById(R.id.spin_vacu1);
        opvacuna2=findViewById(R.id.spin_vacu2);
        selecvacu1= findViewById(R.id.txtspin_v);
        selecvacu2=findViewById(R.id.txtspin_vr);
        opvariante=findViewById(R.id.spin_var);
        selecvariante=findViewById(R.id.txtspin_var);
        //Listas multiselect
        tvenfermedad = findViewById(R.id.txt_enfermedades);
        tvdiscapacidad= findViewById(R.id.txt_discapacidad);
        guardar= findViewById(R.id.button_guardar);
        //Date selector
        setDate= (TextView) findViewById(R.id.reg_p7);
        txtFecha1= (TextView) findViewById(R.id.txt_fecha1);
        btnfecha1= findViewById(R.id.btn_fecha1);
        //Otros datos
        txtpeso= findViewById(R.id.txt_peso);
        txtedad=findViewById(R.id.txt_edad);
        txtndosis=findViewById(R.id.txt_ndosis);
        rr1= (RadioButton)findViewById(R.id.rr_1);
        rr2= (RadioButton)findViewById(R.id.rr_2);

        selectedEnfermedad= new boolean[Enfermedadarray.length];
        selectedDiscapacidad = new boolean[Discapacidadarray.length];

        //Obteniendo texto para la fecha dependiendo del tipo de usuario
        String fechas= getIntent().getStringExtra("fecha");
        setDate.setText(fechas);

        //Metodos para los edittext de las fechas

        btnfecha1.setOnClickListener(this);

        /*Adaptador para el spinner de sexo */
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource
                (this,R.array.opsexo, android.R.layout.simple_spinner_item);
        opcionsexo.setAdapter(adapter);
        opcionsexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int posicion, long id) {
                selecsexo.setText(parent.getItemAtPosition(posicion).toString());
                sexo=posicion+1;
                Toast.makeText(getApplicationContext(),String.valueOf(posicion+1), Toast.LENGTH_SHORT).show();
                //mes_s =parent.getItemAtPosition(posicion).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        /*Adaptador para el spinner de Vacunacion */
        ArrayAdapter<CharSequence> adap1=ArrayAdapter.createFromResource
                (this,R.array.opvacunacion, android.R.layout.simple_spinner_item);
        opvacuna1.setAdapter(adap1);
        opvacuna1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int posicion, long id) {
                selecvacu1.setText(parent.getItemAtPosition(posicion).toString());
                vacuna=posicion+1;
                //mes_s =parent.getItemAtPosition(posicion).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*Adaptador para el spinner de Vacunacion */
        ArrayAdapter<CharSequence> adap2=ArrayAdapter.createFromResource
                (this,R.array.opvacunacion, android.R.layout.simple_spinner_item);
        opvacuna2.setAdapter(adap2);
        opvacuna2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int posicion, long id) {
                selecvacu2.setText(parent.getItemAtPosition(posicion).toString());
                vacunar=posicion+1;
                //mes_s =parent.getItemAtPosition(posicion).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*Adaptador para el spinner de Variante */
        ArrayAdapter<CharSequence> adap3=ArrayAdapter.createFromResource
                (this,R.array.opvariante, android.R.layout.simple_spinner_item);
        opvariante.setAdapter(adap3);
        opvariante.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int posicion, long id) {
                selecvariante.setText(parent.getItemAtPosition(posicion).toString());
                variante=posicion+1;
                //mes_s =parent.getItemAtPosition(posicion).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Guardar();
                }
            }
        });

        MultiseleccionEnfermedades();
        MultiseleccionDiscapacidad();

    }

    private String CargarPreferencias(){
        SharedPreferences preferences= getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String ID= preferences.getString("ID", "NA");
        return ID;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void Guardar(){
        user= getIntent().getStringExtra("user");
        pass= getIntent().getStringExtra("pass");
        Covidstatus= getIntent().getStringExtra("Covidstatus");
        Boolean mov;
        String indexms1="", indexms2="";
        //String.valueOf(posicion)

         String IDsexo=String.valueOf(sexo), IDvacuna=String.valueOf(vacuna), IDvacunar=String.valueOf(vacunar), IDvariante=String.valueOf(variante),
                 Peso=txtpeso.getText().toString(), Edad=txtedad.getText().toString(), Numdosis=txtndosis.getText().toString(), fecha1=txtFecha1.getText().toString(), Movilidad="";
        if(rr1.isChecked()){
            Movilidad= "1";

        }
        if(rr2.isChecked()){
            Movilidad= "0";
        }

        if(IDsexo.isEmpty() || IDvacuna.isEmpty() || IDvacunar.isEmpty() || IDvariante.isEmpty() || Peso.isEmpty() || Edad.isEmpty() || Numdosis.isEmpty()
        || fecha1.isEmpty() || Movilidad.isEmpty()){
            Toast.makeText(getApplicationContext(),"Rellene todos los campos porfavor", Toast.LENGTH_SHORT).show();
        }
        else{
            String ID=CargarPreferencias();
            for (int a=0; a<enfermedadList.size(); a++){
                indexms1=enfermedadList.get(a).toString();
                //Toast.makeText(getApplicationContext(),"pos"+a+":"+indexms1, Toast.LENGTH_SHORT).show();
                int in= Integer.parseInt(indexms1)+1;
                Toast.makeText(getApplicationContext(), String.valueOf(in), Toast.LENGTH_SHORT).show();
                InsertMultiselectEn(String.valueOf(in), ID);
            }
            for (int b=0; b<enfermedadList.size(); b++){
                indexms2=discapacidadList.get(b).toString();
                int ini= Integer.parseInt(indexms2)+1;
                InsertMultiselectDis(String.valueOf(ini), ID);
            }

            GuardarCuestionario(ID, IDsexo, IDvacuna, IDvacunar, IDvariante, Peso, Edad, Numdosis, fecha1, Movilidad);
        }

        Intent principal = new Intent(this, Sintomasreg.class);
        startActivity(principal);
    }


    private void GuardarCuestionario(String ID,String IDsexo, String IDvacuna, String IDvacunar, String IDvariante, String Peso, String Edad, String Numdosis, String TiempoCovid, String Movilidad){

        Toast.makeText(getApplicationContext(), ID+" "+IDsexo+" "+IDvacuna+" "+IDvacunar+" "+IDvariante+" "+Peso+" "+Edad+" "+Numdosis+" "+TiempoCovid+" "+Movilidad, Toast.LENGTH_SHORT).show();

        StringRequest request= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.startsWith("registro completado")){
                    Toast.makeText(getApplicationContext(), "Felicidades ya eres parte de nuestra comunidad", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Algo salio mal", Toast.LENGTH_SHORT).show();
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
                params.put("IDsexo", IDsexo);
                params.put("IDvacuna", IDvacuna);
                params.put("IDvacunar", IDvacunar);
                params.put("IDvariante", IDvariante);
                params.put("Peso", Peso);
                params.put("Edad", Edad);
                params.put("Numdosis", Numdosis);
                params.put("TiempoCovid", TiempoCovid);
                params.put("Movilidad", Movilidad);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(Cuestionariouno.this);
        requestQueue.add(request);
    }

    private void InsertMultiselectEn(String index, String ID){

        String urll="http://coviapp.atwebpages.com/project/InsertarEnfermedad.php";

        StringRequest reques2= new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("registro guardado")) {
                    Toast.makeText(getApplicationContext(), "Historial guardado", Toast.LENGTH_SHORT).show();
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

                params.put("IDuser", ID);
                params.put("IDenfermedad", index);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(Cuestionariouno.this);
        requestQueue.add(reques2);

    }

    private void InsertMultiselectDis(String index, String ID){
        String urll= "http://coviapp.atwebpages.com/project/InsertarDiscapacidad.php";

        StringRequest request3= new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("registro guardado")) {
                    Toast.makeText(getApplicationContext(), "Historial guardado", Toast.LENGTH_SHORT).show();
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

                params.put("IDuser", ID);
                params.put("IDdiscapacidad", index);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(Cuestionariouno.this);
        requestQueue.add(request3);

    }

    private void MultiseleccionEnfermedades(){
        tvenfermedad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Initialize alert dailog
                AlertDialog.Builder builder= new AlertDialog.Builder(
                        Cuestionariouno.this
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
                            //Toast.makeText(getApplicationContext(),enfermedadList.get(j).toString(), Toast.LENGTH_SHORT).show();

                            stringBuilder.append(Enfermedadarray[enfermedadList.get(j)]);
                            //Check condition
                            if(j !=enfermedadList.size()-1){
                                //When j value not equal to enfermedad list size -1
                                stringBuilder.append(", ");
                            }
                        }
                        //Set text on text view
                        tvenfermedad.setText(stringBuilder.toString());

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

    private void MultiseleccionDiscapacidad(){
        tvdiscapacidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Initialize alert dailog
                AlertDialog.Builder builder= new AlertDialog.Builder(
                        Cuestionariouno.this
                );
                //Set title
                builder.setTitle("Seleccione una opcion");
                //Set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(Discapacidadarray, selectedDiscapacidad, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        //Check condition
                        if(b){
                            //When checkbox selected
                            //Add position in enfermedad list
                            discapacidadList.add(i);
                            //Sort enfermedad List
                            Collections.sort(discapacidadList);
                        } else {
                            //When checkbox unselected
                            //Remove position
                            discapacidadList.remove(i);
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Initialize string builder
                        StringBuilder stringBuilder= new StringBuilder();
                        //Use for loop
                        for(int j=0; j<discapacidadList.size(); j++){
                            //Concat array value
                            stringBuilder.append(Discapacidadarray[discapacidadList.get(j)]);
                            //Check condition
                            if(j !=discapacidadList.size()-1){
                                //When j value not equal to enfermedad list size -1
                                stringBuilder.append(", ");
                            }
                        }
                        //Set text on text view
                        tvdiscapacidad.setText(stringBuilder.toString());
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
                        for(int j=0; j<selectedDiscapacidad.length; j++){
                            //remove all selection
                            selectedDiscapacidad[j]= false;
                            //Clear enfermedad list
                            discapacidadList.clear();
                            //Clear text view value
                            tvdiscapacidad.setText("");
                        }
                    }
                });
                //Show dialog
                builder.show();
            }
        });
    }

    private void SelectorFecha(TextView txtf){
        final Calendar c= Calendar.getInstance();
        dia= c.get(Calendar.DAY_OF_MONTH);
        mes= c.get(Calendar.MONTH);
        anio= c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                txtf.setText(year+"/"+(month+1)+"/"+day);
            }
        }, dia, mes, anio);
        datePickerDialog.show();
    }

    @Override
    public void onClick(View view) {
        if(view== btnfecha1){
            SelectorFecha(txtFecha1);
        }
    }
}