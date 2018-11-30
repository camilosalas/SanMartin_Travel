package com.camilo.sanmartin_travel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.camilo.sanmartin_travel.Models.ListaAventuras;
import com.camilo.sanmartin_travel.R;

import java.util.List;

public class AdapterAventura extends ArrayAdapter {


    private Context context;
    private List<ListaAventuras> listaaventur;



    public AdapterAventura(Context context, List<ListaAventuras> listaaventura) {
        super(context, R.layout.row_adapter_aventura, listaaventura);
        this.context = context;
        this.listaaventur = listaaventura;
    }


    @NonNull
    public View getView(final int position, final View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater =  LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.row_adapter_aventura, null);



        TextView nombre = item.findViewById(R.id.listaNombreAventura);
        nombre.setText(listaaventur.get(position).getNombreAventura());

        TextView descripcion = item.findViewById(R.id.listadescripcionAventura);
        descripcion.setText(listaaventur.get(position).getDescripcion());

        return item;
    }
}
