package com.example.miti2.ui.login;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.miti2.R;
import com.example.miti2.mitiutil.network.GETRequest;
import com.example.miti2.mitiutil.network.GetJsonObject;
import com.example.miti2.mitiutil.network.POSTRequest;
import com.example.miti2.mitiutil.network.RequestHelper;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link otpfragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link otpfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class otpfragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    TextInputEditText otpEditText;
    private RequestHelper requestHelper;
    private String MitiCookie;
    private int LoginToOTPCode;
    private int OTPGenerationStatus;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public otpfragment() {
        // Required empty public constructor
    }

    /**<TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Yaha per profile data hoga" />
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment otpfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static otpfragment newInstance(String param1, String param2) {
        otpfragment fragment = new otpfragment();
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
        View v=inflater.inflate(R.layout.fragment_otpfragment, container, false);
//        SessionDatabase db=SessionDatabase.getInstance(v.getContext());
//        SessionDatabase.SessionDatabaseQuery database= new SessionDatabase.SessionDatabaseQuery(db);
//        try {
//            MitiCookie=database.execute().get();
//        } catch (ExecutionException e) {
//            Log.e("Otp fragment","cookie retrieve nahi hua");
//            MitiCookie="";
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            Log.e("Otp fragment","cookie retrieve nahi hua2");
//            MitiCookie="";
//            e.printStackTrace();
//        }
//        Log.e("MitiCookie", MitiCookie);
//        MitiCookie=db.sessionDao().getCookie();
        otpEditText=(TextInputEditText) v.findViewById(R.id.otp);
        LoginToOTPCode=getArguments().getInt("LoginToOTPCode");
        OTPGenerationStatus=RequestOTP();
        ImageButton ib=v.findViewById(R.id.resend_otp);
        Button b1=v.findViewById(R.id.otp_to_profile);
        b1.setOnClickListener(this);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            ib.setTooltipText("Resend Otp");
            // Do something for lollipop and above versions
        } else{
            // do something for phones running an SDK before lollipop
        }
        return v;
    }
    @Override
    public void onClick(View v) {
        String otp=otpEditText.getText().toString();
        if(LoginToOTPCode==200 && OTPGenerationStatus==200){
            int value=SendOTP(otp);
            if(value==200){
                Navigation.findNavController(v).navigate(R.id.action_otpfragment2_to_miti_feed);
            }
        }else if(LoginToOTPCode==1005 && OTPGenerationStatus==200){
            int value=SendOTP(otp);
            if(value==200){
                Navigation.findNavController(v).navigate(R.id.action_otpfragment2_to_profile_creation);
            }
        }else if(LoginToOTPCode==1501 && OTPGenerationStatus==200){
            int value=SendOTP(otp);
            if(value==200){
                Navigation.findNavController(v).navigate(R.id.action_otpfragment2_to_profile_creation);
            }
        }
//        Navigation.findNavController(v).navigate(R.id.action_otpfragment2_to_profile_creation);
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

    private int SendOTP(String otp){
        GetJsonObject getJsonObject=new GetJsonObject();
        String []key={"OTP"};
        String []values={otp};
        String data="";
        try {
            data=getJsonObject.getJson(key,values);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        POSTRequest request=new POSTRequest();
        String result;
        try {
            Log.e("Control","Yahan");

            requestHelper=request.execute("verifyOTPUserverification",data,MitiCookie).get();
            result=requestHelper.getData();
//                Log.e("Control1",result);
        } catch (ExecutionException e) {
            result=null;
            e.printStackTrace();
        } catch (InterruptedException e) {
            result=null;
            e.printStackTrace();
        }
        if(result!=null){
            int value = getJsonObject.getIntValue(result, "Code");
            String Message=getJsonObject.getStringValue(result,"Message");
            return value;
        }
        return 0;
    }

    private int RequestOTP(){
        MitiCookie="5917af48-649c-496a-7de5-fa5d7d5d85d0";
//        MitiCookie=requestHelper.getMitiCookie();

        GETRequest getRequest=new GETRequest();
        String otpgenerateResult;
        try {
            RequestHelper requestHelperTemp;
            requestHelperTemp=getRequest.execute("verifyUser",MitiCookie).get();
            otpgenerateResult=requestHelperTemp.getData();
        } catch (ExecutionException e) {
            otpgenerateResult=null;
            e.printStackTrace();
        } catch (InterruptedException e) {
            otpgenerateResult=null;
            e.printStackTrace();
        }
        GetJsonObject getJsonObject=new GetJsonObject();
        OTPGenerationStatus=getJsonObject.getIntValue(otpgenerateResult,"Code");
        return OTPGenerationStatus;
    }
}
