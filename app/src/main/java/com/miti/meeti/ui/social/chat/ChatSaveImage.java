package com.miti.meeti.ui.social.chat;

import com.miti.meeti.MainActivity;
import com.miti.meeti.database.Chat.ChatDb;
import com.miti.meeti.database.Keyvalue.keyvalue;
import com.miti.meeti.mitiutil.try123;
import com.miti.meeti.mitiutil.uihelper.ImageSaver;

public class ChatSaveImage extends ImageSaver {
    //String userId,String chatId,String messageType,String messageContent,String requestId,int sync, String userCreatedAt
    @Override
    protected void onPostExecute(String s) {
        ChatDb temp=chatdbhelper(social_chat_content.userid,social_chat_content.chatid,s);
        Message temp34=social_chat_content.chathelper(temp);
        social_chat_content.adapterx.addToStart(temp34,true);
        MainActivity.chatDbViewModel.insert(temp);
    }
    public static ChatDb chatdbhelper(String userid,String chatid,String s){
        ChatDb temp=new ChatDb(userid,chatid,"image",s, try123.randomAlphaNumeric(32),-1,try123.mitidt());
        return temp;
    }
}
