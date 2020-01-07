package com.miti.meeti.NetworkObjects;

import com.google.gson.annotations.SerializedName;

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
        public request_body(String messageType, String messageContent,String chatId,String requestId){
            this.ChatId=chatId;
            this.MessageContent=messageContent;
            this.MessageType=messageType;
            this.RequestId=requestId;
        }
    }
    public class response_object{
        public int Code;
        public String Message;
        public String MessageId;
        public String CreatedAt;
        public String RequestId;
    }
}
