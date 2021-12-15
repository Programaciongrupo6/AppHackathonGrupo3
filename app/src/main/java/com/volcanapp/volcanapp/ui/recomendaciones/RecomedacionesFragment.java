package com.volcanapp.volcanapp.ui.recomendaciones;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.volcanapp.volcanapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecomedacionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecomedacionesFragment extends Fragment {

    private ViewFlipper v_flipper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;

    private int images[] = {R.drawable.avalanchatext, R.drawable.inundaciontext, R.drawable.erupciontext};

    public RecomedacionesFragment() {


        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecomedacionesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecomedacionesFragment newInstance(String param1, String param2) {
        RecomedacionesFragment fragment = new RecomedacionesFragment();
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
        view = inflater.inflate(R.layout.fragment_recomedaciones, container, false);
        //https://www.youtube.com/watch?v=-pI8ep8HEp8
        v_flipper = (ViewFlipper) view.findViewById(R.id.v_flipper);

        for (int image: images){
            System.out.println(image);
            flipperImages(image);
        }
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void flipperImages(int image){
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);

//        v_flipper.dispatchTouchEvent(MotionEvent.obtain(MotionEvent)){
//            v_flipper.startFlipping();
//            v_flipper.setInAnimation(), android.R.anim.slide_in_left;
//            v_flipper.setOutAnimation(), android.R.anim.slide_out_right;
//        }
        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(3000);
        v_flipper.setAutoStart(true);
        v_flipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
    }
}