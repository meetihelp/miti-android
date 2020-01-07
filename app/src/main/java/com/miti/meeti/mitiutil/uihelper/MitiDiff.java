package com.miti.meeti.mitiutil.uihelper;

import com.miti.meeti.mitiutil.Logging.Mlog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MitiDiff<Attri,Original>{
    public HashMap<Attri, Original> getattri(List<?>lis,String fieldname){
        HashMap<Attri, Original> map=new HashMap<>();
        for(Object x : lis) {
            Class<?> clazz = x.getClass();
            try{
                Field field = clazz.getField(fieldname); //Note, this can throw an exception if the field doesn't exist.
                Object fieldValue = field.get(x);
                map.put((Attri)fieldValue,(Original)x);
            }catch (Exception e){
                Mlog.e("Field not found");
                return null;
            }
        }
        return map;
    }
    public List<Original> getx(List<?>oldlist,List<?>newlist,String fieldname){
        HashMap<Attri, Original> map1=getattri(oldlist,fieldname);
        HashMap<Attri, Original> map2=getattri(newlist,fieldname);
        List<Original>temp=new ArrayList<>();
        Set<Attri>temp1=map2.keySet();
        for(Attri temp2:temp1){
            if(!map1.containsKey(temp2)){
                temp.add(map2.get(temp2));
            }
        }
        return temp;
    }
}
