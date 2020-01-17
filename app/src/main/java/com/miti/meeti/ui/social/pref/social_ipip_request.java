package com.miti.meeti.ui.social.pref;

import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;
import com.miti.meeti.mitiutil.uihelper.ToastHelper;

import java.util.List;

public class social_ipip_request extends POSTRequest {
    public class request{
        public Integer Page;
        public Integer IPIP1;
        public Integer IPIP2;
        public Integer IPIP3;
        public Integer IPIP4;
        public Integer IPIP5;
        public request(){}
        public request(Integer page,Integer ...ipip){
            this.Page=page;
            this.IPIP1=ipip[0];
            this.IPIP2=ipip[1];
            this.IPIP3=ipip[2];
            this.IPIP4=ipip[3];
            this.IPIP5=ipip[4];
        }
    }
    public class response{
        public Integer Code;
        public String Message;
    }
    public static RequestHelper helper(List<Integer> temp,Integer page){
        Gson gson=new Gson();
        String json=gson.toJson(new social_ipip_request().new request(page,temp.toArray(new Integer[temp.size()])));
        try{
            return new social_ipip_request().execute("updateIPIPResponse",json, MainActivity.MeetiCookie).get();
        }catch (Exception e){
            return null;
        }

    }
}
