package com.miti.meeti.ui.social.chat;

import com.miti.meeti.MainActivity;
import com.miti.meeti.database.Chat.ChatDb;
import com.miti.meeti.database.Keyvalue.keyvalue;
import com.miti.meeti.mitiutil.try123;
import com.miti.meeti.mitiutil.uihelper.ImageSaver;

public class ChatSaveImage extends ImageSaver {
    @Override
    protected void onPostExecute(String s) {
        MainActivity.chatDbViewModel.insert(new ChatDb(social_chat_content.userid,
                social_chat_content.chatid,"image",s, try123.randomAlphaNumeric(32)));
    }
}
