package com.miti.meeti.ui.social.chat;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.miti.meeti.NetworkObjects.GetChatContent;
import com.miti.meeti.NetworkObjects.SendChatContent;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;

public class ChatContentRequest {
    public static void sendmessage(@NonNull SendChatContent.request_body message,@NonNull String cookie){
        if(cookie==null){
            Mlog.e("ChatContentRequest->sendmessage","cookie null");
            return;
        }else{
            Mlog.e("ChatContentRequest->sendmessage","cookie size",Integer.toString(cookie.length()));
        }
        Gson gson=new Gson();
        String jsonInString = gson.toJson(message);
        SendChatPOST postRequest=new SendChatPOST();
        try{
            postRequest.execute("chat",jsonInString,cookie);
        }catch(Exception e){
            Mlog.e("ChatContentRequest->sendmessage",e.toString());
        }
    }
    public static void getmessage(@NonNull GetChatContent.request_body body,@NonNull String cookie){
        if(cookie==null){
            Mlog.e("ChatContentRequest->getmessage","cookie null");
            return;
        }else{
            Mlog.e("ChatContentRequest->getmessage","cookie size",Integer.toString(cookie.length()));
        }
        Gson gson=new Gson();
        String jsonInString = gson.toJson(body);
        Mlog.e("Chatcontentrequest->",jsonInString);
        GetChatPost postRequest=new GetChatPost();
        try{
            postRequest.execute("getChatAfterIndex",jsonInString,cookie);
//            Log.e("Control-SendChat","requestHelper.getData()");
        }catch(Exception e){

        }
    }
}
