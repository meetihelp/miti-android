package com.miti.meeti.MitiExecutors.MitiRunnables;

import com.miti.meeti.MainActivity;
import com.miti.meeti.database.Chat.ChatListDb;
import com.miti.meeti.database.Chat.ChatListDbViewModel;
import com.miti.meeti.database.Cookie.CookieViewModel;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.ui.social.chat.ChatListRequest;

public class UpdateChatlist implements Runnable {
    @Override
    public void run() {
        ChatListDbViewModel chatListDbViewModel=MainActivity.chatListDbViewModel;
        if(chatListDbViewModel==null){
            Mlog.e("chatlistDbviewmodel null in UpdateChatlist implements Runnable");
            return;
        }
        CookieViewModel cookieViewModel=MainActivity.cookieViewModel;
        if(cookieViewModel==null){
            Mlog.e("cookieDbviewmodel null in UpdateChatlist implements Runnable");
            return;
        }
        ChatListDb temp= chatListDbViewModel.getmax();
        new Mlog<ChatListDb>().e1(temp);
        if(temp==null){
            ChatListRequest.getinitial(cookieViewModel.getCookie1());
        }else{
            ChatListRequest.getlater(cookieViewModel.getCookie1(),temp.CreatedAt);
        }
        Mlog.e("UpdateChatlist called");
    }
}
