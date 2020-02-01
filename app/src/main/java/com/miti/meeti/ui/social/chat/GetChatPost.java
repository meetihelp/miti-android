package com.miti.meeti.ui.social.chat;

import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.GetChatContent;
import com.miti.meeti.database.Chat.ChatDb;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;
import com.miti.meeti.mitiutil.try123;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GetChatPost extends POSTRequest {
    @Override
    protected void onPostExecute(RequestHelper result) {
        if(result==null){
            return;
        }
        String response=result.getData();
//        Mlog.e("Getchatpost ka onpostexecute","",response);
        Gson gson=new Gson();
        GetChatContent.response_object response_object=gson.fromJson(response,GetChatContent.response_object.class);
        if(response_object==null){
            Mlog.e("Getchatpost ka onpostexecute","gson response object null",response);
            return;
        }
        List<GetChatContent.chat_object>messages=response_object.Chat;
        if(messages==null){
            Mlog.e("GetChatPost","gson returned nul");
            return;
        }
        if(messages.size()==0){
            return;
        }
        List<ChatDb>tempxy=new ArrayList<>();
        for(GetChatContent.chat_object tempx:messages){
            String json=gson.toJson(tempx);
            ChatDb tempcv=gson.fromJson(json,ChatDb.class);
            tempcv.Sync=1;
            Mlog.e("GetChatPost0",tempcv.MessageType);
            if(tempcv.MessageType.contains("image")){
                Mlog.e("GetChatPost1","inside if");
                tempcv.Sync=-3;
            }else{
                Mlog.e("andar hoon main");
                MainActivity.chatListDbViewModel.updatelast(tempcv.ChatId,tempcv.CreatedAt,tempcv.MessageContent);
            }
            tempcv.UserCreatedAt= try123.mitidt();
            tempxy.add(tempcv);
            Mlog.e("GetChatPost2",tempx.CreatedAt);
            Mlog.e("GetChatPost3",tempcv.CreatedAt);
        }
        MainActivity.chatDbViewModel.insert(tempxy.toArray(new ChatDb[tempxy.size()]));
    }
}
