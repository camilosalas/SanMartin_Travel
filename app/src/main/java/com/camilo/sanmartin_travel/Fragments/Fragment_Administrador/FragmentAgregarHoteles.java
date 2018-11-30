package com.camilo.sanmartin_travel.Fragments.Fragment_Administrador;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.camilo.sanmartin_travel.Models.Hotel;
import com.camilo.sanmartin_travel.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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

public class FragmentAgregarHoteles extends Fragment {

    View vista;

    MapView mMapView;
    private GoogleMap googleMap;

    double lat = 0.0;
    double lng = 0.0;

    String ubicar = "";
    String img = "";

    EditText nombre, descripcion, telefono, ubicacion, precio;
    ImageView imgHotel;
    FloatingActionButton addImg;
    CardView agregar;

    private DatabaseReference reference;
    private StorageReference mStorage;

    String APP_DIRECTORY = "San Martin Travel";
    String TEMPORAL_PICTURE_NAME;
    private final int PHOTO_CODE = 100;
    private final int SELECT_PICTURE = 200;

    Bitmap bitmap;
    Uri path;

    public FragmentAgregarHoteles(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_agregar_hoteles, container, false);

        agregar = vista.findViewById(R.id.addHotel);
        nombre = vista.findViewById(R.id.NombreHotel);
        ubicacion = vista.findViewById(R.id.UbicacionHotel);
        descripcion = vista.findViewById(R.id.DescripcionHotel);
        telefono = vista.findViewById(R.id.TelefonoHotel);
        imgHotel = vista.findViewById(R.id.imgHotel);
        addImg = vista.findViewById(R.id.addImgHotel);
        precio = vista.findViewById(R.id.PrecioHotel);

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        reference = mDatabase.getReference();

        mStorage = FirebaseStorage.getInstance().getReference();

        mMapView = vista.findViewById(R.id.mapaHotel);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap mMap) {
                googleMap = mMap;

                mMap.setMinZoomPreference(12);
                mMap.setIndoorEnabled(true);
                UiSettings uiSettings = mMap.getUiSettings();
                uiSettings.setIndoorLevelPickerEnabled(true);
                uiSettings.setMyLocationButtonEnabled(true);
                uiSettings.setMapToolbarEnabled(true);
                uiSettings.setCompassEnabled(true);
                uiSettings.setZoomControlsEnabled(true);
                uiSettings.setScrollGesturesEnabled(true);

                if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                } else {
                    mMap.setMyLocationEnabled(true);
                }

                LocationManager locationmanager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                assert locationmanager != null;
                String provider = locationmanager.getBestProvider(criteria, false);
                Location location = locationmanager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                mMap.setMyLocationEnabled(true);

                lat = location.getLatitude();
                lng = location.getLongitude();

                final LatLng coordenadas = new LatLng(lat, lng);
                CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 14);
                mMap.animateCamera(miUbicacion);

                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        mMap.clear();
                        Toast.makeText(getActivity(), "Marcador Eliminado, Presione un momento sobre el mapa para seleccionar la posición", Toast.LENGTH_SHORT).show();
                    }
                });

                mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title("Ubicacion")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                                .snippet("Ubicacion del Hotel"));
                        ubicar = latLng.toString();
                        Toast.makeText(getActivity().getApplicationContext(), "Coordenadas: " + latLng, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            Toast.makeText(getContext(), "Aún no está disponible", Toast.LENGTH_SHORT).show();
                        } else if(Opciones[which] == "Seleccionar una Foto"){
                            @SuppressLint("IntentReset") Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

                if(!nombre.getText().toString().equals("") &&!ubicacion.getText().toString().equals("") && !descripcion.getText().toString().equals("") && !telefono.getText().toString().equals("") && !TEMPORAL_PICTURE_NAME.equals("") && !precio.getText().toString().equals("")){

                    lat = Double.parseDouble(ubicar.substring(10, ubicar.indexOf(",")));
                    lng = Double.parseDouble(ubicar.substring(ubicar.indexOf(",") + 1, ubicar.length() - 1));

                    StorageReference filepath = mStorage.child(nombre.getText().toString() +"/"+TEMPORAL_PICTURE_NAME);

                    if(bitmap != null){

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] data = baos.toByteArray();

                        filepath.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                //Aquí va lo que deseas que se muestre cuando ya se agrega la imagen a firebase.
                            }
                        });
                    } else {
                        filepath.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                //Aquí va lo que deseas que se muestre cuando ya se agrega la imagen a firebase.
                            }
                        });
                    }

                    Hotel hotel = new Hotel(nombre.getText().toString(), descripcion.getText().toString(), nombre.getText().toString() + "/" + TEMPORAL_PICTURE_NAME, telefono.getText().toString(),ubicacion.getText().toString(), precio.getText().toString(), lat, lng);
                    reference.child("Hoteles").push().setValue(hotel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                agregar.setEnabled(true);

                                Fragment nuevofragmet = new FragmentListaHotel();
                                assert getFragmentManager() != null;
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.principales, nuevofragmet);
                                transaction.addToBackStack(null);
                                transaction.commit();
                                Toast.makeText(getActivity().getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "No se pudo registrar el Hotel", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Por favor rellene el formulario para continuar", Toast.LENGTH_SHORT).show();
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

        String path = Environment.getExternalStorageDirectory() + File.separator + APP_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;

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
                    String dir = Environment.getExternalStorageDirectory() + File.separator + APP_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;
                    decodeBitmap(dir);
                }
                break;
            case SELECT_PICTURE:
                if(resultCode == RESULT_OK){
                    TEMPORAL_PICTURE_NAME = "Image"+ new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date()) +".jpg";

                    path = data.getData();
                    imgHotel.setVisibility(View.VISIBLE);
                    imgHotel.setImageURI(path);
                }
                break;
        }

    }

    private void decodeBitmap(String dir) {

        bitmap = BitmapFactory.decodeFile(dir);
        imgHotel.setVisibility(View.VISIBLE);
        imgHotel.setImageBitmap(bitmap);

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
