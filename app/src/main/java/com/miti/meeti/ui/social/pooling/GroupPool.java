package com.miti.meeti.ui.social.pooling;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.miti.meeti.MainActivity;
import com.miti.meeti.R;
import com.miti.meeti.apicompat.mitihelper;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.try123;
import com.miti.meeti.ui.social.pref.social_pref_interest;

import java.util.HashMap;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GroupPool.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GroupPool#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupPool extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static View v;
    public HashMap<Integer,String> hashMap=new HashMap<Integer,String>();
    private OnFragmentInteractionListener mListener;
//    private String [][] array= social_pref_interest.array;
    public GroupPool() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GroupPool.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupPool newInstance(String param1, String param2) {
        GroupPool fragment = new GroupPool();
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
        MainActivity.SetNavigationVisibiltity(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_group_pool, container, false);
        Button b1=v.findViewById(R.id.group_button_submit);
        ChipGroup get=(ChipGroup) v.findViewById(R.id.prefchipgroup);
        ViewGroup temp=(ViewGroup)get;
        Bundle bundle=getArguments();
        final String [] array=bundle.getStringArray("Interest");
        for(int j=0;j<array.length;j++){
            if(array[j]==null||array[j].length()==0){
                continue;
            }
            Chip temp1=new Chip(v.getContext());
            temp1.setBackgroundColor(getResources().getColor(R.color.mitiOrange));
            temp1.setText(array[j]);
            temp1.setCheckable(true);
            temp1.setLayoutParams(new ViewGroup.LayoutParams (WRAP_CONTENT,WRAP_CONTENT));
            int id;
            id= mitihelper.getuniqueid();
            hashMap.put(id,array[j]);
            temp1.setId(id);
            temp.addView(temp1);
        }
        get.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, @IdRes int checkedId) {
                // Handle the checked chip change.
                Mlog.e("Control", try123.randomAlphaNumeric(3),hashMap.get(checkedId));
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
    public void onAttach(Context context) {
        super.onAttach(context);
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
