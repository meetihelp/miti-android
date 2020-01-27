package com.miti.meeti.ui.login;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.miti.meeti.R;
import com.miti.meeti.database.Cookie.Cookie;
import com.miti.meeti.database.Cookie.CookieViewModel;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.GetJsonObject;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;
import com.miti.meeti.mitiutil.uihelper.ToastHelper;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class LoginFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RequestHelper requestHelper;
//    private CookieDatabase db;
    private String MeetiCookie;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextInputEditText phoneEditText;
    private TextInputEditText passwordEditText;
    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View v=inflater.inflate(R.layout.fragment_login, container, false);
        phoneEditText=(TextInputEditText) v.findViewById(R.id.miti_login_input_text);
        Button button = v.findViewById(R.id.button2login);
//        final SessionDatabase db=SessionDatabase.getInstance(v.getContext());
//         db=CookieDatabase.getAppDatabase(v.getContext());
//         db.cookieDao().nukeTable();
        button.setOnClickListener(this);
        return v;
    }
    @Override
    public void onClick(View v) {
        String phone=phoneEditText.getText().toString();
        if(phone.length()!=10){
            ToastHelper.ToastFun(v.getContext(),"Length of phone number not 10");
            return;
        }
        GetJsonObject getJsonObject=new GetJsonObject();
        String []key={"Phone"};
        String []values={phone};
        String data="";
        try {
            data=getJsonObject.getJson(key,values);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        POSTRequest request=new POSTRequest();
        String result;
        try {
            Mlog.e("Control","Yahan");
            requestHelper=request.execute("login",data).get();
            result=requestHelper.getData();
            MeetiCookie=requestHelper.getMitiCookie();
        } catch (ExecutionException e) {
            result=null;
            e.printStackTrace();
        } catch (InterruptedException e) {
            result=null;
            e.printStackTrace();
        }
        if(result!=null) {
            int value = getJsonObject.getIntValue(result, "Code");
            String Message=getJsonObject.getStringValue(result,"Message");
            CookieViewModel cookieViewModel= ViewModelProviders.of(this).get(CookieViewModel.class);
            Cookie temp=new Cookie();
            temp.setMeetiCookie(MeetiCookie);
            cookieViewModel.insert(temp);
//            CookieRepository.populateAsync(db,MeetiCookie);
            Bundle bundle=new Bundle();
            bundle.putInt("LoginToOTPCode",value);
            bundle.putString("From","Login");
            if(value==200){
//                        InsertCookie(db,requestHelper.getMitiCookie());
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_otpfragment2,bundle);
            }
            else if(value==1005){
                //User is not verified
//                        InsertCookie(db,requestHelper.getMitiCookie());

                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_otpfragment2,bundle);
            }else if(value==1002){
                ToastHelper.ToastFun(v.getContext(),Message);
            }
            else if(value==1501){
                //No such user, Register the user
                RequestHelper requestHelperRegister;
                requestHelperRegister=Register(data);
                if(requestHelperRegister!=null) {
                    String dataTemp=requestHelperRegister.getData();
                    GetJsonObject getJsonObjectTemp=new GetJsonObject();
                    int registerCode=getJsonObject.getIntValue(dataTemp,"code");
                    if (registerCode == 200) {
//                                InsertCookie(db,requestHelperRegister.getMitiCookie());
                        Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_otpfragment2, bundle);
                    }
                }
//                        Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_otpfragment2);
            }
        }
//                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_otpfragment2);
//        Navigation.findNavController(v).navigate(R.id.action_profile_creation_to_social_pref_interest2);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public RequestHelper Register(String data){
        POSTRequest request=new POSTRequest();
        String result;
        RequestHelper requestHelperTemp;
        try {
            Mlog.e("Control","Yahan");
            requestHelperTemp=request.execute("register",data).get();
            result=requestHelperTemp.getData();
            Mlog.e("Control1",result);
        } catch (ExecutionException e) {
            result=null;
            e.printStackTrace();
        } catch (InterruptedException e) {
            result=null;
            e.printStackTrace();
        }
        return  null;
    }

//    public void InsertCookie(SessionDatabase db,String MitiCookie){
//
//       SessionDatabase.SessionDatabaseInsert database= new SessionDatabase.SessionDatabaseInsert(db);
//       database.execute(MitiCookie);
//    }
}
