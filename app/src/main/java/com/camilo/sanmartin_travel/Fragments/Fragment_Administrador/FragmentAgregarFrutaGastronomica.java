package com.camilo.sanmartin_travel.Fragments.Fragment_Administrador;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

import com.camilo.sanmartin_travel.Models.ComidaGastronomia;
import com.camilo.sanmartin_travel.Models.FrutaGastronomica;
import com.camilo.sanmartin_travel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class FragmentAgregarFrutaGastronomica extends Fragment {

    View vista;

    EditText nombre, descripcion;
    ImageView imgFruta;
    FloatingActionButton btnflotante;
    CardView agregar;


    String APP_DIRECTORY = "San Martin Travel";
    String TEMPORAL_PICTURE_NAME;
    private final int PHOTO_CODE = 100;
    private final int SELECT_PICTURE = 200;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    Bitmap bitmap;
    Uri path;

    public FragmentAgregarFrutaGastronomica() {}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_agregar_fruta_gastronomica, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        nombre = vista.findViewById(R.id.NombreFruta);
        descripcion = vista.findViewById(R.id.DescripcionFruta);
        imgFruta = vista.findViewById(R.id.imgfruta);
        btnflotante = vista.findViewById(R.id.addImgfruta);
        agregar = vista.findViewById(R.id.AgregaFruta);


        btnflotante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final CharSequence[] Opciones = {"Tomar una Foto", "Seleccionar una Foto"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Elija una Opción");
                builder.setIcon(R.drawable.add_img);
                builder.setItems(Opciones, new DialogInterface.OnClickListener() {
                    @SuppressLint("IntentReset")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(Opciones[which] == "Tomar una Foto"){
                            //AbrirCamara();
                            Toast.makeText(getContext(), "Aún no disponible", Toast.LENGTH_SHORT).show();
                        } else if(Opciones[which] == "Seleccionar una Foto"){
                            @SuppressLint("IntentReset") Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, "Seleccionar Aplicación"), SELECT_PICTURE);
                        }
                    }
                });
                builder.show();
            }
        });


        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregar.setEnabled(false);

                if (!nombre.getText().toString().equals("")&& !descripcion.getText().toString().equals("")&& !TEMPORAL_PICTURE_NAME.equals("")){

                    StorageReference filePath = storageReference.child(nombre.getText().toString() +"/"+TEMPORAL_PICTURE_NAME);

                    if(bitmap != null){
                        //Bitmap bitmap = imageView.getDrawingCache();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] data = baos.toByteArray();

                        filePath.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                //Aquí va lo que deseas que se muestre cuando ya se agrega la imagen a firebase.
                            }
                        });
                    } else {
                        filePath.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                //Aquí va lo que deseas que se muestre cuando ya se agrega la imagen a firebase.
                            }
                        });
                    }

                    FrutaGastronomica fruta = new FrutaGastronomica(nombre.getText().toString(),descripcion.getText().toString(),nombre.getText().toString() + "/" + TEMPORAL_PICTURE_NAME);
                    databaseReference.child("FrutaGastronomia").push().setValue(fruta).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                agregar.setEnabled(true);

                                Fragment nuevofruta = new FragmentListaFrutaGastronomica();
                                assert getFragmentManager() != null;
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.principales, nuevofruta);
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

    @SuppressLint("SimpleDateFormat")
    private void AbrirCamara() {

        TEMPORAL_PICTURE_NAME = "Image"+ new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date()) +".jpg";

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), APP_DIRECTORY);
        file.mkdir();

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + APP_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;

        File newFile = new File(path);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
        startActivityForResult(intent, PHOTO_CODE);

    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PHOTO_CODE:
                if(resultCode == RESULT_OK){
                    String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + APP_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;
                    decodeBitmap(dir);
                }
                break;
            case SELECT_PICTURE:
                if(resultCode == RESULT_OK){
                    TEMPORAL_PICTURE_NAME = "Image"+ new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date()) +".jpg";

                    path = data.getData();
                    imgFruta.setVisibility(View.VISIBLE);
                    imgFruta.setImageURI(path);
                }
                break;
        }
    }

    private void decodeBitmap(String dir) {

        bitmap = BitmapFactory.decodeFile(dir);
        imgFruta.setVisibility(View.VISIBLE);
        imgFruta.setImageBitmap(bitmap);

    }


}
