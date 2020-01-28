package com.miti.meeti.ui.social;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.miti.meeti.MainActivity;
import com.miti.meeti.R;
import com.miti.meeti.database.Keyvalue.KeyvalueViewModel;
import com.miti.meeti.database.Keyvalue.keyvalue;
import com.miti.meeti.mitiutil.uihelper.InfoDialog;
import com.miti.meeti.mitiutil.uihelper.MitiLoadingDialog;
import com.miti.meeti.ui.privacy.MoodboardAdapter;
import com.miti.meeti.ui.social.pooling.GetPoolStatus;
import com.miti.meeti.ui.social.pooling.GroupPoolStatus;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SocialFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SocialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SocialFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private OnFragmentInteractionListener mListener;
    public static FragmentActivity myContext;
    public static MitiLoadingDialog bottomSheetDialog;
    public static View v;
    public static TextView tempd;
    private ImageButton poolingHelp;
    public SocialFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        myContext=(FragmentActivity) context;
        super.onAttach(context);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SocialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SocialFragment newInstance(String param1, String param2) {
        SocialFragment fragment = new SocialFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.toolbar_text.setText("Social");
        MainActivity.SetNavigationVisibiltity(false);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_social, container, false);
        KeyvalueViewModel keyvalueViewModel= MainActivity.keyvalueViewModel;
        tempd=v.findViewById(R.id.pooling_status);
        poolingHelp=v.findViewById(R.id.help);
        poolingHelp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String help="Dating Pool :- This is new. So it's a weekly pool of people who want to date. As week starts to end, if you are matched with someone they will be added in your chat. Talk and findout.\n\n";
                String help1="Group Pool :- Meet people with same mission and interest near you. Get in a new pool according to your interest if your location changes";
                InfoDialog inf=new InfoDialog(help+help1);
                inf.show(myContext.getSupportFragmentManager(),"hithere");
            }
        });
        keyvalue temp=keyvalueViewModel.get("pooling");
        if(temp==null){
            tempd=v.findViewById(R.id.pooling_status);
            tempd.setText("Status:  "+"Not Yet Joined");
        }else{
            tempd.setText("Status:  "+temp.mitivalue);
        }
        Button button1 = v.findViewById(R.id.dating_pool);
        Button button2 = v.findViewById(R.id.group_pool);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                bottomSheetDialog = MitiLoadingDialog.newInstance();
                bottomSheetDialog.show(myContext.getSupportFragmentManager(),"hithere");
                GroupPoolStatus.helper();
            }
        });
        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                bottomSheetDialog = MitiLoadingDialog.newInstance();
                bottomSheetDialog.show(myContext.getSupportFragmentManager(),"hithere");
                GetPoolStatus.helper();
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
        MainActivity.toolbar_text.setText("MEETi");
        MainActivity.SetNavigationVisibiltity(true);
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
