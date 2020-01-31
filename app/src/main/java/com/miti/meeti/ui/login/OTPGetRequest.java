package com.miti.meeti.ui.login;

import com.google.gson.Gson;
import com.miti.meeti.NetworkObjects.GenericResponse;
import com.miti.meeti.mitiutil.network.GETRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;
import com.miti.meeti.mitiutil.uihelper.ToastHelper;

public class OTPGetRequest extends GETRequest {
    @Override
    protected void onPostExecute(RequestHelper result) {
        String data=result.getData();
        GenericResponse gr=new Gson().fromJson(data,GenericResponse.class);
        if(gr==null){
            ToastHelper.ToastFun(otpfragment.v.getContext(),"Try again");
        }else{
            ToastHelper.ToastFun(otpfragment.v.getContext(),gr.Message);
        }
    }
}
