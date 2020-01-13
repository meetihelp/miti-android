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
    public String Tag;
}
