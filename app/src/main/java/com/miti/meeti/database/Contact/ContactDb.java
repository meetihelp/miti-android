package com.miti.meeti.database.Contact;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ContactDb")
public class ContactDb {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    public String Phone;
    public String Name;
    public int Status;
    public  String Requestid;
    public ContactDb(){}
    public ContactDb(String phone,String name,int status){
        this.Phone=phone;
        this.Name=name;
        this.Status=status;
    }
    public ContactDb(String phone,String name,int status,String tag,String requestid){
        this.Requestid=requestid;
        this.Phone=phone;
        this.Name=name;
        this.Status=status;
        this.Tag=tag;
    }
    public String Tag;
}
