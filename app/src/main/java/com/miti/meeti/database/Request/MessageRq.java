package com.miti.meeti.database.Request;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.miti.meeti.mitiutil.try123;

@Entity(tableName = "MessageRq")
public class MessageRq {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    public String RequestId;
    public String CreatedAt;
    public String UserCreatedAt;
    public String ImageId;
    public String Name;
    public String Phone;
    public String Tag;
    public String MessageType;
    public String MessageContent;
    public Integer Sync;
    public static MessageRq setImage(String phone,String name,String messageContent){
        MessageRq temp=new MessageRq();
        temp.Phone=phone;
        temp.Name=name;
        temp.RequestId=try123.randomAlphaNumeric(32);
        temp.MessageContent=messageContent;
        temp.UserCreatedAt= try123.mitidt();
        temp.MessageType="image";
        temp.Sync=-1;
        temp.Tag="sent";
        return temp;
    }
    public static MessageRq setText(String phone,String name,String messageContent){
        MessageRq temp=new MessageRq();
        temp.Phone=phone;
        temp.Name=name;
        temp.MessageContent=messageContent;
        temp.MessageType="text";
        temp.UserCreatedAt= try123.mitidt();
        temp.RequestId=try123.randomAlphaNumeric(32);
        temp.Sync=-1;
        temp.Tag="sent";
        return temp;
    }
}
