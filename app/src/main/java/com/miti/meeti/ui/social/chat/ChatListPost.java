package com.miti.meeti.ui.social.chat;


import android.util.Log;

import com.google.gson.Gson;
import com.miti.meeti.NetworkObjects.ChatList;
import com.miti.meeti.database.Chat.ChatListViewModel;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;

import java.util.List;

public class ChatListPost extends POSTRequest {
    @Override
    protected void onPostExecute(RequestHelper result) {
//        super.onPostExecute(result);
        Gson temp=new Gson();
        Log.e("Control-Chatlistpost",result.getData());
        try{
            List<ChatList.chatlist_object>temp1=temp.fromJson(result.getData(),ChatList.response_object.class).ChatDetail;
//             ChatList.response_object temp1=temp.fromJson(result.getData(),ChatList.response_object.class);
            social_chat_list.chatListViewModel.setTodos(temp1);
//             Log.e("Control",temp1.Message);
//            Log.e("Control",Integer.toString(temp1.ChatDetail.size()));
//            Log.e("Control",temp1.ChatDetail.get(0).ActualUserId);

        }catch (Exception e){
            Log.e("Control",e.toString());
        }
    }
}
