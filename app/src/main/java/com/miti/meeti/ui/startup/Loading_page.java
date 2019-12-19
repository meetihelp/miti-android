package com.miti.meeti.ui.startup;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miti.meeti.R;
import com.miti.meeti.database.Cookie.CookieDatabase;
import com.miti.meeti.mitiutil.network.GETRequest;
import com.miti.meeti.mitiutil.network.GetJsonObject;
import com.miti.meeti.mitiutil.network.RequestHelper;

import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Loading_page.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Loading_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Loading_page extends Fragment implements Runnable{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View vx;
    private CookieDatabase db;
    private String MeetiCookie;
    private Handler mWaitHandler = new Handler();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Loading_page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Loading_page.
     */
    // TODO: Rename and change types and number of parameters
    public static Loading_page newInstance(String param1, String param2) {
        Loading_page fragment = new Loading_page();
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
        final View v=inflater.inflate(R.layout.fragment_loading_page, container, false);
        this.vx=v;
        mWaitHandler.post(this);
        return v;
    }
    @Override
    public void run(){
        db=CookieDatabase.getAppDatabase(this.vx.getContext());
        String[] data=db.cookieDao().getCookie();
        if(data.length>0) {
            MeetiCookie = data[data.length - 1];
        }else{
            MeetiCookie="";
        }
        String jsonData=getRequest(MeetiCookie);
        if(jsonData!=null) {
            GetJsonObject getJsonObject=new GetJsonObject();
            int code=getJsonObject.getIntValue(jsonData,"Code");
            Bundle bundle=new Bundle();
            bundle.putString("From","Loading");
            bundle.putInt("LoadingToOTPCode",code);
            if (code == 300) {
                //To newsfeed
                Navigation.findNavController(this.vx).navigate(R.id.action_loading_page_to_newsfeed,bundle);
            } else if (code == 2000) {
                //Login Page
                Navigation.findNavController(this.vx).navigate(R.id.action_loading_page_to_loginFragment,bundle);
            } else if (code == 2001) {
                //To OTP fragment
                Navigation.findNavController(this.vx).navigate(R.id.action_loading_page_to_otpfragment2,bundle);
            } else if (code == 2002) {
                //To profile page
                Navigation.findNavController(this.vx).navigate(R.id.action_loading_page_to_profile_page,bundle);
            } else if (code == 2003) {
                //To Preference page
                int Preference=getJsonObject.getIntValue(jsonData,"Preference");
                bundle.putInt("Preference",Preference);
                Navigation.findNavController(this.vx).navigate(R.id.action_loading_page_to_preference_page,bundle);
            }
        }
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

    public String getRequest(String MeetiCookie){
        try {
            GETRequest getRequest=new GETRequest();
            Log.e("Control","Get Request enter");
            RequestHelper requestHelper=getRequest.execute("/loadingPage",MeetiCookie).get();
            Log.e("Control","Get Request exit");
            String data=requestHelper.getData();
            Log.e("Control",requestHelper.toString());
            return data;
//            GetJsonObject getJsonObject=new GetJsonObject();
//            int code=getJsonObject.getIntValue(data,"Code");
//            return code;
        } catch (ExecutionException e) {
            Log.e("Control",e.toString());
            return null;
        } catch (InterruptedException e) {
            Log.e("Control",e.toString());
            return null;
        }
//        RequestHelper requestHelper=getRequest.execute(MeetiCookie);
    }
}
