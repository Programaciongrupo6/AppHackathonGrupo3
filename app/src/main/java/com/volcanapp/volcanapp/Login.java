package com.volcanapp.volcanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    private Button btnLogin;
    private Button btnShowRegistrar;

    private EditText editTEmail;
    private EditText editTPass;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        event();
        runAnalytics();
    }
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            showHomeUI();
        }
    }
    private void init() {
        btnLogin = findViewById(R.id.button_login);
        btnShowRegistrar= findViewById(R.id.button_showRegistrar);

        editTEmail = findViewById(R.id.editText_login_email);
        editTPass= findViewById(R.id.editText_login_password);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAuth = FirebaseAuth.getInstance();

    }
    private void event() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTEmail.getText().toString();
                String password = editTPass.getText().toString();
                login(email, password);
            }
        });
        btnShowRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegistrarUsuarioUI();
            }
        });
    }
    private boolean login(String email, String pass){
        if (email.isEmpty() || pass.isEmpty()){
            msn("Todos los campos son obligatorios");
        }else{
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        showHomeUI();
                    } else{
                        showAlert();
                    }
                }
            });
        }
        return true;
    }
    private void runAnalytics(){
        Bundle bundle = new Bundle();
        String id = "000";
        String name = "APPLP";
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Autenticación");
        builder.setMessage("Se ha producido un error autenticando el usuario.");
        builder.setCancelable(true);
        builder.setPositiveButton("Aceptar", null);
        AlertDialog alert = builder.create();
        alert.show();

    }
    private void showRegistrarUsuarioUI(){
        Intent intent = new Intent(this, RegistroUsuario.class);
        startActivity(intent);
    }
    private void showHomeUI(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void msn(String data){
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();

    }
}