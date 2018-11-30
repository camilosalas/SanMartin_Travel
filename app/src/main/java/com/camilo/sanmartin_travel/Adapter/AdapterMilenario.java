package com.camilo.sanmartin_travel.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.camilo.sanmartin_travel.Models.ListaMilenario;
import com.camilo.sanmartin_travel.R;

import java.util.List;

public class AdapterMilenario extends ArrayAdapter {
    private Context context;
    private List<ListaMilenario> listamilenario;

    public AdapterMilenario(Context context,List<ListaMilenario>listamilenario){
        super(context, R.layout.row_adapter_milenario, listamilenario);
        this.context = context;
        this.listamilenario = listamilenario;

    }
    @NonNull
    public View getView(final int position, final View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater =  LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.row_adapter_milenario, null);



        TextView nombre = item.findViewById(R.id.listaNombreMilenario);
        nombre.setText(listamilenario.get(position).getNombreMilenario());

        TextView descripcion = item.findViewById(R.id.listadescripcionMilenario);
        descripcion.setText(listamilenario.get(position).getDescripcion());

        return item;
    }
}
