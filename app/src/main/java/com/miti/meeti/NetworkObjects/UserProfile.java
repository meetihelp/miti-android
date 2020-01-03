package com.miti.meeti.NetworkObjects;

import com.google.gson.annotations.SerializedName;

public class UserProfile {
    public class request_header{
        @SerializedName("Miti-Cookie") public String MitiCookie;
        public request_header(String MitiCookie){
            this.MitiCookie=MitiCookie;
        }
    }
    public class request_body{
        String Name;
        String DateOfBirth;
        String Job;
        String Gender;
        String Language;
        String Country;
        public request_body(String name,String dateOfBirth,String job, String gender,String language
        ,String country){
            this.Name=name;
            this.DateOfBirth=dateOfBirth;
            this.Job=job;
            this.Gender=gender;
            this.Language=language;
            this.Country=country;
        }
    }
    public class response_body{
        String Country;
        String Message;
    }
}
