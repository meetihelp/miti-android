package com.miti.meeti.NetworkObjects;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetChatContent {
    public class request_header{
        @SerializedName("Miti-Cookie") public String MitiCookie;
        public request_header(String MitiCookie){
            this.MitiCookie=MitiCookie;
        }
    }
    public class request_body{
        public String ChatId;
        public int NumOfChat;
        public String CreatedAt;
        public request_body(String chatId,int numOfChat,String datetime){
            this.ChatId=chatId;
            this.CreatedAt=datetime;
            this.NumOfChat=numOfChat;
        }
    }
    public class chat_object{
        public String UserId;
        public String ChatId;
        public String MessageId;
        public String MessageType;
        public String MessageContent;
        public String CreatedAt;
        public int Index;
    }
    public class response_object{
        public int Code;
        public String Message;
        public List<chat_object>Chat;
    }
}
