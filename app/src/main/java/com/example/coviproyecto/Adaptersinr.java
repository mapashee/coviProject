package com.example.coviproyecto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adaptersinr extends RecyclerView.Adapter<Adaptersinr.ViewHolder> {

    Context context;

    @NonNull
    @Override
    public Adaptersinr.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Adaptersinr.ViewHolder(LayoutInflater.from(context).inflate(R.layout.sintomasreglist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.nombre.setText(Info.sintomasList.get(position));
    }

    @Override
    public int getItemCount() {
        if(Info.sintomasList != null){
            return Info.sintomasList.size();
        }
        else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre;
        public ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre= itemView.findViewById(R.id.rv_txtnsintoma);
            image= itemView.findViewById(R.id.iv_guardado);
        }
    }
}
