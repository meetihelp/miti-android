package com.miti.meeti.NetworkObjects;

import com.miti.meeti.database.Chat.ChatDb;

import java.util.List;

public class ChatUploadResponse {
    public int Code;
    public String Message;
    public String Messageid;
    public String ImageId;
    public String URL;
    public String RequestId;
    public String CreatedAt;
    public List<ChatDb>Chat;
}
