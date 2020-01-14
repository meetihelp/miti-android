package com.miti.meeti.NetworkObjects;

public class SendBoard {
    public class request_body{
        public String BoardId;
        public String RequestId;
        public String Date;
        public String ContentText;
        public String ContentImageId;
        public request_body(){}
        public request_body(String BoardId,String RequestId,String Date,String ContentText,String ContentImageId){
            this.BoardId=BoardId;
            this.ContentImageId=ContentImageId;
            this.Date=Date;
            this.RequestId=RequestId;
            this.ContentText=ContentText;
        }
    }
    public class response_body{
        public int Code;
        public String Message;
        public String CreatedAt;
        public String RequestId;
        public String BoardId;
        public String ContentId;
    }
}
