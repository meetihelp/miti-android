package com.miti.meeti.NetworkObjects;

import com.google.gson.annotations.SerializedName;

public class GetProfile {
    public class request_header{
        @SerializedName("Miti-Cookie") public String MitiCookie;
        public request_header(String MitiCookie){
            this.MitiCookie=MitiCookie;
        }
    }
    public class request_body{
        public String UserId;
        public request_body(String userid){
            this.UserId=userid;
        }
    }
    public class profile_object{
        public String Name;
        public String DateOfBirth;
        public String Job;
        public String ProfilePicID;
        public String Gender;
        public String Language;
        public String Country;
        public String Sex;
        public String RelationshipStatus;
        public String InterestIndoorPassive1;
        public String InterestIndoorPassive2;
        public String InterestIndoorActive1;
        public String InterestIndoorActive2;
        public String InterestOutdoorPassive1;
        public String InterestOutdoorPassive2;
        public String InterestOutdoorActive1;
        public String InterestOutdoorActive2;
        public String InterestOthers1;
        public String InterestOthers2;
        public String InterestIdeology1;
        public String InterestIdeology2;
    }
    public class response_object{
        public int Code;
        public String Message;
        public profile_object ProfileResponse;
    }
}
