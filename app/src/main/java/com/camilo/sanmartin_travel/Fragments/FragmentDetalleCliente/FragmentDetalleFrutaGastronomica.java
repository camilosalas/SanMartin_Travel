package com.camilo.sanmartin_travel.Fragments.FragmentDetalleCliente;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.camilo.sanmartin_travel.Models.ListaFrutaGastronomica;
import com.camilo.sanmartin_travel.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;


public class FragmentDetalleFrutaGastronomica extends Fragment {

    View vista;

    TextView nombreFruta, descripcionFruta;
    ImageView imgFruta;


    ListaFrutaGastronomica listaFrutaGastronomica;


    @SuppressLint("ValidFragment")
    public FragmentDetalleFrutaGastronomica(ListaFrutaGastronomica listaFrutaGastronomica) {
        this.listaFrutaGastronomica = listaFrutaGastronomica;
    }

    public FragmentDetalleFrutaGastronomica() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_detalle_fruta_gastronomica, container, false);

        nombreFruta = vista.findViewById(R.id.NombreFru);
        descripcionFruta = vista.findViewById(R.id.DescripcionFru);
        imgFruta = vista.findViewById(R.id.imageFruta);


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://san-martin-travel.appspot.com").child(listaFrutaGastronomica.getImg());

        try {
            final File localFile = File.createTempFile("images","jpg");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    imgFruta.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }catch (IOException e){}


        nombreFruta.setText(listaFrutaGastronomica.getNombreFruta());
        descripcionFruta.setText(listaFrutaGastronomica.getDescripcion());


        return vista;
    }


}
