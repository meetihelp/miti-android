package com.miti.meeti.ui.login;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.miti.meeti.NetworkObjects.OTP;
import com.miti.meeti.R;
import com.miti.meeti.database.Cookie.CookieViewModel;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.GETRequest;
import com.miti.meeti.mitiutil.network.GetJsonObject;
import com.miti.meeti.mitiutil.network.Keyvalue;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.miti.meeti.mitiutil.uihelper.ToastHelper;

import org.json.JSONException;

import java.util.HashMap;
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
    private CookieViewModel cookieViewModel;
//    private CookieDatabase db;
    private String MeetiCookie;
    private int LoginToOTPCode;
    private int LoadingToOTPCode;
    private String OTPGenerationStatus;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    HashMap<String,String>MessageDic=Keyvalue.GetHashMap(new Keyvalue("3000","Maximum Fail count reached"),
            new Keyvalue("3001","Maximum Resend count reached"));
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View v1;
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
        this.v1=v;
        otpEditText=(TextInputEditText) v.findViewById(R.id.otp);
        cookieViewModel= ViewModelProviders.of(this).get(CookieViewModel.class);
        GETRequest k=new GETRequest();
        k.execute(Keyvalue.GetHashMap(new Keyvalue("url","/generateOTP"),
                new Keyvalue("Miti-Cookie",cookieViewModel.getCookie1())));
//        OTPGenerationStatus=RequestOTPStatus();
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
        String ret=MessageDic.get(OTPGenerationStatus);
        if(ret!=null){
            ToastHelper.ToastFun(v.getContext(),ret);
            return;
        }
        String otp=otpEditText.getText().toString();
        if(otp.length()!=5){
            ToastHelper.ToastFun(v.getContext(),"Otp length should be 5");
            return;
        }
        OTP.response_object value1=SendOTP(otp);
        int moveTo=value1.MoveTo;
        int value=value1.Code;
        Mlog.e("Send otp ka return value-"+Integer.toString(value));
        if(moveTo==4){
            Navigation.findNavController(v1).navigate(R.id.action_otpfragment2_to_profile_creation);
        }else if(moveTo==5){
            Bundle lk=new Bundle();
            lk.putInt("Preference",value1.Preference);
            Navigation.findNavController(v1).navigate(R.id.action_otpfragment2_to_social_pref_interest2,lk);
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

    private OTP.response_object SendOTP(String otp){
        Gson gson=new Gson();
        OTP.response_object temp;
        String data=gson.toJson(new OTP().new request_object(otp));
        Log.e("Control","OTPDATA->"+data);
        POSTRequest request=new POSTRequest();
        String result;
        MeetiCookie=cookieViewModel.getCookie1();
        Log.e("Control->",MeetiCookie+","+data);
        try {
            Log.e("Control","Yahan");
            requestHelper=request.execute("verifyOTP",data,MeetiCookie).get();
            String temps=requestHelper.getMitiCookie();
            result=requestHelper.getData();
//                Log.e("Control1",result);
        } catch (ExecutionException e) {
            result=null;
            Mlog.e(e);
        } catch (InterruptedException e) {
            result=null;
            Mlog.e(e);
        }
        if(result!=null){
            temp=gson.fromJson(result,OTP.response_object.class);
            return temp;
        }
        return null;
    }

    private String RequestOTPStatus(){
        MeetiCookie=cookieViewModel.getCookie1();
        Log.e("Meeti->",MeetiCookie);
        GETRequest getRequest=new GETRequest();
        String otpgenerateResult;
        try {
            RequestHelper requestHelperTemp;
            requestHelperTemp=getRequest.execute(Keyvalue.GetHashMap(new Keyvalue("url","/otpStatus"),
                    new Keyvalue("Meeti-Cookie",MeetiCookie))).get();
            otpgenerateResult=requestHelperTemp.getData();
        } catch (ExecutionException e) {
            otpgenerateResult=null;
            e.printStackTrace();
        } catch (InterruptedException e) {
            otpgenerateResult=null;
            e.printStackTrace();
        }
        GetJsonObject getJsonObject=new GetJsonObject();
        int temp=getJsonObject.getIntValue(otpgenerateResult,"Code");
        return Integer.toString(temp);
    }
}
