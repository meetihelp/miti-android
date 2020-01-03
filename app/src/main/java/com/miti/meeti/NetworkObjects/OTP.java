package com.miti.meeti.NetworkObjects;

import com.google.gson.annotations.SerializedName;



public class OTP {
    public class request_header{
        @SerializedName("Miti-Cookie") public String MitiCookie;
        public request_header(String MitiCookie){
            this.MitiCookie=MitiCookie;
        }
    }
    public class request_object{
        public String OTP;
        public request_object(String otp){
            this.OTP=otp;
        }
    }
    public class response_object{
        public int Code;
        public String Message;
        public int MoveTo;
        public int Preference;
    }
}
