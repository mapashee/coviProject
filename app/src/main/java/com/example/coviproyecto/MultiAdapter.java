package com.example.coviproyecto;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.IDNA;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MultiAdapter extends RecyclerView.Adapter<MultiAdapter.MultiViewHolder> {

    View view;
    Context context;
    ArrayList<String> arrayList;
    SintomasListener sintomasListener;


    public MultiAdapter(Context context, ArrayList<String> arrayList, SintomasListener sintomasListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.sintomasListener = sintomasListener;
    }


    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.recicler_sintomasview,parent, false);
        return new MultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder holder, int pos) {
        if(arrayList != null && arrayList.size() >0){
            holder.checkBox.setText(arrayList.get(pos));
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(holder.checkBox.isChecked()){
                        Info.arrayList_1.add(arrayList.get(holder.getAdapterPosition()));
                        long id= holder.getAdapterPosition();
                        InsertSintoma(String.valueOf(id+1), "1");
                    }
                    else {
                        Info.arrayList_1.remove(arrayList.get(holder.getAdapterPosition()));
                        long id= holder.getAdapterPosition();
                        InsertSintoma(String.valueOf(id+1), "2");
                    }
                    sintomasListener.onSintomasChange(Info.arrayList_1);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MultiViewHolder extends RecyclerView.ViewHolder{
        private CheckBox checkBox;
        private EditText editText;

        public MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox= itemView.findViewById(R.id.cradio_1);
            editText= itemView.findViewById(R.id.txt_sinextra);
        }
    }

    private String CargarPreferencias(){
        SharedPreferences preferences= context.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String ID= preferences.getString("ID", "NA");
        return ID;
    }

    private void InsertSintoma(String index, String opcion){
        String ID= CargarPreferencias();

        String urll="http://coviapp.atwebpages.com/project/Regsintoma.php";

        StringRequest reques2= new StringRequest(Request.Method.POST, urll, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.startsWith("Sintoma R")) {
                    if(opcion=="1") {
                        Toast.makeText(context.getApplicationContext(), "Sintomas del día guardados", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context.getApplicationContext(), "Sintomas removido", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context.getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();

                params.put("IDuser", ID);
                params.put("IDsintoma", index);
                params.put("Opcion", opcion);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(reques2);

    }

}
