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
        MainActivity.chatDbViewModel.insert(new ChatDb(social_chat_content.userid,
                social_chat_content.chatid,"image",s, try123.randomAlphaNumeric(32),-1,try123.mitidt()));
    }
}
