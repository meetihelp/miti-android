package com.miti.meeti.database.Keyvalue;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "MeetiKV",indices = {@Index(value = "miti_key")})
public class keyvalue {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="miti_key")
    public String mitikey;
    public String mitivalue;
    public keyvalue(){}
    public keyvalue(String key,String value){
        this.mitivalue=value;
        this.mitikey=key;
    }
    public String sync;
}
