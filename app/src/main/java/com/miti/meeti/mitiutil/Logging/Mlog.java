package com.miti.meeti.mitiutil.Logging;

import android.util.Log;


import com.google.gson.Gson;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class Mlog<T>{
    public static void et(String tag,String error){
        Log.e(tag,error);
    }
    public static void e(String ...error){
        String s1="";
        for(String s:error){
            s1=s1+","+s;
        }
        Log.e("Control",s1);
    }
    public void e1(T error){
        Gson gson=new Gson();
        String temp=gson.toJson(error);
        Log.e("Control",temp);
    }
    public static void e(String tag,Exception error){
        Log.e(tag,error.toString());
    }
    public static void e(Exception error){
        Log.e("Control",error.toString());
    }
    public static void printStackTrace(Exception e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        Log.e("Control",sw.toString());
    }
    public static void printStackTrace(Exception e,int limit){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        Log.e("Control",sw.toString().substring(0,limit));
    }
    public static void printStackTrace(Exception e,int limit,String tag){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        Log.e(tag,sw.toString().substring(0,limit));
    }
    public static void printStackTrace(Exception e,String tag){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        Log.e(tag,sw.toString());
    }
}
