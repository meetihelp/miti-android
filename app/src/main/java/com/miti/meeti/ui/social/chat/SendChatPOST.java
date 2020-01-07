package com.miti.meeti.ui.social.chat;

import com.google.gson.Gson;
import com.miti.meeti.NetworkObjects.SendChatContent;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;

public class SendChatPOST extends POSTRequest {
    @Override
    protected void onPostExecute(RequestHelper result) {
        Gson gson=new Gson();
        String output=result.getData();
        SendChatContent.response_object temp=gson.fromJson(output,SendChatContent.response_object.class);
        if(temp==null){
            Mlog.e("SendchatPOST ka response null");
            return;
        }
        if(temp.Code==200){
            social_chat_content.chatDbViewModel.synced(temp.RequestId,temp.CreatedAt,temp.MessageId);
        }else{
            Mlog.e("Request failed for send chat");
        }
    }
}
