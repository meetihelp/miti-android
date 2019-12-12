package com.example.miti2.ui.social.chat;

import com.stfalcon.chatkit.commons.models.IMessage;

import java.util.Date;

public class Message implements IMessage {

    /*...*/
    private String id,text;
    private Date createdAt;
    private Author author;
    public Message(String id, String text, Author author, Date createdAt){
        this.id=id;
        this.text=text;
        this.author=author;
        this.createdAt=createdAt;
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