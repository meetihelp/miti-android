package com.miti.meeti.NetworkObjects;

import com.google.gson.annotations.SerializedName;

public class FeedReaction {
    public class request_header{
        @SerializedName("Miti-Cookie") public String MitiCookie;
        public request_header(String MitiCookie){
            this.MitiCookie=MitiCookie;
        }
    }
    public class request_body{
        public int Id;
        public String Reaction;
        public request_body(int id,String reaction){
            this.Id=id;
            this.Reaction=reaction;
        }
    }
}
