package com.miti.meeti.NetworkObjects;

public class GetImageUrl {

    public class request_body{
        public String ImageId;
        public request_body(String imageId){
            this.ImageId=imageId;
        }
    }
    public class response_body{
        public int Code;
        public String Message;
        public String ImageURL;
    }

}
