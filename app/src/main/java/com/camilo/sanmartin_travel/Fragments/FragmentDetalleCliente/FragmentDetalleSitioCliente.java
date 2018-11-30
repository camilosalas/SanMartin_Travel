package com.camilo.sanmartin_travel.Fragments.FragmentDetalleCliente;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.camilo.sanmartin_travel.Fragments.FragmentDetalles.FragmentInformacion;
import com.camilo.sanmartin_travel.Fragments.FragmentDetalles.FragmentUbicacion;
import com.camilo.sanmartin_travel.Models.ListaSitios;
import com.camilo.sanmartin_travel.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FragmentDetalleSitioCliente extends Fragment {

    View vista;

    AppBarLayout appBar;
    private TabLayout pestanas;
    ViewPager viewPager;

    ImageView imgdetallesit;

    ListaSitios listaSitios;

    @SuppressLint("ValidFragment")
    public FragmentDetalleSitioCliente(ListaSitios listaSitios) {
        this.listaSitios = listaSitios;
    }

    public FragmentDetalleSitioCliente(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_detalle_sitio_cliente, container, false);

        imgdetallesit = vista.findViewById(R.id.imgdetallesit);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://san-martin-travel.appspot.com").child(listaSitios.getImg());

        try {
            final File localFile = File.createTempFile("images","jpg");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    imgdetallesit.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }catch (IOException e){}


        if (savedInstanceState == null) {
            insertarTabs(container);

            viewPager = vista.findViewById(R.id.pagerdetallesitio);
            poblarViewPager(viewPager);
            pestanas.setupWithViewPager(viewPager);
        }

        return vista;
    }

    private void insertarTabs(ViewGroup container) {
        View padre = (View) container.getParent();
        appBar = padre.findViewById(R.id.appbarclient);
        pestanas = new TabLayout(getActivity());
        pestanas.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));
        appBar.addView(pestanas);
    }

    private void poblarViewPager(ViewPager viewPager) {
        AdaptadorSecciones adapter = new AdaptadorSecciones(getFragmentManager());
        adapter.addFragment(new FragmentInformacion(listaSitios), getString(R.string.info));
        adapter.addFragment(new FragmentUbicacion(listaSitios), getString(R.string.dire));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        appBar.removeView(pestanas);
    }

    public class AdaptadorSecciones extends FragmentStatePagerAdapter {
        private final List<Fragment> fragmentossitio = new ArrayList<>();
        private final List<String> titulosFragmentossitio = new ArrayList<>();

        AdaptadorSecciones(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragmentossitio.get(position);
        }

        @Override
        public int getCount() {
            return fragmentossitio.size();
        }

        void addFragment(android.support.v4.app.Fragment fragment, String title) {
            fragmentossitio.add(fragment);
            titulosFragmentossitio.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titulosFragmentossitio.get(position);
        }

    }
}
