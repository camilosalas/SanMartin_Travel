package com.camilo.sanmartin_travel.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.camilo.sanmartin_travel.Models.ListaComidaGastronomica;
import com.camilo.sanmartin_travel.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class AdapterComidaGastronomica extends ArrayAdapter {

    private Context context;
    private List<ListaComidaGastronomica> data;

    public AdapterComidaGastronomica(Context context, List<ListaComidaGastronomica> data) {
        super(context, R.layout.row_adapter_comida, data);
        this.context = context;
        this.data = data;
    }


    @NonNull
    public View getView(final int position, final View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater =  LayoutInflater.from(context);
        final View item = inflater.inflate(R.layout.row_adapter_comida, null);


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://san-martin-travel.appspot.com").child(data.get(position).getImg());

        try {
            final File localFile = File.createTempFile("images","jpg");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    ImageView imgListaComida = item.findViewById(R.id.imgListaComida);
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    imgListaComida.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }catch (IOException e){}




        TextView nombre = item.findViewById(R.id.listaNombreComida);
        nombre.setText(data.get(position).getNombreGastronomia());


        String descripcioncomida=data.get(position).getDescripcion();
        if(descripcioncomida.length()>2){
            descripcioncomida = descripcioncomida.substring(0,4)+"...";
        }else{
            descripcioncomida = descripcioncomida;
        }

        TextView descripcion = item.findViewById(R.id.listadescripcionComida);
        descripcion.setText(descripcioncomida);

        return item;
    }
}
