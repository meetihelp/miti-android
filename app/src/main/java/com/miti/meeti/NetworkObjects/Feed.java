package com.miti.meeti.NetworkObjects;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Feed {
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
    public class feed_object{
        public int Id;
        public String Summary;
        public String Sentiment;
        public String Location;
        public String Event;
        public String Label;
        public String Title;
    }
    public class response_object{
        public int Code;
        public String Message;
        public List<feed_object>NewsData;
    }
}
