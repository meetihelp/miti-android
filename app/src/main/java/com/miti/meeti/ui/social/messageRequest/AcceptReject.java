package com.miti.meeti.ui.social.messageRequest;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.miti.meeti.R;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.uihelper.MitiLoadingDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AcceptReject.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AcceptReject#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AcceptReject extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static FragmentActivity myContext;
    private static View v;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String phone;
    private String name;
    private OnFragmentInteractionListener mListener;
    public static MitiLoadingDialog bottomSheetDialog;
    public AcceptReject() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AcceptReject.
     */
    // TODO: Rename and change types and number of parameters
    public static AcceptReject newInstance(String param1, String param2) {
        AcceptReject fragment = new AcceptReject();
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
        v=inflater.inflate(R.layout.fragment_accept_reject, container, false);
        Bundle bundle=getArguments();
        if(bundle!=null){
            phone=bundle.getString("phone");
            Mlog.e("onCreateView","phone",phone);
            name=bundle.getString("name");
        }
        MitiLoadingDialog mitiLoadingDialog=new MitiLoadingDialog();
        Button accept=v.findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                bottomSheetDialog = MitiLoadingDialog.newInstance();
                bottomSheetDialog.show(myContext.getSupportFragmentManager(),"miti");
                AcceptRejectPOST.helper(phone,"Accept");
            }
        });
        Button reject=v.findViewById(R.id.reject);
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = MitiLoadingDialog.newInstance();
                bottomSheetDialog.show(myContext.getSupportFragmentManager(),"miti");
                AcceptRejectPOST.helper(phone,"Reject");
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
        myContext=(FragmentActivity)context;
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
