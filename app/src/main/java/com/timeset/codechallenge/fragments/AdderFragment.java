package com.timeset.codechallenge.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.timeset.codechallenge.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdderFragment extends Fragment {

    public static TextView resultText;
    public AdderFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(){
        AdderFragment adderFragment = new AdderFragment();
        return adderFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_adder, container, false);
        resultText = (TextView)rootView.findViewById(R.id.resultText);
        resultText.setText(getString(R.string.result_label)+" ?");
        return rootView;
    }





}
