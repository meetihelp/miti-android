package com.example.miti2.ui.login;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miti2.R;
import com.example.miti2.ui.database.Session;
import com.example.miti2.ui.database.SessionDao;
import com.example.miti2.ui.database.SessionDatabase;
import com.example.miti2.ui.utility.GetJsonObject;
import com.example.miti2.ui.utility.POSTRequest;
import com.example.miti2.ui.utility.RequestHelper;
import com.example.miti2.ui.utility.ToastHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RequestHelper requestHelper;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        final TextInputEditText phoneEditText=(TextInputEditText) v.findViewById(R.id.miti_login_input_text);
        final TextInputEditText passwordEditText=(TextInputEditText) v.findViewById(R.id.miti_password_input_text);
        Button button = v.findViewById(R.id.button2login);
        final SessionDatabase db=SessionDatabase.getInstance(v.getContext());
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do something
                String phone=phoneEditText.getText().toString();
                String password=passwordEditText.getText().toString();
                GetJsonObject getJsonObject=new GetJsonObject();
                String []key={"Phone","Password"};
                String []values={phone,password};
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
                    requestHelper=request.execute("login",data).get();
                    result=requestHelper.getData();
//                    Log.e("Control1",result);
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
                    if(value==200){
//                        InsertCookie(db,requestHelper.getMitiCookie());
                        Bundle bundle=new Bundle();
                        bundle.putInt("LoginToOTPCode",value);
                        Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_otpfragment2,bundle);
                    }
                    else if(value==1005){
                        //User is not verified
//                        InsertCookie(db,requestHelper.getMitiCookie());
                        Bundle bundle=new Bundle();
                        bundle.putInt("LoginToOTPCode",value);
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
                                Bundle bundle = new Bundle();
                                bundle.putInt("LoginToOTPCode", value);
                                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_otpfragment2, bundle);
                            }
                        }
//                        Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_otpfragment2);
                    }
                }
//                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_otpfragment2);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public RequestHelper Register(String data){
        POSTRequest request=new POSTRequest();
        String result;
        RequestHelper requestHelperTemp;
        try {
            Log.e("Control","Yahan");
            requestHelperTemp=request.execute("register",data).get();
            result=requestHelperTemp.getData();
            Log.e("Control1",result);
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
