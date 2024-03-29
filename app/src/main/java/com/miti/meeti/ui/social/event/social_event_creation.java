package com.miti.meeti.ui.social.event;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.miti.meeti.MainActivity;
import com.miti.meeti.R;
import com.miti.meeti.apicompat.mitihelper;
import com.miti.meeti.ui.social.pref.social_pref_interest;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link social_event_creation.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link social_event_creation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class social_event_creation extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private String [][] array= social_pref_interest.array;
    public social_event_creation() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity.SetNavigationVisibiltity(false);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment social_event_creation.
     */
    // TODO: Rename and change types and number of parameters
    public static social_event_creation newInstance(String param1, String param2) {
        social_event_creation fragment = new social_event_creation();
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
        View v=inflater.inflate(R.layout.fragment_social_event_creation, container, false);
        ChipGroup get=(ChipGroup) v.findViewById(R.id.prefchipgroup);
        ViewGroup temp=(ViewGroup)get;
        for(int i=0;i<5;i++){
            for(int j=0;j<array[i].length;j++){
                Chip temp1=new Chip(v.getContext());
                temp1.setBackgroundColor(getResources().getColor(R.color.mitiOrange));
                temp1.setText(array[i][j]);
                temp1.setCheckable(true);
                temp1.setLayoutParams(new ViewGroup.LayoutParams (WRAP_CONTENT,WRAP_CONTENT));
                int id;
                id= mitihelper.getuniqueid();
                temp1.setId(id);
                temp.addView(temp1);
            }
        }
        get.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, @IdRes int checkedId) {
                // Handle the checked chip change.
                Log.e("Control",Integer.toString(checkedId));
            }
        });
        Button b1=v.findViewById(R.id.event_button_submit);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
