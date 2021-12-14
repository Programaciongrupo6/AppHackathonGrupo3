package com.volcanapp.volcanapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {
    private Button btnLogin;
    private Button btnShowRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        event();
    }
    private void init() {
        btnLogin = findViewById(R.id.button_login);
        btnShowRegistrar= findViewById(R.id.button_showRegistrar);

    }
    private void event() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHomeUI();
            }
        });
        btnShowRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegistrarUsuarioUI();
            }
        });
    }

    private void showRegistrarUsuarioUI(){
        Intent intent = new Intent(this, RegistroUsuario.class);
        startActivity(intent);
    }
    private void showHomeUI(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}