package com.miti.meeti.ui.social.chat;

import com.miti.meeti.mitiutil.Logging.Mlog;
import com.stfalcon.chatkit.commons.models.IMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements IMessage {

    /*...*/
    private String id,text;
    private Date createdAt;
    private Author author;
    public Message(String id, String text, Author author, String createdAt){
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.id=id;
        this.text=text;
        this.author=author;
        try{
            this.createdAt=format1.parse(createdAt);
        }catch (Exception e){
            Mlog.e("Message.java->",e);
        }
    }
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Author getUser() {
        return author;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }
}