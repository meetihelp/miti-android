package com.miti.meeti.MitiExecutors.MitiRunnables;

import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.GetChatContent;
import com.miti.meeti.database.Chat.ChatListDb;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.ui.social.chat.ChatContentRequest;

import java.util.List;

public class UpdateChatMessages implements Runnable{
    @Override
    public void run() {
        List<ChatListDb>temp= MainActivity.chatListDbViewModel.getold();
        for(ChatListDb tempx:temp){
            Mlog.e("IN UpdateChatMEssages",tempx.ChatId,"");
            String datetime=MainActivity.chatDbViewModel.getmax(tempx.ChatId);
            ChatContentRequest.getmessage(new GetChatContent().new request_body(tempx.ChatId,10,datetime),MainActivity.cookieViewModel.getCookie1());
            try{
                Thread.sleep(2000);
            }catch (Exception e){

            }
        }
    }
}
