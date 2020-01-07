package com.miti.meeti.database.Keyvalue;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MeetiKV")
public class keyvalue {
    @PrimaryKey
    @NonNull
    public String mitikey;
    public String mitivalue;
    public keyvalue(){}
    public keyvalue(String key,String value){
        this.mitivalue=value;
        this.mitikey=key;
    }
    public String sync;
}
