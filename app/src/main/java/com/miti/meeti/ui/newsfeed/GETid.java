package com.miti.meeti.ui.newsfeed;

import com.google.gson.Gson;
import com.miti.meeti.database.Keyvalue.keyvalue;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.GETRequest;
import com.miti.meeti.mitiutil.network.Keyvalue;
import com.miti.meeti.mitiutil.network.RequestHelper;

import java.util.concurrent.ExecutionException;

public class GETid extends GETRequest{
    public class response_object{
        int Code;
        String Message;
        String UserId;
    }
    public static void getid(String cookie){
        GETid getRequest=new GETid();
        String otpgenerateResult;
        RequestHelper requestHelperTemp;
        getRequest.execute(Keyvalue.GetHashMap(new Keyvalue("url","/getTemporaryUserId"),
                new Keyvalue("Miti-Cookie",cookie)));
    }

    @Override
    protected void onPostExecute(RequestHelper result) {
        Gson gson=new Gson();
        String res=result.getData();
        Mlog.e("GETid",res);
        GETid.response_object temp=gson.fromJson(res,GETid.response_object.class);
        if(temp==null){
            Mlog.e("null response GETid");
        }else{
            newfeed.kvm.insert(new keyvalue("userid",temp.UserId));
            Mlog.e("in GETid userid inserted ->",temp.UserId);
        }
    }
}
