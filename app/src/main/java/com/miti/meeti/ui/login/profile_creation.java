package com.miti.meeti.ui.login;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.miti.meeti.NetworkObjects.UserProfile;
import com.miti.meeti.R;
import com.miti.meeti.database.Cookie.CookieViewModel;
import com.miti.meeti.database.Keyvalue.KeyvalueViewModel;
import com.miti.meeti.database.Keyvalue.keyvalue;
import com.miti.meeti.mitiutil.network.RequestHelper;
import com.miti.meeti.mitiutil.uihelper.ToastHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link profile_creation.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link profile_creation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile_creation extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static View v1;
    private CookieViewModel cvm;
    private KeyvalueViewModel kvm;
    private OnFragmentInteractionListener mListener;
    private Spinner spinner;
    private Spinner spinner1;
    public profile_creation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profile_creation.
     */
    // TODO: Rename and change types and number of parameters
    public static profile_creation newInstance(String param1, String param2) {
        profile_creation fragment = new profile_creation();
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
        v1=inflater.inflate(R.layout.fragment_profile_creation, container, false);
        Button b=v1.findViewById(R.id.profile_submit_button);
        spinner = (Spinner) v1.findViewById(R.id.sex_spinner);
        spinner.setPrompt("Sex");
        spinner1 = (Spinner) v1.findViewById(R.id.gender_spinner);
        spinner1.setPrompt("Gender");
        cvm= ViewModelProviders.of(this).get(CookieViewModel.class);
        kvm= ViewModelProviders.of(this).get(KeyvalueViewModel.class);
        b.setOnClickListener(this);
        return v1;
    }
    @Override
    public void onClick(View v) {
        TextView t1=v1.findViewById(R.id.miti_p_name_text);
        String s1=t1.getText().toString().trim();
        if(s1.length()==0){
            ToastHelper.ToastFun(v.getContext(),"Please fill name");
            return;
        }
        t1=v1.findViewById(R.id.miti_p_dob_text);
        String s3=t1.getText().toString().trim();
        try{
            Date date1=new SimpleDateFormat("dd-MM-yyyy").parse(s3);
        }catch (Exception e){
            ToastHelper.ToastFun(v.getContext(),"Please fill dob in this format dd-MM-yyyy");
            return;
        }
        t1=v1.findViewById(R.id.miti_p_job_text);
        String s5=t1.getText().toString().trim();
        if(s5.length()==0){
            ToastHelper.ToastFun(v.getContext(),"Please fill profession");
            return;
        }
        Gson gson = new Gson();
        UserProfile.request_body temp=new UserProfile().new request_body(s1,s3,s5,spinner1.getSelectedItem().toString(),"English","India",
                spinner.getSelectedItem().toString());
        String jsonInString = gson.toJson(temp);
        RequestHelper requestHelper;
        ProfilePostRequest postRequest=new ProfilePostRequest();
        try{
            postRequest.execute("profileCreation",jsonInString,cvm.getCookie1());
            kvm.insert(new keyvalue("profileCreation",jsonInString));
        }catch (Exception e){
            ToastHelper.ToastFun(v.getContext(),e.toString());
        }

    }
    // TODO: Rename method, update argument and hook method into UI event


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
