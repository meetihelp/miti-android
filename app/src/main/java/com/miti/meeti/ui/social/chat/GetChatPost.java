package com.miti.meeti.ui.social.chat;

import com.google.gson.Gson;
import com.miti.meeti.NetworkObjects.GetChatContent;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GetChatPost extends POSTRequest {
    @Override
    protected void onPostExecute(RequestHelper result) {
        String response=result.getData();
        Gson gson=new Gson();
        GetChatContent.response_object response_object=gson.fromJson(response,GetChatContent.response_object.class);
        List<GetChatContent.chat_object>messages=response_object.Chat;
        List<Message>temp12=new ArrayList<>();
        for (GetChatContent.chat_object tempx:messages){
            Author temp45=new Author(tempx.UserId,"","");
            Message temp34=new Message(tempx.MessageId,tempx.MessageContent,temp45,new Date());
            temp12.add(temp34);
        }
        social_chat_content.adapterx.addToEnd(temp12,false);
    }
}
