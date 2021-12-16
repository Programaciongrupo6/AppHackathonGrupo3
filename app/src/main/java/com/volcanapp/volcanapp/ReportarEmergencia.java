package com.volcanapp.volcanapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ReportarEmergencia extends AppCompatActivity {
    private ImageView subirFoto;
    private EditText lugarEvento;
    private EditText reporte;
    private Button enviarReporte;
    private Button btn_reportarEmergenciaBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar_emergencia);

        subirFoto = findViewById(R.id.img_subirFoto);
        lugarEvento = findViewById(R.id.et_lugarEvento);
        reporte = findViewById(R.id.et_reporte);
        enviarReporte = findViewById(R.id.btn_enviar);
        btn_reportarEmergenciaBack = findViewById(R.id.button_reportarLugarBack);

        enviarReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msn("Se ha enviado la información de la emergencia a las autoridades ");
                showHomeUI();

            }
        });
        btn_reportarEmergenciaBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showHomeUI();

            }
        });
    }

    private void msn(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    private void showHomeUI() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}