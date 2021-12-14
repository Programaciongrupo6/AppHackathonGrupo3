package com.volcanapp.volcanapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;

public class RegistroLugar extends AppCompatActivity {
    private TextView descripcion;
    private TextView tipoAlarma;
    private TextView ultimaAct;
    private Spinner lugar;
    private MapView mapa;
    private Button registroLugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_lugar);

        descripcion = findViewById(R.id.tv_descripcion);
        tipoAlarma = findViewById(R.id.tv_tipoAlarma);
        ultimaAct = findViewById(R.id.tv_ultimaAct);
        lugar = findViewById(R.id.spinner);
        mapa = findViewById(R.id.nav_view);
        registroLugar = findViewById(R.id.btn_registrarLugar);


    }
}