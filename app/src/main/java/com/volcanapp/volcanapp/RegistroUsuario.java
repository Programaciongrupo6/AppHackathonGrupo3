package com.volcanapp.volcanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.volcanapp.volcanapp.funciones.ValidacionCampos;
import com.volcanapp.volcanapp.modelos.FirebaseReference;
import com.volcanapp.volcanapp.modelos.User;

public class RegistroUsuario extends AppCompatActivity {
    private Button btnRegistrarUsuario;
    private Button btnAuthBack;

    private EditText editTName;
    private EditText editTEmail;
    private EditText editTPass;
    private EditText editTPassPass;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ValidacionCampos validar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        init();
        event();
    }
    private void init() {
        btnRegistrarUsuario = findViewById(R.id.button_registrarUsuario);
        btnAuthBack = findViewById(R.id.button_AuthBack);
        editTName = findViewById(R.id.editText_registroUsuario_nombre);
        editTEmail = findViewById(R.id.editText_registroUsuario_correo);
        editTPass= findViewById(R.id.editText_registroUsuario_contraseña);
        editTPassPass= findViewById(R.id.editText_registroUsuario_confirm_pass);
        validar = new ValidacionCampos();


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

    }
    private void event() {
        btnAuthBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginUI();
            }
        });
        btnRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTName.getText().toString();
                String email = editTEmail.getText().toString();
                String password = editTPass.getText().toString();
                String passwordConfirmation = editTPassPass.getText().toString();
                signup(name, email, password, passwordConfirmation);
            }
        });
    }
    public boolean signup(String name, String email, String pass, String passPass ){

        if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || passPass.isEmpty()){
            msn("Todos los campos son obligatorios");
        }else{
            if (validar.validateName(name)){

                if (validar.validateEmail(email)){

                    if (validar.validatePass(pass)){

                        if (pass.equals(passPass)){


                            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        User user1 = new User(name, email);
                                        saveUserFB(user1);
                                        showHomeUI();

                                    } else{
                                        showAlert();
                                    }
                                }
                            });

                        }else{
                            showAlertView("Confirmación","Confirmación de contraseña incorrecta");
                        }



                    }else{
                        showAlertView("Contraseña","Revisa tu contraseña debe tener mínimo 8 caracteres, incluir símbolos y al menos una mayúsculas y una minúscula");
                    }


                }else{
                    showAlertView("Correo","Revisa tu correo electrónico debe tener una estructura similar a: ejemplo@ejepmlo.com");
                }


            }else{
                showAlertView("Nombre","Revisa tu nombre debe tener más de tres caracteres");
            }
        }
        return true;
    }
    private void saveUserFB(User user) {
        db.collection(FirebaseReference.DB_REFERENCE_USERS)
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        System.out.println("DocumentSnapshot added with ID: " + documentReference.getId());


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error adding document: "+ e.getMessage());


                    }
                });


    }
    private void showHomeUI(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void showLoginUI(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
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

    private void showAlertView(String title, String msn){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msn);
        builder.setCancelable(true);
        builder.setPositiveButton("Aceptar", null);
        AlertDialog alert = builder.create();
        alert.show();

    }
    private void msn(String data){
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }
}