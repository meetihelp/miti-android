package com.miti.meeti.ui.social.chat;

import android.util.Log;

import com.google.gson.Gson;
import com.miti.meeti.NetworkObjects.AllUrl;
import com.miti.meeti.NetworkObjects.ChatList;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.RequestHelper;

import java.util.ArrayList;
import java.util.List;

public class ChatListRequest {
    public static void getinitial(String cookie){
        List<ChatList.chatlist_object> ret=new ArrayList<>();
        Gson gson = new Gson();
        ChatList.request_body temp=new ChatList().new request_body("",5);
        String jsonInString = gson.toJson(temp);
        ChatListPost postRequest=new ChatListPost();
        RequestHelper requestHelper;
        try{
            postRequest.execute(AllUrl.url_chat().get(5),jsonInString,cookie);
            Mlog.e("Control","returned");
        }catch (Exception e){
            Mlog.e("Control-chatlist->",e.toString());
        }
    }
    public static void getlater(String cookie,String createdAt){
        List<ChatList.chatlist_object> ret=new ArrayList<>();
        Gson gson = new Gson();
        ChatList.request_body temp=new ChatList().new request_body(createdAt,5);
        String jsonInString = gson.toJson(temp);
        ChatListPost postRequest=new ChatListPost();
        RequestHelper requestHelper;
        try{
            postRequest.execute(AllUrl.url_chat().get(5),jsonInString,cookie);
            Mlog.e("Control","returned");
        }catch (Exception e){
            Mlog.e("Control-chatlist->",e.toString());
        }
    }
}
