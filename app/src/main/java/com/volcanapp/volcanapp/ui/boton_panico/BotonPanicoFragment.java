package com.volcanapp.volcanapp.ui.boton_panico;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.volcanapp.volcanapp.Login;
import com.volcanapp.volcanapp.R;
import com.volcanapp.volcanapp.ReportarEmergencia;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BotonPanicoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BotonPanicoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView btnPanico;
    private FirebaseAuth mAuth;
    private final int TIEMPO = 10000;
    private Handler handler = new Handler();
    private int contador = 1;


    private Button btnShowReportarEmergencia;
    View view;

    public BotonPanicoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BotonPanicoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BotonPanicoFragment newInstance(String param1, String param2) {
        BotonPanicoFragment fragment = new BotonPanicoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_boton_panico, container, false);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 1);
        }


        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        event();


    }


    private void init() {
        btnShowReportarEmergencia = view.findViewById(R.id.button_showReportarEmergencia);
        btnPanico = view.findViewById(R.id.imageView_button_panico);
        mAuth = FirebaseAuth.getInstance();


    }

    private void event() {
        btnShowReportarEmergencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowReportarEmergenciaIU();
            }
        });
        btnPanico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Emergencia();
            }
        });

    }

    private void Emergencia() {

        String email = "Default";
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            email = user.getEmail();
            String phone = "3118994279";
            String text = "Mensaje de prueba  de " + email + " debería contener información básica del usuario y geolocalización";
            SmsManager sms = SmsManager.getDefault();
            ArrayList<String> parts = sms.divideMessage(text);

            contador = 1;
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (contador > 3) {
                        return;
                    } else {
                        sms.sendMultipartTextMessage(phone, null, parts, null, null);
                        contador = contador + 1;
                    }

                    handler.postDelayed(this, TIEMPO);
                }

            }, TIEMPO);
            Toast.makeText(getContext(), "Mensaje enviado", Toast.LENGTH_SHORT).show();
        }

        /*
        SmsManager smsManager = SmsManager.getDefault();
        String cel = "3173927733";
        cel = "3118994279";
        String msn = "Mensaje de prueba debería contener información básica del usuario y geolocalización ";
        smsManager.sendTextMessage(cel,null,msn,null,null);
        Toast.makeText(getContext(),"Mensaje enviado",Toast.LENGTH_SHORT).show();



        String phone = "3118994279";
        String text = "Mensaje de prueba debería contener información básica del usuario y geolocalización";
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phone, null, text , null, null);

         */


    }

    private void ShowReportarEmergenciaIU() {

        Intent intent = new Intent(getContext(), ReportarEmergencia.class);
        startActivity(intent);
    }
}