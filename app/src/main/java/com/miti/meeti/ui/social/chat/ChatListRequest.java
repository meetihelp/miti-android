package com.miti.meeti.ui.social.chat;

import android.util.Log;

import com.google.gson.Gson;
import com.miti.meeti.NetworkObjects.ChatList;
import com.miti.meeti.mitiutil.network.RequestHelper;

import java.util.ArrayList;
import java.util.List;

public class ChatListRequest {
    public static List<ChatList.chatlist_object> getinitialnews(){
        List<ChatList.chatlist_object> ret=new ArrayList<>();
        Gson gson = new Gson();
        ChatList.request_body temp=new ChatList().new request_body(0,5);
        String jsonInString = gson.toJson(temp);
        ChatListPost postRequest=new ChatListPost();
        RequestHelper requestHelper;
        try{
            requestHelper= postRequest.execute("getChatDetail",jsonInString,"558eca4e-0475-4164-47e5-a720a4b55119").get();
            String result=requestHelper.getData();
            ChatList.response_object tempqw=gson.fromJson(result,ChatList.response_object.class);
            ret=tempqw.ChatDetail;
            Log.e("Control","returned");
            return ret;
        }catch (Exception e){
            Log.e("Control",e.toString());
        }
        return ret;
    }
}
