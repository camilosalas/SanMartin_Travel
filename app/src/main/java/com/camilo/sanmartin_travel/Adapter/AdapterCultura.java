package com.camilo.sanmartin_travel.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.camilo.sanmartin_travel.Models.ListaCulturas;
import com.camilo.sanmartin_travel.R;

import java.util.List;


public class AdapterCultura extends ArrayAdapter {
    private Context context;
    private List<ListaCulturas> listacultura;

    public AdapterCultura(Context context,List<ListaCulturas>listacultura){
        super(context, R.layout.row_adapter_cultura, listacultura);
        this.context = context;
        this.listacultura = listacultura;

    }
    @NonNull
    public View getView(final int position, final View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater =  LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.row_adapter_cultura, null);



        TextView nombre = item.findViewById(R.id.listaNombreCultura);
        nombre.setText(listacultura.get(position).getNombreCultura());

        TextView descripcion = item.findViewById(R.id.listadescripcionCultura);
        descripcion.setText(listacultura.get(position).getDescripcion());

        return item;
    }
}
