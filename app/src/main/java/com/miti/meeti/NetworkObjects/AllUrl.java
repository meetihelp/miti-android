package com.miti.meeti.NetworkObjects;

import java.util.ArrayList;
import java.util.List;

public class AllUrl {
    private static String pr(String temp1,String temp2){
        return temp1+temp2;
    }
    public static List<String> url_newsfeed(){
        List<String>ret=new ArrayList<>();
        String p="";
        String t0="getNewsArticleList";
        ret.add(pr(p,t0));
        return ret;
    }
    public static List<String> url_chat(){
        List<String>ret=new ArrayList<>();
        String p="";

        String t0="chat";
        String t1="sendChatImage";
        String t2="getImageById";
        String t3="getMessageRequest";
        String t4="sendMessageRequest";

        String t5="getChatDetail";
        String t6="getProfile";
        String t7="getChatAfterIndex";
        ret.add(pr(p,t0));
        ret.add(pr(p,t1));
        ret.add(pr(p,t2));
        ret.add(pr(p,t3));
        ret.add(pr(p,t4));
        ret.add(pr(p,t5));
        ret.add(pr(p,t6));
        ret.add(pr(p,t7));
        return ret;
    }

    public static List<String> url_diary(){
        List<String>ret=new ArrayList<>();
        String p="";
        String t0="getBoardContent";
        String t1="uploadBoardContent";
        ret.add(pr(p,t0));
        ret.add(pr(p,t1));
        return ret;
    }
    public static List<String> url_security(){
        List<String>ret=new ArrayList<>();
        String p="";
        String t0="createPrimaryTrustChain";
        String t1="deletePrimaryTrustChain";
        ret.add(pr(p,t0));
        ret.add(pr(p,t1));
        return ret;
    }
    public static List<String> url_image(){
        List<String>ret=new ArrayList<>();
        String p="";
        String t0="uploadImage";
        String t1="getImageById";
        ret.add(pr(p,t0));
        ret.add(pr(p,t1));
        return ret;
    }
}
