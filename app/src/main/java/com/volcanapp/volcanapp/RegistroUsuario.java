package com.volcanapp.volcanapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistroUsuario extends AppCompatActivity {
    private Button btnRegistrarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        init();
        event();
    }
    private void init() {
        btnRegistrarUsuario = findViewById(R.id.button_registrarUsuario);

    }
    private void event() {
        btnRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHomeUI();
            }
        });
    }

    private void showHomeUI(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}