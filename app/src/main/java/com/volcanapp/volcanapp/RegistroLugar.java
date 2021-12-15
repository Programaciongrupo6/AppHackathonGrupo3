package com.volcanapp.volcanapp;

import static com.volcanapp.volcanapp.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;
import com.volcanapp.volcanapp.databinding.ActivityMainBinding;
import com.volcanapp.volcanapp.databinding.FragmentMapaBinding;

public class RegistroLugar extends AppCompatActivity {
    private TextView descripcion;
    private TextView tipoAlarma;
    private TextView ultimaAct;
    private Spinner lugar;
    private MapView mapa;
    private Button registroLugar;
    private Button volverLugares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_registro_lugar);

        descripcion = findViewById(id.tv_descripcion);
        tipoAlarma = findViewById(id.tv_tipoAlarma);
        ultimaAct = findViewById(id.tv_ultimaAct);
        lugar = findViewById(id.spinner);
        mapa = findViewById(id.nav_view);
        registroLugar = findViewById(id.btn_registrarLugar);
        volverLugares = findViewById(id.button_lugaresBack);

        volverLugares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLugaresUI();
            }
        });


    }

    private void showLugaresUI(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}