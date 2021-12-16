package com.volcanapp.volcanapp.ui.home;

import android.app.Notification;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.volcanapp.volcanapp.R;
import com.volcanapp.volcanapp.modelos.FirebaseReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private TextView textViewAlert;
    private MediaPlayer mp;
    private Handler handler = new Handler();
    private int contador = 1;
    private final int TIEMPO = 5000;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewAlert = view.findViewById(R.id.textView_alert);
        textViewAlert.setVisibility(View.GONE);
        mp = MediaPlayer.create(getContext(), R.raw.alerta);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef = database.getReference(FirebaseReference.DB_REFERENCE_ALERT);
        //Toast.makeText(getContext(), "KEY: " + myRef.getKey(), Toast.LENGTH_SHORT).show();

        // si cambia en fire base se actualiza en tiempo real
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String valor = snapshot.getValue(String.class);
                Toast.makeText(getContext(), "Data: " + valor, Toast.LENGTH_SHORT).show();
                if (valor.equals("True")) {
                    Toast.makeText(getContext(), "Data: " + valor, Toast.LENGTH_SHORT).show();
                    textViewAlert.setVisibility(View.VISIBLE);

                    Notification notification=new Notification(android.R.drawable.ic_btn_speak_now,"Hola",100);
                    notification.sound = Uri.parse("android.resource://" +  "com.volcanapp.volcanapp" + "/" + R.raw.alerta);
                    notification.defaults = Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE;

                    contador = 1;
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            if (contador > 5) {
                                return;
                            } else {
                                mp.start();
                                contador = contador + 1;
                            }

                            handler.postDelayed(this, TIEMPO);
                        }

                    }, TIEMPO);


                }else{
                    textViewAlert.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Erro: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}