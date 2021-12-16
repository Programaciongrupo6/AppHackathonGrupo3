package com.volcanapp.volcanapp.ui.recomendaciones;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.volcanapp.volcanapp.R;


public class RecomedacionesFragment extends Fragment {

    private ViewFlipper v_flipper;
    private View view;

    private int images[] = {R.mipmap.avalanchatext2, R.mipmap.inundaciontext2, R.mipmap.erupciontext2};

    public static RecomedacionesFragment newInstance() {
        RecomedacionesFragment fragment = new RecomedacionesFragment();
                return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_recomedaciones, container, false);
        //https://www.youtube.com/watch?v=-pI8ep8HEp8
        v_flipper = (ViewFlipper) view.findViewById(R.id.v_flipper);

        for (int image: images){
            System.out.println(image);
            flipperImages(image);
        }
        return view;
    }
    public void flipperImages(int image){
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);
        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(3000);
        v_flipper.setAutoStart(true);
        v_flipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
    }
}