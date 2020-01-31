package com.miti.meeti.NetworkObjects;

import com.google.gson.annotations.SerializedName;
import com.miti.meeti.database.Feed.FeedDb;

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
        public String Label;
        public request_body(int id,String label){
            this.Id=id;
            this.Label=label;
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
        public String ImageURL;
        public String ArticeURL;
        public String Flag;
    }
    public class response_object{
        public int Code;
        public String Message;
        public List<FeedDb>NewsData;
    }
}
