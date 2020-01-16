package com.miti.meeti.database.Contact;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ContactDb")
public class ContactDb {
    @PrimaryKey
    @NonNull
    public String Phone;
    public String Name;
    public int Status;
    public ContactDb(){}
    public ContactDb(String phone,String name,int status){
        this.Phone=phone;
        this.Name=name;
        this.Status=status;
    }
    public String Tag;
}
