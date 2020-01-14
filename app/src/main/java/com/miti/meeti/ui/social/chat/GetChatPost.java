package com.miti.meeti.ui.social.chat;

import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.GetChatContent;
import com.miti.meeti.database.Chat.ChatDb;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;
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
            if(tempcv.MessageContent.contains("image")){
                tempcv.Sync=-3;
            }
            tempxy.add(tempcv);
            Mlog.e("GetChatPost",tempx.CreatedAt);
            Mlog.e("GetChatPost",tempcv.CreatedAt);
        }
        MainActivity.chatDbViewModel.insert(tempxy.toArray(new ChatDb[tempxy.size()]));
    }
}
