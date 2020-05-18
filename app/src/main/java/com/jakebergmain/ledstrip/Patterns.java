package com.jakebergmain.ledstrip;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;


public class Patterns extends Fragment {

    final String LOG_TAG = MainFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;


    public Patterns() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }
    }

    SeekBar bright, pSpeed, mbright;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_patterns, container, false);


        bright = (SeekBar) rootView.findViewById(R.id.patBrightness);
        bright.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(SendString.waitForPacket == false) new SendString(getActivity()).execute("bright:" + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        pSpeed = (SeekBar) rootView.findViewById(R.id.pulseSpeed);
        pSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(SendString.waitForPacket == false) new SendString(getActivity()).execute("pSpeed:" + (40-i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mbright = (SeekBar) rootView.findViewById(R.id.minBrightness);
        mbright.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(SendString.waitForPacket == false) new SendString(getActivity()).execute("minbright:" + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Spinner spinner = (Spinner) rootView.findViewById(R.id.pattern);

        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.pattern_array, R.layout.spinner_dropdown_item);

        spinner.setAdapter(spinAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if(SendString.waitForPacket == false) new SendString(getActivity()).execute("pat:" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner paletteSpinner = (Spinner)rootView.findViewById(R.id.palette);
        ArrayAdapter<CharSequence> paletteAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.palette_array, R.layout.spinner_dropdown_item);

        paletteSpinner.setAdapter(paletteAdapter);
        paletteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if(SendString.waitForPacket == false) new SendString(getActivity()).execute("pal:" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button pOn = (Button) rootView.findViewById(R.id.pulseON);
        Button pOff = (Button) rootView.findViewById(R.id.pulseOFF);

        pOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SendString.waitForPacket == false) new SendString(getActivity()).execute("puls:on");
            }
        });

        pOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SendString.waitForPacket == false) new SendString(getActivity()).execute("puls:off");
            }
        });

        return rootView;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}