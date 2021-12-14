package com.volcanapp.volcanapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ReportarEmergencia extends AppCompatActivity {
    private ImageView subirFoto;
    private EditText lugarEvento;
    private EditText reporte;
    private Button enviarReporte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar_emergencia);

        subirFoto = findViewById(R.id.img_subirFoto);
        lugarEvento = findViewById(R.id.et_lugarEvento);
        reporte = findViewById(R.id.et_reporte);
        enviarReporte = findViewById(R.id.btn_enviar);
    }
}