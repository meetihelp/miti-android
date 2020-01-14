package com.miti.meeti.NetworkObjects;

import com.google.gson.annotations.SerializedName;
import com.miti.meeti.database.Chat.ChatDb;

import java.util.List;

public class SendChatContent {
    public class request_header{
        @SerializedName("Miti-Cookie") public String MitiCookie;
        public request_header(String MitiCookie){
            this.MitiCookie=MitiCookie;
        }
    }
    public class request_body{
        public String MessageType;
        public String MessageContent;
        public String ChatId;
        public String RequestId;
        public String CreatedAt;
        public request_body(String messageType, String messageContent,String chatId,String requestId,String createdAt){
            this.ChatId=chatId;
            this.MessageContent=messageContent;
            this.MessageType=messageType;
            this.RequestId=requestId;
            this.CreatedAt=createdAt;
        }
    }
    public class response_object{
        public int Code;
        public String Message;
        public String MessageId;
        public String CreatedAt;
        public String RequestId;
        public List<ChatDb>Chat;
    }
}
