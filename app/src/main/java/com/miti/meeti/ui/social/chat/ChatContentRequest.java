package com.miti.meeti.ui.social.chat;

import android.util.Log;

import com.google.gson.Gson;
import com.miti.meeti.NetworkObjects.GetChatContent;
import com.miti.meeti.NetworkObjects.SendChatContent;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;

public class ChatContentRequest {
    public static void sendmessage(SendChatContent.request_body message){
        Gson gson=new Gson();
        String jsonInString = gson.toJson(message);
        POSTRequest postRequest=new POSTRequest();
        try{
            RequestHelper requestHelper= postRequest.execute("chat",jsonInString,"558eca4e-0475-4164-47e5-a720a4b55119").get();
            Log.e("Control-SendChat",requestHelper.getData());
        }catch(Exception e){

        }
    }
    public static void getmessage(GetChatContent.request_body body){
        Gson gson=new Gson();
        String jsonInString = gson.toJson(body);
        GetChatPost postRequest=new GetChatPost();
        try{
            postRequest.execute("getChatAfterIndex",jsonInString,"558eca4e-0475-4164-47e5-a720a4b55119");
            Log.e("Control-SendChat","requestHelper.getData()");
        }catch(Exception e){

        }
    }
}
