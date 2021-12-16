package com.volcanapp.volcanapp.ui.mapa;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.volcanapp.volcanapp.R;
import com.volcanapp.volcanapp.RegistroLugar;
import com.volcanapp.volcanapp.modelos.FirebaseReference;
import com.volcanapp.volcanapp.modelos.LugarMonitoreado;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapaFragment extends Fragment implements OnMapReadyCallback {


    private Spinner spinnerLugaresMonitoreados;
    private FloatingActionButton floatingButtonAddLugarMonitoreado;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private View view;
    private GoogleMap mMap;


    public static MapaFragment newInstance() {
        MapaFragment fragment = new MapaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mapa, container, false);


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.googleMaps1);
        mapFragment.getMapAsync(this);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        event();

    }

    private void init() {

        spinnerLugaresMonitoreados = view.findViewById(R.id.Spinner_misLugaresMonitoreados);
        floatingButtonAddLugarMonitoreado = view.findViewById(R.id.floatingActionButton_addLugarMonitoreado);
        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();
        loadLuagresMonitoreados();

    }

    private void loadLuagresMonitoreados() {
        List<LugarMonitoreado> lugaresMonitoreados = new ArrayList<>();
        db.collection(FirebaseReference.DB_REFERENCE_LUGARES_MONITOREADOS)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Toast.makeText(getContext(), "FireBase error, " + error.getMessage(), Toast.LENGTH_LONG).show();
                            return;
                        }
                        /*
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){

                                String id = dc.getDocument().getId();
                                String nombre = dc.getDocument().getString("nombre").toString();
                                lugaresMonitoreados.add(new LugarMonitoreado(id, nombre));
                                //lugaresMonitoreados.add(dc.getDocument().toObject(LugarMonitoreado.class));

                            }
                        }
                        ArrayAdapter <LugarMonitoreado> arrayAdapter = new ArrayAdapter<>(getContext(),
                                android.R.layout.simple_dropdown_item_1line, lugaresMonitoreados);
                        spinnerLugaresMonitoreados.setAdapter(arrayAdapter);

                         */
                    }
                });
    }

    private void event() {
        floatingButtonAddLugarMonitoreado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowRegistroLugarIU();
            }
        });

    }

    private void ShowRegistroLugarIU() {

        Intent intent = new Intent(getContext(), RegistroLugar.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng zoom = new LatLng(4.675316, -74.111595);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(zoom));

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            db.collection(FirebaseReference.DB_REFERENCE_USERS)
                    .whereEqualTo("email", email)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    String idUser = document.getId();
                                    ArrayList listLugares = (ArrayList) document.get("lugares");

                                    for (int i = 0; i < listLugares.size(); i++) {
                                        String idLugar = listLugares.get(i).toString();
                                        System.out.println(idLugar);
                                        db.collection(FirebaseReference.DB_REFERENCE_LUGARES_MONITOREADOS)
                                                .document(idLugar)
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot document = task.getResult();
                                                            if (document.exists()) {

                                                                //System.out.println("DocumentSnapshot data: " + document.getData());
                                                                String nombre = document.getString("nombre").toString();
                                                                String descripcion = document.getString("descripcion").toString();
                                                                String tipo_alarma = document.getString("tipo_alarma").toString();
                                                                String ultima_actualizacion = document.getString("ultima_actualizacion").toString();
                                                                Double latitud = document.getDouble("latitud");
                                                                Double longitud = document.getDouble("longitud");
                                                                showPoint(latitud, longitud, nombre);
                                                                System.out.println(nombre + " "+ tipo_alarma);

                                                            } else {
                                                                msn("No such document");
                                                            }
                                                        } else {
                                                            msn( "get failed with "+ task.getException());
                                                        }
                                                    }
                                                });
                                    }



    /*
                                    String idLugarSelecionado = SpinnerLugaresMonitoreados.getSelectedItem().toString();

                                    int indice = listLugares.indexOf(idLugarSelecionado);
                                    if (indice != -1){
                                        msn("Ya tienes agregado el lugar seleccionado");
                                    }else{
                                        listLugares.add(idLugarSelecionado);

                                        //System.out.println(listLugares);
                                        //System.out.println(document.getId() + " => " + document.getData());
                                        //System.out.println(idUser);


                                        Map<String, Object> data = new HashMap<>();
                                        data.put("lugares", listLugares);

                                        db.collection(FirebaseReference.DB_REFERENCE_USERS)
                                                .document(idUser)
                                                .set(data, SetOptions.merge());



                                    }

     */


                                }
                            } else {
                                msn("Error getting documents: " + task.getException());
                                return;
                            }
                        }
                    });
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(zoom));

    }
    private void showPoint(Double latitud_, Double longitud_, String nombre_) {
        LatLng sitio = new LatLng(latitud_, longitud_);
        mMap.addMarker(new MarkerOptions().position(sitio).title(nombre_));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sitio));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(5));
    }

    private void msn(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
    }
}