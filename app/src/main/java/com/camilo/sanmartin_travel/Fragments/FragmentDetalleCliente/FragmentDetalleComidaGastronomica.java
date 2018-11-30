package com.camilo.sanmartin_travel.Fragments.FragmentDetalleCliente;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class FragmentDetalleComidaGastronomica extends Fragment {

    View vista;

    TextView nombreComi, descripcionComi;
    ImageView imageComida;


    ListaComidaGastronomica listaComidaGastronomica;

    @SuppressLint("ValidFragment")
    public FragmentDetalleComidaGastronomica(ListaComidaGastronomica listaComidaGastronomica) {
        this.listaComidaGastronomica = listaComidaGastronomica;
    }


    public FragmentDetalleComidaGastronomica(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_detalle_comida_gastronomica, container, false);

        nombreComi = vista.findViewById(R.id.NombreComi);
        descripcionComi = vista.findViewById(R.id.DescripcionComi);
        imageComida = vista.findViewById(R.id.imageComida);


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://san-martin-travel.appspot.com").child(listaComidaGastronomica.getImg());

        try {
            final File localFile = File.createTempFile("images","jpg");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    imageComida.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }catch (IOException e){}


        nombreComi.setText(listaComidaGastronomica.getNombreGastronomia());
        descripcionComi.setText(listaComidaGastronomica.getDescripcion());

        return vista;
    }

}
