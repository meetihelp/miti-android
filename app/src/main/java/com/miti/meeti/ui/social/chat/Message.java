package com.miti.meeti.ui.social.chat;

import android.net.Uri;

import com.miti.meeti.mitiutil.Logging.Mlog;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.MessageContentType;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements IMessage, MessageContentType.Image {

    /*...*/
    private String id,text;
    private Date createdAt;
    private Author author;
    private String Imageurl;
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
    public void setUrl(String url){
        Mlog.e("seturl",id,url,this.Imageurl);
        this.Imageurl=url;
    }
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
//        Mlog.e("gettextcaled","","");
        if(Imageurl!=null){
            return null;
        }
        Mlog.e("gettextcaled","",text);
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

    @Override
    public String getImageUrl() {
        if(this.Imageurl!=null){
            return this.Imageurl;
        }else{
            return null;
        }

    }
}