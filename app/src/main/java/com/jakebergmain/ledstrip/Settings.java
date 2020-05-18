package com.jakebergmain.ledstrip;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class Settings extends Fragment {

    final String LOG_TAG = MainFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;


    public Settings() {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        final EditText mNoLed = (EditText) rootView.findViewById(R.id.NoLED);
        mNoLed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!mNoLed.getText().toString().matches("")) {
                    new SendString(getActivity()).execute("Led:" + mNoLed.getText());
                }
            }
        });
        final EditText mFPS = (EditText) rootView.findViewById(R.id.fps);
        mFPS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!mFPS.getText().toString().matches("")) {
                    new SendString(getActivity()).execute("fps:" + mFPS.getText());
                }
            }
        });
        final EditText mSSID = (EditText) rootView.findViewById(R.id.ssid);
        mSSID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!mSSID.getText().toString().matches("")) {
                    new SendString(getActivity()).execute("ssid:" + mSSID.getText());
                }
            }
        });
        final EditText mPass = (EditText) rootView.findViewById(R.id.password);
        mPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!mPass.getText().toString().matches("")) {
                    new SendString(getActivity()).execute("pass:" + mPass.getText());
                }
            }
        });

        Button mOn = (Button) rootView.findViewById(R.id.on);
        Button mOff = (Button) rootView.findViewById(R.id.off);
        Button mRst = (Button) rootView.findViewById(R.id.restart);
        mOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SendString(getActivity()).execute("On");
            }
        });
        mOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SendString(getActivity()).execute("Off");
            }
        });
        mRst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SendString(getActivity()).execute("Rst");
            }
        });

        return rootView;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}