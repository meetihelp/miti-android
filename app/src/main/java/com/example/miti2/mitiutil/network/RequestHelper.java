package com.example.miti2.mitiutil.network;

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
