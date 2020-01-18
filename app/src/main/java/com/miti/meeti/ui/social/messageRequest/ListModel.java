package com.miti.meeti.ui.social.messageRequest;


import com.miti.meeti.database.Chat.ChatListDb;
import com.miti.meeti.database.Contact.ContactDb;
import com.miti.meeti.mitiutil.try123;

public class ListModel {
    public String ChatId;
    public String Name;
    public String Phone;
    public ListModel(){}
    public ListModel(ChatListDb temp){
        this.ChatId=temp.ChatId;
        if(temp.Name==null||temp.Name.length()==0){
            this.Name= try123.getname(temp.ChatId);
        }else{
            this.Name=temp.Name;
        }
        this.Phone=null;
    }
    public ListModel(ContactDb temp){
        this.ChatId=null;
        this.Phone=temp.Phone;
        this.Name=temp.Name;
    }
}
