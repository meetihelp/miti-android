package com.miti.meeti.mitiutil.network;

import java.util.HashMap;

public class Keyvalue {
    public String key,value;
    public Keyvalue(String key,String value){
        this.key=key;
        this.value=value;
    }
    public static HashMap<String,String>GetHashMap(Keyvalue...temp){
        HashMap<String,String> ret=new HashMap<>();
        for(int i=0;i<temp.length;i++){
            ret.put(temp[i].key,temp[i].value);
        }
        return ret;
    }

}
