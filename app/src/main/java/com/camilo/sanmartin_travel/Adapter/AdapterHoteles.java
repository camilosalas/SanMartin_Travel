package com.camilo.sanmartin_travel.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.camilo.sanmartin_travel.Models.ListaHoteles;
import com.camilo.sanmartin_travel.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AdapterHoteles extends ArrayAdapter{

    private Context context;
    private List<ListaHoteles> data;


    public AdapterHoteles(Context context, List<ListaHoteles> data) {
        super(context, R.layout.row_adapter_hotel, data);
        this.context = context;
        this.data = data;
    }

    @NonNull
    public View getView(final int position, final View convertView, @NonNull ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(context);
        final View item = inflater.inflate(R.layout.row_adapter_hotel, null);


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://san-martin-travel.appspot.com").child(data.get(position).getImgHotel());

        try {
            final File localFile = File.createTempFile("images","jpg");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    ImageView imgListaHotel = item.findViewById(R.id.imgListaHotel);
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    imgListaHotel.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }catch (IOException e){}


        TextView nombre = item.findViewById(R.id.listaNombrehotel);
        nombre.setText(data.get(position).getNombreHotel());

        TextView telefono = item.findViewById(R.id.listaNumeroHotel);
        telefono.setText(data.get(position).getTelefonoHotel());

        TextView precio = item.findViewById(R.id.listaPrecioHotel);
        precio.setText(data.get(position).getPrecio());

        return item;
    }

}
