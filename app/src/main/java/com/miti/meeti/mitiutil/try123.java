package com.miti.meeti.mitiutil;

import com.miti.meeti.mitiutil.Logging.Mlog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
public class try123 {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
    public static String mitidt(){
        String mitidt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        return mitidt;
    }
    public static Date getdate(String dt){
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try{
            return format1.parse(dt);
        }catch (Exception e){
            Mlog.e("try123.java->","getdate(String dt)",e.toString());
            return null;
        }
    }
    public static Void createifnot(String path){
        File folder = new File(path);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }else{
            Mlog.e("",path,"folder already present");
        }
        if (success) {
            Mlog.e("folder created");
        } else {
            Mlog.e("folder creation failed");
        }
        return null;
    }
}
