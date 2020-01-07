package com.miti.meeti.ui.social.chat;

import com.miti.meeti.mitiutil.Logging.Mlog;
import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.ArrayList;
import java.util.Date;

public class DefaultDialog implements IDialog {

    /*...*/
    private  IMessage lastMessage;
    private String id,Name;
    public DefaultDialog(String mid,String mName){
        Mlog.e("Default Dialog",id);
        this.id=mid;
        this.Name="Ram-"+mid.substring(0,3);
    }
    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getDialogPhoto() {
        return "";
    }

    @Override
    public String getDialogName() {
        return this.Name;
    }

    @Override
    public ArrayList<IUser> getUsers() {
        ArrayList<IUser>temp=new ArrayList<>();
        temp.add(new Author("","",""));
        return temp;
    }

    @Override
    public IMessage getLastMessage() {
        IMessage temp=new Message("","",new Author(this.id,"apoorva",""),"");
        return temp;
    }

    @Override
    public void setLastMessage(IMessage lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public int getUnreadCount() {
        return 1;
    }
}