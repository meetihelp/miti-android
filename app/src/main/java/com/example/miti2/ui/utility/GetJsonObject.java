package com.example.miti2.ui.utility;

import org.json.JSONException;
import org.json.JSONObject;

public class GetJsonObject {
//    private String[] Key={"Phone","Password"};
//    private String[] Value={"9304116447","mypassword"};
//
//    public GetJsonObject(String[] key, String[] value) {
////        this.Key=Key;
////        this.Value=Value;
//    }


    public String getJson(String[] Key,String[] Value) throws JSONException {
        int length=Key.length;
        JSONObject json=new JSONObject();
        for(int i=0;i<length;i++){
            json.put(Key[i],Value[i]);
        }

        return json.toString();
    }

    public String getValue(String result,String key){
        try {
            if(result!=null) {
                JSONObject json = new JSONObject(result);
                result = json.getString(key);
                return result;
            }
            else{
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
