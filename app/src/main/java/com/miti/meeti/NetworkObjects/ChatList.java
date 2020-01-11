package com.miti.meeti.NetworkObjects;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatList {
    public class request_header{
        @SerializedName("Miti-Cookie") public String MitiCookie;
        public request_header(String MitiCookie){
            this.MitiCookie=MitiCookie;
        }
    }
    public class request_body{
        public String CreatedAt;
        public int NumOfChat;
        public request_body(String createdAt,int numOfChat){
            this.CreatedAt=createdAt;
            this.NumOfChat=numOfChat;
        }
    }
    public class chatlist_object{
        public int Index;
        public String TempUserId;
        public String ActualUserId;
        public String ChatId;
        public String ChatType;
        public String CreatedAt;
        public String LastUpdate;
    }
    public class response_object{
        public int Code;
        public String Message;
        public List<ChatList.chatlist_object> ChatDetail;
    }
}
