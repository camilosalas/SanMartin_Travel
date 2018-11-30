package com.camilo.sanmartin_travel.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.camilo.sanmartin_travel.Models.ListaPaisaje;
import com.camilo.sanmartin_travel.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AdapterPaisaje extends ArrayAdapter {

    private Context context;
    private List<ListaPaisaje> data;



    TextView nombre, telefono;

    public AdapterPaisaje(Context context, List<ListaPaisaje> data) {
        super(context, R.layout.row_adapter_paisaje, data);
        this.context = context;
        this.data = data;
    }

    @NonNull
    public View getView(final int position, final View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        final View item = inflater.inflate(R.layout.row_adapter_paisaje, null);


        nombre = item.findViewById(R.id.listaNombrePaisaje);
        telefono = item.findViewById(R.id.listaUbicacionPaisaje);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://san-martin-travel.appspot.com").child(data.get(position).getImgPaisaje());

        try {
            final File localFile = File.createTempFile("images","jpg");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    ImageView imgListaPaisaje = item.findViewById(R.id.imgListaPaisaje);
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    imgListaPaisaje.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }catch (IOException e){}

        nombre.setText(data.get(position).getNombrePaisaje());
        telefono.setText(data.get(position).getSitioUbicado());

        return item;

    }
}
