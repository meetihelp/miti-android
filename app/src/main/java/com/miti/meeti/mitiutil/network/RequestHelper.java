package com.miti.meeti.mitiutil.network;

public class RequestHelper {
    private String MitiCookie;
    private String data;

    public RequestHelper(String MitiCookie, String data){
        this.MitiCookie=MitiCookie;
        this.data=data;
    }

    public String getData(){
        return this.data;
    }

    public String getMitiCookie(){
        return this.MitiCookie;
    }
}
