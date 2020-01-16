package com.miti.meeti.NetworkObjects;

import java.util.List;

public class ContactSync {
    public class phone_object{
        public String Phone;
        public phone_object(){}
        public phone_object(String phone){
            this.Phone=phone;
        }
    }
    public class request{
        public List<phone_object> PhoneList;
        public request(){}
        public request(List<phone_object> phoneList){
            this.PhoneList=phoneList;
        }
    }
    public class response{
        public List<Integer>PhoneStatus;
        public int Code;
        public  String Message;
    }
}
