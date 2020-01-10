package com.miti.meeti.NetworkObjects;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedNews {
    public class request_header{
        @SerializedName("Miti-Cookie") public String MitiCookie;
        public request_header(String MitiCookie){
            this.MitiCookie=MitiCookie;
        }
    }
    public class request_body{
        public int Id;
        public request_body(int id){
            this.Id=id;
        }
    }
    public class news_object{
        public int Id;
        public String Article;
    }
    public class response_object{
        public int Code;
        public String Message;
        public news_object Response;
    }
}
