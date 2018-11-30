package com.camilo.sanmartin_travel.Fragments.Fragment_Administrador;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.camilo.sanmartin_travel.Models.NaturalExperiencias;
import com.camilo.sanmartin_travel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class FragmentAgregarNatural extends Fragment {

    View vista;
    EditText nombre, descripcion;
    ImageView imgNatu;
    FloatingActionButton btnflotante;
    CardView agregar;
    //private ProgressDialog mProgressDialog = new ProgressDialog(this);

    //private static final int GALLERY_INTENT = 1;

    private final int SELECT_PICTURE = 200;

    String TEMPORAL_PICTURE_NAME;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    Uri path;

    public FragmentAgregarNatural(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        vista =  inflater.inflate(R.layout.fragment_agregar_natural, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        agregar = vista.findViewById(R.id.AgregaNatu);
        nombre = vista.findViewById(R.id.NombreNatu);
        descripcion = vista.findViewById(R.id.DescripcionNatu);
        imgNatu = vista.findViewById(R.id.imgNatu);
        btnflotante = vista.findViewById(R.id.addImgNatu);



        btnflotante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);*/

                @SuppressLint("IntentReset") Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Seleccionar Aplicación"), SELECT_PICTURE);
            }
        });


        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregar.setEnabled(false);

                if (!nombre.getText().toString().equals("")&& !descripcion.getText().toString().equals("")&& !TEMPORAL_PICTURE_NAME.equals("")){

                    StorageReference filePath = storageReference.child(nombre.getText().toString() +"/"+TEMPORAL_PICTURE_NAME);
                    filePath.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //Aquí va lo que deseas que se muestre cuando ya se agrega la imagen a firebase.
                        }
                    });

                    NaturalExperiencias naturalExperiencias = new NaturalExperiencias(nombre
                            .getText()
                            .toString(),descripcion
                            .getText().toString(),TEMPORAL_PICTURE_NAME);
                    databaseReference.child("ExperienciasNatural").push().setValue
                            (naturalExperiencias).addOnCompleteListener(new OnCompleteListener<Void>
                            () {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                agregar.setEnabled(true);

                                Fragment nuevofragmet = new FragmentListaNatural();
                                assert getFragmentManager() != null;
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.principales, nuevofragmet);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                Toast.makeText(getActivity().getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "No se pudo registrar los campos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(getActivity().getApplicationContext(), "Por favor rellene el formulario", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return vista;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK){

            //mProgressDialog.setTitle("Subiendo");
            //mProgressDialog.setMessage("Subiendo foto");
            //mProgressDialog.setCancelable(false);
            //mProgressDialog.show();

            TEMPORAL_PICTURE_NAME = "Image"+ new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date()) +".jpg";
            path = data.getData();
            imgNatu.setVisibility(View.VISIBLE);
            imgNatu.setImageURI(path);
            //mProgressDialog.dismiss();

        }
    }
}
