package com.camilo.sanmartin_travel.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.camilo.sanmartin_travel.Models.ListaComidaGastronomica;
import com.camilo.sanmartin_travel.Models.ListaFrutaGastronomica;
import com.camilo.sanmartin_travel.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AdapterFrutaGastronomica extends ArrayAdapter {

    private Context context;
    private List<ListaFrutaGastronomica> data;

    public AdapterFrutaGastronomica(Context context, List<ListaFrutaGastronomica> data) {
        super(context, R.layout.row_adapter_comida, data);
        this.context = context;
        this.data = data;
    }


    @NonNull
    public View getView(final int position, final View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater =  LayoutInflater.from(context);
        final View item = inflater.inflate(R.layout.row_adapter_fruta, null);


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://san-martin-travel.appspot.com").child(data.get(position).getImg());

        try {
            final File localFile = File.createTempFile("images","jpg");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    ImageView imgListaFruta = item.findViewById(R.id.imgListaFruta);

                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    imgListaFruta.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }catch (IOException e){}


        TextView nombre = item.findViewById(R.id.listaNombreFruta);
        nombre.setText(data.get(position).getNombreFruta());


        String descripcionsitio=data.get(position).getDescripcion();
        if(descripcionsitio.length()>2){
            descripcionsitio = descripcionsitio.substring(0,4)+"...";
        }else{
            descripcionsitio = descripcionsitio;
        }

        TextView descripcion = item.findViewById(R.id.listadescripcionFruta);
        descripcion.setText(descripcionsitio);

        return item;
    }
}
