package com.camilo.sanmartin_travel.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.camilo.sanmartin_travel.Models.ListaCulturas;
import com.camilo.sanmartin_travel.Models.ListaNatural;
import com.camilo.sanmartin_travel.R;

import java.util.List;

public class AdapterNatural extends ArrayAdapter {

    private Context context;
    private List<ListaNatural> listanatural;

    public AdapterNatural(Context context,List<ListaNatural>listanatural){
        super(context, R.layout.row_adapter_natural, listanatural);
        this.context = context;
        this.listanatural = listanatural;

    }
    @NonNull
    public View getView(final int position, final View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater =  LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.row_adapter_natural, null);



        TextView nombre = item.findViewById(R.id.listaNombreNatural);
        nombre.setText(listanatural.get(position).getNombreNatural());

        TextView descripcion = item.findViewById(R.id.listadescripcionNatural);
        descripcion.setText(listanatural.get(position).getDescripcion());

        return item;
    }
}
