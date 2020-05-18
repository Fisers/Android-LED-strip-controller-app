 package com.jakebergmain.ledstrip;

        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.SharedPreferences;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ListView;
        import android.widget.SeekBar;
        import android.widget.Spinner;

        import com.skydoves.colorpickerview.ColorEnvelope;
        import com.skydoves.colorpickerview.ColorPickerView;
        import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
        import com.skydoves.colorpickerview.listeners.ColorListener;
        import com.skydoves.colorpickerview.sliders.BrightnessSlideBar;
        import com.jakebergmain.ledstrip.CustomViewPager;

        import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements DiscoverTask.DiscoverCallback {

    final String LOG_TAG = MainFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;


    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
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
    BrightnessSlideBar slider;
    ColorPickerView colorPickerView;
    SeekBar bright;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        searchForDevices();

        slider = (BrightnessSlideBar) rootView.findViewById(R.id.brightnessSlide);
        colorPickerView = (ColorPickerView) rootView.findViewById(R.id.colorPickerView);
        colorPickerView.attachBrightnessSlider(slider);
        colorPickerView.setColorListener(new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                int color[];
                color = envelope.getArgb();
                int r = color[1];
                int b = color[3];
                int g = color[2];
                if(SendString.waitForPacket == false) new SendString(getActivity()).execute(r + ":" + g + ":" + b);
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




    /**
     * method for Discover task callback
     */
    public void onFoundDevice(){
        // we found a LED strip!
        // what do we do now?

        // for debug only
        SharedPreferences preferences = getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, 0);
        String ipString = preferences.getString(Constants.PREFERENCES_IP_ADDR, "");
        Log.v(LOG_TAG, "onDeviceFound() ipAddr: " + ipString);
    }


    /**
     * Start DiscoverTask to search for devices on local network.
     */
    public void searchForDevices(){
        Log.v(LOG_TAG, "starting DiscoverTask");
        new DiscoverTask(getActivity(), this).execute(null, null);
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}