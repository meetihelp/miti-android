package com.miti.meeti.ui.security;

import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.Mitigps;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;
import com.miti.meeti.mitiutil.uihelper.ToastHelper;

public class AlertPOST extends POSTRequest {
    public class response{
        public Integer Code;
        public String Message;
    }
    public static void helper1(){
        Gson gson=new Gson();
        Mlog.e("Alerpost",MainActivity.Latitude,MainActivity.Longitude);
        String req=gson.toJson(new Mitigps(MainActivity.Latitude,MainActivity.Longitude));
        AlertPOST temp=new AlertPOST();
        temp.execute("alertMessage",req,MainActivity.MeetiCookie);
    }

    @Override
    protected void onPostExecute(RequestHelper result) {
        if(result==null){
            helper1();
            return;
        }
        Gson gson=new Gson();
        response tempx=gson.fromJson(result.getData(),response.class);
        if(tempx==null){
            helper1();
            return;
        }
        if(tempx.Code==200){
//            ToastHelper.ToastFun(MainActivity.MainActivityContext,"Message Sent Successgully");
        }
    }
}
