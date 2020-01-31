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
        String Sex;
        String Email;
        public request_body(String name,String dateOfBirth,String job, String gender,String language
        ,String country,String sex,String email){
            this.Name=name;
            this.DateOfBirth=dateOfBirth;
            this.Job=job;
            this.Gender=gender;
            this.Language=language;
            this.Country=country;
            this.Sex=sex;
            this.Email=email;
        }
    }
    public class response_body{
        String Country;
        String Message;
    }
}
