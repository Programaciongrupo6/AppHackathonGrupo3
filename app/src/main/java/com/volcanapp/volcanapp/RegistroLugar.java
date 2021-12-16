package com.volcanapp.volcanapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.volcanapp.volcanapp.modelos.FirebaseReference;
import com.volcanapp.volcanapp.modelos.LugarMonitoreado;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroLugar extends FragmentActivity implements OnMapReadyCallback {
    private TextView TV_descripcion;
    private TextView TV_tipoAlarma;
    private TextView TV_ultimaAct;
    private Spinner SpinnerLugaresMonitoreados;
    private MapView mapaLugar;
    private Button btnRegistroLugar;
    private Button btn_registroLugarBack;
    private FirebaseFirestore db;
    private Task<DocumentSnapshot> docRef;
    private GoogleMap mMap;

    private Double latitud = 4.00;
    private Double longitud = 74.0;
    private String nombre = "Default";

    private FirebaseAuth mAuth;
    private String idUser = "";

    private int GPS_REQUEST_CODE = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_lugar);

        init();
        event();
        initMap();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googleMaps1);
        mapFragment.getMapAsync(this);


    }

    private void init() {
        TV_descripcion = findViewById(R.id.tv_descripcion);
        TV_tipoAlarma = findViewById(R.id.tv_tipoAlarma);
        TV_ultimaAct = findViewById(R.id.tv_ultimaAct);
        SpinnerLugaresMonitoreados = findViewById(R.id.Spinner_lugaresMonitoreados);
        mapaLugar = findViewById(R.id.nav_view);
        btnRegistroLugar = findViewById(R.id.btn_registrarLugar);
        btn_registroLugarBack = findViewById(R.id.button_registroLugarBack);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        loadLuagresMonitoreados();

        /*
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(RegistroLugar.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }

         */


    }

    private void event() {
        btnRegistroLugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registarLugar();

            }
        });

        btn_registroLugarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showHomeUI();

            }
        });
    }

    private void registarLugar() {

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();

            //https://www.youtube.com/watch?v=oKTfnF6hVV0

            db.collection(FirebaseReference.DB_REFERENCE_USERS)
                    .whereEqualTo("email", email)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    idUser = document.getId();
                                    ArrayList listLugares = (ArrayList) document.get("lugares");
                                    String idLugarSelecionado = SpinnerLugaresMonitoreados.getSelectedItem().toString();

                                    int indice = listLugares.indexOf(idLugarSelecionado);
                                    if (indice != -1) {
                                        msn("Ya tienes agregado el lugar seleccionado");
                                    } else {
                                        listLugares.add(idLugarSelecionado);

                                        //System.out.println(listLugares);
                                        //System.out.println(document.getId() + " => " + document.getData());
                                        //System.out.println(idUser);


                                        Map<String, Object> data = new HashMap<>();
                                        data.put("lugares", listLugares);

                                        db.collection(FirebaseReference.DB_REFERENCE_USERS)
                                                .document(idUser)
                                                .set(data, SetOptions.merge());

                                        msn("Lugar registrado correctamente");
                                        showHomeUI();
                                    }


                                }
                            } else {
                                msn("Error getting documents: " + task.getException());
                                return;
                            }
                        }
                    });


        }


    }

    private void showHomeUI() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void initMap() {
        if (isGPSenable()) {

        }
    }

    private boolean isGPSenable() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean providerEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (providerEnable) {
            return true;
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("Permiso de GPS")
                    .setMessage("Se requiere GPS para que esta aplicación funcione, habilite el GPS")
                    .setPositiveButton("Si", ((dialogInterface, i) -> {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, GPS_REQUEST_CODE);
                    }))
                    .setCancelable(false)
                    .show();
        }
        return false;
    }

    private void loadLuagresMonitoreados() {
        List<LugarMonitoreado> lugaresMonitoreados = new ArrayList<>();
        db.collection(FirebaseReference.DB_REFERENCE_LUGARES_MONITOREADOS)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            msn("FireBase error, " + error.getMessage());

                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {


                                String id = dc.getDocument().getId();
                                System.out.println(id);
                                String nombre = dc.getDocument().getString("nombre").toString();
                                String descripcion = dc.getDocument().getString("descripcion").toString();
                                String tipo_alarma = dc.getDocument().getString("tipo_alarma").toString();
                                String ultima_actualizacion = dc.getDocument().getString("ultima_actualizacion").toString();
                                String latitud = dc.getDocument().getDouble("latitud").toString();
                                String longitud = dc.getDocument().getDouble("longitud").toString();
                                System.out.println(id);


                                lugaresMonitoreados.add(new LugarMonitoreado(
                                        id, nombre, descripcion, tipo_alarma, ultima_actualizacion, latitud, longitud
                                ));
                                //lugaresMonitoreados.add(dc.getDocument().toObject(LugarMonitoreado.class));

                            }
                        }
                        ArrayAdapter<LugarMonitoreado> arrayAdapter = new ArrayAdapter<>(RegistroLugar.this,
                                android.R.layout.simple_dropdown_item_1line, lugaresMonitoreados);
                        SpinnerLugaresMonitoreados.setAdapter(arrayAdapter);
                        SpinnerLugaresMonitoreados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                                String uid = adapterView.getItemAtPosition(position).toString();
                                docRef = db.collection(FirebaseReference.DB_REFERENCE_LUGARES_MONITOREADOS)
                                        .document(uid)
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                                                if (documentSnapshot.exists()) {
                                                    nombre = documentSnapshot.getString("nombre");
                                                    String descripcion = documentSnapshot.getString("descripcion");
                                                    String tipo_alarma = documentSnapshot.getString("tipo_alarma");
                                                    String ultima_actualizacion = documentSnapshot.getString("ultima_actualizacion");
                                                    latitud = documentSnapshot.getDouble("latitud");
                                                    longitud = documentSnapshot.getDouble("longitud");

                                                    TV_descripcion.setText("Descripción:\n" + descripcion);
                                                    TV_tipoAlarma.setText("Tipo de alarma:\n" + tipo_alarma);
                                                    TV_ultimaAct.setText("Ultima actualizaci´pon:\n" + ultima_actualizacion);
                                                    showPoint(latitud, longitud, nombre);


                                                } else {
                                                    msn("Lugar (" + uid + ") no encontrado");


                                                }
                                            }
                                        });

                                /*
                                String texto = adapterView.getItemAtPosition(position).toString();
                                String tipoAlarma = adapterView.getItemAtPosition(position).
                                        TV_descripcion.setText("Algo:\n" + texto);
                                TV_tipoAlarma.setText("");
                                TV_ultimaAct.setText("");
                                System.out.println(lugaresMonitoreados);

                                 */
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                });
    }

    private void showPoint(Double latitud_, Double longitud_, String nombre_) {
        LatLng sitio = new LatLng(latitud_, longitud_);
        mMap.addMarker(new MarkerOptions().position(sitio).title(nombre_));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sitio));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }


    private void msn(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
            /*
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(sydney).title(nombre));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

             */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GPS_REQUEST_CODE){
            LocationManager locationManager =(LocationManager) getSystemService(LOCATION_SERVICE);
            boolean providerEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (providerEnable){
                Toast.makeText(this, "GPS está habilitado", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "GPS no está habilitado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}