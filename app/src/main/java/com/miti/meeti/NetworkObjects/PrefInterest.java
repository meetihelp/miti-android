package com.miti.meeti.NetworkObjects;

import com.google.gson.annotations.SerializedName;

public class PrefInterest {
    public class request_header{
        @SerializedName("Miti-Cookie") public String MitiCookie;
        public request_header(String MitiCookie){
            this.MitiCookie=MitiCookie;
        }
    }
    public class request_object{
        public String I1;
        public String I2;
        public request_object(String I1,String I2){
            this.I1=I1;this.I2=I2;
        }
    }
    public class response_object{
        public int Code;
        public String Message;
    }
}
