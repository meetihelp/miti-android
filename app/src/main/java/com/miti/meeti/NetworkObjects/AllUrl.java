package com.miti.meeti.NetworkObjects;

import java.util.ArrayList;
import java.util.List;

public class AllUrl {
    private static String pr(String temp1,String temp2){
        return temp1+temp2;
    }
    public static List<String> url_auth() {
        List<String> ret = new ArrayList<>();
        String p="auth/";
        String t0="login";
        String t1="verifyOTP";
        String t2="otpStatus";
        String t3="generateOTP";
        String t4="loadingPage";
        ret.add(pr(p,t0));
        ret.add(pr(p,t1));
        ret.add(pr(p,t2));
        ret.add(pr(p,t3));
        ret.add(pr(p,t4));
        return ret;
    }
    public static List<String> url_profile(){
        List<String> ret = new ArrayList<>();
        String p="profile/";
        String t0="profileCreation";
        String t1="updateIPIPResponse";
        String t2="updatePreference";
        String t3="getProfile";
        String t4="getTemporaryUserId";
        String t5="updateUserLocation";
        ret.add(pr(p,t0));
        ret.add(pr(p,t1));
        ret.add(pr(p,t2));
        ret.add(pr(p,t3));
        ret.add(pr(p,t4));
        ret.add(pr(p,t5));
        return ret;
    }
    public static List<String> url_newsfeed(){
        List<String>ret=new ArrayList<>();
        String p="feed/";
        String t0="getNewsArticleList";
        String t1="newsFeedReaction";
        ret.add(pr(p,t0));
        ret.add(pr(p,t1));
        return ret;
    }
    public static List<String> url_chat(){
        List<String>ret=new ArrayList<>();
        String p="chat/";

        String t0="chat";
        String t1="sendChatImage";
        String t2="getImageById";
        String t3="getMessageRequest";
        String t4="sendMessageRequest";

        String t5="getChatDetail";
        String t6="getProfile";
        String t7="getChatAfterIndex";
        String t8="actionMessageRequest";
        ret.add(pr(p,t0));
        ret.add(pr(p,t1));
        ret.add(pr(p,t2));
        ret.add(pr(p,t3));
        ret.add(pr(p,t4));
        ret.add(pr(p,t5));
        ret.add(pr(p,t6));
        ret.add(pr(p,t7));
        ret.add(pr(p,t8));
        return ret;
    }

    public static List<String> url_diary(){
        List<String>ret=new ArrayList<>();
        String p="diary/";
        String t0="getBoardContent";
        String t1="uploadBoardContent";
        ret.add(pr(p,t0));
        ret.add(pr(p,t1));
        return ret;
    }
    public static List<String> url_security(){
        List<String>ret=new ArrayList<>();
        String p="security/";
        String t0="createPrimaryTrustChain";
        String t1="deletePrimaryTrustChain";
        String t2="alertMessage";
        ret.add(pr(p,t0));
        ret.add(pr(p,t1));
        ret.add(pr(p,t2));
        return ret;
    }
    public static List<String> url_image(){
        List<String>ret=new ArrayList<>();
        String p="image/";
        String t0="uploadImage";
        String t1="getImageById";
        ret.add(pr(p,t0));
        ret.add(pr(p,t1));
        return ret;
    }
    public static List<String> url_social(){
        List<String>ret=new ArrayList<>();
        String p="social/";
        String t0="getInGroupPool";
        String t1="getInPool";
        String t2="getPoolStatus";
        String t3="groupPoolStatus";
        ret.add(pr(p,t0));
        ret.add(pr(p,t1));
        ret.add(pr(p,t2));
        ret.add(pr(p,t3));
        return ret;
    }
}
