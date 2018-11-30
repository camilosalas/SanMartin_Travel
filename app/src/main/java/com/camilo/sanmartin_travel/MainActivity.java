package com.camilo.sanmartin_travel;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.camilo.sanmartin_travel.Fragments.FragmentMenuInicio.Destino_Fragment;
import com.camilo.sanmartin_travel.Fragments.FragmentMenuInicio.ExperienciaFragment;
import com.camilo.sanmartin_travel.Fragments.FragmentMenuInicio.PaisajeFragment;
import com.camilo.sanmartin_travel.Fragments.FragmentMenuInicio.GastronomiaFragment;
import com.camilo.sanmartin_travel.Fragments.FragmentMenuInicio.InicioFragment;
import com.camilo.sanmartin_travel.Fragments.FragmentMenuInicio.HotelFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    FirebaseDatabase database;

    String TAG="MainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        /*****************************************usuario conexion*************/
        FirebaseUser usuari = FirebaseAuth.getInstance().getCurrentUser();
        if (usuari != null){
            MainActivity.this.finish();
            Intent inicio = new Intent(getApplicationContext(), Administrador.class);
            startActivity(inicio);
        }


        /******************************Comprobar estado de la conexion *************/

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    MainActivity.this.finish();
                    Intent inicio = new Intent(getApplicationContext(), Administrador.class);
                    startActivity(inicio);
                }
            }
        };


        checkAndRequestPermissions();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor,new InicioFragment()).commit();
    }

    //Para los Permisos
    private boolean checkAndRequestPermissions() {
        int accesLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int fineLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int locationCommands = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS);
        int Camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int readStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (accesLocation != PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        } if (fineLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        } if (locationCommands != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS);
        } if (Camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        } if (readStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        } if (writeStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_login,null);

                final EditText musuario = (EditText) mView.findViewById(R.id.txtusuario);
                final EditText mclave = (EditText) mView.findViewById(R.id.txtclave);
                MaterialButton minicio = (MaterialButton) mView.findViewById(R.id.btniniciar);

                minicio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!musuario.getText().toString().equals("") && !mclave.getText().toString().equals("")){
                            mAuth.signInWithEmailAndPassword(musuario.getText().toString(), mclave.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            MainActivity.this.finish();
                                            Intent inicio = new Intent(getApplicationContext(), Administrador.class);
                                            startActivity(inicio);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "No se pudo iniciar sesión", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        } else {
                            Toast.makeText(getApplicationContext(), "Por favor rellene el formulario para continuar", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                mbuilder.setView(mView);
                AlertDialog dialog = mbuilder.create();
                dialog.show();
            break;
        }
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_inicio) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new InicioFragment()).commit();
        } else if (id == R.id.nav_Destinos) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new Destino_Fragment()).commit();

        } else if (id == R.id.nav_Gastronomia) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new GastronomiaFragment()).commit();

        } else if (id == R.id.nav_Experiencias) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new ExperienciaFragment()).commit();

        } else if (id == R.id.nav_Galeria) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new PaisajeFragment()).commit();

        } else if (id == R.id.nav_Utilidades) {
            fragmentManager.beginTransaction().replace(R.id.contenedor,new HotelFragment()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /****************************** metodo necesario para firebase************************/
    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if (mAuthStateListener != null){
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
}
