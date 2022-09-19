package com.example.coviproyecto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adaptersinr extends RecyclerView.Adapter<Adaptersinr.ViewHolder> {
    View view;
    Context context;

    @NonNull
    @Override
    public Adaptersinr.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.sintomasreglist,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final int pos=position;
        viewHolder.nombre.setText(Info.sintomasList.get(pos).getNombre());
    }

    @Override
    public int getItemCount() {
        return Info.sintomasList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre= itemView.findViewById(R.id.rv_txtnsintoma);
        }
    }
}
