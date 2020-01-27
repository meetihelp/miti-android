package com.miti.meeti.ui.social.chat;


import android.util.Log;

import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.ChatList;
import com.miti.meeti.database.Chat.ChatListDb;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;

import java.util.ArrayList;
import java.util.List;

public class ChatListPost extends POSTRequest {
    @Override
    protected void onPostExecute(RequestHelper result) {
//        super.onPostExecute(result);
        Gson temp=new Gson();
        //Log.e("Control-Chatlistpost",result.getData());
        try{
            List<ChatList.chatlist_object>temp1=temp.fromJson(result.getData(),ChatList.response_object.class).ChatDetail;
            if(temp1.size()==0){
                return;
            }
//             ChatList.response_object temp1=chat_list_helper.fromJson(result.getData(),ChatList.response_object.class);
            List<ChatListDb>temp3=new ArrayList<>();
            for(ChatList.chatlist_object temp2:temp1){
                ChatListDb temp32=temp.fromJson(temp.toJson(temp2),ChatListDb.class);
                Mlog.e("chatlistpost",temp32.UserId);
                Mlog.e("chatlistpost",temp32.UserId2);
                temp3.add(temp32);
            }
            MainActivity.chatListDbViewModel.insert(temp3.toArray(new ChatListDb[temp3.size()]));
//             Log.e("Control",temp1.Message);
//            Log.e("Control",Integer.toString(temp1.ChatDetail.size()));
//            Log.e("Control",temp1.ChatDetail.get(0).ActualUserId);

        }catch (Exception e){
            Log.e("Control",e.toString());
        }
    }
}
