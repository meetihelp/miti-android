package com.example.miti2.database.Session;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//@Entity(tableName = "session")
//public class Session {
//    @PrimaryKey(autoGenerate = true)
//    public int id;
//    @ColumnInfo(name="miticookie")
//    public String MitiCookie;
//
//    public Session(String MitiCookie){
//        this.MitiCookie=MitiCookie;
//    }
//
//}
@Entity(tableName = "sessionapoorva")
public class Session {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
